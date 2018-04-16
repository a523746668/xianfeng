package com.qingyii.hxtz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.qingyii.hxtz.adapter.AuditCrewListAdapter;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.httpway.GLUpload;
import com.qingyii.hxtz.pojo.AuditCrewList;
import com.qingyii.hxtz.pojo.AuditGroupList;
import com.qingyii.hxtz.pojo.DocumentaryStatus;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class AuditCrewListActivity extends AbBaseActivity implements View.OnClickListener {
    private TextView activity_tltle_name;
    private Intent intent;
    private ListView mListView;
    private LinearLayout empty_ll;
    private AuditCrewListAdapter auditCrewListAdapter;
    private AbPullToRefreshView mPullRefreshView;

    private DocumentaryStatus.DataBean dsDataBean;
    private AuditGroupList.DataBean.GroupsBean aGroupsBean;

    private AuditCrewList.DataBean aDataBean = null;
    private List<AuditCrewList.DataBean.UsresBean> aUsresBeanList = new ArrayList<>();

    //提交按钮
    private TextView inform_confirm;

    //弹窗
    private Dialog dialog;
    //确认弹窗
    private View affirmContentLayout;
    private TextView affirm_context;
    private TextView affirm_cancel;
    private TextView affirm_submit;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                default:
                    break;
            }
            auditCrewListAdapter.notifyDataSetChanged();
            //刷新，加载更新提示隐藏
            mPullRefreshView.onHeaderRefreshFinish();
            mPullRefreshView.onFooterLoadFinish();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_crew_list);
        //tltle设置
        activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
        activity_tltle_name.setText("部门纪实");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        dsDataBean = intent.getParcelableExtra("dsDataBean");
        aGroupsBean = intent.getParcelableExtra("aGroupsBean");

        if (dsDataBean != null)
            if (aGroupsBean != null) {
                String[] month = dsDataBean.getCurr_month().split("-");
                activity_tltle_name.setText(aGroupsBean.getName() + " " + Integer.parseInt(month[month.length - 1]) + " 月纪实审核");
            } else {
                String[] month = dsDataBean.getCurr_month().split("-");
                activity_tltle_name.setText(Integer.parseInt(month[month.length - 1]) + " 月部门纪实");
            }

        initView();
    }

    private void initView() {
        inform_confirm = (TextView) findViewById(R.id.inform_confirm);

        //弹窗设置
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);

        mListView = (ListView) findViewById(R.id.mListView);
        empty_ll = (LinearLayout) findViewById(R.id.empty_ll);
        mPullRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);

        auditCrewListAdapter = new AuditCrewListAdapter(this, aUsresBeanList);
        mListView.setAdapter(auditCrewListAdapter);
        mListView.setEmptyView(empty_ll);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(AuditCrewListActivity.this, AuditContextListActivity.class);
                intent.putExtra("aUsresBean", aUsresBeanList.get(position));
                intent.putExtra("dsDataBean", dsDataBean);
                startActivity(intent);
            }
        });

        //下拉刷新
        mPullRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
//                refreshTask();
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        });
        //上拉加载
        mPullRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
//                loadMoreTask();
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (MyApplication.userUtil.getCheck_level()) {
            case 1:
                getDateList("");
//                activity_tltle_name.setText("部门纪实");
                break;
            case 2:
//                activity_tltle_name.setText(aGroupsBean.getName() + "纪实审核");
                getDateList(aGroupsBean.getId() + "");
                break;
            case 4:
//                activity_tltle_name.setText(aGroupsBean.getName() + "纪实审核");
                getDateList(aGroupsBean.getId() + "");
                break;
            case 5:
//                activity_tltle_name.setText(aGroupsBean.getName() + "纪实审核");
                getDateList(aGroupsBean.getId() + "");
                break;
            default:
                break;
        }
    }

    public void initF5OME() {

        int state = 0;
        for (int i = 0; i < aDataBean.getUsres().size(); i++) {
            if (aDataBean.getUsres().get(i).getAppeal() == 1)
                state++;
            if (aDataBean.getUsres().get(i).getUnchecked() == 1)
                state++;
//            if (aDataBean.getUsres().get(i).getUnconfirm() == 1)
//                state++;
        }

        if (aDataBean.getUsres().size() > 0) {
            inform_confirm.setVisibility(View.VISIBLE);
        }else {
            //Toast.makeText(this, "此月没有讯息，请确认后查询", Toast.LENGTH_LONG).show();
        }
        if (aDataBean.getIs_checked() == 1) {
            inform_confirm.setText("已上报");
            inform_confirm.setBackgroundColor(Color.parseColor("#727272"));
        } else if (state <= 0) {
//            if (dsDataBean.getCheck_cnt() <= 0) {
//                inform_confirm.setBackgroundColor(Color.parseColor("#727272"));
//                inform_confirm.setTextColor(Color.parseColor("#FFFFFF"));
//            } else {
            inform_confirm.setText("完成审核并通知确认");
            inform_confirm.setBackgroundColor(Color.parseColor("#F34235"));
            inform_confirm.setOnClickListener(this);
//            }
        } else {
            inform_confirm.setText("请完成审核再通知确认");
            inform_confirm.setBackgroundColor(Color.parseColor("#727272"));
        }
    }

    public void initF5TWO() {
    }

    public void initF5FOUR() {
    }

    public void initF5FIVE() {
    }

    //初始化 待确认列表界面
    private View initAffirmContentLayout() {
        affirmContentLayout = View.inflate(this, R.layout.user_context_affirm_menu, null);
        affirm_context = (TextView) affirmContentLayout.findViewById(R.id.affirm_context);
        affirm_cancel = (TextView) affirmContentLayout.findViewById(R.id.affirm_cancel);
        affirm_submit = (TextView) affirmContentLayout.findViewById(R.id.affirm_submit);
        affirm_cancel.setOnClickListener(this);
        affirm_submit.setOnClickListener(this);

        return affirmContentLayout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inform_confirm:
                //将布局设置给Dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.setContentView(initAffirmContentLayout());
                    affirm_context.setText("确定要提交通知确认？");
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
                }
                break;
            case R.id.affirm_cancel:
                dialog.dismiss();
                break;
            case R.id.affirm_submit:
                GLUpload glUpload = GLUpload.getGLUpload();
                glUpload.reportUpload(AuditCrewListActivity.this, dialog, dsDataBean.getCurr_month());
                break;
            default:
                break;
        }
    }

    /**
     * 我的纪实数据
     */
    public void getDateList(String group_id) {

        Log.e("Url", XrjHttpClient.getManageListUrl() + "/" + dsDataBean.getCurr_month() + "/users");

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl() + "/" + dsDataBean.getCurr_month() + "/users")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("group_id", group_id + "")
                .build()
                .execute(new AuditCrewListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("AuditCrewList_onError", e.toString());
                                 Toast.makeText(AuditCrewListActivity.this, "网络已断开，请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(AuditCrewList response, int id) {
                                 Log.e("AuditCrewListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         aDataBean = response.getData();
                                         aUsresBeanList.clear();
                                         aUsresBeanList.addAll(aDataBean.getUsres());
                                         switch (MyApplication.userUtil.getCheck_level()) {
                                             case 1:
                                                 initF5OME();
                                                 break;
                                             case 2:
                                                 initF5TWO();
                                                 break;
                                             case 4:
                                                 initF5FOUR();
                                                 break;
                                             case 5:
                                                 initF5FIVE();
                                                 break;
                                             default:
                                                 break;
                                         }
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(AuditCrewListActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class AuditCrewListCallback extends com.zhy.http.okhttp.callback.Callback<AuditCrewList> {

        @Override
        public AuditCrewList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("人员名单 JSON", result);
            AuditCrewList auditCrewList = new Gson().fromJson(result, AuditCrewList.class);
            return auditCrewList;
        }
    }
}

package com.qingyii.hxtz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.qingyii.hxtz.adapter.AuditGroupListAdapter;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.httpway.GLUpload;
import com.qingyii.hxtz.pojo.AuditGroupList;
import com.qingyii.hxtz.pojo.DocumentaryStatus;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class AuditGroupListActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView activity_tltle_name;
    private Intent intent;
    private ListView mListView;
    private LinearLayout empty_ll;
    private AuditGroupListAdapter auditGroupListAdapter;
    private AbPullToRefreshView mPullRefreshView;

    private DocumentaryStatus.DataBean dsDataBean;

    private AuditGroupList.DataBean aDataBean;
    private AuditGroupList.DataBean.GroupsBean aGroupsBean;
    private List<AuditGroupList.DataBean.GroupsBean> aGroupsBeanList = new ArrayList<>();

    //二级管理按钮
    private TextView audit_group_two_hint;
    private TextView audit_group_two_up;
    private TextView audit_group_two_in;
    //四级管理按钮
    private TextView audit_group_four_up;
    //五级管理按钮
    private TextView audit_group_fvie_up;
    private TextView audit_group_fvie_out;

    //弹窗
    private Dialog dialog;
    //确认弹窗
    private View affirmContentLayout;
    private TextView affirm_context;
    private TextView affirm_cancel;
    private TextView affirm_submit;

    GLUpload glUpload = GLUpload.getGLUpload();

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1190: //GLUpload.yishangbao
                    changeButtonColor();

                    break;
                default:
                    break;
            }
            auditGroupListAdapter.notifyDataSetChanged();
            //刷新，加载更新提示隐藏
            mPullRefreshView.onHeaderRefreshFinish();
            mPullRefreshView.onFooterLoadFinish();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_group_list);
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
                //截取尾部 转化为int
                String[] month = dsDataBean.getCurr_month().split("-");
                activity_tltle_name.setText(aGroupsBean.getName() + " " + Integer.parseInt(month[month.length - 1]) + " 月纪实");
            } else {
                String[] month = dsDataBean.getCurr_month().split("-");
                activity_tltle_name.setText(Integer.parseInt(month[month.length - 1]) + " 月部门纪实");
            }

        initView();
    }

    private void initView() {
        //二级管理按钮
        audit_group_two_hint = (TextView) findViewById(R.id.audit_group_two_hint);
        audit_group_two_up = (TextView) findViewById(R.id.audit_group_two_up);
        audit_group_two_in = (TextView) findViewById(R.id.audit_group_two_in);
        //四级管理按钮
        audit_group_four_up = (TextView) findViewById(R.id.audit_group_four_up);
        //五级管理按钮
        audit_group_fvie_up = (TextView) findViewById(R.id.audit_group_fvie_up);
        audit_group_fvie_out = (TextView) findViewById(R.id.audit_group_fvie_out);

        //弹窗设置
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);

        mListView = (ListView) findViewById(R.id.mListView);
        empty_ll = (LinearLayout) findViewById(R.id.empty_ll);
        mPullRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);

        auditGroupListAdapter = new AuditGroupListAdapter(this, aGroupsBeanList);
        mListView.setAdapter(auditGroupListAdapter);
        mListView.setEmptyView(empty_ll);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (aDataBean.getGroups().get(position).getIs_haveuser() == 1)
                    intent = new Intent(AuditGroupListActivity.this, AuditCrewListActivity.class);
                else
                    intent = new Intent(AuditGroupListActivity.this, AuditGroupListActivity.class);
                intent.putExtra("aGroupsBean", aGroupsBeanList.get(position));
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
        if (aGroupsBean == null)
            getDateList("");
        else
            getDateList(aGroupsBean.getId() + "");

    }

    private void initF5TWO() {
        if (aGroupsBean == null) {
            audit_group_two_up.setVisibility(View.VISIBLE);
            if (aDataBean.getIs_checked() == 1) {
                audit_group_two_up.setText("已上报");
                audit_group_two_up.setBackgroundColor(Color.parseColor("#727272"));
            } else {
//            audit_group_two_in.setVisibility(NotifyListView.VISIBLE);
                audit_group_two_hint.setVisibility(View.VISIBLE);
                if (dsDataBean.getCheck_cnt() <= 0) {
                    audit_group_two_up.setText("请稍后再审核");
                    audit_group_two_up.setBackgroundColor(Color.parseColor("#727272"));
                } else
                    audit_group_two_up.setOnClickListener(this);
                audit_group_two_in.setOnClickListener(this);
            }
        }
    }

    private void initF5FOUR() {
        if (aGroupsBean == null) {
            audit_group_four_up.setVisibility(View.VISIBLE);
            if (aDataBean.getIs_checked() == 1) {
                audit_group_four_up.setText("已审阅");
                audit_group_four_up.setBackgroundColor(Color.parseColor("#727272"));
            } else {
                if (dsDataBean.getCheck_cnt() <= 0) {
                    audit_group_four_up.setText("请稍后再审阅");
                    audit_group_four_up.setBackgroundColor(Color.parseColor("#727272"));
                } else
                    audit_group_four_up.setOnClickListener(this);
            }
        }
    }

    private void initF5FIVE() {
        if (aGroupsBean == null) {
            if (aDataBean.getIs_reject() == 1) {
                audit_group_fvie_out.setVisibility(View.VISIBLE);
                audit_group_fvie_out.setText("已退回");
                audit_group_fvie_up.setBackgroundColor(Color.parseColor("#727272"));
            } else if (aDataBean.getIs_checked() == 1) {
                audit_group_fvie_up.setVisibility(View.VISIBLE);
                audit_group_fvie_up.setText("本月数据已归档");
                audit_group_fvie_up.setBackgroundColor(Color.parseColor("#727272"));
            } else {
                audit_group_fvie_up.setVisibility(View.VISIBLE);
                if (dsDataBean.getCheck_cnt() <= 0) {
                    audit_group_fvie_up.setText("请稍后再审定");
                    audit_group_fvie_up.setBackgroundColor(Color.parseColor("#727272"));
                } else {
                    audit_group_fvie_out.setVisibility(View.VISIBLE);
                    audit_group_fvie_up.setOnClickListener(this);
                    audit_group_fvie_out.setOnClickListener(this);
                    audit_group_fvie_up.setBackgroundColor(Color.parseColor("#F34235"));
                }
            }
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.audit_group_two_up://同意上报
                //将布局设置给Dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.setContentView(initAffirmContentLayout());
                    affirm_context.setText("确定要上报？");
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
                }
                break;
            case R.id.audit_group_two_in://后台修改
                //将布局设置给Dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.setContentView(initAffirmContentLayout());
                    affirm_context.setText("确定转到后台修改？");
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
                }
                break;
            case R.id.audit_group_four_up://已  阅
                //将布局设置给Dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.setContentView(initAffirmContentLayout());
                    affirm_context.setText("确认已阅？");
                    //dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().width = (int) (getWindowManager().getDefaultDisplay().getWidth() * 0.8);
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
                }
                break;
            case R.id.audit_group_fvie_up://同意归档
                //将布局设置给Dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.setContentView(initAffirmContentLayout());
                    affirm_context.setText("确定进行归档操作？\n归档之后数据不可修改");
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
                }
                break;
            case R.id.audit_group_fvie_out://退回重审
                //将布局设置给Dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.setContentView(initAffirmContentLayout());
                    affirm_context.setText("确定进行退回重审操作？");
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
                }
                break;
            case R.id.affirm_cancel://弹窗取消
                dialog.dismiss();
                break;
            case R.id.affirm_submit://弹窗确认
                switch (affirm_context.getText().toString()) {
                    case "确定要上报？":
                        glUpload.reportUpload(AuditGroupListActivity.this, dialog, dsDataBean.getCurr_month(),handler);
                        break;
                    case "确认已阅？":
                        glUpload.reportUpload(AuditGroupListActivity.this, dialog, dsDataBean.getCurr_month());
                        break;
                    case "确定转到后台修改？":
                        dialog.dismiss();
                        AuditGroupListActivity.this.finish();
                        Toast.makeText(AuditGroupListActivity.this, "已经向后台提交", Toast.LENGTH_LONG).show();
                        break;
                    case "确定进行归档操作？\n归档之后数据不可修改":
                        glUpload.reportUpload(AuditGroupListActivity.this, dialog, dsDataBean.getCurr_month());
                        break;
                    case "确定进行退回重审操作？":
                        dialog.dismiss();
                        glUpload.rejectUpload(AuditGroupListActivity.this, dialog, dsDataBean.getCurr_month());
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void changeButtonColor() {

        if (affirm_submit!=null) {
            affirm_submit.setBackgroundColor(Color.parseColor("#727272"));
        }
    }

    /**
     * 我的纪实数据
     */
    public void getDateList(String group_id) {

//        Log.e("AuditGroupList_URL", XrjHttpClient.getManageListUrl() + "/" + dsDataBean.getCurr_month() + "/groups");
        Log.e("AuditGroup_ID", group_id);
        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl() + "/" + dsDataBean.getCurr_month() + "/groups")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("group_id", group_id + "")
                .build()
                .execute(new AuditGroupListCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("AuditGroupList_onError", e.toString());
                        Toast.makeText(AuditGroupListActivity.this, "网络已断开，请检查网络", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onResponse(AuditGroupList response, int id) {
                        Log.e("AuditGroupListCallback", response.getError_msg());

                        switch (response.getError_code()) {
                            case 0:
                                aDataBean = response.getData();
                                aGroupsBeanList.clear();
                                aGroupsBeanList.addAll(response.getData().getGroups());
                                switch (MyApplication.userUtil.getCheck_level()) {
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
                                Toast.makeText(AuditGroupListActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                break;
                        }

                    }
                });
    }

    private abstract class AuditGroupListCallback extends com.zhy.http.okhttp.callback.Callback<AuditGroupList> {
        @Override
        public AuditGroupList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("小组列表 JSON", result);
            AuditGroupList auditGroupList = new Gson().fromJson(result, AuditGroupList.class);
            return auditGroupList;
        }
    }
}

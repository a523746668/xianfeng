package com.qingyii.hxt;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.qingyii.hxt.adapter.AuditContextListAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.AuditCrewList;
import com.qingyii.hxt.pojo.DocumentaryStatus;
import com.qingyii.hxt.pojo.WaitAuditList;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class AuditContextListActivity extends AbBaseActivity implements View.OnClickListener {
    private TextView activity_tltle_name;
    private Intent intent;
    private ListView mListView;
    private AuditContextListAdapter auditContextListAdapter;
    private AbPullToRefreshView mPullRefreshView;

    private DocumentaryStatus.DataBean dsDataBean;
    private AuditCrewList.DataBean.UsresBean aUsresBean;

    private List<WaitAuditList.DataBean> wDataBeanList = new ArrayList<>();

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                default:
                    break;
            }
            auditContextListAdapter.notifyDataSetChanged();
            //刷新，加载更新提示隐藏
            mPullRefreshView.onHeaderRefreshFinish();
            mPullRefreshView.onFooterLoadFinish();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_context_list);
        //tltle设置
        activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
//        activity_tltle_name.setText("待确认打分项");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        aUsresBean = intent.getParcelableExtra("aUsresBean");
        dsDataBean = intent.getParcelableExtra("dsDataBean");

        if (aUsresBean != null && dsDataBean != null) {
            String[] month = dsDataBean.getCurr_month().split("-");
            activity_tltle_name.setText(aUsresBean.getUsername() + " " + Integer.parseInt(month[month.length - 1]) + " 月纪实审核");
        }

        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        mPullRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);

        auditContextListAdapter = new AuditContextListAdapter(this, wDataBeanList);
        mListView.setAdapter(auditContextListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(AuditContextListActivity.this, AuditContextActivity.class);
                intent.putExtra("wDataBean", wDataBeanList.get(position));
                intent.putExtra("aUsresBean", aUsresBean);
                intent.putExtra("dsDataBean", dsDataBean);
                startActivity(intent);
            }
        });

        //下拉刷新
        mPullRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
//                refreshTask();
                handler.sendEmptyMessageDelayed(1, 3000);
            }
        });
        //上拉加载
        mPullRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
//                loadMoreTask();
                handler.sendEmptyMessageDelayed(1, 3000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDateList();
//        switch (MyApplication.userUtil.getCheck_level()) {
//            case 1:
//                activity_tltle_name.setText("待确认打分项");
//                break;
//            case 2:
//                activity_tltle_name.setText(aUsresBean.getUsername() + "的纪实审核");
//                break;
//            case 4:
//                activity_tltle_name.setText(aUsresBean.getUsername() + "的纪实审核");
//                break;
//            case 5:
//                activity_tltle_name.setText(aUsresBean.getUsername() + "的纪实审核");
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 我的纪实数据
     */
    public void getDateList() {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getManageListUrl() + "/" + dsDataBean.getCurr_month() + "/user/" + aUsresBean.getUser_id())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new WaitAuditListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("WaitAffirmList_onError", e.toString());
                                 Toast.makeText(AuditContextListActivity.this, "网络已断开，请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(WaitAuditList response, int id) {
                                 Log.e("WaitAffirmListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         wDataBeanList.clear();
                                         wDataBeanList.addAll(response.getData());
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(AuditContextListActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class WaitAuditListCallback extends com.zhy.http.okhttp.callback.Callback<WaitAuditList> {

        @Override
        public WaitAuditList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("待审核列表 JSON", result);
            WaitAuditList waitAuditList = new Gson().fromJson(result, WaitAuditList.class);
            return waitAuditList;
        }
    }
}

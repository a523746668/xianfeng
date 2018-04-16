package com.qingyii.hxtz.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.qingyii.hxtz.AuditContextListActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.adapter.AuditGroupListAdapter;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.AuditGroupList;
import com.qingyii.hxtz.pojo.DocumentaryMy;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class AuditGroupListActivity extends AbBaseActivity implements View.OnClickListener{
    private Intent intent;
    private ListView mListView;
    private AuditGroupListAdapter auditGroupListAdapter;
    private AbPullToRefreshView mPullRefreshView;

    private DocumentaryMy.DataBean dDataBean;

    private List<AuditGroupList.DataBean> aDataBeanList = new ArrayList<>();

    private TextView inform_confirm;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
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
        TextView activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
        activity_tltle_name.setText("部门纪实");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        dDataBean = intent.getParcelableExtra("dDataBean");

        mListView = (ListView) findViewById(R.id.mListView);
        mPullRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);

//        auditGroupListAdapter = new AuditGroupListAdapter(this,aDataBeanList);
        mListView.setAdapter(auditGroupListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(AuditGroupListActivity.this, AuditContextListActivity.class);
                intent.putExtra("aDataBean", aDataBeanList.get(position));
                intent.putExtra("dDataBean", dDataBean);
                startActivity(intent);
            }
        });

        //下拉刷新
        mPullRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
//                refreshTask();
                handler.sendEmptyMessageDelayed(1,3000);
            }
        });
        //上拉加载
        mPullRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
//                loadMoreTask();
                handler.sendEmptyMessageDelayed(1,3000);
            }
        });

        switch (MyApplication.userUtil.getCheck_level()){
//            case 1:
//                initOME();
//                break;
            case 1:
                initTWO();
                break;
            case 4:
                initFOUR();
                break;
            case 5:
                initFIVE();
                break;
            default:
                break;
        }
    }

//    public void initOME(){
//    }

    public void initTWO(){
        inform_confirm = (TextView) findViewById(R.id.inform_confirm);
        inform_confirm.setVisibility(View.VISIBLE);
        inform_confirm.setOnClickListener(this);
        getDateList();
    }

    public void initFOUR(){
    }

    public void initFIVE(){
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 我的纪实数据
     */
    public void getDateList() {

        Log.e("AuditGroupList_URL", XrjHttpClient.getManageListUrl()+"/" + dDataBean.getCurr_month()+"/groups");
        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl()+"/" + dDataBean.getCurr_month()+"/groups")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new AuditGroupListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("AuditGroupList_onError", e.toString());
                                 Toast.makeText(AuditGroupListActivity.this, "网络异常—部门纪实", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(AuditGroupList response, int id) {
                                 Log.e("AuditGroupListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
//                                         aDataBeanList.clear();
//                                         aDataBeanList.addAll(response.getData());
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class AuditGroupListCallback extends com.zhy.http.okhttp.callback.Callback<AuditGroupList> {

        @Override
        public AuditGroupList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("二级审核用户列表 JSON", "{\"error_msg\": \"success\",\"error_code\": 0,\"data\": [{\"id\": 108,\"name\": \"党支部\",\"remark\": \"党支部\",\"people_cnt\": 9,\"docs_cnt\": 5,\"submit_cnt\": 3}]}");
            AuditGroupList auditGroupList = new Gson().fromJson(
                    "{\"error_msg\": \"success\",\"error_code\": 0,\"data\": [{\"id\": 108,\"name\": \"党支部\",\"remark\": \"党支部\",\"people_cnt\": 9,\"docs_cnt\": 5,\"submit_cnt\": 3}]}"
//                    result
                    , AuditGroupList.class);
            return auditGroupList;
        }
    }
}

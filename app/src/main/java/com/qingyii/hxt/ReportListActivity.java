package com.qingyii.hxt;

import android.content.Intent;
import android.os.Message;
import android.os.Handler;
import android.os.Handler.Callback;
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
import com.qingyii.hxt.adapter.ReportListAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.DocumentaryStatus;
import com.qingyii.hxt.pojo.WaitAffirmList;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ReportListActivity extends AbBaseActivity implements View.OnClickListener {
    private Intent intent;
    private ListView mListView;
    private LinearLayout empty_ll;
    private ReportListAdapter reportListAdapter;
    private AbPullToRefreshView mPullRefreshView;

    private DocumentaryStatus.DataBean dsDataBean;

    private List<WaitAffirmList.DataBean> wDataBeanList = new ArrayList<>();

    private TextView a_key_to_confirm;

    private Handler handler = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    getDateList();
                    break;
                default:
                    break;
            }
            reportListAdapter.notifyDataSetChanged();
            //刷新，加载更新提示隐藏
            mPullRefreshView.onHeaderRefreshFinish();
            mPullRefreshView.onFooterLoadFinish();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        //tltle设置
        TextView activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
        activity_tltle_name.setText("纪实积分确认");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        dsDataBean = intent.getParcelableExtra("dsDataBean");

        mListView = (ListView) findViewById(R.id.mListView);
        empty_ll = (LinearLayout) findViewById(R.id.empty_ll);
        mPullRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);

        reportListAdapter = new ReportListAdapter(this, wDataBeanList, handler);
        mListView.setAdapter(reportListAdapter);
        mListView.setEmptyView(empty_ll);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(ReportListActivity.this, ReportContextActivity.class);
                intent.putExtra("wDataBean", wDataBeanList.get(position));
                startActivity(intent);
            }
        });

        a_key_to_confirm = (TextView) findViewById(R.id.a_key_to_confirm);
        a_key_to_confirm.setOnClickListener(this);

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

//        getDateList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDateList();
    }

    protected void refreshTask() {
        getDateList();
    }

    protected void loadMoreTask() {
        getDateList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.a_key_to_confirm:

                break;
            default:
                break;
        }
    }

    /**
     * 我的纪实数据
     */
    public void getDateList() {

//        Log.e("待审核列表_URL", XrjHttpClient.getWaitAffirmListUrl() + dsDataBean.getCurr_month());

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getWaitAffirmListUrl() + dsDataBean.getCurr_month())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new WaitAffirmListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("WaitAffirmList_onError", e.toString());
                                 Toast.makeText(ReportListActivity.this, "网络已断开，请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(WaitAffirmList response, int id) {
                                 Log.e("WaitAffirmListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         wDataBeanList.clear();
                                         wDataBeanList.addAll(response.getData());
//                                         if (wDataBeanList.size() == 0)
//                                             Toast.makeText(ReportListActivity.this, "您本月没有需要审核的记录", Toast.LENGTH_LONG).show();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(ReportListActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class WaitAffirmListCallback extends com.zhy.http.okhttp.callback.Callback<WaitAffirmList> {

        @Override
        public WaitAffirmList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("待确认列表 JSON", result);
            WaitAffirmList waitAffirmList = new Gson().fromJson(result, WaitAffirmList.class);
            return waitAffirmList;
        }
    }
}

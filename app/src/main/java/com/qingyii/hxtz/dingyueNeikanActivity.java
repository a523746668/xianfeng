package com.qingyii.hxtz;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.qingyii.hxtz.adapter.dingYueNeikanAdapter;
import com.qingyii.hxtz.httpway.NKUpload;
import com.qingyii.hxtz.pojo.SubscribedNK;

import java.util.ArrayList;
import java.util.List;

/**
 * 订阅内刊
 *
 * @author Administrator
 */
public class dingyueNeikanActivity extends AbBaseActivity {
    //    private ArrayList<Magazine> list = new ArrayList<Magazine>();
    private List<SubscribedNK.DataBean> list = new ArrayList<SubscribedNK.DataBean>();
    private ListView lv_dyneikan_item;
    private LinearLayout iv_back;
    private dingYueNeikanAdapter adapter;
    private Handler handler = new Handler(new Callback() {

        @SuppressLint("NewApi")
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                adapter.notifyDataSetChanged();
            } else if (msg.what == 2) {
                Toast.makeText(dingyueNeikanActivity.this, "已是最新数据", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 0) {
                Toast.makeText(dingyueNeikanActivity.this, "数据加载异常", Toast.LENGTH_SHORT).show();
            }

            //刷新，加载更新提示隐藏
            mAbPullToRefreshView.onHeaderRefreshFinish();
            mAbPullToRefreshView.onFooterLoadFinish();
            return false;
        }
    });
    private int page = 1;
    private int pagesize = 10;
    //1上拉刷新 2下拉加载
//    int type = 1;
    private AbPullToRefreshView mAbPullToRefreshView;
    private LinearLayout emptyView;
    private NKUpload nkUpload = NKUpload.getNKUpload();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingyueneikan);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("订阅内刊");

        //注册刷新数据广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.neikan");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        initUI();
        initData();
    }

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.neikan")) {
//	        	   list.clear();
                refreshTask();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.finish();
    }

    private void initData() {
        refreshTask();
    }

    private void initUI() {
        lv_dyneikan_item = (ListView) findViewById(R.id.lv_dyneikan_item);
        iv_back = (LinearLayout) findViewById(R.id.returns_arrow);
        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        adapter = new dingYueNeikanAdapter(this, list, handler);
        lv_dyneikan_item.setAdapter(adapter);

        emptyView = (LinearLayout) findViewById(R.id.empty_dingyueneikan);
        lv_dyneikan_item.setEmptyView(emptyView);


        mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPull_suggestion);
        // 设置监听器
        mAbPullToRefreshView
                .setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(AbPullToRefreshView view) {
                        refreshTask();
                    }
                });
        mAbPullToRefreshView
                .setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

                    @Override
                    public void onFooterLoad(AbPullToRefreshView view) {
                        loadMoreTask();

                    }
                });

        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));

    }

    protected void refreshTask() {
//        type = 1;
//        getNeikanList(1, pagesize);
        nkUpload.NKUnSubscribed(this, adapter, 0, list, handler);
    }

    protected void loadMoreTask() {
//        type = 2;
//        getNeikanList(page, pagesize);
        nkUpload.NKUnSubscribed(this, adapter, list.get(list.size()-1).getId(), list, handler);
    }

    /**
     * 获取内刊列表
     */
    private void getNeikanList(int mypage, int mypagesize) {
//        JSONObject jsonObj = new JSONObject();
//        try {
//            jsonObj.put("page", mypage);
//            jsonObj.put("pagesize", mypagesize);
//            jsonObj.put("flag", 1);
//            jsonObj.put("userid", CacheUtil.userid + "");
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            // 处理保存数据中文乱码问题
//            try {
//                bytes = jsonObj.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("dingyue_获取内刊_out", jsonObj.toString());
//
//                YzyHttpClient.post(dingyueNeikanActivity.this, HttpUrlConfig.queryMagazineList,
//                        entity, new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("dingyue_获取内刊_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(dingyueNeikanActivity.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(dingyueNeikanActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    Gson gson = new Gson();
//                                    try {
//                                        if (type == 1) {
//                                            list.clear();
//                                            page = 2;
//                                        }
//                                        JSONObject Obj = new JSONObject(content);
//                                        if (EmptyUtil.IsNotEmpty(Obj.getString("rows"))) {
//                                            JSONArray mlist = Obj.getJSONArray("rows");
//                                            if (mlist.length() <= 0) {
//                                                emptyView.setText("暂无内容");
//                                                handler.sendEmptyMessage(2);
//                                            } else {
//                                                //如果获取到新数据 页码加一
//                                                if (type == 2) {
//                                                    page += 1;
//                                                }
//                                                for (int i = 0; i < mlist.length(); i++) {
//                                                    Magazine m = gson.fromJson(mlist.getString(i), Magazine.class);
//                                                    list.add(m);
//                                                }
//                                            }
//                                            handler.sendEmptyMessage(1);
//                                        } else {
//                                            handler.sendEmptyMessage(2);
//                                        }
//
//                                    } catch (JSONException e) {
//                                        handler.sendEmptyMessage(0);
//                                        e.printStackTrace();
//                                    }
//
//
//                                }
//                            }
//
//                        });
//            } catch (UnsupportedEncodingException e) {
//                handler.sendEmptyMessage(0);
//                e.printStackTrace();
//            }
//
//        } catch (JSONException e) {
//            handler.sendEmptyMessage(0);
//            e.printStackTrace();
//        }
    }
}

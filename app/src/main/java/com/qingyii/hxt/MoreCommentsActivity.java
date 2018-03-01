package com.qingyii.hxt;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.view.dialog.AbProgressDialogFragment;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.qingyii.hxt.adapter.PingLunAdapter;
import com.qingyii.hxt.httpway.SJIpload;
import com.qingyii.hxt.pojo.BooksParameter;
import com.qingyii.hxt.pojo.Comment;

import java.util.ArrayList;

public class MoreCommentsActivity extends AbBaseActivity {

    private ImageView iv_back;
    private AbPullToRefreshView abPullToRefreshView = null;
    //private ArrayList<Discuss> list = new ArrayList<Discuss>();
    private ArrayList<Comment.DataBean> list = new ArrayList<Comment.DataBean>();
    private ListView listView = null;
    private PingLunAdapter adapter;
    //private Book bookInfo;
    private BooksParameter.DataBean bookInfo;
    //private int page = 1;
    //private int pagesize = 10;

    //private int type = 1;
    private Handler handler = new Handler(new Callback() {

        @SuppressLint("NewApi")
        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
//            if (abLoadDialogFragment != null) {
//                abLoadDialogFragment.dismiss();
//            }
            if (msg.what == 1) {
//                Toast.makeText(MoreCommentsActivity.this, "已是最新数据",
//                        Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                adapter.notifyDataSetChanged();
            } else if (msg.what == 3) {
//                Toast.makeText(MoreCommentsActivity.this, "数据加载异常",
//                        Toast.LENGTH_SHORT).show();
            }

            abPullToRefreshView.onHeaderRefreshFinish();
            abPullToRefreshView.onFooterLoadFinish();
            return false;
        }
    });

    SJIpload sjIpload = SJIpload.getSJIpload();

    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//  private AbLoadDialogFragment abLoadDialogFragment = null;
//    private AbProgressDialogFragment abLoadDialogFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.more_comments);
        iv_back = (ImageView) findViewById(R.id.back_btn);
        iv_back.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        //注册刷新数据广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshMyAddress");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        bookInfo = (BooksParameter.DataBean) getIntent().getParcelableExtra("book");

        initUI();
        initDate();
        sjIpload.BooksCommentList(MoreCommentsActivity.this, adapter, 0, bookInfo, list, handler);

    }

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals("action.refreshMyAddress")) {
//                list.clear();
//                getcomment(1, pagesize);
            }
        }
    };

    private void initDate() {
        /**
         * 进度框
         */

        /**
         * Load失效修改为Progress
         *
         * 监听失效
         */
//        abLoadDialogFragment = AbDialogUtil.showLoadDialog(this,
//                R.mipmap.ic_load, "查询中,请等一小会");
//        abLoadDialogFragment
//                .setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//
//                    public void onLoad() {
//                        // TODO Auto-generated method stub
//                        refreshTask();
//
//                    }
//                });
//        abLoadDialogFragment = AbDialogUtil.showProgressDialog(this, R.mipmap.ic_load, "查询中,请等一小会");
    }

    private void initUI() {

        listView = (ListView) findViewById(R.id.list_more_comment);
        abPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView_more_comment);

        adapter = new PingLunAdapter(this, list, bookInfo);
        listView.setAdapter(adapter);

        /**
         * 下拉刷新，监听
         */
        abPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            public void onHeaderRefresh(AbPullToRefreshView view) {
                // TODO Auto-generated method stub
//                loadMoreTask();
                sjIpload.BooksCommentList(MoreCommentsActivity.this, adapter, 0, bookInfo, list, handler);
            }
        });

        /**
         * 上拉加载，监听
         */
        abPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            public void onFooterLoad(AbPullToRefreshView view) {
                // TODO Auto-generated method stub
//                refreshTask();
                sjIpload.BooksCommentList(MoreCommentsActivity.this, adapter, list.get(list.size()-1).getId(), bookInfo, list, handler);
            }
        });

        /**
         * 设置上下拉刷新式样
         */
        // 设置进度条的样式
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
    }

//    protected void loadMoreTask() {
//        type = 2;
//        getcomment(page, pagesize);
//    }
//
//    protected void refreshTask() {
//        type = 1;
//        getcomment(1, pagesize);
//    }

//    private void getcomment(int mypage, int mypagesize) {
//        JSONObject jsonObject = new JSONObject();
//
//        try {
////            jsonObject.put("bookid", bookInfo.getBookid());
//            jsonObject.put("page", mypage);
//            jsonObject.put("pagesize", mypagesize);
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            try {
//                bytes = jsonObject.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//                YzyHttpClient.post(MoreCommentsActivity.this,
//                        HttpUrlConfig.queryDiscussList2, entity,
//                        new AsyncHttpResponseHandler() {
//
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//                                // TODO Auto-generated method stub
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(MoreCommentsActivity.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(MoreCommentsActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    Gson gson = new Gson();
//                                    try {
//                                        if (type == 1) {
//                                            list.clear();
//                                            page = 2;
//
//                                        }
//                                        JSONObject jsonObject = new JSONObject(content);
//                                        JSONArray array = jsonObject.getJSONArray("rows");
//                                        if (array.length() <= 0) {
//                                            handler.sendEmptyMessage(1);
//                                        } else {
//                                            if (type == 2) {
//                                                page += 1;
//                                            }
//                                            for (int i = 0; i < array.length(); i++) {
//                                                Discuss m = gson.fromJson(array.getString(i), Discuss.class);
//                                                list.add(m);
//                                            }
//                                        }
//                                        handler.sendEmptyMessage(2);
//
//                                    } catch (JSONException e) {
//                                        // TODO Auto-generated catch block
//                                        handler.sendEmptyMessage(3);
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            }
//                        });
//
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                handler.sendEmptyMessage(3);
//                e.printStackTrace();
//
//            }
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            handler.sendEmptyMessage(3);
//            e.printStackTrace();
//        }
//    }
}

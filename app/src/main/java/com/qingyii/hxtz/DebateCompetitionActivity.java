package com.qingyii.hxtz;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.http.listener.AbHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.util.AbToastUtil;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.qingyii.hxtz.adapter.CommentListAdapter;
import com.qingyii.hxtz.bean.CommentInfo;
import com.qingyii.hxtz.bean.huodongInfo;
import com.qingyii.hxtz.http.NetworkWeb;

import java.util.ArrayList;
import java.util.List;

//import com.ab.http.AbHttpListener;
//import com.ab.http.AbRequestParams;
//import com.ab.util.AbToastUtil;
//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
//import com.andbase.library.http.listener.AbHttpListener;

public class DebateCompetitionActivity extends AbBaseActivity {
    private TextView tv_content, tv_time, tv_enter, tv_title;
    private ImageView back_btn;
    private huodongInfo huodong;
    private ListView lv_comment;
    private AbPullToRefreshView mAbPullToRefreshView;
    private EditText et_reply;
    private CommentListAdapter mAdapter;
    private ArrayList<CommentInfo> list;
    private String replyTxt;
    private Handler handler;
    private int currentPage = 1;
    private int pageSize = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case 1:
                        mAdapter = new CommentListAdapter(DebateCompetitionActivity.this, list);
                        lv_comment.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
        initUI();
        refreshTask();
        ;
    }

    /**
     * 获取评论数据
     */
    private void refreshTask() {
        currentPage = 1;
        AbRequestParams params = new AbRequestParams();
        params.put("huodongId", huodong.getId());
        params.put("pageSize", String.valueOf(pageSize));
        params.put("toPageNo", String.valueOf(currentPage));
        // 下载网络数据
        NetworkWeb web = NetworkWeb.newInstance(DebateCompetitionActivity.this);
        /**
         * AbHttpListener 失效，修改为 AbHttpResponseListener
         */
//        web.findLogList(params, new AbHttpListener() {
//
//            @Override
//            public void onSuccess(List<?> newList) {
//                list.clear();
//                if (newList != null && newList.size() > 0) {
//                    list.addAll((List<CommentInfo>) newList);
//                    newList.clear();
//                    handler.sendEmptyMessage(1);
//                }
//
//            }
//
//            @Override
//            public void onFailure(String content) {
//                AbToastUtil.showToast(DebateCompetitionActivity.this, content);
//                //显示重试的框
//            }
//
//        });

        web.findLogList(params, new AbHttpResponseListener() {

            @Override
            public void onSuccess(int statusCode, Object content) {
                super.onSuccess(statusCode, content);
                list.clear();
                List<?> newList = (List<?>)content;
                if (newList != null && newList.size() > 0) {
                    list.addAll((List<CommentInfo>) newList);
                    newList.clear();
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                AbToastUtil.showToast(DebateCompetitionActivity.this, s);
                //显示重试的框

            }
        });
    }

    private void initUI() {
        huodong = (huodongInfo) getIntent().getSerializableExtra("huodongdetail");
        tv_title = (TextView) findViewById(R.id.title_suggestion);
        tv_title.setText("辩论赛");
        tv_content = (TextView) findViewById(R.id.tv_content_suggestion);
        tv_time = (TextView) findViewById(R.id.tv_end_time_suggestion);
        back_btn = (ImageView) findViewById(R.id.iv_back_suggestion);
        tv_enter = (TextView) findViewById(R.id.enter_suggestion);
        et_reply = (EditText) findViewById(R.id.et_suggestion);
        lv_comment = (ListView) findViewById(R.id.lv_comment_suggestion);
        list = new ArrayList<CommentInfo>();
//		mAdapter = new CommentListAdapter(this, list);
//		lv_comment.setAdapter(mAdapter);
        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        tv_content.setText(huodong.getContent());
        tv_time.setText(huodong.getEndTime());
        tv_enter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                replyTxt = et_reply.getText().toString();
                if (replyTxt.equals("")) {
                    Toast.makeText(DebateCompetitionActivity.this, "评论不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    //提交评论
                    sendComment();
                    //提交完刷新列表
                    handler.sendEmptyMessage(1);
                }
            }
        });
        initPullToRefresh();
    }

    private void initPullToRefresh() {
        mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPull_suggestion);
        //设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                refreshTask();
            }
        });
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                loadMoreTask();

            }
        });

        //设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));

    }

    protected void loadMoreTask() {
        currentPage++;
        // 绑定参数
        AbRequestParams params = new AbRequestParams();
        params.put("cityCode", "11");
        params.put("pageSize", String.valueOf(pageSize));
        params.put("toPageNo", String.valueOf(currentPage));
        // 下载网络数据
        NetworkWeb web = NetworkWeb.newInstance(this);
        /**
         * AbHttpListener 失效，修改为 AbHttpResponseListener
         */
//        web.findLogList(params, new AbHttpListener() {
//
//            @Override
//            public void onSuccess(List<?> newList) {
//                if (newList != null && newList.size() > 0) {
//                    list.addAll((List<CommentInfo>) newList);
//                    newList.clear();
//                    handler.sendEmptyMessage(1);
//                }
//                mAbPullToRefreshView.onFooterLoadFinish();
//            }
//
//            @Override
//            public void onFailure(String content) {
//
//            }
//
//        });

        web.findLogList(params, new AbHttpResponseListener() {

            @Override
            public void onSuccess(int statusCode, Object content) {
                super.onSuccess(statusCode, content);
                List<?> newList = (List<?>)content;
                if (newList != null && newList.size() > 0) {
                    list.addAll((List<CommentInfo>) newList);
                    newList.clear();
                    handler.sendEmptyMessage(1);
                }
                mAbPullToRefreshView.onFooterLoadFinish();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {

            }
        });
    }

    /**
     * 提交评论
     */
    protected void sendComment() {
    }

}

package com.qingyii.hxt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxt.adapter.ZuanTiItemAdapter;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.YzyHttpClient;
import com.qingyii.hxt.httpway.SJIpload;
import com.qingyii.hxt.pojo.Book;
import com.qingyii.hxt.pojo.BooksClassify;
import com.qingyii.hxt.pojo.BooksParameter;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

/**
 * 专题列表界面
 *
 * @author Administrator
 */
public class ZuantiListActivity extends AbBaseActivity {
    private ListView mListView = null;
    private AbPullToRefreshView mAbPullToRefreshView = null;
    private ZuanTiItemAdapter adapter;
    //    private ArrayList<Book> list = new ArrayList<Book>();
    private List<BooksParameter.DataBean> list = new ArrayList<BooksParameter.DataBean>();
    private BooksClassify.DataBean bookinfo;
    private ImageView back_btn;
    private TextView tv_liebiao_title;
    private Handler handler;
    private int page = 1;
    private int pagesize = 10;
    // 列表操作类型：1表示下拉刷新，2表示上拉加载更多
    int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenleiliebiao);
        handler = new Handler(new Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    adapter.notifyDataSetChanged();
                } else if (msg.what == 0) {
                    Toast.makeText(ZuantiListActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 5) {
                    Toast.makeText(ZuantiListActivity.this, "已是最新数据", Toast.LENGTH_SHORT).show();
                }
                if (mAbPullToRefreshView != null) {
                    //刷新，加载更新提示隐藏
                    mAbPullToRefreshView.onHeaderRefreshFinish();
                    mAbPullToRefreshView.onFooterLoadFinish();
                }
                return false;
            }
        });
        bookinfo = (BooksClassify.DataBean) getIntent().getParcelableExtra("Book");
        initUI();
        initDATA();
    }

    private void initDATA() {
//        zhuantiBookList(page, pagesize);
        SJIpload sjIpload = SJIpload.getSJIpload();
        sjIpload.BooksSubjectList(this, adapter, bookinfo.getId() + "",0, list, handler);
    }

    private void initUI() {
        tv_liebiao_title = (TextView) findViewById(R.id.tv_liebiao_title);
        mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
        mListView = (ListView) findViewById(R.id.mListView);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        tv_liebiao_title.setText(bookinfo.getName());
        adapter = new ZuanTiItemAdapter(ZuantiListActivity.this, list);
        mListView.setAdapter(adapter);

        LinearLayout emptyView;
        //当listview没有内容时显示暂无内容
        emptyView = (LinearLayout) findViewById(R.id.empty_fragment_shuwu);
        mListView.setEmptyView(emptyView);

        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                refreshTask();

            }
        });
        //上拉加载
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                loadMoreTask();

            }
        });

        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent it = new Intent(ZuantiListActivity.this, shujixiangqingActivity.class);
                it.putExtra("Book", list.get(position));
                startActivity(it);
            }
        });
        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    protected void refreshTask() {
        type = 1;
//        zhuantiBookList(1, pagesize);
        SJIpload sjIpload = SJIpload.getSJIpload();
        sjIpload.BooksSubjectList(this, adapter, bookinfo.getId() + "",0, list, handler);
    }

    protected void loadMoreTask() {
        type = 2;
//        zhuantiBookList(page, pagesize);
        SJIpload sjIpload = SJIpload.getSJIpload();
        if (list.size() > 0)
            sjIpload.BooksSubjectList(this, adapter, "recommend", list.get(list.size()-1).getId(), list, handler);
    }
}

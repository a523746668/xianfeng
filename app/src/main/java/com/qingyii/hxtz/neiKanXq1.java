package com.qingyii.hxtz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxtz.adapter.neiKanXq1Adapter;
import com.qingyii.hxtz.httpway.NKUpload;
import com.qingyii.hxtz.pojo.ArticleListNK;
import com.qingyii.hxtz.pojo.AxpectNK;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章列表
 *
 * @author Administrator
 */
public class neiKanXq1 extends BaseActivity {
    private LinearLayout back_btn;
    private AbPullToRefreshView mPullRefreshView;
    private ListView mListView;
    private LinearLayout emptyView;
    private TextView tv_neikan_qishu;
    //    private ArrayList<PeriodsArticleRela> list = new ArrayList<PeriodsArticleRela>();
    private List<ArticleListNK.DataBean> list = new ArrayList<ArticleListNK.DataBean>();
    private neiKanXq1Adapter adapter;
    //    private int page = 1;
//    private int pagesize = 10;
//    int type = 1;
    //    private Periods periods;
//    private PeriodsArticleRela rela;
//    private int posionId;
    //    private Magazine magazine;
    //private SubscribedNK.DataBean sDataBean;
    private AxpectNK.DataBean aDataBean;

    private ShapeLoadingDialog shapeLoadingDialog = null;

    /**
     * 期数ID:极光推送时用到，点击添加的期数加载文章列表
     */
    private int periodsid = 0;

    private Thread thread;

    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }
            if (msg.what == 2) {
                Toast.makeText(neiKanXq1.this, "已是最新数据", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 0) {
                Toast.makeText(neiKanXq1.this, "获取数据异常", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                //开启缓存数据线程
                adapter.notifyDataSetChanged();
            }
            //刷新，加载更新提示隐藏
            mPullRefreshView.onHeaderRefreshFinish();
            mPullRefreshView.onFooterLoadFinish();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neikanxq1);

        /**
         * 接收广播刷新事件
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.broadcast");
        registerReceiver(broadcastReceiver, intentFilter);

//        periods = (Periods) getIntent().getSerializableExtra("Periods");
//        magazine = (Magazine) getIntent().getSerializableExtra("Magazine");
        aDataBean = getIntent().getParcelableExtra("AxpectNK");
        //sDataBean = getIntent().getParcelableExtra("SubscribedNK");

        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("查询中,请等一小会");
        shapeLoadingDialog.show();

        initData();
        initUI();
        /**
         * 原本被封装入广播中
         */
        list.clear();
        refreshTask();
    }

    /**
     * 广播
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals("action.broadcast")) {
                list.clear();
                refreshTask();
            }
        }
    };

    private void initData() {
//        getNkQishuXqing(page, pagesize);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.finish();
    }

    private void initUI() {

        back_btn = (LinearLayout) findViewById(R.id.returns_arrow);
        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        tv_neikan_qishu = (TextView) findViewById(R.id.activity_tltle_name);
//        if (periods != null) {
//            tv_neikan_qishu.setText(periods.getPeriodsname());
//        }
        if (aDataBean != null) {
            tv_neikan_qishu.setText(aDataBean.getIssuename());
        }
        mPullRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
        mListView = (ListView) findViewById(R.id.mListView);
        adapter = new neiKanXq1Adapter(neiKanXq1.this, list);
        mListView.setAdapter(adapter);


        //当listview没有内容时显示暂无内容
        emptyView = (LinearLayout) findViewById(R.id.empty_neikanxq1);
        mListView.setEmptyView(emptyView);

        //下拉刷新
        mPullRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                refreshTask();

            }
        });
        //上拉加载
        mPullRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                loadMoreTask();

            }
        });

        // 设置进度条的样式
        mPullRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        mPullRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                posionId = position;
//                addreadcount();
                Intent it = null;

                ArticleListNK.DataBean oneArticle = list.get(position);

                String type = oneArticle.getType();
                System.out.println("a type ---" + type);

                if (type.equals("article")) {
                    it = new Intent(neiKanXq1.this, neiKanWebViewActivity.class);
                } else if (type.equals("pdf")) {
                    it = new Intent(neiKanXq1.this, neiKanPDFActivity.class);
                }

                Log.e("Is_upvote", list.get(position).getIs_upvote() + "");
                it.putExtra("Article", list.get(position));
                //it.putExtra("AxpectNK", aDataBean);
                //it.putExtra("SubscribedNK", sDataBean);
                startActivityForResult(it, position);
                //startActivity(it);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ArticleListNK.DataBean article_pdf = data.getParcelableExtra("Article");
            list.get(requestCode).setIs_upvote(article_pdf.getIs_upvote());
        }
    }

    protected void refreshTask() {
//        type = 1;
//        getNkQishuXqing(1, pagesize);
        NKUpload nkUpload = NKUpload.getNKUpload();
        nkUpload.NKArticleList(this, adapter, aDataBean.getId(), 0, list, handler);
    }

    protected void loadMoreTask() {
//        type = 2;
//        getNkQishuXqing(page, pagesize);
        NKUpload nkUpload = NKUpload.getNKUpload();
        nkUpload.NKArticleList(this, adapter, aDataBean.getId(), list.get(list.size() - 1).getId(), list, handler);
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}

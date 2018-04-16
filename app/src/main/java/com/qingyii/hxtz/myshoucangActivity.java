package com.qingyii.hxtz;

import android.content.BroadcastReceiver;
import android.content.Context;
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
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxtz.adapter.neiKanXq1Adapter;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.ArticleListNK;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 我的收藏界面
 *
 * @author Administrator
 */
public class myshoucangActivity extends AbBaseActivity {
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

    private ShapeLoadingDialog shapeLoadingDialog = null;

    /**
     * 期数ID:极光推送时用到，点击添加的期数加载文章列表
     */
    private int periodsid = 0;

    private Thread thread;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }
            if (msg.what == 2) {
                Toast.makeText(myshoucangActivity.this, "已是最新数据", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 0) {
                Toast.makeText(myshoucangActivity.this, "获取数据异常", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                //开启缓存数据线程
                adapter.notifyDataSetChanged();
            }
            //刷新，加载更新提示隐藏

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshoucang);

        /**
         * 接收广播刷新事件
         */
      /*  IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.broadcast");
        registerReceiver(broadcastReceiver, intentFilter);  */

//        periods = (Periods) getIntent().getSerializableExtra("Periods");
//        magazine = (Magazine) getIntent().getSerializableExtra("Magazine");

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
      //  unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.finish();
    }

    private void initUI() {

        back_btn = (LinearLayout) findViewById(R.id.returns_arrow);
        back_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        tv_neikan_qishu = (TextView) findViewById(R.id.activity_tltle_name);
        tv_neikan_qishu.setText("我的收藏");
        mPullRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
        mListView = (ListView) findViewById(R.id.mListView);
        adapter = new neiKanXq1Adapter(myshoucangActivity.this, list);
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
                    it = new Intent(myshoucangActivity.this, neiKanWebActivity.class);
                } else if (type.equals("pdf")) {
                    it = new Intent(myshoucangActivity.this, neiKanPDFActivity.class);
                }


                it.putExtra("Article", list.get(position));
                startActivity(it);
            }
        });
    }

    protected void refreshTask() {
        ArticleList(0);
    }

    protected void loadMoreTask() {
        if (list.size() > 0)
            ArticleList(list.get(list.size() - 1).getId());
        for (int i = 0; i < list.size(); i++)
            Log.e("AdirectionID:", list.get(i).getId() + "");

    }

    /**
     * 收藏文章列表
     *
     * @param directionID
     */
    public void ArticleList(final int directionID) {

        Log.e("Article_directionID:", directionID + "");

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getCollectedUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("type", "article")
                .addParams("id", directionID + "")
                .addParams("direction", "lt")
                .build()
                .execute(new ArticleListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("ArticleList_onError", e.toString());
                                 Toast.makeText(myshoucangActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                                 handler.sendEmptyMessage(1);
                             }

                             @Override
                             public void onResponse(ArticleListNK response, int id) {
                                 Log.e("ArticleListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         if (directionID == 0)
                                             list.clear();
                                         if (response.getData().size() == 0)
                                             Toast.makeText(myshoucangActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
                                         list.addAll(response.getData());
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(myshoucangActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class ArticleListCallback extends Callback<ArticleListNK> {
        @Override
        public ArticleListNK parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("ArticleList_onError", result);
            ArticleListNK articleListNK = new Gson().fromJson(result, ArticleListNK.class);
            return articleListNK;
        }
    }

//    private void initUI() {
//        kanwuFragment page1 = new kanwuFragment();
////        MyshoucBooksFragment page2 = new MyshoucBooksFragment();
//        List<Fragment> fragments = new ArrayList<Fragment>();
//        fragments.add(page1);
////        fragments.add(page2);
//        List<String> tabTexts = new ArrayList<String>();
////        tabTexts.add("刊物");
////        tabTexts.add("书籍");
////        //设置样式
////        mAbSlidingTabView.setTabTextColor(Color.BLACK);
////        mAbSlidingTabView.setTabSelectColor(Color.rgb(30, 168, 131));
////        mAbSlidingTabView.setTabBackgroundResource(R.drawable.tab_bg);
////        mAbSlidingTabView.setTabLayoutBackgroundResource(R.drawable.slide_top);
////        //演示增加一组
////        mAbSlidingTabView.addItemViews(tabTexts, fragments);
////        mAbSlidingTabView.setTabPadding(20, 8, 20, 8);
////        mAbSlidingTabView.setBackgroundColor(getResources().getColor(R.color.gray));
//    }
}

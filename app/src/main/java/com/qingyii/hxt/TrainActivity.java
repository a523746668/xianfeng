package com.qingyii.hxt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.qingyii.hxt.adapter.TrainAdapter;
import com.qingyii.hxt.httpway.PSUpload;
import com.qingyii.hxt.pojo.TrainList;

import java.util.ArrayList;
import java.util.List;

/**
 * 培训首页
 */
public class TrainActivity extends AbBaseActivity {
    private ListView mListView = null;
    private LinearLayout emptyView;
    private TrainAdapter trainAdapter = null;
    private List<TrainList.DataBean> tDataBeanlist = new ArrayList<TrainList.DataBean>();
    private AbPullToRefreshView mAbPullToRefreshView = null;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                trainAdapter.notifyDataSetChanged();
            }
            if (mAbPullToRefreshView != null) {
                //刷新，加载更新提示隐藏
                mAbPullToRefreshView.onHeaderRefreshFinish();
                mAbPullToRefreshView.onFooterLoadFinish();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("培训");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);

        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 获取ListView对象
        mListView = (ListView) findViewById(R.id.mListView);
        emptyView = (LinearLayout) findViewById(R.id.empty_view);
        mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
        mListView.setEmptyView(emptyView);

        this.loadUI();
        this.loadDatas();
        this.onClick();

    }

    @Override
    public void onResume() {
        super.onResume();
        trainAdapter.notifyDataSetChanged();
    }

    /**
     * UI界面加载
     *
     */
    private void loadUI() {

        trainAdapter = new TrainAdapter(this, tDataBeanlist);
        mListView.setAdapter(trainAdapter);

        //下拉刷新
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
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));

    }

    private void loadDatas() {
        refreshTask();
    }

    private void onClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(TrainActivity.this, TrainDetailsActivity.class);
                intent.putExtra("training",tDataBeanlist.get(position));
                startActivity(intent);
            }
        });
    }

    protected void refreshTask() {
        PSUpload psUpload = PSUpload.getPSUpload();
        psUpload.trainList(this, trainAdapter, tDataBeanlist, handler);
    }

    protected void loadMoreTask() {
        PSUpload psUpload = PSUpload.getPSUpload();
        psUpload.trainList(this, trainAdapter, tDataBeanlist, handler);
    }
}

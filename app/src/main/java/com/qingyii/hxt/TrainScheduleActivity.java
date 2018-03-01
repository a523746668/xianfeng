package com.qingyii.hxt;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.qingyii.hxt.adapter.TrainScheduleAdapter;
import com.qingyii.hxt.httpway.PSUpload;
import com.qingyii.hxt.pojo.TrainCourseList;
import com.qingyii.hxt.pojo.TrainList;

import java.util.ArrayList;
import java.util.List;

/**
 * 培训日程
 */
public class TrainScheduleActivity extends AbBaseActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean;

    private ListView mListView = null;
    private AbPullToRefreshView mAbPullToRefreshView;
    private TrainScheduleAdapter trainAdapter = null;
    private List<TrainCourseList.DataBean> tcDataBeanList = new ArrayList<TrainCourseList.DataBean>();


    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
//            if (msg.what == 1) {
//                trainAdapter.notifyDataSetChanged();
//            }
//            if (mAbPullToRefreshView != null) {
//                //刷新，加载更新提示隐藏
            mAbPullToRefreshView.onHeaderRefreshFinish();
            mAbPullToRefreshView.onFooterLoadFinish();
//            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_schedule);
        intent = getIntent();
        tDataBean = intent.getParcelableExtra("training");

        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText(intent.getStringExtra("tltle"));
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 获取ListView对象
        mListView = (ListView) findViewById(R.id.mListView);
        mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);

        this.loadUI(savedInstanceState);
        this.loadDatas(savedInstanceState);
        this.onClick();
    }

    /**
     * UI界面加载
     *
     * @param savedInstanceState
     */
    private void loadUI(Bundle savedInstanceState) {

        trainAdapter = new TrainScheduleAdapter(this, tcDataBeanList);
        mListView.setAdapter(trainAdapter);

    }

    private void loadDatas(Bundle savedInstanceState) {
        refreshTask();

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
    }

    private void onClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrainScheduleActivity.this, ScheduleActivity.class);
//                intent.putExtra("tltle",tcDataBeanList.get(position).getCoursename());
                intent.putExtra("training", tDataBean);
                intent.putExtra("schedule", tcDataBeanList.get(position));
                startActivity(intent);
            }
        });
    }

    protected void refreshTask() {
        PSUpload psUpload = PSUpload.getPSUpload();
        psUpload.TrainCourseList(this, trainAdapter, tDataBean.getId(), 0, tcDataBeanList, handler);
    }

    protected void loadMoreTask() {
        PSUpload psUpload = PSUpload.getPSUpload();
        psUpload.TrainCourseList(this, trainAdapter, tDataBean.getId(), tcDataBeanList.get(tcDataBeanList.size() - 1).getId(), tcDataBeanList, handler);
    }
}

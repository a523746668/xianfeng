package com.qingyii.hxtz;

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
import com.qingyii.hxtz.adapter.TrainDownloadAdapter;
import com.qingyii.hxtz.httpway.PSUpload;
import com.qingyii.hxtz.pojo.TrainCourseList;
import com.qingyii.hxtz.pojo.TrainFilesList;
import com.qingyii.hxtz.pojo.TrainList;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程资料下载
 */
public class TrainCoursewareActivity extends AbBaseActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean;
    private TrainCourseList.DataBean tcDataBean;

    private ListView mListView = null;
    private TrainDownloadAdapter trainAdapter = null;
    private List<TrainFilesList.DataBean> tfDataBeanList = new ArrayList<TrainFilesList.DataBean>();


    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
//            if (msg.what == 1) {
//                trainAdapter.notifyDataSetChanged();
//            }
//            if (mAbPullToRefreshView != null) {
//                //刷新，加载更新提示隐藏
//                mAbPullToRefreshView.onHeaderRefreshFinish();
//                mAbPullToRefreshView.onFooterLoadFinish();
//            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_download);
        intent = getIntent();
        tDataBean = intent.getParcelableExtra("training");
        tcDataBean = intent.getParcelableExtra("schedule");

        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText(tcDataBean.getCoursename() + " 资料");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 获取ListView对象
        mListView = (ListView) findViewById(R.id.mListView);

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

        trainAdapter = new TrainDownloadAdapter(this, tfDataBeanList);
        mListView.setAdapter(trainAdapter);

    }

    private void loadDatas(Bundle savedInstanceState) {
        refreshTask();
    }

    private void onClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrainCoursewareActivity.this, TrainDownloadActivity.class);
                intent.putExtra("training", tfDataBeanList.get(position));
                startActivity(intent);
            }
        });
    }

    protected void refreshTask() {
        PSUpload psUpload = PSUpload.getPSUpload();
        psUpload.trainFilesList(this, trainAdapter, tDataBean.getId() + "",
                tcDataBean.getId() + "", tfDataBeanList, handler);
    }

    protected void loadMoreTask() {
        PSUpload psUpload = PSUpload.getPSUpload();
        psUpload.trainFilesList(this, trainAdapter, tDataBean.getId() + "",
                tcDataBean.getId() + "", tfDataBeanList, handler);
    }
}

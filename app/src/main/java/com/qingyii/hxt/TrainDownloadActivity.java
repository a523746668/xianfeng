package com.qingyii.hxt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.util.FileUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxt.adapter.TrainDownloadAdapter;
import com.qingyii.hxt.base.app.EventBusTags;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.httpway.PSUpload;
import com.qingyii.hxt.meeting.di.component.DaggerMeetingDetailsComponent;
import com.qingyii.hxt.meeting.di.module.MeetingDetailsModule;
import com.qingyii.hxt.meeting.mvp.presenter.MeetingetailsPresenter;
import com.qingyii.hxt.pojo.TrainCourseList;
import com.qingyii.hxt.pojo.TrainFilesList;
import com.qingyii.hxt.pojo.TrainList;
import com.qingyii.hxt.util.UpdateUtil;
import com.zhf.PDFreadActivity;
import com.zhf.Util.FileUtil;

import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * 资料下载
 */
public class TrainDownloadActivity extends BaseActivity<MeetingetailsPresenter> implements CommonContract.MeetingDetailsView {
    private Intent intent;
    private TrainList.DataBean tDataBean;
    private TrainCourseList.DataBean tcDataBean;
    private ListView mListView = null;
    private TrainDownloadAdapter trainAdapter = null;
    private List<TrainFilesList.DataBean> tfDataBeanList = new ArrayList<TrainFilesList.DataBean>();
    private int position = -1;


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
        trainAdapter.setOnDownloadClickListener((TrainFilesList.DataBean data, View v) -> {
            File file = new File(HttpUrlConfig.cacheDir, data.getFilename());

            if (file.exists()) {
                v.setClickable(false);
                try{
                  /* if(FileUtil.ispdf(file)){
                       Intent intent=new Intent(this, PDFreadActivity.class);
                        intent.putExtra("path",file.getPath());
                        startActivity(intent);
                    }else {

                    } */
                    FileUtils.openFile(this, file.getPath(), FileUtils.getMIMEType(file.getPath()));
                } catch (Exception e){
                    Toasty.info(this,"没有打开此文件的应用",0).show();
                }

            } else {
               //mPresenter.downloadFile(data.getUri(), file, FileUtils.getMIMEType(file.getPath()));
                UpdateUtil util=new UpdateUtil();
                util.download(this,file,data.getUri());
            }
            v.setClickable(true);
            trainAdapter.notifyDataSetChanged();
        });
    }

    @Subscriber(tag = EventBusTags.DOWNLOAD)
    public void onEvent(Message msg) {
        if (position >= 0) {
            if (trainAdapter != null)
                trainAdapter.notifyDataSetChanged();
        }
    }

    protected void refreshTask() {
        PSUpload psUpload = PSUpload.getPSUpload();
        psUpload.trainFilesList(this, trainAdapter, tDataBean.getId() + "", tcDataBean == null ? null : String.valueOf(tcDataBean.getId()), tfDataBeanList, new Handler());
    }

    protected void loadMoreTask() {
        PSUpload psUpload = PSUpload.getPSUpload();
        psUpload.trainFilesList(this, trainAdapter, tDataBean.getId() + "", tcDataBean == null ? null : String.valueOf(tcDataBean.getId()), tfDataBeanList, new Handler());
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMeetingDetailsComponent
                .builder()
                .appComponent(appComponent)
                .meetingDetailsModule(new MeetingDetailsModule(this))
                .build()
                .inject(this);

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_train_download;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        intent = getIntent();
        tDataBean = intent.getParcelableExtra("training");
        if (intent.hasExtra("schedule")) {
            tcDataBean = intent.getParcelableExtra("schedule");
        }
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText(intent.getStringExtra("tltle"));
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(view -> finish());

        // 获取ListView对象
        mListView = (ListView) findViewById(R.id.mListView);

        this.loadUI(savedInstanceState);
        this.loadDatas(savedInstanceState);
        this.onClick();
    }

    @Override
    public void UpdateFeedbackStatus(String status) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }



}

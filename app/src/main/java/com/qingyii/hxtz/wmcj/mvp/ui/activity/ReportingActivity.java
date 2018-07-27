package com.qingyii.hxtz.wmcj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.widget.AutoLoadMoreRecyclerView;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerReportingComponent;
import com.qingyii.hxtz.wmcj.di.module.WorkParkItemModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkitembean;
import com.qingyii.hxtz.wmcj.mvp.presenter.ReportingPresenter;
import com.qingyii.hxtz.wmcj.mvp.presenter.WorkPresenter;
import com.qingyii.hxtz.zhf.Util.Global;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

//已上报任务
public class ReportingActivity extends BaseActivity<ReportingPresenter> implements WMCJContract.WorkParkView {
    @BindView(R.id.workpark_item_recyclerView)
    AutoLoadMoreRecyclerView recyclerView;

    @BindView(R.id.workpark_item_refresh)
    PtrClassicFrameLayout ptr;

    private ArrayList<WorkParkitembean.DataBean.AllactivityBean> list =new ArrayList<>();

    @BindView(R.id.toolbar_back)
    Button back;

    @BindView(R.id.toolbar_title)
    TextView title;

    private String time;

    @BindView(R.id.empty_view)
    AutoLinearLayout emtview;

    @Inject
    WorkPresenter presenter1;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerReportingComponent.builder()
                .appComponent(appComponent)
                .workParkItemModule(new WorkParkItemModule(this))
                .build()
                .inject(this);

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main2;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
          init();
       if(Global.userid==6){
       presenter1.getWorkMenu();
       }else {
           mPresenter.getReportdata();
       }


    }

    private void init() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("已上报任务列表");
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
              //  presenter.getreportingdata();
             if(Global.userid==6){
                mPresenter.getWorkItem(system_id);
             } else {
                 mPresenter.getReportdata();
             }


            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, content, header);
            }
        });
        recyclerView.setOnLoadMoreListener(new AutoLoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                 if(Global.userid==6){
                     mPresenter.getworkitemmore(system_id);
                 } else {
                     mPresenter.getReportMore();
                 }

            }
        });

    }

     private String  system_id;

    @Override
    public void getdatasuccess(WorkParkbean workParkbean) {
        system_id=String .valueOf(workParkbean.getData().getMenu_item().get(0).getId());
        mPresenter.getWorkItem(system_id);
    }

    @Override
    public void getdataerror(Exception e) {
        recyclerView.setVisibility(View.GONE);
        emtview.setVisibility(View.VISIBLE);
    }

    @Override
    public void setRecyclerviewAdapter(HeaderFooterAdapter adapter) {
        recyclerView.setAutoLayoutManager(new LinearLayoutManager(this))
                .setAutoItemAnimator(new DefaultItemAnimator())
                .addAutoItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL))
                .setAutoAdapter(adapter);
    }

    @Override
    public void doRefresh() {
            ptr.autoRefresh();
    }

    @Override
    public void finishRefresh() {
        ptr.refreshComplete();
    }

    @Override
    public void setflag(boolean flag) {

    }

    @Override
    public void showLoading() {
        recyclerView.notifyMoreLoaded();
    }

    @Override
    public void hideLoading() {
        ptr.refreshComplete();
    }

    @Override
    public void showMessage(String message) {
        recyclerView.setVisibility(View.VISIBLE);
        emtview.setVisibility(View.GONE);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}

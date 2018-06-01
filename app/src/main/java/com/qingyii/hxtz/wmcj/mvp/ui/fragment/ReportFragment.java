package com.qingyii.hxtz.wmcj.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.base.widget.AutoLoadMoreRecyclerView;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerAlreadyComponent;
import com.qingyii.hxtz.wmcj.di.module.WorkParkItemModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkitembean;
import com.qingyii.hxtz.wmcj.mvp.presenter.ReportingPresenter;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WMCJcategoryActivity;
import com.zhy.autolayout.AutoLinearLayout;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by zhf on 2018/4/12.
 */

public class ReportFragment extends BaseFragment<ReportingPresenter> implements WMCJContract.WorkParkView {


    @BindView(R.id.workpark_item_recyclerView)
    AutoLoadMoreRecyclerView recyclerView;

    @BindView(R.id.workpark_item_refresh)
    PtrClassicFrameLayout ptr;


    @BindView(R.id.toolbar_back)
    Button back;

    @BindView(R.id.toolbar_title)
    TextView title;

    private String time;

    @BindView(R.id.empty_view)
    AutoLinearLayout emtview;

    @BindView(R.id.toolbar_right_tv)
    TextView barright;

    private Context context;

    @BindView(R.id.sxrecyc)
    RecyclerView sxrecyc;

    private ArrayList<String>  shaixuan=new ArrayList<>();

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerAlreadyComponent.builder()
                .appComponent(appComponent)
                .workParkItemModule(new WorkParkItemModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main2,null,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
            context=getActivity();
            init();
        mPresenter.getReportdata();

    }
    private void init() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(true, EventBusTags.HOME);
                getActivity().finish();
            }
        });
        title.setText("文明创建完成动态");
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //  presenter.getreportingdata();
               mPresenter.getReportdata();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, content, header);
            }
        });
        recyclerView.setOnLoadMoreListener(new AutoLoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                mPresenter.getReportMore();
            }
        });
        barright.setText("筛选");
        barright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getActivity(), WMCJcategoryActivity.class);
               intent.putExtra("wmcj","Report");
               startActivity(intent);
            }
        });
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void getdatasuccess(WorkParkbean workParkbean) {

    }

    @Override
    public void getdataerror(Exception e) {
        recyclerView.setVisibility(View.GONE);
        emtview.setVisibility(View.VISIBLE);
    }

    @Override
    public void setRecyclerviewAdapter(HeaderFooterAdapter adapter) {
        recyclerView.setAutoLayoutManager(new LinearLayoutManager(context))
                .setAutoItemAnimator(new DefaultItemAnimator())
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

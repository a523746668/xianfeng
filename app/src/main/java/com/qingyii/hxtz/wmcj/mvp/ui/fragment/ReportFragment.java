package com.qingyii.hxtz.wmcj.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter;
import com.mcxtzhang.commonadapter.rv.IHeaderHelper;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.base.widget.AutoLoadMoreRecyclerView;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerAlreadyComponent;
import com.qingyii.hxtz.wmcj.di.module.WorkParkItemModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Common;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkitembean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.shuaixuan;
import com.qingyii.hxtz.wmcj.mvp.presenter.ReportingPresenter;
import com.qingyii.hxtz.wmcj.mvp.presenter.WorkPresenter;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WMCJcategoryActivity;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WMCj_categoryActivity;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.CommonViewBinder;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.selectViewBinder;
import com.qingyii.hxtz.zhf.Util.Global;
import com.qingyii.hxtz.zhf.Util.HintUtil;
import com.zhy.autolayout.AutoLinearLayout;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zhf on 2018/4/12.
 */

public class ReportFragment extends BaseFragment<ReportingPresenter> implements WMCJContract.WorkParkView {


    @BindView(R.id.workpark_item_recyclerView)
    AutoLoadMoreRecyclerView recyclerView;

    @BindView(R.id.workpark_item_refresh)
    PtrClassicFrameLayout ptr;

    @BindView(R.id.toolbar_back_layout)
    AutoLinearLayout back;

    @BindView(R.id.toolbar_title)
    TextView title;

    private String time;

    @BindView(R.id.empty_view)
    AutoLinearLayout emtview;

    @BindView(R.id.toolbar_right_tv)
    TextView barright;

    @BindView(R.id.toolbar_right_layout)
    AutoLinearLayout rightlayout;

    private Context context;

    @BindView(R.id.sxrecyc)
    RecyclerView sxrecyc;

    PtrClassicFrameLayout layout;

    private ArrayList<String>  shaixuan=new ArrayList<>();

    MultiTypeAdapter adapter;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout= (PtrClassicFrameLayout) view.findViewById(R.id.workpark_item_refresh);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
            context=getActivity();
            init();
            initadapter();
            mPresenter.getReportdata();
    }

    private void initadapter() {
        ChipsLayoutManager layoutManager =  ChipsLayoutManager.newBuilder(getContext())
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setScrollingEnabled(true)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();
        sxrecyc.setLayoutManager(layoutManager);
        adapter=new MultiTypeAdapter();
        adapter.register(Common.class,new CommonViewBinder());
        sxrecyc.setAdapter(adapter);
    }

       String system_id;
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
                if(sx!=null){
                    mPresenter.getReportSX(sx);
                }else {
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
                Log.i("tmdflag",flag+"-----");
                if(sx!=null){
                    mPresenter.getReportSXMore(sx);
                    Log.i("tmdflag",flag+"-----");
                } else {
                    mPresenter.getReportMore();
                }
            }
        });
        barright.setText("筛选");
        barright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getActivity(), WMCj_categoryActivity.class);
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
        // system_id=String.valueOf(workParkbean.getData().getMenu_item().get(0).getId());
         //mPresenter.getWorkItem(system_id);
    }

    @Override
    public void getdataerror(Exception e) {
      Log.i("tmdreport","12312312313");
        sxrecyc.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        emtview.setVisibility(View.VISIBLE);
    }

    @Override
    public void setRecyclerviewAdapter(HeaderFooterAdapter adapter) {
        recyclerView.setAutoLayoutManager(new LinearLayoutManager(context))
                .setAutoItemAnimator(new DefaultItemAnimator())
                .setAutoAdapter(adapter);
    }

    private boolean flag=true;
    @Override
    public void doRefresh() {
        ptr.autoRefresh();
    }

    @Override
    public void finishRefresh() {
        ptr.refreshComplete();
    }

    @Override
    public void setflag(boolean f) {
      flag=f;
      Log.i("tmdsetflag",flag+"---11");
    }

    @Override
    public void showLoading() {
        HintUtil.showdialog(getContext());
    }

    @Override
    public void hideLoading() {
        ptr.refreshComplete();
        recyclerView.notifyMoreLoaded();
    }

    @Override
    public void showMessage(String message) {
      //   sxrecyc.setVisibility(View.VISIBLE);
        layout.setVisibility(View.VISIBLE);
        emtview.setVisibility(View.GONE);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }
     private shuaixuan sx;
    ArrayList<Common>  commons =new ArrayList<>();
    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.WMCJ_REPORT)
    public void refreshsx(shuaixuan sx){
        commons.clear();
        commons.addAll(sx.getTtags());
        commons.addAll(sx.getOnetag());
        commons.addAll(sx.getTwotag());
        commons.addAll(sx.getDanwei());
        adapter.setItems(commons);
      //  Log.i("tmdsx",commons.size()+"----");
        adapter.notifyDataSetChanged();
        if(commons.size()>0){
            this.sx=sx;
            sxrecyc.setVisibility(View.VISIBLE);
        } else {
            sx=null;
            Log.i("tmdsx",commons.size()+"----");
            sxrecyc.setVisibility(View.GONE);
        }
        mPresenter.getReportSX(sx);
    }
}

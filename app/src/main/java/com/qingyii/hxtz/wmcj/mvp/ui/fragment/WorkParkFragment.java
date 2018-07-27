package com.qingyii.hxtz.wmcj.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.autolayout.AutoTabLayout;
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.base.widget.AutoLoadMoreRecyclerView;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerWorkComponent;
import com.qingyii.hxtz.wmcj.di.module.WorkModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkbean;
import com.qingyii.hxtz.wmcj.mvp.presenter.WorkParkItemPresenter;
import com.qingyii.hxtz.wmcj.mvp.presenter.WorkPresenter;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WorkParkCategoryActivity;


import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;


import javax.inject.Inject;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by zhf on 2018/3/12.  改写后的文明创建工作动态
 */

public class WorkParkFragment extends BaseFragment<WorkPresenter>  implements WMCJContract.WorkParkView {
    @BindView(R.id.toolbar_back)
    Button back;

    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.toolbar_right)
    Button  zhuanti;

    @BindView(R.id.workpark_tablayout)
    AutoTabLayout tablayout;

   @BindView(R.id.workpark_item_recyclerView)
    AutoLoadMoreRecyclerView recyclerView;

    @BindView(R.id.workpark_item_refresh)
    PtrClassicFrameLayout ptr;

    private ArrayList<WorkParkbean.DataBean.MenuItemBean> list1=new ArrayList<>();
    private ArrayList<WorkParkbean.DataBean.TagItemBean>  list2=new ArrayList<>();
    //private ArrayList<Fragment>  list=new ArrayList<>();
   // private WorkparkvpAdapter adapter;

    @Inject
    WorkParkItemPresenter mPresenter1;

    private boolean flag=true;
    private int system_id=-99;
    private int id;


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerWorkComponent.builder()
                .appComponent(appComponent)
                .workModule(new WorkModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workpark,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
       initview();
       mPresenter1.initadapter();

        ptr.setLastUpdateTimeRelateObject(this);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                if(system_id!=-99){
                    mPresenter1.getdata(id,system_id);
                } else {
                    mPresenter1.getdata(id);
                }

                if(flag){
                    mPresenter1.getimages();
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
                if(system_id!=-99){
                    mPresenter1.getdatamore(id,system_id);
                }  else {
                    mPresenter1.getdatamore(id);
                }
            }
        });


    }

    private void initview() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        title.setText("工作动态");
        zhuanti.setText("专题");

        zhuanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(getActivity(), WorkParkCategoryActivity.class);
                startActivity(intent);
            }
        });

        mPresenter.getWorkMenu();
    }

    @Override
    public void setData(Object data) {

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

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter1.onDestroy();
        list1.clear();
        list2.clear();
    }

    @Override
    public void getdatasuccess(WorkParkbean workParkbean) {
        zhuanti.setVisibility(View.VISIBLE);
        list1.clear();
        list1.addAll(workParkbean.getData().getMenu_item());
        list2.clear();
        list2.addAll(workParkbean.getData().getTag_item());
        id=list1.get(0).getId();
        system_id=list1.get(0).getSystem_id();
        getdata();
    }

    //
    private void getdata() {
        if(system_id!=-99){
            mPresenter1.getdata(id,system_id);
        }
        else {
            Log.i("tmdvipager","----"+id);
            mPresenter1.getdata(id);
        }
        if(flag){
            Log.i("tmddiyige","------");
            mPresenter1.getimages();

        }

    }

    @Override
    public void getdataerror(Exception e) {

    }

    @Override
    public void setRecyclerviewAdapter(HeaderFooterAdapter adapter) {
         recyclerView.setAutoLayoutManager(new LinearLayoutManager(getActivity()))
                .setAutoItemAnimator(new DefaultItemAnimator())
                .addAutoItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL))
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

    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.WORKPARK)
    public void getzhuanti(Message msg){
       recyclerView.scrollToPosition(0);
        int i=msg.arg1;
       if(i!=0){
           flag=false;
          mPresenter1.clearhead();
       } else {
           flag= true;
       }
        if(i<list1.size()){
            id=list1.get(i).getId();
            system_id=list1.get(i).getSystem_id();
        } else {
            id=list2.get(i-list1.size()).getId();
            system_id=-99;
        }
        getdata();
    }


}

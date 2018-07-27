package com.qingyii.hxtz.wmcj.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerTaskSonComponent;
import com.qingyii.hxtz.wmcj.di.module.TaskSonModule;
import com.qingyii.hxtz.wmcj.mvp.presenter.TaskSonPresenter;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.TaskLineAdaper;
import com.qingyii.hxtz.zhf.Util.HintUtil;
import com.qingyii.hxtz.zhf.wight.DefaultItem;

import butterknife.BindView;

/**
 * Created by zhf on 2018/3/22.
 */

public class TaskListSonFragment  extends BaseFragment<TaskSonPresenter> implements WMCJContract.TaskSonView {

    @BindView(R.id.tasklinerecyc)
     RecyclerView recyclerView;
    private int titleid;
    private String titlename;

    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }

    public void setTitleid(int titleid) {
        this.titleid = titleid;
    }



    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerTaskSonComponent.builder()
                .appComponent(appComponent)
                .taskSonModule(new TaskSonModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tasklistaonfragment,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
         mPresenter.getTaskSonData(titleid,titlename);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void showLoading() {
        HintUtil.showdialog(getContext());
    }

    @Override
    public void hideLoading() {
              HintUtil.stopdialog();
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
    public void setRecyclerviewAdapter(TaskLineAdaper adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DefaultItem());
    }
}

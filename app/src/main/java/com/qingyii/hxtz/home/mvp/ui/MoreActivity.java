package com.qingyii.hxtz.home.mvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.TrainActivity;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;

import com.qingyii.hxtz.home.di.component.DaggerHomeComponent;
import com.qingyii.hxtz.home.di.module.HomeModule;
import com.qingyii.hxtz.home.mvp.model.entity.FakeData;
import com.qingyii.hxtz.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxtz.home.mvp.presenter.HomePresenter;
import com.qingyii.hxtz.meeting.mvp.ui.activity.MeetingActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.qingyii.hxtz.base.app.GlobalConsts.MEETING;
import static com.qingyii.hxtz.base.app.GlobalConsts.TRAIN;

/**
 * 主页  改写成更多页
 */
public class MoreActivity extends BaseActivity<HomePresenter> implements CommonContract.HomeInfoView {

    @BindView(R.id.home_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar_back)
    Button back;

    @BindView(R.id.toolbar_title)
    TextView title;
    private Map<String, Class<?>> map = new HashMap<>();
    @Override
    public void setupActivityComponent(AppComponent appComponent) {

        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .injcet(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        initHomeInfoName();
     //   CrashHandler.getCrashHandler().init(this);

        return R.layout.activity_home;
    }

    private void initHomeInfoName() {

        map.put(MEETING, MeetingActivity.class);
        map.put(TRAIN, TrainActivity.class);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        inittoolbar();
        mPresenter.requestHomeInfo(this);
    }

    private void inittoolbar() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("更多");
    }

    @Override
    public void setAdapter(BaseRecyclerAdapter adapter) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<HomeInfo.AccountBean.ModulesBean>() {

            @Override
            public void onItemClick(HomeInfo.AccountBean.ModulesBean data, View view, int position) {


                Class clazz = map.get(data.getMark());
                if (clazz == null) {
                    showMessage(getString(R.string.function_is_developing));
                    return;
                }

                Intent it = new Intent(MoreActivity.this, map.get(data.getMark()));

                startActivity(it);

            }
            @Override
            public void onItemLongClick(HomeInfo.AccountBean.ModulesBean Data, View view, int position) {

            }
        });
    }

    @Override
    public void updateUI(HomeInfo homeInfo) {


    }

    @Override
    public RxPermissions getRxPermissions() {
        return null;
    }

    @Override
    public void getfakedatasuceess(FakeData fakeData) {


    }

    @Override
    public String[] getPerMissions() {
      return  null;
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

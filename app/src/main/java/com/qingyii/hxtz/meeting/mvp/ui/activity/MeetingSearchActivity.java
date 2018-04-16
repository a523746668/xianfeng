package com.qingyii.hxtz.meeting.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.base.widget.EmptyRecyclerView;
import com.qingyii.hxtz.meeting.di.component.DaggerMeetingSearchComponent;
import com.qingyii.hxtz.meeting.di.module.MeetingSearchModule;
import com.qingyii.hxtz.meeting.mvp.presenter.MeetingSearchPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MeetingSearchActivity extends BaseActivity<MeetingSearchPresenter> implements CommonContract.MeetingSearchListView, SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    @BindView(R.id.meeting_recyclerView)
    EmptyRecyclerView mRecyclerView;
    @Nullable
    @BindView(R.id.meeting_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Nullable
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.empty_view)
    View empty;
    private boolean isLoadingMore;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMeetingSearchComponent
                .builder()
                .appComponent(appComponent)
                .meetingSearchModule(new MeetingSearchModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_meeting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitle.setText(R.string.meeting_list);
        mPresenter.requestMeetingLists(true);
    }


    @Override
    public void showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void setAdapter(BaseRecyclerAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setEmptyView(empty);
//        adapter.setOnItemClickListener((view, viewType, data, position) -> {
//            NotifyList.DataBean bean = (NotifyList.DataBean) data;
////            Intent intent = new Intent(NotifyActivity.this, NotifyDetailsActivity.class);
//            //修改缓存状态 为已阅读，数据库数据 在进入下一个页面后修改
////                iDataBeanlist.get(i).setMark(1);
//            bean.setReaded(1);
//            intent.putExtra("notify", bean);
//            startActivityForResult(intent, position);
//        });
        initListener();
    }
    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(this));
//        initPaginate();
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }
    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @OnClick({R.id.toolbar_back_layout, R.id.toolbar_right_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_layout:
                break;
            case R.id.toolbar_right_layout:
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.requestMeetingLists(true);
    }
}
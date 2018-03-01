package com.qingyii.hxt.meeting.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.widget.AutoLoadMoreRecyclerView;
import com.qingyii.hxt.meeting.di.component.DaggerMeetingPublishComponent;
import com.qingyii.hxt.meeting.di.module.MeetingPublishModule;
import com.qingyii.hxt.meeting.mvp.presenter.MeetingPublishPresenter;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MeetingPublishItemFragment extends BaseFragment<MeetingPublishPresenter> implements CommonContract.MeetingPublishListView, SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    @BindView(R.id.meeting_publish_recyclerView)
    AutoLoadMoreRecyclerView mRecyclerView;
    @Nullable
    @BindView(R.id.meeting_publish_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_view)
    View empty;

    public static MeetingPublishItemFragment newInstance() {
        MeetingPublishItemFragment fragment = new MeetingPublishItemFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMeetingPublishComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .meetingPublishModule(new MeetingPublishModule(this))
                .build()
                .inject(this);

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meeting_publish_item, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.requestMeetingPublishLists(true);
        initListener();
    }

    @Override
    public void setAdapter(BaseRecyclerAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setAutoLayoutManager(new LinearLayoutManager(getActivity())).setAutoHasFixedSize(true)
                .setAutoItemAnimator(new DefaultItemAnimator()).setAutoAdapter(adapter);

        mRecyclerView.setOnLoadMoreListener(() -> {
            // 状态停止，并且滑动到最后一位
            mPresenter.requestMeetingPublishLists(false);
        });
        mRecyclerView.setEmptyView(empty);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object data,View view, int position) {
//            NotifyList.DataBean bean = (NotifyList.DataBean) adapter.getData().get(position);
//            Intent intent = new Intent(MeetingActivity.this, NotifyDetailsActivity.class);
//            //修改缓存状态 为已阅读，数据库数据 在进入下一个页面后修改
////                iDataBeanlist.get(i).setMark(1);
//            bean.setIs_receipt(1);
//            adapter.notifyDataSetChanged();
//            intent.putExtra("notify", bean);
//            startActivityForResult(intent, position);
            }

            @Override
            public void onItemLongClick(Object data,View view, int position) {

            }
        });
    }


    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
        if (mRecyclerView.isAllLoaded()) {
            // 之前全部加载完了的话，这里把状态改回来供底部加载用
            mRecyclerView.notifyMoreLoaded();
        }
        ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).showFooter(true);
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).hideFooter();
    }

    @Override
    public void notifyAllLoad() {
        mRecyclerView.notifyAllLoaded();
//        ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).hideFooter();
    }
    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onActivityCreated
     * 还没执行,setData里调用presenter的方法时,是会报空的,因为dagger注入是在onActivityCreated
     * 方法中执行的,如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

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

    }

    @Override
    public void onRefresh() {
        ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).getData().clear();
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.notifyMoreLoaded();
        mPresenter.requestMeetingPublishLists(true);
    }
}
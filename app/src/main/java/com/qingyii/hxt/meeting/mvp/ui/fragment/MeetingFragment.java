package com.qingyii.hxt.meeting.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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
import com.qingyii.hxt.base.app.EventBusTags;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.widget.AutoLoadMoreRecyclerView;
import com.qingyii.hxt.meeting.di.component.DaggerMeetingComponent;
import com.qingyii.hxt.meeting.di.module.MeetingModule;
import com.qingyii.hxt.meeting.di.module.entity.MeetingList;
import com.qingyii.hxt.meeting.mvp.presenter.MeetingPresenter;
import com.qingyii.hxt.meeting.mvp.ui.activity.MeetingDetailsActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MeetingFragment extends BaseFragment<MeetingPresenter> implements CommonContract.MeetingListView, SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    @BindView(R.id.meeting_recyclerView)
    AutoLoadMoreRecyclerView mRecyclerView;
    @Nullable
    @BindView(R.id.meeting_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_view)
    View empty;
    private boolean isLoadingMore;

    public static MeetingFragment newInstance() {
        MeetingFragment fragment = new MeetingFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMeetingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .meetingModule(new MeetingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meeting, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.requestMeetingLists(true);
        initListener();
    }

    @Override
    public void setAdapter(BaseRecyclerAdapter adapter) {
        mRecyclerView.setAutoLayoutManager(new LinearLayoutManager(getContext())).setAutoHasFixedSize(true)
                .setAutoItemAnimator(new DefaultItemAnimator()).setAutoAdapter(adapter);
        mRecyclerView.setEmptyView(empty);
        mRecyclerView.setOnLoadMoreListener(() -> {
            mPresenter.requestMeetingLists(false);
        });
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<MeetingList.DataBean>() {
            @Override
            public void onItemClick(MeetingList.DataBean data, View view, int position) {
                Intent intent = new Intent(getActivity(), MeetingDetailsActivity.class);
                //修改缓存状态 为已阅读，数据库数据 在进入下一个页面后修改
//                iDataBeanlist.get(i).setMark(1);
                if (data.getIs_read() == 0) {
                    data.setIs_read(1);
                    adapter.notifyDataSetChanged();
                    Message msg = new Message();
                    msg.what = EventBusTags.UPDATE_HOME_MEETING_COUNT_DEL;
                    msg.obj = EventBusTags.MEETING;
                    EventBus.getDefault().post(msg, EventBusTags.HOME);
                }
                intent.putExtra(MeetingDetailsActivity.PARAMS, data);
                intent.putExtra(MeetingDetailsActivity.PARAMS2, position);
                launchActivity(intent);
            }

            @Override
            public void onItemLongClick(MeetingList.DataBean data, View view, int position) {

            }
        });
    }

    @Subscriber(tag = EventBusTags.MEETING, mode = ThreadMode.MAIN)
    public void onEvent(Message message) {
        int position = message.arg1;
        switch (message.what) {
            case EventBusTags.UPDATE_MEETING_FEEDBAK_JOIN:
                ((MeetingList.DataBean) ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).getData().get(position)).setIs_confirm(message.arg2);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                break;
            case EventBusTags.UPDATE_MEETING_FEEDBAK_LEAVE:
                ((MeetingList.DataBean) ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).getData().get(position)).setIs_confirm(message.arg2);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                break;
            case EventBusTags.UPDATE_MEETING_LIST_SUMMARY_FINISH:
                ((MeetingList.DataBean) ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).getData().get(position)).setHas_summary(1);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                break;
        }
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
        mRecyclerView.notifyMoreLoaded();
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
        mPresenter.requestMeetingLists(true);
    }

}
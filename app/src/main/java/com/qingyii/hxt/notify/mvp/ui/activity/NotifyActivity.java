package com.qingyii.hxt.notify.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxt.base.app.EventBusTags;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.widget.AutoLoadMoreRecyclerView;
import com.qingyii.hxt.notify.di.component.DaggerNotifyComponent;
import com.qingyii.hxt.notify.di.module.NotifyModule;
import com.qingyii.hxt.notify.mvp.model.entity.NotifyList;
import com.qingyii.hxt.notify.mvp.presenter.NotifyPresenter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.qingyii.hxt.notify.mvp.ui.activity.NotifyDetailsActivity.NOTIFY;
import static com.qingyii.hxt.notify.mvp.ui.activity.NotifyDetailsActivity.POSITION;


public class NotifyActivity extends BaseActivity<NotifyPresenter> implements CommonContract.NotifyListView, SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    @BindView(R.id.notify_recyclerView)
    AutoLoadMoreRecyclerView mRecyclerView;
    @Nullable
    @BindView(R.id.notify_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Nullable
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.empty_view)
    View empty;
    private boolean allLoad;
    //标记来源地
    private String origin = "";


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerNotifyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .notifyModule(new NotifyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        if (getIntent().hasExtra(EventBusTags.NOTIFY_SYSTEM)) {
            origin = getIntent().getStringExtra(EventBusTags.NOTIFY_SYSTEM);
        }
        return R.layout.activity_notify; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @OnClick(R.id.toolbar_back)
    public void onClick() {
        killMyself();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (origin.equals(EventBusTags.NOTIFY_SYSTEM))
            mTitle.setText(R.string.notify_system_title);
        else
            mTitle.setText(R.string.notify_title);
        mPresenter.requestNotifyLists(true, origin);
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
        mRecyclerView.setAutoLayoutManager(new LinearLayoutManager(this)).setAutoHasFixedSize(true)
                .setAutoItemAnimator(new DefaultItemAnimator()).setAutoAdapter(adapter);

        mRecyclerView.setOnLoadMoreListener(() -> {
            // 状态停止，并且滑动到最后一位
            mPresenter.requestNotifyLists(false, origin);
        });
        mRecyclerView.setEmptyView(empty);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<NotifyList.DataBean>() {
            @Override
            public void onItemClick(NotifyList.DataBean data, View view, int position) {
                NotifyList.DataBean bean = (NotifyList.DataBean) adapter.getData().get(position);
                Intent intent = new Intent(NotifyActivity.this, NotifyDetailsActivity.class);
                //修改缓存状态 为已阅读，数据库数据 在进入下一个页面后修改
//                iDataBeanlist.get(i).setMark(1);
                if (bean.getIs_receipt() == 0) {
                    bean.setIs_receipt(1);
                    adapter.notifyDataSetChanged();
                    Message msg = new Message();
                    msg.what = EventBusTags.UPDATE_HOME_NOTIFY_COUNT_DEL;
                    msg.obj = EventBusTags.MEETING;
                    EventBus.getDefault().post(msg, EventBusTags.HOME);
                    if (bean.getReceipt_status_mark().equals(NotifyList.DataBean.UNREAD)) {
                        bean.setReceipt_status_mark(NotifyList.DataBean.READ);
                    }
                }
                intent.putExtra(NOTIFY, bean);
                intent.putExtra(POSITION, position);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(NotifyList.DataBean data, View view, int position) {

            }
        });
        initListener();
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
//        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(this));
    }

    /**
     * eventbus 更新状态
     *
     * @param message
     */
    @Subscriber(tag = EventBusTags.NOTIFY, mode = ThreadMode.MAIN)
    public void onEvent(Message message) {
        switch (message.what) {
            case EventBusTags.UPDATE_NOTIFY_SIGN_IN:
                //更新已签收状态
                int position = message.arg1;
                ((NotifyList.DataBean) ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).getData().get(position)).setReceipt_status_mark(NotifyList.DataBean.SIGN);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                break;
            case EventBusTags.UPDATE_NOTIFY_RETURN_LIST:
                //更新已签收状态
                ((NotifyList.DataBean) ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).getData().get(message.arg1)).setReceipt_status_mark(NotifyList.DataBean.RETURN);
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

    @Override
    public void onRefresh() {
        ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).getData().clear();
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.notifyMoreLoaded();
        mPresenter.requestNotifyLists(true, origin);
    }

    @Override
    protected void onDestroy() {
        BaseRecyclerAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
    }
}
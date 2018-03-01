package com.qingyii.hxt.meeting.mvp.presenter;

import android.app.Application;
import android.view.ViewGroup;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxt.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.utils.RxUtils;
import com.qingyii.hxt.meeting.di.module.entity.MeetingList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MeetingPublishPresenter extends BasePresenter<CommonContract.MeetingPublishListModel, CommonContract.MeetingPublishListView> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<MeetingList.DataBean> meetingLists = new ArrayList<>();
    private int lastMeetingListId;
    private boolean isFirst = true;
    private int preEndIndex;
    private BaseRecyclerAdapter<MeetingList.DataBean> mAdapter;
    private String direction = "lt";

    @Inject
    public MeetingPublishPresenter(CommonContract.MeetingPublishListModel model, CommonContract.MeetingPublishListView rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void requestMeetingPublishLists(final boolean pullToRefresh) {
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<MeetingList.DataBean>(meetingLists) {
                @Override
                public int getItemLayoutId(int viewType) {
                    return R.layout.meeting_publish_recyclerview_item;
                }

                @Override
                public void bindData(BaseRecyclerViewHolder holder, int position, MeetingList.DataBean item) {
                    //动态设置最后一个item marginBottom = 20
                    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
                    if (position == getItemCount() - 1) {
                        lp.setMargins(30, 20, 30, 20);
                        holder.itemView.setLayoutParams(lp);
                    } else {
                        lp.setMargins(30, 20, 30, 0);
                        holder.itemView.setLayoutParams(lp);
                    }
                }

            };
        }
        mRootView.setAdapter(mAdapter);//设置Adapter18975

        if (pullToRefresh) lastMeetingListId = 0;//上拉刷新默认只请求第一页

        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b

        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存

        if (pullToRefresh && isFirst) {//默认在第一次上拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }

        mModel.getMeetingPublishList(lastMeetingListId, direction, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showLoading();//显示上拉刷新的进度条
                    else
                        mRootView.startLoadMore();//显示下拉加载更多的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏上拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏下拉加载更多的进度条
                })
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<MeetingList>(mErrorHandler) {
                    @Override
                    public void onNext(MeetingList mMeetingList) {
                        if (mMeetingList.getData().size() < 10) {
                            mRootView.notifyAllLoad();
                        } else {
                            if (mMeetingList.getData().size() > 0)
                                lastMeetingListId = mMeetingList.getData().get(mMeetingList.getData().size() - 1).getId();
                        }
                        if (pullToRefresh) meetingLists.clear();
                        meetingLists.addAll(mMeetingList.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

}
package com.qingyii.hxt.notice.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.utils.RxUtils;
import com.qingyii.hxt.notice.mvp.model.entity.NoticeDetail;
import com.qingyii.hxt.notice.mvp.model.entity.NoticeList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class NoticeDetailsPresenter extends BasePresenter<CommonContract.NoticeListModel, CommonContract.NoticeView> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<NoticeList.DataBean> mNotices = new ArrayList<>();
    private int pagesize = 10;
    private boolean isFirst = true;
    private BaseRecyclerAdapter<NoticeList.DataBean> mAdapter;
    private int lastId = 0;
    private String direction = "lt";
    //判断当前是否已经在请求数据

    @Inject
    public NoticeDetailsPresenter(CommonContract.NoticeListModel notifyListModel, CommonContract.NoticeView rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(notifyListModel, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void requestNoticeListsForDetails(String id) {
        mModel.getLastNotice(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    mRootView.hideLoading();//隐藏上拉刷新的进度条
                })
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<NoticeDetail>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull NoticeDetail noticeDetail) {
                        mRootView.updateUI(noticeDetail);
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
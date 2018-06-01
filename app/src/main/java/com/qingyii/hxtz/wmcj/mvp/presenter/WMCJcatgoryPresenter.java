package com.qingyii.hxtz.wmcj.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportMenu;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class WMCJcatgoryPresenter extends BasePresenter<WMCJContract.WMCJcategoryModel,WMCJContract.WMCJcategoryView> {
    private RxErrorHandler mErrorHandler;
    private ImageLoader mImageLoader;
    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public WMCJcatgoryPresenter(WMCJContract.WMCJcategoryModel model, WMCJContract.WMCJcategoryView rootView, RxErrorHandler mErrorHandler, ImageLoader mImageLoader, Application mApplication, AppManager mAppManager) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mImageLoader = mImageLoader;
        this.mApplication = mApplication;
        this.mAppManager = mAppManager;
    }

   //获得完成动态筛选标签
    public void getReportMenu(){
        mModel.getReportMenu()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(2,2)) //重试次数
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mRootView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<ReportMenu>(mErrorHandler) {
                    @Override
                    public void onNext(ReportMenu reportMenu) {
                         mRootView.getReportMenuSuccess(reportMenu);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.hideLoading();
                               }}

                );
    }

   //切换考核标签
   public void getExmineMenu(){
           mModel.getExamineMenu()
                   .subscribeOn(Schedulers.io())
                   .retryWhen(new RetryWithDelay(2,2)) //重试次数
                   .doOnSubscribe(new Consumer<Disposable>() {
                       @Override
                       public void accept(@NonNull Disposable disposable) throws Exception {
                           mRootView.showLoading();
                       }
                   })
                   .subscribeOn(AndroidSchedulers.mainThread())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new ErrorHandleSubscriber<ExamineMenu>(mErrorHandler) {
                       @Override
                       public void onNext(ExamineMenu examineMenu) {
                              mRootView.getExmineMenuSuccess(examineMenu);
                       }

                       @Override
                       public void onError(Throwable e) {
                           super.onError(e);
                          mRootView.hideLoading();
                       }
                   });

   }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler = null;
        mImageLoader = null;
        mAppManager = null;
        mApplication = null;
    }
}

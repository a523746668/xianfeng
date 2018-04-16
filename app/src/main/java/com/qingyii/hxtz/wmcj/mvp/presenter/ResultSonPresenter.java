package com.qingyii.hxtz.wmcj.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxtz.base.utils.RxUtils;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Resultbean;

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

/**
 * Created by zhf on 2018/3/28.
 */
@ActivityScope
public class ResultSonPresenter extends BasePresenter<WMCJContract.ResultModel,WMCJContract.ResultSonView> {
    private RxErrorHandler mErrorHandler;
    private ImageLoader mImageLoader;
    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public ResultSonPresenter(WMCJContract.ResultModel model, WMCJContract.ResultSonView rootView, RxErrorHandler mErrorHandler, ImageLoader mImageLoader, Application mApplication, AppManager mAppManager) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mImageLoader = mImageLoader;
        this.mApplication = mApplication;
        this.mAppManager = mAppManager;
    }

     public void getResultData(int id,int indusid){
          mModel.getResultBean(id,indusid)
                  .subscribeOn(Schedulers.io())
                  .doOnSubscribe(new Consumer<Disposable>() {
                      @Override
                      public void accept(@NonNull Disposable disposable) throws Exception {
                          mRootView.showLoading();
                      }
                  })
                  .retryWhen(new RetryWithDelay(2,2)) //重试次数
                  .compose(RxUtils.bindToLifecycle(mRootView))
                  .doAfterTerminate(new Action() {
                      @Override
                      public void run() throws Exception {
                          mRootView.hideLoading();
                      }
                  })
                  .subscribeOn(AndroidSchedulers.mainThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new ErrorHandleSubscriber<Resultbean>(mErrorHandler) {
                      @Override
                      public void onNext(@NonNull Resultbean resultbean) {
                      mRootView.getResultDataSuccess(resultbean);
                      }

                                 @Override
                                 public void onError(@NonNull Throwable e) {

                                 }
                             }


                  );
     }
}

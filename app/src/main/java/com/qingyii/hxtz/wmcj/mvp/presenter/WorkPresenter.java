package com.qingyii.hxtz.wmcj.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.qingyii.hxtz.base.utils.RxUtils;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkbean;
import com.qingyii.hxtz.zhf.Util.Global;
import com.qingyii.hxtz.zhf.Util.HintUtil;

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
 * Created by zhf on 2018/3/13.
 */
@ActivityScope
public class WorkPresenter extends BasePresenter<WMCJContract.WorkParkModel,WMCJContract.WorkParkView> {
    private RxErrorHandler handler;
    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public WorkPresenter(WMCJContract.WorkParkModel model, WMCJContract.WorkParkView rootView, RxErrorHandler handler, Application mApplication, AppManager mAppManager) {
        super(model, rootView);
        this.handler = handler;
        this.mApplication = mApplication;
        this.mAppManager = mAppManager;
    }


    public void getWorkMenu(){
           mModel.getWorkMenu()
           .subscribeOn(Schedulers.io())
                   .retryWhen(new RetryWithDelay(2,2)) //重试次数
          .doOnSubscribe(new Consumer<Disposable>() {
              @Override
              public void accept(@NonNull Disposable disposable) throws Exception {
                  HintUtil.showdialog(mAppManager.getCurrentActivity());
              }
          })
           .compose(RxUtils.bindToLifecycle(mRootView))
                   .subscribeOn(AndroidSchedulers.mainThread())
                   .doAfterTerminate(new Action() {
                       @Override
                       public void run() throws Exception {
                          HintUtil.stopdialog();
                       }
                   })
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new ErrorHandleSubscriber<WorkParkbean>(handler) {
               @Override
               public void onNext(@NonNull WorkParkbean workParkbean) {
                   Global.workParkbean=workParkbean;
                   mRootView.getdatasuccess(workParkbean);
               }

               @Override
               public void onError(@NonNull Throwable e) {
                   super.onError(e);
               }
           });

      }



    @Override
    public void onDestroy() {
        super.onDestroy();
       this.handler=null;
       this.mApplication=null;
    }

}

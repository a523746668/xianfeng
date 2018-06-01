package com.qingyii.hxtz.wmcj.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.qingyii.hxtz.base.utils.RxUtils;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkbean;
import com.qingyii.hxtz.zhf.Util.Global;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
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

    @Inject
    public WorkPresenter(WMCJContract.WorkParkModel model, WMCJContract.WorkParkView rootView, RxErrorHandler handler) {
        super(model, rootView);
        this.handler = handler;
    }

      public void getWorkMenu(){
           mModel.getWorkMenu()
           .subscribeOn(Schedulers.io())
                   .retryWhen(new RetryWithDelay(2,2)) //重试次数
           .compose(RxUtils.bindToLifecycle(mRootView))
                   .subscribeOn(AndroidSchedulers.mainThread())
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
    }

}

package com.qingyii.hxtz.wmcj.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxtz.base.utils.RxUtils;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskTitlebean;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.ResultSonFragment;

import java.util.ArrayList;
import java.util.List;

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
 * Created by zhf on 2018/3/26.
 */
@ActivityScope
public class ResultPresenter extends BasePresenter<WMCJContract.ResultModel,WMCJContract.ResultView> {
    private RxErrorHandler mErrorHandler;
    private ImageLoader mImageLoader;
    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public ResultPresenter(WMCJContract.ResultModel model, WMCJContract.ResultView rootView, RxErrorHandler mErrorHandler, ImageLoader mImageLoader, Application mApplication, AppManager mAppManager) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mImageLoader = mImageLoader;
        this.mApplication = mApplication;
        this.mAppManager = mAppManager;
    }

  public void  getResultData(){
      mModel.getTaskTitle()
              .subscribeOn(Schedulers.io())
              .doOnSubscribe(new Consumer<Disposable>() {
                  @Override
                  public void accept(@NonNull Disposable disposable) throws Exception {
                      mRootView.showLoading();
                  }
              })
           //   .retryWhen(new RetryWithDelay(2,2)) //重试次数
              .compose(RxUtils.bindToLifecycle(mRootView))
              .doAfterTerminate(new Action() {
                  @Override
                  public void run() throws Exception {
                      mRootView.hideLoading();
                  }
              })
              .subscribeOn(AndroidSchedulers.mainThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new ErrorHandleSubscriber<TaskTitlebean>(mErrorHandler) {
                  @Override
                  public void onNext(@NonNull TaskTitlebean taskTitlebean) {
                    if(taskTitlebean.getError_code()==1){
                        mRootView.getdatano();
                        Log.i("tmderror_code","123123123123123");
                        return;
                    }
                    if(taskTitlebean.getData().getLibsystem()!=null&&taskTitlebean.getData().getLibsystem().size()>0&&taskTitlebean.getData().isIsadmin()) {
                          mRootView.gettitlesuccess((ArrayList<TaskTitlebean.DataBean.LibsystemBean>) taskTitlebean.getData().getLibsystem());
                          getdvpdata(taskTitlebean.getData().getLibsystem());
                      } else {
                         mRootView.getdatano();
                        Log.i("tmderror_code",taskTitlebean.getData().isIsadmin()+"1231313");
                      }
                  }
              });
  }

    public void  getResultData( int industryid){
        mModel.getTaskTitle()
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
                .subscribe(new ErrorHandleSubscriber<TaskTitlebean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull TaskTitlebean taskTitlebean) {
                        if(taskTitlebean.getData().getLibsystem().size()>0) {
                            mRootView.gettitlesuccess((ArrayList<TaskTitlebean.DataBean.LibsystemBean>) taskTitlebean.getData().getLibsystem());
                            getdvpdata(taskTitlebean.getData().getLibsystem(),industryid);
                        } else {
                            mRootView.getdatano();
                        }
                    }
                });
    }

    private void getdvpdata(List<TaskTitlebean.DataBean.LibsystemBean> list, int industryid) {
        ArrayList<ResultSonFragment> list1=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            ResultSonFragment fragment=new ResultSonFragment();
            fragment.setLibrarySystem(list.get(i).getId());
            fragment.setIndustryid(industryid);
            list1.add(fragment);
        }
        Log.i("tmdindustryid",industryid+"--------");
        mRootView.getdatasuccess(list1);
    }

    private void getdvpdata(List<TaskTitlebean.DataBean.LibsystemBean> list) {
        ArrayList<ResultSonFragment> list1=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            ResultSonFragment fragment=new ResultSonFragment();
            fragment.setLibrarySystem(list.get(i).getId());

            list1.add(fragment);
        }
       mRootView.getdatasuccess(list1);
    }
}

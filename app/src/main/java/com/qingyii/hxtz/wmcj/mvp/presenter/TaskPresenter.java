package com.qingyii.hxtz.wmcj.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxtz.base.utils.RxUtils;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskTitlebean;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.TaskListSonFragment;

import java.util.ArrayList;

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
 * Created by zhf on 2018/3/21.
 */
@ActivityScope
public class TaskPresenter extends BasePresenter<WMCJContract.TaskModel,WMCJContract.TaskView> {
    private RxErrorHandler mErrorHandler;
    private ImageLoader mImageLoader;
    private Application mApplication;
    private AppManager mAppManager;

    private ArrayList<TaskTitlebean.DataBean.LibsystemBean> list=new ArrayList<>();

    @Inject
    public TaskPresenter(WMCJContract.TaskModel model, WMCJContract.TaskView rootView, RxErrorHandler mErrorHandler, ImageLoader mImageLoader, Application mApplication, AppManager mAppManager) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mImageLoader = mImageLoader;
        this.mApplication = mApplication;
        this.mAppManager = mAppManager;
    }

     public void getTitle(){
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
                          if(taskTitlebean.getData().getLibsystem()!=null&&taskTitlebean.getData().getLibsystem().size()>0){
                               list.clear();
                              list.addAll(taskTitlebean.getData().getLibsystem());

                              mRootView.gettitile(list);
                              initfragment();
                          }else {
                              mRootView.getdatano();
                          }
                          }
                      });
     }

    private void initfragment() {
        ArrayList<TaskListSonFragment> list1=new ArrayList<>();
        for(int i=0;i<list.size();i++){
           TaskListSonFragment fragment=new TaskListSonFragment();
            fragment.setTitleid(list.get(i).getId());
            fragment.setTitlename(list.get(i).getTitle());
            list1.add(fragment);
        }
        mRootView.getdatasuccess(list1);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler=null;
        mImageLoader=null;
        mApplication=null;
        mAppManager=null;
        list.clear();
        list=null;

    }




}

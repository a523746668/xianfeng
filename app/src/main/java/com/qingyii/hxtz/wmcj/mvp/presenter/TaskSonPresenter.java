package com.qingyii.hxtz.wmcj.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.qingyii.hxtz.base.utils.RxUtils;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskLineSonbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskListViewBean;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.TaskLineAdaper;
import com.zhf.Util.Global;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by zhf on 2018/3/22.
 */
@ActivityScope
public class TaskSonPresenter extends BasePresenter<WMCJContract.TaskModel,WMCJContract.TaskSonView> {
    private RxErrorHandler mErrorHandler;
    private ImageLoader mImageLoader;
    private Application mApplication;
    private AppManager mAppManager;

    private TaskLineAdaper adaper;

    private ArrayList<TaskListViewBean> list=new ArrayList<>();
    private ArrayList<TaskLineSonbean> list2=new ArrayList<>();

    @Inject
    public TaskSonPresenter(WMCJContract.TaskModel model, WMCJContract.TaskSonView rootView, RxErrorHandler mErrorHandler, ImageLoader mImageLoader, Application mApplication, AppManager mAppManager) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mImageLoader = mImageLoader;
        this.mApplication = mApplication;
        this.mAppManager = mAppManager;
    }



      public void getTaskSonData(int id,String titlename){
          if(adaper==null){
              adaper=new TaskLineAdaper(mApplication,list2);
              adaper.setTitilename(titlename);
          }
          mRootView.setRecyclerviewAdapter(adaper);
        mModel.getTaskListData(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                          mRootView.showLoading();
                    }
                })
                .retryWhen(new RetryWithDelay(2,2)) //重试次数
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<okhttp3.ResponseBody>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull okhttp3.ResponseBody response) {
                        try {
                            org.json.JSONObject object=new org.json.JSONObject(response.string());
                            JSONArray object1=object.getJSONArray("data");

                            for(int i=0;i<object1.length();i++){
                                JSONObject object2=object1.getJSONObject(i);
                                String json=object2.toString();
                                TaskListViewBean bean=new Gson().fromJson(json,TaskListViewBean.class);
                                list.add(bean);
                            }
                            response.close();
                            chulishuju();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finally {
                            mRootView.hideLoading();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        mRootView.hideLoading();
                    }
                });
      }




    //得到的数据再处理
    private double chulishuju() {
        double sum=0;
        double sun=0;
        if(list.size()>0){
            for(TaskListViewBean bean:list){
                String level= Global.leves[bean.getLevel()];
                String  name=bean.getName();
                TaskLineSonbean tasklinebean=new TaskLineSonbean();
                tasklinebean.setLevel(level);
                tasklinebean.setTaskname(name);
                list2.add(tasklinebean);
                if(bean.getChild()!=null&&bean.getChild().size()>0){
                    sum=dochild(bean.getChild());
                    sun=sun+sum;
                }
                tasklinebean.setScore(String.valueOf(sum));

            }

        }
        adaper.notifyDataSetChanged();
        adaper.setSum(String.valueOf(sun));
        return  sun;

    }
    // 处理子数据
    private double dochild(List<TaskListViewBean.ChildBean> childs) {
        double sum=0;
        double tasksum=0;
        for(TaskListViewBean.ChildBean child:childs){
            String level=Global.leves[child.getLevel()];
            String name=child.getName();
            TaskLineSonbean tasklinebean=new TaskLineSonbean();
            tasklinebean.setLevel(level);
            tasklinebean.setTaskname(name);

            list2.add(tasklinebean);
            if(child.getChild()!=null&&child.getChild().size()>0){
                dochild(child.getChild());
            } else if(child.getMytask()!=null&&child.getMytask().size()>0){
                tasksum= dotask(child.getMytask()) ;
                tasklinebean.setScore(String.valueOf(tasksum));
                sum=sum+tasksum;
            }

        }
        return  sum;

    }
    //处理任务数据
    private double dotask(List<TaskListViewBean.ChildBean.MytaskBean> mytask) {
        double sun=0;
        for(TaskListViewBean.ChildBean.MytaskBean bean :mytask){
            String  score=bean.getScore();
            String name=bean.getTarget();
            int  id=bean.getId();
            TaskLineSonbean tasklinebean=new TaskLineSonbean();
            tasklinebean.setScore(score);
            tasklinebean.setTaskname(name);
            tasklinebean.setIstask(true);
            tasklinebean.setId(id);
            sun=sun+Double.valueOf(bean.getScore());
            list2.add(tasklinebean);
        }
        return sun;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler=null;
        mImageLoader=null;
        mApplication=null;
        mAppManager=null;
        list.clear();
        list2.clear();
        list=null;
        list2=null;
    }
}

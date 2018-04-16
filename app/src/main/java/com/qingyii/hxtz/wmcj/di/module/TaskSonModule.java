package com.qingyii.hxtz.wmcj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.TaskModel;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhf on 2018/3/22.
 */
@Module
public class TaskSonModule {
        private WMCJContract.TaskSonView taskSonView;

    @Inject
    public TaskSonModule(WMCJContract.TaskSonView taskSonView) {
        this.taskSonView = taskSonView;
    }


   @ActivityScope
    @Provides
    public WMCJContract.TaskSonView provideView(){
      return  this.taskSonView;
  }


 @ActivityScope
    @Provides
    public WMCJContract.TaskModel provideModel(TaskModel model){
        return  model;
 }

}

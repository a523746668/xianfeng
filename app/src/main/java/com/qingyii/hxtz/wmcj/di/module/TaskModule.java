package com.qingyii.hxtz.wmcj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.TaskModel;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhf on 2018/3/21.
 */
@Module
public class TaskModule {
  private WMCJContract.TaskView taskView;

    @Inject
    public TaskModule(WMCJContract.TaskView taskView) {
        this.taskView = taskView;
    }

    @ActivityScope
    @Provides
    public WMCJContract.TaskView provideView(){
        return  this.taskView;
    }

    @ActivityScope
    @Provides
    public WMCJContract.TaskModel provideModel(TaskModel model){
          return  model;
    }


}

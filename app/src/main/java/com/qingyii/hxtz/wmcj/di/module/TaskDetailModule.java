package com.qingyii.hxtz.wmcj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.TaskModel;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhf on 2018/3/23.
 */
@Module
public class TaskDetailModule {
      private WMCJContract.TaskDetaileView detaileView;

    @Inject
    public TaskDetailModule(WMCJContract.TaskDetaileView detaileView) {
        this.detaileView = detaileView;
    }

    @ActivityScope
    @Provides
   public WMCJContract.TaskDetaileView provideView(){
       return  this.detaileView;
   }

   @ActivityScope
    @Provides
    public WMCJContract.TaskModel provideModel(TaskModel model){
       return  model;
   }

}

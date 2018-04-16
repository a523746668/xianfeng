package com.qingyii.hxtz.wmcj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.WorkModel;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhf on 2018/3/13.
 */
@Module
public class WorkModule {
     private WMCJContract.WorkParkView  workParkView;


    @Inject
    public WorkModule(WMCJContract.WorkParkView workParkView) {
        this.workParkView = workParkView;
    }




    @ActivityScope
    @Provides
    WMCJContract.WorkParkView provideView(){
      return  this.workParkView;
    }



    @ActivityScope
    @Provides
    WMCJContract.WorkParkModel provideModel(WorkModel model){
       return  model;
   }

}

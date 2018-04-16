package com.qingyii.hxtz.wmcj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.WorkModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhf on 2018/3/19.
 */
@Module
public class WorkParkItemModule  {
    private WMCJContract.WorkParkView  workParkItemView;


    public WorkParkItemModule(WMCJContract.WorkParkView workParkItemView) {
        this.workParkItemView = workParkItemView;
    }

    @ActivityScope
    @Provides
    WMCJContract.WorkParkModel provideModel(WorkModel model){
        return  model;
    }

    @ActivityScope
    @Provides
    WMCJContract.WorkParkView provideItemView(){
        return  this.workParkItemView;
    }


}

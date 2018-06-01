package com.qingyii.hxtz.wmcj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.ExamineModel;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class ExamineModule {
    private WMCJContract.ExamineView examineView;

    @Inject
    public ExamineModule(WMCJContract.ExamineView examineView) {
        this.examineView = examineView;
    }

    @ActivityScope
    @Provides
    WMCJContract.ExamineView provideView(){
        return  this.examineView;
    }

   @ActivityScope
   @Provides
   WMCJContract.ExamineModel provideModel(ExamineModel model){
        return  model;
   }

}

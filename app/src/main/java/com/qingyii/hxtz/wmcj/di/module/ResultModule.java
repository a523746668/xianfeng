package com.qingyii.hxtz.wmcj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.ResultModel;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhf on 2018/3/26.
 */

@Module
public class ResultModule {
     private WMCJContract.ResultView resultView;


    @Inject
    public ResultModule(WMCJContract.ResultView resultView) {
        this.resultView = resultView;
    }

     @ActivityScope
     @Provides
     public WMCJContract.ResultView provideView(){
         return  this.resultView;
     }

    @ActivityScope
    @Provides
    public WMCJContract.ResultModel provideModel(ResultModel model){
          return  model;
    }

}

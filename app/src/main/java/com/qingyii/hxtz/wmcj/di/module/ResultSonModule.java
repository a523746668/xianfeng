package com.qingyii.hxtz.wmcj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.ResultModel;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhf on 2018/3/28.
 */

@Module
public class ResultSonModule {
  private WMCJContract.ResultSonView resultSonView;

    @Inject
    public ResultSonModule(WMCJContract.ResultSonView resultSonView) {
        this.resultSonView = resultSonView;
    }

    @ActivityScope
    @Provides
    public WMCJContract.ResultSonView provideView(){
        return  this.resultSonView;
    }


    @ActivityScope
    @Provides
    public WMCJContract.ResultModel provideModel(ResultModel model){
         return  model;
    }


}

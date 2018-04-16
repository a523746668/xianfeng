package com.qingyii.hxtz.wmcj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.IssuseTaskModel;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhf on 2018/4/10.
 */

@Module
public class IssuseModule {
      private WMCJContract.IssuseTaskView issuseTaskView;

    @Inject
     public IssuseModule(WMCJContract.IssuseTaskView issuseTaskView) {
        this.issuseTaskView = issuseTaskView;
    }

    @ActivityScope
    @Provides
    public  WMCJContract.IssuseTaskView provideView(){
        return  this.issuseTaskView;
    }

    @ActivityScope
    @Provides
    public WMCJContract.IssuseTaskModel provideModel(IssuseTaskModel model){
        return  model;
   }

}

package com.qingyii.hxtz.wmcj.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.WMCJcategoryModel;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class WMCJcategoryModule {
     private WMCJContract.WMCJcategoryView wmcJcategoryView;

     @Inject
    public WMCJcategoryModule(WMCJContract.WMCJcategoryView wmcJcategoryView) {
        this.wmcJcategoryView = wmcJcategoryView;
    }

    @ActivityScope
    @Provides
    WMCJContract.WMCJcategoryView provideView(){
         return this.wmcJcategoryView;
   }


   @ActivityScope
   @Provides
   WMCJContract.WMCJcategoryModel provideModel(WMCJcategoryModel model){
         return  model;
  }


}

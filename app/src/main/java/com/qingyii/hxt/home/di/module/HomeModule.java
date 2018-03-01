package com.qingyii.hxt.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.home.mvp.model.HomeModel;

import dagger.Module;
import dagger.Provides;


@Module
public class HomeModule {
    private CommonContract.HomeInfoView view;

    /**
     * 构建HomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HomeModule(CommonContract.HomeInfoView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommonContract.HomeInfoView provideHomeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommonContract.HomeInfoModel provideHomeModel(HomeModel model) {
        return model;
    }
}
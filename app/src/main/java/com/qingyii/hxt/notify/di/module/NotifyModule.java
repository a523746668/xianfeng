package com.qingyii.hxt.notify.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.notify.mvp.model.NotifyListModel;

import dagger.Module;
import dagger.Provides;


@Module
public class NotifyModule {
    private CommonContract.NotifyListView view;
    /**
     * 构建NoticeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NotifyModule(CommonContract.NotifyListView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommonContract.NotifyListView provideNotifyView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommonContract.NotifyListModel provideNotifyModel(NotifyListModel model) {
        return model;
    }
}
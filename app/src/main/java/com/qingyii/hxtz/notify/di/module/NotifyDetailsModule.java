package com.qingyii.hxtz.notify.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.notify.mvp.model.NotifyDetailsModel;

import dagger.Module;
import dagger.Provides;


@Module
public class NotifyDetailsModule {
    private CommonContract.NotifyDetailsView view;

    /**
     * 构建NoticeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NotifyDetailsModule(CommonContract.NotifyDetailsView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommonContract.NotifyDetailsView provideNotifyDetailsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommonContract.NotifyDetailsModel provideNotifyDetailsModel(NotifyDetailsModel model) {
        return model;
    }
}
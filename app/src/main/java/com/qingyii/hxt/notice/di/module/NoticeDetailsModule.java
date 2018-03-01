package com.qingyii.hxt.notice.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.notice.mvp.model.NoticeListModel;

import dagger.Module;
import dagger.Provides;


@Module
public class NoticeDetailsModule {
    private CommonContract.NoticeView view;
    /**
     * 构建NoticeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NoticeDetailsModule(CommonContract.NoticeView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommonContract.NoticeView provideNoticeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommonContract.NoticeListModel provideNoticeModel(NoticeListModel model) {
        return model;
    }
}
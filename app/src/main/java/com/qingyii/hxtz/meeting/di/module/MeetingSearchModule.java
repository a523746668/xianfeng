package com.qingyii.hxtz.meeting.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.meeting.mvp.model.MeetingSearchListModel;

import dagger.Module;
import dagger.Provides;


@Module
public class MeetingSearchModule {
    private CommonContract.MeetingSearchListView view;

    /**
     * 构建MeetingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MeetingSearchModule(CommonContract.MeetingSearchListView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommonContract.MeetingSearchListView provideMeetingSearchView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommonContract.MeetingSearchListModel provideMeetingSearchModel(MeetingSearchListModel model) {
        return model;
    }
}
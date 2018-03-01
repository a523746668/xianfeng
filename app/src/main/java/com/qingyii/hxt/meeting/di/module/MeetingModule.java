package com.qingyii.hxt.meeting.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.meeting.mvp.model.MeetingListModel;

import dagger.Module;
import dagger.Provides;


@Module
public class MeetingModule {
    private CommonContract.MeetingListView view;

    /**
     * 构建MeetingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MeetingModule(CommonContract.MeetingListView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommonContract.MeetingListView provideMeetingView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommonContract.MeetingListModel provideMeetingModel(MeetingListModel model) {
        return model;
    }
}
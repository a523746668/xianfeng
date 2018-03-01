package com.qingyii.hxt.meeting.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.meeting.mvp.model.MeetingSummaryModel;

import dagger.Module;
import dagger.Provides;


@Module
public class MeetingSummaryModule {
    private CommonContract.MeetingSummaryView view;

    /**
     * 构建MeetingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MeetingSummaryModule(CommonContract.MeetingSummaryView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommonContract.MeetingSummaryView provideMeetingSummaryView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommonContract.MeetingSummaryModel provideMeetingSummaryModel(MeetingSummaryModel model) {
        return model;
    }
}
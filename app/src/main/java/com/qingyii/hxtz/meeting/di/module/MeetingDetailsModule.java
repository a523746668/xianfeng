package com.qingyii.hxtz.meeting.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.meeting.mvp.model.MeetingDetailsModel;

import dagger.Module;
import dagger.Provides;


@Module
public class MeetingDetailsModule {
    private CommonContract.MeetingDetailsView view;

    /**
     * 构建MeetingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MeetingDetailsModule(CommonContract.MeetingDetailsView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommonContract.MeetingDetailsView provideMeetingDetailsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommonContract.MeetingDetailsModel provideMeetingModel(MeetingDetailsModel model) {
        return model;
    }
}
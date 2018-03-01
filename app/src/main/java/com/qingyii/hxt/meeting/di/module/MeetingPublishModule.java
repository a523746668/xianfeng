package com.qingyii.hxt.meeting.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.meeting.mvp.model.MeetingPublishListModel;

import dagger.Module;
import dagger.Provides;


@Module
public class MeetingPublishModule {
    private CommonContract.MeetingPublishListView view;

    /**
     * 构建MeetingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MeetingPublishModule(CommonContract.MeetingPublishListView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommonContract.MeetingPublishListView provideMeetingPublishView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommonContract.MeetingPublishListModel provideMeetingPublishModel(MeetingPublishListModel model) {
        return model;
    }
}
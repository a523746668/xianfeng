package com.qingyii.hxt.meeting.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.TrainDownloadActivity;
import com.qingyii.hxt.meeting.di.module.MeetingDetailsModule;
import com.qingyii.hxt.meeting.mvp.ui.activity.MeetingDetailsActivity;
import com.qingyii.hxt.meeting.mvp.ui.activity.MeetingSignInActivity;

import dagger.Component;

@ActivityScope
@Component(modules = MeetingDetailsModule.class, dependencies = AppComponent.class)
public interface MeetingDetailsComponent {
    void inject(MeetingDetailsActivity activity);
    void inject(MeetingSignInActivity activity);

    void inject(TrainDownloadActivity trainDownloadActivity);
}
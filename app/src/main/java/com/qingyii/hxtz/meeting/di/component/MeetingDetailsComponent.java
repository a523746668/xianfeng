package com.qingyii.hxtz.meeting.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.TrainDownloadActivity;
import com.qingyii.hxtz.meeting.di.module.MeetingDetailsModule;
import com.qingyii.hxtz.meeting.mvp.ui.activity.MeetingDetailsActivity;
import com.qingyii.hxtz.meeting.mvp.ui.activity.MeetingSignInActivity;

import dagger.Component;

@ActivityScope
@Component(modules = MeetingDetailsModule.class, dependencies = AppComponent.class)
public interface MeetingDetailsComponent {
    void inject(MeetingDetailsActivity activity);
    void inject(MeetingSignInActivity activity);

    void inject(TrainDownloadActivity trainDownloadActivity);
}
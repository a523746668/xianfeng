package com.qingyii.hxt.meeting.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.meeting.di.module.MeetingSummaryModule;
import com.qingyii.hxt.meeting.mvp.ui.activity.MeetingSummaryActivity;

import dagger.Component;

@ActivityScope
@Component(modules = MeetingSummaryModule.class, dependencies = AppComponent.class)
public interface MeetingSummaryComponent {
    void inject(MeetingSummaryActivity activity);
}
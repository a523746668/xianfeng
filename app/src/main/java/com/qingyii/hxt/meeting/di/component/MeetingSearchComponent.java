package com.qingyii.hxt.meeting.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.meeting.di.module.MeetingSearchModule;
import com.qingyii.hxt.meeting.mvp.ui.activity.MeetingSearchActivity;

import dagger.Component;

@ActivityScope
@Component(modules = MeetingSearchModule.class, dependencies = AppComponent.class)
public interface MeetingSearchComponent {
    void inject(MeetingSearchActivity activity);
}
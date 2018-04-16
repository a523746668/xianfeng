package com.qingyii.hxtz.meeting.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.meeting.di.module.MeetingSearchModule;
import com.qingyii.hxtz.meeting.mvp.ui.activity.MeetingSearchActivity;

import dagger.Component;

@ActivityScope
@Component(modules = MeetingSearchModule.class, dependencies = AppComponent.class)
public interface MeetingSearchComponent {
    void inject(MeetingSearchActivity activity);
}
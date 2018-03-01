package com.qingyii.hxt.meeting.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.meeting.di.module.MeetingModule;
import com.qingyii.hxt.meeting.mvp.ui.fragment.MeetingFragment;

import dagger.Component;

@ActivityScope
@Component(modules = MeetingModule.class, dependencies = AppComponent.class)
public interface MeetingComponent {
    void inject(MeetingFragment activity);
}
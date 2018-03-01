package com.qingyii.hxt.meeting.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.meeting.di.module.MeetingPublishModule;
import com.qingyii.hxt.meeting.mvp.ui.fragment.MeetingPublishItemFragment;

import dagger.Component;

@ActivityScope
@Component(modules = MeetingPublishModule.class, dependencies = AppComponent.class)
public interface MeetingPublishComponent {
    void inject(MeetingPublishItemFragment activity);
}
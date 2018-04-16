package com.qingyii.hxtz.meeting.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.meeting.di.module.MeetingPublishModule;
import com.qingyii.hxtz.meeting.mvp.ui.fragment.MeetingPublishItemFragment;

import dagger.Component;

@ActivityScope
@Component(modules = MeetingPublishModule.class, dependencies = AppComponent.class)
public interface MeetingPublishComponent {
    void inject(MeetingPublishItemFragment activity);
}
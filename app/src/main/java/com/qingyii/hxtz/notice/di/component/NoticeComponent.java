package com.qingyii.hxtz.notice.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.notice.di.module.NoticeModule;
import com.qingyii.hxtz.notice.mvp.ui.activity.NoticeActivity;

import dagger.Component;

@ActivityScope
@Component(modules = NoticeModule.class, dependencies = AppComponent.class)
public interface NoticeComponent {
    void inject(NoticeActivity activity);
}
package com.qingyii.hxt.notice.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.notice.di.module.NoticeDetailsModule;
import com.qingyii.hxt.notice.mvp.ui.activity.NoticeDetailsActivity;

import dagger.Component;

@ActivityScope
@Component(modules = NoticeDetailsModule.class, dependencies = AppComponent.class)
public interface NoticeDetailsComponent {
    void inject(NoticeDetailsActivity activity);
}
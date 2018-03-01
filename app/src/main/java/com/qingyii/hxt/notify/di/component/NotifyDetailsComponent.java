package com.qingyii.hxt.notify.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.notify.di.module.NotifyDetailsModule;
import com.qingyii.hxt.notify.mvp.ui.activity.NotifyDetailsActivity;

import dagger.Component;

@ActivityScope
@Component(modules = NotifyDetailsModule.class, dependencies = AppComponent.class)
public interface NotifyDetailsComponent {
    void inject(NotifyDetailsActivity activity);
}
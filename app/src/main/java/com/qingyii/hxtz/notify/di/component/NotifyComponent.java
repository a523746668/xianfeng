package com.qingyii.hxtz.notify.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.notify.di.module.NotifyModule;
import com.qingyii.hxtz.notify.mvp.ui.activity.NotifyActivity;

import dagger.Component;

@ActivityScope
@Component(modules = NotifyModule.class, dependencies = AppComponent.class)
public interface NotifyComponent {
    void inject(NotifyActivity activity);
}
package com.qingyii.hxtz.my;

import android.app.Activity;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.home.di.module.HomeModule;
import com.qingyii.hxtz.notify.di.module.NotifyDetailsModule;
import com.qingyii.hxtz.notify.mvp.model.NotifyDetailsModel;

import dagger.Component;

@ActivityScope
@Component(modules = NotifyDetailsModule.class, dependencies = AppComponent.class)
public interface StudyComponent {
    void inject(My_StudyActivity activity);
}

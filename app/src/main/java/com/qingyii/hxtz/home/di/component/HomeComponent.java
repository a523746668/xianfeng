package com.qingyii.hxtz.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.home.di.module.HomeModule;
import com.qingyii.hxtz.home.mvp.ui.MoreActivity;

import dagger.Component;


@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void injcet(MoreActivity activity);
}
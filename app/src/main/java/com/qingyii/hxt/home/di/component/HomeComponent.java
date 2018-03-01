package com.qingyii.hxt.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.home.di.module.HomeModule;
import com.qingyii.hxt.home.mvp.ui.HomeActivity;

import dagger.Component;


@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void injcet(HomeActivity activity);
}
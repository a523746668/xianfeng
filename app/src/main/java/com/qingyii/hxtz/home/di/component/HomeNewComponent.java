package com.qingyii.hxtz.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.home.di.module.HomeModule;
import com.qingyii.hxtz.home.mvp.ui.HomeNewActivity;
import com.qingyii.hxtz.notify.di.module.NotifyDetailsModule;

import dagger.Component;

/**
 * Created by zhf on 2018/4/12.
 */
@ActivityScope
@Component(modules = {HomeModule.class,NotifyDetailsModule.class}, dependencies = AppComponent.class)
public interface HomeNewComponent {
  void inject(HomeNewActivity activity);
}

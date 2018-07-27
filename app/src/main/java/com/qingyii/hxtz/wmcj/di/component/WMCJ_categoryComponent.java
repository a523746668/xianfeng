package com.qingyii.hxtz.wmcj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.di.module.WMCJcategoryModule;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WMCj_categoryActivity;

import dagger.Component;

@ActivityScope
@Component(modules = WMCJcategoryModule.class,dependencies = AppComponent.class)
public interface WMCJ_categoryComponent {
   void inject(WMCj_categoryActivity activity);
}

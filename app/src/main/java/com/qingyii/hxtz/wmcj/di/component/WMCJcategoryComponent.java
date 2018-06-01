package com.qingyii.hxtz.wmcj.di.component;


import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.di.module.WMCJcategoryModule;
import com.qingyii.hxtz.wmcj.di.module.WorkParkItemModule;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WMCJcategoryActivity;

import dagger.Component;

@ActivityScope
@Component(modules = WMCJcategoryModule.class,dependencies = AppComponent.class)
public interface WMCJcategoryComponent {
    void inject(WMCJcategoryActivity activity);
}

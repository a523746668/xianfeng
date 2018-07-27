package com.qingyii.hxtz.wmcj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.di.module.WorkModule;
import com.qingyii.hxtz.wmcj.di.module.WorkParkItemModule;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.ReportingActivity;

import dagger.Component;

/**
 * Created by zhf on 2018/3/14.
 */

@ActivityScope
@Component(modules = {WorkParkItemModule.class},dependencies = AppComponent.class)
public interface ReportingComponent {
           void inject(ReportingActivity activity);
}

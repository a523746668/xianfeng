package com.qingyii.hxtz.wmcj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.di.module.WorkModule;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.WorkParkFragment;

import dagger.Component;

/**
 * Created by zhf on 2018/3/13.
 */
@ActivityScope
@Component(modules = WorkModule.class,dependencies = AppComponent.class)
public interface WorkComponent {
   void inject(WorkParkFragment  fragment);
}

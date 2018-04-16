package com.qingyii.hxtz.wmcj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.di.module.TaskModule;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.TaskListFragment;

import dagger.Component;

/**
 * Created by zhf on 2018/3/21.
 */
@ActivityScope
@Component(modules = TaskModule.class,dependencies = AppComponent.class)
public interface TaskComponent {
      void inject(TaskListFragment fragment);
}

package com.qingyii.hxtz.wmcj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.di.module.TaskSonModule;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.TaskListSonFragment;

import dagger.Component;

/**
 * Created by zhf on 2018/3/22.
 */
@ActivityScope
@Component(modules = TaskSonModule.class,dependencies = AppComponent.class)
public interface TaskSonComponent  {
      void inject(TaskListSonFragment fragment);
}

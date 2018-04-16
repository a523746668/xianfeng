package com.qingyii.hxtz.wmcj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.di.module.TaskDetailModule;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.TaskDetailActivity;

import dagger.Component;

/**
 * Created by zhf on 2018/3/23.
 */

@ActivityScope
@Component(modules = TaskDetailModule.class,dependencies = AppComponent.class)
public interface TaskDetailComponent {
    void inject(TaskDetailActivity activity);
}

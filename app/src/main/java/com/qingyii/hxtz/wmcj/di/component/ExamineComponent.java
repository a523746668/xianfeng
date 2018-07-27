package com.qingyii.hxtz.wmcj.di.component;


import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.di.module.ExamineModule;
import com.qingyii.hxtz.wmcj.di.module.TaskModule;
import com.qingyii.hxtz.wmcj.di.module.WorkParkItemModule;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.ExamineFragment;

import dagger.Component;

@ActivityScope
@Component(modules = {ExamineModule.class, TaskModule.class},dependencies = AppComponent.class)
public interface ExamineComponent {
    void inject(ExamineFragment fragment);
}

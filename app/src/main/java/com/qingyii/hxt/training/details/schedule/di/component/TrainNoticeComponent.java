package com.qingyii.hxt.training.details.schedule.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.training.details.schedule.di.module.TrainNoticeModule;
import com.qingyii.hxt.training.details.schedule.mvp.ui.activity.TrainNoticeActivity;

import dagger.Component;

/**
 * Created by xubo on 2017/8/9.
 */
@ActivityScope
@Component(modules = TrainNoticeModule.class, dependencies = AppComponent.class)
public interface TrainNoticeComponent {
    void inject(TrainNoticeActivity activity);
}

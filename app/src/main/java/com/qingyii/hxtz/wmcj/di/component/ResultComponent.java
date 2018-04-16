package com.qingyii.hxtz.wmcj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.di.module.ResultModule;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.ResultPostionFragment;

import dagger.Component;

/**
 * Created by zhf on 2018/3/26.
 */


@ActivityScope
@Component(modules = ResultModule.class,dependencies = AppComponent.class)
public interface ResultComponent {
    void inject(ResultPostionFragment fragment);
}

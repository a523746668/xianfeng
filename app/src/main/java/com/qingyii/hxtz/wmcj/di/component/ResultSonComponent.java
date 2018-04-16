package com.qingyii.hxtz.wmcj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxtz.wmcj.di.module.ResultSonModule;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.ResultSonFragment;

import dagger.Component;

/**
 * Created by zhf on 2018/3/28.
 */

@ActivityScope
@Component(modules = ResultSonModule.class,dependencies = AppComponent.class)
public interface ResultSonComponent {
     void inject(ResultSonFragment fragment);
}

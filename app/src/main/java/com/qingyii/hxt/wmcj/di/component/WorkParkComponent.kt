package com.qingyii.hxt.wmcj.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.qingyii.hxt.wmcj.di.module.WorkParkModule
import com.qingyii.hxt.wmcj.mvp.ui.WorkParkItemFragment
import dagger.Component


@ActivityScope
@Component(modules = arrayOf(WorkParkModule::class), dependencies = arrayOf(AppComponent::class))
interface WorkParkComponent {
    fun injcet(fragment: WorkParkItemFragment)
}
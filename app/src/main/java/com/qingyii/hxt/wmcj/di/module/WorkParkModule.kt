package com.qingyii.hxt.wmcj.di.module

import com.jess.arms.di.scope.ActivityScope
import com.qingyii.hxt.base.mvp.contract.CommonContract
import com.qingyii.hxt.wmcj.mvp.model.WorkParkModel

import dagger.Module
import dagger.Provides


@Module
class WorkParkModule
/**
 * 构建HomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
 *
 * @param view
 */
(private val view: CommonContract.WorkParkListView) {

    @ActivityScope
    @Provides
    internal fun provideWorkParkView(): CommonContract.WorkParkListView {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideHomeModel(model: WorkParkModel): CommonContract.WorkParkListModel {
        return model
    }
}
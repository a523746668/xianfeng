package com.qingyii.hxt.training.details.schedule.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.training.details.schedule.mvp.model.TrainNoticeModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xubo on 2017/8/9.
 */

@Module
public class TrainNoticeModule {
    private CommonContract.TrainingNoticeContractView view;
    /**
     * 构建NoticeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TrainNoticeModule(CommonContract.TrainingNoticeContractView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommonContract.TrainingNoticeContractView provideTrainNoticeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommonContract.TrainingNoticeContractModel provideNoticeModel(TrainNoticeModel model) {
        return model;
    }
}

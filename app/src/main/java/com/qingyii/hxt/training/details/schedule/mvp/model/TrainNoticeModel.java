package com.qingyii.hxt.training.details.schedule.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxt.base.mvp.api.service.ApiService;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.training.details.schedule.di.module.entity.TrainNoticeList;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by xubo on 2017/8/9.
 */

public class TrainNoticeModel extends BaseModel implements CommonContract.TrainingNoticeContractModel {

    @Inject
    public TrainNoticeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<TrainNoticeList> getTrainNoticeList(int trainId) {

        return mRepositoryManager.obtainRetrofitService(ApiService.class).getTrainNoticeList(trainId);
    }
}
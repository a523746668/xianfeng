package com.qingyii.hxtz.notify.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxtz.base.mvp.api.service.ApiService;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.notify.mvp.model.entity.NotifyList;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class NotifyListModel extends BaseModel implements CommonContract.NotifyListModel {

    @Inject
    public NotifyListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<NotifyList> getNotifyList(int lastId, String direction, boolean type) {
        Observable<NotifyList> NotifyLists = type?
                mRepositoryManager.obtainRetrofitService(ApiService.class).getNotifySystemLists(lastId,direction):
                mRepositoryManager.obtainRetrofitService(ApiService.class).getNotifyLists(lastId,direction);
        return NotifyLists;
    }
}
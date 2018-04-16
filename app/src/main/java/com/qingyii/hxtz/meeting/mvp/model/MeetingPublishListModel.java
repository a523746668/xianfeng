package com.qingyii.hxtz.meeting.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxtz.base.mvp.api.service.ApiService;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.meeting.di.module.entity.MeetingList;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class MeetingPublishListModel extends BaseModel implements CommonContract.MeetingPublishListModel {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MeetingPublishListModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MeetingList> getMeetingPublishList(int lastIdQueried, String direction, boolean update) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getMeetingPublishLists(lastIdQueried,direction);
    }
}
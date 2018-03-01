package com.qingyii.hxt.meeting.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxt.base.mvp.api.service.ApiService;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.mvp.model.entity.CommonData;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.meeting.di.module.entity.MeetingSummary;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


@ActivityScope
public class MeetingSummaryModel extends BaseModel implements CommonContract.MeetingSummaryModel {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MeetingSummaryModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<CommonData<String>> requestMeetingSummary(MeetingSummary data) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).requestSummary(XrjHttpClient.URL_UP+"/api/meeting/summary",data);
    }

    @Override
    public Observable<CommonData<String>> uploadPic(MultipartBody.Part part, String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).uploadPic(XrjHttpClient.URL_UP+"/api/upload/meeting/summary/picture?user_id="+id,part);
    }
}
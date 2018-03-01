package com.qingyii.hxt.meeting.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxt.base.mvp.api.service.ApiService;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.mvp.model.entity.CommonData;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


@ActivityScope
public class MeetingDetailsModel extends BaseModel implements CommonContract.MeetingDetailsModel {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MeetingDetailsModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<CommonData<String>> requestFeedback(int id, String status,String rejectresult) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getFeedback(id,status,rejectresult);
    }
    @Override
    public Observable<ResponseBody> download(String url) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).download(url);
    }
}
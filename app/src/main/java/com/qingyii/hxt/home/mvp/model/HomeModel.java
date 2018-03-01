package com.qingyii.hxt.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxt.base.mvp.api.service.ApiService;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.home.mvp.model.entity.FakeData;
import com.qingyii.hxt.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class HomeModel extends BaseModel implements CommonContract.HomeInfoModel {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<HomeInfo> getHomeInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getHomeInfo();
    }

    @Override
    public Observable<FakeData> getfakedata() {
        String url=XrjHttpClient.URL_PR+"/task/rollInfo?token="+ MyApplication.hxt_setting_config.getString("token","");
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getfakedata(url);
    }
}
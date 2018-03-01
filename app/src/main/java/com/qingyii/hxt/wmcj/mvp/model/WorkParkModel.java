package com.qingyii.hxt.wmcj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxt.base.mvp.api.service.ApiService;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.wmcj.mvp.model.entity.WorkParkList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class WorkParkModel extends BaseModel implements CommonContract.WorkParkListModel {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public WorkParkModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<List<WorkParkList>> getWorkParkList(int page) {
        //int lastId, String direction, int typeId
        String url = "http://api.iclient.ifeng.com/ClientNews?id=SYLB10,SYDT10,SYRECOMMEND&gv=5.4.0&av=5.4.0&uid=866500027180423&deviceid=866500027180423&proid=ifengnews&os=android_23&df=androidphone&vt=5&screen=1080x1920&publishid=6001&nw=wifi";
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getWorkParkListData(url,page);
    }

}
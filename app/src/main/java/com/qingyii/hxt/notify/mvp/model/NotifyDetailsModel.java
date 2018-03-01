package com.qingyii.hxt.notify.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.mvp.api.service.ApiService;
import com.qingyii.hxt.base.mvp.model.entity.CommonData;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


@ActivityScope
public class NotifyDetailsModel extends BaseModel implements CommonContract.NotifyDetailsModel {

    @Inject
    public NotifyDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<CommonData<String>> getReadMark(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getReadMark(id);
    }

    @Override
    public Observable<CommonData<String>> getSignMark(int id,String message) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getSignMark(id,message);
    }
    @Override
    public Observable<ResponseBody> download(String url) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).download(url);
    }
}
package com.qingyii.hxtz.wmcj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxtz.wmcj.WMCJContract;

import javax.inject.Inject;

/**
 * Created by zhf on 2018/4/4.
 */
@ActivityScope
public class IssuseTaskModel extends BaseModel implements WMCJContract.IssuseTaskModel {
    private Gson mGson;
    private Application mApplication;


    @Inject
    public IssuseTaskModel(IRepositoryManager repositoryManager, Gson mGson, Application mApplication) {
        super(repositoryManager);
        this.mGson = mGson;
        this.mApplication = mApplication;
    }

    @Override
    public void onDestroy() {

    }
}

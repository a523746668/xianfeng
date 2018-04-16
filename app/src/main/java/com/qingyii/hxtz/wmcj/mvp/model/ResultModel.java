package com.qingyii.hxtz.wmcj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.wmcj.WMCJApi;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Resultbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskTitlebean;
import com.zhf.Util.Global;
import com.zhf.http.Urlutil;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by zhf on 2018/3/26.
 */

@ActivityScope
public class ResultModel extends BaseModel implements WMCJContract.ResultModel {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ResultModel(IRepositoryManager repositoryManager, Gson mGson, Application mApplication) {
        super(repositoryManager);
        this.mGson = mGson;
        this.mApplication = mApplication;
    }

    @Override
    public Observable<Resultbean> getResultBean(int librarySystem) {
        String purl= Urlutil.baseurl+"/task/"+ Global.userid+"/library/"+librarySystem
                +"/ranking?token="+ MyApplication.hxt_setting_config.getString("token","");
        return  mRepositoryManager.obtainRetrofitService(WMCJApi.class).getResultData(purl);
    }

    @Override
    public Observable<Resultbean> getResultBean(int librarySystem, int industryid) {
        if(industryid!=-999){
            String purl= Urlutil.baseurl+"/task/"+Global.userid+"/library/"+librarySystem
                    +"/ranking?token="+ MyApplication.hxt_setting_config.getString("token","");

            return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getResultData(purl,String.valueOf(industryid));
        } else {
            return getResultBean(librarySystem);
        }

    }

    @Override
    public Observable<TaskTitlebean> getTaskTitle() {
        String pUrl= Urlutil.baseurl+"/task/"+ Global.userid +"?token="+ MyApplication.hxt_setting_config.getString("token","");

        return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getTaskTitle(pUrl);
    }

    @Override
    public void onDestroy() {
            mGson=null;
            mApplication=null;
    }
}

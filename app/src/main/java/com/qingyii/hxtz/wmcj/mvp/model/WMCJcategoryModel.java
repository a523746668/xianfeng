package com.qingyii.hxtz.wmcj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.wmcj.WMCJApi;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineMenu;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportMenu;
import com.qingyii.hxtz.zhf.Util.Global;
import com.qingyii.hxtz.zhf.http.Urlutil;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.flowables.GroupedFlowable;


@ActivityScope
public class WMCJcategoryModel extends BaseModel implements WMCJContract.WMCJcategoryModel {

    private Gson mGson;
    private Application mApplication;

    @Inject
    public WMCJcategoryModel(IRepositoryManager repositoryManager, Gson mGson, Application mApplication) {
        super(repositoryManager);
        this.mGson = mGson;
        this.mApplication = mApplication;
    }


    @Override
    public Observable<ReportMenu> getReportMenu() {
        String murl= Urlutil.baseurl+"/kh/"+Global.userid+"/actMenu?token="+ MyApplication.hxt_setting_config.getString("token","");
        return   mRepositoryManager.obtainRetrofitService(WMCJApi.class).getReportMenu(murl);
    }

    @Override
    public Observable<ExamineMenu> getExamineMenu() {
        String murl=Urlutil.baseurl+"/kh/check/selectTag/"+Global.userid+"?token="+MyApplication.hxt_setting_config.getString("token","");
        return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getExamineMenu(murl);
    }


}

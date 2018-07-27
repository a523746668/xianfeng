package com.qingyii.hxtz.wmcj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.wmcj.WMCJApi;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ExamineBean;
import com.qingyii.hxtz.zhf.Util.Global;
import com.qingyii.hxtz.zhf.http.Urlutil;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

@ActivityScope
public class ExamineModel extends BaseModel implements WMCJContract.ExamineModel {

    private Gson mGson;
    private Application mApplication;


    @Inject
    public ExamineModel(IRepositoryManager repositoryManager, Gson mGson, Application mApplication) {
        super(repositoryManager);
        this.mGson = mGson;
        this.mApplication = mApplication;
    }

    @Override
    public Observable<ExamineBean> getExamineBean(int system_id) {
        String murl= Urlutil.baseurl+"/kh/manages/"+ Global.userid+"/"+system_id+"?token="+ MyApplication.hxt_setting_config.getString("token","");
       return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getExamine(murl,null);
    }

    @Override
    public Observable<ExamineBean> getExamineBean(int system_id,String  tag_id) {
        String murl= Urlutil.baseurl+"/kh/manages/"+Global.userid+"/"+system_id+"?token="+ MyApplication.hxt_setting_config.getString("token","");
        return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getExamine(murl,String.valueOf(tag_id));
    }

    @Override
    public Observable<ResponseBody> download(String url) {
            return mRepositoryManager.obtainRetrofitService(WMCJApi.class).download(url);
    }
}

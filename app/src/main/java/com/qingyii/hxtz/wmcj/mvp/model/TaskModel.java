package com.qingyii.hxtz.wmcj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.wmcj.WMCJApi;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskTitlebean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Taskdetailbean;
import com.zhf.Util.Global;
import com.zhf.http.Urlutil;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by zhf on 2018/3/21.
 */

@ActivityScope
public class TaskModel extends BaseModel implements WMCJContract.TaskModel {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public TaskModel(IRepositoryManager repositoryManager, Gson mGson, Application mApplication) {
        super(repositoryManager);
        this.mGson = mGson;
        this.mApplication = mApplication;
    }


    @Override
    public Observable<TaskTitlebean> getTaskTitle() {
        String pUrl= Urlutil.baseurl+"/task/"+ Global.userid +"?token="+ MyApplication.hxt_setting_config.getString("token","");

        return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getTaskTitle(pUrl);
    }

    @Override
    public Observable<ResponseBody> getTaskListData(int id) {
        String purl= Urlutil.baseurl+"/task/"+ Global.userid +"/library/"+id
                +"?token="+ MyApplication.hxt_setting_config.getString("token","");
        return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getTaskListData(purl);
    }

    @Override
    public Observable<Taskdetailbean> getTaskDetail(int taskid) {
         String purl=Urlutil.baseurl+"/task/"+ Global.userid+"/getTask?token="+ MyApplication.hxt_setting_config.getString("token","");
        return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getTaskDetailData(purl,String.valueOf(taskid));
    }


}

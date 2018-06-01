package com.qingyii.hxtz.wmcj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.qingyii.hxtz.bean.ReportBean;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.wmcj.WMCJApi;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Headbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkitembean;
import com.qingyii.hxtz.zhf.Util.Global;
import com.qingyii.hxtz.zhf.http.Urlutil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by zhf on 2018/3/13.
 */
@ActivityScope
public class WorkModel extends BaseModel implements WMCJContract.WorkParkModel {

    private Gson mGson;
    private Application mApplication;
    String purl=Urlutil.baseurl+"/kh/"+Global.userid+"/getDynamic?token="+ MyApplication.hxt_setting_config.getString("token","");
   @Inject
    public WorkModel(IRepositoryManager repositoryManager, Gson mGson, Application mApplication) {
        super(repositoryManager);
        this.mGson = mGson;
        this.mApplication = mApplication;
    }

    @Override
    public Observable<WorkParkbean> getWorkMenu() {
        String purl= Urlutil.baseurltest+"/kh/"+ Global.userid + "/actMenu?token="+ MyApplication.hxt_setting_config.getString("token","");
        return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getWorkMenu(purl);
    }

    @Override
    public Observable<WorkParkitembean> getWorkParkItem(int tag_id) {

        return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getWorkParkItem(purl,String.valueOf(tag_id));

    }

    @Override
    public Observable<WorkParkitembean> getWorkParkItem(int library_id, int system_id) {
        if(library_id!=-99){
            Map<String,String> map=new HashMap<String ,String>();
            map.put("library_id",String.valueOf(library_id));
            map.put("system_id",String.valueOf(system_id));
            return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getWorkParkItem(purl,map);
        } else {
            return  mRepositoryManager.obtainRetrofitService(WMCJApi.class).getWorkParkItem(purl,String.valueOf(system_id));
        }
    }

    @Override
    public Observable<WorkParkitembean> getWorkParkItemMore(String time, int library_id, int system_id) {
         if(library_id!=-99) {

             Map<String, String> map = new HashMap<String,String >();
             map.put("library_id", String.valueOf(library_id));
             map.put("system_id", String.valueOf(system_id));
             map.put("created_at", time);
             map.put("direction", "lt");
             return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getWorkParkItemMore(purl, map);
         }  else {
            return getWorkParkItemMore(time,system_id);
         }

    }

    @Override
    public Observable<WorkParkitembean> getWorkParkItemMore(String time, int tag_id) {
       Map<String,String> map=new HashMap<>();
        map.put("tag_id",String.valueOf(tag_id));
        map.put("created_at",time);
        map.put("direction","lt");
        return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getWorkParkItemMore(purl,map);
    }

    @Override
    public Observable<Headbean> getWorkParkItemHead() {
        String purl1= Urlutil.baseurl+"/kh/"+Global.userid+"/silder?token="+ MyApplication.hxt_setting_config.getString("token","");
        return mRepositoryManager.obtainRetrofitService(WMCJApi.class).getWorkParkItemSlider(purl1);
    }

    @Override
    public Observable<ReportBean> getAlreadyWork(String time) {
       String murl= Urlutil.baseurl+"/kh/"+ Global.userid+"/account?token="+ MyApplication.hxt_setting_config.getString("token","");
        if(time.equalsIgnoreCase("null")){
            return  mRepositoryManager.obtainRetrofitService(WMCJApi.class).getReportBean(murl);
        }
        return  mRepositoryManager.obtainRetrofitService(WMCJApi.class).getReportMore(murl,time);
    }


}

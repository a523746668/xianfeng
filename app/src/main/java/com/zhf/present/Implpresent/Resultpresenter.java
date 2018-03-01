package com.zhf.present.Implpresent;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.zhf.Util.HintUtil;
import com.zhf.Util.Global;
import com.zhf.bean.TaskTitlebean;
import com.zhf.http.Urlutil;
import com.zhf.present.Implview.Resultview;
import com.zhf.zfragment.ResultsonFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Response;

/**
 * Created by zhf on 2017/9/22.
 */

public class Resultpresenter {
     private Context context;
     private Resultview resultview;

    public Resultpresenter(Context context, Resultview resultview) {
        this.context = context;
        this.resultview = resultview;
    }


     public void getdata(){
        HintUtil.showdialog(context);
         String pUrl= Urlutil.baseurl+"/task/"+ Global.userid+"?token="+ MyApplication.hxt_setting_config.getString("token","");
         OkHttpUtils
                 .post()
                 .url(pUrl)
                 .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                 .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
                 .build()
                 .execute(new TasktitleCallback() {
                     @Override
                     public void onError(okhttp3.Call call, Exception e, int id) {
                         Log.i("tmdtitleerror",e.getMessage().toString());
                         resultview.getdataerror(e);
                         HintUtil.stopdialog();
                     }

                     @Override
                     public void onResponse(TaskTitlebean response, int id) {
                         if(response.getData().getLibsystem().size()>0){
                             ArrayList<TaskTitlebean.DataBean.LibsystemBean> list= (ArrayList<TaskTitlebean.DataBean.LibsystemBean>) response.getData().getLibsystem();
                             resultview.gettitlesuccess(list);
                             getdvpdata(list);
                              Log.i("tmdtoken",MyApplication.hxt_setting_config.getString("credentials",""));
                             // HintUtil.stopdialog();
                         }
                         else {
                             resultview.getdatano();
                             HintUtil.stopdialog();
                         }


                     }

                 });

     }
    //拿到viewpager的数据
    public void getdvpdata( ArrayList<TaskTitlebean.DataBean.LibsystemBean> list){
        ArrayList<ResultsonFragment> list1=new ArrayList<>();
        for(int i=0;i<list.size();i++){
           ResultsonFragment fragment=new ResultsonFragment();
            fragment.setLibrarySystem(list.get(i).getId());

            list1.add(fragment);
        }
        resultview.getdatasuccess(list1);
    }

   public void unbind(){
       context=null;
       resultview=null;
   }

    public abstract class TasktitleCallback extends Callback<TaskTitlebean> {
        @Override
        public TaskTitlebean parseNetworkResponse(Response response, int id) throws Exception {
            String result=response.body().string();
            Log.i("tmdresponse",result);
            TaskTitlebean taskTitlebean=new Gson().fromJson(result,TaskTitlebean.class);

            return taskTitlebean;
        }
    }

}

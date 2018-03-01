package com.zhf.present.Implpresent;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.zhf.Util.Global;
import com.zhf.base.Basepresenter;
import com.zhf.bean.WorkParkbean;
import com.zhf.http.Urlutil;
import com.zhf.present.Implview.WorkParkview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhf on 2017/9/29.
 */

public class WorkParkpresenter extends Basepresenter<WorkParkview> {

      int i=0;
      Timer timer=new Timer(true);
     TimerTask task=new TimerTask() {
         @Override
         public void run() {
              getdata();
         }
     };

    public WorkParkpresenter(Context context, WorkParkview acvitivtyview) {
        super(context, acvitivtyview);
    }

      public void getdata(){
     String purl= Urlutil.baseurl+"/task/"+ Global.userid + "/actMenu?token="+ MyApplication.hxt_setting_config.getString("token","");
          OkHttpUtils
                  .post()
                  .url(purl)
                  .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                  .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
                  .build()
                  .execute(new WorkParkBack() {
                      @Override
                      public void onError(Call call, Exception e, int id) {

                      }

                      @Override
                      public void onResponse(WorkParkbean response, int id) {
                               Acvitivtyview.getdatasuccess(response);
                            Global.workParkbean=response;
                      }
                  });

      }

   public abstract class WorkParkBack extends Callback<WorkParkbean>{
       @Override
       public WorkParkbean parseNetworkResponse(Response response, int id) throws Exception {
           String result=response.body().string();
           Log.i("tmd",result);
           WorkParkbean workParkbean=new Gson().fromJson(result,WorkParkbean.class);

           return workParkbean;
       }
   }

}

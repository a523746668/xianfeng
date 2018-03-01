package com.zhf.present.Implpresent;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.zhf.Util.HintUtil;
import com.zhf.Util.Global;
import com.zhf.bean.Tasklistbean;
import com.zhf.http.Urlutil;
import com.zhf.present.Implview.Taskindexoneview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


public class Taskindexonepresenter {
   private Taskindexoneview indexview;
    private Context context;

    public Taskindexonepresenter(Taskindexoneview indexview, Context context) {
        this.indexview = indexview;
        this.context = context;
    }

    public void getdata(int titleid,int indlibsysid ){
        HintUtil.showdialog(context);
        String purl= Urlutil.baseurl+"/task/"+ Global.userid+"/library/"+titleid
                +"?token="+ MyApplication.hxt_setting_config.getString("token","");
        OkHttpUtils
                .post()
                .url(purl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
                .addParams("indlibsysid",String.valueOf(indlibsysid))
                .build()
                .execute(new Tasklistbeanback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                       // HintUtil.showtoast(context,e.getMessage().toString());
                        Log.i("tmderror",e.getMessage().toString());
                        HintUtil.stopdialog();

                    }

                    @Override
                    public void onResponse(Tasklistbean response, int id) {
                        indexview.getdatasuccess((ArrayList<Tasklistbean.DataBean.IndlibsyiesBean>) response.getData().getIndlibsyies());
                        if(response.getData().getTask()!=null&&response.getData().getTask().size()>0){
                            indexview.gettaskdatasuccesss((ArrayList<Tasklistbean.DataBean.TaskBean>) response.getData().getTask());
                        }

                          HintUtil.stopdialog();

                    }
                });



    }

   public void unbind(){
       indexview=null;
       context=null;
   }

    public abstract  class Tasklistbeanback extends Callback<Tasklistbean> {
        @Override
        public Tasklistbean parseNetworkResponse(Response response, int id) throws Exception {
            String result=response.body().string();
            Log.i("tmdresult",result);
            Tasklistbean tasklistbean=new Gson().fromJson(result,Tasklistbean.class);

            return tasklistbean;
        }
    }


}

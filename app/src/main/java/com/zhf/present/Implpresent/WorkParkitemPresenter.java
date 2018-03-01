package com.zhf.present.Implpresent;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.zhf.Util.Global;
import com.zhf.Util.HintUtil;
import com.zhf.base.Basepresenter;
import com.zhf.bean.Headbean;
import com.zhf.bean.WorkParkitembean;
import com.zhf.http.Urlutil;
import com.zhf.present.Implview.WorkParkitemview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhf on 2017/9/29.
 */

public class WorkParkitemPresenter extends Basepresenter<WorkParkitemview> {


    public WorkParkitemPresenter(Context context, WorkParkitemview acvitivtyview) {
        super(context, acvitivtyview);
    }

   public void getdata(String time  ,int library_id,int system_id){
      Acvitivtyview.startloadmore();
       String purl= Urlutil.baseurl+"/task/"+ Global.userid+"/getDynamic?token="+ MyApplication.hxt_setting_config.getString("token","");
       OkHttpUtils
               .post()
               .url(purl)
               .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
               .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
               .addParams("library_id",String.valueOf(library_id))
               .addParams("system_id",String.valueOf(system_id))
               .addParams("created_at",time)
               .addParams("direction","lt")
               .build()
               .execute(new WorkParkitemcall() {
                   @Override
                   public void onError(Call call, Exception e, int id) {
                       Log.i("tmditemerror",e.getMessage());
                       Acvitivtyview.endloadmore();
                   }

                   @Override
                   public void onResponse(WorkParkitembean response, int id) {

                     Acvitivtyview.getmoredatasuccess(response);
                       Acvitivtyview.endloadmore();
                   }
               });
   }
    public void getdata(String time  ,int tag_id){
        Acvitivtyview.startloadmore();
        String purl= Urlutil.baseurl+"/task/"+ Global.userid+"/getDynamic?token="+ MyApplication.hxt_setting_config.getString("token","");
        OkHttpUtils
                .post()
                .url(purl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
                .addParams("tag_id",String.valueOf(tag_id))
                .addParams("created_at",time)
                .addParams("direction","lt")
                .build()
                .execute(new WorkParkitemcall() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("tmditemerror",e.getMessage());
                        Acvitivtyview.endloadmore();
                    }

                    @Override
                    public void onResponse(WorkParkitembean response, int id) {

                        Acvitivtyview.getmoredatasuccess(response);
                        Acvitivtyview.endloadmore();
                    }
                });
    }
    public void getdata(  int tag_id){
        String purl= Urlutil.baseurl+"/task/"+Global.userid+"/getDynamic?token="+ MyApplication.hxt_setting_config.getString("token","");
        OkHttpUtils
                .post()
                .url(purl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
                .addParams("tag_id",String.valueOf(tag_id))
                .build()
                .execute(new WorkParkitemcall() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("tmderror",e.getMessage());
                        Acvitivtyview.finishrefresh();
                    }

                    @Override
                    public void onResponse(WorkParkitembean response, int id) {
                        Acvitivtyview.finishrefresh();
                        if(response.getData().getAllactivity()!=null&&response.getData().getAllactivity().size()>0){
                            Acvitivtyview.getdatasuccess(response);
                        }
                    }
                });


    }

     public void getdata(  int library_id,int system_id){
        String purl= Urlutil.baseurl+"/task/"+Global.userid+"/getDynamic?token="+ MyApplication.hxt_setting_config.getString("token","");
        Log.i("tmdworkitem",purl+"--"+library_id+"--"+system_id);
        OkHttpUtils
                .post()
                .url(purl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
                .addParams("library_id",String.valueOf(library_id))
                .addParams("system_id",String.valueOf(system_id))
                .build()
                .execute(new WorkParkitemcall() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("tmditemerror",e.getMessage());
                        Acvitivtyview.finishrefresh();
                    }

                    @Override
                    public void onResponse(WorkParkitembean response, int id) {
                        Acvitivtyview.finishrefresh();
                        if(response.getData().getAllactivity()!=null&&response.getData().getAllactivity().size()>0){
                            Acvitivtyview.getdatasuccess(response);
                        }  else {

                        }


                    }
                });
    }

   public void getimages(){

       String purl= Urlutil.baseurl+"/task/"+Global.userid+"/silder?token="+ MyApplication.hxt_setting_config.getString("token","");
      Log.i("tmdimges",purl);
       OkHttpUtils
               .post()
               .url(purl)
               .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
               .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
               .build()
               .execute(new Headbeancall() {
                   @Override
                   public void onError(Call call, Exception e, int id) {
                                Log.i("tmdimgs",e.getMessage().toString());

                   }

                   @Override
                   public void onResponse(Headbean response, int id) {
                         if(response.getData().getSilder()!=null&&response.getData().getSilder().size()>0){
                             Acvitivtyview.getimagessuccee(response);
                         }

                   }
               });
   }

   public abstract  class  Headbeancall extends Callback<Headbean>{
       @Override
       public Headbean parseNetworkResponse(Response response, int id) throws Exception {
          String result=response.body().string();
           Headbean headbean=new Gson().fromJson(result,Headbean.class);
           return headbean;
       }
   }

  public abstract class WorkParkitemcall extends Callback<WorkParkitembean>{
      @Override
      public WorkParkitembean parseNetworkResponse(Response response, int id) throws Exception {
          String result=response.body().string();
          Log.i("tmdworkitem",result);
         WorkParkitembean workParkitembean=new Gson().fromJson(result,WorkParkitembean.class);

          return workParkitembean;
      }
  }

}

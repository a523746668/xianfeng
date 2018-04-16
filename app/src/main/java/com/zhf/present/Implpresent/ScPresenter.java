package com.zhf.present.Implpresent;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.zhf.bean.SCbean;
import com.zhf.bean.SCtypebean;
import com.zhf.http.Urlutil;
import com.zhf.present.Implview.Scview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhf on 2018/1/12.
 */

public class ScPresenter  {
      private Context context;
      private Scview scview;

    public ScPresenter(Context context, Scview scview) {
        this.context = context;
        this.scview = scview;
    }

    public void gettypes(){
        String murl= Urlutil.baseurl+"/user/collectedtype?token="+ MyApplication.hxt_setting_config.getString("token","");
        OkHttpUtils.get()
                .url(murl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new SCTypebeanBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(SCtypebean response, int id) {
                        if(response.getData().getType()!=null&&response.getData().getType().size()>0)
                         scview.gettypessuccess(response);
                    }
                });


    }


      public void getdata(String type){
          OkHttpUtils
                  .post()
                  .url(XrjHttpClient.getCollectedUrl())
                  .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                  .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                  .addParams("type", type)
                  .build()
                  .execute(new SCdatabeanback() {
                      @Override
                      public void onError(Call call, Exception e, int id) {
                          Log.i("tmdscerror",e.getMessage());
                      }

                      @Override
                      public void onResponse(SCbean response, int id) {
                           scview.getdatasuccess((ArrayList<SCbean.DataBean>) response.getData());
                      }
                  });

      }



    public void unbind(){
           context=null;
          scview=null;
       }

    public abstract  class  SCTypebeanBack extends Callback<SCtypebean>{
        @Override
        public SCtypebean parseNetworkResponse(Response response, int id) throws Exception {
           String result=response.body().string();
           SCtypebean sCtypebean=new Gson().fromJson(result,SCtypebean.class);
            return  sCtypebean;
        }
    }

    public abstract class  SCdatabeanback extends  Callback<SCbean>{
        @Override
        public SCbean parseNetworkResponse(Response response, int id) throws Exception {
            String result=response.body().string();
              SCbean  sCtypebean=new Gson().fromJson(result,SCbean.class);
            return  sCtypebean;
        }
    }

}

package com.zhf.present.Implpresent;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.zhf.Util.HintUtil;
import com.zhf.Util.Global;
import com.zhf.bean.Resultbean;
import com.zhf.http.Urlutil;
import com.zhf.present.Implview.Resultsonview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhf on 2017/9/23.
 */
//resultsonFragment对应的presenter
public class Resultsonpresenter {
   private Context context;
    private Resultsonview sonview;

    public Resultsonpresenter(Context context, Resultsonview sonview) {
        this.context = context;
        this.sonview = sonview;
    }

    public void getdata(int librarySystem ){
        HintUtil.showdialog(context);
        String purl= Urlutil.baseurl+"/task/"+ Global.userid+"/library/"+librarySystem
                +"/ranking?token="+ MyApplication.hxt_setting_config.getString("token","");
        Log.i("tmdresult",purl);
        OkHttpUtils
                .post()
                .url(purl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
                .build()
                .execute(new ResultsonBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        HintUtil.stopdialog();
                        Log.i("tmderror",e.getMessage());

                    }

                    @Override
                    public void onResponse(Resultbean response, int id) {
                        if(response.getData()!=null) {
                            sonview.getdatasuccess(response);
                        } else {
                            Log.i("resultfragment","resultfragment为空");
                        }
                    }
                });

    }

    public void getdata(int librarySystem,int  industryid){
       // HintUtil.showdialog(context);
        String purl= Urlutil.baseurl+"/task/"+Global.userid+"/library/"+librarySystem
                +"/ranking?token="+ MyApplication.hxt_setting_config.getString("token","");
        OkHttpUtils
                .post()
                .url(purl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
                .addParams("industryid",String.valueOf(industryid))
                .build()
                .execute(new ResultsonBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("tmderror",e.getMessage().toString()+"----"+industryid);
                        HintUtil.stopdialog();
                    }

                    @Override
                    public void onResponse(Resultbean response, int id) {
                         if(response.getData()!=null){
                        sonview.getdatasuccess(response);

                         }
                    }
                });

    }


    public void unbind(){
       context=null;
        sonview=null;
    }
    public abstract class ResultsonBack extends Callback<Resultbean>{
        @Override
        public Resultbean parseNetworkResponse(Response response, int id) throws Exception {
            String result=response.body().string();
            Log.i("tmdresult",result);
            Resultbean resultsonbean=new Gson().fromJson(result,Resultbean.class);
            return resultsonbean;

        }
    }

}

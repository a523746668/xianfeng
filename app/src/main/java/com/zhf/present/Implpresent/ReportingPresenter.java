package com.zhf.present.Implpresent;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.zhf.Util.Global;
import com.zhf.Util.HintUtil;
import com.zhf.bean.WorkParkitembean;
import com.zhf.http.Urlutil;
import com.zhf.present.Implview.ReportingView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhf on 2017/12/26.
 */

public class ReportingPresenter   {
      private Context context;
      private ReportingView reportingView;


    public ReportingPresenter(Context context, ReportingView reportingView) {
        this.context = context;
        this.reportingView = reportingView;
    }

    public void getreportingdata(){

        String murl= Urlutil.baseurl+"/task/"+ Global.userid+"/account?token="+ MyApplication.hxt_setting_config.getString("token","");
         OkHttpUtils.post()
                .url(murl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
                 .build()
                .execute(new WorkParkitemcall() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                                 Log.i("tmdreporting",e.getMessage().toString());

                               reportingView.finishrefresh();
                              reportingView.getdatano();
                   }

                    @Override
                    public void onResponse(WorkParkitembean response, int id) {
                            if(response.getData().getAllactivity()!=null&&response.getData().getAllactivity().size()>0){
                                reportingView.getreportingsuccess(response);
                            }  else {
                                reportingView.getdatano();
                            }

                        reportingView.finishrefresh();
                    }
                });
    }
    public void getreportingdatamore(String time){
         reportingView.startloadmore();
        String murl=Urlutil.baseurl+"/task/"+Global.userid+"/account?token="+ MyApplication.hxt_setting_config.getString("token","");
        OkHttpUtils.post()
                .url(murl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
                .addParams("time",time)

                .build()
                .execute(new WorkParkitemcall() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("tmdreporting",e.getMessage().toString());
                        reportingView.endloadmore();
                    }

                    @Override
                    public void onResponse(WorkParkitembean response, int id) {
                        reportingView.getreportingmoresuccess(response);
                        reportingView.endloadmore();

                    }
                });
    }





    public void unbind(){
        context=null;
        reportingView=null;
   }

    public abstract class WorkParkitemcall extends Callback<WorkParkitembean> {
        @Override
        public WorkParkitembean parseNetworkResponse(Response response, int id) throws Exception {
            String result=response.body().string();
            Log.i("tmdworkitem",result);
            WorkParkitembean workParkitembean=new Gson().fromJson(result,WorkParkitembean.class);
            return workParkitembean;
        }
    }
}

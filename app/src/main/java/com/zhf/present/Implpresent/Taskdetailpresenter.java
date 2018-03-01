package com.zhf.present.Implpresent;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.zhf.Util.HintUtil;
import com.zhf.Util.Global;
import com.zhf.bean.Taskdetailbean;
import com.zhf.http.Urlutil;
import com.zhf.present.Implview.Taskdetailview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

//任务清单对应
public class Taskdetailpresenter {
    private Taskdetailview taskdetailview;
    private Context context;

    public Taskdetailpresenter(Taskdetailview taskdetailview, Context context) {
        this.taskdetailview = taskdetailview;
        this.context = context;
    }

   public void getdata(int taskid){
       HintUtil.showdialog(context);
      String purl= Urlutil.baseurl+"/task/"+ Global.userid+"/getTask?token="+ MyApplication.hxt_setting_config.getString("token","");
       OkHttpUtils
               .post()
               .url(purl)
               .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
               .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
               .addParams("taskid",String.valueOf(taskid))
               .build()
               .execute(new TaskdetailCall() {
                   @Override
                   public void onError(Call call, Exception e, int id) {
                       Log.i("tmderror",e.getMessage()+purl+"---"+taskid);

                       HintUtil.stopdialog();
                   }

                   @Override
                   public void onResponse(Taskdetailbean response, int id) {
                           taskdetailview.getdatasuccess(response);
                          Log.i("tmdtaskdetail",response.getData().getTask().getIs_input()+"---"+taskid+"----"+purl);
                           HintUtil.stopdialog();
                   }
               });


   }

    public void unbind(){
          context=null;
          taskdetailview=null;

    }

   public abstract class TaskdetailCall extends Callback<Taskdetailbean>{
       @Override
       public Taskdetailbean parseNetworkResponse(Response response, int id) throws Exception {
            String result=response.body().string();
           Log.i("tmdtaskdetail",result);
          Taskdetailbean taskdetailbean=new Gson().fromJson(result,Taskdetailbean.class);
           return     taskdetailbean;
       }
   }
}

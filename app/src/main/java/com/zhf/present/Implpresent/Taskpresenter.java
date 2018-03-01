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
import com.zhf.present.Implview.Taskview;
import com.zhf.zfragment.TasklistsonFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Response;

//任务清单fragment对应
public class Taskpresenter {
      private Taskview taskview;
      private Context context;

    public Taskpresenter(Taskview taskview, Context context) {
        this.taskview = taskview;
        this.context = context;

    }

    //拿到viewpager的数据
     public void getdvpdata( ArrayList<TaskTitlebean.DataBean.LibsystemBean> list){
         ArrayList<TasklistsonFragment> list1=new ArrayList<>();
         for(int i=0;i<list.size();i++){
             TasklistsonFragment fragment=new TasklistsonFragment();
             fragment.setTitleid(list.get(i).getId());
             fragment.setTitlename(list.get(i).getTitle());
             list1.add(fragment);
           }
           taskview.getdatasuccess(list1);
     }

     //拿到titles
    public void gettitles(){

        HintUtil.showdialog(context);
     String pUrl= Urlutil.baseurl+"/task/"+ Global.userid +"?token="+MyApplication.hxt_setting_config.getString("token","");
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
                      taskview.getdataerror(e);
                        HintUtil.stopdialog();
                    }

                    @Override
                    public void onResponse(TaskTitlebean response, int id) {
                       if(response.getData().getLibsystem()!=null&&response.getData().getLibsystem().size()>0){
                        ArrayList<TaskTitlebean.DataBean.LibsystemBean> list= (ArrayList<TaskTitlebean.DataBean.LibsystemBean>) response.getData().getLibsystem();

                         taskview.gettitlessuccess(list);
                          getdvpdata(list);
                           HintUtil.stopdialog();
                           Log.i("tmdtoken",MyApplication.hxt_setting_config.getString("credentials",""));
                       }
                       else {
                           taskview.getdatano();

                       }
                    }
                });


    }

    public void unbind(){
        context=null;
        taskview=null;
    }
    public abstract class TasktitleCallback extends Callback<TaskTitlebean>{
        @Override
        public TaskTitlebean parseNetworkResponse(Response response, int id) throws Exception {
            String result=response.body().string();


            Log.i("tmdresponse",result);
           TaskTitlebean bean =new Gson().fromJson(result,TaskTitlebean.class);

            return bean;
        }
    }


}

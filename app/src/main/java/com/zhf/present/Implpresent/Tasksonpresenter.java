package com.zhf.present.Implpresent;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.zhf.Util.Global;
import com.zhf.bean.TaskLineBean;
import com.zhf.bean.TaskListViewBean;
import com.zhf.http.Urlutil;
import com.zhf.present.Implview.Tasksonview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Response;


public class Tasksonpresenter {
      private Context context;
      private Tasksonview tasksonview;

    public Tasksonpresenter(Context context, Tasksonview tasksonview) {
        this.context = context;
        this.tasksonview = tasksonview;
    }

  public void getlistdata(int id){


      String purl= Urlutil.baseurl+"/task/"+ Global.userid +"/library/"+id
              +"?token="+ MyApplication.hxt_setting_config.getString("token","");
      Log.i("tmdpurl",purl);
      OkHttpUtils
              .post()
              .url(purl)
              .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
              .addHeader("Authorization",MyApplication.hxt_setting_config.getString("credentials",""))
              .build()
              .execute(new StringCallback() {
                  @Override
                  public void onError(Call call, Exception e, int id) {

                  }

                  @Override
                  public void onResponse(String response, int id) {
                    ArrayList<TaskListViewBean>  list=new ArrayList<TaskListViewBean>();
                      try {
                          org.json.JSONObject object=new org.json.JSONObject(response);
                        JSONArray object1=object.getJSONArray("data");

                         for(int i=0;i<object1.length();i++){


                           JSONObject object2=object1.getJSONObject(i);
                              String json=object2.toString();
                              TaskListViewBean bean=new Gson().fromJson(json,TaskListViewBean.class);
                              Log.i("tmdi",json);
                              list.add(bean);
                          }
                          Log.i("tmdi",list.size()+"-----");
                          tasksonview.getyijishuju(list);

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              });




  }

 public void unbind(){
       context=null;
       tasksonview=null;

 }

     public abstract  class Tasklistbeanback extends Callback<TaskLineBean>{
         @Override
         public TaskLineBean parseNetworkResponse(Response response, int id) throws Exception {
               String result=response.body().string();
             Log.i("tmdresult",result);
               TaskLineBean tasklistbean=new Gson().fromJson(result,TaskLineBean.class);

               return tasklistbean;
         }
     }


}

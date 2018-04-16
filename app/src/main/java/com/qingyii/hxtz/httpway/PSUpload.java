package com.qingyii.hxtz.httpway;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.TrainCourseList;
import com.qingyii.hxtz.pojo.TrainFilesList;
import com.qingyii.hxtz.pojo.TrainList;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 63264 on 16/9/7.
 */

public class PSUpload {

    private static PSUpload psUpload = new PSUpload();

    private PSUpload() {
    }

    public static PSUpload getPSUpload() {
        return psUpload;
    }

    /**
     * 培训列表
     *
     * @param activity
     * @param baseAdapter
     * @param handler
     */
    public void trainList(final Activity activity, final BaseAdapter baseAdapter,
                          final List<TrainList.DataBean> tDataBeanlist, final Handler handler) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getTrainListUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
//                .addParams("id","0")
//                .addParams("direction","it")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new TrainListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("TrainList_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                                 handler.sendEmptyMessage(1);
                             }

                             @Override
                             public void onResponse(TrainList response, int id) {
                                 Log.e("TrainListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         tDataBeanlist.clear();
                                         tDataBeanlist.addAll(response.getData());
                                         for (int i = 0; i < tDataBeanlist.size(); i++) {
                                             Log.e("培训 ID：", tDataBeanlist.get(i).getId() + "");
                                         }
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(activity, "没有更多了", Toast.LENGTH_LONG).show();
                                         handler.sendEmptyMessage(1);
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class TrainListCallback extends Callback<TrainList> {

        @Override
        public TrainList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            TrainList trainList = new Gson().fromJson(result, TrainList.class);
            return trainList;
        }
    }

    /**
     * 培训资料
     *
     * @param activity
     * @param baseAdapter
     * @param training_id
     * @param course_id
     * @param tDataBeanlist
     * @param handler
     */
    public void trainFilesList(final Activity activity, final BaseAdapter baseAdapter, final String training_id,
                               final String course_id, final List<TrainFilesList.DataBean> tDataBeanlist, final Handler handler) {
        String Url = null;
        if (course_id != null) {
            Url = XrjHttpClient.getTrainListUrl() + "/" + training_id + "/course/" + course_id + "/files";

        } else {
            Url = XrjHttpClient.getTrainListUrl() + "/" + training_id + "/files";
        }

        Log.e("TrainFilesList_Url", Url);

        OkHttpUtils
                .post()
                .url(Url)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
//                .addParams("id","0")
//                .addParams("direction","it")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new TrainFilesListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("TrainFilesList_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(TrainFilesList response, int id) {
                                 Log.e("TrainFilesListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         tDataBeanlist.clear();

                                         tDataBeanlist.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                          if(tDataBeanlist.size()<=0){
                                              Toasty.info(activity, "本次培训未上传资料", Toast.LENGTH_LONG, true).show();
                                          }

                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class TrainFilesListCallback extends Callback<TrainFilesList> {

        @Override
        public TrainFilesList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("TrainFilesList_result", result);
            TrainFilesList trainFilesList = new Gson().fromJson(result, TrainFilesList.class);
            return trainFilesList;
        }
    }

    /**
     * 培训课程
     *
     * @param activity
     * @param baseAdapter
     * @param id
     * @param tDataBeanlist
     * @param handler
     */
    public void TrainCourseList(final Activity activity, final BaseAdapter baseAdapter, final int id, final int scheduleid,
                                final List<TrainCourseList.DataBean> tDataBeanlist, final Handler handler) {
        OkHttpUtils
                .post()
                .url(XrjHttpClient.getTrainListUrl() + "/" + id + "/course")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("id", scheduleid + "")
                .addParams("direction", "lt")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new TrainCourseListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("TrainCourseList_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(TrainCourseList response, int id) {
                                 Log.e("TrainCourseListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         if (scheduleid == 0)
                                             tDataBeanlist.clear();
                                         tDataBeanlist.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class TrainCourseListCallback extends Callback<TrainCourseList> {

        @Override
        public TrainCourseList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            TrainCourseList trainCourseList = new Gson().fromJson(result, TrainCourseList.class);
            return trainCourseList;
        }
    }
}

package com.qingyii.hxtz.httpway;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.ExamList;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by XRJ on 16/9/7.
 */

public class KSUpload {

    private static KSUpload ksUpload = new KSUpload();

    private KSUpload() {
    }

    public static KSUpload getKSUpload() {
        return ksUpload;
    }

    /**
     * 考试列表
     *
     * @param activity
     * @param baseAdapter
     * @param handler
     */
    public void examList(final Activity activity, String training_id, final int examID, final BaseAdapter baseAdapter,
                         final List<ExamList.DataBean> eDataBeanlist, final Handler handler) {

        Log.e("ExamList_examID", examID + "");

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getExamListUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("training_id", training_id)
                .addParams("id", examID + "")
                .addParams("direction", "lt")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new ExamListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("ExamList_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(ExamList response, int id) {
                                 Log.e("ExamListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         if (examID == 0)
                                             eDataBeanlist.clear();
                                         eDataBeanlist.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();

//                                         if (examID == 0)
//                                             if (response.getData().size() == 0) {
//                                                 //Toast.makeText(activity, "没有更多了aa", Toast.LENGTH_LONG).show();
//
//                                                 if (handler != null)
//                                                     handler.sendEmptyMessage(120); //没有更多了
//                                             } else {
//                                                 if (handler != null)
                                                     handler.sendEmptyMessage(1);
//                                             }
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class ExamListCallback extends Callback<ExamList> {

        @Override
        public ExamList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("ExamList_String", result);
            //System.out.println("response------"+result);
            ExamList examList = new Gson().fromJson(result, ExamList.class);
            return examList;
        }
    }
}

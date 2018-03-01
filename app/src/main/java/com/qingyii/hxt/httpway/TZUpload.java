package com.qingyii.hxt.httpway;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.notify.mvp.model.entity.NotifyList;
import com.qingyii.hxt.pojo.HandleParameter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by 63264 on 16/9/7.
 */

public class TZUpload {

    private static TZUpload tzUpload = new TZUpload();

    private TZUpload() {
    }

    public static TZUpload getTZUpload() {
        return tzUpload;
    }

    /**
     * 通知列表
     *
     * @param activity
     * @param baseAdapter
     * @param list
     * @param handler
     */
    public void informList(final Activity activity, final BaseAdapter baseAdapter,
                           final List<NotifyList.DataBean> list, final Handler handler) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getInformListUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
//                .addParams("id","0")
//                .addParams("direction","it")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new InformListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("NotifyList_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(NotifyList response, int id) {
                                 Log.e("InformListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         list.clear();
                                         list.addAll(response.getData());
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class InformListCallback extends Callback<NotifyList> {

        @Override
        public NotifyList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            NotifyList informList = new Gson().fromJson(result, NotifyList.class);
            return informList;
        }
    }

    /**
     * 签到
     *
     * @param context
     */
    public void signIn(final Context context, final Dialog bottomDialog, final NotifyList.DataBean iDataBean,
                       final TextView textView, final Handler handler) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getInformListUrl() + "/" + iDataBean.getId() + "/sign")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("signIn_onError", e.toString() + MyApplication.hxt_setting_config.getString("credentials", ""));
                                 Toast.makeText(context, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("signIn_Callback", response.getData() + "");
                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(context, "签收成功", Toast.LENGTH_LONG).show();
                                         textView.setSelected(true);
                                         textView.setText("已签收");
                                         iDataBean.setIs_receipt(1);
                                         iDataBean.setReceipt_status("sign");
                                         try {
                                             SQLiteDatabase dbWrite = MyApplication.helper.getWritableDatabase();
                                             dbWrite.execSQL("update Inform_info set is_receipt = 1,receipt_status = 'sign' where _id =" + iDataBean.getId());

                                             dbWrite.close();
                                         } catch (Exception e) {
                                             Log.e("数据库", "通知修改错误：" + e.toString());
                                         }
                                         handler.sendEmptyMessage(0);
                                         bottomDialog.dismiss();
                                         break;
                                     case 1:
                                         Toast.makeText(context, "签收失败:" + response.getError_msg(), Toast.LENGTH_LONG).show();
//                                         bottomSheetLayout.dismissSheet();
                                         break;
                                     default:
                                         Toast.makeText(context, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }

                             }

                         }
                );
    }

    /**
     * 签到
     *
     * @param context
     */
    public void askForLeave(final Context context, final Dialog bottomDialog, final NotifyList.DataBean iDataBean,
                            String rejectresult, final TextView textView, final Handler handler) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getInformListUrl() + "/" + iDataBean.getId() + "/reject")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("rejectresult", rejectresult)
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("signIn_onError", e.toString() + MyApplication.hxt_setting_config.getString("credentials", ""));
                                 Toast.makeText(context, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("signIn_Callback", response.getData() + "");
                                 switch (response.getError_code()) {
                                     case 0:
                                         textView.setSelected(true);
                                         textView.setText("已请假");
                                         iDataBean.setIs_receipt(1);
                                         iDataBean.setReceipt_status("rejected");
                                         Toast.makeText(context, "请假成功", Toast.LENGTH_LONG).show();
                                         try {
                                             SQLiteDatabase dbWrite = MyApplication.helper.getWritableDatabase();
                                             dbWrite.execSQL("update Inform_info set is_receipt = 1, receipt_status = 'rejected' where _id =" + iDataBean.getId());
                                             dbWrite.close();
                                         } catch (Exception e) {
                                             Log.e("数据库", "通知修改错误：" + e.toString());
                                         }
                                         handler.sendEmptyMessage(0);
                                         bottomDialog.dismiss();
                                         break;
                                     case 1:
                                         Toast.makeText(context, "提交失败:" + response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                     default:
                                         Toast.makeText(context, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }

                             }

                         }
                );
    }

}

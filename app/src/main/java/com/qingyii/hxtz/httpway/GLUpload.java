package com.qingyii.hxtz.httpway;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.qingyii.hxtz.pojo.ManageList;
import com.qingyii.hxtz.pojo.MyUserList;
import com.qingyii.hxtz.pojo.UserContext;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 63264 on 16/9/7.
 */

public class GLUpload {

    private static GLUpload glUpload = new GLUpload();
    public static int yishangbao = 1190;

    private GLUpload() {
    }

    public static GLUpload getGLUpload() {
        return glUpload;
    }

    /**
     * 成员列表
     *
     * @param activity
     * @param baseAdapter
     * @param handler
     */
    public void usersList(final Activity activity, final BaseAdapter baseAdapter,
                          final List<ManageList.DataBean> mDataBeanlist, final Handler handler) {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getManageListUrl() + "/" +
                        MyApplication.hxt_setting_config.getString("ManageDate", "日期") + "/users")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new ManageListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("ManageList_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(ManageList response, int id) {
                                 Log.e("ManageListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         mDataBeanlist.clear();
                                         mDataBeanlist.addAll(response.getData());
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

    private abstract class ManageListCallback extends Callback<ManageList> {

        @Override
        public ManageList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("成员列表 JSON", result);
            ManageList manageList = new Gson().fromJson(result, ManageList.class);
            return manageList;
        }
    }

    /**
     * 待审核列表
     *
     * @param activity
     * @param baseAdapter
     * @param handler
     */
    public void contextList(final Activity activity, final BaseAdapter baseAdapter, int UserID,
                            final List<UserContext.DataBean> mDataBeanlist, final Handler handler) {

        Log.e("UserList_URL", XrjHttpClient.getUserContextUrl() + "/" +
                MyApplication.hxt_setting_config.getString("ManageDate", "日期")
                + "/users/" + UserID);

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getUserContextUrl() + "/" +
                        MyApplication.hxt_setting_config.getString("ManageDate", "日期")
                        + "/user/" + UserID)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new UserListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("UserList_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(UserContext response, int id) {
                                 Log.e("UserListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         mDataBeanlist.clear();
                                         mDataBeanlist.addAll(response.getData());
//                                         baseAdapter.notifyDataSetChanged();
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

    private abstract class UserListCallback extends Callback<UserContext> {

        @Override
        public UserContext parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("待审核列表 JSON", result);
            UserContext userContext = new Gson().fromJson(result, UserContext.class);
            return userContext;
        }
    }

    /**
     * 待确认列表
     *
     * @param activity
     * @param baseAdapter
     * @param handler
     */
    public void confirmedList(final Activity activity, final BaseAdapter baseAdapter,
                              final List<MyUserList.DataBean> mDataBeanlist, final Handler handler) {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getManageListUrl() + "/checklog/" +
                        MyApplication.hxt_setting_config.getString("ManageDate", "日期"))
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new MyUserListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("UserListMy_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(MyUserList response, int id) {
                                 Log.e("UserListMyCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         mDataBeanlist.clear();
                                         mDataBeanlist.addAll(response.getData());
//                                         baseAdapter.notifyDataSetChanged();
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

    private abstract class MyUserListCallback extends Callback<MyUserList> {

        @Override
        public MyUserList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("待确认列表 JSON", result);
            MyUserList myUserList = new Gson().fromJson(result, MyUserList.class);
            return myUserList;
        }
    }

    /**
     * 审核
     *
     * @param activity
     */
    public void auditUpload(final Activity activity, final Dialog dialog, int docID, String score, String reason, String checklogID) {

        Log.e("doc_id", docID + "");
        Log.e("score", score + "");
        Log.e("reason", reason + "");
        Log.e("checklog_id", checklogID);

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl() + "/grade")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("doc_id", docID + "")
                .addParams("score", score)//打分
                .addParams("reason", reason + "")//审核依据
                .addParams("reasonpic", "")//审核依据图片
                .addParams("checklog_id", checklogID + "")//修改时传修改那条的 ID
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Audit_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("AuditCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(activity, "审核已提交", Toast.LENGTH_LONG).show();
                                         dialog.dismiss();
                                         activity.finish();
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 上报上级
     *
     * @param activity
     */
    public void appearUpload(final Activity activity, final Dialog dialog, int docID) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl() + "/appeal/report")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("doc_id", docID + "")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Appear_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("AppearCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(activity, "已上报上级", Toast.LENGTH_LONG).show();
                                         dialog.dismiss();
                                         activity.finish();
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 申诉审核
     *
     * @param activity
     */
    public void appealUpload(final Activity activity, final Dialog dialog, int doc_id, String reason, int checklog_id) {

        Log.e("Url", XrjHttpClient.getManageListUrl() + "/appeal");
        Log.e("appealParams", doc_id + "  " + reason + "  " + checklog_id);

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl() + "/appeal")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("doc_id", doc_id + "")
                .addParams("reason", reason + "")
                .addParams("checklog_id", checklog_id + "")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Appeal_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("AppealCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(activity, "申诉成功，等待审核", Toast.LENGTH_LONG).show();
                                         dialog.dismiss();
                                         activity.finish();
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 确认审核
     *
     * @param activity
     */
    public void submitUpload(final Activity activity, final Dialog dialog, int docID) {

        Log.e("Submit_docID", docID + "");

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl() + "/confirm")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("doc_id", docID + "")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Submit_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("SubmitCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(activity, "已确认，不可更改", Toast.LENGTH_LONG).show();
                                         dialog.dismiss();
                                         activity.finish();
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 确认审核
     *
     * @param activity
     */
    public void submitListUpload(final Activity activity, final BaseAdapter baseAdapter,
                                 final TextView textView, final Dialog dialog, int docID, final Handler handler) {

        Log.e("Submit_docID", docID + "");

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl() + "/confirm")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("doc_id", docID + "")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Submit_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("SubmitCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(activity, "已确认，不可更改", Toast.LENGTH_LONG).show();
                                         dialog.dismiss();
                                         textView.setText("已确认");
                                         textView.setTextColor(Color.parseColor("#24C280"));
                                         textView.setSelected(true);
                                         baseAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(2);
                                         //activity.finish();
                                         break;
                                     default:
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 完成审核并通知
     *
     * @param activity
     */
    public void reportUpload(final Activity activity, final Dialog dialog, String month) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl() + "/report/" + month)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Report_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("ReportCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(activity, "已提交成功", Toast.LENGTH_LONG).show();
                                         dialog.dismiss();
                                         activity.finish();
                                         break;
                                     default:
//                                         Toast.makeText(activity, "提交失败，有未完成项", Toast.LENGTH_LONG).show();
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         dialog.dismiss();
                                         activity.finish();
                                         break;
                                 }
                             }
                         }
                );
    }


    /**
     * 同意上报
     *
     * @param activity
     */
    public void reportUpload(final Activity activity, final Dialog dialog, String month, final Handler handler) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl() + "/report/" + month)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Report_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("ReportCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(activity, "已提交成功", Toast.LENGTH_LONG).show();
                                         dialog.dismiss();
                                         activity.finish();
                                         if (handler != null)
                                             handler.sendEmptyMessage(GLUpload.yishangbao);
                                         break;
                                     default:
//                                         Toast.makeText(activity, "提交失败，有未完成项", Toast.LENGTH_LONG).show();
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         dialog.dismiss();
                                         activity.finish();
                                         break;
                                 }
                             }
                         }
                );
    }


    /**
     * 完成审核并通知
     *
     * @param activity
     */
    public void rejectUpload(final Activity activity, final Dialog dialog, String month) {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getManageListUrl() + "/reject/" + month)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Reject_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("RejectCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(activity, "退回成功", Toast.LENGTH_LONG).show();
                                         dialog.dismiss();
                                         activity.finish();
                                         break;
                                     default:
//                                         Toast.makeText(activity, "提交失败，有未完成项", Toast.LENGTH_LONG).show();
                                         Toast.makeText(activity, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         dialog.dismiss();
                                         activity.finish();
                                         break;
                                 }
                             }
                         }
                );
    }
}

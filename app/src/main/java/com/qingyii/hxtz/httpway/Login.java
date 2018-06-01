package com.qingyii.hxtz.httpway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxtz.base.app.GlobalConsts;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.qingyii.hxtz.pojo.LoginParameter;
import com.qingyii.hxtz.pojo.UserParameter;
import com.qingyii.hxtz.util.DateUtils;
import com.qingyii.hxtz.zhf.Util.Global;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by XRJ on 2016/8/19.
 */

public class Login {

    public SharedPreferences sharedPreferences = MyApplication.hxt_setting_config;
    private SharedPreferences.Editor editor = MyApplication.hxt_setting_config.edit();
    private ProgressDialog pd = null;

    private static Login login = new Login();

    private Login() {
    }

    public static Login getLogin() {
        return login;
    }

    public void userLogin(final Activity activity, final String phone, final String password, final Handler handler) {

        pd = ProgressDialog.show(activity, "", "登录中，请稍后……");
        pd.setCancelable(true);

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getLoginUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addParams("phone", phone)
                .addParams("password", password)
                .build()
                .execute(new LoginCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Lodin_onError", e.toString());
                                 Toast.makeText(activity, "网络异常", Toast.LENGTH_LONG).show();

                                 if (pd != null) {
                                     pd.dismiss();
                                 }

                             }

                             @Override
                             public void onResponse(LoginParameter response, int id) {
                                 try {
                                     Log.e("LoginCallback", response.getError_msg());
                                     switch (response.getError_code()) {
                                         case 0:
                                             // 1、设置极光用户别名
//                                         JPushInterface.setAlias(activity.getApplicationContext(), CacheUtil.user.getPhone() + "", mAliasCallback);

                                             //2、保存账号和密码
                                             editor.putString("phone", phone).commit();
                                             editor.putString("pwd", password).commit();
                                             editor.putString("credentials", "Bearer " + response.getData()).commit();
                                             editor.putString("token",response.getData()).commit();
                                             Log.e("ToKen", MyApplication.hxt_setting_config.getString("credentials", ""));
                                             Global.phone=phone;
                                            Global.flag=true;
                                             userRFI();
                                             //userDevice(activity, response.getData(), handler);
                                             handler.sendEmptyMessage(1);
                                             break;
                                         case 2:
                                             Toast.makeText(activity, "用户账号未注册,请联系管理员", Toast.LENGTH_LONG).show();
                                             break;
                                         default:
                                             Toast.makeText(activity, "账号或密码错误", Toast.LENGTH_LONG).show();
//                                         handler.sendEmptyMessage(1);
                                             break;
                                     }
                                 } catch (Exception e) {
                                     Toast.makeText(activity, "账号或密码错误", Toast.LENGTH_LONG).show();
                                 }

                                 if (pd != null) {
                                     pd.dismiss();
                                 }
                             }
                         }
                );
    }

    public void firstLogin(final Activity activity, final String phone, final String password, final Handler handler) {

        pd = ProgressDialog.show(activity, "", "登录中，请稍后……");
        pd.setCancelable(true);

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getLoginUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addParams("phone", phone)
                .addParams("password", password)
                .build()
                .execute(new LoginCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Lodin_onError", e.toString());
                                 Toast.makeText(activity, "网络异常", Toast.LENGTH_LONG).show();
                                 editor.putString("UserID", "").commit();

                                 if (pd != null) {
                                     pd.dismiss();
                                 }
                                 handler.sendEmptyMessage(1);

                             }

                             @Override
                             public void onResponse(LoginParameter response, int id) {
                                 Log.e("LoginCallback", "Bearer " + response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         // 1、设置极光用户别名
//                                         JPushInterface.setAlias(activity.getApplicationContext(), CacheUtil.user.getPhone() + "", mAliasCallback);

                                         //2、保存账号和密码
                                         editor.putString("phone", phone).commit();
                                         editor.putString("pwd", password).commit();
                                         editor.putString("credentials", "Bearer " + response.getData()).commit();
                                         editor.putString("token",response.getData()).commit();

                                        //3.报送任务相关
                                             Global.phone=phone;
                                             Global.flag=true;
                                         Log.i("tmdtokenbaocun",response.getData());
                                         userRFI();
                                        // userDevice(activity, response.getData(), handler);
                                         handler.sendEmptyMessage(1);
                                         break;
                                     case 2:
                                         Toast.makeText(activity, "用户账号未注册,请联系管理员", Toast.LENGTH_LONG).show();
                                         break;
                                     default:
                                         Toast.makeText(activity, "账号或密码错误", Toast.LENGTH_LONG).show();
                                         handler.sendEmptyMessage(1);
                                         break;
                                 }

                                 if (pd != null) {
                                     pd.dismiss();
                                 }
                             }
                         }
                );
    }

    public void userRFI() {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getUserUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
//                .addHeader("Authorization", "Bearer " + credentials)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new UserCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("UserCallback_onError", e.toString());
//                                 Toast.makeText(activity, "网络异常--用户信息", Toast.LENGTH_LONG).show();
                                 editor.putString("UserID", "").commit();
//                                 UserParameterUtil.userUtil = new UserParameter().getData();

                             }

                             @Override
                             public void onResponse(UserParameter response, int id) {
                                 Log.e("UserCallback", response.getData().getId() + "");
                                 if (!response.getData().getTruename().equals("")) {
                                     editor.putString("UserID", response.getData().getTruename()).commit();
                                     editor.putInt("UserLevel", response.getData().getCheck_level()).commit();
                                     editor.putInt(GlobalConsts.ACCOUNT_ID, response.getData().getAccount_id()).commit();
                                     Log.i("tmdaccountid",response.getData().getAccount_id()+"---");
                                 } else {
                                     editor.putString("UserID", "").commit();
                                     editor.putInt("UserLevel", 0).commit();
                                     editor.putInt(GlobalConsts.ACCOUNT_ID, 0).commit();
                                     Log.i("tmdaccountid","---");
                                 }
//                                 CacheUtil.userid = response.getData().getId();
//                                 CacheUtil.userName = response.getData().getTruename();
//                                 CacheUtil.userData = response.getData();
//                                 CacheUtil.hometitle = userobj.getString("hometitle");
                                 MyApplication.userUtil = response.getData();
                                 MyApplication.userUtil.setAvatar(MyApplication.userUtil.getAvatar() + "?r=" + DateUtils.getDateLong());
                             }
                         }
                );
    }

    public void userDevice(final Activity activity, String credentials) {

        Log.e("DeviceID", MyApplication.hxt_setting_config.getString("DeviceID", "")+"--------");

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getUserUpdateUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", "Bearer " + credentials)
                .addParams("device_id", MyApplication.hxt_setting_config.getString("DeviceID", ""))
                .addParams("jpushbz","1")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("DeviceCallback_onError", e.toString());
//                                 Toast.makeText(activity, "网络异常--请检查网络", Toast.LENGTH_LONG).show();
                             Global.isFlag=false;
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("DeviceCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         Log.e("DeviceCallback", "推送上传成功");
                                         Global.isjpush=true;
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class LoginCallback extends Callback<LoginParameter> {

        @Override
        public LoginParameter parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("LoginCallback_String", result);
            LoginParameter login = new Gson().fromJson(result, LoginParameter.class);
            return login;
        }
    }

    private abstract class UserCallback extends Callback<UserParameter> {
        @Override
        public UserParameter parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("User_String", result);
            UserParameter user = new Gson().fromJson(result, UserParameter.class);
            return user;
        }
    }
}

package com.qingyii.hxt.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.util.FileUtils;
import com.google.gson.Gson;
import com.qingyii.hxt.BuildConfig;
import com.qingyii.hxt.R;
import com.qingyii.hxt.home.mvp.ui.HomeActivity;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.Update;
import com.zhf.Util.FileUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Response;
import timber.log.Timber;

import static com.qingyii.hxt.R.id.text99;

/**
 * Created by zhf on 2017/11/30.
 */


//这个类主要用于检查版本更新的网络请求
public class UpdateUtil {


    /**
     *   检查版本号更新
     */
    public    void Update(Context context) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.URL_PR + "/server")
//                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
//                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new UpdateCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Timber.e("Update_onError", e.toString());
//                                 Toast.makeText(FirstActivity.this, "网络异常--请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(Update response, int id) {
                                 Log.e("UpdateCallback", response.getError_msg()+"--------"+response.getData().getDownload());
                                 switch (response.getError_code()) {
                                     case 0:
                                         try {
//                                             PackageManager pm = FirstActivity.this.getPackageManager();
//                                             PackageInfo pi = pm.getPackageInfo(FirstActivity.this.getPackageName(), 0);
                                             PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                                             String versionName = packageInfo.versionName;
                                             int versioncode = packageInfo.versionCode;
                                             //Toast.makeText(FirstActivity.this, "软件版本号为" + versionName, Toast.LENGTH_LONG).show();
                                             if (versioncode < Integer.parseInt(response.getData().getVersion())) {


                                                 View view= LayoutInflater.from(context).inflate(R.layout.drafit,null);
                                                 AlertDialog dialog=new AlertDialog.Builder(context)
                                                         .setView(view)
                                                         .setCancelable(false)
                                                         .show();
                                                 Button qr= (Button) view.findViewById(R.id.draftqueren);
                                                 Button qx= (Button) view.findViewById(R.id.draftquxiao);
                                                 EditText name= (EditText) view.findViewById(R.id.draftname);
                                                 name.setVisibility(View.GONE);
                                                 TextView textView= (TextView) view.findViewById(text99);
                                                 textView.setVisibility(View.VISIBLE);
                                                 TextView tishi= (TextView) view.findViewById(R.id.tishi);
                                                 ProgressBar bar= (ProgressBar) view.findViewById(R.id.updateprogress);
                                                 TextView  bfb= (TextView) view.findViewById(R.id.baifenbi);

                                                 qr.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         //  Toast.makeText(FirstActivity.this, "软件版本较低，请先更新再继续使用", Toast.LENGTH_LONG).show();
                                                        /* Intent intent = new Intent();
                                                         intent.setAction("android.intent.action.VIEW");

                                                         Uri content_url = Uri.parse(response.getData().getDownload() + "");
                                                         intent.setData(content_url);
                                                         startActivity(intent);
                                                         dialog.dismiss();  */
                                                        Log.i("tmdxiazai",response.getData().getDownload());
                                                         qr.setVisibility(View.GONE);
                                                         qx.setVisibility(View.GONE);
                                                         textView.setVisibility(View.GONE);
                                                         tishi.setVisibility(View.VISIBLE);
                                                         bar.setVisibility(View.VISIBLE);
                                                         bfb.setVisibility(View.VISIBLE);
                                                         OkHttpUtils.get()
                                                                 .url(response.getData().getDownload())
                                                                 .build()
                                                                 .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), versionName+"xfypt.apk") {

                                                                     @Override
                                                                     public void onError(Call call, Exception e, int id) {
                                                                         Log.i("tmdxiazai",e.getMessage().toString());
                                                                     }

                                                                     @Override
                                                                     public void onResponse(File response, int id) {
                                                                         installApk(context,response);

                                                                     }

                                                                     @Override
                                                                     public void inProgress(float progress, long total, int id) {
                                                                         super.inProgress(progress, total, id);
                                                                         int  i= (int) (progress*100);
                                                                         bfb.setText(i+"%");
                                                                         bar.setProgress(i);
                                                                     }
                                                                 });


                                                     }
                                                 });
                                                 qx.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         if(dialog!=null&&dialog.isShowing())
                                                             dialog.dismiss();
                                                     }
                                                 });



                                             } else {
                                                 Toasty.info(context,"已经是最新版本了",0).show();
                                             }
                                         } catch (Exception e) {
                                             Log.e("VersionInfo", "Exception", e);
                                         }
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }
    /**
     *   检查版本号更新
     */
    public    void Updatehome(Context context) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.URL_PR + "/server")
//                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
//                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new UpdateCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Timber.e("Update_onError", e.toString());
//                                 Toast.makeText(FirstActivity.this, "网络异常--请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(Update response, int id) {
                                 Log.e("UpdateCallback", response.getError_msg()+"--------"+response.getData().getDownload());
                                 switch (response.getError_code()) {
                                     case 0:
                                         try {
//                                             PackageManager pm = FirstActivity.this.getPackageManager();
//                                             PackageInfo pi = pm.getPackageInfo(FirstActivity.this.getPackageName(), 0);
                                             PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                                             String versionName = packageInfo.versionName;
                                             int versioncode = packageInfo.versionCode;
                                             //Toast.makeText(FirstActivity.this, "软件版本号为" + versionName, Toast.LENGTH_LONG).show();
                                             if (versioncode < Integer.parseInt(response.getData().getVersion())) {


                                                 View view= LayoutInflater.from(context).inflate(R.layout.drafit,null);
                                                 AlertDialog dialog=new AlertDialog.Builder(context)
                                                         .setView(view)
                                                         .setCancelable(false)
                                                         .show();
                                                 Button qr= (Button) view.findViewById(R.id.draftqueren);
                                                 Button qx= (Button) view.findViewById(R.id.draftquxiao);
                                                 EditText name= (EditText) view.findViewById(R.id.draftname);
                                                 name.setVisibility(View.GONE);
                                                 TextView textView= (TextView) view.findViewById(text99);
                                                 textView.setVisibility(View.VISIBLE);
                                                 TextView tishi= (TextView) view.findViewById(R.id.tishi);
                                                 ProgressBar bar= (ProgressBar) view.findViewById(R.id.updateprogress);
                                                 TextView  bfb= (TextView) view.findViewById(R.id.baifenbi);

                                                 qr.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         //  Toast.makeText(FirstActivity.this, "软件版本较低，请先更新再继续使用", Toast.LENGTH_LONG).show();
                                                        /* Intent intent = new Intent();
                                                         intent.setAction("android.intent.action.VIEW");

                                                         Uri content_url = Uri.parse(response.getData().getDownload() + "");
                                                         intent.setData(content_url);
                                                         startActivity(intent);
                                                         dialog.dismiss();  */
                                                         Log.i("tmdxiazai",response.getData().getDownload());
                                                         qr.setVisibility(View.GONE);
                                                         qx.setVisibility(View.GONE);
                                                         textView.setVisibility(View.GONE);
                                                         tishi.setVisibility(View.VISIBLE);
                                                         bar.setVisibility(View.VISIBLE);
                                                         bfb.setVisibility(View.VISIBLE);
                                                         OkHttpUtils.get()
                                                                 .url(response.getData().getDownload())
                                                                 .build()
                                                                 .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), versionName+"xfypt.apk") {

                                                                     @Override
                                                                     public void onError(Call call, Exception e, int id) {
                                                                         Log.i("tmdxiazai",e.getMessage().toString());
                                                                     }

                                                                     @Override
                                                                     public void onResponse(File response, int id) {
                                                                         installApk(context,response);

                                                                     }

                                                                     @Override
                                                                     public void inProgress(float progress, long total, int id) {
                                                                         super.inProgress(progress, total, id);
                                                                         int  i= (int) (progress*100);
                                                                         bfb.setText(i+"%");
                                                                         bar.setProgress(i);
                                                                     }
                                                                 });


                                                     }
                                                 });
                                                 qx.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         if(dialog!=null&&dialog.isShowing())
                                                             dialog.dismiss();
                                                     }
                                                 });



                                             } else {
                                                // Toasty.info(context,"已经是最新版本了",0).show();
                                             }
                                         } catch (Exception e) {
                                             Log.e("VersionInfo", "Exception", e);
                                         }
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }
    /**
     *   检查版本号更新  直接更新
     */
    public    void Updatehome(Context context,boolean flag) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.URL_PR + "/server")
//                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
//                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new UpdateCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Timber.e("Update_onError", e.toString());
//                                 Toast.makeText(FirstActivity.this, "网络异常--请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(Update response, int id) {
                                 Log.e("UpdateCallback", response.getError_msg()+"--------"+response.getData().getDownload());
                                 switch (response.getError_code()) {
                                     case 0:
                                         try {
//                                             PackageManager pm = FirstActivity.this.getPackageManager();
//                                             PackageInfo pi = pm.getPackageInfo(FirstActivity.this.getPackageName(), 0);
                                             PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                                             String versionName = packageInfo.versionName;
                                             int versioncode = packageInfo.versionCode;
                                             //Toast.makeText(FirstActivity.this, "软件版本号为" + versionName, Toast.LENGTH_LONG).show();
                                             if (versioncode < Integer.parseInt(response.getData().getVersion())) {


                                                 View view= LayoutInflater.from(context).inflate(R.layout.drafit,null);
                                                 AlertDialog dialog=new AlertDialog.Builder(context)
                                                         .setView(view)
                                                         .setCancelable(false)
                                                         .show();
                                                 Button qr= (Button) view.findViewById(R.id.draftqueren);
                                                 Button qx= (Button) view.findViewById(R.id.draftquxiao);
                                                 EditText name= (EditText) view.findViewById(R.id.draftname);
                                                 name.setVisibility(View.GONE);
                                                 TextView textView= (TextView) view.findViewById(text99);
                                                 textView.setVisibility(View.VISIBLE);
                                                 TextView tishi= (TextView) view.findViewById(R.id.tishi);
                                                 ProgressBar bar= (ProgressBar) view.findViewById(R.id.updateprogress);
                                                 TextView  bfb= (TextView) view.findViewById(R.id.baifenbi);


                                                         //  Toast.makeText(FirstActivity.this, "软件版本较低，请先更新再继续使用", Toast.LENGTH_LONG).show();
                                                        /* Intent intent = new Intent();
                                                         intent.setAction("android.intent.action.VIEW");

                                                         Uri content_url = Uri.parse(response.getData().getDownload() + "");
                                                         intent.setData(content_url);
                                                         startActivity(intent);
                                                         dialog.dismiss();  */
                                                         Log.i("tmdxiazai",response.getData().getDownload());
                                                         qr.setVisibility(View.GONE);
                                                         qx.setVisibility(View.GONE);
                                                         textView.setVisibility(View.GONE);
                                                         tishi.setVisibility(View.VISIBLE);
                                                         tishi.setText("重大版本，强制更新中");
                                                         bar.setVisibility(View.VISIBLE);
                                                         bfb.setVisibility(View.VISIBLE);
                                                         OkHttpUtils.get()
                                                                 .url(response.getData().getDownload())
                                                                 .build()
                                                                 .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), versionName+"xfypt.apk") {

                                                                     @Override
                                                                     public void onError(Call call, Exception e, int id) {
                                                                         Log.i("tmdxiazai",e.getMessage().toString());
                                                                     }

                                                                     @Override
                                                                     public void onResponse(File response, int id) {
                                                                         installApk(MyApplication.getInstance(),response);

                                                                     }

                                                                     @Override
                                                                     public void inProgress(float progress, long total, int id) {
                                                                         super.inProgress(progress, total, id);
                                                                         int  i= (int) (progress*100);
                                                                         bfb.setText(i+"%");
                                                                         bar.setProgress(i);
                                                                     }
                                                                 });
                                             } else {
                                                 // Toasty.info(context,"已经是最新版本了",0).show();
                                             }
                                         } catch (Exception e) {
                                             Log.e("VersionInfo", "Exception", e);
                                         }
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }



    //判断SD卡是否可用
    public   boolean issafe(){
        return Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED);
    }
    /**
     * 安装apk
     */
    private   void installApk(Context mContext, File file) {
        ;
        Intent it = new Intent(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT>=24){
            Uri apkUri = FileProvider.getUriForFile(mContext.getApplicationContext(),
                    BuildConfig.APPLICATION_ID + ".fileprovider", file);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            it.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }   else {
            Uri fileUri = Uri.fromFile(file);
            it.setDataAndType(fileUri, "application/vnd.android.package-archive");
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 防止打不开应用
        }
        mContext.startActivity(it);
    }

    private abstract class UpdateCallback extends com.zhy.http.okhttp.callback.Callback<Update> {

        @Override
        public Update parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("UpdateCallback_String", result);
            Update update = new Gson().fromJson(result, Update.class);
            return update;
        }
    }

     public void download(Context context,File file ,String url) {
         View view= LayoutInflater.from(context).inflate(R.layout.drafit,null);

         Button qr= (Button) view.findViewById(R.id.draftqueren);
         Button qx= (Button) view.findViewById(R.id.draftquxiao);
         EditText name= (EditText) view.findViewById(R.id.draftname);
         name.setVisibility(View.GONE);
         TextView textView= (TextView) view.findViewById(text99);
         textView.setVisibility(View.VISIBLE);
         TextView tishi= (TextView) view.findViewById(R.id.tishi);
         ProgressBar bar= (ProgressBar) view.findViewById(R.id.updateprogress);
         TextView  bfb= (TextView) view.findViewById(R.id.baifenbi);
         qr.setVisibility(View.GONE);
         qx.setVisibility(View.GONE);
         textView.setVisibility(View.GONE);
         tishi.setVisibility(View.VISIBLE);
         bar.setVisibility(View.VISIBLE);
         bfb.setVisibility(View.VISIBLE);
         AlertDialog dialog=new AlertDialog.Builder(context)
                 .setView(view)
                 .setCancelable(false)
                 .show();
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new FileCallBack(file.getParent(),file.getName()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("tmddownload","----"+file.getPath()+file.getName()+file.getParent());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        dialog.dismiss();
                        FileUtils.openFile(context,response.getPath(),FileUtils.getMIMEType(response.getPath()));
                        Log.i("tmddownload",response.getPath()+"----"+file.getPath());
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        int  i= (int) (progress*100);
                        bfb.setText(i+"%");
                        bar.setProgress(i);
                    }
                });

     }

}

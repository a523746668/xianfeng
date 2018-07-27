package com.qingyii.hxtz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.andbase.library.app.base.AbBaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.qingyii.hxtz.home.mvp.model.entity.ImageBean;
import com.qingyii.hxtz.home.mvp.ui.HomeNewActivity;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.httpway.Login;
import com.qingyii.hxtz.pojo.Update;
import com.qingyii.hxtz.util.EmptyUtil;
import com.qingyii.hxtz.zhf.http.Urlutil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;
import okhttp3.Response;

/**
 * APP进入第一个activity
 *
 * @author shelia
 */
public class FirstActivity extends AbBaseActivity {

    ImageView login_loading;
    ValueAnimator mAnimation;
    Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0) {

            } else if (msg.what == 1) {
               Intent intent = new Intent(FirstActivity.this, HomeNewActivity.class);
                startActivity(intent);
            }

            return true;
        }
    });
    Editor editor = MyApplication.hxt_setting_config.edit();
    //自动登录账号
    private String phone = "";
    //自动登录密码
    private String pwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        login_loading = (ImageView) findViewById(R.id.first);
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = packageInfo.versionName;
        int versioncode = packageInfo.versionCode;
        OkHttpUtils.get()
                .url(Urlutil.baseurl+"/appdiagram/"+versioncode)
                .build()
                .execute(new ImageCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                       Log.i("tmderrorfirst",e.getMessage());
                        Intent intent = new Intent(FirstActivity.this, HomeNewActivity.class);
                        startActivity(intent);
                        FirstActivity.this.finish();
                    }

                    @Override
                    public void onResponse(ImageBean response, int id) {
                        Glide.with(FirstActivity.this)
                                .load(response.getData().get(0).getUrl())
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .error(R.mipmap.ydy)
                                .into(login_loading);
                       initData();
                    }
                });
//        OnlineConfigAgent.getInstance().updateOnlineConfig(this);
//        UmengUpdateAgent.update(this);
//        UmengUpdateAgent.setUpdateAutoPopup(false);
//        UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
//            @Override
//            public void onClick(int arg0) {
//                switch (arg0) {
//                    case UpdateStatus.NotNow:
//                        String value = OnlineConfigAgent.getInstance().getConfigParams(FirstActivity.this, "forceUpdate");
//                        if (value != null && value.equals("1")) {
//                            UmengUpdateAgent.update(FirstActivity.this);
//                            Toast.makeText(FirstActivity.this, "您的软件版本过低，必须更新才能继续使用", Toast.LENGTH_LONG).show();
//                        } else {
//                            initData();
//                        }
//                        break;
//                }
//            }
//        });
//        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
//
//            @Override
//            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
//                switch (updateStatus) {
//                    case UpdateStatus.Yes: // has update
//                        UmengUpdateAgent.showUpdateDialog(FirstActivity.this, updateInfo);
//                        //UmengUpdateAgent.forceUpdate(FirstActivity.this);
//                        break;
//                    case UpdateStatus.No: // has no update
//                    case UpdateStatus.NoneWifi: // none wifi
//                    case UpdateStatus.Timeout: // time out
//                        initData();
//                        break;
//                }
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
       // Update();

    }


    private void initData() {

        /** 加载透明动画 **/


        phone = MyApplication.hxt_setting_config.getString("phone", "");
        pwd = MyApplication.hxt_setting_config.getString("pwd", "");
        if (EmptyUtil.IsNotEmpty(phone) && EmptyUtil.IsNotEmpty(pwd)) {
            //自动登录
//            login();
            Login login = Login.getLogin();
//            Login login = new Login();
            login.firstLogin(FirstActivity.this, phone, pwd, handler);
        } else {
           handler.sendEmptyMessageDelayed(1,2500);
        }
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

    public abstract class  ImageCallback extends com.zhy.http.okhttp.callback.Callback<ImageBean>{

        @Override
        public ImageBean parseNetworkResponse(Response response, int id) throws Exception {
            String result=response.body().string();
            ImageBean bean=new Gson().fromJson(result,ImageBean.class);
            return bean;
        }
    }

}

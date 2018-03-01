package com.qingyii.hxt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.google.gson.Gson;
import com.qingyii.hxt.home.mvp.ui.HomeActivity;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.httpway.Login;
import com.qingyii.hxt.pojo.Update;
import com.qingyii.hxt.util.EmptyUtil;
import com.zhf.IssusetaskActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.w3c.dom.Text;

import java.io.File;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Response;
import timber.log.Timber;

import static com.qingyii.hxt.R.id.text99;

/**
 * APP进入第一个activity
 *
 * @author shelia
 */
public class FirstActivity extends AbBaseActivity {

    ImageView login_loading;
    Animation mAnimation;
    Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0) {

            } else if (msg.what == 1) {
//                Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
//                startActivity(intent);
            }
            login_loading.startAnimation(mAnimation);
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
        initData();
    }

    private void initData() {
        login_loading = (ImageView) findViewById(R.id.first);
        /** 加载透明动画 **/
        mAnimation = AnimationUtils.loadAnimation(
                FirstActivity.this, R.anim.wmh);
        mAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (MyApplication.hxt_setting_config.getInt("guideState", 0) == 0) {
                    editor.putInt("guideState", 1);
                    editor.commit();
                    Intent intent = new Intent(FirstActivity.this, GuideActivity.class);
                    startActivity(intent);
                } else {
//                    if (CacheUtil.userid == 0) {
//                    if (UserParameterUtil.userUtil.getId() == 0) {

                    if (MyApplication.userUtil == null) {
                        Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(FirstActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
                FirstActivity.this.finish();
            }
        });

        phone = MyApplication.hxt_setting_config.getString("phone", "");
        pwd = MyApplication.hxt_setting_config.getString("pwd", "");
        if (EmptyUtil.IsNotEmpty(phone) && EmptyUtil.IsNotEmpty(pwd)) {
            //自动登录
//            login();
            Login login = Login.getLogin();
//            Login login = new Login();
            login.firstLogin(FirstActivity.this, phone, pwd, handler);
        } else {
            handler.sendEmptyMessage(1);
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





}

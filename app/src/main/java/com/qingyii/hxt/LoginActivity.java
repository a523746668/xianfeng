package com.qingyii.hxt;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxt.base.utils.WindowUtils;
import com.qingyii.hxt.home.mvp.ui.HomeActivity;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.httpway.Login;

import java.util.Set;

import cn.jpush.android.api.TagAliasCallback;

/**
 * 登录界面
 *
 * @author Administrator
 */
public class LoginActivity extends BaseActivity {
    private ImageView login_back;
    private TextView regiest_go, login_go;
    private EditText login_username, login_pwd;
    private RelativeLayout login_user_delete;
    private ProgressDialog pd = null;
    private String username, pwd;
    private RelativeLayout login_re_title;
    private TextView login_name_title;
    private CheckBox checkBox;
    private SharedPreferences sp;
    private Editor edit;
    private TextView regiest_go_02, change_password;
    /**
     * 内刊期数对像：处理极光推送未登录时需求先登录在查看文章列表
     */
//    private Periods periods = null;

    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (pd != null) {
                pd.dismiss();
            }
            if (msg.what == 1) {
//                    if (periods == null) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
//                    } else {
                //极光推送未登录时进入
//                        Intent aIntent = new Intent();
//                        aIntent.setClass(LoginActivity.this, neiKanXq1.class);
//                        aIntent.putExtra("Periods", periods);
//                        startActivity(aIntent);
//                        myApplication.finishActivity(LoginActivity.this);
//                    }
            } else if (msg.what == 0) {
                Toast.makeText(LoginActivity.this, "登录失败，请重新登录！", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新登录！", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 3) {
                Toast.makeText(LoginActivity.this, "用户信息错误，网络异常！", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });

    MyApplication myApplication = new MyApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("先锋云平台");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setVisibility(View.GONE);

        myApplication.addActivity(this);

//        periods = (Periods) getIntent().getSerializableExtra("Periods");

        initUI();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @SuppressLint("ResourceAsColor")
    private void initUI() {
        change_password = (TextView) findViewById(R.id.change_password);
        change_password.setOnClickListener(arg0 -> {
//				Intent intent=new Intent(LoginActivity.this,ChangePassWordActivity.class);
            Intent intent = new Intent(LoginActivity.this, RegiestActivity.class);
            intent.putExtra("comingType", 1);
            intent.putExtra("tltle", "忘记密码");
            startActivity(intent);
        });
        regiest_go_02 = (TextView) findViewById(R.id.regiest_go_02);
        regiest_go_02.setOnClickListener(arg0 -> {
            Intent intent = new Intent(LoginActivity.this, RegiestActivity.class);
            startActivity(intent);
        });
//        login_name_title = (TextView) findViewById(R.id.login_name_title);
//		if(!TextUtils.isEmpty(CacheUtil.userName)){
//			login_name_title.setText(CacheUtil.userName);
//		}
//        login_re_title = (RelativeLayout) findViewById(R.id.login_re_title);
        login_username = (EditText) findViewById(R.id.login_username);
        login_user_delete = (RelativeLayout) findViewById(R.id.login_user_delete);
        login_pwd = (EditText) findViewById(R.id.login_pwd);
        login_user_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login_username.setText("");
            }
        });
//        login_back = (ImageView) findViewById(R.id.login_back);
//        login_back.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyListView v) {
//
//                onBackPressed();
//
//            }
//        });
//		sp=getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE);
        sp = MyApplication.hxt_setting_config;
        checkBox = (CheckBox) findViewById(R.id.login_auto_go);
        edit = sp.edit();
//		 if(sp.getBoolean("isCheck", false)==true){
//			 checkBox.setChecked(true);
//		 }
//		checkBox=(CheckBox) findViewById(R.id.login_auto_go);
//		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				// TODO Auto-generated method stub
//				if(isChecked){
//					edit.putBoolean("isCheck", true).commit();
//				}else{
//					edit.putBoolean("isCheck", false).commit();
//				}
//			}
//		});
        login_go = (TextView) findViewById(R.id.login_go);
        login_go.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                username = login_username.getText().toString();
                pwd = login_pwd.getText().toString();
                if ("".equals(username) || "null".equals(username) || username == null) {
                    Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                } else if (username.length() < 11) {
                    Toast.makeText(LoginActivity.this, "账号需为11位手机号码", Toast.LENGTH_SHORT).show();
                } else if ("".equals(pwd) || "null".equals(pwd) || pwd == null) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
//                    login();
                    Login login = Login.getLogin();
//                    Login login = new Login();
                    login.userLogin(LoginActivity.this, username, pwd, handler);

                }
            }
        });
//        regiest_go = (Button) findViewById(R.id.regiest_go);
//        regiest_go.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyListView arg0) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent(LoginActivity.this, RegiestActivity.class);
//                startActivity(intent);
//            }
//        });
//		if(CacheUtil.userid>0){
//			//已登录用户登录功能失效
//			login_go.setClickable(false);
//			login_go.setBackgroundColor(R.color.red);
//			login_go.setText("已登录,不能在登录");
//		}
    }

    @Override
    public void onBackPressed() {
        WindowUtils.showExitDialog(this);
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
            System.out.println("极光别名设置状态：" + logs);
        }

    };

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}

package com.qingyii.hxtz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.httpway.HandleParameterCallback;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.qingyii.hxtz.util.EmptyUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * 第一次登录/忘记密码界面
 *
 * @author Administrator
 */
public class RegiestActivity extends AbBaseActivity {
    private View login_back;
    private Intent intent;

    private EditText reg_text;
    private TextView one_text, two_text, three_text, confirm_password;
    private Button regiest_go;
    private LinearLayout login_auto_go_ll, ll_confirm_password;
    private String phoneStr = "", codeStr = "", pwdStr = "", conPwdStr = "";
    /**
     * 获取短信验证码接口返回验证码：判断填写的验证码是否一致，一致才能进入下一步填写新密码提交修改
     */
    private String msgCode = "";
    /**
     * 用户ID
     */
    private String userid = "";
    /**
     * 当前步骤
     */
    private int step = 1;
    private TextView login_name_title;
    /**
     * 进入类型：默认0第一登录进入，1忘记密码进入
     */
    private int comingType = 0;
    private String tipmsg = "";

    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            if (msg.what == 1) {
                Toast.makeText(RegiestActivity.this, "恭喜你设置密码成功！", Toast.LENGTH_SHORT).show();
//					Intent intent = new Intent(RegiestActivity.this,MoreActivity.class);
//					startActivity(intent);
                onBackPressed();
            } else if (msg.what == 2) {
                step = 2;
                F5UI();
            } else if (msg.what == 0) {
                Toast.makeText(RegiestActivity.this, "操作失败，请重新操作！", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 3) {
                //获取短信验证码失败时：设置全局变更step=1;回到第一步可重新获取短信验证码
                step = 1;
//                Toast.makeText(RegiestActivity.this, tipmsg, Toast.LENGTH_LONG).show();
                F5UI();
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiest_02);
        intent = getIntent();
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText(intent.getStringExtra("tltle"));

        comingType = getIntent().getIntExtra("comingType", 0);

        initUI();
    }

    /**
     * 刷新UI
     */
    private void F5UI() {

        if (step == 2) {
            two_text.setTextColor(getResources().getColor(
                    R.color.red));
            one_text.setTextColor(getResources().getColor(
                    R.color.one_black));
            three_text.setTextColor(getResources().getColor(
                    R.color.one_black));
            reg_text.setHint("输入验证码");
            regiest_go.setText("确定");
            login_auto_go_ll.setVisibility(View.GONE);
            //设置输入类型
            int inputType = InputType.TYPE_CLASS_NUMBER;
            reg_text.setInputType(inputType);
            reg_text.setText("");
        } else if (step == 3) {
            three_text.setTextColor(getResources().getColor(
                    R.color.red));
            one_text.setTextColor(getResources().getColor(
                    R.color.one_black));
            two_text.setTextColor(getResources().getColor(
                    R.color.one_black));
            ll_confirm_password.setVisibility(View.VISIBLE);
            reg_text.setHint("设置密码");
            confirm_password.setHint("确认密码");
            regiest_go.setText("提交");
            login_auto_go_ll.setVisibility(View.GONE);
            //设置输入类型
//			int inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD;
//			reg_text.setInputType(inputType);
            reg_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
            confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            reg_text.setText("");
            confirm_password.setText("");
        } else if (step == 1) {
            two_text.setTextColor(getResources().getColor(
                    R.color.one_black));
            one_text.setTextColor(getResources().getColor(
                    R.color.red));
            three_text.setTextColor(getResources().getColor(
                    R.color.one_black));
            reg_text.setHint("输入手机号");
            regiest_go.setText("获取验证码");
            login_auto_go_ll.setVisibility(View.GONE);
            //设置输入类型
            int inputType = InputType.TYPE_CLASS_NUMBER;
            reg_text.setInputType(inputType);
            if (EmptyUtil.IsNotEmpty(phoneStr)) {
                reg_text.setText(phoneStr);
            } else {
                reg_text.setText("");
            }
        }
    }

    private void initUI() {
        login_name_title = (TextView) findViewById(R.id.activity_tltle_name);
        if (comingType == 1) {
            login_name_title.setText("忘记密码");
        }
        login_auto_go_ll = (LinearLayout) findViewById(R.id.login_auto_go_ll);
        regiest_go = (Button) findViewById(R.id.regiest_go);
        regiest_go.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (EmptyUtil.IsNotEmpty(reg_text.getText().toString())) {
                    if (step == 1) {
                        //获取手机号
                        phoneStr = reg_text.getText().toString();
                        gainCode(phoneStr);
                        step = 2;
                    } else if (step == 2) {
                        //获取验证码
                        codeStr = reg_text.getText().toString();
                        verifyCode(codeStr);
                        //填写验证码=接口返回验证码时进入修改密码
//                        if (msgCode.equals(codeStr)) {
                        step = 3;
//                        } else {
//                            Toast.makeText(RegiestActivity.this, "短信验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
//                        }
                    } else if (step == 3) {
                        //获取设置密码
                        pwdStr = reg_text.getText().toString();
                        conPwdStr = confirm_password.getText().toString();
                        if (EmptyUtil.IsNotEmpty(phoneStr) && EmptyUtil.IsNotEmpty(codeStr) &&
                                EmptyUtil.IsNotEmpty(pwdStr) && EmptyUtil.IsNotEmpty(conPwdStr)) {
                            if (pwdStr.equals(conPwdStr)) {
                                addUser();
                            } else {
                                Toast.makeText(RegiestActivity.this, "密码不统一，请重新验证", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegiestActivity.this, "手机号，验证码，密码其中有空的项", Toast.LENGTH_SHORT).show();
                        }
                    }
//					step += 1;
                    F5UI();
                } else {
                    String tipMsg = "手机号不能为空！";
                    if (step == 1) {
                        tipMsg = "手机号不能为空！";
                    } else if (step == 2) {
                        tipMsg = "短信验证码不能为空！";
                    } else if (step == 3) {
                        tipMsg = "新密码不能为空！";
                    }
                    Toast.makeText(RegiestActivity.this, tipMsg, Toast.LENGTH_SHORT).show();
                }
            }

            /**
             * 获取短信验证码
             * @param phoneStr
             */
            private void gainCode(String phoneStr) {

                OkHttpUtils
                        .post()
                        .url(XrjHttpClient.getGainCodeUrl())
                        .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
//                        .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                        .addParams("phone", phoneStr)
                        .build()
                        .execute(new HandleParameterCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("Report_onError", e.toString());
                                Toast.makeText(RegiestActivity.this, "验证码请求失败", Toast.LENGTH_LONG).show();
                                handler.sendEmptyMessage(3);
                            }

                            @Override
                            public void onResponse(HandleParameter response, int id) {
                                Log.e("ReportCallback", response.getError_msg());

                                switch (response.getError_code()) {
                                    case 0:
                                        Toast.makeText(RegiestActivity.this, "发送成功", Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        handler.sendEmptyMessage(3);
                                        Toast.makeText(RegiestActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        });
//                final ProgressDialog pd = ProgressDialog.show(RegiestActivity.this, "", "获取短信验证码申请中，请稍等！");
//                pd.setCancelable(true);
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("phone", phoneStr);
//                    byte[] bytes = null;
//                    ByteArrayEntity entity = null;
//                    // stringEntity = new StringEntity(jsonObject.toString());
//                    // 处理保存数据中文乱码问题
//                    try {
//                        bytes = jsonObject.toString().getBytes("utf-8");
//                    } catch (UnsupportedEncodingException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    entity = new ByteArrayEntity(bytes);
//                    YzyHttpClient.post(RegiestActivity.this, HttpUrlConfig.checkPhone, entity, new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, String content) {
//                            // TODO Auto-generated method stub
//                            super.onSuccess(statusCode, content);
//                            if (statusCode == 200) {
//                                try {
//                                    JSONObject obj = new JSONObject(content);
//                                    tipmsg = obj.getString("msg");
//                                    if ("check_success".equals(obj.getString("message"))) {
//                                        //获取验证码和user对像
//                                        msgCode = obj.getString("code");
//                                        userid = obj.getJSONObject("user").getString("userid");
//                                    } else {
//                                        handler.sendEmptyMessage(3);
//                                    }
//                                } catch (JSONException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                    handler.sendEmptyMessage(3);
//                                }
//
//                            } else {
//                                handler.sendEmptyMessage(3);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode,
//                                              Throwable error, String content) {
//                            // TODO Auto-generated method stub
//                            super.onFailure(statusCode, error, content);
//                            handler.sendEmptyMessage(3);
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            // TODO Auto-generated method stub
//                            super.onFinish();
//                            if (pd != null) {
//                                pd.dismiss();
//                            }
//                        }
//                    });
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
            }

            /**
             * 验证验证码
             * @param codeStr
             */
            private void verifyCode(String codeStr) {

                OkHttpUtils
                        .post()
                        .url(XrjHttpClient.getVerifyCodeUrl())
                        .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
//                        .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                        .addParams("phone", phoneStr)
                        .addParams("code", codeStr)
                        .build()
                        .execute(new HandleParameterCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("Verify_onError", e.toString());
                                Toast.makeText(RegiestActivity.this, "验证请求失败", Toast.LENGTH_LONG).show();
                                handler.sendEmptyMessage(2);
                            }

                            @Override
                            public void onResponse(HandleParameter response, int id) {
                                Log.e("VerifyCallback", response.getError_msg());

                                switch (response.getError_code()) {
                                    case 0:
                                        Toast.makeText(RegiestActivity.this, "验证成功", Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        handler.sendEmptyMessage(2);
                                        Toast.makeText(RegiestActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        });
            }

            /**
             * 修改用户密码
             */
            private void addUser() {

                Log.e("Verify_Url", XrjHttpClient.getVerifyCodeUrl());
                Log.e("Verify_phone", phoneStr);
                Log.e("Verify_password", pwdStr);

                OkHttpUtils
                        .post()
                        .url(XrjHttpClient.getChangePasswordUrl())
                        .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
//                        .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                        .addParams("phone", phoneStr)
                        .addParams("password", pwdStr)
                        .build()
                        .execute(new HandleParameterCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("Verify_onError", e.toString());
                                Toast.makeText(RegiestActivity.this, "修改请求失败", Toast.LENGTH_LONG).show();
                                handler.sendEmptyMessage(0);
                            }

                            @Override
                            public void onResponse(HandleParameter response, int id) {
                                Log.e("VerifyCallback", response.getError_msg());

                                switch (response.getError_code()) {
                                    case 0:
                                        Toast.makeText(RegiestActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                                        handler.sendEmptyMessage(1);
                                        break;
                                    default:
                                        handler.sendEmptyMessage(0);
                                        Toast.makeText(RegiestActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        });

//                final ProgressDialog pd = ProgressDialog.show(RegiestActivity.this, "", "提交中，请稍等！");
//                pd.setCancelable(true);
//                JSONObject jsonObject = new JSONObject();
//                try {
////					 jsonObject.put("username", phoneStr);
//                    jsonObject.put("userid", userid);
////					 jsonObject.put("code", codeStr);
//                    jsonObject.put("phone", phoneStr);
//                    jsonObject.put("password", pwdStr);
//                    byte[] bytes;
//                    ByteArrayEntity entity = null;
//                    try {
//                        // stringEntity = new StringEntity(jsonObject.toString());
//                        // 处理保存数据中文乱码问题
//                        bytes = jsonObject.toString().getBytes("utf-8");
//                        entity = new ByteArrayEntity(bytes);
//                        YzyHttpClient.post(RegiestActivity.this,
//                                HttpUrlConfig.updateUser, entity,
//                                new AsyncHttpResponseHandler() {
//                                    @Override
//                                    public void onSuccess(int statecode, String arg0) {
//                                        // TODO Auto-generated method stub
//                                        if (statecode == 200) {
//                                            JSONObject obj;
//                                            try {
//                                                obj = new JSONObject(arg0);
//                                                if ("update_success".equals(obj.getString("message"))) {
////													if(obj.has("user")){
////														JSONObject userobj=obj.getJSONObject("user");
////														if(userobj!=null){
////															CacheUtil.userid=userobj.getInt("userid");
////															CacheUtil.userName=userobj.getString("username");
////															Gson gson=new Gson();
////															CacheUtil.user=gson.fromJson(userobj.toString(), User.class);
////														}
////													}
//                                                    handler.sendEmptyMessage(1);
//                                                }
//                                            } catch (JSONException e) {
//                                                handler.sendEmptyMessage(0);
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(int statusCode, Throwable error, String content) {
//                                        // TODO Auto-generated method stub
//                                        super.onFailure(statusCode, error, content);
//                                        handler.sendEmptyMessage(0);
//                                    }
//
//                                    @Override
//                                    public void onFinish() {
//                                        // TODO Auto-generated method stub
//                                        super.onFinish();
//                                        System.out.println("结束方法");
//                                        if (pd != null) {
//                                            pd.dismiss();
//                                        }
//                                    }
//                                });
//                    } catch (UnsupportedEncodingException e1) {
//                        // TODO Auto-generated catch block
//                        handler.sendEmptyMessage(0);
//                        e1.printStackTrace();
//                    }
//                } catch (JSONException e2) {
//                    // TODO Auto-generated catch block
//                    e2.printStackTrace();
//                    handler.sendEmptyMessage(0);
//                }
            }
        });
        one_text = (TextView) findViewById(R.id.one_text);
        two_text = (TextView) findViewById(R.id.two_text);
        three_text = (TextView) findViewById(R.id.three_text);
        reg_text = (EditText) findViewById(R.id.reg_text);
        ll_confirm_password = (LinearLayout) findViewById(R.id.ll_confirm_password);
        confirm_password = (TextView) findViewById(R.id.confirm_password);
        login_back = findViewById(R.id.returns_arrow);
        login_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.finish();
    }
}

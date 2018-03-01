package com.qingyii.hxt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.httpway.HandleParameterCallback;
import com.qingyii.hxt.pojo.HandleParameter;
import com.zhf.Util.HintUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

public class AlterPwdActivity extends AbBaseActivity implements View.OnClickListener {

    LinearLayout returnsArrow;
    TextView activityTltleName;
    EditText alterPwdOld;
    EditText alterPwdNew;
    EditText alterPwdAgain;
    Button alterPwdConfirm;

    private SharedPreferences.Editor editor = MyApplication.hxt_setting_config.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_pwd);
        activityTltleName = (TextView) findViewById(R.id.activity_tltle_name);
        activityTltleName.setText("修改密码");

        returnsArrow = (LinearLayout) findViewById(R.id.returns_arrow);
        alterPwdOld = (EditText) findViewById(R.id.alter_pwd_old);
        alterPwdNew = (EditText) findViewById(R.id.alter_pwd_new);
        alterPwdAgain = (EditText) findViewById(R.id.alter_pwd_again);
        alterPwdConfirm = (Button) findViewById(R.id.alter_pwd_confirm);

        returnsArrow.setOnClickListener(this);
        alterPwdConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.returns_arrow:
                finish();
                break;
            case R.id.alter_pwd_confirm:
                if (alterPwdOld.getText().toString().equals(""))
                    Toast.makeText(this, "输入旧密码", Toast.LENGTH_LONG).show();
                else if (alterPwdNew.getText().toString().equals(""))
                    Toast.makeText(this, "输入新密码", Toast.LENGTH_LONG).show();
                else if (alterPwdAgain.getText().toString().equals(""))
                    Toast.makeText(this, "再次输入新密码", Toast.LENGTH_LONG).show();
                else if (!alterPwdAgain.getText().toString().equals(alterPwdNew.getText().toString()))
                    Toast.makeText(this, "确认密码输入错误，请重新输入", Toast.LENGTH_LONG).show();
                else
                    alterPwd();
                break;
            default:
                break;
        }
    }

    public void alterPwd() {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getAlterPwdUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("oldpassword", alterPwdOld.getText().toString())
                .addParams("newpassword", alterPwdNew.getText().toString())
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Lodin_onError", e.toString());
                                 Toast.makeText(AlterPwdActivity.this, "网络异常", Toast.LENGTH_LONG).show();

                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 try {
                                     Log.e("AlterPwdback", response.getError_msg());
                                     switch (response.getError_code()) {
                                         case 0:
                                             // 1、设置极光用户别名
//                                         JPushInterface.setAlias(activity.getApplicationContext(), CacheUtil.user.getPhone() + "", mAliasCallback);

                                             //2、保存账号和密码
                                             editor.putString("pwd", alterPwdNew.getText().toString()).commit();
                                             HintUtil.showtoast(AlterPwdActivity.this,"密码修改成功");
                                             finish();
                                             break;
                                         default:
                                             Toast.makeText(AlterPwdActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                             break;
                                     }
                                 } catch (Exception e) {
                                     Toast.makeText(AlterPwdActivity.this, "数据异常", Toast.LENGTH_LONG).show();
                                 }
                             }
                         }
                );
    }
}

package com.qingyii.hxtz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.httpway.HandleParameterCallback;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

public class NameAlterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name_alter_edit;
    private TextView name_alter_affirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_alter);
        ImageView back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        name_alter_edit = (EditText) findViewById(R.id.name_alter_edit);
        name_alter_affirm = (TextView) findViewById(R.id.name_alter_affirm);

        name_alter_affirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name_alter_affirm:
                if (!name_alter_edit.getText().toString().equals(""))
                    alter();
                    break;
            default:
                break;
        }
    }

    private void alter(){

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getUserEditUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("nickname", name_alter_edit.getText().toString() + "")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("NickName_onError", e.toString());
                                 Toast.makeText(NameAlterActivity.this, "用户名修改失败", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("NickNameCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(NameAlterActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                                         MyApplication.userUtil.setNickname(name_alter_edit.getText().toString() + "");

                                         Log.e("返回的头像：", response.getData());
                                         Log.e("返回的头像：", MyApplication.userUtil.getAvatar());
                                         NameAlterActivity.this.finish();
                                         break;
                                     default:
                                         Toast.makeText(NameAlterActivity.this, "修改失败", Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }
}

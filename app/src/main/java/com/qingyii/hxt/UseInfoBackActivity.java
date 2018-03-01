package com.qingyii.hxt;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.httpway.HandleParameterCallback;
import com.qingyii.hxt.pojo.HandleParameter;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

public class UseInfoBackActivity extends AbBaseActivity {
    private RelativeLayout use_info_back_title_rl;
    private ImageView use_info_back;
    private EditText use_info_tel, use_info_content;
    private String use_info_tel_str = "", use_info_content_str = "";
    private TextView use_info_save;
    private ProgressDialog pd;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_info_back);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法
                if (pd != null) {
                    pd.dismiss();
                }
                if (msg.what == 1) {
                    Toast.makeText(UseInfoBackActivity.this,
                            "谢谢你的宝贵意见！", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else if (msg.what == 0) {
                    Toast.makeText(UseInfoBackActivity.this,
                            "对不起，反馈信息失败，请重新反馈！", Toast.LENGTH_SHORT).show();
                }
            }
        };
        initUI();
    }

    private void initUI() {
        use_info_save = (TextView) findViewById(R.id.use_info_save);
        use_info_save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//				use_info_tel_str=use_info_tel.getText().toString();
                use_info_content_str = use_info_content.getText().toString();
//				if(use_info_tel_str.length()==0){
//					Toast.makeText(UseInfoBackActivity.this, "联系方式不能为空！", Toast.LENGTH_SHORT).show();
//					return;
//				}
                if (use_info_content_str.length() == 0) {
                    Toast.makeText(UseInfoBackActivity.this, "反馈内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (use_info_content_str.length() > 0) {
                    useInfoSave(use_info_content_str);
                }
            }

        });
        use_info_back_title_rl = (RelativeLayout) findViewById(R.id.use_info_back_title_rl);
        use_info_back = (ImageView) findViewById(R.id.use_info_back);
        use_info_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        use_info_tel = (EditText) findViewById(R.id.use_info_tel);
        use_info_content = (EditText) findViewById(R.id.use_info_content);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.finish();
    }

    private void useInfoSave(String content) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.URL_PR + "/feedback")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("content", content)
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("意见反馈_onError", e.toString());
                                 Toast.makeText(UseInfoBackActivity.this, "网络异常—意见反馈", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("意见反馈Callback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(UseInfoBackActivity.this, "意见提交成功", Toast.LENGTH_LONG).show();
                                         finish();
                                         break;
                                     default:
                                         Toast.makeText(UseInfoBackActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );


//        // TODO Auto-generated method stub
//        try {
//            pd = ProgressDialog.show(UseInfoBackActivity.this, "",
//                    "提交中，请稍后……");
//            JSONObject jsonObject = new JSONObject();
//            byte[] bytes;
//            ByteArrayEntity entity = null;
////			jsonObject.put("contact", use_info_tel_str);
//            jsonObject.put("content", use_info_content_str);
//            bytes = jsonObject.toString().getBytes("utf-8");
//            entity = new ByteArrayEntity(bytes);
//            YzyHttpClient.post(UseInfoBackActivity.this,
//                    HttpUrlConfig.addSuggestions, entity,
//                    new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, String content) {
//                            // TODO Auto-generated method stub
//                            if (statusCode == 200) {
//                                try {
//                                    JSONObject obj = new JSONObject(content);
//                                    String message = obj.getString("message");
//                                    if ("add_success".equals(message)) {
//                                        handler.sendEmptyMessage(1);
//                                    } else {
//                                        handler.sendEmptyMessage(0);
//                                    }
//
//                                } catch (JSONException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                    handler.sendEmptyMessage(0);
//                                }
//                            } else {
//                                handler.sendEmptyMessage(0);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Throwable error,
//                                              String content) {
//                            handler.sendEmptyMessage(0);
//                        }
//
//                        @Override
//                        public void onFinish() {
//                        }
//
//                    });
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}

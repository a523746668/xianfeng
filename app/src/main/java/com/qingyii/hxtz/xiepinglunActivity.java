package com.qingyii.hxtz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.httpway.HandleParameterCallback;
import com.qingyii.hxtz.pojo.BooksParameter;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.qingyii.hxtz.pojo.PeriodsArticleRela;
import com.qingyii.hxtz.util.EmptyUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * 添加评论界面(内刊,书籍通用)
 *
 * @author Administrator
 */
public class xiepinglunActivity extends AbBaseActivity {
    private ImageView back_btn;
    private TextView tv_fabu;
    private EditText et_xierizhi;
    private PeriodsArticleRela periodsrela;
    /*
     * 默认0，内刊评论，1书籍评论
     */
    private int comingType = 0;
    //    private Book book = null;
    private BooksParameter.DataBean book = null;
    private int parentid = 0;
    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                if (comingType == 0) {
                    //广播通知 :刷新确认订单界面UI
                    Intent intent = new Intent();
                    intent.setAction("action.refreshMyAddress");
                    sendBroadcast(intent);
                    xiepinglunActivity.this.finish();
                } else if (comingType == 1) {
                    //广播通知 :刷新确认订单界面UI
                    Intent intent = new Intent();
                    intent.setAction("action.refreshMyAddress");
                    sendBroadcast(intent);
                    xiepinglunActivity.this.finish();
                }
                Toast.makeText(xiepinglunActivity.this, "添加评论成功", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 0) {
                Toast.makeText(xiepinglunActivity.this, "添加评论失败", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiepinglun);

        comingType = getIntent().getIntExtra("comingType", 0);
        parentid = getIntent().getIntExtra("parentid", 0);
        if (comingType == 0) {
            periodsrela = (PeriodsArticleRela) getIntent().getSerializableExtra("periodsrela");
        } else if (comingType == 1) {
            book = (BooksParameter.DataBean) getIntent().getParcelableExtra("book");
        }


        initUI();
    }

    private void initUI() {
        tv_fabu = (TextView) findViewById(R.id.tv_fabu);
        et_xierizhi = (EditText) findViewById(R.id.et_xierizhi);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        tv_fabu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!EmptyUtil.IsNotEmpty(et_xierizhi.getText().toString().trim())) {
                    Toast.makeText(getBaseContext(), "评论内容不能为空!", Toast.LENGTH_SHORT).show();
                } else {
                    fbPinglun();
                }
            }
        });
    }

    //添加评论接口
    private void fbPinglun() {
        Log.e("书籍评论_URL", XrjHttpClient.getCommentUrl());

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getCommentUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("book_id",book.getId()+"")
                .addParams("content",et_xierizhi.getText().toString())
//                .addParams("parent_id","")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("书籍评论_onError", e.toString());
//                                 Toast.makeText(xiepinglunActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                                 Toast.makeText(xiepinglunActivity.this, "评论成功", Toast.LENGTH_LONG).show();
                                 finish();
                                 handler.sendEmptyMessage(1);
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("书籍评论Callback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         Toast.makeText(xiepinglunActivity.this, "评论成功", Toast.LENGTH_LONG).show();
                                         finish();
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         Toast.makeText(xiepinglunActivity.this, "评论失败", Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );

//        JSONObject Obj = new JSONObject();
//        try {
//            Obj.put("content", et_xierizhi.getText().toString());
//            Obj.put("userid", CacheUtil.userid + "");
//            if (comingType == 0) {
//                Obj.put("articleid", periodsrela.getArticleid());
//            } else if (comingType == 1) {
//                if (parentid > 0) {
//                    Obj.put("parentid", parentid);
//                }
//                Obj.put("bookid", book.getBookid());
//            }
//        } catch (JSONException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        byte[] bytes;
//        ByteArrayEntity entity = null;
//        try {
//            bytes = Obj.toString().getBytes("utf-8");
//            entity = new ByteArrayEntity(bytes);
//            YzyHttpClient.post(xiepinglunActivity.this, HttpUrlConfig.addDiscuss, entity,
//                    new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, String content) {
//                            super.onSuccess(statusCode, content);
//                            if (statusCode == 499) {
//                                Toast.makeText(xiepinglunActivity.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(xiepinglunActivity.this, LoginActivity.class);
//                                startActivity(intent);
//                                onFinish();
//                            } else if (statusCode == 200) {
//                                try {
//                                    JSONObject Obj = new JSONObject(content);
//                                    if ("add_success".equals(Obj.getString("message"))) {
//                                        handler.sendEmptyMessage(1);
//                                    } else {
//                                        handler.sendEmptyMessage(0);
//
//                                    }
//                                } catch (JSONException e) {
//                                    handler.sendEmptyMessage(0);
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//
//                    });
//        } catch (UnsupportedEncodingException e) {
//            handler.sendEmptyMessage(0);
//            e.printStackTrace();
//        }
    }
}

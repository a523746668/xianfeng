package com.qingyii.hxt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.TrainList;

import java.util.HashMap;
import java.util.Map;

/**
 * 培训学员
 */
public class TrainStudentActivity extends AbBaseActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean;

    private WebView train_student_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_student);
        intent = getIntent();
        tDataBean = intent.getParcelableExtra("training");

        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText(intent.getStringExtra("tltle"));
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        train_student_web = (WebView) findViewById(R.id.train_student_web);
        //不加，单击超连接，启动系统的浏览器，加了之后在我们自己的APP中显示网页。
        train_student_web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading
                    (WebView view, String url) {
                Log.i("用户单击超连接", url);
                //判断用户单击的是那个超连接
                if (url.startsWith("tel:")) {
                    String mobile = url.substring(url.lastIndexOf("/") + 1);
                    Uri uri = Uri.parse("tel:" + mobile);
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //这个超连接,java已经处理了，webview不要处理了
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        //WebView加载
        String webUrl = null;
        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));
        //load网页
        webUrl = XrjHttpClient.getTrainListUrl() + "/" + tDataBean.getId() + "/students";
        Log.e("TrainStudent_webUrl", webUrl);
        train_student_web.loadUrl(webUrl, extraHeaders);
    }
}

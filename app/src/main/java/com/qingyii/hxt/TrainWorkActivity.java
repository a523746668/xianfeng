package com.qingyii.hxt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.TrainList;

import java.util.HashMap;
import java.util.Map;

/**
 *  出勤排行
 */
public class TrainWorkActivity extends AbBaseActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean;

    private WebView train_work_web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_work);
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

        train_work_web = (WebView) findViewById(R.id.train_work_web);

        //WebView加载
        String webUrl = null;
        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));
        //load网页
        webUrl = XrjHttpClient.getTrainListUrl() + "/" +tDataBean.getId()+"/attendance";
        Log.e("TrainWork_webUrl", webUrl);
        train_work_web.loadUrl(webUrl, extraHeaders);
    }
}

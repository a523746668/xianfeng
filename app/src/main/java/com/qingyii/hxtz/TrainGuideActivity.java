package com.qingyii.hxtz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.TrainList;

import java.util.HashMap;
import java.util.Map;

/**
 * 培训指南
 */
public class TrainGuideActivity extends AbBaseActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean;

    private WebView train_guide_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_guide);
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

        train_guide_web = (WebView) findViewById(R.id.train_guide_web);

        //WebView加载
        String webUrl = null;
        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));
        //load网页
        webUrl = XrjHttpClient.getTrainListUrl() + "/" + tDataBean.getId() + "/notice";
        Log.e("TrainGuide_webUrl", webUrl);
        train_guide_web.loadUrl(webUrl, extraHeaders);
    }
}

package com.qingyii.hxt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.TrainList;

import java.util.HashMap;
import java.util.Map;

/**
 *  天气
 */
public class TrainWeatherActivity extends AppCompatActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean;

    private WebView train_weather_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_weather);
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

        train_weather_web = (WebView) findViewById(R.id.train_weather_web);

        //WebView加载
        String webUrl = null;
        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));
        //load网页
        webUrl = XrjHttpClient.getTrainListUrl() + "/" + tDataBean.getId() + "/weather";
        Log.e("TrainWeather_webUrl", webUrl);
        train_weather_web.loadUrl(webUrl, extraHeaders);
    }
}

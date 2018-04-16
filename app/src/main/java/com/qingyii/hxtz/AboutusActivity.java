package com.qingyii.hxtz;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.widget.ImageView;

import com.andbase.library.app.base.AbBaseActivity;

import im.delight.android.webview.AdvancedWebView;

public class AboutusActivity extends AbBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        // 点击事件结束后的事件处理
        AdvancedWebView aboutus_content = (AdvancedWebView) findViewById(R.id.aboutus_content);
        aboutus_content.getSettings().setBuiltInZoomControls(false);
        aboutus_content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
//        aboutus_content.getSettings().setUseWideViewPort(true);// 可任意比例缩放
        aboutus_content.getSettings().setLoadWithOverviewMode(true);
        aboutus_content.loadUrl("file:///android_asset/aboutus.html");
        ImageView aboutus_back = (ImageView) findViewById(R.id.aboutus_back);
        aboutus_back.setOnClickListener(v ->
                AboutusActivity.this.finish()
        );
    }
}

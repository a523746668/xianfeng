package com.qingyii.hxtz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.Associates;

import java.util.HashMap;
import java.util.Map;

public class CircleReportWebActivity extends AppCompatActivity {
    private WebView wb_circle_report;
    private Associates.DataBean.DocsBean aDocsBean;

    //文件上传
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_report_web);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("举报");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        aDocsBean = intent.getParcelableExtra("DynamicInfo");

        wb_circle_report = (WebView) findViewById(R.id.wb_circle_report);
        /**
         *
         * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
         * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
         * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
         * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
         * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
         * setSupportZoom 设置是否支持变焦
         * */
        wb_circle_report.getSettings().setBuiltInZoomControls(true);// 隐藏缩放按钮
        //wb_circle_report.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
        //wb_circle_report.getSettings().setUseWideViewPort(true);// 可任意比例缩放
        //wb_circle_report.getSettings().setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        //wb_circle_report.getSettings().setSavePassword(true);
        //wb_circle_report.getSettings().setSaveFormData(true);// 保存表单数据
        wb_circle_report.getSettings().setJavaScriptEnabled(true);
        //wb_circle_report.getSettings().setGeolocationEnabled(true);// 启用地理定位
        // wb_circle_report.getSettings().setDomStorageEnabled(true);
        //urlView.getBackground().setAlpha(155);
        //wb_circle_report.getSettings().setSupportZoom(true);
        //webSettings.setDisplayZoomControls(false);
        wb_circle_report.getSettings().setAllowFileAccess(true);

        //wb_circle_report.addJavascriptInterface(new JsObject(), "huodong");
        //wb_circle_report.getSettings().setLoadWithOverviewMode(true);

        loadUI();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if (requestCode == FILECHOOSER_RESULTCODE) {
//            if (null == mUploadMessage)
//                return;
//            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
//            mUploadMessage.onReceiveValue(result);
//            mUploadMessage = null;
//        }

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null
                    : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null
                    : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }

    private void loadUI() {
        String webUrl = null;
        final Map<String, String> extraHeaders = new HashMap<String, String>();

        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));


        //load网页
        webUrl = XrjHttpClient.getReportUrl() + "/" + MyApplication.userUtil.getId() + "/" + aDocsBean.getId();
        //webUrl = "http://demo.jiluai.com/xxx.html";

        System.out.println("-------------load url " + webUrl);

//        webUrl = "http://v.youku.com/v_show/id_XMTgxMjc3MDIzMg==_ev_1.html?spm=a2hww.20020887.m_205902.5~5~5~5~5~5~5~A&from=y9.3-idx-uhome-1519-20887.205902.1-1&x=1";
        Log.e("同事圈举报网页连接", "UIR：" + webUrl);

        wb_circle_report.loadUrl(webUrl, extraHeaders);

        //WebView播放视频 方法实现

        CircleReportChromeClient crcc = new CircleReportChromeClient();
        crcc.setWebCall(new CircleReportChromeClient.WebCall() {
            @Override
            public void fileChose(ValueCallback<Uri> uploadMsg) {
                openFileChooserImpl(uploadMsg);
            }

            @Override
            public void fileChose5(ValueCallback<Uri[]> uploadMsg) {
                openFileChooserImplForAndroid5(uploadMsg);
            }

            private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"),
                        FILECHOOSER_RESULTCODE);
            }

            private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
                mUploadMessageForAndroid5 = uploadMsg;
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");

                startActivityForResult(chooserIntent,
                        FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
            }
        });


        wb_circle_report.setWebChromeClient(crcc); //文件上传


        //WebView点击事件依旧显示在本页面
        wb_circle_report.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url, extraHeaders);
                return true;
            }


        });

    }

    class JsObject {
        @JavascriptInterface
        public String toString() {
            return "injectedObject";
        }

        @JavascriptInterface
        public void clickOnJoinEvent() {
            //System.out.println("pressed");
        }
    }


}

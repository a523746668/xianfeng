package com.zhf;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.zhf.Util.Global;
import com.zhf.http.Urlutil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import im.delight.android.webview.AdvancedWebView;

public class WorkParkDetailActivity extends AppCompatActivity implements AdvancedWebView.Listener{

      private Unbinder unbinder;

    @BindView(R.id.workpark_details_webView)
    AdvancedWebView  mWebView;

    @BindView(R.id.toolbar_back)
    Button but;

    @BindView(R.id.toolbar_title)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_park_details);
          unbinder=ButterKnife.bind(this);
          inittoolbar();
          initwebview();


    }

    private void inittoolbar() {
              but.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (mWebView.canGoBack()) {
                          mWebView.goBack();
                      } else {
                          finish();
                      }
                  }
              });

           tv.setText("详情");
    }

    private void initwebview() {
         mWebView.getSettings().setBuiltInZoomControls(true);// 隐藏缩放按钮
         mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
         mWebView.getSettings().setUseWideViewPort(true);// 可任意比例缩放
         mWebView.getSettings().setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
         mWebView.getSettings().setSavePassword(true);
         mWebView.getSettings().setSaveFormData(true);// 保存表单数据
         mWebView.getSettings().setJavaScriptEnabled(true);
         mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
         mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
         mWebView.setListener(this, this);
         //mWebView.addHttpHeader("Accept", XrjHttpClient.ACCEPT_V2);
        // mWebView.addHttpHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));

         int param=getIntent().getIntExtra("actid",0);
         String weburl=  Urlutil.baseurl+"/task/"+ Global.userid+"/dynamicDetail?token="+MyApplication.hxt_setting_config.getString("token","")+
                 "&actid="+param;
         Map<String ,String> map=new HashMap<>();
         map.put("Accept", "application/vnd.xianfeng.v2+json");
         map.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));
         mWebView.loadUrl(weburl,map);
         Log.i("tmd",weburl);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mWebView=null;
    }

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == keyEvent.KEYCODE_BACK) {//监听返回键，如果可以后退就后退
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;

            }
        }
        return super.onKeyDown(keyCode, keyEvent);
    }


    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }
}

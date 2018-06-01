package com.qingyii.hxtz.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IView;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.utils.WindowUtils;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.zhf.Util.Global;
import com.qingyii.hxtz.zhf.Util.HintUtil;
import com.qingyii.hxtz.zhf.http.Urlutil;

import butterknife.BindView;

import static android.view.KeyEvent.KEYCODE_BACK;


//2018 5 11 新增加我的学习页面,我的积分,会议签到公用
public class My_StudyActivity extends BaseActivity implements View.OnClickListener,IView {
    @BindView(R.id.toolbar_back)
    Button back;

    @BindView(R.id.toolbar_title)
    TextView title1;

    @BindView(R.id.study_webview)
    WebView webview;

    String mUrl="";

    int wzid=-99;

    int flag;
    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_my__study2;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        flag=getIntent().getIntExtra("flag",1);

        mUrl= Urlutil.waburl+"mystudy?token="+ MyApplication.hxt_setting_config.getString("token","");
        String meeturl=getIntent().getStringExtra("meetqiandao");
       title1.setText("我的学习");
        if(!TextUtils.isEmpty(meeturl)){
            mUrl=meeturl;
            title1.setText("签到结果");
        }
       wzid=getIntent().getIntExtra("wzid",-99);
        if(wzid!=-99){
            mUrl=Urlutil.waburl+"wz/article/"+wzid;
            title1.setText("资讯详情");
        }
       initoolbar();
       initwebview();
    }

    private void initwebview() {
        //声明WebSettings子类
        WebSettings webSettings = webview.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);


//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setBlockNetworkImage(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高webview渲染的优先级
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
      webview.setWebChromeClient(new WebChromeClient(){
          @Override
          public void onReceivedTitle(WebView view, String title) {
              super.onReceivedTitle(view, title);

          }

      });
      webview.setWebViewClient(new WebViewClient(){
          @Override
          public void onPageFinished(WebView view, String url) {
              super.onPageFinished(view, url);
             HintUtil.stopdialog();
          }

          @Override
          public void onPageStarted(WebView view, String url, Bitmap favicon) {
              super.onPageStarted(view, url, favicon);
              HintUtil.showdialog(My_StudyActivity.this);
          }
      });


    webview.loadUrl(mUrl);
    Log.i("tmdmystydy",mUrl);
    }

    private void initoolbar() {
       back.setOnClickListener(this);
       //title.setText("我的学习");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back :
                 killMyself();
                 finish();
                 break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        if (webview != null) {
            webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webview.clearHistory();

            ((ViewGroup) webview.getParent()).removeView(webview);
            webview.destroy();
            webview = null;
        }

    }

    @Override
    protected void onDestroy() {
         killMyself();
        super.onDestroy();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("tmdhome",webview.canGoBack()+"");
        if ((keyCode == KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        } else {
           finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}

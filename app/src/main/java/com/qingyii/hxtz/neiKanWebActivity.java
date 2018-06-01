package com.qingyii.hxtz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.zhf.bean.SCbean;

import java.util.HashMap;
import java.util.Map;

import static com.qingyii.hxtz.http.XrjHttpClient.URL_PR;

/**
 * 文章详情界面
 *
 * @author Administrator
 */
public class neiKanWebActivity extends AbBaseActivity {
    private WebView mWebView;
    private Handler mHandler = new Handler();
    //private String blogdomain;
    private int dWidth;
    private ProgressBar progressbar;
    private static TextView urlView;
    private String Url;
    // private static String Cookies;
    private RelativeLayout r;
    private Context mContext;

    //文件上传
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;

    private String articleUrl= "";

    private static String Cookies;
    //private ArticleListNK.DataBean article_web;

   private SCbean.DataBean  dataBean;
    private void initData(){

        Bundle b = getIntent().getExtras();
        if (b!=null){
            //article_web =  b.getParcelable("Article");
            dataBean= (SCbean.DataBean) b.getSerializable("data");
            if (dataBean!=null){
                //http://xfapi.ccketang.com/article/{article_id}
                articleUrl = URL_PR +"/article/"+dataBean.getId();
                //NKSaveArticle(bookadr,article_pdf);
            }
        }
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mContext = this;
        //创建进度条

        Url = getIntent().getStringExtra("url");

        if (Url == null) {

            Url = "http://m.jiluai.com/news.php";

            //Url = "http://m.jiluai.com/gift.php?c=post&from=android_app";
            //Url = "http://m.jiluai.com/gift.php?from=android_app";
        }

        //System.out.println(" ---------------------------- "+Url);

        initData();

        setContentView(R.layout.activity_neikanweb);
        mWebView = (WebView) findViewById(R.id.wb_neikaninfo_content);

        progressbar = (ProgressBar) findViewById(R.id.color_progressBar);
        urlView = (TextView) findViewById(R.id.url);
        //urlView.getBackground().setAlpha(155);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);


        mWebView.addJavascriptInterface(new JsObject(), "huodong");
        mWebView.getSettings().setLoadWithOverviewMode(true);

        final Map<String, String> extraHeaders = new HashMap<String, String>();

        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));
        //mWebView.loadUrl(articleUrl,extraHeaders);

        System.out.println("首页加载+---------"+articleUrl);

        //urlView.setText(getResources().getString(R.string.ourzone_url)+": "+Url+"\n"+getString(R.string.ourzone_notice));
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {

                mWebView.loadUrl(articleUrl,extraHeaders);
                //Url  = url;
                return super.shouldOverrideUrlLoading(view, url);
            }

            ;


        });

        mWebView.setWebChromeClient(new MyWebChromeClient() );

        mWebView.loadUrl(articleUrl,extraHeaders);
        //默认到30
        progressbar.setProgress(60);

        initTitleBar();
    }

    class JsObject {
        @JavascriptInterface
        public String toString() { return "injectedObject"; }
        @JavascriptInterface
        public void clickOnJoinEvent(){
            //System.out.println("pressed");
        }
    }

    private void initTitleBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
        setTitle(dataBean.getTitle());
    }

    private void goBack() {
        if(getIntent().getBooleanExtra("fromNoticeItemActivity",false)){
            //如果来自于fromNoticeItemActivity
            setResult(205);
            finish();
        }else {
        }
    }



    @Override
    // 退出时提示
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //System.out.println(Url +" "+mWebView.getUrl());
            if (articleUrl != null && mWebView.getUrl().equals(articleUrl)) {
                return super.onKeyDown(keyCode, event);
            } else {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //mWebView.clearView();
        //mWebView.removeAllViews();
        //mWebView.destroy();
        //mWebView = null;
        //setConfigCallback(null);

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }


    class MyWebChromeClient extends WebChromeClient {
        // The undocumented magic method override
        // Eclipse will swear at you if you try to put @Override here

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            System.out.println(" open file");

            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            neiKanWebActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            System.out.println(" open file");

            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            neiKanWebActivity.this.startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE);
        }

        //For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            System.out.println(" open file");

            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            neiKanWebActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }
//        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//
//            mUploadMessage = uploadMsg;
//            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//            i.addCategory(Intent.CATEGORY_OPENABLE);
//            i.setType("image/*");
//            CurrentEvent.this.startActivityForResult(
//                    Intent.createChooser(i, "Image Browser"),
//                    FILECHOOSER_RESULTCODE);
//        }
    }
}

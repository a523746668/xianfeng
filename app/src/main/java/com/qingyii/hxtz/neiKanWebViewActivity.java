package com.qingyii.hxtz;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.ArticleListNK;

import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

import static com.qingyii.hxtz.http.XrjHttpClient.URL_PR;

//import com.jjshome.banking.R;
//import com.jjshome.banking.activity.BasicFragmentActivity;

/**
 * VebView播放视频
 * by 赵国良 2016/5/6
 */
public class neiKanWebViewActivity extends FragmentActivity {

    private FrameLayout videoview;// 全屏时视频加载view
    private AdvancedWebView videowebview;
    private Boolean islandport = true;//true表示此时是竖屏，false表示此时横屏。
    private View xCustomView;
    private mWebChromeClient mWebchromeclient;
    private String url;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;
    private ImageView back;

    private boolean isOnPause = false;

    private String articleUrl= "";
    private ArticleListNK.DataBean article_web;

    private Map<String, String> extraHeaders = new HashMap<String, String>(); ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //System.out.println(" ---------------------------- "+Url);
        //  Log.i("tmdneikan","zzzzzzz");
        initData();


        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉应用标题
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_neikanwebview);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initwidget();
        
        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));
        videowebview.loadUrl(articleUrl,extraHeaders);


    }

    private void initData(){

        Bundle b = getIntent().getExtras();
        if (b!=null){
            article_web =  b.getParcelable("Article");
            if (article_web!=null){
                //http://xfapi.ccketang.com/article/{article_id}
                articleUrl = URL_PR +"/article/"+article_web.getId();
                Log.i("tmdneikan","zzzzzzz"+articleUrl);
                //NKSaveArticle(bookadr,article_pdf);
            }
        }

    }

    private void initwidget() {
        // KeyBoardListener.getInstance(this).init();
        // TODO Auto-generated method stub
        videoview = (FrameLayout) findViewById(R.id.video_view);
        videowebview = (AdvancedWebView) findViewById(R.id.video_webview);
        //back = (ImageView) findViewById(R.id.goback) ;
       // back.setOnClickListener(new Listener());
        WebSettings ws = videowebview.getSettings();
        /**
         *
         * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
         *  setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
         *  setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
         *  setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
         *  setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
         *  setSupportZoom 设置是否支持变焦
         * */
        //ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
        ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSupportZoom(false);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setSupportMultipleWindows(true);
        ws.setGeolocationEnabled(true);// 启用地理定位
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
        ws.setDomStorageEnabled(true);
        mWebchromeclient = new mWebChromeClient();
        videowebview.setWebChromeClient(mWebchromeclient);



        //final Map<String, String> extraHeaders = new HashMap<String, String>();

        videowebview.setWebViewClient(new xWebViewClientent());

    }
   /*
    class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.goback:
                    if (videowebview.canGoBack()) {
                        videowebview.goBack();
                    } else {
                        videowebview.loadUrl("about:blank");
                        videowebview = null;
                        finish();
                    }
                    break;
                default:
                    break;
            }
        }
    }  */


    @Override
    public void onResume (){

        if (isOnPause){
                showCustomView();
            if (videowebview!=null) {
                videowebview.onResume();
            }
                //videowebview.loadUrl(articleUrl,extraHeaders);
        }

        isOnPause = false;
        super.onResume();
    }


    @Override
    public void onPause (){

        if (inCustomView()) {
            hideCustomView();
        } else {

            if (videowebview!=null) {
                videowebview.onPause();
                //videowebview.loadUrl("about:blank");
            }
        }

        isOnPause = true;
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomView()) {
                hideCustomView();
                return true;
            } else {
                if (videowebview!= null) {
                    videowebview.loadUrl("about:blank");
                    videowebview = null;
                    this.finish();
                }
            }
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){//音量+
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume+1, 1);
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){//音量-
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume-1, 1);
        }
        return true;
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
    public boolean inCustomView() {
        return (xCustomView != null);
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        mWebchromeclient.onHideCustomView();
    }

    public void showCustomView() {

        mWebchromeclient.onHideCustomView();
        videoview.setVisibility(View.VISIBLE);

    }

    /**
     * 处理Javascript的对话框、网站图标、网站标题以及网页加载进度等
     *
     * @author
     **/
    public class mWebChromeClient extends WebChromeClient {
        private Bitmap xdefaltvideo;
        private View xprogressvideo;

        @Override
        //播放网络视频时全屏会被调用的方法
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            videowebview.setVisibility(View.GONE);
            //如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            videoview.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            videoview.setVisibility(View.VISIBLE);
        }

        @Override
        //视频播放退出全屏会被调用的
        public void onHideCustomView() {
            if (xCustomView == null)//不是全屏播放状态
                return;
            // Hide the custom view.
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);
            // Remove the custom view from its container.
            videoview.removeView(xCustomView);
            xCustomView = null;
            videoview.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();
            videowebview.setVisibility(View.VISIBLE);
        }

//        //视频加载添加默认图标
//        @Override
//        public Bitmap getDefaultVideoPoster() {
//            //Log.i(LOGTAG, "here in on getDefaultVideoPoster");
//            if (xdefaltvideo == null) {
//                xdefaltvideo = BitmapFactory.decodeResource(getResources(), R.drawable.seach_icon);
//            }
//            return xdefaltvideo;
//        }

        //视频加载时进程loading
//        @Override
//        public NotifyListView getVideoLoadingProgressView() {
//            //Log.i(LOGTAG, "here in on getVideoLoadingPregressView");
//            if (xprogressvideo == null) {
//                LayoutInflater inflater = LayoutInflater.from(PlayVideoWebViewActivity.this);
//                xprogressvideo = inflater.inflate(R.layout.video_loading_progress, null);
//            }
//            return xprogressvideo;
//        }

        //网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            (neiKanWebViewActivity.this).setTitle(title);
        }

    }

    /**
     * 处理各种通知、请求等事件      *
     *
     * @author
     */
    public class xWebViewClientent extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("webviewtest", "shouldOverrideUrlLoading: " + url);

            view.loadUrl(articleUrl,extraHeaders);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    /**
     * * 当横竖屏切换时会调用该方法
     * * @author
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("testwebview", "=====<<<  onConfigurationChanged  >>>=====");
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("webview", "   现在是横屏1");
            islandport = false;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("webview", "   现在是竖屏1");
            islandport = true;
        }
    }
}


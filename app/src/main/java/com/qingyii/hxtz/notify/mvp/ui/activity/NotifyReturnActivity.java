package com.qingyii.hxtz.notify.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.UiUtils;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.notify.mvp.model.entity.NotifyList;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class NotifyReturnActivity extends BaseActivity implements IView {

    @BindView(R.id.toolbar_back)
    Button toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    Button toolbarRight;
    @BindView(R.id.wb_notify_return)
    WebView mWebView;
    private int notify_id;
    @BindView(R.id.webview_progressbar)
    ProgressBar webview_progressbar;
    private boolean isIntercept;

    @OnClick(R.id.toolbar_back_layout)
    public void onViewClicked() {
        killMyself();
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        if(getIntent().hasExtra(NotifyList.DataBean.ID))
            notify_id = getIntent().getIntExtra(NotifyList.DataBean.ID,0);
        return R.layout.activity_notify_return;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if(getIntent().hasExtra(NotifyList.DataBean.ID))
            notify_id = getIntent().getIntExtra(NotifyList.DataBean.ID,0);
        toolbarTitle.setText(R.string.notify_return);
        initWebView();
    }

    private void initWebView() {
        mWebView.getSettings().setBuiltInZoomControls(true);// 隐藏缩放按钮
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
        mWebView.getSettings().setUseWideViewPort(true);// 可任意比例缩放
        mWebView.getSettings().setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        mWebView.getSettings().setSavePassword(true);
        mWebView.getSettings().setSaveFormData(true);// 保存表单数据
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebView.getSettings().setGeolocationEnabled(true);// 启用地理定位
        mWebView.getSettings().setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
        mWebView.getSettings().setDomStorageEnabled(true);
        String webUrl = "";
        final Map<String, String> extraHeaders = new HashMap<String, String>();

        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));

        //load网页
        webUrl = XrjHttpClient.getInformListUrl() + "/return/" + notify_id;
        Log.i("tmdweburl",webUrl);
        mWebView.loadUrl(webUrl, extraHeaders);

        //WebView播放视频 方法实现
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                webview_progressbar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
        //WebView点击事件依旧显示在本页面
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (isIntercept) {
                    isIntercept = false;
                    return true;
                }
                view.loadUrl(url, extraHeaders);
                return false;
            }
            //在页面开始加载时候做一些操作，比如展示进度条
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webview_progressbar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            //在页面加载完成的时候做一些操作,比如隐藏进度条
            @Override
            public void onPageFinished(WebView view, String url) {
                webview_progressbar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        mWebView.addJavascriptInterface(new clickOnJS(), "click");
        mWebView.loadUrl(webUrl, extraHeaders);
    }
    class clickOnJS {
        @JavascriptInterface
        public void closeWindow() {
            isIntercept = true;
            Message event = new Message();
            event.what = EventBusTags.UPDATE_NOTIFY_RETURN;
            EventBus.getDefault().post(event,EventBusTags.NOTIFY);
            finish();
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
        checkNotNull(message);
        UiUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onBackPressed() {
        killMyself();
    }
}

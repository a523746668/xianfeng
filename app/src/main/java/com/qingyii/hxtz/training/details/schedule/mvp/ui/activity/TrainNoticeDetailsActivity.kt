package com.qingyii.hxtz.training.details.schedule.mvp.ui.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.qingyii.hxtz.R
import com.qingyii.hxtz.base.app.GlobalConsts
import com.qingyii.hxtz.base.app.bindView
import com.qingyii.hxtz.http.MyApplication
import com.qingyii.hxtz.http.XrjHttpClient
import com.qingyii.hxtz.training.details.schedule.mvp.presenter.TrainNoticePresenter
import java.util.*

class TrainNoticeDetailsActivity : BaseActivity<TrainNoticePresenter>() {
    val toolbarBack: Button by bindView(R.id.toolbar_back)
    val toolbarBackLayout: LinearLayout by bindView(R.id.toolbar_back_layout)
    val toolbarTitle: TextView by bindView(R.id.toolbar_title)
    val toolbarRightTv: TextView by bindView(R.id.toolbar_right_tv)
    val toolbarRight: Button by bindView(R.id.toolbar_right)
    val toolbarRightLayout: LinearLayout by bindView(R.id.toolbar_right_layout)
    val webview_progressbar: ProgressBar by bindView(R.id.webview_progressbar)
    val mWebView: WebView by bindView(R.id.wb_notify_details_content)

    private var  trainId: Int = 0

    override fun initView(savedInstanceState: Bundle?): Int {
        if (intent.hasExtra(GlobalConsts.TRAIN_ID)) {
            trainId = intent.getIntExtra(GlobalConsts.TRAIN_ID,0)
        }
       return R.layout.activity_train_notice_details
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbarTitle.setText(R.string.notify_details)
        initWebView()
        toolbarBackLayout.setOnClickListener { finish() }
    }

    private fun initWebView() {
        mWebView.settings.builtInZoomControls = true// 隐藏缩放按钮
        mWebView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS// 排版适应屏幕
        mWebView.settings.useWideViewPort = true// 可任意比例缩放
        mWebView.settings.loadWithOverviewMode = true// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        mWebView.settings.saveFormData = true// 保存表单数据
        mWebView.settings.javaScriptEnabled = true
        mWebView.isHorizontalScrollBarEnabled = false//水平不显示
        mWebView.isVerticalScrollBarEnabled = false //垂直不显示
        mWebView.settings.setGeolocationEnabled(true)// 启用地理定位
        mWebView.settings.domStorageEnabled = true
        val extraHeaders = HashMap<String, String>()

        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2)
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))

        //load网页
        var webUrl = XrjHttpClient.URL_PR + "/notify/" + trainId


        //WebView播放视频 方法实现
        mWebView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                webview_progressbar.progress = newProgress
                super.onProgressChanged(view, newProgress)
            }
        })
        //WebView点击事件依旧显示在本页面
        mWebView.setWebViewClient(object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url, extraHeaders)
                return true
            }

            //在页面开始加载时候做一些操作，比如展示进度条
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                webview_progressbar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            //在页面加载完成的时候做一些操作,比如隐藏进度条
            override fun onPageFinished(view: WebView, url: String) {
                webview_progressbar.setVisibility(View.GONE)
                super.onPageFinished(view, url)
            }
        })
        mWebView.loadUrl(webUrl, extraHeaders)
    }

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }
}

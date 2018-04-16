package com.qingyii.hxtz.wmcj.mvp.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.webkit.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.qingyii.hxtz.R
import com.qingyii.hxtz.base.app.bindView
import com.qingyii.hxtz.http.MyApplication
import com.qingyii.hxtz.http.XrjHttpClient
import com.zhf.Util.Global
import com.zhf.http.Urlutil
import im.delight.android.webview.AdvancedWebView


class WorkParkDetailsActivity : BaseActivity<BasePresenter<IModel,IView>>() {
    private var webUrl: String?=null
    private  var prams:String?=null
    val toolbarBackLayout: LinearLayout by bindView(R.id.toolbar_back_layout)
    val toolbarTitle: TextView by bindView(R.id.toolbar_title)
    private val  webView: AdvancedWebView? by bindView(R.id.workpark_details_webView)
    private val video_fullView: FrameLayout? by bindView(R.id.video_fullView)
    private val title_layout: View? by bindView(R.id.title_layout)
    private var xCustomView: View? = null
    private var xCustomViewCallback: WebChromeClient.CustomViewCallback? = null
    private var xwebchromeclient: myWebChromeClient? = null
    private var  map =HashMap<String,String>()
    override fun initData(savedInstanceState: Bundle?) {
        window.addFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        toolbarTitle.text = "详情"
        toolbarBackLayout.setOnClickListener {

            if (webView!!.canGoBack()) {
                webView!!.goBack()
                //返回true webview自己处理

            }

           else {
                webView!!.loadUrl("about:blank")
                this@WorkParkDetailsActivity.finish()
            }


        }
        xwebchromeclient=myWebChromeClient()
        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.setSupportMultipleWindows(true)// 新加
        webView!!.setWebChromeClient(xwebchromeclient)
        webView!!.setWebViewClient(myWebViewClient())
        webView!!.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        map.put("Accept", XrjHttpClient.ACCEPT_V2)


        showContent()
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_work_park_details
    }

    private fun showContent() {

        webView!!.loadUrl(webUrl,map)

        //        //专题、视频用webView展示
        //        if(viewType==2||viewType==3||title.equals(Constant.Top_TITLE)){
        //            scrollView.setVisibility(View.GONE);
        //            newsContentView.setVisibility(View.GONE);
        //            webView!!.loadUrl(commentsUrl);
        //        }
        //        //单图、多图
        //        else{
        //            //用自定义NewsContentView解析数据，再自定义布局内容
        //        }


    }

    override fun setupActivityComponent(appComponent: AppComponent?) {

        var param :Int?=  intent.getIntExtra("actid",0);
        webUrl = Urlutil.baseurl+"/task/"+Global.userid+"/dynamicDetail?token="+MyApplication.hxt_setting_config.getString("token","")+
                "&actid="+param;
        Log.i("tmd",webUrl);

    }

    inner class myWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
        }
    }

    inner class myWebChromeClient : WebChromeClient() {
        private var xprogressvideo: View? = null

        // 播放网络视频时全屏会被调用的方法
        override fun onShowCustomView(view: View, callback: WebChromeClient.CustomViewCallback) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            title_layout!!.visibility = View.GONE
            webView!!.visibility = View.INVISIBLE
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden()
                return
            }
            video_fullView!!.addView(view)
            xCustomView = view
            xCustomViewCallback = callback
            video_fullView!!.visibility = View.VISIBLE
        }

        // 视频播放退出全屏会被调用的
        override fun onHideCustomView() {
            if (xCustomView == null)
            // 不是全屏播放状态
                return
            title_layout!!.visibility = View.VISIBLE
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            xCustomView!!.visibility = View.GONE
            video_fullView!!.removeView(xCustomView)
            xCustomView = null
            video_fullView!!.visibility = View.GONE
            xCustomViewCallback!!.onCustomViewHidden()
            webView!!.visibility = View.VISIBLE
        }

        // 视频加载时进程loading
        override fun getVideoLoadingProgressView(): View? {
            if (xprogressvideo == null) {
                val inflater = LayoutInflater
                        .from(this@WorkParkDetailsActivity)
                xprogressvideo = inflater.inflate(
                        R.layout.video_loading_progress, null)
            }
            return xprogressvideo
        }
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
    private fun inCustomView(): Boolean = xCustomView != null

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    private fun hideCustomView() {
        xwebchromeclient!!.onHideCustomView()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onResume() {
        super.onResume()
        super.onResume()
        webView!!.onResume()
        webView!!.resumeTimers()

        /**
         * 设置为横屏
         */
      /*  if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }*/
    }

    override fun onPause() {
        super.onPause()
        webView!!.onPause()
        webView!!.pauseTimers()
    }

    override fun onDestroy() {
        super.onDestroy()
        video_fullView!!.removeAllViews()
        webView!!.loadUrl("about:blank")
        webView!!.stopLoading()
        webView!!.setWebChromeClient(null)
        webView!!.setWebViewClient(null)
        webView!!.destroy()
    }

    //点击backspace可返回上个页面，而不是退出(若webview只加载了一个页面)
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView!!.visibility == View.VISIBLE) {
                // 按返回时，看网页是否能返回
                if (webView!!.canGoBack()) {
                    webView!!.goBack()
                    //返回true webview自己处理
                    return true

                }
            }
            if (inCustomView()) {
                // webViewDetails.loadUrl("about:blank");
                hideCustomView()
                return true
            } else {
                webView!!.loadUrl("about:blank")
                this@WorkParkDetailsActivity.finish()
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}

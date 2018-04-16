package com.qingyii.hxtz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxtz.base.utils.PickPhotoUtil;
import com.qingyii.hxtz.base.widget.MyWebChromeClient;
import com.qingyii.hxtz.circle.GetPathFromUri4kitkat;
import com.qingyii.hxtz.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

/**
 * 首页广告详情
 *
 * @author Administrator
 */
public class MainWebActivity extends BaseActivity {
    private Intent intent;
    private HomeInfo.AdBean.INDEXBANNERBean aDataBean;
    private AdvancedWebView wb_inform_content;
    private MyWebChromeClient mWebChromeClient;
private int flag;
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main_web);
        intent = getIntent();
        aDataBean = intent.getParcelableExtra("aDataBean");
       flag=intent.getIntExtra("flag",0);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText(aDataBean.getAdtitle() + "");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);

        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mWebChromeClient=new MyWebChromeClient(new PickPhotoUtil(this),tltle);
        wb_inform_content = (AdvancedWebView) findViewById(R.id.wb_inform_content);
        /**
         *
         * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
         * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
         * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
         * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
         * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
         * setSupportZoom 设置是否支持变焦
         * */
       wb_inform_content.getSettings().setBuiltInZoomControls(true);// 隐藏缩放按钮
       wb_inform_content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
       wb_inform_content.getSettings().setUseWideViewPort(true);// 可任意比例缩放
       wb_inform_content.getSettings().setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
       wb_inform_content.getSettings().setSavePassword(true);
       wb_inform_content.getSettings().setSaveFormData(true);// 保存表单数据
      wb_inform_content.getSettings().setJavaScriptEnabled(true);
      wb_inform_content.getSettings().setGeolocationEnabled(true);// 启用地理定位
      wb_inform_content.getSettings().setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
        wb_inform_content.getSettings().setDomStorageEnabled(true);
        wb_inform_content.getSettings().setSupportMultipleWindows(true);

        this.loadUI();
    }

    private void loadUI() {
        String webUrl = null;
        final Map<String, String> extraHeaders = new HashMap<String, String>();

        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));

        //load网页
        webUrl = aDataBean.getAdlink() + "";
//        webUrl = "http://v.youku.com/v_show/id_XMTgxMjc3MDIzMg==_ev_1.html?spm=a2hww.20020887.m_205902.5~5~5~5~5~5~5~A&from=y9.3-idx-uhome-1519-20887.205902.1-1&x=1";
        Log.e("广告网页连接", "UIR：" + webUrl);

       if(flag!=0){
           wb_inform_content.loadUrl(webUrl );
       }else {
           wb_inform_content.loadUrl(webUrl ,extraHeaders);
       }


        //WebView播放视频 方法实现
        wb_inform_content.setWebChromeClient(mWebChromeClient);
        //WebView点击事件依旧显示在本页面
        wb_inform_content.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url,extraHeaders);
                return true;
            }
        });
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理页面返回或取消选择结果
         */
        switch (requestCode) {
            case PickPhotoUtil.REQUEST_FILE_PICKER:
                pickPhotoResult(resultCode, data);
                break;
            case PickPhotoUtil.REQUEST_CODE_PICK_PHOTO:
                pickPhotoResult(resultCode, data);
                break;
            case PickPhotoUtil.REQUEST_CODE_TAKE_PHOTO:
                takePhotoResult(resultCode);

                break;
            case PickPhotoUtil.REQUEST_CODE_PREVIEW_PHOTO:
                cancelFilePathCallback();
                break;
            default:

                break;
        }
    }

    private void pickPhotoResult(int resultCode, Intent data) {
        if (PickPhotoUtil.mFilePathCallback != null) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = GetPathFromUri4kitkat.getPath(this, result);
                Uri uri = Uri.fromFile(new File(path));
                PickPhotoUtil.mFilePathCallback.onReceiveValue(new Uri[]{uri});
                /**
                 * 将路径赋值给常量photoFile4，记录第一张上传照片路径
                 */
                PickPhotoUtil.photoPath = path;

                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /**
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PickPhotoUtil.mFilePathCallback.onReceiveValue(null);
                PickPhotoUtil.mFilePathCallback = null;
            }
            /**
             * 针对API 19之前的版本
             */
        } else if (PickPhotoUtil.mFilePathCallback4 != null) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = GetPathFromUri4kitkat.getPath(this, result);
                Uri uri = Uri.fromFile(new File(path));
                PickPhotoUtil.mFilePathCallback4.onReceiveValue(uri);
                /**
                 * 将路径赋值给常量photoFile
                 */
                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /**
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PickPhotoUtil.mFilePathCallback4.onReceiveValue(null);
                PickPhotoUtil.mFilePathCallback4 = null;
            }
        }
    }

    private void takePhotoResult(int resultCode) {
        if (PickPhotoUtil.mFilePathCallback != null) {
            if (resultCode == RESULT_OK) {
                String path = PickPhotoUtil.photoPath;
                Uri uri = Uri.fromFile(new File(path));
                PickPhotoUtil.mFilePathCallback.onReceiveValue(new Uri[]{uri});

                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /**
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PickPhotoUtil.mFilePathCallback.onReceiveValue(null);
                PickPhotoUtil.mFilePathCallback = null;
            }
            /**
             * 针对API 19之前的版本
             */
        } else if (PickPhotoUtil.mFilePathCallback4 != null) {
            if (resultCode == RESULT_OK) {
                String path = PickPhotoUtil.photoPath;
                Uri uri = Uri.fromFile(new File(path));
                PickPhotoUtil.mFilePathCallback4.onReceiveValue(uri);

                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /**
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PickPhotoUtil.mFilePathCallback4.onReceiveValue(null);
                PickPhotoUtil.mFilePathCallback4 = null;
            }
        }
    }

    private void cancelFilePathCallback() {
        if (PickPhotoUtil.mFilePathCallback4 != null) {
            PickPhotoUtil.mFilePathCallback4.onReceiveValue(null);
            PickPhotoUtil.mFilePathCallback4 = null;
        } else if (PickPhotoUtil.mFilePathCallback != null) {
            PickPhotoUtil.mFilePathCallback.onReceiveValue(null);
            PickPhotoUtil.mFilePathCallback = null;
        }
    }
}

package com.qingyii.hxtz.base.widget;

import android.net.Uri;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.qingyii.hxtz.base.utils.PickPhotoUtil;

/**
 * Created by xubo on 2017/7/4.
 */

public class MyWebChromeClient extends WebChromeClient {
    private static final String TAG = "MyWebChromeClient";
    private PickPhotoUtil pickPhotoUtil;
    private TextView textView;

    public MyWebChromeClient(PickPhotoUtil pickPhotoUtil,TextView textView) {
        this.pickPhotoUtil = pickPhotoUtil;
        this.textView=textView;
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        pickPhotoUtil.promptDialog();
        PickPhotoUtil.mFilePathCallback = filePathCallback;
        Log.d(TAG, "onShowFileChooser: 被调用几次？");
        /**
         * 返回true，如果filePathCallback被调用；返回false，如果忽略处理
         */

        return true;
    }

    /**
     * 过时的方法：openFileChooser
     */
    public void openFileChooser(ValueCallback<Uri> filePathCallback) {
        pickPhotoUtil.openFileManager();
        PickPhotoUtil.mFilePathCallback4 = filePathCallback;
        Log.d(TAG, "openFileChooser: 被调用几次？");
    }

    /**
     * 过时的方法：openFileChooser
     */
    public void openFileChooser(ValueCallback filePathCallback, String acceptType) {
        pickPhotoUtil.promptDialog();
        String title = acceptType;
        PickPhotoUtil.mFilePathCallback4 = filePathCallback;
    }

    /**
     * 过时的方法：openFileChooser
     */
    public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
        pickPhotoUtil.promptDialog();
        String title = acceptType;
        PickPhotoUtil.mFilePathCallback4 = filePathCallback;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        textView.setText(title);
    }
}

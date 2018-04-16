package com.qingyii.hxtz;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by xuzhe on 2016/11/29.
 */

public class CircleReportChromeClient extends WebChromeClient {

    private WebCall webCall;

    public void setWebCall(WebCall webCall) {
        this.webCall = webCall;

    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (webCall != null)
            webCall.fileChose(uploadMsg);
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    // For Android > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    // For Android > 5.0
    //@Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean onShowFileChooser(WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {

        System.out.println("onShowFileChooser"+ "-------------------------");

        if (webCall != null) {
            webCall.fileChose5(filePathCallback);


        }

        return true;
        //return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }

    public interface WebCall {
        void fileChose(ValueCallback<Uri> uploadMsg);

        void fileChose5(ValueCallback<Uri[]> uploadMsg);
    }

}
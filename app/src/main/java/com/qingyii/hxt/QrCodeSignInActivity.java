package com.qingyii.hxt;

import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxt.base.app.EventBusTags;
import com.qingyii.hxt.base.utils.RxLocationUtils;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 扫码签到页面
 */
public class QrCodeSignInActivity extends BaseActivity {
    public static final String TITLE = "title";
    public static final int REQUEST_CODE = 10001;
    private String address;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.train_signin_web)
    WebView train_signin_web;
    @BindView(R.id.train_signin_tv)
    TextView train_signin_tv;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_train_sign_in;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getIntent().hasExtra(TITLE))
            toolbarTitle.setText(getIntent().getStringExtra(TITLE));
        train_signin_web = (WebView) findViewById(R.id.train_signin_web);
        train_signin_tv = (TextView) findViewById(R.id.train_signin_tv);
        //WebView加载
        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));
        //load网页
//        webUrl = XrjHttpClient.getTrainListUrl() + "/" + tDataBean.getId() + "/signin";


        RxLocationUtils.register(this, 0, 10, new RxLocationUtils.OnLocationChangeListener() {

            @Override
            public void getLastKnownLocation(Location location) {
                Address address = RxLocationUtils.getAddress(QrCodeSignInActivity.this, location.getLatitude(), location.getLongitude());
                QrCodeSignInActivity.this.address = address.getAddressLine(0);
                String webUrl = getIntent().getStringExtra("webUrl")+"?address="+address.getAddressLine(0);
                if (!webUrl.startsWith(XrjHttpClient.URL_PR))
                    train_signin_tv.setText("二维码错误");
                else {
                    train_signin_web.loadUrl(webUrl, extraHeaders);
                    Message msg = new Message();
                    msg.what = EventBusTags.ALREAY_SIGN_IN;
                    EventBus.getDefault().post(msg, EventBusTags.SIGN_IN);
                }
            }

            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });

    }

    @OnClick({R.id.toolbar_back_layout, R.id.train_signin_web})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_layout:
                finish();
                break;
            case R.id.train_signin_web:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxLocationUtils.unregister();
    }
}

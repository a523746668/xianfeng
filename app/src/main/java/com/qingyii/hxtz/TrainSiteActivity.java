package com.qingyii.hxtz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.google.gson.Gson;
import com.qingyii.hxtz.base.mvp.model.entity.MyLocations;
import com.qingyii.hxtz.base.utils.RxLocationUtils;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.TrainList;

import java.util.HashMap;
import java.util.Map;

import cn.jiluai.android.framework.Dialog.ActionSheetDialog;
import cn.jiluai.android.framework.Map.CheckInstalled;

/**
 * 地图
 */
public class TrainSiteActivity extends AbBaseActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean;

    private WebView train_site_web;

    private Button navigaterBtn;
    private Context mContext;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private MyLocations locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_site);
        mContext = this;
        intent = getIntent();
        tDataBean = intent.getParcelableExtra("training");


        String lat_lng = tDataBean.getLocation();
        locations = new Gson().fromJson(lat_lng,MyLocations.class);

        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText(intent.getStringExtra("tltle"));
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        navigaterBtn = (Button) findViewById(R.id.navigate);

        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        navigaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ActionSheetDialog(TrainSiteActivity.this).builder()
                        .setTitle("请选择地图")
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("百度地图", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {


                                boolean ifinstalled = checkIfInstall("baidu"); //检查是否安装百度地图

                                if (ifinstalled) {
                                    goBaidu();
                                } else {
                                    new Toast(mContext).makeText(mContext, "百度地图没有安装", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .addSheetItem("高德地图", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                                boolean ifinstalled = checkIfInstall("gaode"); //检查是否安装百度地图

                                if (ifinstalled) {
                                    goGaode();
                                } else {
                                    new Toast(mContext).makeText(mContext, "高德地图没有安装", Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .show();
//                        .addSheetItem("腾讯地图", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
//                            @Override
//                            public void onClick(int which) {
//
//                            }
//
//                        })

            }
        });

        train_site_web = (WebView) findViewById(R.id.train_site_web);

        //WebView加载
        String webUrl = null;


        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));
        //load网页
        if (intent.hasExtra("isMeeting")) {
            webUrl = XrjHttpClient.getMeetingMapUrl() + "/" + tDataBean.getId() + "/map";
        } else {
            webUrl = XrjHttpClient.getTrainListUrl() + "/" + tDataBean.getId() + "/map";
        }
        Log.e("Trainite_webUrl", webUrl);

        train_site_web.getSettings().setSupportZoom(false);
        train_site_web.getSettings().setJavaScriptEnabled(true);
        train_site_web.getSettings().setDomStorageEnabled(true);

        train_site_web.setWebChromeClient(new WebChromeClient() {


            public void onProgressChanged(WebView webView, int progress) {
            }

            public void onReceivedTitle(WebView view, String title) {
            }

        });
        train_site_web.loadUrl(webUrl, extraHeaders);
    }

    private boolean checkIfInstall(String dituname) {

        if (dituname.equals("baidu")) {
            return CheckInstalled.isInstalled(TrainSiteActivity.this, "com.baidu.BaiduMap");
        } else if (dituname.equals("gaode")) {

            return CheckInstalled.isInstalled(TrainSiteActivity.this, "com.autonavi.minimap");
        } else {
            return false;
        }
    }


    private void goBaidu() {
        try {
            Intent i1 = new Intent();
            // 驾车导航
            i1.setData(Uri.parse("baidumap://map/geocoder?location="+ RxLocationUtils.GCJ02ToBD09(locations.getLocation().get(locations.getLocation().size()-1).getLat(),locations.getLocation().get(locations.getLocation().size()-1).getLng())));
            startActivity(i1);
        } catch (Exception e) {
            Log.d("URISyntaxException : ", e.getMessage());
            e.printStackTrace();
        }

    }

    private void goGaode() {
        Intent intent = new Intent("android.intent.action.VIEW",
                Uri.parse("" +
                        "androidamap://viewMap?sourceApplication=先锋云平台&poiname="+
                        locations.getLocation().get(locations.getLocation().size()-1).getText()
                        +"&lat="+
                        locations.getLocation().get(locations.getLocation().size()-1).getLat()
                        +"&lon="+
                        locations.getLocation().get(locations.getLocation().size()-1).getLng()
                        +"&dev=0"));
        intent.setPackage("com.autonavi.minimap");
        startActivity(intent);
    }
}

package com.qingyii.hxtz.httpway;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.Advertisement;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 63264 on 16/9/7.
 */

public class ADUpload {

    private static ADUpload adUpload = new ADUpload();

    private ADUpload() {
    }

    public static ADUpload getADUpload() {
        return adUpload;
    }

    /**
     * 广告
     *
     * @param activity
     * @param handler
     */
    public void adList(final Activity activity, final List<Advertisement.DataBean> aDataBeanlist, final Handler handler) {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getAdvertisementUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new AdvertisementCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Advertisement_onError", e.toString());
                                 Toast.makeText(activity, "网络已断开，请连接网络！", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(Advertisement response, int id) {
                                 Log.e("AdvertisementCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         aDataBeanlist.clear();
                                         aDataBeanlist.addAll(response.getData());
                                         handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class AdvertisementCallback extends Callback<Advertisement> {

        @Override
        public Advertisement parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("首页 JSON", result);
            Advertisement advertisement = new Gson().fromJson(result, Advertisement.class);
            return advertisement;
        }
    }
}

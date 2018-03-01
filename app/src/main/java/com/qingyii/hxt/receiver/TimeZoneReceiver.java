package com.qingyii.hxt.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.YzyHttpClient;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class TimeZoneReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		if (arg1.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)
				|| arg1.getAction().equals(Intent.ACTION_TIME_CHANGED)) {
			JSONObject jsonObject = new JSONObject();
			byte[] bytes = null;
			try {
				bytes = jsonObject.toString().getBytes("utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			YzyHttpClient.post(arg0, HttpUrlConfig.reqSyncTime,
					new ByteArrayEntity(bytes), new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							if (statusCode == 200) {
								try {
									JSONObject obj = new JSONObject(content);
									String string = obj.getString("servertime");
									CacheUtil.timeOffset = System.currentTimeMillis()/1000 - Long.valueOf(string);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							} else {
							}
						}

						@Override
						public void onFailure(int statusCode, Throwable error,
								String content) {
							// System.out.println(content);
						}

						@Override
						public void onFinish() {
						}

					});
		}
	}

}

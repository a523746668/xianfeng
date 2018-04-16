package com.qingyii.hxtz.http;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

//AsyncHttpClient  是一个框架提供的库  可以异步传输，使用时需下载android-async-http-1.4.4.jar包导入到项目中


public class YzyHttpClient {
	private static final String BASE_URL = HttpUrlConfig.BASE_URL;
	//�첽
	private static AsyncHttpClient client = new AsyncHttpClient();
	//ͬ��
	private static SyncHttpClient client2=new SyncHttpClient();
	
	static {  
		//���ó�ʱʱ��
        client.setTimeout(10*60*1000);
//        client.setMaxConnections(50);
    } 
	/**
	 * ͬ������
	 */
   /* public static void post2(Context c, String url, ByteArrayEntity params,
			AsyncHttpResponseHandler responseHandler){
    	client2.p
    }*/
	public static void get(Context c, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		     client.get(getAbsoluteUrl(url), params, responseHandler);
	}
	public static void getUrl(Context c, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		     client.get(url, params, responseHandler);
	}
	public static void post(Context c, String url, StringEntity params,
			AsyncHttpResponseHandler responseHandler) {
		// client.post(getAbsoluteUrl(url), params, responseHandler);
		client.post(c, getAbsoluteUrl(url), params, "application/json",
				responseHandler);
	}
	public static void get(Context c, String url,AsyncHttpResponseHandler responseHandler) {
		client.get(c,url,responseHandler);
	}
	
	//���ϴ��ļ�����
	public static void postRequestParams(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	/**
	 * ���?�������������POST����
	 * 
	 * @param c
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static synchronized void post(Context c, String url, ByteArrayEntity params,
			AsyncHttpResponseHandler responseHandler) {
		client.post(c, getAbsoluteUrl(url), params, "application/json",
				responseHandler);
		/*if(NetworkUtils.isNetConnected(c)){
			client.post(c, getAbsoluteUrl(url), params, "application/json",
					responseHandler);
		}else{
			Toast.makeText(c, "���粻��������������!", Toast.LENGTH_SHORT).show();
		}*/
	}

	// �����ļ���������ֻ�����ض������ļ���ͼƬ��
	public static void postDownFile(Context c, String url,
									ByteArrayEntity params, String contentType, BinaryHttpResponseHandler responseHandler) {
		/*client.post(c, getAbsoluteUrl(url), params, "text/plain; charset=utf-8",
				responseHandler);*/
		client.post(getAbsoluteUrl(url), responseHandler);
	}
	//�����ļ���������:�������������ļ�
	public static void postDownFile2(Context c, String url,
									 ByteArrayEntity params, String contentType, FileAsyncHttpResponseHandler responseHandler) {
		client.post(c, getAbsoluteUrl(url), params, contentType,
				responseHandler);
//		client.post(getAbsoluteUrl(url), responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		Log.i("tmdurl",BASE_URL + relativeUrl);
		return BASE_URL + relativeUrl;

	}
}

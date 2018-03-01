package com.qingyii.hxt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.YzyHttpClient;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class myjifenActivity extends AbBaseActivity {
/**
 * 我的积分
 */
    private ImageView iv_back;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_myjifen);
           getMyjifen();
           initUI();
    }
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
	}
	private void initUI() {
		iv_back=(ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				onBackPressed();
			}
		});

		
	}	
	private void getMyjifen(){
		JSONObject jsObj=new JSONObject();
		try {
			jsObj.put("userid", CacheUtil.userid);
			byte[] bytes;
			ByteArrayEntity entity=null;
			try {
				bytes=jsObj.toString().getBytes("utf-8");
				entity=new ByteArrayEntity(bytes);
				YzyHttpClient.post(myjifenActivity.this, HttpUrlConfig.getMyjifen,entity,
						new AsyncHttpResponseHandler(){
					@Override
					
					public void onSuccess(int statusCode, String content) {
						  super.onSuccess(statusCode, content);
							if(statusCode==499){
								Toast.makeText(myjifenActivity.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
								Intent intent=new Intent(myjifenActivity.this,LoginActivity.class);
								   startActivity(intent);
								   onFinish();
							}else if (statusCode==200) {  
							Gson g=new Gson();
							System.out.println(content);
						  }
					}
                              @Override
							public void onFailure(Throwable error,
									String content) {
					        	 
								super.onFailure(error, content);
								System.out.println(content);
							}
				});
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JSONException e) {
	
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
}  

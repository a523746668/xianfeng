package com.qingyii.hxt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.YzyHttpClient;
import com.qingyii.hxt.pojo.Discuss;
import com.qingyii.hxt.util.EmptyUtil;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 全部评论里添加评论界面
 * @author Administrator
 *
 */

public class xiePinlunActivity2 extends AbBaseActivity {
    private ImageView back_btn;
    private TextView tv_fabu;
    private EditText et_xierizhi;
    private Discuss dinfo;
    private Handler handler;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState); 
        	setContentView(R.layout.activity_xiepinglun);
        	
        	handler=new Handler(new Callback() {
				
				@Override
				public boolean handleMessage(Message msg) {
					if (msg.what==1) {
						//广播通知 :刷新确认订单界面UI
				         Intent intent = new Intent();  
	                     intent.setAction("action.refreshMyAddress");  
				         sendBroadcast(intent);
				         xiePinlunActivity2.this.finish();
				         Toast.makeText(xiePinlunActivity2.this, "添加评论成功", Toast.LENGTH_SHORT).show();
					}else if (msg.what==0) {
						Toast.makeText(xiePinlunActivity2.this, "添加评论失败", Toast.LENGTH_SHORT).show();
					}
					return false;
				}
			});
        	dinfo=(Discuss) getIntent().getSerializableExtra("dinfo");
        	
        	initUI();
        }
        
    	private void initUI() {
    		tv_fabu=(TextView) findViewById(R.id.tv_fabu);
    		et_xierizhi=(EditText) findViewById(R.id.et_xierizhi);
    		back_btn=(ImageView) findViewById(R.id.back_btn);
    		back_btn.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				onBackPressed();
    				
    			}
    		});
    		tv_fabu.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				if (!EmptyUtil.IsNotEmpty(et_xierizhi.getText().toString().trim())) {
    					Toast.makeText(getBaseContext(), "评论内容不能为空!", Toast.LENGTH_SHORT).show();	
    				}else {
    					fbPinglun();
    					
    				}
    		
    				
    			}
    		});
    	}
    	
    	private void fbPinglun(){
    	       JSONObject Obj=new JSONObject();
    	      try {	
    	        Obj.put("content", et_xierizhi.getText().toString());
    	        Obj.put("userid", CacheUtil.userid);
    			Obj.put("parentid",dinfo.getDiscussid());
    			Obj.put("articleid",dinfo.getArticleid());
    		} catch (JSONException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    			byte[] bytes;
    			ByteArrayEntity entity = null;
    			try {
    				bytes = Obj.toString().getBytes("utf-8");
    				entity = new ByteArrayEntity(bytes);
    				YzyHttpClient.post(xiePinlunActivity2.this, HttpUrlConfig.addDiscuss, entity,
    						new AsyncHttpResponseHandler(){
    					     @Override
    					    public void onSuccess(int statusCode, String content) {
    					    	  super.onSuccess(statusCode, content);
  								if(statusCode==499){
									Toast.makeText(xiePinlunActivity2.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
									Intent intent=new Intent(xiePinlunActivity2.this,LoginActivity.class);
									   startActivity(intent);
									   onFinish();
  								}else if (statusCode==200) {
    								try {
    									JSONObject Obj=new JSONObject(content);
    									handler.sendEmptyMessage(1);
    								} catch (JSONException e) {
    									handler.sendEmptyMessage(0);
    									e.printStackTrace();
    								}
    							}
    					    }
    					
    				});
    			} catch (UnsupportedEncodingException e) {
    				handler.sendEmptyMessage(0);
    				e.printStackTrace();
    			}
    		
    		
    		}
}

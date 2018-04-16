package com.qingyii.hxtz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.util.EmptyUtil;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 修改密码
 * @author Administrator
 *
 */
public class ChangePassWordActivity extends AbBaseActivity {
     private EditText et_changeword_old,et_changeword_new;
     private Button bt_change;
     private ImageView iv_back;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_changepsword);
       initUI();
	}
	private void initUI() {
		iv_back=(ImageView) findViewById(R.id.iv_back);
		et_changeword_old=(EditText) findViewById(R.id.et_changeword_old);
		et_changeword_new=(EditText) findViewById(R.id.et_changeword_new);
		bt_change=(Button) findViewById(R.id.bt_change);
		bt_change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!EmptyUtil.IsNotEmpty(et_changeword_old.getText().toString())){
					Toast.makeText(ChangePassWordActivity.this, "输入新密码不能为空!", Toast.LENGTH_SHORT).show();
				}else if(!EmptyUtil.IsNotEmpty(et_changeword_new.getText().toString())){
					Toast.makeText(ChangePassWordActivity.this, "确认新密码不能为空!", Toast.LENGTH_SHORT).show();
				}else{
					if(et_changeword_old.getText().toString().equals(et_changeword_new.getText().toString())){
						//提交修改
						changePassWord();
					}else{
						Toast.makeText(ChangePassWordActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
					}
					
				}
			}
		});
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			     onBackPressed();
				
			}
		});

	}
	/**
	 * 修改密码接口
	 */
	private void changePassWord(){
		JSONObject jsObj=new JSONObject();
		try {
			jsObj.put("userid", CacheUtil.userid);
			jsObj.put("password", et_changeword_new.getText().toString());
			jsObj.put("phone", CacheUtil.user.getPhone());
			byte[] bytes;
			ByteArrayEntity entity=null;
			try {
				bytes=jsObj.toString().getBytes("utf-8");
				entity=new ByteArrayEntity(bytes);
				YzyHttpClient.post(ChangePassWordActivity.this, HttpUrlConfig.changepassWord, entity, new AsyncHttpResponseHandler(){
					  @Override
					public void onSuccess(int statusCode, String content) {
			                super.onSuccess(statusCode, content);
							if(statusCode==499){
								Toast.makeText(ChangePassWordActivity.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
								Intent intent=new Intent(ChangePassWordActivity.this,LoginActivity.class);
								   startActivity(intent);
								   onFinish();
							}else if (statusCode==200) {
								try {
									JSONObject object=new JSONObject(content);
									if("update_success".equals(object.getString("message"))){
										//当密码修改成功后更新保存的密码，防止下次自动登录因密码错误而登录失败
										MyApplication.hxt_setting_config.edit().putString("pwd",et_changeword_new.getText().toString()).commit();
										Toast.makeText(ChangePassWordActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
										ChangePassWordActivity.this.finish();
									}else{
										Toast.makeText(ChangePassWordActivity.this, "修改密码失败，请重新操作", Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
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


















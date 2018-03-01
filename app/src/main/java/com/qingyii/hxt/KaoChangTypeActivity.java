package com.qingyii.hxt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.kang.zbar.CameraTestActivity;

/**
 * 考试分类选择
 * @author shelia
 *
 */
public class KaoChangTypeActivity extends AbBaseActivity {
	private ImageView back_btn,zbar;
	private LinearLayout type_tkcx,type_dcmt,type_dtcg;
	private final static int SCANNIN_GREQUEST_CODE = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kaochang_type);
		initUI();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}
	private void initUI() {
		zbar=(ImageView) findViewById(R.id.zbar);
		zbar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(KaoChangTypeActivity.this,CameraTestActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});
		type_tkcx=(LinearLayout) findViewById(R.id.type_tkcx);
		type_tkcx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(KaoChangTypeActivity.this,kaochangActivity.class);
				intent.putExtra("type", 1);
				startActivity(intent);
			}
		});
		type_dcmt=(LinearLayout) findViewById(R.id.type_dcmt);
		type_dcmt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(KaoChangTypeActivity.this,kaochangActivity.class);
				intent.putExtra("type", 2);
				startActivity(intent);
			}
		});
		type_dtcg=(LinearLayout) findViewById(R.id.type_dtcg);
		type_dtcg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(KaoChangTypeActivity.this,kaochangActivity.class);
				intent.putExtra("type", 5);
				startActivity(intent);
			}
		});
		back_btn=(ImageView) findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == 10){
				Bundle bundle = data.getExtras();
				//显示扫描到的内容
				String pwd=bundle.getString("result");
				Toast.makeText(KaoChangTypeActivity.this, pwd, Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
        super.onActivityResult(requestCode, resultCode, data);
    }
}

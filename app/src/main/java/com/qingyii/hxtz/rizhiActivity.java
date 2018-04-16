package com.qingyii.hxtz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.adapter.rizhiAdapter;
import com.qingyii.hxtz.bean.rizhiInfo;

import java.util.ArrayList;

/**
 * 我的日志界面
 * @author Administrator
 *
 */
public class rizhiActivity extends AbBaseActivity {
	private ImageView back_btn,iv_xierizhi;
	private rizhiAdapter adapter;
	private ListView lv_rizhi;
	private ArrayList<rizhiInfo> list=new ArrayList<rizhiInfo>();
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rizhi);
		initUI();
	}

	private void initUI() {
		iv_xierizhi=(ImageView) findViewById(R.id.iv_xierizhi);
		lv_rizhi=(ListView) findViewById(R.id.lv_rizhi);
		back_btn=(ImageView) findViewById(R.id.back_btn);
//		list.add(new rizhiInfo("2012.12.26", "公司活动今天泡温泉"));
//		list.add(new rizhiInfo("2012.02.12", "天气好"));
		rizhiInfo info1=new rizhiInfo();
		info1.setCreatDate("2012.12.26");
		info1.setRizhi_title("公司活动今天泡温泉");
		rizhiInfo info2=new rizhiInfo();
		info2.setCreatDate("2012.02.12");
		info2.setRizhi_title("天气好");
		list.add(info1);
		list.add(info2);
		adapter=new rizhiAdapter(this, list);
        lv_rizhi.setAdapter(adapter);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				
			}
		});
		iv_xierizhi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it=new Intent(rizhiActivity.this,xierizhiActivity.class);
				startActivity(it);
				
			}
		});
		
	}
}

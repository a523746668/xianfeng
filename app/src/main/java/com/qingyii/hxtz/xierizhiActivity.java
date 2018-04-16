package com.qingyii.hxtz;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.adapter.xierizhiImageAdapter;
import com.qingyii.hxtz.bean.rizhiInfo;
import com.qingyii.hxtz.view.HorizontalListView;

import java.util.ArrayList;

/**
 * 写日志界面
 * @author Administrator
 *
 */
public class xierizhiActivity extends AbBaseActivity {
    private ImageView back_btn,add_rizhi_image;
    private HorizontalListView add_rizhi_image_HorizontalListView;
    private xierizhiImageAdapter adapter;
    private ArrayList<rizhiInfo> list=new ArrayList<rizhiInfo>();
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
    	 setContentView(R.layout.activity_xierizhi);
    	 initUI();
    }
	private void initUI() {
		add_rizhi_image=(ImageView) findViewById(R.id.add_rizhi_image);
		back_btn=(ImageView) findViewById(R.id.back_btn);
		add_rizhi_image_HorizontalListView=(HorizontalListView) findViewById(R.id.add_rizhi_image_HorizontalListView);
		adapter=new xierizhiImageAdapter(xierizhiActivity.this, list);
		rizhiInfo info1=new rizhiInfo();
		info1.setRizhi_photo("drawable://"+ R.mipmap.neikantu1);
		rizhiInfo info2=new rizhiInfo();
		info2.setRizhi_photo("drawable://"+ R.mipmap.neikantu2);
		rizhiInfo info3=new rizhiInfo();
		info3.setRizhi_photo("drawable://"+ R.mipmap.neikantu3);
		rizhiInfo info4=new rizhiInfo();
		info4.setRizhi_photo("drawable://"+ R.mipmap.neikantu3);
		list.add(info1);
		list.add(info2);
		list.add(info3);
		list.add(info4);
		add_rizhi_image_HorizontalListView.setAdapter(adapter);
		add_rizhi_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				
			}
		});
		
	}
}

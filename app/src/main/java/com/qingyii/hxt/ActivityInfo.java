package com.qingyii.hxt;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.adapter.ActivityInfoAdapter;
import com.qingyii.hxt.pojo.ActivityDiscussPic;
import com.zhf.android_viewflow.ViewFlow;
import com.zhf.android_viewflow.ViewFlow.ViewSwitchListener;

import java.util.ArrayList;

/**
 * 活动树图片详情列表UI 
 * @author shelia
 *
 */
public class ActivityInfo extends AbBaseActivity {
	private ViewFlow my_view_flow;
	private ActivityInfoAdapter myAdater;
	private ImageView iv_back;
	/**
	 * 默认选择图片
	 */
	private int selectIndext=0;
	private ArrayList<ActivityDiscussPic> list=new ArrayList<ActivityDiscussPic>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		selectIndext=getIntent().getIntExtra("selectIndext", 0);
		initData();
		initUI();
	}
	private void initData() {
		//静态数据
		for (int i = 0; i < 3; i++) {
			ActivityDiscussPic adp=new ActivityDiscussPic();
			adp.setContent("动态照片"+i+"动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片动态照片9999照片动态照片动态照片照片动态照片动态照片照片动态照片动态照片照片动态照片动态照片照片动态照片动态照片照片动态照片动态照片照片动态照片动态照片");
			adp.setPicaddress("drawable://"+ R.mipmap.dongtaidu1);
			list.add(adp);
		}
		
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
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		my_view_flow=(ViewFlow) findViewById(R.id.my_view_flow);
		myAdater=new ActivityInfoAdapter(ActivityInfo.this, list);
		my_view_flow.setAdapter(myAdater,selectIndext);
		my_view_flow.setOnViewSwitchListener(new ViewSwitchListener() {
			
			@Override
			public void onSwitched(View view, int position) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}

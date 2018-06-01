package com.qingyii.hxtz;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.adapter.viewFlorAdapter;
import com.qingyii.hxtz.bean.huodongInfo;
import com.qingyii.hxtz.bean.researchInfo;
import com.qingyii.hxtz.zhf.android_viewflow.ViewFlow;

import java.util.ArrayList;

//import com.ab.view.sliding.AbSlidingTabView;

public class researchActivity extends AbBaseActivity {
	private ImageView iv_back;
    private TextView tv_title;
    private huodongInfo huodong;
    private ViewFlow VF_research;
    private viewFlorAdapter adapter;
    private ArrayList<researchInfo> list=new ArrayList<researchInfo>();
//	private AbSlidingTabView abst_research;
    private huodongInfo huodonginfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_research);
		huodonginfo=(huodongInfo) getIntent().getSerializableExtra("huodongInfo");
		initUI();
		
	}

	private void initUI() {
		VF_research=(ViewFlow) findViewById(R.id.VF_research);
		iv_back = (ImageView) findViewById(R.id.iv_back_research);
		tv_title = (TextView) findViewById(R.id.title_research);
		tv_title.setText(huodonginfo.getTitle());
		researchInfo info=new researchInfo();
		info.setReserchTitle("刘备和曹操谁更适做国足教练？");
		researchInfo info1=new researchInfo();
		info1.setReserchTitle("今天星期几 ？");
		list.add(info);
		list.add(info1);
		adapter=new viewFlorAdapter(researchActivity.this, list);
		VF_research.setAdapter(adapter);
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});
	}
}

package com.qingyii.hxtz;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.adapter.xinxiangAdapter;
import com.qingyii.hxtz.bean.xinxiangInfo;

import java.util.ArrayList;
import java.util.List;

public class myxinxiangActivity extends AbBaseActivity {
      /**
       * 我的信箱
       */
	private xinxiangAdapter adapter;
       private List<xinxiangInfo> list=new ArrayList<xinxiangInfo>();
       private ListView mListView;	
       private ImageView iv_back;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myxinxiang);
        initUI();
    }

	private void initUI() {
		iv_back=(ImageView) findViewById(R.id.iv_back);
		mListView=(ListView) findViewById(R.id.mListView);
		list.add(new xinxiangInfo("Eason", "要放假啦要放假啦要放假啦", "1"));
		list.add(new xinxiangInfo("Jarvan", "要上班啦要上班啦要上班啦", "2"));
		list.add(new xinxiangInfo("Tom", "今天天气真好", "2"));
		adapter=new xinxiangAdapter(this,list);
		mListView.setAdapter(adapter);
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    onBackPressed();
				
			}
		});
	}
}

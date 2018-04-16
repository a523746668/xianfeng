package com.qingyii.hxtz;

import android.os.Bundle;
import android.view.View;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.adapter.GuideViewflowAdapter;
import com.zhf.android_viewflow.ViewFlow;
import com.zhf.android_viewflow.ViewFlow.ViewSwitchListener;

/**
 * 引导界面
 * @author shelia
 *
 */
public class GuideActivity extends AbBaseActivity {
	private ViewFlow guide_viewflow;
	private GuideViewflowAdapter myAdater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		guide_viewflow=(ViewFlow) findViewById(R.id.guide_viewflow);
		int[] imgIds={R.mipmap.guide_01, R.mipmap.guide_02, R.mipmap.guide_03, R.mipmap.guide_04};
		myAdater=new GuideViewflowAdapter(this, imgIds);
		guide_viewflow.setAdapter(myAdater,0);
		guide_viewflow.setOnViewSwitchListener(new ViewSwitchListener() {
			
			@Override
			public void onSwitched(View view, int position) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}

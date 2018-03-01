package com.qingyii.hxt;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.andbase.library.app.base.AbBaseActivity;

/**
 * 我的时光
 * @author Administrator
 *
 */
public class myTimeActivity extends AbBaseActivity {
    private ImageView iv_back;   
	@Override
    protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_mytime);
           initUI();
    }
	private void initUI() {
		iv_back=(ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 onBackPressed();
				
			}
		});
		
	}
}

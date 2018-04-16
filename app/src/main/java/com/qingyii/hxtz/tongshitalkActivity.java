package com.qingyii.hxtz;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.andbase.library.app.base.AbBaseActivity;

public class tongshitalkActivity extends AbBaseActivity {
    private ImageView back_btn;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tongshitalk);
    initUI();
    }
	private void initUI() {
		back_btn=(ImageView) findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  onBackPressed();
				 
			}
		});
		
	}
}

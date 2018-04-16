package com.qingyii.hxtz;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.kang.zbar.CameraTestActivity;
import com.qingyii.hxtz.fragment.DancimingtiFragment;
import com.qingyii.hxtz.fragment.Datichuangguanfragment;

import java.util.ArrayList;
import java.util.List;

//import com.ab.view.sliding.AbSlidingTabView;

public class KaoChangTypesActivity extends AbBaseActivity {
	private int type=0;
	private ImageView back_btn,zbar;
	/**
	 * AbSlidingTabView失效，注释相关
	 */
//	private AbSlidingTabView kctypeSlidingTabView;
	private final static int SCANNIN_GREQUEST_CODE = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kaochang_types);
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
				Intent intent=new Intent(KaoChangTypesActivity.this,CameraTestActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
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
		
//		kctypeSlidingTabView = (AbSlidingTabView) findViewById(kctypeSlidingTabView);
		
		DancimingtiFragment page1 = new DancimingtiFragment();
		page1.mytype = 2;
		Datichuangguanfragment page2 = new Datichuangguanfragment();
		page2.mytype = 5;
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(page1);
		fragments.add(page2);
		
		List<String> tabTexts = new ArrayList<String>();
		tabTexts.add("单次命题");
		tabTexts.add("答题闯关");
		//设置样式
//		kctypeSlidingTabView.setTabTextColor(Color.BLACK);
//		kctypeSlidingTabView.setTabSelectColor(Color.rgb(30, 168, 131));
//		kctypeSlidingTabView.setTabBackgroundResource(R.drawable.tab_bg);
//		kctypeSlidingTabView.setTabLayoutBackgroundResource(R.drawable.slide_top);
//		kctypeSlidingTabView.addItemViews(tabTexts, fragments);
		
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == 10){
				Bundle bundle = data.getExtras();
				//显示扫描到的内容
				String pwd=bundle.getString("result");
				Toast.makeText(KaoChangTypesActivity.this, pwd, Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
        super.onActivityResult(requestCode, resultCode, data);
    }
	

}

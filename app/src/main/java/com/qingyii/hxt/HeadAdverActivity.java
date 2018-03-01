package com.qingyii.hxt;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.pojo.Advert;

//import com.ab.activity.AbActivity;

public class HeadAdverActivity extends AbBaseActivity {

	private WebView webView;
	private WebSettings webSettings;
	private Advert advert;
	private ImageView headadver_back_iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.headadver);

		advert = (Advert) getIntent().getSerializableExtra("advert");

		initUI();

	}

	/**
	 * 初始化控件
	 */
	private void initUI() {

		webView = (WebView) findViewById(R.id.headadver_webview_wb);
		/**
		 * 设置WebView属性，能够执行Javascript脚本
		 */
		webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		/**
		 * 适应屏幕，内容将自动缩放
		 */
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// webView.loadDataWithBaseURL(null, advert.getLinkaddress(),
		// "text/html", "utf-8", null);
		webView.loadUrl(advert.getLinkaddress());

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub

				view.loadUrl(url);

				return true;

			}
		});

		/**
		 * 返回
		 */
		headadver_back_iv = (ImageView) findViewById(R.id.headadver_back_iv);
		headadver_back_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				onBackPressed();
//				Intent intent = new Intent(HeadAdverActivity.this,
//						HomeActivity.class);
//				startActivity(intent);
//				finish();
			}
		});

	}
	
	public void onBackPressed() {
	    super.onBackPressed();
		this.finish();
	}

}

package com.qingyii.hxtz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.qingyii.hxtz.adapter.dongtaiAdapter;
import com.qingyii.hxtz.bean.dongtaiInfo;

import java.util.ArrayList;

//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

/**
 * 动态activity
 * @author Administrator
 *
 */
public class dongtaiActivity extends AbBaseActivity {
	private dongtaiAdapter adapter;
	private ArrayList<dongtaiInfo> list = new ArrayList<dongtaiInfo>();
	private ImageView iv_back;
	private Handler handler;
	private ListView lv_dongtai;

	private AbPullToRefreshView mAbPullToRefreshView;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dongtai);
		handler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				
				return false;
			}
		});
		initUI();
//		refreshTask();
	}

	private void initUI() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();

			}
		});
		lv_dongtai = (ListView) findViewById(R.id.lv_dongtai);
		/**
		 * 加载静态数据
		 */
        dongtaiInfo dongtai1 = new dongtaiInfo("张帆", "上传了一本书《简爱》。");
		dongtai1.setPhoto("drawable://"+ R.mipmap.haimianbb);
		dongtaiInfo dongtai2 = new dongtaiInfo("李帆", "参与答题，获得80分。");
		dongtai2.setPhoto("drawable://"+ R.mipmap.dashitouxiang);
		list.add(dongtai1);
		list.add(dongtai2);
		adapter = new dongtaiAdapter(dongtaiActivity.this,list);
		lv_dongtai.setAdapter(adapter);
		mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPull_dongtai);
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				        refreshTask();
			}
		});
		mAbPullToRefreshView
				.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

					@Override
					public void onFooterLoad(AbPullToRefreshView view) {
						loadMoreTask();

					}
				});

		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
}

	private void refreshTask() {
		mAbPullToRefreshView.onHeaderRefreshFinish();

	}

	protected void loadMoreTask() {
		mAbPullToRefreshView.onFooterLoadFinish();
	}
}
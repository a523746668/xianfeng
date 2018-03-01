package com.qingyii.hxt;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxt.adapter.neiKanInfoAdapter;
import com.qingyii.hxt.bean.ontherPinglunInfo;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.YzyHttpClient;
import com.qingyii.hxt.pojo.Discuss;
import com.qingyii.hxt.pojo.PeriodsArticleRela;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

/**
 * 更多评论acvitity
 * @author Administrator
 *
 */
public class allPinglunActivity extends AbBaseActivity {
	private ListView mListView;
	private ArrayList<Discuss> list = new ArrayList<Discuss>();
	private ArrayList<ontherPinglunInfo> ontherPinlunList=new ArrayList<ontherPinglunInfo>();
	private neiKanInfoAdapter adapter;
	private ImageView iv_back_kaoshirank;
	private PeriodsArticleRela rela;
	private AbPullToRefreshView mAbPullToRefreshView = null;
	private int page = 1;
	private int pagesize = 10;
	private Handler handler;
	// 1上拉刷新 2下拉加载
	int type = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allpinglun);
		handler = new Handler(new Callback() {

			@SuppressLint("NewApi")
			@Override
			public boolean handleMessage(Message msg) {
				if (msg.what == 1) {
					adapter.notifyDataSetChanged();
				} else if (msg.what == 2) {
					Toast.makeText(allPinglunActivity.this, "已是最新数据", Toast.LENGTH_SHORT).show();
				}
				// 刷新，加载更新提示隐藏
				mAbPullToRefreshView.onHeaderRefreshFinish();
				mAbPullToRefreshView.onFooterLoadFinish();
				return false;
			}
		});
		
		/**
		 * 注册广播事件
		 */
		IntentFilter filter=new IntentFilter();
		filter.addAction("action.refreshMyAddress");
		registerReceiver(broadcastReceiver, filter);
		
		
		rela = (PeriodsArticleRela) getIntent().getSerializableExtra("PeriodsArticleRela");
		initData();
		initUI();
	}
	

	/**
	 * 广播
	 */
	private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			if(("action.refreshMyAddress").equals(action)){
				list.clear();
				pinglunList(1, pagesize);
			}
			
		}
	};
	
	
	
	
	
	
	
	
	private void initData() {
		pinglunList(1, pagesize);
	}

	private void initUI() {
		mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
		iv_back_kaoshirank = (ImageView) findViewById(R.id.iv_back_kaoshirank);
		mListView = (ListView) findViewById(R.id.mListView);

		mAbPullToRefreshView
				.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

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
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		adapter = new neiKanInfoAdapter(allPinglunActivity.this, list);
		mListView.setAdapter(adapter);
		iv_back_kaoshirank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();

			}
		});

	}
	

	/**
	 * 获取评论列表
	 */
	private void pinglunList(int mypage, int mypagesize) {
		JSONObject JSOBJ = new JSONObject();
		try {
			JSOBJ.put("articleid", rela.getArticleid());
			JSOBJ.put("page", mypage);
			JSOBJ.put("pagesize", mypagesize);
			byte[] bytes;
			ByteArrayEntity entity = null;
			// 处理保存数据中文乱码问题
			try {
				bytes = JSOBJ.toString().getBytes("utf-8");
				entity = new ByteArrayEntity(bytes);
				YzyHttpClient.post(allPinglunActivity.this,
						HttpUrlConfig.pinglunshu, entity,
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode, String content) {
								super.onSuccess(statusCode, content);
								if(statusCode==499){
									Toast.makeText(allPinglunActivity.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
									Intent intent=new Intent(allPinglunActivity.this,LoginActivity.class);
									   startActivity(intent);
									   onFinish();
								}else if (statusCode == 200) {
									Gson gson = new Gson();
									try {
										if (type == 1) {
											list.clear();
											page = 2;
										}
										JSONObject Obj = new JSONObject(content);
										JSONArray mlist = Obj.getJSONArray("rows");
										if (mlist.length() <= 0) {
											handler.sendEmptyMessage(2);
										} else {
											//如果获取到新数据 页码加一
											if (type==2) {
												page+=1;
											}

											for (int i = 0; i < mlist.length(); i++) {
												Discuss d=gson.fromJson(mlist.getString(i), Discuss.class);

			                                    list.add(d);
											}
											handler.sendEmptyMessage(1);
										}
									} catch (JSONException e) {

										e.printStackTrace();
									}
								}
							}
						});
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JSONException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void refreshTask() {
		type = 1;
		pinglunList(1, pagesize);

	}

	protected void loadMoreTask() {
		type = 2;
		pinglunList(page, pagesize);
	}
}

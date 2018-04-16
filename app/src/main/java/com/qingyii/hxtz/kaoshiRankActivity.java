package com.qingyii.hxtz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxtz.adapter.ExamRankListAdapter;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.pojo.ExamRank;
import com.qingyii.hxtz.pojo.Examination;
import com.qingyii.hxtz.util.EmptyUtil;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

public class kaoshiRankActivity extends AbBaseActivity {
	private ListView lv_rank;
	private ImageView iv_back;
	private List<ExamRank.DataBean> myList=new ArrayList<>();
	private Examination examination;
	private AbPullToRefreshView mAbPullToRefreshView;
	private ExamRankListAdapter mAdapter;
	private TextView rank_kaoshirank_item,name_kaoshirank_item,job_kaoshirank_item,score_kaoshirank_item;
	private int page = 1;
	private int pagesize=10;
	// 列表操作类型：1表示下拉刷新，2表示上拉加载更多
	int type = 1;
	private Handler handler;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kaoshi_rank);
		examination= getIntent().getParcelableExtra("examination");
		handler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					mAdapter.notifyDataSetChanged();
					break;
				case 0:
					 Toast.makeText(kaoshiRankActivity.this, "数据获取异常！", Toast.LENGTH_SHORT).show();
					break;
				case 5:
					Toast.makeText(kaoshiRankActivity.this, "已是最新数据！", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
				if(mAbPullToRefreshView!=null){
					mAbPullToRefreshView.onHeaderRefreshFinish();
					mAbPullToRefreshView.onFooterLoadFinish();
				}
				return true;
			}
		});
		initData();
//		refreshTest();
		initUI();
	}

	private void initData() {
		queryScoreList(page,pagesize);
	}
	
	/**
	 * 获取考分列表
	 */
	private void queryScoreList(int mypage,int mypagesize) {
		JSONObject jsonObject = new JSONObject();
		try {
//			JSONObject obj=new JSONObject("{}");
			jsonObject.put("page", mypage);
			jsonObject.put("pagesize", mypagesize);
			jsonObject.put("examinationid", examination.getExaminationid());
			byte[] bytes;
			ByteArrayEntity entity = null;
			try {
				// stringEntity = new StringEntity(jsonObject.toString());
				// 处理保存数据中文乱码问题
				bytes = jsonObject.toString().getBytes("utf-8");
				entity = new ByteArrayEntity(bytes);
				YzyHttpClient.post(this, HttpUrlConfig.queryScoreList, entity,
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int statecode, String arg0) {
								// TODO Auto-generated method stub
								if(statecode==499){
									Toast.makeText(kaoshiRankActivity.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
									Intent intent=new Intent(kaoshiRankActivity.this,LoginActivity.class);
									   startActivity(intent);
									   onFinish();
								}else if (statecode == 200) {
									Gson gson=new Gson();
									try {
										if (type == 1) {
											myList.clear();
											page = 2;
										}
										JSONObject js = new JSONObject(arg0);
										if(EmptyUtil.IsNotEmpty(js.getString("rows"))){
											JSONArray lists = js.getJSONArray("rows");
											if (lists.length() <= 0) {
												handler.sendEmptyMessage(5);
											} else {
												// 如果获取到更新数据，页码加1
												if (type == 2) {
													page += 1;
												}
												
												for (int i = 0; i < lists.length(); i++) {
													ExamRank.DataBean b=gson.fromJson(lists.getString(i), ExamRank.DataBean.class);
													myList.add(b);
												}
												
												// 服务器正确获取数据更新数据源
												handler.sendEmptyMessage(1);
											}
										}
									
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										handler.sendEmptyMessage(0);
										e.printStackTrace();
									}
								}

							}

							@Override
							public void onFailure(int statusCode,
									Throwable error, String content) {
								// TODO Auto-generated method stub
								super.onFailure(statusCode, error, content);
								handler.sendEmptyMessage(0);
							}

							@Override
							public void onFinish() {
								// TODO Auto-generated method stub
								super.onFinish();
							}

						});
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				handler.sendEmptyMessage(0);
				e1.printStackTrace();
			}
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			handler.sendEmptyMessage(0);
		}
	}

	private void initUI() {
		
//		rank_kaoshirank_item=(TextView) findViewById(R.id.rank_kaoshirank_item);
//		name_kaoshirank_item=(TextView) findViewById(R.id.name_kaoshirank_item);
//		job_kaoshirank_item=(TextView) findViewById(R.id.job_kaoshirank_item);
		score_kaoshirank_item=(TextView) findViewById(R.id.score_kaoshirank_item);
//		rank_kaoshirank_item.setVisibility(NotifyListView.GONE);
		
		if(examination!=null){
			if(EmptyUtil.IsNotEmpty(examination.getExamtype())){
				if("2".equals(examination.getExamtype())){
					score_kaoshirank_item.setText("分数");
				}else if("3".equals(examination.getExamtype())||"4".equals(examination.getExamtype())){
					score_kaoshirank_item.setText("分数");
				}
			}
		}
		
		
		
		title=(TextView) findViewById(R.id.title);
		if(examination!=null){
			title.setText(examination.getExaminationname()+"排行");
		}
		lv_rank = (ListView) findViewById(R.id.lv_kaoshirank);
		iv_back = (ImageView) findViewById(R.id.iv_back_kaoshirank);
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		mAdapter = new ExamRankListAdapter(kaoshiRankActivity.this, myList);
		lv_rank.setAdapter(mAdapter);
		mAbPullToRefreshView = (AbPullToRefreshView)findViewById(R.id.mPull_kaoshirank);
        //设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
			
			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
//				refreshTest();
				type=1;
				page=1;
				queryScoreList(page,pagesize);
			}
		});
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
			
			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				type=2;
				queryScoreList(page,pagesize);
			}
		});
       
        //设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        
	}
//	private void refreshTest(){
//		list.add(new ExamRankInfo("张三", 1, 99));
//		list.add(new ExamRankInfo("张四", 2, 98));
//		list.add(new ExamRankInfo("张五", 3, 97));
//		list.add(new ExamRankInfo("张六", 4, 96));
//		list.add(new ExamRankInfo("张七", 5, 95));
//		list.add(new ExamRankInfo("张八", 6, 94));
//		handler.sendEmptyMessage(1);
//	}
	
	
}

package com.qingyii.hxtz.myscfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxtz.LoginActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.adapter.KanwuAdapter;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.neiKanInfoActivity;
import com.qingyii.hxtz.pojo.PeriodsArticleRela;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import com.ab.db.MyDBHelper;
//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

@SuppressLint("NewApi")
public class kanwuFragment extends Fragment{
	
	private ListView mlistview;
	private AbPullToRefreshView mPullRefreshView;
	private KanwuAdapter kwadpter;
	private   ArrayList<PeriodsArticleRela> list = new ArrayList<PeriodsArticleRela>();
	private int page=1;
	private int pagersize=10;
	// 列表操作类型：1表示下拉刷新，2表示上拉加载更多
	private int type =1;
	private Activity mActivity;
	private Handler handler;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		handler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				
				if (msg.what==101) {

				}else if(msg.what==1){
					Toast.makeText(mActivity, "数据异常", Toast.LENGTH_SHORT).show();
				}else if(msg.what==111){
					Toast.makeText(mActivity, "已是最新数据", Toast.LENGTH_SHORT).show();
				}
				if(mPullRefreshView!= null){
					//刷新，加载更新提示隐藏
					mPullRefreshView.onHeaderRefreshFinish();
					mPullRefreshView.onFooterLoadFinish();
				}
				return false;
			}
		});
				
		mActivity=this.getActivity();
		View view = inflater.inflate(R.layout.ggg, null);
		mPullRefreshView = (AbPullToRefreshView) view.findViewById(R.id.mPullRefreshView);
		
		mlistview = (ListView) view.findViewById(R.id.mListView);
		
     
		kwadpter = new KanwuAdapter(mActivity, list);
		mlistview.setAdapter(kwadpter);
		
		
		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent =new Intent(mActivity,neiKanInfoActivity.class);
				intent.putExtra("periodsrela", list.get(position));
				startActivity(intent);
			}
		});
		
		
		mPullRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
				
				@Override
				public void onHeaderRefresh(AbPullToRefreshView view) {
					refreshTask();
					
				}

				
			});
			//上拉加载
		mPullRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
				
				@Override
				public void onFooterLoad(AbPullToRefreshView view) {
					loadMoreTask();
					
				}

				
			});

			// 设置进度条的样式
			mPullRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
			mPullRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getkanwuinfoDate(1, pagersize);
	}
	private void getkanwuinfoDate(int mypager, int pagersize) {
		JSONObject object = new JSONObject();	

		try {
			object.put("page", mypager);
			object.put("pagesize", pagersize);
			object.put("userid", CacheUtil.userid);
			object.put("flag", 3);
			//接口定义固定传参
			object.put("articleid", 0);
			byte[] bytes;
			ByteArrayEntity entity = null;
			// 处理保存数据中文乱码问题
			bytes = object.toString().getBytes("utf-8");
			entity = new ByteArrayEntity(bytes);
			YzyHttpClient.post(getActivity(), HttpUrlConfig.shoucList, entity, new AsyncHttpResponseHandler(){
				    @Override
				    public void onSuccess(int statusCode, String content) {
				    	
				    	super.onSuccess(statusCode, content);
						if(statusCode==499){
							Toast.makeText(mActivity, CacheUtil.logout, Toast.LENGTH_SHORT).show();
							Intent intent=new Intent(mActivity,LoginActivity.class);
							   startActivity(intent);
							   onFinish();
						}else if(statusCode ==200){
				    	     Gson gson = new Gson();
				    	   try {
								if (type == 1) {
									list.clear();
									page = 2;
								}
							JSONObject Obj=new JSONObject(content);
							JSONArray mlist=Obj.getJSONArray("rows");
							if(mlist.length()<=0){
								handler.sendEmptyMessage(111);
							}else{
								if(type==2){
									page+=1;
								}
								
							
							for (int i = 0; i < mlist.length(); i++) {
								PeriodsArticleRela info=gson.fromJson(mlist.getString(i), PeriodsArticleRela.class);
								list.add(info);
							}
//							Toast.makeText(mActivity, "成功", 0).show();
							kwadpter.notifyDataSetChanged();				
						
							}
						} catch (JSONException e) {
							
							e.printStackTrace();
						}  
				    	   
				    	    handler.sendEmptyMessage(0);
				    	}
				    }
				    @Override
				    public void onFailure(Throwable error, String content) {				    
				    super.onFailure(error, content);
				    }
				                               
				
				
			});
			
			
		} catch (Exception e) {
			handler.sendEmptyMessage(1);
			e.printStackTrace();
		}
		
	}


	private void refreshTask() {
		type=1;
		getkanwuinfoDate(1, pagersize);
		
	}
	
	
	
	
	private void loadMoreTask() {
		type=2;
		getkanwuinfoDate(page, pagersize);
	}

}

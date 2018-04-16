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
import com.qingyii.hxtz.adapter.MyshoucBookAdapter;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.pojo.Book;
import com.qingyii.hxtz.shujixiangqingActivity;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

@SuppressLint("NewApi")
public class MyshoucBooksFragment extends Fragment{
	private Activity activity;
	private AbPullToRefreshView myshoucfreshView;
	private ListView bookListView;    
	private ArrayList<Book> list= new ArrayList<Book>();
	private int page=1;
	private int pagersize=10;
	int type=1;
	private Handler handler;
	private MyshoucBookAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		handler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				if(msg.what==1){
//				Toast.makeText(activity, "已是最新数据", 0).show();
			}
				if(msg.what==2){
					adapter.notifyDataSetChanged();
				}
				if(myshoucfreshView!=null){
					//刷新，加载更新提示隐藏
					myshoucfreshView.onHeaderRefreshFinish();
					myshoucfreshView.onFooterLoadFinish();
				}
				return false;
			}
		});
	
		View view = inflater.inflate( R.layout.myshoucbook, null);
		myshoucfreshView = (AbPullToRefreshView) view.findViewById(R.id.myshoucfreshView);
		bookListView = (ListView) view.findViewById(R.id.bookListView);
		
		activity = this.getActivity();
		
		adapter = new MyshoucBookAdapter(activity, list);
	
		bookListView.setAdapter(adapter);
		
		bookListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(activity,shujixiangqingActivity.class);
				intent.putExtra("Book", list.get(position));
				startActivity(intent);
			}
		});
		
		
		
		myshoucfreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
			
			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				refreshTask();
				
			}		
		});
		//上拉加载
		myshoucfreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
			
			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				loadMoreTask();
				
			}

			
		});

		// 设置进度条的样式
		myshoucfreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		myshoucfreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		return view;
	}
		
	
	private void getshoucBoojDate(int mypager, int pagersize) {
		
		JSONObject object = new JSONObject();
		try {
			object.put("page", mypager);
			object.put("pagesize", pagersize);
			object.put("userid", CacheUtil.userid);
			object.put("flag", 2);
			//接口定义固定传参
			object.put("bookid", 0);
			byte[] bytes = object.toString().getBytes("utf-8");
			ByteArrayEntity entity =null;
			entity= new ByteArrayEntity(bytes);
			YzyHttpClient.post(activity, HttpUrlConfig.shoucList, entity, new AsyncHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, String content) {
//					super.onSuccess(statusCode, content);
					if(statusCode==499){
						Toast.makeText(activity, CacheUtil.logout, Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(activity,LoginActivity.class);
						   startActivity(intent);
						   onFinish();
					}else if(statusCode==200){
						Gson gson = new Gson();
						if(type==1){
							list.clear();
							page=2;
						}
						try {
							JSONObject js = new JSONObject(content);
							JSONArray lists = js.getJSONArray("rows");
							if(lists.length()<=0){
								handler.sendEmptyMessage(1);
							}else{
								//如果获取到数据页码加一
								if(type==2){
									page+=1;
								}
								for (int i = 0; i < lists.length(); i++) {
									Book bookinfo =gson.fromJson(lists.getString(i), Book.class);
									list.add(bookinfo);
								}
								// 服务器正确获取数据更新数据源
								handler.sendEmptyMessage(2);
							}
						
						} catch (JSONException e) {
							
							e.printStackTrace();
						}
					}
					
					
				}
				
				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
				}
				@Override
				public void onFinish() {
					super.onFinish();
				}
			});
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
//	@Override
//	public void onStart() {
//		super.onStart();
//		type=1;
//		page=1;
//		//获取收藏书籍数据
//		getshoucBoojDate(page,pagersize);	
//	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		type=1;
		page=1;
		//获取收藏书籍数据
		getshoucBoojDate(page,pagersize);	
	}
	private void refreshTask() {
		type=1;
		getshoucBoojDate(1, pagersize);
	}
	private void loadMoreTask() {
		type=2;
		getshoucBoojDate(page, pagersize);
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshTask();		
	}
	
	
	
	
}

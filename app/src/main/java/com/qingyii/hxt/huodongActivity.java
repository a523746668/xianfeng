package com.qingyii.hxt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.qingyii.hxt.adapter.huodongAdapter;
import com.qingyii.hxt.bean.huodongInfo;

import java.util.ArrayList;

//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

/**
 * 活动界面
 * @author Administrator
 *
 */
public class huodongActivity extends AbBaseActivity {
	 private ArrayList<huodongInfo> list = new ArrayList<huodongInfo>();
	 private ListView lv_huodong;
	 private huodongAdapter adapter;
	 private ImageView iv_back;
	 private Handler handler;
	 private AbPullToRefreshView mAbPullToRefreshView;
	 private int currentPage = 1;
	 private int pageSize = 5;
	 
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_huodong);
    	handler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter = new huodongAdapter(huodongActivity.this, list);
					lv_huodong.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					break;
                case 102:
					
					break;

				default:
					break;
				}
				// TODO Auto-generated method stub
				return false;
			}
		});
    	initUI();
    }
	private void initUI() {
		iv_back=(ImageView) findViewById(R.id.iv_back);
		jiashuju();
		adapter = new huodongAdapter(huodongActivity.this, list);
		lv_huodong = (ListView) findViewById(R.id.lv_huodong_item);
		lv_huodong.setAdapter(adapter);
		lv_huodong.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if (list.get(position).getLeixing().equals("调查问卷")){
					Intent it = new Intent(huodongActivity.this,researchActivity.class);
					it.putExtra("huodongInfo", list.get(position));
					startActivity(it);
				} else if (list.get(position).getLeixing().equals("意见征集")){
					Intent it = new Intent(huodongActivity.this,SuggestionCollectionActivity.class);
					it.putExtra("huodongInfo", list.get(position));
					startActivity(it);
				} else if (list.get(position).getLeixing().equals("辩论赛")){
					
				}
			}
		});
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				onBackPressed();
			}
		});
		initPullToRefresh();
	}
	private void jiashuju() {
		//静态数据
//				huodongInfo huodong1 = new huodongInfo("1", null, "关于文明用餐的调查", "调查问卷", "2015.4.10", "11");
//				huodongInfo huodong2 = new huodongInfo("2", null, "关于组织活动意见征集", "意见征集", "2015.5.1", "51组织什么活动？");
		        huodongInfo info=new huodongInfo();
		        info.setId("1");
		        info.setTitle("关于文明用餐的调查");
		        info.setLeixing("调查问卷");
		        info.setEnd_time("2015.1.10");
		        huodongInfo info1=new huodongInfo();
		        info1.setId("2");
		        info1.setTitle("关于组织活动意见征集");
		        info1.setLeixing("意见征集");
		        info1.setEnd_time("2015.1.10");
				list.add(info);
				list.add(info1);
	}
	private void initPullToRefresh() {
        mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPull_huodong);
        //设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
			
			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
//				refreshTask();
				mAbPullToRefreshView.onHeaderRefreshFinish();
			}
		});
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
			
			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
//				loadMoreTask();
				mAbPullToRefreshView.onFooterLoadFinish();
			}
		});
       
        //设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        
	}
	protected void loadMoreTask() {}
	protected void refreshTask() {}
}

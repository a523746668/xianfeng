package com.qingyii.hxt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.qingyii.hxt.adapter.huodongshuAdapter;
import com.qingyii.hxt.bean.huodongshuInfo;

import java.util.ArrayList;
import java.util.List;

//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

/**
 * 活动数界面
 * @author Administrator
 *
 */
public class huodongshuActivity extends AbBaseActivity {
    private ListView lv_huodongshu;
	private ImageView iv_back;
    private huodongshuAdapter adapter;
    private List<huodongshuInfo> list=new ArrayList<huodongshuInfo>();
    private int currentPage = 1;
    private int pageSize = 2;
    private Handler handler;
    private AbPullToRefreshView mAbPullToRefreshView;
    private int screenW;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_huodongshu);
             handler = new Handler(){
     			@Override
     			public void handleMessage(Message msg) {
     				switch (msg.what) {
     				case 1:
     					
     					break;

     				default:
     					break;
     				}
     				super.handleMessage(msg);
     			}
     		};
             getScreenWindth();
             initUI();
    }

	private void getScreenWindth() {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenW = metrics.widthPixels;
	}

	private void initUI() {
        lv_huodongshu=(ListView) findViewById(R.id.lv_huodongshu_list);
        /**
         * 加载静态数据
         */
        huodongshuInfo huodongshu1 = new huodongshuInfo("泡温泉", "2015.03.24");
        String [] icon = {"drawable://"+ R.mipmap.dongtaidu1,"drawable://"+ R.mipmap.dongtaidu1,"drawable://"+ R.mipmap.dongtaidu1};
        huodongshu1.setImgUrls(icon);
        huodongshuInfo huodongshu2 = new huodongshuInfo("篮球比赛", "2015.03.24");
        huodongshu2.setImgUrls(icon);
        list.add(huodongshu1);
        list.add(huodongshu2);
		iv_back=(ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				
			}
		});
		adapter = new huodongshuAdapter(this, list, screenW);
		lv_huodongshu.setAdapter(adapter);
        mAbPullToRefreshView = (AbPullToRefreshView)findViewById(R.id.mPull_huodongshu);
        //设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
			
			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
//				refreshTask();
			}
		});
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
			
			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
//				loadMoreTask();
				
			}
		});
       
        //设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        
	} 
	
}

package com.qingyii.hxtz.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.adapter.zongjifenAdapter;
import com.qingyii.hxtz.bean.myjifenInfo;
import com.qingyii.hxtz.http.MyApplication;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class zongjifenFragment extends Fragment {   
	private Activity mActivity = null;
	private List<myjifenInfo> list;
//	private AbPullToRefreshView mAbPullToRefreshView = null;
	private ListView mListView = null;
	private TextView tv_fenshu;
	private myjifenInfo jifeninfo;
	private zongjifenAdapter adapter;
	private MyApplication application;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mActivity=this.getActivity();
        application = (MyApplication) mActivity.getApplication();
    	View view =inflater.inflate(R.layout.fragment_zongjifen, null);
    	mListView=(ListView) view.findViewById(R.id.mListView);
    	tv_fenshu=(TextView) view.findViewById(R.id.tv_fenshu);
    	tv_fenshu.setText("1314");
    	   //ListView数据
    	list = new ArrayList<myjifenInfo>();
    	list.add(new myjifenInfo( "2014.02.12", "天气好天气好天气好天气好天气好天气好"));
    	list.add(new myjifenInfo( "2014.05.1", "天气好天气好天气好天气好天气好"));
    	list.add(new myjifenInfo( "2014.6.31", "天气好天气好天气好"));
    	list.add(new myjifenInfo( "2015.02.18", "天气好天气好天气好天气好天气好天气好"));
    	list.add(new myjifenInfo("2015.03.05", "今天天气也好"));
    	adapter=new zongjifenAdapter(mActivity, list);
    	mListView.setAdapter(adapter);
        return view;
	}
//	@Override
//    public NotifyView onCreateContentView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
//	    	mActivity=this.getActivity();
//	        application = (MyApplication) mActivity.getApplication();
//	    	NotifyView view =inflater.inflate(R.layout.fragment_zongjifen, null);
//	    	mListView=(ListView) view.findViewById(R.id.mListView);
//	    	tv_fenshu=(TextView) view.findViewById(R.id.tv_fenshu);
//	    	tv_fenshu.setText("1314");
//	    	   //ListView数据
//	    	list = new ArrayList<myjifenInfo>();
//	    	list.add(new myjifenInfo( "2014.02.12", "天气好"));
//	    	adapter=new zongjifenAdapter(mActivity, list);
//	    	mListView.setAdapter(adapter);
//            return view;
//	    }
	
//	@Override
//	public void setResource() {
//		//设置加载的资源
//		this.setLoadDrawable(R.drawable.ic_load);
//		this.setLoadMessage("正在查询,请稍候");
//		 
//		this.setRefreshDrawable(R.drawable.ic_refresh);
//		this.setRefreshMessage("请求出错，请重试");
//	}

}

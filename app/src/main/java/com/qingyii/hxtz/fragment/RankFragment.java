package com.qingyii.hxtz.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.adapter.ExamRankListAdapter_02;
import com.qingyii.hxtz.bean.ExamRankInfo;

import java.util.ArrayList;
import java.util.List;

//import com.ab.fragment.AbFragment;
//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

/**
 * 界面调整后此类不用了
 * @author shelia
 *
 */
@SuppressLint({ "NewApi", "ValidFragment" })
public class RankFragment extends Fragment{

   private Activity mActivity = null;
   private AbPullToRefreshView mAbPullToRefreshView = null;
   private ListView mListView = null;
   private ExamRankListAdapter_02 myListViewAdapter;
   private List<ExamRankInfo> mList;
   private List<ExamRankInfo> refreshList;
   private List<ExamRankInfo> loadMoreList;

   public RankFragment(List<ExamRankInfo> refreshList, List<ExamRankInfo> loadMoreList){
       this.refreshList = refreshList;
       this.loadMoreList = loadMoreList;
   }
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
           Bundle savedInstanceState) {
        mActivity = this.getActivity();

        View view = inflater.inflate(R.layout.pull_to_refresh_list, null);
        //获取ListView对象
        mAbPullToRefreshView = (AbPullToRefreshView)view.findViewById(R.id.mPullRefreshView);
        mListView = (ListView)view.findViewById(R.id.mListView);

        //设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

           @Override
           public void onHeaderRefresh(AbPullToRefreshView view) {
               refreshTask();
           }
       });
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

           @Override
           public void onFooterLoad(AbPullToRefreshView view) {
               if(loadMoreList != null){
                   mList.addAll(loadMoreList);
                   myListViewAdapter.notifyDataSetChanged();
                   loadMoreList.clear();
               }
               mAbPullToRefreshView.onFooterLoadFinish();
           }
       });

        //设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));

        //ListView数据
        mList = new ArrayList<ExamRankInfo>();

        //使用自定义的Adapter
        myListViewAdapter = new ExamRankListAdapter_02(mActivity, mList);
        mListView.setAdapter(myListViewAdapter);
        refreshTask();
       return view;
   }

   protected void refreshTask() {
//		mList.clear();
       if(refreshList != null){
           mList.addAll(refreshList);
           myListViewAdapter.notifyDataSetChanged();
           refreshList.clear();
       }
       mAbPullToRefreshView.onHeaderRefreshFinish();
   }

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

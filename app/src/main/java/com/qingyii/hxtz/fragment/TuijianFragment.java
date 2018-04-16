package com.qingyii.hxtz.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.adapter.shuwuAdapter;
import com.qingyii.hxtz.httpway.SJIpload;
import com.qingyii.hxtz.pojo.BooksParameter;
import com.qingyii.hxtz.shujixiangqingActivity;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class TuijianFragment extends Fragment {
    private Activity mActivity;
    private ListView mListView = null;
    private LinearLayout emptyView;
    private shuwuAdapter adapter;
    private AbPullToRefreshView mAbPullToRefreshView = null;
    //    private ArrayList<Book> list = new ArrayList<Book>();
    private List<BooksParameter.DataBean> list = new ArrayList<BooksParameter.DataBean>();
    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                adapter.notifyDataSetChanged();
            }
            if (mAbPullToRefreshView != null) {
                //刷新，加载更新提示隐藏
                mAbPullToRefreshView.onHeaderRefreshFinish();
                mAbPullToRefreshView.onFooterLoadFinish();
            }
            return false;
        }
    });

//    private int page = 1;
//    private int pagesize = 10;
    // 列表操作类型：1表示下拉刷新，2表示上拉加载更多
//    int type = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = this.getActivity();

        View view = inflater.inflate(R.layout.fragment_shuwu, null);
        // 获取ListView对象
        mListView = (ListView) view.findViewById(R.id.mListView);
        mAbPullToRefreshView = (AbPullToRefreshView) view.findViewById(R.id.mPullRefreshView);

        adapter = new shuwuAdapter(mActivity, list, "tuijian");
        mListView.setAdapter(adapter);

        //当listview没有内容时显示暂无内容
        emptyView = (LinearLayout) view.findViewById(R.id.empty_fragment_shuwu);
        mListView.setEmptyView(emptyView);

        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                refreshTask();
            }
        });

        //上拉加载
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                loadMoreTask();
            }
        });

        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent it = new Intent(getActivity(), shujixiangqingActivity.class);
//                it.putExtra("Book", list.get(position));
                it.putExtra("Book", list.get(position));
                startActivity(it);
            }
        });
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
//        type = 1;
//        page = 1;
        SJIpload sjIpload = SJIpload.getSJIpload();
        sjIpload.BooksList(this.getActivity(), adapter, "recommend", 0, list, handler);
//        getshujiaList(page, pagesize);
    }

    protected void refreshTask() {
//        type = 1;
//        getshujiaList(1, pagesize);
        SJIpload sjIpload = SJIpload.getSJIpload();
        sjIpload.BooksList(this.getActivity(), adapter, "recommend", 0, list, handler);

    }

    protected void loadMoreTask() {
//        type = 2;
//        getshujiaList(page, pagesize);
        SJIpload sjIpload = SJIpload.getSJIpload();
        if (list.size() > 0)
            sjIpload.BooksList(this.getActivity(), adapter, "recommend", list.get(list.size() - 1).getId(), list, handler);
    }
}

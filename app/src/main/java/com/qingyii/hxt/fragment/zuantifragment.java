package com.qingyii.hxt.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxt.LoginActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.ZuantiListActivity;
import com.qingyii.hxt.adapter.zuanTiAdapter;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.YzyHttpClient;
import com.qingyii.hxt.httpway.SJIpload;
import com.qingyii.hxt.pojo.Book;
import com.qingyii.hxt.pojo.BooksClassify;
import com.qingyii.hxt.util.EmptyUtil;

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

@SuppressLint("NewApi")
public class zuantifragment extends Fragment {
    private Activity mActivity;
    private ListView mListView;
    private LinearLayout emptyView;
    //    private ArrayList<Book> list = new ArrayList<Book>();
    private List<BooksClassify.DataBean> list = new ArrayList<BooksClassify.DataBean>();
    private zuanTiAdapter adapter;
    private AbPullToRefreshView mAbPullToRefreshView = null;
    private int page = 1;
    private int pagesize = 10;
    int type = 1;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        handler = new Handler(new Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    adapter.notifyDataSetChanged();
                } else if (msg.what == 5) {
//					Toast.makeText(mActivity, "已是最新数据", 0).show();
                } else if (msg.what == 0) {
                    Toast.makeText(mActivity, "获取数据失败", Toast.LENGTH_SHORT).show();
                }
                if (mAbPullToRefreshView != null) {
                    //刷新，加载更新提示隐藏
                    mAbPullToRefreshView.onHeaderRefreshFinish();
                    mAbPullToRefreshView.onFooterLoadFinish();
                }
                return false;
            }
        });
        mActivity = this.getActivity();

        View view = inflater.inflate(R.layout.fragment_shuwu, null);
        mAbPullToRefreshView = (AbPullToRefreshView) view.findViewById(R.id.mPullRefreshView);
        mListView = (ListView) view.findViewById(R.id.mListView);
        adapter = new zuanTiAdapter(mActivity, list);
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
                Intent it = new Intent(getActivity(), ZuantiListActivity.class);
                it.putExtra("Book", list.get(position));
                startActivity(it);

            }
        });
        return view;
    }

//    private void zhuantiList(int mypage, int mypagesize) {
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//            jsonObject.put("page", mypage);
//            jsonObject.put("pagesize", mypagesize);
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            try {
//                bytes = jsonObject.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("专题_out", jsonObject.toString());
//
//                YzyHttpClient.post(mActivity, HttpUrlConfig.querySpecialList, entity,
//                        new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("专题_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(mActivity, CacheUtil.logout, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(mActivity, LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    Gson gson = new Gson();
//                                    if (type == 1) {
//                                        list.clear();
//                                        page = 2;
//                                    }
//
//                                    try {
//                                        JSONObject Obj = new JSONObject(content);
//                                        if (EmptyUtil.IsNotEmpty(Obj.getString("rows"))) {
//
//
//                                            JSONArray mlist = Obj.getJSONArray("rows");
//                                            if (mlist.length() <= 0) {
//                                                emptyView.setText("暂无内容");
//                                                handler.sendEmptyMessage(5);
//                                            } else {
//                                                // 如果获取到更新数据，页码加1
//                                                if (type == 2) {
//                                                    page += 1;
//                                                }
//                                                for (int i = 0; i < mlist.length(); i++) {
//                                                    Book s = gson.fromJson(mlist.getString(i), Book.class);
//                                                    list.add(s);
//                                                }
//                                                handler.sendEmptyMessage(1);
//                                            }
//                                        }
//                                    } catch (JSONException e) {
//                                        handler.sendEmptyMessage(0);
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            }
//                        });
//            } catch (UnsupportedEncodingException e) {
//                handler.sendEmptyMessage(0);
//                e.printStackTrace();
//            }
//
//        } catch (JSONException e) {
//            handler.sendEmptyMessage(0);
//            e.printStackTrace();
//        }
//
//
//    }

    @Override
    public void onStart() {
        super.onStart();
        type = 1;
        page = 1;
//        zhuantiList(page, pagesize);
        SJIpload sjIpload = SJIpload.getSJIpload();
        sjIpload.BooksClassifyList(this.getActivity(), adapter, "subject", list, handler);
    }

    private void refreshTask() {
        type = 1;
//        zhuantiList(1, pagesize);
        SJIpload sjIpload = SJIpload.getSJIpload();
        sjIpload.BooksClassifyList(this.getActivity(), adapter, "subject", list, handler);
    }

    private void loadMoreTask() {
        type = 2;
//        zhuantiList(page, pagesize);
    }
}

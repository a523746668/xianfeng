package com.qingyii.hxtz.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;
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

//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

@SuppressLint("NewApi")
public class zuixinFragment extends Fragment {
    private Activity mActivity;
    private ListView mListView = null;
    private LinearLayout emptyView;
    private shuwuAdapter adapter;
    private AbPullToRefreshView mAbPullToRefreshView = null;
    //    private ArrayList<Book> list = new ArrayList<Book>();
    private List<BooksParameter.DataBean> list = new ArrayList<BooksParameter.DataBean>();
    private Handler handler;
    private int page = 1;
    private int pagesize = 10;
    // 列表操作类型：1表示下拉刷新，2表示上拉加载更多
    int type = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        handler = new Handler(new Callback() {

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
        mActivity = this.getActivity();
        View view = inflater.inflate(R.layout.fragment_shuwu, null);

        mListView = (ListView) view.findViewById(R.id.mListView);


        // 获取ListView对象
        mListView = (ListView) view.findViewById(R.id.mListView);
        mAbPullToRefreshView = (AbPullToRefreshView) view.findViewById(R.id.mPullRefreshView);

        adapter = new shuwuAdapter(mActivity, list, "zuixin");
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
                it.putExtra("Book", list.get(position));
                startActivity(it);
            }
        });
        return view;
    }

//    private void getshujiaList(int mypage, int mypagesize) {
//
//
//        try {
//            JSONObject jsonObject = new JSONObject();
////			JSONObject obj=new JSONObject("{}");
//            jsonObject.put("page", mypage);
//            jsonObject.put("pagesize", mypagesize);
//
//            byte[] bytes;
//            ByteArrayEntity entity = null;
//            try {
//                // stringEntity = new StringEntity(jsonObject.toString());
//                // 处理保存数据中文乱码问题
//                bytes = jsonObject.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("最新_out", jsonObject.toString());
//
//                YzyHttpClient.post(mActivity, HttpUrlConfig.queryBookList1, entity,
//                        new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statecode, String arg0) {
//
//                                Log.e("最新_in", arg0);
//
//                                // TODO Auto-generated method stub
//                                if (statecode == 499) {
//                                    Toast.makeText(mActivity, CacheUtil.logout, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(mActivity, LoginActivity.class);
//                                    startActivity(intent);
//                                    onFinish();
//                                } else if (statecode == 200) {
//                                    Gson gson = new Gson();
//                                    try {
//                                        if (type == 1) {
//                                            list.clear();
//                                            page = 2;
//                                        }
//                                        JSONObject js = new JSONObject(arg0);
//                                        if (EmptyUtil.IsNotEmpty(js.getString("rows"))) {
//                                            JSONArray lists = js.getJSONArray("rows");
//                                            if (lists.length() <= 0) {
//                                                emptyView.setText("暂无内容");
//                                                handler.sendEmptyMessage(5);
//                                            } else {
//                                                // 如果获取到更新数据，页码加1
//                                                if (type == 2) {
//                                                    page += 1;
//                                                }
//
//                                                for (int i = 0; i < lists.length(); i++) {
//                                                    Book b = gson.fromJson(lists.getString(i), Book.class);
//                                                    list.add(b);
//                                                }
//
//                                                // 服务器正确获取数据更新数据源
//                                                handler.sendEmptyMessage(1);
//                                            }
//                                        } else {
//                                            handler.sendEmptyMessage(0);
//                                        }
//
//                                    } catch (JSONException e) {
//                                        handler.sendEmptyMessage(0);
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(int statusCode,
//                                                  Throwable error, String content) {
//                                // TODO Auto-generated method stub
//                                super.onFailure(statusCode, error, content);
//
//                            }
//
//                            @Override
//                            public void onFinish() {
//                                // TODO Auto-generated method stub
//                                super.onFinish();
//                                System.out.println("结束方法");
//                            }
//
//                        });
//            } catch (UnsupportedEncodingException e1) {
//                // TODO Auto-generated catch block
//                handler.sendEmptyMessage(0);
//                e1.printStackTrace();
//            }
//        } catch (JSONException e2) {
//            // TODO Auto-generated catch block
//            e2.printStackTrace();
//            handler.sendEmptyMessage(0);
//        }
//
//    }

    @Override
    public void onStart() {
        super.onStart();
        type = 1;
        page = 1;
//        getshujiaList(page, pagesize);
        SJIpload sjIpload = SJIpload.getSJIpload();
        sjIpload.BooksList(this.getActivity(), adapter, "latest",0, list, handler);
    }

    protected void refreshTask() {
        type = 1;
//        getshujiaList(1, pagesize);
        SJIpload sjIpload = SJIpload.getSJIpload();
        sjIpload.BooksList(this.getActivity(), adapter, "latest",0, list, handler);
    }

    protected void loadMoreTask() {
        type = 2;
//        getshujiaList(page, pagesize);
        SJIpload sjIpload = SJIpload.getSJIpload();
        if (list.size() > 0)
            sjIpload.BooksList(this.getActivity(), adapter, "recommend", list.get(list.size()-1).getId(), list, handler);
    }


}

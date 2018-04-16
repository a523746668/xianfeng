package com.qingyii.hxtz.fragment;

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
import com.qingyii.hxtz.KaoChangInfoActivity;
import com.qingyii.hxtz.LoginActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.adapter.kaoshiAdapter;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.pojo.Examination;
import com.qingyii.hxtz.util.EmptyUtil;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//import com.ab.http.AbHttpListener;
//import com.ab.http.AbRequestParams;
//import com.ab.util.AbToastUtil;
//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

@SuppressLint("NewApi")
public class joiningExamFragment extends Fragment {
    public static String TAG = "JioningExam";
    private Activity mActivity = null;
    private AbPullToRefreshView mAbPullToRefreshView = null;
    private ListView mListView = null;
    /**
     * 考试列表
     */
    private ArrayList<Examination> myList = new ArrayList<Examination>();
    private kaoshiAdapter myAdapter;
    private int page = 1;
    private int pagesize = 10;
    // 列表操作类型：1表示下拉刷新，2表示上拉加载更多
    int type = 1;
    private Handler handler;
    /**
     * 考试类型
     */
    public int kaoshiType = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = this.getActivity();
        handler = new Handler(new Callback() {

            @Override
            public boolean handleMessage(Message arg0) {
                switch (arg0.what) {
                    case 0:

                        break;
                    case 1:
                        myAdapter.notifyDataSetChanged();
                        break;
                    case 5:
                        Toast.makeText(mActivity, "已是最新数据", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                if (mAbPullToRefreshView != null) {
                    mAbPullToRefreshView.onFooterLoadFinish();
                    mAbPullToRefreshView.onHeaderRefreshFinish();
                }
                return true;
            }
        });
        View view = inflater.inflate(R.layout.pull_to_refresh_list, null);
        // 获取ListView对象
        mAbPullToRefreshView = (AbPullToRefreshView) view
                .findViewById(R.id.mPullRefreshView);
        mListView = (ListView) view.findViewById(R.id.mListView);

        // 设置监听器
        mAbPullToRefreshView
                .setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(AbPullToRefreshView view) {
                        type = 1;
                        page = 1;
                        getData(page, pagesize);
                    }
                });
        mAbPullToRefreshView
                .setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

                    @Override
                    public void onFooterLoad(AbPullToRefreshView view) {
                        type = 2;
                        getData(page, pagesize);
                    }
                });

        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));

        // item被点击事件
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (EmptyUtil.IsNotEmpty(myList.get(position).getJoinflag())) {
                    switch (Integer
                            .parseInt(myList.get(position).getJoinflag())) {
                        // 人是否可参与（1-可参与，2-不可参与）
                        case 1:
//						Intent intent = new Intent(mActivity,
//								kaoshiContentActivity.class);
//						intent.putExtra("kaoshidetail", myList.get(position));
//						startActivity(intent);
                            Intent intent = new Intent(mActivity, KaoChangInfoActivity.class);
                            intent.putExtra("examination", myList.get(position));
                            startActivity(intent);
                            break;
                        case 2:
                            Toast.makeText(mActivity, "你不能参与这个考试！", Toast.LENGTH_SHORT).show();
                            break;

                        default:
                            break;
                    }
                } else {
                    // 默认可以参与
//					Intent intent = new Intent(mActivity,
//							kaoshiContentActivity.class);
//					intent.putExtra("kaoshidetail", myList.get(position));
//					startActivity(intent);
                    Intent intent = new Intent(mActivity, KaoChangInfoActivity.class);
                    intent.putExtra("examination", myList.get(position));
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        page = 1;
        type = 1;
        getData(page, pagesize);
//        myAdapter = new kaoshiAdapter(mActivity, myList);
        mListView.setAdapter(myAdapter);
    }

    private void getData(int mypage, int mypagesize) {
        System.out.println("考试类型:" + kaoshiType);
        JSONObject jsonObject = new JSONObject();
        try {
//			JSONObject obj = new JSONObject("{}");
            jsonObject.put("page", mypage);
            jsonObject.put("pagesize", mypagesize);
            if (kaoshiType > 0) {
                jsonObject.put("type", kaoshiType);
            }
            byte[] bytes;
            ByteArrayEntity entity = null;
            try {
                // stringEntity = new StringEntity(jsonObject.toString());
                // 处理保存数据中文乱码问题
                bytes = jsonObject.toString().getBytes("utf-8");
                entity = new ByteArrayEntity(bytes);
                YzyHttpClient.post(this.getActivity(),
                        HttpUrlConfig.queryExaminationList, entity,
                        new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statecode, String json) {
                                // TODO Auto-generated method stub
                                if (statecode == 499) {
                                    Toast.makeText(mActivity, CacheUtil.logout, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mActivity, LoginActivity.class);
                                    startActivity(intent);
                                    onFinish();
                                } else if (statecode == 200) {
                                    Gson gson = new Gson();
                                    try {
                                        if (type == 1) {
                                            myList.clear();
                                            page = 2;
                                        }

                                        System.out.println(json+" ----");

                                        JSONObject js = new JSONObject(json);
                                        JSONArray lists = js
                                                .getJSONArray("rows");
                                        if (lists.length() <= 0) {
                                            handler.sendEmptyMessage(5);
                                        } else {
                                            // 如果获取到更新数据，页码加1
                                            if (type == 2) {
                                                page += 1;
                                            }

                                            for (int i = 0; i < lists.length(); i++) {
                                                Examination b = gson.fromJson(
                                                        lists.getString(i),
                                                        Examination.class);
                                                myList.add(b);
                                            }

                                            // 服务器正确获取数据更新数据源
                                            handler.sendEmptyMessage(1);
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
                                System.out.println(content);
                            }

                            @Override
                            public void onFinish() {
                                // TODO Auto-generated method stub
                                super.onFinish();
                                System.out.println("结束方法");
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

}

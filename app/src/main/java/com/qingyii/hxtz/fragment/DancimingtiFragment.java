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

import java.util.ArrayList;

//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

@SuppressLint("NewApi")
public class DancimingtiFragment extends Fragment {
    public int mytype = 0;

    private AbPullToRefreshView kaoctypeRefreshView;
    private ArrayList<Examination> myList = new ArrayList<Examination>();
    private ListView mListView;
    private int page = 1;
    private int pagesize = 10;
    private int type = 1;// 列表操作类型：1表示下拉刷新，2表示上拉加载更多
    private Activity mActivity;
    private kaoshiAdapter adapter;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kaochangtype, null);
        mActivity = this.getActivity();

        handler = new Handler(new Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Toast.makeText(mActivity, "获取数据异常", Toast.LENGTH_SHORT).show();
                    case 1:
                        adapter.notifyDataSetChanged();
                    case 5:
                        Toast.makeText(mActivity, "已是最新数据", Toast.LENGTH_SHORT).show();
                        break;
                }
                if (kaoctypeRefreshView != null) {
                    kaoctypeRefreshView.onFooterLoadFinish();
                    kaoctypeRefreshView.onHeaderRefreshFinish();
                }
                return true;
            }
        });

        mListView = (ListView) view.findViewById(R.id.mListView);
//        adapter = new kaoshiAdapter(mActivity, myList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long arg3) {
                if (isjoing(myList.get(position))) {
                    Intent intent = new Intent(mActivity, KaoChangInfoActivity.class);
                    intent.putExtra("examination", myList.get(position));
                    startActivity(intent);
                } else {
                    Toast.makeText(mActivity, "此考试已结束", Toast.LENGTH_SHORT).show();
                }

            }
        });
        kaoctypeRefreshView = (AbPullToRefreshView) view.findViewById(R.id.kaoctypeRefreshView);
        kaoctypeRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                type = 1;
                page = 1;
                getData(page, pagesize);
            }
        });


        kaoctypeRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                type = 2;
                getData(page, pagesize);
            }
        });
        // 设置进度条的样式
        kaoctypeRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        kaoctypeRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData(page, pagesize);
    }


    public boolean isjoing(Examination examination) {
        long starttime = 0;
        long endtime = 0;
        boolean iskk = false;
//		if(EmptyUtil.IsNotEmpty(examination.getStarttime())){
//			 starttime=Long.parseLong(examination.getStarttime());
//		}
//		if(EmptyUtil.IsNotEmpty(examination.getEndtime())){
//			 endtime=Long.parseLong(examination.getEndtime());
//		}
        starttime = examination.getStarttimeInt();
        endtime = examination.getEndtimeInt();
        long now = System.currentTimeMillis();
        if (now >= starttime && now <= endtime) {
            iskk = true;
        } else {
            iskk = false;
        }
        return iskk;
    }

    ;


    public void getData(int mypage, int mypagesize) {
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("page", mypage);
            jsonobject.put("pagesize", mypagesize);
            if (mytype > 0) {
                jsonobject.put("type", mytype);
            }
            byte[] bytes = jsonobject.toString().getBytes("utf-8");
            ByteArrayEntity entity = new ByteArrayEntity(bytes);
            YzyHttpClient.post(this.getActivity(), HttpUrlConfig.queryExaminationList, entity, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, String content) {
                    if (statusCode == 499) {
                        Toast.makeText(mActivity, CacheUtil.logout, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        startActivity(intent);
                        onFinish();
                    } else if (statusCode == 200) {
                        Gson gson = new Gson();
                        if (type == 1) {

                            myList.clear();
                            page = 2;

                        }
                        try {
                            JSONObject js = new JSONObject(content);
                            String a = js.getString("rows");
                            System.out.println(a);
                            if (EmptyUtil.IsNotEmpty(js.getString("rows"))) {

                                JSONArray lists = js.getJSONArray("rows");

                                if (lists.length() <= 0) {
                                    handler.sendEmptyMessage(5);
                                } else {
                                    type = 2;
                                    page += 1;
                                }
                                for (int i = 0; i < lists.length(); i++) {
                                    Examination gg = gson.fromJson(lists.getString(i), Examination.class);
                                    if (isjoing(gg)) {
                                        gg.setJoinflag("1");
                                    } else {
                                        gg.setJoinflag("2");
                                    }
                                    ;

                                    myList.add(gg);
                                }
                                handler.sendEmptyMessage(1);

                            } else {
                                handler.sendEmptyMessage(5);
                            }

                        } catch (JSONException e) {
                            handler.sendEmptyMessage(0);
                            e.printStackTrace();
                        }
                    }
                    super.onSuccess(statusCode, content);
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    super.onFailure(error, content);
                    System.out.println(content);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    System.out.println("finish");
                }

            });
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

}

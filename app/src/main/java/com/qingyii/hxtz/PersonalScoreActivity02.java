package com.qingyii.hxtz;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxtz.adapter.PersonalScoreAdapter;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.pojo.PersonalScore;
import com.qingyii.hxtz.util.EmptyUtil;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//import com.ab.fragment.AbDialogFragment;
//import com.ab.fragment.AbDialogFragment.AbDialogOnLoadListener;
//import com.ab.util.AbDialogUtil;
//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

public class PersonalScoreActivity02 extends AbBaseActivity {

    private PersonalScore personalscore;
    private String typess;
    private String examinationname;
    private String examinationid;
    private ListView listView;
    private AbPullToRefreshView abPullToRefreshView;
    /**
     * AbDialogFragment 失效修改为 DialogFragment
     */
//	private AbDialogFragment abDialogFragment=null;
    private DialogFragment abDialogFragment = null;
    private PersonalScoreAdapter adapter;
    private ArrayList<PersonalScore> list = new ArrayList<PersonalScore>();
    private TextView personalscore_title, personalscore_kaoshiming, personalscore_cishu, personalscore_shijian,
            personalscore_score, personalscore_howlongtime;
    private ImageView personalscore_goback_iv;
    private int page = 1;
    private int pagesize = 100;
    private int type = 1;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalscore);


        handler = new Handler(new Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                // TODO Auto-generated method stub
                if (abDialogFragment != null) {
                    abDialogFragment.dismiss();
                }
                if (msg.what == 1) {
                    adapter.notifyDataSetChanged();
                } else if (msg.what == 0) {
                    Toast.makeText(PersonalScoreActivity02.this, "数据获取异常！", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 5) {
                    Toast.makeText(PersonalScoreActivity02.this, "已是最新数据！", Toast.LENGTH_SHORT).show();
                }


                return false;
            }
        });


        personalscore = (PersonalScore) getIntent().getSerializableExtra("personalscore");
        typess = getIntent().getStringExtra("typess");
        examinationname = getIntent().getStringExtra("examinationname");
        examinationid = getIntent().getStringExtra("examinationid");

        initUI();
        initData();

    }


    private void initData() {
        //进度查询
        /**
         * Load失效修改为Progress
         *
         * 监听失效
         */
//		 abDialogFragment= AbDialogUtil.showLoadDialog(this, R.mipmap.ic_load, "查询中,请等一小会");
//		 abDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//
//			@Override
//			public void onLoad() {
//				// TODO Auto-generated method stub
//				refreshTask();
//			}
//		});
        abDialogFragment = AbDialogUtil.showProgressDialog(this, R.mipmap.ic_load, "查询中,请等一小会");


    }


    private void initUI() {


        personalscore_title = (TextView) findViewById(R.id.personalscore_title);
        personalscore_kaoshiming = (TextView) findViewById(R.id.personalscore_kaoshiming);
        personalscore_cishu = (TextView) findViewById(R.id.personalscore_cishu);
        personalscore_shijian = (TextView) findViewById(R.id.personalscore_shijian);
        personalscore_score = (TextView) findViewById(R.id.personalscore_score);
        personalscore_howlongtime = (TextView) findViewById(R.id.personalscore_howlongtime);
        personalscore_goback_iv = (ImageView) findViewById(R.id.personalscore_goback_iv);


        if (EmptyUtil.IsNotEmpty(personalscore.getUsername())) {
            personalscore_title.setText(personalscore.getName() + "的考试成绩");
        }
        if (EmptyUtil.IsNotEmpty(examinationname)) {
            personalscore_kaoshiming.setText(examinationname);
        }
        if (EmptyUtil.IsNotEmpty(typess)) {
            if ("2".equals(typess)) {
                personalscore_cishu.setText("答题序号");
                personalscore_shijian.setText("答题时间");
                personalscore_score.setText("成绩");
                personalscore_howlongtime.setText("答题耗时");
            } else if ("3".equals(typess)) {
                personalscore_cishu.setText("答题序号");
                personalscore_shijian.setText("闯关时间");
                personalscore_score.setText("成绩");
                personalscore_howlongtime.setText("答题耗时");
            } else if ("4".equals(typess)) {
                personalscore_cishu.setText("答题序号");
                personalscore_shijian.setText("答题时间");
                personalscore_score.setText("成绩");
                personalscore_howlongtime.setText("答题耗时");
            }
        }

        //实例化listview
        listView = (ListView) findViewById(R.id.personalscore_list);
        abPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.personalscore_mPull_kaoshirank);


        //实例化适配器
        adapter = new PersonalScoreAdapter(this, list, typess);
        listView.setAdapter(adapter);


        /**
         * 下拉刷新监听
         */
        abPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                // TODO Auto-generated method stub
                refreshTask();
            }
        });


        /**
         * 上啦加载监听
         */
        abPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                // TODO Auto-generated method stub
                loadMoreTask();
            }
        });

        //设置式样
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));

			 /*
			  * 返回按钮
			  * */
        personalscore_goback_iv = (ImageView) findViewById(R.id.personalscore_goback_iv);
        personalscore_goback_iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                onBackPressed();
            }
        });


    }


    /**
     * 某个人的考试的所有成绩接口
     */
    private void getscorelist(int mypage, int mypagesize) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", mypage);
            jsonObject.put("pagesize", mypagesize);
            jsonObject.put("examinationid", examinationid);
            jsonObject.put("userid", personalscore.getUserid());
            byte[] bytes;
            ByteArrayEntity arrayEntity = null;
            try {
                bytes = jsonObject.toString().getBytes("utf-8");
                arrayEntity = new ByteArrayEntity(bytes);
                YzyHttpClient.post(PersonalScoreActivity02.this, HttpUrlConfig.queryScoreAllList, arrayEntity, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, String content) {
                        // TODO Auto-generated method stub
                        super.onSuccess(statusCode, content);
                        if (statusCode == 499) {
                            Toast.makeText(PersonalScoreActivity02.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PersonalScoreActivity02.this, LoginActivity.class);
                            startActivity(intent);
                            onFinish();
                        } else if (statusCode == 200) {
                            Gson gson = new Gson();
                            try {
                                if (type == 1) {
                                    list.clear();
                                    page = 2;
                                }

                                JSONObject jsonObject = new JSONObject(content);
                                if (EmptyUtil.IsNotEmpty(jsonObject.getString("rows"))) {
                                    JSONArray array = jsonObject.getJSONArray("rows");
                                    if (array.length() <= 0) {
                                        handler.sendEmptyMessage(5);
                                    } else {
                                        if (type == 2) {

                                            page += 1;
                                        }
                                        for (int i = 0; i < array.length(); i++) {

                                            PersonalScore a = gson.fromJson(array.getString(i), PersonalScore.class);
                                            list.add(a);
                                        }

                                        handler.sendEmptyMessage(1);

                                    }
                                } else {
                                    Toast.makeText(PersonalScoreActivity02.this, "已是最新数据", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                handler.sendEmptyMessage(0);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
                        // TODO Auto-generated method stub
                        super.onFailure(error, content);
                        handler.sendEmptyMessage(0);
                    }
                });

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                handler.sendEmptyMessage(0);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            handler.sendEmptyMessage(0);
        }


    }

    private void refreshTask() {
        type = 1;
        getscorelist(1, pagesize);
        abPullToRefreshView.onHeaderRefreshFinish();
    }


    private void loadMoreTask() {
        type = 2;
        getscorelist(page, pagesize);
        abPullToRefreshView.onFooterLoadFinish();
    }


}

package com.qingyii.hxt;

import android.app.DialogFragment;
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
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxt.adapter.UnitScoreAdapter;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.YzyHttpClient;
import com.qingyii.hxt.pojo.PersonalScore;
import com.qingyii.hxt.pojo.UnitScore;
import com.qingyii.hxt.util.EmptyUtil;

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

public class UnitScoreActivity extends AbBaseActivity {


    private String examinationid;
    private String typess;
    private String examinationname;
    private ListView listView;
    private AbPullToRefreshView abPullToRefreshView;
    /**
     * AbDialogFragment 失效修改为 DialogFragment
     */
//	private AbDialogFragment abDialogFragment=null;
    private DialogFragment abDialogFragment = null;
    private ArrayList<PersonalScore> list = new ArrayList<PersonalScore>();
    private UnitScoreAdapter adapter;
    private TextView personalscore_title, personalscore_kaoshiming, personalscore_cishu, personalscore_shijian,
            personalscore_score, personalscore_howlongtime;
    private ImageView personalscore_goback_iv;
    private int page = 1;
    private int pagesize = 100;
    private int type = 1;
    private String groupid;
    private Handler handler;
    private UnitScore unitscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalscore_unit);


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
                    Toast.makeText(UnitScoreActivity.this, "数据获取异常！", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 5) {
                    Toast.makeText(UnitScoreActivity.this, "已是最新数据！", Toast.LENGTH_SHORT).show();
                }


                return false;
            }
        });


        unitscore = (UnitScore) getIntent().getSerializableExtra("unitscore");
        typess = getIntent().getStringExtra("typess");
        examinationname = getIntent().getStringExtra("examinationname");
        examinationid = getIntent().getStringExtra("examinationid");
        groupid = getIntent().getStringExtra("groupid");

        initUI();
        initDate();
    }


    private void initDate() {
        //进度条
        /**
         * Load失效修改为Progress
         *
         * 监听失效
         */
//        abDialogFragment = AbDialogUtil.showLoadDialog(this, R.mipmap.ic_load, "查询中,请等一小会");
//        abDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//
//            @Override
//            public void onLoad() {
//                // TODO Auto-generated method stub
//                refreshTask();
//            }
//        });
        abDialogFragment = AbDialogUtil.showProgressDialog(this, R.mipmap.ic_load, "查询中,请等一小会");


    }


    private void initUI() {

        personalscore_title = (TextView) findViewById(R.id.personalscore_title);
        personalscore_kaoshiming = (TextView) findViewById(R.id.personalscore_kaoshiming);
        personalscore_cishu = (TextView) findViewById(R.id.personalscore_cishu);
        personalscore_shijian = (TextView) findViewById(R.id.personalscore_shijian);
        personalscore_score = (TextView) findViewById(R.id.personalscore_score);
        personalscore_howlongtime = (TextView) findViewById(R.id.personalscore_howlongtime);

//		personalscore_cishu.setVisibility(NotifyListView.GONE);

        if (EmptyUtil.IsNotEmpty(unitscore.getCompanyname())) {
            personalscore_title.setText(unitscore.getCompanyname() + "的考试成绩");
        }
        if (EmptyUtil.IsNotEmpty(examinationname)) {
            personalscore_kaoshiming.setText(examinationname);
        }


        if (EmptyUtil.IsNotEmpty(typess)) {
            if ("2".equals(typess)) {
                personalscore_cishu.setText("排名");
                personalscore_shijian.setText("姓名");
                personalscore_score.setText("单位");
                personalscore_howlongtime.setText("得分");

            } else if ("3".equals(typess)) {
                personalscore_cishu.setText("排名");
                personalscore_shijian.setText("姓名");
                personalscore_score.setText("单位");
                personalscore_howlongtime.setText("成绩");
            } else if ("4".equals(typess)) {
                personalscore_cishu.setText("排名");
                personalscore_shijian.setText("姓名");
                personalscore_score.setText("单位");
                personalscore_howlongtime.setText("成绩");
            }

        }


        //回返按钮监听
        personalscore_goback_iv = (ImageView) findViewById(R.id.personalscore_goback_iv);


        personalscore_goback_iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });


        //实例化listview
        listView = (ListView) findViewById(R.id.personalscore_list);
        abPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.personalscore_mPull_kaoshirank);

        //实例化适配器
        adapter = new UnitScoreAdapter(this, list, unitscore, typess);
        listView.setAdapter(adapter);


        //listview点击事件
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(UnitScoreActivity.this, PersonalScoreActivity02.class);
                intent.putExtra("personalscore", list.get(position));
                intent.putExtra("typess", typess);
                intent.putExtra("examinationname", examinationname);
                intent.putExtra("examinationid", examinationid);
                startActivity(intent);
            }
        });


        //下拉刷新监听
        abPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                // TODO Auto-generated method stub
                refreshTask();
            }
        });


        //上啦加载监听
        abPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                // TODO Auto-generated method stub
                loadMoreTask();
            }
        });


    }


    /**
     * 某个人单位的考试的所有成绩接口
     */
    private void getunitscorelist(int mypage, int mypagesize) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", mypage);
            jsonObject.put("pagesize", mypagesize);
            jsonObject.put("examinationid", examinationid);
            jsonObject.put("companyid", unitscore.getCompanyid());
            if (EmptyUtil.IsNotEmpty(this.groupid)) {
                int groupid1 = Integer.valueOf(this.groupid);
                if (groupid1 <= 0) {
                    if (EmptyUtil.IsNotEmpty(unitscore.getDepid())) {
                        jsonObject.put("depid", unitscore.getDepid());
                    }
                }
            }
            byte[] bytes;
            ByteArrayEntity arrayEntity = null;
            try {
                bytes = jsonObject.toString().getBytes("utf-8");
                arrayEntity = new ByteArrayEntity(bytes);
                YzyHttpClient.post(UnitScoreActivity.this, HttpUrlConfig.queryScoreAllCompanyList, arrayEntity, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, String content) {
                        // TODO Auto-generated method stub
                        super.onSuccess(statusCode, content);
                        if (statusCode == 499) {
                            Toast.makeText(UnitScoreActivity.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UnitScoreActivity.this, LoginActivity.class);
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
                                    Toast.makeText(UnitScoreActivity.this, "已是最新数据", Toast.LENGTH_SHORT).show();
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
        getunitscorelist(1, pagesize);
        abPullToRefreshView.onHeaderRefreshFinish();
    }


    private void loadMoreTask() {
        type = 2;
        getunitscorelist(page, pagesize);
        abPullToRefreshView.onFooterLoadFinish();
    }


}

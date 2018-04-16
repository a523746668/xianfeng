package com.qingyii.hxtz.contact;

import android.content.Intent;
import android.graphics.Color;
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
import com.andbase.library.view.dialog.AbProgressDialogFragment;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxtz.Constant;
import com.qingyii.hxtz.LoginActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.adapter.ContactOrgAdapter;
import com.qingyii.hxtz.bean.ContactOrg;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.util.EmptyUtil;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 同事通讯录首页
 *
 * @author Lee
 */
public class ContactMainActivity extends AbBaseActivity {

    private static final int TYPE_HEADER = 0x0001;
    private static final int TYPE_FOOTER = 0x0002;

    private TextView mTextOrg, mTextDepartment, mTextFollow;

    private AbPullToRefreshView mRefreshOrg;
    private ListView mListOrg;
    private ArrayList<ContactOrg> mDatasOrg = new ArrayList<ContactOrg>();
    private ContactOrgAdapter mOrgAdapter;

    private ImageView mImgLeft;
    private ImageView mImgRight;

    private int mRefreshOrgType;
    private int mRefreshDepartmentType;
    private int mRefreshFollowType;

    private int mPageOrg = 1;
    private int mPageDepartment = 1;
    private int mPageFollow = 1;
    private int mPagesize = 10;

    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//    private AbLoadDialogFragment mDialogFragment = null;
    private AbProgressDialogFragment mDialogFragment = null;
    private Handler mHandler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (mDialogFragment != null) {
                mDialogFragment.dismiss();
            }
            switch (msg.what) {
                case Constant.MSG_ERROR:
                    Toast.makeText(ContactMainActivity.this, "获取数据异常", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case Constant.MSG_SUC:
                    if (msg.arg1 == 0) {
                        mOrgAdapter.notifyDataSetChanged();
                    } else if (msg.arg1 == 1) {
                        // mDepartmentAdapter.notifyDataSetChanged();
                    }
                    break;
                case Constant.MSG_NODATA:
                    Toast.makeText(ContactMainActivity.this, "已是最新数据", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
            if (msg.arg1 == 0) {
                mRefreshOrg.onHeaderRefreshFinish();
                mRefreshOrg.onFooterLoadFinish();
            } else if (msg.arg1 == 1) {
                // mRefreshDepartment.onHeaderRefreshFinish();
                // mRefreshDepartment.onFooterLoadFinish();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_main);
        findView();
        initData();
        setListener();
        mRefreshOrg.headerRefreshing();
    }

    private void findView() {
        mTextOrg = (TextView) findViewById(R.id.contact_main_org);
        mTextFollow = (TextView) findViewById(R.id.contact_main_follow);
        mTextDepartment = (TextView) findViewById(R.id.contact_main_department);

        mRefreshOrg = (AbPullToRefreshView) findViewById(R.id.contact_main_refreshorg);
        mListOrg = (ListView) findViewById(R.id.contact_main_listorg);
        mOrgAdapter = new ContactOrgAdapter(ContactMainActivity.this, mDatasOrg);
        mListOrg.setAdapter(mOrgAdapter);

        mImgLeft = (ImageView) findViewById(R.id.contact_main_left);
        mImgRight = (ImageView) findViewById(R.id.contact_main_right);
    }

    private void initData() {
    }

    private void setListener() {
        mImgLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        mImgRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ContactMainActivity.this, ContactSearchActivity.class);
                startActivity(intent);
            }
        });
        mTextOrg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mTextOrg.setTextColor(Color.RED);
                mTextDepartment.setTextColor(Color.BLACK);
                mTextFollow.setTextColor(Color.BLACK);
            }
        });
        mTextDepartment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mTextOrg.setTextColor(Color.BLACK);
                mTextDepartment.setTextColor(Color.RED);
                mTextFollow.setTextColor(Color.BLACK);
            }
        });
        mTextFollow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mTextOrg.setTextColor(Color.BLACK);
                mTextDepartment.setTextColor(Color.BLACK);
                mTextFollow.setTextColor(Color.RED);
            }
        });
        mRefreshOrg.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                mRefreshOrgType = TYPE_HEADER;
                mPageOrg = 1;
                getDataOrg(mPageOrg, mPagesize);
            }
        });
        mRefreshOrg.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                mRefreshOrgType = TYPE_FOOTER;
                getDataOrg(mPageOrg, mPagesize);
            }
        });
        mListOrg.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ContactOrg org = mDatasOrg.get(arg2);
                Intent intent = new Intent(ContactMainActivity.this, ContactOrgDetailActivity.class);
                intent.putExtra("ContactOrg", org);
                startActivity(intent);
            }
        });
    }

    public void getDataOrg(int mypage, int mypagesize) {
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("page", mypage);
            jsonobject.put("pagesize", mypagesize);
            jsonobject.put("prevclass", 0);
            jsonobject.put("userid", CacheUtil.userid + "");
            byte[] bytes = jsonobject.toString().getBytes("utf-8");
            ByteArrayEntity entity = new ByteArrayEntity(bytes);
            YzyHttpClient.post(this, HttpUrlConfig.reqAddressBook, entity,
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, String content) {
                            if (statusCode == 499) {
                                Toast.makeText(ContactMainActivity.this,
                                        CacheUtil.logout, Toast.LENGTH_SHORT)
                                        .show();
                                Intent intent = new Intent(
                                        ContactMainActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                onFinish();
                            } else if (statusCode == 200) {
                                Gson gson = new Gson();
                                try {
                                    JSONObject js = new JSONObject(content);
                                    if (EmptyUtil.IsNotEmpty(js
                                            .getString("list"))) {
                                        JSONArray lists = js
                                                .getJSONArray("list");
                                        int curclass = js.getInt("curclass");
                                        if (lists.length() <= 0) {
                                            mHandler.sendEmptyMessage(Constant.MSG_NODATA);
                                        } else {
                                            if (mRefreshOrgType == TYPE_HEADER) {
                                                ContactMainActivity.this.mDatasOrg
                                                        .clear();
                                            } else {

                                            }
                                            mPageOrg++;
                                            for (int i = 0; i < lists.length(); i++) {
                                                ContactOrg info = gson.fromJson(
                                                        lists.getString(i),
                                                        ContactOrg.class);
                                                info.setCurclass(curclass);
                                                ContactMainActivity.this.mDatasOrg.add(info);
                                            }
                                            Message msg = Message.obtain();
                                            msg.what = Constant.MSG_SUC;
                                            msg.arg1 = 0;
                                            mHandler.sendMessage(msg);
                                        }

                                    } else {
                                        mHandler.sendEmptyMessage(Constant.MSG_NODATA);
                                    }

                                } catch (JSONException e) {
                                    mHandler.sendEmptyMessage(Constant.MSG_ERROR);
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
                            // System.out.println("finish");
                        }

                    });
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}

package com.qingyii.hxtz.circle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.view.dialog.AbProgressDialogFragment;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxtz.LoginActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.CircleReport;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.util.EmptyUtil;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import com.ab.fragment.AbDialogFragment.AbDialogOnLoadListener;
//import com.ab.fragment.AbLoadDialogFragment;
//import com.ab.util.AbDialogUtil;
//import com.qingyii.hxt.AbActivity;

/**
 * 选择类型界面
 *
 * @author Lee
 */
public class CircleChooseReasonActivity extends AbBaseActivity {

   // private ImageView mImgLeft;
    private ListView mListView;
    private ArrayList<CircleReport> mArrayType;
    private ArrayList<String> mArrayTypeStr;

    private ArrayAdapter<String> mArrayAdapter;

    private Handler mHandler;
    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//    private AbLoadDialogFragment mDialogFragment = null;
    private AbProgressDialogFragment mDialogFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_typelist);
        findView();
        initData();
        /**
         * Load失效修改为Progress
         *
         * 监听失效
         */
//        mDialogFragment = AbDialogUtil.showLoadDialog(
//                CircleChooseReasonActivity.this, R.mipmap.ic_load,
//                "发送中,请等一小会");
//        mDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//            @Override
//            public void onLoad() {
//                getReasonList();
//            }
//        });
        mDialogFragment = AbDialogUtil.showProgressDialog(
                CircleChooseReasonActivity.this, R.mipmap.ic_load,
                "发送中,请等一小会");

        setListener();
    }

    private void findView() {
      //  mImgLeft = (ImageView) findViewById(R.id.circle_choosetype_left);
        mListView = (ListView) findViewById(R.id.circle_typelist_list);
    }

    private void initData() {
        mArrayType = new ArrayList<CircleReport>();
        mArrayTypeStr = new ArrayList<String>();
        mArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mArrayTypeStr);
        mListView.setAdapter(mArrayAdapter);
        mHandler = new Handler(new Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                if (mDialogFragment != null) {
                    mDialogFragment.dismiss();
                }
                switch (msg.what) {
                    case 0:
                        Toast.makeText(CircleChooseReasonActivity.this, "获取数据异常", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case 1:
                        mArrayAdapter.notifyDataSetChanged();
                        break;
                    case 5:
                        Toast.makeText(CircleChooseReasonActivity.this, "已是最新数据", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case 6:
                        break;
                }
                return false;
            }
        });
    }

    private void setListener() {


        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(CircleChooseReasonActivity.this,
                        CircleReportActivity.class);
                intent.putExtra("CircleReport", mArrayType.get(arg2));
                CircleChooseReasonActivity.this.setResult(RESULT_OK, intent);
                CircleChooseReasonActivity.this.finish();
            }
        });
    }

    public void getReasonList() {
        if (CacheUtil.user == null) {
            return;
        }
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("userid", CacheUtil.userid + "");
            byte[] bytes = jsonobject.toString().getBytes("utf-8");
            ByteArrayEntity entity = new ByteArrayEntity(bytes);
            YzyHttpClient.post(this, HttpUrlConfig.reqReportResonList, entity,
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, String content) {
                            if (statusCode == 499) {
                                Toast.makeText(CircleChooseReasonActivity.this,
                                        CacheUtil.logout, Toast.LENGTH_SHORT)
                                        .show();
                                Intent intent = new Intent(
                                        CircleChooseReasonActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                onFinish();
                            } else if (statusCode == 200) {
                                Gson gson = new Gson();
                                try {

                                    JSONObject js = new JSONObject(content);
                                    if (EmptyUtil.IsNotEmpty(js
                                            .getString("rows"))) {

                                        JSONArray lists = js
                                                .getJSONArray("rows");

                                        if (lists.length() <= 0) {
                                            mHandler.sendEmptyMessage(0);
                                        } else {
                                            for (int i = 0; i < lists.length(); i++) {
                                                CircleReport type = gson.fromJson(
                                                        lists.getString(i),
                                                        CircleReport.class);
                                                mArrayType.add(type);
                                                mArrayTypeStr.add(type
                                                        .getTypename());
                                            }
                                            mHandler.sendEmptyMessage(1);
                                        }

                                    } else {
                                        mHandler.sendEmptyMessage(5);
                                    }

                                } catch (JSONException e) {
                                    mHandler.sendEmptyMessage(0);
                                    e.printStackTrace();
                                }
                            }
                            super.onSuccess(statusCode, content);
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            super.onFailure(error, content);
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

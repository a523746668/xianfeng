package com.qingyii.hxtz.circle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.view.dialog.AbProgressDialogFragment;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxtz.Constant;
import com.qingyii.hxtz.LoginActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.CircleReport;
import com.qingyii.hxtz.bean.DynamicInfo;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.pojo.User;
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
 * 举报页
 *
 * @author Lee
 */
public class ReportCircleActivity extends AbBaseActivity {

    private static final int ARG_REASONLIST = 0;
    private static final int ARG_CREATEREPORT = 1;

    private DynamicInfo mInfoReport;

    private ArrayList<CircleReport> mReports = new ArrayList<CircleReport>();
    private ArrayList<CircleReport> mReportsSelected = new ArrayList<CircleReport>();
    private ArrayList<String> mObjectPhotos = new ArrayList<String>();
    private ArrayList<CheckBox> mViewReason = new ArrayList<CheckBox>();

    private Handler mHandler;

    private ImageView mImgObjectHeader;
    private TextView mTextObjectName, mTextObjectDepartment,
            mTextObjectCategory, mTextObjectContent;
    private NonScrollGridView mGridObjectPhotos;
    private PhotoAdapter mPhotoAdapter;
    private LinearLayout mViewReports;

    private TextView mTextContent;
    private CheckBox mCheckAnony;
    private Button mBtnReport;

    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//    private AbLoadDialogFragment mDialogFragment = null;
    private AbProgressDialogFragment mDialogFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            mInfoReport = (DynamicInfo) getIntent().getSerializableExtra(
                    "DynamicInfo");
        }
        setContentView(R.layout.activity_circle_report);
        findView();
        initData();
        setListener();
        /**
         * Load失效修改为Progress
         *
         * 监听失效
         */
//        mDialogFragment = AbDialogUtil.showLoadDialog(
//                ReportCircleActivity.this, R.mipmap.ic_load, "获取数据中,请等一小会");
//        mDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//            @Override
//            public void onLoad() {
//                getReasonList();
//            }
//        });
        mDialogFragment = AbDialogUtil.showProgressDialog(
                ReportCircleActivity.this, R.mipmap.ic_load, "获取数据中,请等一小会");

        mHandler = new Handler(new Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                if (mDialogFragment != null) {
                    mDialogFragment.dismiss();
                }
                switch (msg.what) {
                    case Constant.MSG_ERROR:

                        if (msg.arg1 == ARG_REASONLIST) {
                            Toast.makeText(ReportCircleActivity.this, "获取数据异常", Toast.LENGTH_SHORT)
                                    .show();
                        } else if (msg.arg1 == ARG_CREATEREPORT) {
                            Toast.makeText(ReportCircleActivity.this, "举报失败，请重试", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }
                        break;
                    case Constant.MSG_SUC:
                        if (msg.arg1 == ARG_REASONLIST) {
                            initReasonListView();
                        } else if (msg.arg1 == ARG_CREATEREPORT) {
                            Toast.makeText(ReportCircleActivity.this, "举报成功", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }
                        break;
                    case Constant.MSG_NODATA:
                        Toast.makeText(ReportCircleActivity.this, "已是最新数据", Toast.LENGTH_SHORT)
                                .show();
                        break;
                }
                if (msg.arg1 == ARG_REASONLIST) {
                }
                return false;
            }
        });
    }

    private void initReasonListView() {
        for (int i = 0; i < mReports.size(); i++) {
            final CircleReport report = mReports.get(i);
            CheckBox box1 = new CheckBox(this);
            box1.setTag(i);
            box1.setTextColor(Color.BLACK);
            box1.setTextSize(14);
            box1.setText("" + report.getTypename());
            box1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                    for (int i = 0; i < mViewReason.size(); i++) {
                        CheckBox box = mViewReason.get(i);
                        if ((Integer) box.getTag() != (Integer) arg0.getTag()) {
                            box.setChecked(false);
                        }
                    }
                    mReportsSelected.clear();
                    mReportsSelected.add(report);
                }
            });
            mViewReason.add(box1);
            mViewReports.addView(box1, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    private void findView() {
        mImgObjectHeader = (ImageView) findViewById(R.id.circle_report_header);
        mTextObjectName = (TextView) findViewById(R.id.circle_report_name);
        mTextObjectDepartment = (TextView) findViewById(R.id.circle_report_department);
        mTextObjectContent = (TextView) findViewById(R.id.circle_report_content);
        mTextObjectCategory = (TextView) findViewById(R.id.circle_report_category);
        mGridObjectPhotos = (NonScrollGridView) findViewById(R.id.circle_report_photo);
        mViewReports = (LinearLayout) findViewById(R.id.circle_report_reason);

        mCheckAnony = (CheckBox) findViewById(R.id.circle_report_reporter);
        mTextContent = (TextView) findViewById(R.id.circle_report_advice);
        mBtnReport = (Button) findViewById(R.id.circle_report_confirm);
    }

    private void initData() {
        final User createUser = mInfoReport.getCreateuser();
        ImageLoader.getInstance().displayImage(
                HttpUrlConfig.photoDir + createUser.getPicaddress(),
                mImgObjectHeader);
        mTextObjectName.setText("" + createUser.getUsername());
        mTextObjectDepartment.setText("" + createUser.getDepname());
        mTextObjectContent.setText("" + mInfoReport.getContenttxt());
        mTextObjectCategory.setText("" + mInfoReport.getInfotypename());
        String[] images = mInfoReport.getContentimageurllist();
        if (images != null) {
            for (String str : images) {
                mObjectPhotos.add(str);
                if (mObjectPhotos.size() >= 9) {
                    break;
                }
            }
        }
        mPhotoAdapter = new PhotoAdapter(this, mObjectPhotos);
        mGridObjectPhotos.setAdapter(mPhotoAdapter);
    }

    public void setListener() {
        mCheckAnony.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
            }
        });
        mBtnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                report();
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
                                Toast.makeText(ReportCircleActivity.this,
                                        CacheUtil.logout, Toast.LENGTH_SHORT)
                                        .show();
                                Intent intent = new Intent(
                                        ReportCircleActivity.this,
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
                                            mHandler.sendEmptyMessage(Constant.MSG_NODATA);
                                        } else {
                                            for (int i = 0; i < lists.length(); i++) {
                                                CircleReport report = gson.fromJson(
                                                        lists.getString(i),
                                                        CircleReport.class);
                                                mReports.add(report);
                                            }
                                            Message msg = Message.obtain();
                                            msg.what = Constant.MSG_SUC;
                                            msg.arg1 = ARG_REASONLIST;
                                            mHandler.sendMessage(msg);
                                        }
                                    } else {
                                        mHandler.sendEmptyMessage(Constant.MSG_NODATA);
                                    }

                                } catch (JSONException e) {
                                    Message msg = Message.obtain();
                                    msg.what = Constant.MSG_ERROR;
                                    msg.arg1 = ARG_REASONLIST;
                                    mHandler.sendMessage(msg);
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

    public void report() {
        if (CacheUtil.user == null) {
            return;
        }
        if (mReportsSelected.size() <= 0) {
            Toast.makeText(this, "提交失败，请选择举报原因", Toast.LENGTH_LONG).show();
            return;
        }
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("userid", CacheUtil.userid + "");
            jsonobject.put("dynamicinfoid", mInfoReport.getDynamicid());
            jsonobject.put("commentid", 0);
            jsonobject.put("resonid", mReportsSelected.get(0).getTypeid());
            jsonobject.put("content", "" + mTextContent.getText().toString());
            Gson gson = new Gson();
            JSONArray urlsJson = new JSONArray(gson.toJson(new String[]{},
                    String[].class));
            jsonobject.put("imagelist", urlsJson);
            jsonobject.put("isanonymous", mCheckAnony.isChecked() ? 1 : 0);
            byte[] bytes = jsonobject.toString().getBytes("utf-8");
            ByteArrayEntity entity = new ByteArrayEntity(bytes);
            YzyHttpClient.post(this, HttpUrlConfig.reqCreateReport, entity,
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, String content) {
                            if (statusCode == 499) {
                                Toast.makeText(ReportCircleActivity.this,
                                        CacheUtil.logout, Toast.LENGTH_SHORT)
                                        .show();
                                Intent intent = new Intent(
                                        ReportCircleActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                onFinish();
                            } else if (statusCode == 200) {
                                Message msg = Message.obtain();
                                msg.what = Constant.MSG_SUC;
                                msg.arg1 = ARG_CREATEREPORT;
                                mHandler.sendMessage(msg);
                            }
                            super.onSuccess(statusCode, content);
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            super.onFailure(error, content);
                            Message msg = Message.obtain();
                            msg.what = Constant.MSG_ERROR;
                            msg.arg1 = ARG_CREATEREPORT;
                            mHandler.sendMessage(msg);
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

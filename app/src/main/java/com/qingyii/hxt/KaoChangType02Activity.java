package com.qingyii.hxt;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxt.adapter.kaoshiAdapter;
import com.qingyii.hxt.httpway.KSUpload;
import com.qingyii.hxt.pojo.ExamList;
import com.qingyii.hxt.pojo.TrainList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 考试列表
 *
 * @author shelia
 */
public class KaoChangType02Activity extends AbBaseActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean = null;
    public static HashMap<String, String> mAccumulateUseTime = new HashMap<String, String>();//累计闯关的用时存储

    private LinearLayout emptyView;
    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//    private AbLoadDialogFragment mDialogFragment = null;
//    private AbProgressDialogFragment mDialogFragment = null;
    private ShapeLoadingDialog shapeLoadingDialog = null;
    private AbPullToRefreshView abPullToRefreshView;
    //    private ArrayList<Examination> myList = new ArrayList<Examination>();
    private List<ExamList.DataBean> eDataBeanlist = new ArrayList<ExamList.DataBean>();
    private ListView mListView;
    private int page = 1;
    private int pagesize = 10;
    private int type = 1;// 列表操作类型：1表示下拉刷新，2表示上拉加载更多
    private kaoshiAdapter adapter;
    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
//                if (mDialogFragment != null) {
//                    mDialogFragment.dismiss();
//                }
            if (shapeLoadingDialog != null)
                shapeLoadingDialog.dismiss();

            switch (msg.what) {
                case 0:
                    Toast.makeText(KaoChangType02Activity.this, "获取数据异常", Toast.LENGTH_SHORT).show();
                    break;
//                case 1:
//                    adapter.notifyDataSetChanged();
//                    if (emptyView!=null)
//                        emptyView.setVisibility(NotifyListView.GONE);
//                    break;
//                case 120://没有更多了
//                    if (emptyView!=null)
//                        emptyView.setVisibility(NotifyListView.VISIBLE);
//                    break;
                case 5:

                    Toast.makeText(KaoChangType02Activity.this, "已是最新数据", Toast.LENGTH_SHORT).show();
                    //mDialogFragment.dismiss();
                    shapeLoadingDialog.dismiss();
                    break;
            }

            if (abPullToRefreshView != null) {
                abPullToRefreshView.onFooterLoadFinish();
                abPullToRefreshView.onHeaderRefreshFinish();
            }
            return false;
        }
    });

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kaochang_type_02);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("考场");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);

        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        if (intent != null)
            tDataBean = intent.getParcelableExtra("training");

        initUI();
        initData();
    }

    private void initData() {
        // 显示进度框
        /**
         * Load失效修改为Progress
         *
         * 监听失效
         */
//        mDialogFragment = AbDialogUtil.showLoadDialog(this, R.mipmap.ic_load,
//                "查询中,请等一小会");
//        mDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//
//            @Override
//            public void onLoad() {
//                // 下载网络数据
//                getData(page, pagesize);
        KSUpload ksUpload = KSUpload.getKSUpload();
        if (tDataBean != null)
            ksUpload.examList(this, tDataBean.getId() + "", 0, adapter, eDataBeanlist, handler);
        else
            ksUpload.examList(this, "", 0, adapter, eDataBeanlist, handler);
//            }
//
//        });
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("查询中,请等一小会");
        shapeLoadingDialog.show();
//        mDialogFragment = AbDialogUtil.showProgressDialog(this, R.mipmap.ic_load, "查询中,请等一小会");
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
    }

    private void initUI() {
        mListView = (ListView) findViewById(R.id.mListView);
        emptyView = (LinearLayout) findViewById(R.id.empty_exam);
        adapter = new kaoshiAdapter(this, eDataBeanlist);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(emptyView);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {
//              if (isjoing(myList.get(position))) {
                Intent intent = new Intent(KaoChangType02Activity.this, KaoChangInfoActivity.class);
                intent.putExtra("examination", eDataBeanlist.get(position));
                startActivity(intent);
//              } else {
//                  Toast.makeText(KaoChangType02Activity.this, "此考试已结束", 0)
//                          .show();
//              }

            }
        });
        abPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
        abPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                type = 1;
                page = 1;
//                        getData(page, pagesize);
                KSUpload ksUpload = KSUpload.getKSUpload();
                if (tDataBean != null)
                    ksUpload.examList(KaoChangType02Activity.this, tDataBean.getId() + "", 0, adapter, eDataBeanlist, handler);
                else
                    ksUpload.examList(KaoChangType02Activity.this, "", 0, adapter, eDataBeanlist, handler);
            }
        });

        abPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                type = 2;
                //getData(page, pagesize);
                KSUpload ksUpload = KSUpload.getKSUpload();
                if (tDataBean != null)
                    ksUpload.examList(KaoChangType02Activity.this, tDataBean.getId() + "", eDataBeanlist.get(eDataBeanlist.size() - 1).getId(), adapter, eDataBeanlist, handler);
                else
                    ksUpload.examList(KaoChangType02Activity.this, "", eDataBeanlist.get(eDataBeanlist.size() - 1).getId(), adapter, eDataBeanlist, handler);
            }
        });
        // 设置进度条的样式
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
    }

    public void getData(int page, int pagesize) {
        if (handler != null) {
            handler.sendEmptyMessage(5);
        }
    }
}

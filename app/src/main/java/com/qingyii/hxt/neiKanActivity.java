package com.qingyii.hxt;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxt.adapter.neiKanAdapter;
import com.qingyii.hxt.bean.neikanInfo;
import com.qingyii.hxt.httpway.NKUpload;
import com.qingyii.hxt.pojo.SubscribedNK;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 已订阅刊物列表接口
 *
 * @author Administrator
 */
public class neiKanActivity extends BaseActivity {
    private AutoLinearLayout iv_back;
    private neiKanAdapter adapter;
    //    private AbPullToRefreshView mAbPullToRefreshView = null;
    private ListView mListView = null;
    private AutoLinearLayout emptyView;
    //    private TextView txEmptyView;
    //    private ArrayList<Magazine> list = new ArrayList<Magazine>();
    private List<SubscribedNK.DataBean> list = new ArrayList<SubscribedNK.DataBean>();
    private neikanInfo neikaninfo;
    private AutoRelativeLayout rl_dingyue;

    int posionId;

    private NKUpload nkUpload = NKUpload.getNKUpload();

    private Dialog dialog;
    private View menuContentLayout;
    private TextView neikan_dialog_l;
    private TextView neikan_dialog_r;

    // 当你想实例化一个Handler,而又不实现自己的Hanlder的子类
    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    refreshTask();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.neikan")) {
//                 list.clear();
//                refreshTask();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neikan);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("我的刊物");
        // 注册刷新数据广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.neikan");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTask();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //注销广播
        unregisterReceiver(mRefreshBroadcastReceiver);
    }

    private void initUI() {
        iv_back = (AutoLinearLayout) findViewById(R.id.returns_arrow);
        rl_dingyue = (AutoRelativeLayout) findViewById(R.id.rl_dingyue);
        mListView = (ListView) this.findViewById(R.id.mListView);

//        mAbPullToRefreshView = (AbPullToRefreshView) this
//                .findViewById(R.id.mPullRefreshView);
//        mAbPullToRefreshView
//                .setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
//                    @Override
//                    public void onHeaderRefresh(AbPullToRefreshView view) {
//                        refreshTask();
//                    }
//                });
//
//        mAbPullToRefreshView
//                .setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
//                    @Override
//                    public void onFooterLoad(AbPullToRefreshView view) {
//                        loadMoreTask();
//                    }
//                });
        // 设置进度条的样式
//        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
//                this.getResources().getDrawable(R.drawable.progress_circular));
//        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
//                this.getResources().getDrawable(R.drawable.progress_circular));
        adapter = new neiKanAdapter(this, list);
        mListView.setAdapter(adapter);
        emptyView = (AutoLinearLayout) findViewById(R.id.empty_view);
        mListView.setEmptyView(emptyView);
//        TextView emptyView = new TextView(this);
//        emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//        emptyView.setText("暂无内容");
//        emptyView.setTextSize(22);
//        emptyView.setVisibility(NotifyListView.GONE);
//        ((ViewGroup) mListView.getParent()).addView(emptyView);
//        mListView.setEmptyView(emptyView);
        rl_dingyue.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(neiKanActivity.this, dingyueNeikanActivity.class);
                startActivity(it);
            }
        });
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                posionId = position;
                Intent it = new Intent(neiKanActivity.this, neiKanXq.class);
//                it.putExtra("Magazine", list.get(position));
                it.putExtra("SubscribedNK", list.get(position));
                startActivity(it);
            }
        });
        /**
         * 置顶 退订
         *
         *
         */
        mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                posionId = position;
                dialog = new Dialog(neiKanActivity.this, R.style.DialogStyle);

                menuContentLayout = View.inflate(neiKanActivity.this, R.layout.neikan_dialog, null);
                neikan_dialog_l = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_l);
                neikan_dialog_r = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_r);
                neikan_dialog_l.setText("置顶");
                neikan_dialog_r.setText("退订");
                neikan_dialog_l.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                                        zhiding();
                        nkUpload.SubscribedZD(neiKanActivity.this, dialog, posionId, list, handler);
                    }
                });
                neikan_dialog_r.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nkUpload.SubscribedCZ(neiKanActivity.this, 0, dialog, posionId, list, handler);
                    }
                });

                dialog.setContentView(menuContentLayout);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
                return true;
            }
        });

        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    protected void refreshTask() {
//        getNeikanList(1, pagesize);
        nkUpload.NKSubscribed(this, adapter, list, handler);
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

//    protected void loadMoreTask() {
//        getNeikanList(page, pagesize);
//        nkUpload.NKSubscribed(this, adapter,list.get(list.size()-1).getId(), list, handler);
//    }

}

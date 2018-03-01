package com.qingyii.hxt;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxt.adapter.ManageListAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.httpway.GLUpload;
import com.qingyii.hxt.pojo.ManageList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理 待审核用户列表
 */
public class ManageListActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView activity_tltle_name;

    private Intent intent;
    private String uiParameter;

    private ShapeLoadingDialog shapeLoadingDialog = null;
    private AbPullToRefreshView abPullToRefreshView;
    private ListView mListView;
    private ManageListAdapter adapter;
    private List<ManageList.DataBean> mDataBeanList = new ArrayList<ManageList.DataBean>();

    //最高权限部分
    private RelativeLayout max_button_ll;
    private TextView max_button_affirm;
    private TextView max_button_appeal;

    private GLUpload glUpload = GLUpload.getGLUpload();

    // 当你想实例化一个Handler,而又不实现自己的Hanlder的子类
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }
            if (msg.what == 1) {
                adapter.notifyDataSetChanged();
                if (mDataBeanList.size() == 0) {
                }
            } else if (msg.what == 2) {
                Toast.makeText(ManageListActivity.this, "已是最新数据", Toast.LENGTH_SHORT).show();
                shapeLoadingDialog.dismiss();
            } else if (msg.what == 0) {
                Toast.makeText(ManageListActivity.this, "数据加载异常", Toast.LENGTH_SHORT).show();
            }

            // 刷新，加载更新提示隐藏
            abPullToRefreshView.onHeaderRefreshFinish();
            abPullToRefreshView.onFooterLoadFinish();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);
        //tltle设置
        activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        uiParameter = intent.getStringExtra("UIparameter");
        Log.e("ManageListUI", uiParameter);

        activity_tltle_name.setText(MyApplication.hxt_setting_config.getString("ManageDate", "日期"));

        abPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
        mListView = (ListView) findViewById(R.id.mListView);

        if (uiParameter.equals("Max")) {
            max_button_ll = (RelativeLayout) findViewById(R.id.max_button_ll);
            max_button_appeal.setVisibility(View.VISIBLE);
            max_button_affirm = (TextView) findViewById(R.id.max_button_affirm);
            max_button_appeal = (TextView) findViewById(R.id.max_button_appeal);
            max_button_affirm.setOnClickListener(this);
            max_button_appeal.setOnClickListener(this);
        }

        initUI();
        refreshTask();
    }

    private void initUI() {
        mListView = (ListView) this.findViewById(R.id.mListView);

        abPullToRefreshView = (AbPullToRefreshView) this
                .findViewById(R.id.mPullRefreshView);

        abPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                refreshTask();

            }
        });

        abPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                loadMoreTask();

            }
        });

        // 设置进度条的样式
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                this.getResources().getDrawable(R.drawable.progress_circular));
        adapter = new ManageListAdapter(this, mDataBeanList);

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(ManageListActivity.this, UserListActivity.class);
                intent.putExtra("UIparameter", uiParameter);
                intent.putExtra("UserNews", mDataBeanList.get(position));
                startActivity(intent);
            }
        });
//        emptyView = (LinearLayout) findViewById(R.id.empty_neikan);
//        mListView.setEmptyView(emptyView);
    }

    protected void refreshTask() {
        glUpload.usersList(this, adapter, mDataBeanList, handler);
    }

    protected void loadMoreTask() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.max_button_affirm:
                break;
            case R.id.max_button_appeal:
                break;
            default:
                break;
        }
    }
}

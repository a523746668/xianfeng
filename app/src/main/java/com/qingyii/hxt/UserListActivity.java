package com.qingyii.hxt;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxt.adapter.UserListAdapter;
import com.qingyii.hxt.adapter.UserListMyAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.httpway.GLUpload;
import com.qingyii.hxt.pojo.ManageList;
import com.qingyii.hxt.pojo.MyUserList;
import com.qingyii.hxt.pojo.UserContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理 内容列表
 */
public class UserListActivity extends AbBaseActivity {
    private TextView activity_tltle_name;

    private Intent intent;

    private String uiParameter;
    private ManageList.DataBean mDataBean;

    private ShapeLoadingDialog shapeLoadingDialog = null;
    private AbPullToRefreshView abPullToRefreshView;
    private ListView mListView;
    private UserListAdapter userListAdapter;
    private List<UserContext.DataBean> uDataBeanList = new ArrayList<UserContext.DataBean>();
    private UserListMyAdapter listMyAdapter;
    private List<MyUserList.DataBean> mDataBeanList = new ArrayList<MyUserList.DataBean>();

    private GLUpload glUpload = GLUpload.getGLUpload();
    private String simpleDate = "";

    // 当你想实例化一个Handler,而又不实现自己的Hanlder的子类
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }
            if (msg.what == 1) {
                if (uiParameter.equals("Manage"))
                    userListAdapter.notifyDataSetChanged();
                else if (uiParameter.equals("My"))
                    listMyAdapter.notifyDataSetChanged();
                if (uDataBeanList.size() == 0) {
                }
            } else if (msg.what == 2) {
                Toast.makeText(UserListActivity.this, "已是最新数据", Toast.LENGTH_SHORT).show();
                shapeLoadingDialog.dismiss();
            } else if (msg.what == 0) {
                Toast.makeText(UserListActivity.this, "数据加载异常", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_user_list);
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
        mDataBean = intent.getParcelableExtra("UserNews");
        Log.e("UserListUI", uiParameter);
//        simpleDate = intent.getStringExtra("simpleDate");
        activity_tltle_name.setText(MyApplication.hxt_setting_config.getString("ManageDate", "日期"));

        abPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
        mListView = (ListView) findViewById(R.id.mListView);
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
        userListAdapter = new UserListAdapter(this, uDataBeanList);
        listMyAdapter = new UserListMyAdapter(this, mDataBeanList);
        if (uiParameter.equals("Manage"))
            mListView.setAdapter(userListAdapter);
        else if (uiParameter.equals("My"))
            mListView.setAdapter(listMyAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(UserListActivity.this, "第" + position + "个", Toast.LENGTH_LONG).show();
                intent = new Intent(UserListActivity.this, UserContextActivity.class);
                intent.putExtra("UIparameter", uiParameter);
                switch (uiParameter) {
                    case "My":
                        intent.putExtra("tltleName", position + 1 + "/" + mDataBeanList.size());
                        intent.putExtra("UserContext", mDataBeanList.get(position));
                        startActivity(intent);
                        break;
                    case "Manage":
                        intent.putExtra("tltleName", position + 1 + "/" + uDataBeanList.size());
                        intent.putExtra("UserContext", uDataBeanList.get(position));
                        startActivity(intent);
                        break;
                    case "Max":
                        break;
                    default:
                        break;
                }
            }
        });
//        emptyView = (LinearLayout) findViewById(R.id.empty_neikan);
//        mListView.setEmptyView(emptyView);
    }

    protected void refreshTask() {
        if (mDataBean != null)
            glUpload.contextList(this, userListAdapter, mDataBean.getId(), uDataBeanList, handler);
        else
            glUpload.confirmedList(this, listMyAdapter, mDataBeanList, handler);
    }

    protected void loadMoreTask() {
    }
}

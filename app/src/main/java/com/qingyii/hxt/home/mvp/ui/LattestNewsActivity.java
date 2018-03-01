package com.qingyii.hxt.home.mvp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxt.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxt.base.widget.AutoLoadMoreRecyclerView;
import com.qingyii.hxt.home.mvp.model.entity.FakeData;
import com.qingyii.hxt.wmcj.mvp.ui.WorkParkDetailsActivity;
import com.zhf.wight.Itemdecotion;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;


// 首页的最新动态跳转的动态页面
public class LattestNewsActivity extends BaseActivity {
    @BindView(R.id.new_recyc)
    AutoLoadMoreRecyclerView recyclerView;

    @BindView(R.id.toolbar_back_layout)
    AutoLinearLayout back;

    @BindView(R.id.toolbar_title)
    TextView textView;
    private BaseRecyclerAdapter<FakeData.DataBeanX.DataBean>  adapter;

    private ArrayList<FakeData.DataBeanX.DataBean> fakedatas=new ArrayList<>();

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_lattest_news;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
         fakedatas.clear();
         fakedatas.addAll(getIntent().getExtras().getParcelableArrayList("fakedatas"));
        Log.i("tmdnew",fakedatas.size()+"");
       adapter=new BaseRecyclerAdapter<FakeData.DataBeanX.DataBean>(fakedatas) {
           @Override
           public int getItemLayoutId(int viewType) {
               return R.layout.lattestrecyc;
           }

           @Override
           public void bindData(BaseRecyclerViewHolder holder, int position, FakeData.DataBeanX.DataBean item) {
                   holder.getTextView(R.id.lattext).setText(item.getName());
           }
       };

         recyclerView.setAutoLayoutManager(new LinearLayoutManager(this)).setAutoHasFixedSize(true)
                 .setAutoItemAnimator(new DefaultItemAnimator()).setAutoAdapter(adapter)
                  .addItemDecoration(new Itemdecotion());
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object Data, View view, int position) {
                   if(fakedatas.get(position).getType().equalsIgnoreCase("activity")) {
                       Intent intent=new Intent(LattestNewsActivity.this, WorkParkDetailsActivity.class);
                       intent.putExtra("actid",fakedatas.get(position).getId());
                       startActivity(intent);

                   }
            }

            @Override
            public void onItemLongClick(Object Data, View view, int position) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        textView.setText("最新动态");
    }


}

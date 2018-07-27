package com.qingyii.hxtz.zhf;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.zhf.adapter.SCAdapter;
import com.qingyii.hxtz.zhf.bean.SCbean;
import com.qingyii.hxtz.zhf.bean.SCtypebean;
import com.qingyii.hxtz.zhf.present.Implpresent.ScPresenter;
import com.qingyii.hxtz.zhf.present.Implview.Scview;
import com.qingyii.hxtz.zhf.wight.Itemdecotion;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyshoucangActivity extends AppCompatActivity implements Scview {
     private ScPresenter presenter;
    @BindView(R.id.shoucangtab)
    TabLayout tabLayout;

    @BindView(R.id.shoucangrecyc)
   RecyclerView recyclerView;

    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.toolbar_back_layout)
    AutoLinearLayout back;
    private SCAdapter adapter;
    private Unbinder unbinder;
    private ArrayList<String > types=new ArrayList<>();
   private ArrayList<SCbean.DataBean> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshoucang2);
         unbinder= ButterKnife.bind(this);
         initactionbar();
         presenter=new ScPresenter(this,this);
         presenter.gettypes();
        initrecyclerview();
    }

    private void initactionbar() {
           title.setText("我的收藏");
           back.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                      finish();
               }
           });
    }

    private void initrecyclerview() {
        adapter=new SCAdapter(list,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new Itemdecotion());
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.unbind();
    }

    @Override
    public void gettypessuccess(SCtypebean ctypebean) {
           inittablayout(ctypebean);
           presenter.getdata(types.get(0));
    }

    @Override
    public void getdatasuccess(ArrayList<SCbean.DataBean> list) {
             this.list.clear();
             this.list.addAll(list);
             Iterator<SCbean.DataBean> it=  this.list.iterator();
             while (it.hasNext()){
                 SCbean.DataBean bean=it.next();
                 if(bean.getTitle()==null| TextUtils.isEmpty(bean.getTitle())){
                     it.remove();
                 }
             }
             adapter.notifyDataSetChanged();
    }

    private void inittablayout(SCtypebean ctypebean) {
            types.clear();
            types.addAll(ctypebean.getData().getType());
            for(String str:types){
                 if(str.equalsIgnoreCase("article")){
                     tabLayout.addTab(tabLayout.newTab().setText("文章"));

                 }
                if(str.equalsIgnoreCase("activity")){
                    tabLayout.addTab(tabLayout.newTab().setText("任务"));
                }
                if(str.equalsIgnoreCase("wz")){
                     tabLayout.addTab(tabLayout.newTab().setText("资讯"));
                }
            }

           tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
               @Override
               public void onTabSelected(TabLayout.Tab tab) {
                   presenter.getdata(types.get(tab.getPosition()));
                   Log.i("tmdtypepositon",tab.getPosition()+"");
               }

               @Override
               public void onTabUnselected(TabLayout.Tab tab) {

               }

               @Override
               public void onTabReselected(TabLayout.Tab tab) {

               }
           });
    }
}

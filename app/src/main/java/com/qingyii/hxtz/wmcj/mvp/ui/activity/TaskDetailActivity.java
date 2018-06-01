package com.qingyii.hxtz.wmcj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.wmcj.WMCJContract;

import com.qingyii.hxtz.wmcj.di.component.DaggerTaskDetailComponent;
import com.qingyii.hxtz.wmcj.di.module.TaskDetailModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Taskdetailbean;
import com.qingyii.hxtz.wmcj.mvp.presenter.TaskDetailPresenter;


import com.qingyii.hxtz.wmcj.mvp.ui.adapter.Taskdetailadapter;
import com.qingyii.hxtz.zhf.Util.HintUtil;
import com.qingyii.hxtz.zhf.wight.Itemdecotion;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;


//改写后任务详情
public class TaskDetailActivity extends BaseActivity<TaskDetailPresenter> implements WMCJContract.TaskDetaileView {
    RecyclerView recyclerView;
    TextView title1 ,title2,title3,score1,score2,score3,title4,score4;
    TextView  bartitile,title,name;
    Button back;
    private ArrayList<Taskdetailbean> list=new ArrayList<>();

    ArrayList<TextView> list3=new ArrayList<>();
    ArrayList<TextView>  list4=new ArrayList<>();

    private Taskdetailadapter adapter;
    private int taskid;
    private String taget;
    ArrayList<Integer>  list5=new ArrayList<>();

    private AutoLinearLayout huaxian;

    //从listfragment传过来的title
    private String titlename="";


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerTaskDetailComponent.builder()
                .appComponent(appComponent)
                .taskDetailModule(new TaskDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_taskdetail;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
         initview(savedInstanceState);
        mPresenter.getTaskDetailData(taskid);
    }

    private void initview(Bundle savedInstanceState) {
        taskid=getIntent().getIntExtra("id",0);
        taget=getIntent().getStringExtra("taget");
        titlename=getIntent().getStringExtra("title");
        huaxian= (AutoLinearLayout) findViewById(R.id.taskdetailll1);
        title= (TextView) findViewById(R.id.taskindextitle);
        title.setText(titlename);
        recyclerView= (RecyclerView) findViewById(R.id.taskdetailrecyc);
        name= (TextView) findViewById(R.id.taskindexname);
        name.setText(taget);

        title1= (TextView) findViewById(R.id.taskonetitle);
        title2= (TextView) findViewById(R.id.tasktwotitle);
        title3= (TextView) findViewById(R.id.taskthreetitle);
        title4= (TextView) findViewById(R.id.taskfourtitle);
        list3.add(title1);
        list3.add(title2);
        list3.add(title3);
        list3.add(title4);
        score1= (TextView) findViewById(R.id.taskonescore);
        score2= (TextView) findViewById(R.id.tasktwoscore);
        score3= (TextView) findViewById(R.id.taskthreescore);
        score4= (TextView) findViewById(R.id.taskfourscore);
        list4.add(score1);
        list4.add(score2);
        list4.add(score3);
        list4.add(score4);
        list5.add(R.id.taskdetailal1);
        list5.add(R.id.taskdetailal2);
        list5.add(R.id.taskdetailal3);list5.add(R.id.taskdetailal4);

        bartitile= (TextView) findViewById(R.id.toolbar_title);
        bartitile.setText("任务详情");
        back= (Button) findViewById(R.id.toolbar_back);
        back.setClickable(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new Taskdetailadapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new Itemdecotion());
    }

    @Override
    public void showLoading() {
        HintUtil.showdialog(this);
    }

    @Override
    public void hideLoading() {
    HintUtil.stopdialog();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void getdatasuccess(Taskdetailbean taskdetailbean) {
        list.clear();
        list.add(taskdetailbean);
        if(taskdetailbean.getData().getIndlibsyies()!=null){
            for(int i=0;i<taskdetailbean.getData().getIndlibsyies().size();i++){

                int layoutid=list5.get(taskdetailbean.getData().getIndlibsyies().get(i).getLevel());
                int postion=list5.indexOf(layoutid);
                AutoRelativeLayout layout = (AutoRelativeLayout) findViewById(layoutid);
                layout.setVisibility(View.VISIBLE);
                list3.get(postion).setText(taskdetailbean.getData().getIndlibsyies().get(i).getTitle());
                list4.get(postion).setText(taskdetailbean.getData().getIndlibsyies().get(i).getScore()
                        +"("+taskdetailbean.getData().getIndlibsyies().get(i).getLevelChines()+")");
            }
        }

        switch (taskdetailbean.getData().getIndlibsyies().size()){
            case 0:
                break;
            case 1:
                huaxian.setPadding(0,0,0,0);
                break;
            case 2:

                huaxian.setPadding(0,0,0,15);

                break;
            case 3:

                huaxian.setPadding(0,0,0,0);
                break;
            case 4:

                huaxian.setPadding(0,0,0,15);
                break;
        }


        adapter.notifyDataSetChanged();

    }

}

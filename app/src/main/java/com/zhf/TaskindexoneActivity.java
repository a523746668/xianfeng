package com.zhf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.zhf.Util.Global;
import com.zhf.adapter.Tasksonlistadapter;
import com.zhf.bean.Tasklistbean;
import com.zhf.present.Implpresent.Taskindexonepresenter;
import com.zhf.present.Implview.Taskindexoneview;

import java.util.ArrayList;


//任务一级指标
public class TaskindexoneActivity extends AppCompatActivity implements Taskindexoneview {
      private int titleid;


      private String indexname;
      private int ndlibsysid;
      private Taskindexonepresenter presenter;
      private ListView listview;
      private ArrayList<Tasklistbean.DataBean.IndlibsyiesBean>  list=new ArrayList<>();
      private ArrayList<Tasklistbean.DataBean.TaskBean>  list2=new ArrayList<>();
      private Tasksonlistadapter adapter;
   TextView  title,title1;
     ImageView back;
    TextView tname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskindexone);
        indexname=getIntent().getStringExtra("indexname");
        ndlibsysid= getIntent().getIntExtra("ndlibsysid",0);
        titleid=getIntent().getIntExtra("titleid",0);
        initview();
       initlistview();
    }

    private void initlistview() {
          adapter=new Tasksonlistadapter(list,this);
          listview.setAdapter(adapter);
          presenter=new Taskindexonepresenter(this,this);

          listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  if(position>0&&position<list.size()+1){

                     if(list.get(position-1).getHaschild()>0||list.get(position-1).getScore()>0){
                      Intent intent=new Intent(TaskindexoneActivity.this,TaskindexoneActivity.class);
                          intent.putExtra("indexname",list.get(position-1).getTitle());
                          intent.putExtra("ndlibsysid",list.get(position-1).getId());
                         intent.putExtra("titleid",titleid);
                      startActivity(intent);
                  }}
                  if(list2.size()>0&&position>list.size()&&position<list.size()+list2.size()+1){
                       Intent intent=new Intent(TaskindexoneActivity.this,TaskdetailActivity.class);
                      intent.putExtra("id",list2.get(position-list.size()-1).getId());
                      intent.putExtra("taget",list2.get(position-list.size()-1).getTarget());
                      startActivity(intent);
                  }

              }
          });
    }

    private void initview() {
       listview= (ListView) findViewById(R.id.taskindexlist);
      title= (TextView) findViewById(R.id.taskindextitle);
        title.setText(Global.TaskVpTitle);
        tname= (TextView) findViewById(R.id.taskindexname);
       tname.setText(indexname);
        title1= (TextView) findViewById(R.id.activity_tltle_name);
        title1.setText("任务指标");
        back= (ImageView) findViewById(R.id.toolbar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 finish();
            }
        });
    }

    @Override
    public void getdatasuccess(ArrayList<Tasklistbean.DataBean.IndlibsyiesBean> list) {
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void gettaskdatasuccesss(ArrayList<Tasklistbean.DataBean.TaskBean> list) {
         list2.clear();
        list2.addAll(list);
        adapter.setList2(list2);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getdata(titleid,ndlibsysid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbind();
        presenter=null;
    }
}

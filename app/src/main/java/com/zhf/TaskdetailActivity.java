package com.zhf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.zhf.Util.Global;
import com.zhf.adapter.Taskdetailadapter;
import com.zhf.bean.Taskdetailbean;
import com.zhf.present.Implpresent.Taskdetailpresenter;
import com.zhf.present.Implview.Taskdetailview;
import com.zhf.wight.Itemdecotion;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;

//任务详情
public class TaskdetailActivity extends AppCompatActivity   implements Taskdetailview{
       RecyclerView recyclerView;
       TextView  title1 ,title2,title3,score1,score2,score3,title4,score4;
       TextView  bartitile,title,name;
      Button back;
       private  ArrayList<Taskdetailbean> list=new ArrayList<>();
      ArrayList<Taskdetailbean.DataBean.IndlibsyiesBean> list2=new ArrayList<>();
       ArrayList<TextView> list3=new ArrayList<>();
       ArrayList<TextView>  list4=new ArrayList<>();
       private Taskdetailpresenter presenter;
       private Taskdetailadapter adapter;
      private int taskid;
    private String taget;
       ArrayList<Integer>  list5=new ArrayList<>();

    private AutoLinearLayout huaxian;

    //从listfragment传过来的title
    private String titlename="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskdetail);
        taskid=getIntent().getIntExtra("id",0);
        taget=getIntent().getStringExtra("taget");
        titlename=getIntent().getStringExtra("title");
        initview();
        initrecyclerview();
        presenter=new Taskdetailpresenter(this,this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getdata(taskid);
    }

    private void initrecyclerview() {
           recyclerView.setLayoutManager(new LinearLayoutManager(this));
           adapter=new Taskdetailadapter(list,this);
          recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new Itemdecotion());
    }

    private void initview() {
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
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbind();
        presenter=null;
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
            //    list3.get(0).setPadding(0,30,0,30);
               break;
           case 2:
              /* list3.get(0).setPadding(0,30,0,15);
               list3.get(1).setPadding(0,15,0,30);
               TextView line2= (TextView) findViewById(R.id.taskline2);
*/
               huaxian.setPadding(0,0,0,15);

               break;
           case 3:
              /* list3.get(0).setPadding(0,30,0,30);
               list3.get(1).setPadding(0,0,0,0);
               list3.get(2).setPadding(0,30,0,30);
*/
               huaxian.setPadding(0,0,0,0);
               break;
           case 4:
             /*  list3.get(0).setPadding(0,30,0,30);
               list3.get(1).setPadding(0,0,0,0);
               list3.get(2).setPadding(0,30,0,30);
               list3.get(3).setPadding(0,0,0,30);
               list4.get(3).setPadding(0,0,0,30);
               TextView line4= (TextView) findViewById(R.id.taskline4);
               line4.setPadding(0,0,0,30);
*/
               huaxian.setPadding(0,0,0,15);
               break;
       }


        adapter.notifyDataSetChanged();

    }
}

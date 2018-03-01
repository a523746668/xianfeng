package com.zhf.zfragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.SumPathEffect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.zhf.TaskdetailActivity;
import com.zhf.TaskindexoneActivity;
import com.zhf.Util.Global;
import com.zhf.adapter.TaskLineAdaper;
import com.zhf.adapter.Tasksonlistadapter;
import com.zhf.bean.TaskLineBean;
import com.zhf.bean.TaskLineSonbean;
import com.zhf.bean.TaskListViewBean;
import com.zhf.bean.Tasklistbean;
import com.zhf.present.Implpresent.Tasksonpresenter;
import com.zhf.present.Implview.TasklineView;
import com.zhf.present.Implview.Tasksonview;
import com.zhf.wight.DefaultItem;
import com.zhf.wight.Itemdecotion;

import org.geometerplus.zlibrary.core.util.ZLSearchUtil;

import java.util.ArrayList;
import java.util.List;


//整个Fragment只有一个list列表

public class TasklistsonFragment extends Fragment implements Tasksonview {

      private RecyclerView recyclerView;
     private ListView listView;
    // private Tasklistbean  bean;
     //private Tasksonlistadapter tasksonlistadapter;
    //ArrayList<Tasklistbean.DataBean.IndlibsyiesBean> list=new ArrayList<>();
    // ArrayList<Tasklistbean.DataBean.TaskBean> list2=new ArrayList<>();

     private Tasksonpresenter presnter;
    TextView title;
    Button back;
    private int titleid;
    private String titlename;
    TaskLineAdaper adaper;
    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }

    public void setTitleid(int titleid) {
        this.titleid = titleid;
    }




    private ArrayList<TaskListViewBean> list=new ArrayList<>();
    private ArrayList<TaskLineSonbean> list2=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // initlistview();
        //setListAdapter(tasksonlistadapter);
        return inflater.inflate(R.layout.tasklistaonfragment,container,false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);
       // listView=getListView();
        recyclerView= (RecyclerView) view.findViewById(R.id.tasklinerecyc);
        initrecyc();
    }

    private void initrecyc() {

        adaper=new TaskLineAdaper(getActivity(),list2);
        adaper.setTitilename(titlename);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       recyclerView.setAdapter(adaper);
        recyclerView.addItemDecoration(new DefaultItem());
       presnter=new Tasksonpresenter(getActivity(),this);
        presnter.getlistdata(this.titleid);
    }




    /*@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
       /* if(position>0&&position<list.size()+1){

            if(list.get(position-1).getHaschild()>0|list.get(position-1).getScore()!=0){
                Intent intent=new Intent(getActivity(),TaskindexoneActivity.class);
                intent.putExtra("indexname",list.get(position-1).getTitle());
                intent.putExtra("ndlibsysid",list.get(position-1).getId());
                intent.putExtra("titleid",titleid);
                Global.TaskVpTitle=titlename;
                Global.TaskVpTitleId=titleid;
                startActivity(intent);
            }}
        if(list2.size()>0&&position>list.size()&&position<list.size()+list2.size()+1){
            Intent intent=new Intent(getActivity(),TaskdetailActivity.class);
            intent.putExtra("id",list2.get(position-list.size()-1).getId());
            intent.putExtra("taget",list2.get(position-list.size()-1).getTarget());
            startActivity(intent);
        }



    }*/


    private void initlistview() {
        //tasksonlistadapter=new Tasksonlistadapter(list,getContext());
      //  presnter=new Tasksonpresenter(getContext(),this);



    }


    @Override
    public void onDestroy() {
        super.onDestroy();
      /* presnter.unbind();
        presnter=null;
       // list.clear();*/
      list.clear();
    }

    @Override
    public void getdatasuccess(TaskLineBean tasklistbean) {
         /*  list.clear();
          list.addAll(tasklistbean.getData().getIndlibsyies());
         if(tasklistbean.getData().getTask()!=null&&tasklistbean.getData().getTask().size()>0){
               list2.clear();
               list2.addAll(tasklistbean.getData().getTask());
               tasksonlistadapter.setList2(list2);
         }
          tasksonlistadapter.notifyDataSetChanged();*/
    }

    @Override
    public void getyijishuju(ArrayList<TaskListViewBean> list) {
           this.list.clear();
           this.list.addAll(list);
            chulishuju();
    }
  //得到的数据再处理
    private double chulishuju() {
        double sum=0;
        double sun=0;
        if(list.size()>0){
       for(TaskListViewBean bean:list){
              String level=Global.leves[bean.getLevel()];
              String  name=bean.getName();
               TaskLineSonbean tasklinebean=new TaskLineSonbean();
           tasklinebean.setLevel(level);
           tasklinebean.setTaskname(name);
              list2.add(tasklinebean);
              if(bean.getChild()!=null&&bean.getChild().size()>0){
                sum=dochild(bean.getChild());
               sun=sun+sum;
              }
            tasklinebean.setScore(String.valueOf(sum));

       }

        }
        adaper.notifyDataSetChanged();
       adaper.setSum(String.valueOf(sun));
       return  sun;

    }
  // 处理子数据
    private double dochild(List<TaskListViewBean.ChildBean> childs) {
        double sum=0;
        double tasksum=0;
        for(TaskListViewBean.ChildBean child:childs){
               String level=Global.leves[child.getLevel()];
                String name=child.getName();
              TaskLineSonbean tasklinebean=new TaskLineSonbean();
              tasklinebean.setLevel(level);
              tasklinebean.setTaskname(name);

              list2.add(tasklinebean);
               if(child.getChild()!=null&&child.getChild().size()>0){
                   dochild(child.getChild());
               } else if(child.getMytask()!=null&&child.getMytask().size()>0){
               tasksum= dotask(child.getMytask()) ;
                tasklinebean.setScore(String.valueOf(tasksum));
                sum=sum+tasksum;
               }

          }
          return  sum;

    }
   //处理任务数据
    private double dotask(List<TaskListViewBean.ChildBean.MytaskBean> mytask) {
         double sun=0;
        for(TaskListViewBean.ChildBean.MytaskBean bean :mytask){
                 String  score=bean.getScore();
                 String name=bean.getTarget();
                int  id=bean.getId();
                 TaskLineSonbean tasklinebean=new TaskLineSonbean();
                 tasklinebean.setScore(score);
                 tasklinebean.setTaskname(name);
                 tasklinebean.setIstask(true);
                 tasklinebean.setId(id);
                  sun=sun+Double.valueOf(bean.getScore());
                 list2.add(tasklinebean);
             }
          return sun;
    }


}

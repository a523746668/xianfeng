package com.qingyii.hxtz.wmcj.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskLineSonbean;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.TaskDetailActivity;


import java.util.ArrayList;

/**
 * Created by zhf on 2017/10/10.
 */

public class TaskLineAdaper extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
      Context context;
      ArrayList<TaskLineSonbean> list;
      LayoutInflater inflater;
      private String titilename;
      private String sum;

    public void setSum(String sum) {
        this.sum = sum;
    }

    public void setTitilename(String titilename) {
        this.titilename = titilename;
    }

    public TaskLineAdaper(Context context, ArrayList<TaskLineSonbean> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(context,inflater.inflate(R.layout.tasklineadd,parent,false));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
       /* TaskLineBean.DataBean.MylibrarysystemBean.ChildBeanX bean=list.get(position);
       AutoLinearLayout layout= (AutoLinearLayout) holder.getView(R.id.taskline);
        View view=inflater.inflate(R.layout.tasklineadd,layout);
        TextView tvcj= (TextView) view.findViewById(R.id.tasklinecj);
        tvcj.setText(Global.leves[bean.getLevel()]);
        TextView tvtitle= (TextView) view.findViewById(R.id.tasklinetitle);
        tvtitle.setText(bean.getName());
        TextView tvfenshu= (TextView) view.findViewById(R.id.tasklinefenshu);
       tvfenshu.setVisibility(View.INVISIBLE);
         for(TaskLineBean.DataBean.MylibrarysystemBean.ChildBeanX.ChildBean childBean:bean.getChild()){
          addchild(view,layout,childBean);
         } */
        TextView tvcj     =  holder.getTextView(R.id.tasklinecj);
        TextView tvtitle  =  holder.getTextView(R.id.tasklinetitle);
        TextView tvscore  =  holder.getTextView(R.id.tasklinefenshu);
        TextView shuixian1=  holder.getTextView(R.id.shuxian1);
        TextView shuixian2=  holder.getTextView(R.id.shuxian2);
        holder.itemView.setClickable(true);
         if(position<list.size()&&list.get(position).istask()){
           tvcj.setVisibility(View.INVISIBLE);
             tvtitle.setText(list.get(position).getTaskname());
             tvscore.setText(list.get(position).getScore()+"分");
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent=new Intent(context,TaskDetailActivity.class);
                     intent.putExtra("id",list.get(position).getId());
                     intent.putExtra("taget",list.get(position).getTaskname());
                     intent.putExtra("system_id",list.get(position).getSystem_id());
                     intent.putExtra("title",titilename);
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     context.startActivity(intent);
                  }
             });
             tvtitle.setGravity(Gravity.NO_GRAVITY);
             tvscore.setVisibility(View.VISIBLE);

           } else if(position==list.size()){
                  tvtitle.setText("总分");
                  tvtitle.setGravity(Gravity.CENTER);
                  tvtitle.setTextColor(context.getResources().getColor(R.color.white));
                  tvscore.setText(sum+"分");
                  tvscore.setTextColor(context.getResources().getColor(R.color.white));
                  tvcj.setVisibility(View.INVISIBLE);
             holder.itemView.setClickable(false);
             holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.textcolorred));
             return;
         }
         else {
               if(list.get(position).getLevel().equalsIgnoreCase("一级")){
                     holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.textColorShallow));
                } else if(list.get(position).getLevel().equalsIgnoreCase("二级")){
                   holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.tasklisttextview));
                }
               tvcj.setTextColor(context.getResources().getColor(R.color.textcolorred));
               tvcj.setVisibility(View.VISIBLE);
               tvtitle.setText(list.get(position).getTaskname());
               tvtitle.setGravity(Gravity.NO_GRAVITY);
               tvscore.setText(list.get(position).getScore()+"分");
               tvcj.setText(list.get(position).getLevel());
               if(list.get(position).getTaskname().contains("加分项")){

                   tvscore.setVisibility(View.INVISIBLE);
               }
               return;
           }
           tvscore.setVisibility(View.VISIBLE);
          tvtitle.setTextColor(context.getResources().getColor(R.color.black));
          tvscore.setTextColor(context.getResources().getColor(R.color.black));
          holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
    }

   /* private void addchild(View view, AutoLinearLayout layout, TaskLineBean.DataBean.MylibrarysystemBean.ChildBeanX.ChildBean childBean){
        layout.addView(view);
        TextView tvcj1= (TextView) view.findViewById(R.id.tasklinecj);
        TextView tvtitle1= (TextView) view.findViewById(R.id.tasklinetitle);
        TextView tvfenshu1= (TextView) view.findViewById(R.id.tasklinefenshu);
        tvcj1.setText(Global.leves[childBean.getLevel()]);
        tvtitle1.setText(childBean.getName());
        tvfenshu1.setVisibility(View.INVISIBLE);
        if(childBean.getChild()!=null&&childBean.getChild().size()>0){
          for(TaskLineBean.DataBean.MylibrarysystemBean.ChildBeanX.ChildBean childBean1 :childBean.getChild()){
              addchild(view,layout,childBean1);
          }
        } else  if(childBean.getMytask()!=null&&childBean.getMytask().size()>0) {
           for(TaskLineBean.DataBean.MylibrarysystemBean.ChildBeanX.ChildBean.MytaskBean taskbean:childBean.getMytask()){
                  layout.addView(view);
               TextView tvcj2= (TextView) view.findViewById(R.id.tasklinecj);
               TextView tvtitle2= (TextView) view.findViewById(R.id.tasklinetitle);
               TextView tvfenshu2= (TextView) view.findViewById(R.id.tasklinefenshu);
               tvcj2.setVisibility(View.INVISIBLE);
               tvtitle2.setText(taskbean.getTarget());
               tvfenshu2.setText(taskbean.getScore());

           }
        }
    }*/

    @Override
    public int getItemCount() {
         if(list!=null&&list.size()>0){
             return  list.size()+1;
         }
        return 0;
    }
}

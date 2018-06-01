package com.qingyii.hxtz.zhf.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.zhf.bean.Tasklistbean;

import java.util.ArrayList;



public class Tasksonlistadapter extends BaseAdapter {
  private ArrayList<Tasklistbean.DataBean.IndlibsyiesBean> list=new ArrayList<>();
  private ArrayList<Tasklistbean.DataBean.TaskBean> list2=new ArrayList<>();

    public ArrayList<Tasklistbean.DataBean.TaskBean> getList2() {
        return list2;
    }

    public void setList2(ArrayList<Tasklistbean.DataBean.TaskBean> list2) {
        this.list2 = list2;
    }

    private Context context;

    public Tasksonlistadapter(ArrayList<Tasklistbean.DataBean.IndlibsyiesBean> list, Context context) {

        this.list=list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(list!=null&&list.size()>0&&list2!=null&&list2.size()>0){
            return  list.size()+2+list2.size();
        }else if((list!=null&&list.size()>0)&&(list2==null||list2.size()<=0)) {

            return  list.size()+2;
        }else if(list.size()<=0&&list2.size()>0){
            return  list2.size()+2;

        }

        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            //使用自定义的list_items作为Layout
            convertView = LayoutInflater.from(context).inflate(R.layout.tasklistsonlist, parent, false);
            //使用减少findView的次数
            holder = new ViewHolder();
            holder.name = ((TextView) convertView.findViewById(R.id.tasklistname));
            holder.score= ((TextView) convertView.findViewById(R.id.tasklistscore));
            holder.zk = ((TextView) convertView.findViewById(R.id.tasklistzk));
            holder.iv= (ImageView) convertView.findViewById(R.id.unzk);
            //设置标记
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       //如果只有list有数据
          if(list.size()>0&&list2.size()<=0) {
              if (position == 0) {
                  holder.name.setText("考核指标名称");
                  holder.name.setGravity(Gravity.CENTER);
                  holder.score.setText("分数");
                  holder.zk.setText("展开");
                  holder.zk.setVisibility(View.VISIBLE);
                  holder.iv.setVisibility(View.INVISIBLE);
                  convertView.setBackgroundColor(context.getResources().getColor(R.color.tasklisttextview));
                  return  convertView;
              } else if (position == list.size() + 1) {
                  holder.name.setText("总计");

                  int sum = 0;

                  for (Tasklistbean.DataBean.IndlibsyiesBean bean : list) {
                      if (Integer.valueOf(bean.getScore()) != 0) {
                          sum = sum + Integer.valueOf(bean.getScore());
                      }

                  }
                  holder.iv.setVisibility(View.VISIBLE);
                  holder.zk.setVisibility(View.INVISIBLE);
                  holder.iv.setImageResource(R.mipmap.into_unclick);
                  holder.name.setGravity(Gravity.CENTER);
                  holder.score.setText(String.valueOf(sum));
                  convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
                  return  convertView;
              } else {
                  holder.name.setText(list.get(position - 1).getTitle());

                  holder.score.setText(String.valueOf(list.get(position - 1).getScore()));


                  convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
                  if (list.get(position - 1).getHaschild() > 0|list.get(position-1).getScore()!=0) {
                      holder.iv.setImageResource(R.mipmap.into_hold);
                  }
              }
          }
       //两个列表都有数据
          else if(list.size()>0&&list2.size()>0){
              if (position == 0) {
                  holder.name.setText("考核指标名称");
                  holder.name.setGravity(Gravity.CENTER);
                  holder.score.setText("分数");
                  holder.zk.setText("展开");
                  holder.zk.setVisibility(View.VISIBLE);
                  holder.iv.setVisibility(View.INVISIBLE);
                  convertView.setBackgroundColor(context.getResources().getColor(R.color.tasklisttextview));
              }   else if(position>0&&position<list.size()+1){
                  holder.name.setText(list.get(position - 1).getTitle());

                  holder.score.setText(String.valueOf(list.get(position - 1).getScore()));


                  convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
                  if (list.get(position - 1).getHaschild() > 0|list.get(position-1).getScore()!=0) {
                      holder.iv.setImageResource(R.mipmap.into_hold);
                  }
              }
                else if(position>list.size()&&position<list.size()+list2.size()+1){
                     holder.name.setText(list2.get(position-list.size()-1).getTarget());
                     holder.score.setText(list2.get(position-list.size()-1).getScore());
                      holder.iv.setImageResource(R.mipmap.into_hold);
              }

              else if(position==list.size()+list2.size()+1){
                  holder.name.setText("总计");
                  int sum = 0;
                  int sum2=0;
                  for(Tasklistbean.DataBean.IndlibsyiesBean bean :list){
                      sum= (int) (Double.valueOf(bean.getScore())+ sum);
                    }
                  for(Tasklistbean.DataBean.TaskBean  bean2 :list2){
                      if(!TextUtils.isEmpty(bean2.getScore())){
                      sum2= (int) (Double.valueOf(bean2.getScore()) +sum2);}
                  }
                  holder.name.setGravity(Gravity.CENTER);
                  holder.zk.setVisibility(View.VISIBLE);
                  holder.iv.setVisibility(View.INVISIBLE);
                holder.score.setText(String.valueOf(sum+sum2));
                  holder.iv.setImageResource(R.mipmap.into_unclick);
                  convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
                  return  convertView;
              }

          }
          //只有list2有数据
          else if(list.size()<=0&&list2.size()>0){
              if (position == 0) {
                  holder.name.setText("考核指标名称");
                  holder.name.setGravity(Gravity.CENTER);
                  holder.score.setText("分数");
                  holder.zk.setText("展开");
                  holder.zk.setVisibility(View.VISIBLE);
                  holder.iv.setVisibility(View.INVISIBLE);
                  convertView.setBackgroundColor(context.getResources().getColor(R.color.tasklisttextview));
                  return  convertView;
              } else if (position == list2.size() + 1) {
                  holder.name.setText("总计");

                  double sum = 0;

                  for (Tasklistbean.DataBean.TaskBean bean : list2) {
                      if (Double.valueOf(bean.getScore()) != 0) {
                          sum = sum + Double.valueOf(bean.getScore());
                      }

                  }
                  holder.zk.setVisibility(View.INVISIBLE);
                  holder.iv.setVisibility(View.VISIBLE);
                  holder.iv.setImageResource(R.mipmap.into_unclick);
                  holder.name.setGravity(Gravity.CENTER);
                  holder.score.setText(String.valueOf(sum));
                  convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
                  return  convertView;
              } else {
                  holder.name.setText(list2.get(position - 1).getTarget());

                  holder.score.setText(String.valueOf(list2.get(position - 1).getScore()));


                  convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
                  holder.iv.setImageResource(R.mipmap.into_hold);

              }

          }
         holder.name.setGravity(Gravity.CENTER_VERTICAL);
        holder.iv.setVisibility(View.VISIBLE);
        holder.zk.setVisibility(View.INVISIBLE);
        convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        return convertView;
    }

    /**
     * ViewHolder类
     */
    static class ViewHolder {
         TextView  name;
        TextView    score;
        TextView   zk;
        ImageView iv;
    }

}
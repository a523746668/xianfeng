package com.zhf.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.qingyii.hxt.R;
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxt.base.adapter.BaseRecyclerViewHolder;
import com.zhf.Util.HintUtil;
import com.zhf.IssusetaskActivity;
import com.zhf.Util.Global;
import com.zhf.bean.Taskdetailbean;
import java.util.List;


//任务清单Rcyclerview对应
public class Taskdetailadapter extends BaseRecyclerAdapter<Taskdetailbean> {
    private Context context;

    public Taskdetailadapter(List<Taskdetailbean> data,Context context) {
        super(data);
        this.context=context;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.taskrecyc1;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, Taskdetailbean item) {
           holder.getTextView(R.id.tvrequeir).setText(item.getData().getTask().getTarget());
           holder.getTextView(R.id.tvtime).setText(item.getData().getTask().getBegin_at()+"--"+item.getData().getTask().getEnd_at());
          holder.getTextView(R.id.tvsouce).setText(item.getData().getTask().getIndustryName());
          holder.getTextView(R.id.tvmesage).setText(item.getData().getTask().getData());
         holder.getTextView(R.id.tasktvnum).setText(String.valueOf(item.getData().getTask().getNum()));
          holder.getTextView(R.id.taskright6).setText(item.getData().getTask().getAccountName());
        Button  button=holder.getButton(R.id.taskrecycfabu);
        Button   button1=holder.getButton(R.id.taskrecyc1button);
       button1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               HintUtil.showtoast(context,"您暂时没有权限");
           }
       });
       if(item.getData().getTask().getIs_input()==1){

           button.setVisibility(View.VISIBLE);
           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(context, IssusetaskActivity.class);
                   Global.taskdetailbean=item;
                   context.startActivity(intent);
               }
           });
           return;
       }

       button.setVisibility(View.GONE);

    }
}
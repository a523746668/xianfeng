package com.qingyii.hxtz.wmcj.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Taskdetailbean;
import com.qingyii.hxtz.zhf.IssusetaskActivity;
import com.qingyii.hxtz.zhf.Util.Global;
import com.qingyii.hxtz.zhf.Util.HintUtil;


import java.util.List;


//任务清单Rcyclerview对应
public class Taskdetailadapter extends BaseRecyclerAdapter<Taskdetailbean> {
    private Context context;
   private int system_id;
    public Taskdetailadapter(List<Taskdetailbean> data, Context context,int system_id) {
        super(data);
        this.context=context;
        this.system_id=system_id;
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
                   intent.putExtra("system_id",system_id);
                   Global.taskdetailbean=item;
                   context.startActivity(intent);
               }
           });
           return;
       }

       button.setVisibility(View.GONE);

    }
}

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
import com.qingyii.hxtz.wmcj.mvp.model.bean.Resultbean;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.ResultPostionActivity;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhf on 2017/9/23.
 */

public class ResultsonAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
  private Context context;
    private ArrayList<Resultbean.DataBean.BrothindustryBean> mData;
    public ResultsonAdapter(List<Resultbean.DataBean.BrothindustryBean> data, Context context) {
         this.mData = (ArrayList<Resultbean.DataBean.BrothindustryBean>) data;
        this.context=context;
    }

    @Override
    public int getItemCount() {
        if(mData!=null&&mData.size()>0){
            return mData.size()+1;
        }
        return  0;
    }



    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          View v= LayoutInflater.from(context).inflate(R.layout.resultsonlist, parent,false);
          return  new BaseRecyclerViewHolder(context,v);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if(position==0){
            TextView tv= holder.getTextView(R.id.tasklistname);
            tv.setText("直属下级名称");
            tv.setGravity(Gravity.CENTER);
            holder.getTextView(R.id.tasklistscore).setText("得分/总分");
            holder.getTextView(R.id.tasklistzk).setText("排名");
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.tasklisttextview));

        } else {

            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.getTextView(R.id.tasklistname).setText(mData.get(position-1).getName());
            holder.getTextView(R.id.tasklistname).setGravity(Gravity.CENTER_VERTICAL);
            holder.getTextView(R.id.tasklistscore).setText( mData.get(position-1).getMyscore()+"/"+ mData.get(position-1).getScore());
          //  holder.getTextView(R.id.tasklistzk).setText(mData.get(position-1).getPostion());
            holder.getTextView(R.id.tasklistzk).setText(String.valueOf(mData.get(position-1).getRanking()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ResultPostionActivity.class);
                    intent.putExtra("industryid",mData.get(position-1).getIndustry_sub_id());
                    context.startActivity(intent);
                }
            });
        }
    }


}

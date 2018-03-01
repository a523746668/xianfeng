package com.zhf.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qingyii.hxt.R;
import com.qingyii.hxt.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxt.wheelpicker.picker.NumberPicker;
import com.zhf.bean.WorkParkitembean;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/9/29.
 */

public class WorkParkitemAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

      private Context context;
      private ArrayList<WorkParkitembean.DataBean.AllactivityBean> list;
     private LayoutInflater inflater;

    public WorkParkitemAdapter(Context context, ArrayList<WorkParkitembean.DataBean.AllactivityBean> list) {
        this.context = context;
        this.list = list;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return   new BaseRecyclerViewHolder(context ,inflater.inflate(viewType,parent,false)) ;
    }


    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        String  item=list.get(position).getType();

          if(item.equalsIgnoreCase("lessthire")){

        }else if (item.equalsIgnoreCase("alltext")){

        } else {

        }

    }



    @Override
    public int getItemCount() {
        if(list!=null&&list.size()>0){
            return  list.size();
        }

        return 0;
    }

    @Override
    public int getItemViewType(int position) {
       String  item=list.get(position).getType();


        if(item.equalsIgnoreCase("lessthire")){
      return   R.layout.workpark_content_layout1;
       }else if (item.equalsIgnoreCase("alltext")){
               return R.layout.alltext;
        } else {
       return   R.layout.workpark_content_layout2;
       }

    }
}

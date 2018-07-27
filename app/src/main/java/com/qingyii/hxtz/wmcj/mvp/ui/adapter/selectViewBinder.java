package com.qingyii.hxtz.wmcj.mvp.ui.adapter;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.bean.ReportBean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Common;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportMenu;

import org.geometerplus.android.fbreader.api.FBReaderIntents;
import org.simple.eventbus.EventBus;

import me.drakeet.multitype.ItemViewBinder;

public class selectViewBinder extends ItemViewBinder<Common, selectViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.category_recyclerview_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Common select) {
        holder.textView.setText( select.getTitle());
        if(select.ischeck){
            holder.imageView.setVisibility(View.VISIBLE);
        }else {
            holder.imageView.setVisibility(View.GONE);
        }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Message msg=Message.obtain();
              if(select.ischeck){
                  select.setIscheck(false);
                  msg.arg1=1;//删除
                  holder.imageView.setVisibility(View.GONE);
              } else {
                  msg.arg1=2;//增加
                  select.setIscheck(true);
                  holder.imageView.setVisibility(View.VISIBLE);
              }


               if(select instanceof ReportMenu.DataBean.TagItemBean){

                   ReportMenu.DataBean.TagItemBean bean= (ReportMenu.DataBean.TagItemBean) select;
                    msg.obj=bean;
                    msg.arg2=1;
                   EventBus.getDefault().post(msg,EventBusTags.WMCJ_Adapter_Select);
                 return;
               }
               if(select instanceof ReportMenu.DataBean.MenuItemBean) {
                   ReportMenu.DataBean.MenuItemBean bean= (ReportMenu.DataBean.MenuItemBean) select;
                   msg.obj=bean;
                   msg.arg2=2;
                   EventBus.getDefault().post(msg,EventBusTags.WMCJ_Adapter_Select);
                return;
               }
               if(select instanceof ReportMenu.DataBean.MenuItem1Bean){
                   ReportMenu.DataBean.MenuItem1Bean bean= (ReportMenu.DataBean.MenuItem1Bean) select;
                   msg.obj=bean;
                   msg.arg2=3;
                   EventBus.getDefault().post(msg,EventBusTags.WMCJ_Adapter_Select);
                    return;
               }
               if(select instanceof ReportMenu.DataBean.AllsonIndustryBean){
                   ReportMenu.DataBean.AllsonIndustryBean bean= (ReportMenu.DataBean.AllsonIndustryBean) select;
                   msg.obj=bean;
                   msg.arg2=4;
                   EventBus.getDefault().post(msg,EventBusTags.WMCJ_Adapter_Select);
                    return;
               }
           }
       });


    }

    static class ViewHolder extends RecyclerView.ViewHolder {
          TextView textView;
          ImageView imageView;
          ViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.category_item_tv);
            imageView= (ImageView) itemView.findViewById(R.id.itemtishi);
        }
    }
}

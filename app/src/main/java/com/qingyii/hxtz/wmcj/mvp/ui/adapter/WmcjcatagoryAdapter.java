package com.qingyii.hxtz.wmcj.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.app.ButterKnifeKt;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportMenu;

import java.util.ArrayList;
import java.util.List;

public class WmcjcatagoryAdapter extends RecyclerView.Adapter {
    ArrayList<ReportMenu.DataBean.AllsonIndustryBean> alldanwei=new ArrayList<>();
    ArrayList<ReportMenu.DataBean.MenuItemBean> ones=new ArrayList<>();
    ArrayList<ReportMenu.DataBean.MenuItem1Bean> twos=new ArrayList<>();
    ArrayList<ReportMenu.DataBean.TagItemBean> tags=new ArrayList<>();
    private Context context;

    public WmcjcatagoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==R.layout.wmcj_recyc){
            return  new MyViewHolder2(LayoutInflater.from(context).inflate(viewType,null));
        } else {
            return  new MyViewHolder(LayoutInflater.from(context).inflate(viewType,null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
              if(holder instanceof MyViewHolder){
                 TextView tv=((MyViewHolder) holder).textView;
                 ImageView iv=((MyViewHolder) holder).iv;
                 if(position>0&&position<tags.size()+1){
                     tv.setText(tags.get(position-1).getTitle());
                 }

              } else if(holder instanceof  MyViewHolder){

              }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0||position==tags.size()+1||position==ones.size()+tags.size()+2||position==ones.size()+twos.size()+tags.size()+3){
            return R.layout.wmcj_recyc;
        } else {
            return R.layout.category_recyclerview_item;
        }
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{
         ImageView iv;
         TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
          iv= (ImageView) itemView.findViewById(R.id.category_item_tv);
          textView= (TextView) itemView.findViewById(R.id.itemtishi);
        }
        }

      class  MyViewHolder2 extends RecyclerView.ViewHolder{
          TextView textView;
          public MyViewHolder2(View itemView) {
              super(itemView);
              textView= (TextView) itemView.findViewById(R.id.category_tv_title);
          }
      }
    //检查列表，避免空指针
    public<T> boolean isnull(List<T> list){
        if(list!=null&&list.size()>0){
            return  true;
        }
        return  false;
    }
    @Override
    public int getItemCount() {
        int i=0;
        if(isnull(ones)){
            i=i+ones.size()+1;
        }
        if(isnull(twos)){
            i=i+twos.size()+1;
        }

        if(isnull(tags)){
            i=i+tags.size()+1;
        }
        if(isnull(alldanwei)){
            i=i+alldanwei.size()+1;
        }
        return  i;
    }
}

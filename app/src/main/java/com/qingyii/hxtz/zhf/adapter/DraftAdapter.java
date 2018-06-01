package com.qingyii.hxtz.zhf.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxtz.zhf.Sqlitehelper.Drafitbean;

import java.util.List;

/**
 * Created by zhf on 2017/10/24.
 */

public class DraftAdapter extends BaseRecyclerAdapter<Drafitbean> {
  private Context context;


    public DraftAdapter(List<Drafitbean> data) {
        super(data);
    }
    public DraftAdapter(List<Drafitbean> data,Context context) {
           super(data);
            this.context=context;
    }



    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.draftlist;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, Drafitbean item) {
         String str=item.getName();
        Log.i("tmdadater",str);
          String[] strs=str.split(",");
                 Log.i("tmdadapter",strs[0]);
         holder.getTextView(R.id.draftrecycname).setText(strs[0]);
        holder.getTextView(R.id.draftrecyctime).setText(strs[1]);
        if(mClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mClickListener.onItemLongClick(item,v,position);
                    return  true;
                }
            });
        }


    }
}

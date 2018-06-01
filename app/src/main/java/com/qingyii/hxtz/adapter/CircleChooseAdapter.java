package com.qingyii.hxtz.adapter;

import com.qingyii.hxtz.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.pojo.Category;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Taskdetailbean;

import java.util.List;

/**
 * Created by 63264 on 16/10/8.
 */

public class CircleChooseAdapter extends BaseAdapter {

    private List<Category.DataBean> cDataBeenList;
    private Context context;

    private List<Taskdetailbean.DataBean.IndustryParentBean> industryParent;

    public CircleChooseAdapter(Context abBaseActivity, List<Category.DataBean> cDataBeenList) {
        this.context = abBaseActivity;
        this.cDataBeenList = cDataBeenList;
    }

    public CircleChooseAdapter(Context context, List<Taskdetailbean.DataBean.IndustryParentBean> industryParent,int i) {
        this.context = context;
        this.industryParent = industryParent;
    }

    @Override
    public int getCount() {
       if(cDataBeenList!=null){
           return cDataBeenList.size();
       }
       if(industryParent!=null){
           return  industryParent.size();
         }
         return 0;

    }

    @Override
    public Object getItem(int position) {
        return cDataBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_circle_choose, null);
            vh = new ViewHolder();
            vh.item_circle_choose_tv = (TextView) convertView.findViewById(R.id.item_circle_choose_tv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
          if(industryParent!=null){
              vh.item_circle_choose_tv.setText(industryParent.get(position).getName());
          }
          else {
              vh.item_circle_choose_tv.setText(cDataBeenList.get(position).getName());
          }

        return convertView;
    }

    class ViewHolder {
        TextView item_circle_choose_tv;
    }
}

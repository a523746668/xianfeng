package com.qingyii.hxtz.adapter;

import com.qingyii.hxtz.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.pojo.Category;

import java.util.List;

/**
 * Created by 63264 on 16/10/8.
 */

public class CircleChooseAdapter extends BaseAdapter {
    private AbBaseActivity abBaseActivity;
    private List<Category.DataBean> cDataBeenList;

    public CircleChooseAdapter(AbBaseActivity abBaseActivity, List<Category.DataBean> cDataBeenList) {
        this.abBaseActivity = abBaseActivity;
        this.cDataBeenList = cDataBeenList;
    }

    @Override
    public int getCount() {
        return cDataBeenList.size();
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
            convertView = View.inflate(abBaseActivity, R.layout.item_circle_choose, null);
            vh = new ViewHolder();
            vh.item_circle_choose_tv = (TextView) convertView.findViewById(R.id.item_circle_choose_tv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.item_circle_choose_tv.setText(cDataBeenList.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView item_circle_choose_tv;
    }
}

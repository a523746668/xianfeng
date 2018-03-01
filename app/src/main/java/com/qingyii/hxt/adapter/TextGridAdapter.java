package com.qingyii.hxt.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.qingyii.hxt.AuditCrewListActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.customview.MyGridView;
import com.qingyii.hxt.pojo.AuditCrewList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 63264 on 16/9/22.
 */

public class TextGridAdapter extends BaseAdapter {
    private Activity activity;
    private GridView gridView;
    private List<String> count;
    public static int ROW_NUMBER = 5;

    public TextGridAdapter(Activity activity, GridView gridView, List<String> count) {
        this.activity = activity;
        this.gridView = gridView;
        this.count = count;
    }

    @Override
    public int getCount() {
        return count.size();
    }

    @Override
    public Object getItem(int position) {
        return count.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = convertView.inflate(activity, R.layout.text_grid_item, null);
            holder = new ViewHolder();
            holder.textview = (TextView) convertView.findViewById(R.id.textview);
            //高度计算
//            AbsListView.LayoutParams param = new AbsListView.LayoutParams(
//                    android.view.ViewGroup.LayoutParams.FILL_PARENT,
//                    gridView.getHeight() / ROW_NUMBER);
//            convertView.setLayoutParams(param);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            holder.textview.setText(count.get(position) + "");

        } catch (Exception e) {
            Log.e("AuditCrewListAdapter", e.toString());
        }

        return convertView;
    }

    class ViewHolder {
        TextView textview;
    }
}
package com.qingyii.hxt.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.pojo.MainModuleGrid;

import java.util.List;

/**
 * Created by XRJ on 16/12/21.
 */

public class MainModuleGridAdapter extends BaseAdapter {
    private Activity activity;
    private List<MainModuleGrid> mainModuleGridList;

    public MainModuleGridAdapter(Activity activity, List<MainModuleGrid> mainModuleGridList) {
        this.activity = activity;
        this.mainModuleGridList = mainModuleGridList;
    }

    @Override
    public int getCount() {
        return mainModuleGridList.size();
    }

    @Override
    public Object getItem(int i) {
        return mainModuleGridList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            //使用自定义的list_items作为Layout
            view = view.inflate(activity, R.layout.activity_main_grid_item, null);
            holder = new ViewHolder();
            holder.main_grid_item_iv = (ImageView) view.findViewById(R.id.main_grid_item_iv);
            holder.main_grid_item_tv = (TextView) view.findViewById(R.id.main_grid_item_tv);
            holder.main_grid_item_tv_tip = (TextView) view.findViewById(R.id.main_grid_item_tv_tip);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        MainModuleGrid mainModuleGrid = mainModuleGridList.get(i);

        holder.main_grid_item_iv.setBackgroundResource(mainModuleGrid.getModuleImage());

        holder.main_grid_item_tv.setText(mainModuleGrid.getModuleText() + "");

        holder.main_grid_item_tv_tip.setText(mainModuleGrid.getModuleTip() + "");

        if (mainModuleGrid.getModuleTip() > 0)
            holder.main_grid_item_tv_tip.setVisibility(View.VISIBLE);
        else
            holder.main_grid_item_tv_tip.setVisibility(View.GONE);

        return view;
    }

    class ViewHolder {
        ImageView main_grid_item_iv;
        TextView main_grid_item_tv;
        TextView main_grid_item_tv_tip;
    }
}

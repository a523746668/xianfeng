package com.zhf.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/9/29.
 */

public class WorkItemlbAdaper  extends PagerAdapter {
     private ArrayList<ImageView> list;

    public WorkItemlbAdaper(ArrayList<ImageView> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
         if(list!=null&&list.size()>0){

        return list.size();}
        return  0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return    list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
         container.removeView(list.get(position%list.size()));
    }
}

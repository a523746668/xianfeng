package com.zhf.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.jess.arms.widget.autolayout.AutoTabLayout;
import com.zhf.bean.WorkParkbean;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/9/29.
 */

public class WorkparkvpAdapter extends FragmentStatePagerAdapter {
     private ArrayList<Fragment> list;
     private ArrayList<WorkParkbean.DataBean.MenuItemBean> list1;
    private int mChildCount = 0;
     private AutoTabLayout tabLayout;

    public WorkparkvpAdapter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<WorkParkbean.DataBean.MenuItemBean> list1, AutoTabLayout tabLayout) {
        super(fm);
        this.list = list;
        this.list1 = list1;
        this.tabLayout = tabLayout;
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }



    public WorkparkvpAdapter(FragmentManager fm,ArrayList<Fragment> list,ArrayList<WorkParkbean.DataBean.MenuItemBean> list1) {
        super(fm);
        this.list=list;
        this.list1=list1;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
      if(list!=null&&list.size()>0){
          return  list.size();

      }

        return 0;
    }
/*
    @Override
    public CharSequence getPageTitle(int position) {


        return list1.get(position).getTitle();
    }
   */

}

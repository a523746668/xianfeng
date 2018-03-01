package com.zhf.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zhf.zfragment.TasklistsonFragment;

import java.util.ArrayList;



public class Taskvpadapter extends FragmentStatePagerAdapter {
   private Context context;
    private ArrayList<TasklistsonFragment> fragments;

    public Taskvpadapter(FragmentManager fm, Context context, ArrayList<TasklistsonFragment> fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }

    public Taskvpadapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return  fragments.get(position);
    }

    @Override
    public int getCount() {
        if(fragments!=null&&fragments.size()>0){
            return  fragments.size();
        }

        return 0;
    }
}

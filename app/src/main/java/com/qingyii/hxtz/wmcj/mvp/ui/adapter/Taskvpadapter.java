package com.qingyii.hxtz.wmcj.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.qingyii.hxtz.wmcj.mvp.ui.fragment.TaskListSonFragment;

import java.util.ArrayList;


public class Taskvpadapter extends FragmentStatePagerAdapter {
   private Context context;
    private ArrayList<TaskListSonFragment> fragments;

    public Taskvpadapter(FragmentManager fm, Context context, ArrayList<TaskListSonFragment> fragments) {
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

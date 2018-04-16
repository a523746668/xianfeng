package com.qingyii.hxtz.wmcj.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.qingyii.hxtz.wmcj.mvp.ui.fragment.ResultSonFragment;


import java.util.ArrayList;

/**
 * Created by zhf on 2017/9/22.
 */

public class Resultvpadapter extends FragmentStatePagerAdapter {
    private Context context;
    private ArrayList<ResultSonFragment> fragments;


    public Resultvpadapter(FragmentManager fm, Context context, ArrayList<ResultSonFragment> fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }

    public Resultvpadapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
       if(fragments!=null&&fragments.size()>0){
           return  fragments.size();
       }

        return 0;
    }
}

package com.qingyii.hxtz.zhf.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.qingyii.hxtz.zhf.zfragment.ImgandVideoFragment;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/12/13.
 */

public class ImgandVideoAdapter extends FragmentStatePagerAdapter {
       private ArrayList<ImgandVideoFragment> fragments;
       private Context context;
    public ImgandVideoAdapter(FragmentManager fm,ArrayList<ImgandVideoFragment> list,Context context) {
        super(fm);
        fragments=list;
        this.context=context;
        notifyDataSetChanged();
    }


    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
         if(fragments!=null&&fragments.size()>0) {
      return   fragments.size();
         }
        return 0;
    }
}

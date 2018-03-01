package com.qingyii.hxt.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by XRJ on 2016/4/29.
 */
public class TabLayoutPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private List<String> stringList;
    private int mChildCount = 0;

    public TabLayoutPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> stringList) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.fragmentList = fragmentList;
        this.stringList = stringList;
    }

//    public void setFragments(List<Fragment> fragmentList) {
    public void setFragments() {
        if (this.fragmentList != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (Fragment f : this.fragmentList) {
                fragmentTransaction.remove(f);
            }
            fragmentTransaction.commit();
            fragmentTransaction = null;
            fragmentManager.executePendingTransactions();
        }
//        this.fragmentList = fragmentList;
//        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    //强制重载Item
    @Override
    public int getItemPosition(Object object) {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
//        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragmentList.size();//页卡数
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);//页卡标题
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}

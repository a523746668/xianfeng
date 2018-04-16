package com.qingyii.hxtz.wmcj.mvp.ui.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.qingyii.hxtz.wmcj.mvp.model.entity.WorkParkData

/**
 * Created by xubo on 2017/9/14.
 */
class MYViewPagerAdapter(private val context: Context, fm: FragmentManager, private val list: List<Fragment>, private val titlesList: List<WorkParkData>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titlesList[position].title
    }

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }
}
package com.qingyii.hxtz.meeting.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.meeting.di.module.entity.MeetingList;
import com.qingyii.hxtz.meeting.mvp.ui.holder.MeetingItemHolder;

import java.util.List;


/**
 * Created by xubo on 9/4/16 12:57
 *
 */
public class MeetingAdapter extends DefaultAdapter<MeetingList> {


    public MeetingAdapter(List<MeetingList> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MeetingList> getHolder(View v, int viewType) {
        return new MeetingItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.meeting_recyclerview_item;
    }


}

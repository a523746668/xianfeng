package com.qingyii.hxtz.meeting.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.meeting.di.module.entity.MeetingList;

import butterknife.BindView;

/**
 * Created by xubo on 9/4/16 12:56
 *
 */
public class MeetingItemHolder extends BaseHolder<MeetingList> {
    @BindView(R.id.meeting_item_title)
    TextView meetingItemTitle;
    @BindView(R.id.meeting_item_unit)
    TextView meetingItemUnit;
    @BindView(R.id.meeting_item_train_type)
    TextView meetingItemTrainType;
    @BindView(R.id.meeting_item_address)
    TextView meetingItemAddress;
    @BindView(R.id.meeting_item_train_time)
    TextView meetingItemTrainTime;
    @BindView(R.id.meeting_item_mark)
    ImageView meetingItemMark;
    private AppComponent mAppComponent;

    public MeetingItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
//        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(MeetingList item, int position) {
    }

}

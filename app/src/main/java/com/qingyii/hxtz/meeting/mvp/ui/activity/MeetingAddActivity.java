package com.qingyii.hxtz.meeting.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.meeting.mvp.presenter.MeetingSearchPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MeetingAddActivity extends BaseActivity<MeetingSearchPresenter> implements CommonContract.MeetingSearchListView, SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.meeting_add_address_tv)
    TextView TvAddress;
    @BindView(R.id.toolbar_back)
    Button toolbarBack;
    @BindView(R.id.toolbar_right)
    Button toolbarRight;
    @BindView(R.id.meeting_add_title)
    EditText meetingAddTitle;
    @BindView(R.id.meeting_add_content)
    EditText meetingAddContent;
    @BindView(R.id.meeting_add_type)
    EditText meetingAddType;
    @BindView(R.id.meeting_add_tag)
    EditText meetingAddTag;
    @BindView(R.id.meeting_add_poster_iv)
    ImageView meetingAddPosterIv;
    @BindView(R.id.meeting_add_pic_btn)
    Button meetingAddPicBtn;
    @BindView(R.id.meeting_add_address_et)
    EditText meetingAddAddressEt;
    @BindView(R.id.meeting_add_start_time)
    TextView meetingAddStartTime;
    @BindView(R.id.meeting_add_end_time)
    TextView meetingAddEndTime;
    @BindView(R.id.meeting_add_feedback_time)
    TextView meetingAddFeedbackTime;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_meeting_add; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitle.setText(R.string.meeting_add);
        setDrawRight(new Rect(0, 0, 51, 49), R.drawable.gps_icon_selector, TvAddress);
        setDrawRight(new Rect(0, 0, 51, 49), R.drawable.time_pick_selector, meetingAddStartTime, meetingAddEndTime,meetingAddFeedbackTime);


//        mPresenter.requestMeetingLists(true);
    }

    //动态设置drawableRight大小
    private void setDrawRight(Rect bounds, int resId, TextView... view) {
        for (TextView textView : view) {
            Drawable drawable = getResources().getDrawable(resId);
            drawable.setBounds(bounds);
            textView.setCompoundDrawables(null, null, drawable, null);
        }
    }


    @Override
    public void showLoading() {
//        Observable.just(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading() {
//        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void setAdapter(BaseRecyclerAdapter adapter) {
//        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.setEmptyView(empty);
//        adapter.setOnItemClickListener((view, viewType, data, position) -> {
//            TrainSchedule.DataBean bean = (NoticeList.DataBean) data;
////            Intent intent = new Intent(NotifyActivity.this, NotifyDetailsActivity.class);
//            //修改缓存状态 为已阅读，数据库数据 在进入下一个页面后修改
////                iDataBeanlist.get(i).setMark(1);
//            bean.setReaded(1);
//            intent.putExtra("notify", bean);
//            startActivityForResult(intent, position);
//        });
        initListener();
    }

    private void initListener() {
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(this));
//        initPaginate();
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
    }

    @OnClick({R.id.toolbar_back_layout, R.id.toolbar_right_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_layout:
                killMyself();
                break;
            case R.id.toolbar_right_layout:
                break;
        }
    }

    @Override
    public void onRefresh() {
//        mPresenter.requestMeetingLists(true);
    }

}
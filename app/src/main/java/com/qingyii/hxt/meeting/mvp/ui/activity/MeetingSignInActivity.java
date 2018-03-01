package com.qingyii.hxt.meeting.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.meeting.di.component.DaggerMeetingDetailsComponent;
import com.qingyii.hxt.meeting.di.module.MeetingDetailsModule;
import com.qingyii.hxt.meeting.mvp.presenter.MeetingetailsPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 签到详情
 */
public class MeetingSignInActivity extends BaseActivity<MeetingetailsPresenter> implements CommonContract.MeetingDetailsView, View.OnClickListener {
    public static final String PARAMS = "meeting_data";
    @BindView(R.id.toolbar_back)
    Button toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    Button toolbarRight;
    @BindView(R.id.toolbar_right_tv)
    TextView toolbarRightTv;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMeetingDetailsComponent
                .builder()
                .appComponent(appComponent)
                .meetingDetailsModule(new MeetingDetailsModule(this))
                .build()
                .inject(this);
    }



    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_meeting_sign_in_details;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbarTitle.setText(R.string.sign_in_details);
        toolbarRightTv.setTextColor(getResources().getColorStateList(R.color.toolbar_right_tv_selector));
        toolbarRightTv.setVisibility(View.VISIBLE);
        toolbarRightTv.setText(R.string.scan_qr_code_sign_in);
    }

    @Nullable
    @OnClick({R.id.toolbar_back_layout, R.id.meeting_details_feedback, R.id.toolbar_right_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_layout:
                killMyself();
                break;
            case R.id.meeting_details_feedback:
                break;
            case R.id.toolbar_right_layout:
//                Intent summary = new Intent(this, MeetingSummaryActivity.class);
//                launchActivity(summary);
                break;
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        checkNotNull(message);
        UiUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void UpdateFeedbackStatus(String status) {

    }
}

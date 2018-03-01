package com.qingyii.hxt.meeting.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.UiUtils;
import com.qingyii.hxt.R;
import com.qingyii.hxt.meeting.mvp.ui.fragment.MeetingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MeetingActivity extends BaseActivity implements IView {

    @Nullable
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.meeting_viewPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_right)
    Button mRightBtn;
    @BindView(R.id.meeting_radioGroup)
    RadioGroup radioGroup;
    private List<BaseFragment> fragments;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_meeting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitle.setText(R.string.meeting_list);
        mRightBtn.setVisibility(View.VISIBLE);
        mRightBtn.setBackgroundResource(R.drawable.toolbar_right_add_selector);
//        //此处可自适应大小，设计图以1920*1080为准
//        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) mRightBtn.getLayoutParams();
//        lp.height = 70;
//        lp.width = 70;
//        mRightBtn.setLayoutParams(lp);
        initViewPager();
    }

    public void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(MeetingFragment.newInstance());
//        fragments.add(MeetingFragment.newInstance());
//        fragments.add(MeetingPublishBoxFragment.newInstance());
        final List<String> title = new ArrayList<>();
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setOffscreenPageLimit(fragments.size());// 这个是设置viewpager保留多少个显示界面
        viewPager.setAdapter(adapter);

    }

    @OnCheckedChanged({R.id.meeting_rb1, R.id.meeting_rb2, R.id.meeting_rb3})
    public void OnCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.meeting_rb1:
                    viewPager.setCurrentItem(0);
                    mTitle.setText(R.string.meeting_list);
                    break;
                case R.id.meeting_rb2:
                    viewPager.setCurrentItem(1);
                    mTitle.setText(R.string.meeting_list);
                    break;
                case R.id.meeting_rb3:
                    viewPager.setCurrentItem(2);
                    mTitle.setText(R.string.my_publish_box);
                    break;
            }
        }
    }

    @OnClick({R.id.toolbar_back, R.id.toolbar_right_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                killMyself();
                break;
            case R.id.toolbar_right_layout:
//                launchActivity(new Intent(this,MeetingAddActivity.class));
                showMessage("暂未开通此权限");
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

}
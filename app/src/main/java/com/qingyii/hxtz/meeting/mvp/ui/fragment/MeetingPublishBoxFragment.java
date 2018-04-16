package com.qingyii.hxtz.meeting.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.widget.AutoRadioGroup;
import com.qingyii.hxtz.base.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

/**
 * create by xubo 2017/06/20
 * 我的发布箱
 */
public class MeetingPublishBoxFragment extends BaseFragment {

    @BindView(R.id.meeting_publish_radioGroup)
    AutoRadioGroup mRadioGroup;
    @BindView(R.id.meeting_publish_viewPager)
    NoScrollViewPager mViewPager;
    private List<BaseFragment> fragments;
    public MeetingPublishBoxFragment() {
        // Required empty public constructor
    }

    public static MeetingPublishBoxFragment newInstance() {
        MeetingPublishBoxFragment fragment = new MeetingPublishBoxFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meeting_publish_box, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
        }
        initViewPager();
    }
    public void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(MeetingPublishItemFragment.newInstance());
        fragments.add(MeetingPublishItemFragment.newInstance());
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        mViewPager.setOffscreenPageLimit(fragments.size());// 这个是设置viewpager保留多少个显示界面
        mViewPager.setAdapter(adapter);

    }
    @OnCheckedChanged({R.id.meeting_publish_rb1, R.id.meeting_publish_rb2})
    public void OnCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.meeting_publish_rb1:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.meeting_publish_rb2:
                    mViewPager.setCurrentItem(1);
                    break;
            }
        }
    }
    @Override
    public void setData(Object data) {

    }

}

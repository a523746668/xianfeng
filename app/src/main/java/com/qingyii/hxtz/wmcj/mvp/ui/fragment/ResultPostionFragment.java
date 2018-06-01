package com.qingyii.hxtz.wmcj.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerResultComponent;
import com.qingyii.hxtz.wmcj.di.module.ResultModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskTitlebean;
import com.qingyii.hxtz.wmcj.mvp.presenter.ResultPresenter;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WMCJcategoryActivity;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.Resultvpadapter;
import com.zhy.autolayout.AutoLinearLayout;

import org.geometerplus.android.fbreader.api.FBReaderIntents;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zhf on 2018/3/23.
 */

public class ResultPostionFragment  extends BaseFragment<ResultPresenter> implements WMCJContract.ResultView {
   @BindView(R.id.resultfragsub)
    Button sub;

    @BindView(R.id.resultdad)
    Button add;

    @BindView(R.id.toolbar_back)
    Button back;

   @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.resulttitle)
    TextView name;

    @BindView(R.id.resultvp)
    ViewPager viewPager;
    Resultvpadapter adapter;

   @BindView(R.id.empty_view)
    AutoLinearLayout emtview;

    @BindView(R.id.resultall)
    AutoLinearLayout emtview2;

    @BindView(R.id.toolbar_right_tv)
    TextView barright;

    private ArrayList<TaskTitlebean.DataBean.LibsystemBean> titles=new ArrayList<>();
    private ArrayList<ResultSonFragment> fragments=new ArrayList<>();


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerResultComponent.builder()
                .appComponent(appComponent)
                .resultModule(new ResultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return   inflater.inflate(R.layout.resultpostion,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        init();
        mPresenter.getResultData();
    }

    private void init() {
        barright.setText("切换排行榜");
        barright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getActivity(), WMCJcategoryActivity.class);
               intent.putExtra("wmcj","Result");
               startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(true, EventBusTags.HOME);
                getActivity().finish();
            }
        });

        if(viewPager.getCurrentItem()==0){
            sub.setBackgroundResource(R.mipmap.leftbutton_hold);
        }

        if(viewPager.getCurrentItem()==fragments.size()-1){
            add.setBackgroundResource(R.mipmap.rightbutton_hold);
        }

        adapter=new Resultvpadapter(getFragmentManager(),getActivity(),fragments);
        viewPager.setAdapter(adapter);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getCount()>0&&viewPager.getCurrentItem()>0){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
                    name.setText(titles.get(viewPager.getCurrentItem()).getTitle());
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getCount()>0&&viewPager.getCurrentItem()<adapter.getCount()-1){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    name.setText(titles.get(viewPager.getCurrentItem()).getTitle());

                }
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                name.setText(titles.get(position).getTitle());
                if(position==0&&adapter.getCount()>1){
                    sub.setBackgroundResource(R.mipmap.leftbutton_hold);
                    add.setBackgroundResource(R.mipmap.rightbutton_unclick);
                }else if(position==fragments.size()-1&&position!=0){
                    add.setBackgroundResource(R.mipmap.rightbutton_hold);
                    sub.setBackgroundResource(R.mipmap.leftbutton_unclick);

                }   else {
                    sub.setBackgroundResource(R.mipmap.leftbutton_unclick);
                    add.setBackgroundResource(R.mipmap.rightbutton_unclick);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emtview2= (AutoLinearLayout) view.findViewById(R.id.resultall);
        emtview= (AutoLinearLayout) view.findViewById(R.id.empty_view);
        title= (TextView) view.findViewById(R.id.toolbar_title);
        title.setText("结果排名");
        name= (TextView) view.findViewById(R.id.resulttitle);
        sub= (Button) view.findViewById(R.id.resultfragsub);
        add= (Button) view.findViewById(R.id.resultdad);
        back= (Button) view.findViewById(R.id.toolbar_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        viewPager= (ViewPager) view.findViewById(R.id.resultvp);

        if(viewPager.getCurrentItem()==0){
            sub.setBackgroundResource(R.mipmap.leftbutton_hold);
        }

        if(viewPager.getCurrentItem()==fragments.size()-1){
            add.setBackgroundResource(R.mipmap.rightbutton_hold);
        }
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void getdatano() {
        emtview.setVisibility(View.VISIBLE);
        emtview2.setVisibility(View.GONE);
    }

    @Override
    public void getdatasuccess(ArrayList<ResultSonFragment> list) {
        this.fragments.clear();
        this.fragments.addAll(list);
        adapter.notifyDataSetChanged();
        if(viewPager.getCurrentItem()==0){
            sub.setBackgroundResource(R.mipmap.leftbutton_hold);
        }

        if(viewPager.getCurrentItem()==fragments.size()-1){
            add.setBackgroundResource(R.mipmap.rightbutton_hold);
        }
    }

    @Override
    public void gettitlesuccess(ArrayList<TaskTitlebean.DataBean.LibsystemBean> titles) {
        emtview.setVisibility(View.INVISIBLE);
        emtview2.setVisibility(View.VISIBLE);
        this.titles.clear();
        this.titles.addAll(titles);
        name.setText(titles.get(0).getTitle());
    }
}

package com.qingyii.hxtz.wmcj.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerResultActivtyComponent;
import com.qingyii.hxtz.wmcj.di.module.ResultModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskTitlebean;
import com.qingyii.hxtz.wmcj.mvp.presenter.ResultPresenter;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.Resultvpadapter;
import com.qingyii.hxtz.wmcj.mvp.ui.fragment.ResultSonFragment;
import com.zhf.Util.HintUtil;

import java.util.ArrayList;

import butterknife.BindView;

public class ResultPostionActivity extends BaseActivity<ResultPresenter> implements WMCJContract.ResultView {
    private String companyname;
    Button sub,add;
    ImageView back;
    TextView title,name;
    int industryid;

    @BindView(R.id.resultvp)
    ViewPager viewPager;

    private ArrayList<TaskTitlebean.DataBean.LibsystemBean> titles=new ArrayList<>();
    private ArrayList<ResultSonFragment> fragments =new ArrayList<>();

    Resultvpadapter adapter;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerResultActivtyComponent.builder()
                .appComponent(appComponent)
                .resultModule(new ResultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_resultpostion;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initview();
        initviewpager();
        mPresenter.getResultData(industryid);
    }

    private void initviewpager() {
        adapter=new Resultvpadapter(getSupportFragmentManager(),this, fragments);
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

    private void initview() {

        viewPager= (ViewPager) findViewById(R.id.resultvp);

        title= (TextView) findViewById(R.id.activity_tltle_name);
        title.setText("结果排名");
        name= (TextView) findViewById(R.id.resulttitle);
        sub= (Button) findViewById(R.id.resultfragsub);
        add= (Button) findViewById(R.id.resultdad);
        back= (ImageView) findViewById(R.id.toolbar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        if(viewPager.getCurrentItem()==0){
            sub.setBackgroundResource(R.mipmap.leftbutton_hold);
        }

        if(viewPager.getCurrentItem()== fragments.size()-1){
            add.setBackgroundResource(R.mipmap.rightbutton_hold);
        }
    }

    @Override
    public void getdatano() {

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
        this.titles.clear();
        this.titles.addAll(titles);
        name.setText(titles.get(0).getTitle());
    }

    @Override
    public void showLoading() {
        HintUtil.showdialog(this);
    }

    @Override
    public void hideLoading() {
     HintUtil.stopdialog();
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
}

package com.qingyii.hxtz.wmcj.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.wmcj.WMCJContract;
import com.qingyii.hxtz.wmcj.di.component.DaggerTaskComponent;
import com.qingyii.hxtz.wmcj.di.module.TaskModule;
import com.qingyii.hxtz.wmcj.mvp.model.bean.TaskTitlebean;
import com.qingyii.hxtz.wmcj.mvp.presenter.TaskPresenter;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.ReportingActivity;
import com.qingyii.hxtz.wmcj.mvp.ui.adapter.Taskvpadapter;
import com.zhy.autolayout.AutoLinearLayout;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zhf on 2018/3/21.
 */

public class TaskListFragment   extends BaseFragment<TaskPresenter> implements WMCJContract.TaskView {
    TextView title;

    TextView rightbar;

    AutoLinearLayout leftbar;

    AutoLinearLayout emtview;
    AutoLinearLayout emtview2;
    //每一页的名字
    TextView name;
    Button sub;
    Button add;

    TextView reporting;

    private ViewPager viewPager;
    // viewpager的数据源
    private ArrayList<TaskListSonFragment> fragments=new ArrayList<>();
    private Taskvpadapter adapter;
    // 每一页的名字
    private ArrayList<TaskTitlebean.DataBean.LibsystemBean> titles=new ArrayList<>();


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerTaskComponent.builder()
                .appComponent(appComponent)
                .taskModule(new TaskModule(this))
                .build()
                .inject(this);
    }



    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           return inflater.inflate(R.layout.tasklistfragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview(view);
    }

    private void initview(View view) {
        title = (TextView) view.findViewById(R.id.toolbar_title);
        leftbar= (AutoLinearLayout) view.findViewById(R.id.toolbar_back_layout);

       /* reporting= (TextView) view.findViewById(R.id.toolbar_right_tv);
     //   reporting.setVisibility(View.VISIBLE);
        // reporting.setText("已上报动态");
        reporting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), ReportingActivity.class);
                startActivity(intent);
            }
        }); */

        title.setText("任务清单");
        title.setVisibility(View.VISIBLE);
        // leftbar.setBackgroundResource(R.mipmap.back_white);
        leftbar.setVisibility(View.VISIBLE);
        leftbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(true, EventBusTags.HOME);
                getActivity().finish();
            }
        });
        emtview2= (AutoLinearLayout) view.findViewById(R.id.tasklistall);
        emtview= (AutoLinearLayout) view.findViewById(R.id.empty_view);
        viewPager= (ViewPager) view.findViewById(R.id.taskvp);
        adapter=new Taskvpadapter(getFragmentManager(),getContext(),fragments);
        viewPager.setAdapter(adapter);
        name= (TextView) view.findViewById(R.id.resulttitle);
        sub= (Button) view.findViewById(R.id.resultfragsub);
        add= (Button) view.findViewById(R.id.taskadd);
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
        if(viewPager.getCurrentItem()==0){
            sub.setBackgroundResource(R.mipmap.leftbutton_hold);
        }

        if(viewPager.getCurrentItem()==fragments.size()-1){
            add.setBackgroundResource(R.mipmap.rightbutton_hold);
        }
        //参数指数区别这里与Examine
        mPresenter.getTitle(false);


    }

    @Override
    public void initData(Bundle savedInstanceState) {


    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void setRecyclerviewAdapter(BaseRecyclerAdapter adapter) {

    }

    @Override
    public void getdatano() {
        emtview.setVisibility(View.VISIBLE);
        emtview2.setVisibility(View.GONE);
    }

    @Override
    public void getdatasuccess(ArrayList<TaskListSonFragment> list) {

        fragments.clear();
        fragments.addAll(list);
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(titles.size()-1);
        if(viewPager.getCurrentItem()==0){
            sub.setBackgroundResource(R.mipmap.leftbutton_hold);
        }

        if(viewPager.getCurrentItem()==fragments.size()-1){
            add.setBackgroundResource(R.mipmap.rightbutton_hold);
        }

    }

    @Override
    public void gettitile(ArrayList<TaskTitlebean.DataBean.LibsystemBean> list) {
        emtview.setVisibility(View.INVISIBLE);
        emtview2.setVisibility(View.VISIBLE);
        titles.clear();
        titles.addAll(list);
        name.setText(titles.get(0).getTitle());
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
    public void onDestroy() {
        super.onDestroy();
        fragments.clear();
        titles.clear();
        adapter=null;
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.WMCJ_TASK)
    public  void change(Message msg){
       getdatano();
    }
}

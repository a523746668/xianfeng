package com.zhf.zfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.zhf.Util.HintUtil;
import com.zhf.ReportingMainActivity;
import com.zhf.adapter.Taskvpadapter;
import com.zhf.bean.TaskTitlebean;
import com.zhf.present.Implpresent.Taskpresenter;
import com.zhf.present.Implview.Taskview;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/9/19.
 */
//任务清单
public class TasklistFragment extends Fragment implements Taskview {

    TextView  title;

    TextView rightbar;

    Button leftbar;

    AutoLinearLayout emtview;
    AutoLinearLayout emtview2;
    //每一页的名字
    TextView name;
    Button sub;
    Button add;

    TextView reporting;

    private Taskpresenter taskpresenter;
    private ViewPager viewPager;
    // viewpager的数据源
    private ArrayList<TasklistsonFragment> fragments=new ArrayList<>();
    private Taskvpadapter adapter;
    // 每一页的名字
    private ArrayList<TaskTitlebean.DataBean.LibsystemBean> titles=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tasklistfragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);
        //ButterKnife.bind(getActivity());
        initactionbar(view);
        initviewpager(view);
        taskpresenter=new Taskpresenter(this,getContext());
        taskpresenter.gettitles();
    }
    //viewpager相关信息初始化
    private void initviewpager(View view) {
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
    }

    //初始化标题栏
    private void initactionbar(View view) {
        title = (TextView) view.findViewById(R.id.toolbar_title);
        leftbar= (Button) view.findViewById(R.id.toolbar_back);
        reporting= (TextView) view.findViewById(R.id.toolbar_right_tv);
        reporting.setVisibility(View.VISIBLE);
        reporting.setText("已上报动态");
        reporting.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent =new Intent(getActivity(), ReportingMainActivity.class);
                 startActivity(intent);
             }
         });

        title.setText("任务清单");
        title.setVisibility(View.VISIBLE);
       // leftbar.setBackgroundResource(R.mipmap.back_white);
        leftbar.setVisibility(View.VISIBLE);
        leftbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getActivity().finish();
            }
        });
    }



    //成功拿到vp数据的回调
    @Override
    public void getdatasuccess(ArrayList<TasklistsonFragment> list) {
          fragments.clear();
        fragments.addAll(list);
        adapter.notifyDataSetChanged();
        if(viewPager.getCurrentItem()==0){
            sub.setBackgroundResource(R.mipmap.leftbutton_hold);
        }

        if(viewPager.getCurrentItem()==fragments.size()-1){
            add.setBackgroundResource(R.mipmap.rightbutton_hold);
        }
    }

    //成功拿到每一页的名字回调
    @Override
    public void gettitlessuccess(ArrayList<TaskTitlebean.DataBean.LibsystemBean > list) {
        emtview.setVisibility(View.INVISIBLE);
        emtview2.setVisibility(View.VISIBLE);
        titles.clear();
        titles.addAll(list);
        name.setText(titles.get(0).getTitle());
    }

    @Override
    public void getdatano() {

        HintUtil.stopdialog();
       emtview.setVisibility(View.VISIBLE);
        emtview2.setVisibility(View.GONE);
    }

    @Override
    public void getdataerror(Exception e) {
        //HintUtil.showtoast(getActivity(),e.getMessage().toString());
        getdatano();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        taskpresenter.unbind();
         taskpresenter=null;
        fragments.clear();
    }
}

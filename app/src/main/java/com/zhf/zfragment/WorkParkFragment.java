package com.zhf.zfragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.widget.autolayout.AutoTabLayout;
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter;
import com.mcxtzhang.commonadapter.rv.mul.BaseMulTypeAdapter;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.app.EventBusTags;
import com.qingyii.hxt.wmcj.mvp.ui.*;
import com.zhf.adapter.WorkparkvpAdapter;
import com.zhf.bean.WorkParkbean;
import com.zhf.present.Implpresent.WorkParkpresenter;
import com.zhf.present.Implview.WorkParkview;
import com.zhf.wight.CustomViewPager;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhf on 2017/9/29.
 */

public class WorkParkFragment  extends Fragment implements  WorkParkview{
    @BindView(R.id.toolbar_back)
    Button  back;

    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.toolbar_right)
    Button  zhuanti;

    @BindView(R.id.workpark_tablayout)
    AutoTabLayout tablayout;

    @BindView(R.id.workpark_viewpager)
    CustomViewPager viewpager;

    private ArrayList<WorkParkbean.DataBean.MenuItemBean> list1=new ArrayList<>();
    private ArrayList<Fragment>  list=new ArrayList<>();
    private WorkparkvpAdapter adapter;

    WorkParkpresenter presenter;

    Unbinder unbinder;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        initview();
      initdata();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view =inflater.inflate(R.layout.fragment_workpark, container, false);
        unbinder=ButterKnife.bind(this,view);
        return  view;
    }

    private void initdata()   {
        adapter=new WorkparkvpAdapter(getFragmentManager(),list,list1);
        viewpager.setAdapter(adapter);
        presenter=createPresenter();
        presenter.getdata();
    }

    private void initview() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        title.setText("工作动态");
        zhuanti.setText("专题");
        zhuanti.setVisibility(View.VISIBLE);
        zhuanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(getActivity(), WorkParkCategoryActivity.class);
                startActivity(intent);
            }
        });
    viewpager.setPagingEnabled(false
    );

    }


    protected WorkParkpresenter createPresenter() {
        return new WorkParkpresenter(getActivity(),this);
    }


    @Override
    public void showerrow(Exception e) {

    }

    @Override
    public void getdatasuccess(ArrayList<WorkParkbean> list) {

    }

    @Override
    public void getdatasuccess(WorkParkbean workParkbean) {
            list.clear();
           list1.clear();
           list1.addAll(workParkbean.getData().getMenu_item());
           for(int i=0;i<list1.size();i++){
               WorkParkItemFragment fragment= new WorkParkItemFragment();
                 fragment.setId(list1.get(i).getId());
               fragment.setSystem_id(list1.get(i).getSystem_id());
             if(i==0){
                 fragment.setFlag(true);
             }
               list.add(fragment);
           }
            for(int i=0;i<workParkbean.getData().getTag_item().size();i++){
                WorkParkItemFragment fragment= new WorkParkItemFragment();
                fragment.setId(workParkbean.getData().getTag_item().get(i).getId());
                list.add(fragment);
            }


        adapter.notifyDataSetChanged();

    }

    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.WORKPARK)
    public void getzhuanti(Message msg){
         int postion= msg.arg1;
        Log.i("tmdviewpager",postion+"");
          viewpager.setCurrentItem(postion);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.unbind();
        list.clear();
        EventBus.getDefault().unregister(this);
    }


}

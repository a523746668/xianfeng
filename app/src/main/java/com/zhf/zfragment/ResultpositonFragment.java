package com.zhf.zfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.zhf.adapter.Resultvpadapter;
import com.zhf.bean.TaskTitlebean;
import com.zhf.present.Implpresent.Resultpresenter;
import com.zhf.present.Implview.Resultview;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/9/22.
 */

//结果排名
public class ResultpositonFragment extends Fragment implements Resultview {
    Button sub,add,back;
    TextView title,name;
    ViewPager viewPager;
    Resultvpadapter adapter;


    AutoLinearLayout emtview;
    AutoLinearLayout emtview2;


    private ArrayList<TaskTitlebean.DataBean.LibsystemBean> titles=new ArrayList<>();
    private ArrayList<ResultsonFragment> fragments=new ArrayList<>();
    private Resultpresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return   inflater.inflate(R.layout.resultpostion,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview(view);
        initviewpager();
        presenter.getdata();

    }



    private void initviewpager() {
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

    private void initview(View view) {
        emtview2= (AutoLinearLayout) view.findViewById(R.id.resultall);
        emtview= (AutoLinearLayout) view.findViewById(R.id.empty_view);
        presenter=new Resultpresenter(getActivity(),this);
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
    public void onDestroy() {
        super.onDestroy();
        presenter.unbind();
         presenter=null;
        fragments.clear();
    }

    @Override
    public void getdatasuccess(ArrayList<ResultsonFragment> list) {

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

    @Override
    public void getdataerror(Exception e) {
        //HintUtil.showtoast(getActivity(),e.getMessage().toString());
          getdatano();

    }

    @Override
    public void getdatano() {

        emtview.setVisibility(View.VISIBLE);
        emtview2.setVisibility(View.GONE);
    }
}

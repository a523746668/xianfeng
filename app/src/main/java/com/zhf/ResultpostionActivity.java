package com.zhf;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.zhf.adapter.Resultvpadapter;
import com.zhf.bean.TaskTitlebean;
import com.zhf.present.Implpresent.ResultpostionPresenter;
import com.zhf.present.Implpresent.Resultpresenter;
import com.zhf.present.Implview.Resultpotionview;
import com.zhf.present.Implview.Resultview;
import com.zhf.zfragment.ResultsonFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

//结果排名
public class ResultpostionActivity extends AppCompatActivity implements Resultpotionview{
   private String companyname;
    Button sub,add;
    ImageView back;
    TextView title,name;
    int industryid;

    @BindView(R.id.resultvp)
    ViewPager viewPager;

    private ArrayList<TaskTitlebean.DataBean.LibsystemBean> titles=new ArrayList<>();
    private ArrayList<ResultsonFragment> fragments =new ArrayList<>();
    private ResultpostionPresenter presenter;
    Resultvpadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpostion);
        ButterKnife.bind(this);
        industryid=getIntent().getIntExtra("industryid",0);
        initview();
        initviewpager();


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getdata(industryid);

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
        presenter=new ResultpostionPresenter(this,this);
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
             this.titles.clear();
            this.titles.addAll(titles);
            name.setText(titles.get(0).getTitle());
    }

    @Override
    public void getdataerror(Exception e) {

    }

    @Override
    public void getdatano() {

    }
}

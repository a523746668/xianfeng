package com.qingyii.hxt;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.fragment.PersonalFragment;
import com.qingyii.hxt.fragment.UnitFragment;
import com.qingyii.hxt.pojo.ExamList;

import java.util.ArrayList;
import java.util.List;


//成绩排行
public class ScoreRanking extends AbBaseActivity {
    /**
     * AbSlidingTabView 失效 注销相关
     */
//	private AbSlidingTabView mAbSlidingTabView;
    private ImageView scoreranking_goback_iv;
    private ExamList.DataBean examination;
    private TextView scoreranking_kaoshiming;
    private FragmentManager fm;
    private  List<Fragment> mFragment;
    public static final String RANKING_PERSONAL_FRAGMENT_TAG = "ranking_personal";
    public static final String RANKING_DEPARTMENT_FRAGMENT_TAG = "ranking_department";


    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public String getExamId(){
        return  String.valueOf(examination.getId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreranking);
        examination =  getIntent().getParcelableExtra("examination"); //(ExamList.DataBean)

//		mAbSlidingTabView=(AbSlidingTabView) findViewById(R.id.scorerankingm_AbSlidingTabView);
        scoreranking_goback_iv = (ImageView) findViewById(R.id.scoreranking_goback_iv);
        scoreranking_kaoshiming = (TextView) findViewById(R.id.scoreranking_kaoshiming);

        //指定加载页数
//		mAbSlidingTabView.getViewPager().setOffscreenPageLimit(2);

        PersonalFragment page1 = new PersonalFragment();

        page1.typess = examination.getExamtype();
        page1.examinationname = examination.getExamname();
        page1.examinationid = String.valueOf(examination.getId()) ;
        page1.groupid = examination.getCompany().getId();  //getGroupId 之前app用的这个参数

        if (examination.getExamname() != null)
            scoreranking_kaoshiming.setText(examination.getExamname());

        UnitFragment page2 = new UnitFragment();
        page2.typess = examination.getExamtype();
        page2.examinationname = examination.getExamname();
        page2.examinationid = String.valueOf(examination.getId());
        page2.groupid = examination.getCompany().getId();  //getGroupId() 之前app用的这个参数


        mFragment = new ArrayList<Fragment>();
        mFragment.add(page1);
        mFragment.add(page2);


        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.ranking_viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
       // tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

       /* TabLayout tblayout =(TabLayout) findViewById(R.id.sliding_tabs);
        tblayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (viewPager!=null)
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

        scoreranking_goback_iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
    }

    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[]{"个人成绩排名","单位成绩排名"};
        private Context context;

        public SimpleFragmentPagerAdapter(FragmentManager fm,Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return PersonalFragment.newInstance(position);
                case 1:
                    return UnitFragment.newInstance(position);
                default:
                    return PersonalFragment.newInstance(position);
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}

package com.qingyii.hxtz;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qingyii.hxtz.adapter.TabLayoutPagerAdapter;
import com.qingyii.hxtz.fragment.TuijianFragment;
import com.qingyii.hxtz.fragment.fenleiFragment;
import com.qingyii.hxtz.fragment.paihangFragment;
import com.qingyii.hxtz.fragment.zuantifragment;
import com.qingyii.hxtz.fragment.zuixinFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 书屋界面
 *
 * @author Administrator
 */
public class shuWuActivity extends AppCompatActivity {
    /**
     * AbSlidingTabView 失效，注释相关
     */
//    private AbSlidingTabView mAbSlidingTabView;
    private View returns_arrow;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayoutPagerAdapter tabLayoutPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_top);

        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("书城");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tablayout_menu);
        viewPager = (ViewPager) findViewById(R.id.tablayout_viewpagaer);

        TuijianFragment page1 = new TuijianFragment();
        zuixinFragment page2 = new zuixinFragment();
        paihangFragment page3 = new paihangFragment();
        fenleiFragment page4 = new fenleiFragment();
        zuantifragment page5 = new zuantifragment();

        List<Fragment> mFragments = new ArrayList<Fragment>();
        mFragments.add(page1);
        mFragments.add(page2);
        mFragments.add(page3);
        mFragments.add(page4);
        mFragments.add(page5);

        List<String> tabTexts = new ArrayList<String>();
        tabTexts.add("推荐");
        tabTexts.add("最新");
        tabTexts.add("排行");
        tabTexts.add("分类");
        tabTexts.add("专题");

        tabLayoutPagerAdapter = new TabLayoutPagerAdapter(getSupportFragmentManager(), mFragments, tabTexts);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        viewPager.setAdapter(tabLayoutPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
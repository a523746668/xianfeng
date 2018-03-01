package com.qingyii.hxt;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.qingyii.hxt.fragment.TXLAttentionFragment;
import com.qingyii.hxt.fragment.TXLCompanyFragment;
import com.qingyii.hxt.fragment.TXLDepartmentFragment;

public class MyTongXunLuActivity extends AppCompatActivity {

    private FragmentTabHost fragmentTabHost;
    private LayoutInflater layoutInflater;

    private Class[] fragmentArray = {TXLCompanyFragment.class, TXLDepartmentFragment.class, TXLAttentionFragment.class};

    private int[] imgs = {R.drawable.circle_unit_selector_style,
            R.drawable.circle_department_selector_style,
            R.drawable.circle_attention_selector_style};

    private String[] title = {"单位", "部门", "关注"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tong_xun_lu);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("通讯录");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        layoutInflater = LayoutInflater.from(this);

        // 找到TabHost
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        //去掉分割线
//        fragmentTabHost.getTabWidget().setDividerDrawable(null);
        // 得到fragment的个数
        for (int i = 0; i < fragmentArray.length; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(title[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            fragmentTabHost.addTab(tabSpec, fragmentArray[i], null);
            // 设置Tab按钮的背景
//            fragmentTabHost.getTabWidget().getChildAt(i)
//                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tabwidget_item_layout, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.tab_item_layout_img);
        imageView.setImageResource(imgs[index]);

        return view;
    }
}

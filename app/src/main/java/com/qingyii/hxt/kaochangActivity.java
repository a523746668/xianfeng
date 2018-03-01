package com.qingyii.hxt;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.kang.zbar.CameraTestActivity;
import com.qingyii.hxt.fragment.endedExamFragment;
import com.qingyii.hxt.fragment.joiningExamFragment;
import com.qingyii.hxt.fragment.successExamFragment;

import java.util.ArrayList;
import java.util.List;

//import com.ab.view.sliding.AbSlidingTabView;

public class kaochangActivity extends AbBaseActivity {
//	private AbSlidingTabView mAbSlidingTabView;
    private ImageView iv_back;
    private ImageView zbar;
    private final static int SCANNIN_GREQUEST_CODE = 10;
    /**
     * 考试类型：1-题库抽选，2-单次命题，3-答题闯关1,4-答题闯关2）
     */
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaochang);
        type = getIntent().getIntExtra("type", 0);
        initUI();
    }

    private void initUI() {

        zbar = (ImageView) findViewById(R.id.zbar);
        zbar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(kaochangActivity.this,
                        CameraTestActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        /**
         * 获得 AbSlidingTabView
         */
//		mAbSlidingTabView = (AbSlidingTabView) findViewById(mAbSlidingTabView);
        joiningExamFragment page1 = new joiningExamFragment();
        page1.kaoshiType = type;
        endedExamFragment page2 = new endedExamFragment();
        page2.kaoshiType = type;
        successExamFragment page3 = new successExamFragment();
        page3.kaoshiType = type;
        List<Fragment> mFragments = new ArrayList<Fragment>();
        mFragments.add(page1);
        mFragments.add(page2);
        mFragments.add(page3);

        List<String> tabTexts = new ArrayList<String>();
        tabTexts.add("进行中");
        tabTexts.add("已结束");
        tabTexts.add("参与的");
        // 设置样式
        /**
         * 设置 AbSlidingTabView
         */
//		mAbSlidingTabView.setTabTextColor(Color.BLACK);
//		mAbSlidingTabView.setTabSelectColor(Color.rgb(30, 168, 131));
//		mAbSlidingTabView.setTabBackgroundResource(R.drawable.tab_bg);
//		mAbSlidingTabView.setTabLayoutBackgroundResource(R.drawable.slide_top);
//		mAbSlidingTabView.addItemViews(tabTexts, mFragments);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == 10) {
                    Bundle bundle = data.getExtras();
                    // 显示扫描到的内容
                    String pwd = bundle.getString("result");
                    Toast.makeText(kaochangActivity.this, pwd, Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

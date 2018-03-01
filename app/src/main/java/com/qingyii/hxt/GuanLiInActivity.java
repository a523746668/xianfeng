package com.qingyii.hxt;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mingle.widget.ShapeLoadingDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.adapter.TabLayoutPagerAdapter;
import com.qingyii.hxt.fragment.BranchRankFragment;
import com.qingyii.hxt.fragment.SectionLevelFragment;
import com.qingyii.hxt.fragment.SectionTotalFragment;
import com.qingyii.hxt.fragment.UnitLevelFragment;
import com.qingyii.hxt.fragment.UnitTotalFragment;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.DocumentaryMy;
import com.qingyii.hxt.pojo.DocumentaryStatus;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;
import com.qingyii.hxt.view.RoundedImageView;
import com.qingyii.hxt.wheelpicker.picker.DatePicker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class GuanLiInActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private String[] mTitles = new String[]{"单位星级榜", "部门星级榜", "单位积分榜", "部门积分榜"};
    private String[] mTitlesAdvanced = new String[]{"星级排行榜", "总积分排行榜", "支部排行榜"};
    private ViewPager mViewPager;
    private TabLayoutPagerAdapter tabLayoutPagerAdapter;
    private TabLayout tabLayout;
    private List<Fragment> mFragments;
    private List<String> tabTexts;
//    private UnitLevelFragment unitLevelFragment;
//    private SectionLevelFragment sectionLevelFragment;
//    private UnitTotalFragment unitTotalFragment;
//    private SectionTotalFragment sectionTotalFragment;
//    private BranchRankFragment branchRankFragment;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    //年月日选择器
    private Calendar calendar = Calendar.getInstance();
    //时间
    final int MONTH = calendar.get(Calendar.MONTH) <= 0 ? 12 : calendar.get(Calendar.MONTH),
            YEAR = calendar.get(Calendar.MONTH) <= 0 ? calendar.get(Calendar.YEAR) - 1 : calendar.get(Calendar.YEAR);

    //右上角弹窗
//    private ListView check_month_list;
//    private boolean is_check_month = false;
//    ArrayAdapter<String> arrayAdapter;

    private RelativeLayout activity_tltle_operation;
    private TextView guan_li_confirmed_in;
    private TextView guan_li_advanced_in;
    private TextView guan_li_audit_in;

    private TextView guan_li_in_head_advanced_message;

    private RoundedImageView guan_li_in_head_photo;

    private LinearLayout guan_li_in_head_star_ll;
    //星级
    private ImageView guan_li_in_head_star[] = new ImageView[5];
    //无星表示
    private TextView guan_li_in_head_star1_no;

    private TextView guan_li_in_head_name;
    private LinearLayout guan_li_in_head_rank_ll;
    private TextView guan_li_in_head_unit;
    private TextView guan_li_in_head_section;
    private LinearLayout guan_li_in_head_num_ll;
    private TextView guan_li_in_head_num1;
    private TextView guan_li_in_head_num2;
    private TextView guan_li_in_head_num3;
    private TextView guan_li_in_head_num4;
    private TextView guan_li_in_head_num5;
    private LinearLayout guan_li_in_head_advanced_ll;
    private TextView guan_li_in_head_advanced1;
    private TextView guan_li_in_head_advanced2;
    private TextView guan_li_in_head_advanced3;
    private TextView guan_li_in_head_advanced4;
    private TextView guan_li_in_head_advanced5;
    private LinearLayout guan_li_in_head_advanced_ll2;
    private TextView guan_li_in_head_advanced6;
    private TextView guan_li_in_head_advanced7;
    private TextView guan_li_in_head_advanced8;
    private TextView guan_li_in_head_advanced9;
    private TextView guan_li_in_head_advanced10;

    private DocumentaryMy.DataBean dDataBean;
    private DocumentaryStatus.DataBean dsDataBean;

    private ShapeLoadingDialog shapeLoadingDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_li_in);
        Log.e("onCreate()", "被执行");
        //tltle设置
        TextView activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
        activity_tltle_name.setText("我的纪实");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        activity_tltle_operation = (RelativeLayout) findViewById(R.id.activity_tltle_operation);
        activity_tltle_operation.setVisibility(View.VISIBLE);
        activity_tltle_operation.setOnClickListener(this);
        initViews();

//        Log.e("账户等级", MyApplication.userUtil.getCheck_level() + "");
//        switch (MyApplication.userUtil.getCheck_level()) {
//            case 0:
//                initDatas();
//                getDateList("");
//                break;
//            case 1:
//                getDateList("");
//                break;
//            case 2:
//                getDateList("");
//                break;
//            case 3:
//                getDateList("");
//                break;
//            case 4:
//                getDateList("");
//                break;
//            case 5:
//                getDateList("");
//                break;
//            default:
//                break;
//        }

        if (MyApplication.userUtil != null)
            switch (MyApplication.userUtil.getCheck_level()) {
                case 0:
                    F5UI();
                    break;
                case 1:
                    F5UI();
                    break;
                case 2:
                    F5UI();
                    break;
                case 3:
                    F5UI();
                    break;
                case 4:
                    F5AdvancedUI();
                    break;
                case 5:
                    F5AdvancedUI();
                    break;
                default:
                    break;
            }
        if (MONTH > 9) {
            getDateMy(YEAR + "-" + MONTH);
            getDateStatus(YEAR + "-" + MONTH);
        } else {
            getDateMy(YEAR + "-0" + MONTH);
            getDateStatus(YEAR + "-0" + MONTH);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume()", "被执行");

    }

    private void initViews() {
        shapeLoadingDialog = new ShapeLoadingDialog(GuanLiInActivity.this);
        shapeLoadingDialog.setLoadingText("查询中,请等一小会");

        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_menu);
        guan_li_confirmed_in = (TextView) findViewById(R.id.guan_li_confirmed_in);
        guan_li_advanced_in = (TextView) findViewById(R.id.guan_li_advanced_in);
        guan_li_audit_in = (TextView) findViewById(R.id.guan_li_audit_in);
        guan_li_in_head_photo = (RoundedImageView) findViewById(R.id.guan_li_in_head_photo);

        //可选择月份列表
//        check_month_list = (ListView) findViewById(R.id.check_month_list);
        //星级
        guan_li_in_head_star_ll = (LinearLayout) findViewById(R.id.guan_li_in_head_star_ll);
        guan_li_in_head_star[0] = (ImageView) findViewById(R.id.guan_li_in_head_star1);
        guan_li_in_head_star[1] = (ImageView) findViewById(R.id.guan_li_in_head_star2);
        guan_li_in_head_star[2] = (ImageView) findViewById(R.id.guan_li_in_head_star3);
        guan_li_in_head_star[3] = (ImageView) findViewById(R.id.guan_li_in_head_star4);
        guan_li_in_head_star[4] = (ImageView) findViewById(R.id.guan_li_in_head_star5);
        guan_li_in_head_star1_no = (TextView) findViewById(R.id.guan_li_in_head_star1_no);
        //用户名
        guan_li_in_head_name = (TextView) findViewById(R.id.guan_li_in_head_name);
        //用信息
        guan_li_in_head_rank_ll = (LinearLayout) findViewById(R.id.guan_li_in_head_rank_ll);
        guan_li_in_head_unit = (TextView) findViewById(R.id.guan_li_in_head_unit);
        guan_li_in_head_section = (TextView) findViewById(R.id.guan_li_in_head_section);
        //高级用户信息
        guan_li_in_head_advanced_message = (TextView) findViewById(R.id.guan_li_in_head_advanced_message);

        guan_li_in_head_num_ll = (LinearLayout) findViewById(R.id.guan_li_in_head_num_ll);
        guan_li_in_head_num1 = (TextView) findViewById(R.id.guan_li_in_head_num1);
        guan_li_in_head_num2 = (TextView) findViewById(R.id.guan_li_in_head_num2);
        guan_li_in_head_num3 = (TextView) findViewById(R.id.guan_li_in_head_num3);
        guan_li_in_head_num4 = (TextView) findViewById(R.id.guan_li_in_head_num4);
        guan_li_in_head_num5 = (TextView) findViewById(R.id.guan_li_in_head_num5);

        guan_li_in_head_advanced_ll = (LinearLayout) findViewById(R.id.guan_li_in_head_advanced_ll);
        guan_li_in_head_advanced1 = (TextView) findViewById(R.id.guan_li_in_head_advanced1);
        guan_li_in_head_advanced2 = (TextView) findViewById(R.id.guan_li_in_head_advanced2);
        guan_li_in_head_advanced3 = (TextView) findViewById(R.id.guan_li_in_head_advanced3);
        guan_li_in_head_advanced4 = (TextView) findViewById(R.id.guan_li_in_head_advanced4);
        guan_li_in_head_advanced5 = (TextView) findViewById(R.id.guan_li_in_head_advanced5);

        guan_li_in_head_advanced_ll2 = (LinearLayout) findViewById(R.id.guan_li_in_head_advanced_ll2);
        guan_li_in_head_advanced6 = (TextView) findViewById(R.id.guan_li_in_head_advanced6);
        guan_li_in_head_advanced7 = (TextView) findViewById(R.id.guan_li_in_head_advanced7);
        guan_li_in_head_advanced8 = (TextView) findViewById(R.id.guan_li_in_head_advanced8);
        guan_li_in_head_advanced9 = (TextView) findViewById(R.id.guan_li_in_head_advanced9);
        guan_li_in_head_advanced10 = (TextView) findViewById(R.id.guan_li_in_head_advanced10);

        guan_li_audit_in.setOnClickListener(this);

        //Fragment载入
        mFragments = new ArrayList<Fragment>();
        tabTexts = new ArrayList<String>();

        tabLayoutPagerAdapter = new TabLayoutPagerAdapter(getSupportFragmentManager(), mFragments, tabTexts);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewPager.setAdapter(tabLayoutPagerAdapter);
        mViewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void F5UI() {
        try {
            guan_li_in_head_rank_ll.setVisibility(View.VISIBLE);
            guan_li_in_head_num_ll.setVisibility(View.VISIBLE);
            //隐藏高级人员显示
            guan_li_in_head_advanced_ll.setVisibility(View.GONE);
            guan_li_in_head_advanced_ll2.setVisibility(View.GONE);

//        if ()
            switch (dDataBean.getPunishtype()) {
                case "talk":
                    guan_li_in_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_talk);
                    guan_li_in_head_photo.setOnClickListener(this);
                    break;
                case "orange":
                    guan_li_in_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_orange);
                    guan_li_in_head_photo.setOnClickListener(this);
                    break;
                case "yellow":
                    guan_li_in_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_yellow);
                    guan_li_in_head_photo.setOnClickListener(this);
                    break;
                case "red":
                    guan_li_in_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_red);
                    guan_li_in_head_photo.setOnClickListener(this);
                    break;
                default:
                    ImageLoader.getInstance().displayImage(dDataBean.getAvatar(), guan_li_in_head_photo, MyApplication.options, animateFirstListener);
                    break;
            }

//        guan_li_in_head_photo.
//        arrayAdapter = new ArrayAdapter<String>(this, R.layout.text_list_item, dDataBean.getCheck_month());
//        check_month_list.setAdapter(arrayAdapter);
//        check_month_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, NotifyListView view, int i, long l) {
//                getDateList(dDataBean.getCheck_month().get(i));
//
//                check_month_list.setVisibility(NotifyListView.GONE);
//                is_check_month = false;
//            }
//        });

            guan_li_in_head_star_ll.setVisibility(View.VISIBLE);
            judgeStars();

            guan_li_in_head_name.setText(dDataBean.getTruename() + "   " + dDataBean.getDepartment() + "  " + dDataBean.getJobname());
            guan_li_in_head_unit.setText(dDataBean.getCompany_rank() + "");
            guan_li_in_head_section.setText(dDataBean.getDepartment_rank() + "");

            DocumentaryMy.DataBean.ScoreBean dDataBeanScore = dDataBean.getScore();
            if (dDataBeanScore.get累计积分() != null)
                guan_li_in_head_num1.setText(dDataBeanScore.get累计积分() + "");
            if (dDataBeanScore.get作用积分() != null)
                guan_li_in_head_num2.setText(dDataBeanScore.get作用积分() + "");
            if (dDataBeanScore.get政治积分() != null)
                guan_li_in_head_num3.setText(dDataBeanScore.get政治积分() + "");
            if (dDataBeanScore.get纪律积分() != null)
                guan_li_in_head_num4.setText(dDataBeanScore.get纪律积分() + "");
            if (dDataBeanScore.get品德积分() != null)
                guan_li_in_head_num5.setText(dDataBeanScore.get品德积分() + "");
        } catch (Exception e) {
            Log.e("F5UI", e.toString());
        }
    }

    private void F5Fragmrnt() {
        try {
//        unitLevelFragment.getDataBean(dDataBean);
//        sectionLevelFragment.getDataBean(dDataBean);
//        unitTotalFragment.getDataBean(dDataBean);
//        sectionTotalFragment.getDataBean(dDataBean);
            Bundle bundle = new Bundle();
            bundle.putParcelable("dDataBean", dDataBean);
            UnitLevelFragment unitLevelFragment;
            SectionLevelFragment sectionLevelFragment;
            UnitTotalFragment unitTotalFragment;
            SectionTotalFragment sectionTotalFragment;
            unitLevelFragment = new UnitLevelFragment();
            sectionLevelFragment = new SectionLevelFragment();
            unitTotalFragment = new UnitTotalFragment();
            sectionTotalFragment = new SectionTotalFragment();

            tabLayoutPagerAdapter.setFragments();

            mFragments.clear();
            unitLevelFragment.setArguments(bundle);
            mFragments.add(unitLevelFragment);
            sectionLevelFragment.setArguments(bundle);
            mFragments.add(sectionLevelFragment);
            unitTotalFragment.setArguments(bundle);
            mFragments.add(unitTotalFragment);
            sectionTotalFragment.setArguments(bundle);
            mFragments.add(sectionTotalFragment);

            tabTexts.clear();
            for (int i = 0; i < mTitles.length; i++) {
                tabTexts.add(mTitles[i]);
            }

//        Message msg =new Message();
//        msg.what = -1;
//        msg.obj = dDataBean.getCurr_month();
//        unitLevelFragment.getHandler().sendMessage(msg);
            tabLayoutPagerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("F5Fragmrnt", e.toString());
        }
    }

    private void F5AdvancedUI() {
        try {
            guan_li_in_head_advanced_ll.setVisibility(View.VISIBLE);
            guan_li_in_head_advanced_ll2.setVisibility(View.VISIBLE);
            guan_li_in_head_advanced_message.setVisibility(View.VISIBLE);
            //隐藏低级人员显示
            guan_li_in_head_rank_ll.setVisibility(View.GONE);
            guan_li_in_head_num_ll.setVisibility(View.GONE);

            ImageLoader.getInstance().displayImage(dDataBean.getAvatar(), guan_li_in_head_photo, MyApplication.options, animateFirstListener);

//        arrayAdapter = new ArrayAdapter<String>(this, R.layout.text_list_item, dDataBean.getCheck_month());
//        check_month_list.setAdapter(arrayAdapter);
//        check_month_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, NotifyListView view, int i, long l) {
//                getDateList(dDataBean.getCheck_month().get(i));
//                check_month_list.setVisibility(NotifyListView.GONE);
//                is_check_month = false;
//            }
//        });

            guan_li_in_head_star_ll.setVisibility(View.GONE);
//        judgeStars();

            guan_li_in_head_name.setText(dDataBean.getTruename() + "   " + dDataBean.getDepartment() + "  " + dDataBean.getJobname());
            //截取尾部 转化为int
            String[] month = dDataBean.getCurr_month().split("-");
            guan_li_in_head_advanced_message.setText("截至 " + Integer.parseInt(month[month.length - 1]) + "月份纪实情况");

            DocumentaryMy.DataBean.StarsNumBean dStarsNumBean = dDataBean.getStars_num();
            guan_li_in_head_advanced1.setText(dStarsNumBean.get纪实总人数() + "");
            guan_li_in_head_advanced2.setText(dStarsNumBean.get四星党员() + "");
            guan_li_in_head_advanced3.setText(dStarsNumBean.get三星党员() + "");
            guan_li_in_head_advanced4.setText(dStarsNumBean.get二星党员() + "");
            guan_li_in_head_advanced5.setText(dStarsNumBean.get一星党员() + "");
            guan_li_in_head_advanced6.setText(dStarsNumBean.get无星党员() + "");
            guan_li_in_head_advanced7.setText(dStarsNumBean.get橙色警示() + "");
            guan_li_in_head_advanced8.setText(dStarsNumBean.get黄色警示() + "");
            guan_li_in_head_advanced9.setText(dStarsNumBean.get不合格() + "");
            guan_li_in_head_advanced10.setText(dStarsNumBean.get四星比例() + "");
        } catch (Exception e) {
            Log.e("F5AdvancedUI", e.toString());
        }
    }

    private void F5AdvancedFragmrnt() {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("dDataBean", dDataBean);
            UnitLevelFragment unitLevelFragment;
            UnitTotalFragment unitTotalFragment;
            BranchRankFragment branchRankFragment;
            unitLevelFragment = new UnitLevelFragment();
            unitTotalFragment = new UnitTotalFragment();
            branchRankFragment = new BranchRankFragment();

            tabLayoutPagerAdapter.setFragments();

            mFragments.clear();
            unitLevelFragment.setArguments(bundle);
            mFragments.add(unitLevelFragment);
            unitTotalFragment.setArguments(bundle);
            mFragments.add(unitTotalFragment);
            branchRankFragment.setArguments(bundle);
            mFragments.add(branchRankFragment);

            tabTexts.clear();
            for (int i = 0; i < mTitlesAdvanced.length; i++) {
                tabTexts.add(mTitlesAdvanced[i]);
            }

//        Message msg =new Message();
//        msg.what = -1;
//        msg.obj = dDataBean.getCurr_month();
//        unitLevelFragment.getHandler().sendMessage(msg);
            tabLayoutPagerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("F5AdvancedFragmrnt", e.toString());
        }

    }

    private void judgeStars() {
        int i = 0;
        Log.e("我的星级：", dDataBean.getLast_month_stars() + "");
        for (; i < dDataBean.getLast_month_stars(); i++)
            guan_li_in_head_star[i].setVisibility(View.VISIBLE);

        for (; i < guan_li_in_head_star.length; i++)
            guan_li_in_head_star[i].setVisibility(View.GONE);

        //无星标识显示
        if (dDataBean.getLast_month_stars() > 0)
            guan_li_in_head_star1_no.setVisibility(View.GONE);
        else
            guan_li_in_head_star1_no.setVisibility(View.VISIBLE);
    }

    private void F5BottomUI() {
        try {
            //最高两级别
            if (MyApplication.userUtil.getCheck_level() > 3) {
                guan_li_advanced_in.setVisibility(View.VISIBLE);
                guan_li_confirmed_in.setVisibility(View.GONE);
                guan_li_audit_in.setVisibility(View.GONE);
//            if (dsDataBean.getWait_check().equals("0"))
//                guan_li_advanced_in.setText(dsDataBean.getCurr_month() + "月纪实积分确认");
//            else {
                guan_li_advanced_in.setOnClickListener(this);
                String[] month = dsDataBean.getCurr_month().split("-");
                if (dsDataBean.getCheck_cnt() > 0) {
                    if (MyApplication.userUtil.getCheck_level() == 4) {
                        guan_li_advanced_in.setText(Integer.parseInt(month[month.length - 1]) + " 月份纪实事项请您审阅");
                    } else if (MyApplication.userUtil.getCheck_level() == 5) {
                        guan_li_advanced_in.setText(Integer.parseInt(month[month.length - 1]) + " 月份纪实事项请您审定");
                    }
                } else {
                    if (MyApplication.userUtil.getCheck_level() == 4) {
                        guan_li_advanced_in.setText("当前没有纪实事项需要审阅");
                    } else if (MyApplication.userUtil.getCheck_level() == 5) {
                        guan_li_advanced_in.setText("当前没有纪实事项需要审定");
                    }
                    guan_li_advanced_in.setBackgroundColor(Color.parseColor("#727272"));
                }
//            }

                //一级审核
            } else {
                guan_li_confirmed_in.setVisibility(View.VISIBLE);
                guan_li_audit_in.setVisibility(View.VISIBLE);
                guan_li_advanced_in.setVisibility(View.GONE);

                String YearMonth;
                if (MONTH > 9)
                    YearMonth = YEAR + "-" + MONTH;
                else
                    YearMonth = YEAR + "-0" + MONTH;

                String[] month = dsDataBean.getCurr_month().split("-");
                if (dsDataBean.getDocs_cnt() == 0 && dsDataBean.getCurr_month().equals(YearMonth)) {
                    guan_li_confirmed_in.setText("您有" + dsDataBean.getDocs_cnt() + "条记录需要确认");
                    guan_li_confirmed_in.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    guan_li_confirmed_in.setTextColor(Color.parseColor("#AAAAAA"));
                    guan_li_confirmed_in.setOnClickListener(this);
                } else if (dsDataBean.getDocs_cnt() > 0 && dsDataBean.getCurr_month().equals(YearMonth)) {
                    guan_li_confirmed_in.setText("您有" + dsDataBean.getDocs_cnt() + "条记录需要确认");
                    guan_li_confirmed_in.setBackgroundColor(Color.parseColor("#F34235"));
                    guan_li_confirmed_in.setTextColor(Color.parseColor("#FFFFFF"));
                    guan_li_confirmed_in.setOnClickListener(this);
                } else if (dsDataBean.getDocs_cnt() == 0) {
                    guan_li_confirmed_in.setText("查看" + Integer.parseInt(month[month.length - 1]) + "月记录");
                    guan_li_confirmed_in.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    guan_li_confirmed_in.setTextColor(Color.parseColor("#AAAAAA"));
                    guan_li_confirmed_in.setOnClickListener(this);
                } else {
                    guan_li_confirmed_in.setText("您本月没有需要审核的记录");
                    guan_li_confirmed_in.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    guan_li_confirmed_in.setTextColor(Color.parseColor("#AAAAAA"));
                }

                if (MyApplication.userUtil.getCheck_level() == 1) {
                    if (dsDataBean.getCheck_cnt() > 0) {
                        guan_li_audit_in.setText("您有" + dsDataBean.getCheck_cnt() + "条纪实项目需要审核");
                        guan_li_audit_in.setBackgroundColor(Color.parseColor("#F34235"));
                    } else {
                        guan_li_audit_in.setText("您有" + dsDataBean.getCheck_cnt() + "条纪实项目需要审核");
                        guan_li_audit_in.setBackgroundColor(Color.parseColor("#88838B8B"));
                    }
                } else if (MyApplication.userUtil.getCheck_level() == 2) {
                    if (dsDataBean.getCheck_cnt() == 0) {
                        guan_li_audit_in.setBackgroundColor(Color.parseColor("#88838B8B"));
                        guan_li_audit_in.setText("当前没有纪实事项需要审核");
                    } else {
                        guan_li_audit_in.setBackgroundColor(Color.parseColor("#F34235"));
                        guan_li_audit_in.setText(Integer.parseInt(month[month.length - 1]) + "月份纪实事项请您审核");
                    }
                }

                //为普通用户时隐藏
                if (MyApplication.userUtil.getCheck_level() == 0 || MyApplication.userUtil.getCheck_level() == 3)
                    guan_li_audit_in.setVisibility(View.GONE);
                else
                    guan_li_audit_in.setVisibility(View.VISIBLE);

//        if (!(MyApplication.userUtil.getCheck_level() == 0 || MyApplication.userUtil.getCheck_level() == 3))
//            guan_li_confirmed_in.setVisibility(NotifyListView.GONE);
//        else
//            guan_li_confirmed_in.setVisibility(NotifyListView.VISIBLE);
            }
        } catch (Exception e) {
            Log.e("F5BottomUI", e.toString());
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_tltle_operation:
                Log.e("YEAR_MONTH", "YEAR" + YEAR + "MONTH" + MONTH);
                DatePicker picker = new DatePicker(GuanLiInActivity.this, DatePicker.YEAR_MONTH);
                picker.setRangeStart(2016, 1);
                picker.setRangeEnd(YEAR, MONTH);
                picker.setSelectedItem(YEAR, MONTH);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
                    @Override
                    public void onDatePicked(String year, String month) {

                        shapeLoadingDialog.show();
                        if (MONTH > 9) {
                            getDateMy(year + "-" + month);
                            getDateStatus(year + "-" + month);
                        } else {
                            getDateMy(year + "-" + month);
                            getDateStatus(year + "-" + month);
                        }
                    }
                });
                picker.show();
//                fxPopWindow.showPopupWindow(activity_tltle_operation);
//                if (is_check_month) {
//                    check_month_list.setVisibility(NotifyListView.GONE);
//                    is_check_month = false;
//                } else {
//                    check_month_list.setVisibility(NotifyListView.VISIBLE);
//                    is_check_month = true;
//                }
                break;
            case R.id.guan_li_confirmed_in:
//                dsDataBean.setCurr_month("2016-10");
                intent = new Intent(GuanLiInActivity.this, ReportListActivity.class);
                intent.putExtra("dsDataBean", dsDataBean);
                startActivity(intent);
                break;
            case R.id.guan_li_audit_in:
                Log.e("账户等级", MyApplication.userUtil.getCheck_level() + "");
//                dsDataBean.setCurr_month("2016-10");
                switch (MyApplication.userUtil.getCheck_level()) {
                    case 1:
                        intent = new Intent(GuanLiInActivity.this, AuditCrewListActivity.class);
                        intent.putExtra("dsDataBean", dsDataBean);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(GuanLiInActivity.this, AuditGroupListActivity.class);
                        intent.putExtra("dsDataBean", dsDataBean);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.guan_li_advanced_in:
//                dsDataBean.setCurr_month("2016-10");
                intent = new Intent(GuanLiInActivity.this, AuditGroupListActivity.class);
                intent.putExtra("dsDataBean", dsDataBean);
                startActivity(intent);
                break;
            case R.id.guan_li_in_head_photo:
                intent = new Intent(GuanLiInActivity.this, GuanLiWarnActivity.class);
                intent.putExtra("dDataBean", dDataBean);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 我的纪实数据
     */
    public void getDateMy(final String month) {

        Log.e("DocumentaryMy_month", month);
        OkHttpUtils
                .get()
                .url(XrjHttpClient.getDocumentaryMyUrl() + month)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new DocumentaryMyCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("DocumentaryMy_onError", e.toString());
                                 Toast.makeText(GuanLiInActivity.this, "网络已断开，请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(DocumentaryMy response, int id) {
                                 Log.e("DocumentaryMyCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         dDataBean = response.getData();
                                         if (dDataBean.getCurr_month().equals(""))
                                             dDataBean.setCurr_month(month);
                                         Log.e("月份  ", dDataBean.getCurr_month() + "");
                                         Log.e("账户等级", MyApplication.userUtil.getCheck_level() + "");
                                         switch (MyApplication.userUtil.getCheck_level()) {
                                             case 0:
                                                 F5UI();
                                                 F5Fragmrnt();
                                                 break;
                                             case 1:
                                                 F5UI();
                                                 F5Fragmrnt();
                                                 break;
                                             case 2:
                                                 F5UI();
                                                 F5Fragmrnt();
                                                 break;
                                             case 3:
                                                 F5UI();
                                                 F5Fragmrnt();
                                                 break;
                                             case 4:
                                                 F5AdvancedUI();
                                                 F5AdvancedFragmrnt();
                                                 break;
                                             case 5:
                                                 F5AdvancedUI();
                                                 F5AdvancedFragmrnt();
                                                 break;
                                             default:
                                                 break;
                                         }

                                         break;
                                     default:
                                         Toast.makeText(GuanLiInActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                                 if (shapeLoadingDialog != null) {
                                     shapeLoadingDialog.dismiss();
                                 }
                             }
                         }
                );
    }

    private abstract class DocumentaryMyCallback extends Callback<DocumentaryMy> {

        @Override
        public DocumentaryMy parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("我的纪实数据 JSON", result);
            DocumentaryMy documentaryMy = new Gson().fromJson(result, DocumentaryMy.class);
            return documentaryMy;
        }
    }

    /**
     * 我的纪实数据
     */
    public void getDateStatus(String month) {

        Log.e("Status_month", month);
        OkHttpUtils
                .get()
                .url(XrjHttpClient.getDocumentaryStatusUrl() + month)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new DocumentaryStatusCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Status_onError", e.toString());
//                                 Toast.makeText(GuanLiInActivity.this, "网络异常，请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(DocumentaryStatus response, int id) {
                                 Log.e("StatusCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         dsDataBean = response.getData();
                                         F5BottomUI();
                                         break;
                                     default:
                                         Toast.makeText(GuanLiInActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class DocumentaryStatusCallback extends Callback<DocumentaryStatus> {

        @Override
        public DocumentaryStatus parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("我的待定数据 JSON", result);
            DocumentaryStatus documentaryStatus = new Gson().fromJson(result, DocumentaryStatus.class);
            return documentaryStatus;
        }
    }
}

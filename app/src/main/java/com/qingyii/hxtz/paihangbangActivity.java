package com.qingyii.hxtz;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.http.listener.AbHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.util.AbToastUtil;
import com.qingyii.hxtz.bean.ExamRankInfo;
import com.qingyii.hxtz.fragment.RankFragment;
import com.qingyii.hxtz.http.NetworkWeb;

import java.util.ArrayList;
import java.util.List;

//import com.ab.http.AbHttpListener;
//import com.ab.http.AbRequestParams;
//import com.ab.util.AbToastUtil;
//import com.ab.view.sliding.AbSlidingTabView;
//import com.andbase.library.http.listener.AbHttpListener;

/**
 * 此activity不用了
 *
 * @author shelia
 */
public class paihangbangActivity extends AbBaseActivity {
    /**
     * AbSlidingTabView 失效 注销相关
     */
//    private AbSlidingTabView mAbSlidingTabView;
    private int currentPage = 1;
    private int pageSize = 8;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paihangbang);
        initUI();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.finish();
    }

    private void initUI() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
//		mAbSlidingTabView=(AbSlidingTabView) findViewById(mAbSlidingTabView);
//		RankFragment page1=new RankFragment(refreshTask("jifen"), loadMoreTask("jifen"));
//		RankFragment page2=new RankFragment(refreshTask("chengji"), loadMoreTask("chengji"));
//		RankFragment page3=new RankFragment(refreshTask("xuefen"), loadMoreTask("xuefen"));
//		RankFragment page4=new RankFragment(refreshTask("jingyan"), loadMoreTask("jingyan"));
        List<ExamRankInfo> list1 = new ArrayList<ExamRankInfo>();
        ExamRankInfo rank1 = new ExamRankInfo("苏伟", 1, 99);
        ExamRankInfo rank2 = new ExamRankInfo("张伟", 2, 98);
        ExamRankInfo rank3 = new ExamRankInfo("李伟", 3, 97);
        list1.add(rank1);
        list1.add(rank2);
        list1.add(rank3);
        List<ExamRankInfo> list2 = new ArrayList<ExamRankInfo>();
        list2.add(rank1);
        list2.add(rank2);
        list2.add(rank3);
        List<ExamRankInfo> list3 = new ArrayList<ExamRankInfo>();
        list3.add(rank1);
        list3.add(rank2);
        list3.add(rank3);
        List<ExamRankInfo> list4 = new ArrayList<ExamRankInfo>();
        list4.add(rank1);
        list4.add(rank2);
        list4.add(rank3);
        List<ExamRankInfo> list5 = new ArrayList<ExamRankInfo>();
        list5.add(rank1);
        list5.add(rank2);
        list5.add(rank3);
        RankFragment page1 = new RankFragment(list1, null);
        RankFragment page2 = new RankFragment(list2, null);
        RankFragment page3 = new RankFragment(list3, null);
        RankFragment page4 = new RankFragment(list4, null);
        RankFragment page5 = new RankFragment(list5, null);
        List<Fragment> mFragments = new ArrayList<Fragment>();
        mFragments.add(page1);
        mFragments.add(page2);
        mFragments.add(page3);
        mFragments.add(page4);
        mFragments.add(page5);
        List<String> tabtexts = new ArrayList<String>();
        tabtexts.add("积分榜");
        tabtexts.add("成绩榜");
        tabtexts.add("学分榜");
        tabtexts.add("经验榜");
        tabtexts.add("勤奋榜");
        //设置样式
//        mAbSlidingTabView.setTabTextColor(Color.BLACK);
//        mAbSlidingTabView.setTabSelectColor(Color.rgb(30, 168, 131));
//        mAbSlidingTabView.setTabBackgroundResource(R.drawable.tab_bg);
//        mAbSlidingTabView.setTabLayoutBackgroundResource(R.mipmap.slide_top);
//        //演示增加一组
//        mAbSlidingTabView.addItemViews(tabtexts, mFragments);
//        mAbSlidingTabView.setTabPadding(20, 8, 20, 8);

    }

    private List<ExamRankInfo> refreshTask(String type) {
        currentPage = 1;
        final List<ExamRankInfo> list = new ArrayList<ExamRankInfo>();
        AbRequestParams params = new AbRequestParams();
        params.put("type", type);
        params.put("pageSize", String.valueOf(pageSize));
        params.put("toPageNo", String.valueOf(currentPage));
        // 下载网络数据
        NetworkWeb web = NetworkWeb.newInstance(paihangbangActivity.this);
        /**
         * AbHttpListener 失效，修改为 AbHttpResponseListener
         */
//        web.findLogList(params, new AbHttpListener() {
//
//            @SuppressWarnings("unchecked")
//            @Override
//            public void onSuccess(List<?> newList) {
//                list.clear();
//                if (newList != null && newList.size() > 0) {
//                    list.addAll((List<ExamRankInfo>) newList);
//                    newList.clear();
//                }
//
//            }
//
//            @Override
//            public void onFailure(String content) {
//                AbToastUtil.showToast(paihangbangActivity.this, content);
//                //显示重试的框
//            }
//
//        });
        web.findLogList(params, new AbHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, Object content) {
                super.onSuccess(statusCode, content);
                List<?> newList = (List<?>) content;
                list.clear();
                if (newList != null && newList.size() > 0) {
                    list.addAll((List<ExamRankInfo>) newList);
                    newList.clear();
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                AbToastUtil.showToast(paihangbangActivity.this, s);
                //显示重试的框

            }
        });
        return list;
    }

    private List<ExamRankInfo> loadMoreTask(String type) {
        currentPage++;
        final List<ExamRankInfo> list = new ArrayList<ExamRankInfo>();
        AbRequestParams params = new AbRequestParams();
        params.put("type", type);
        params.put("pageSize", String.valueOf(pageSize));
        params.put("toPageNo", String.valueOf(currentPage));
        // 下载网络数据
        NetworkWeb web = NetworkWeb.newInstance(paihangbangActivity.this);
        /**
         * AbHttpListener 失效，修改为 AbHttpResponseListener
         */
//        web.findLogList(params, new AbHttpListener() {
//
//            @SuppressWarnings("unchecked")
//            @Override
//            public void onSuccess(List<?> newList) {
//                list.clear();
//                if (newList != null && newList.size() > 0) {
//                    list.addAll((List<ExamRankInfo>) newList);
//                    newList.clear();
//                }
//
//            }
//
//            @Override
//            public void onFailure(String content) {
//                AbToastUtil.showToast(paihangbangActivity.this, content);
//                //显示重试的框
//            }
//
//        });
        web.findLogList(params, new AbHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, Object content) {
                super.onSuccess(statusCode, content);
                List<?> newList = (List<?>) content;
                list.clear();
                if (newList != null && newList.size() > 0) {
                    list.addAll((List<ExamRankInfo>) newList);
                    newList.clear();
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                AbToastUtil.showToast(paihangbangActivity.this, s);
                //显示重试的框

            }
        });
        return list;
    }
}

package com.qingyii.hxt;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxt.adapter.KaoshiHistoryAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.ExamList;
import com.qingyii.hxt.pojo.ExamResult;
import com.qingyii.hxt.util.EmptyUtil;
import com.qingyii.hxt.util.TimeUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 考试历史分数列表
 *
 * @author shelia
 */
// 我的成绩
public class KaoshiHistory extends AbBaseActivity {
    private ListView listView;
    private ImageView iv_back;
    private ExamResult.DataBean erDataBean;
    private List<ExamResult.DataBean.LogsBean> erDataBeanList = new ArrayList<>();
    private ExamList.DataBean examination;
    private AbPullToRefreshView mAbPullToRefreshView;
    /**
     * AbDialogFragment 失效修改为 DialogFragment
     */
    private ShapeLoadingDialog shapeLoadingDialog = null;
    //private DialogFragment abDialogFragment = null;
    private KaoshiHistoryAdapter mAdapter;
    //	private ExamRankListAdapter mAdapter;
    private int page = 1;
    private int pagesize = 100;
    // 列表操作类型：1表示下拉刷新，2表示上拉加载更多
    int type = 1;
    private Handler handler;
    private TextView kaoshi_history_title;
    private TextView kaoshi_history_paihang, kaoshi_history_xingming, kaoshi_history_danwei, kaoshi_history_score;
    private TextView score_tv;
    private TextView time_tv;
    private TextView sort_tv;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kaoshi_history);
        examination = getIntent().getParcelableExtra("examination");
        handler = new Handler(new Callback() {

            @Override
            public boolean handleMessage(Message msg) {

                System.out.println("msg.what ---- = " + msg.what);

                switch (msg.what) {
                    case 1:
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 0:
                        Toast.makeText(KaoshiHistory.this, "数据获取异常！", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(KaoshiHistory.this, "已是最新数据！", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        //刷新bestscore最好成绩  bestduration最好成绩答题耗时  bestordernum最好成绩排名
//                        ArrayList<String> list = (ArrayList<String>) msg.obj;
//                        if (list != null && list.size() >= 3) {
//                            String strDuration = list.get(1);
//                            if (strDuration == null || strDuration.equals("") || strDuration.equals("0")) {
//                                kaoshi_history_scorehint.setText("我在本次考试中的无最好成绩");
//                            } else {
//                                String strScore = list.get(0);
//                                if (EmptyUtil.IsNotEmpty(list.get(0))) {
//                                    if ("repeat".equals(examination.getExamtype())) { //3
//                                        strScore = "第" + strScore + "关";
//                                    } else if ("single".equals(examination.getExamtype()) || "accrued".equals(examination.getExamtype())) {  //2 //4
//                                        strScore = TextUtil.getDefaultDecimalFormat().format(Float.parseFloat(strScore)) + "分";
//                                    }
//                                }
//                                if (EmptyUtil.IsNotEmpty(strDuration)) {
//                                    strDuration = TimeUtil.secToTime02(Integer.parseInt(strDuration));
//                                }
//                                kaoshi_history_scorehint.setText("我在本次考试中的最好成绩为" + strScore + "，答题耗时" + strDuration + "，排名第" + list.get(2) + "位");
//                            }
//                        }
                        break;
                    default:
                        break;
                }
                if (shapeLoadingDialog != null) {
                    shapeLoadingDialog.dismiss();
                }
                if (mAbPullToRefreshView != null) {
                    mAbPullToRefreshView.onHeaderRefreshFinish();
                    mAbPullToRefreshView.onFooterLoadFinish();
                }
                return true;
            }
        });
        initData();
//		refreshTest();
        initUI();
    }

    private void initData() {
        //进度查询
        /**
         * showLoadDialog 修改为 showProgressDialog，监听方式失效。
         */
//        abDialogFragment = AbDialogUtil.showLoadDialog(this, R.mipmap.ic_load, "查询中,请等一小会");
//        abDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//
//            @Override
//            public void onLoad() {
//                // TODO Auto-generated method stub
//                queryScoreList(page, pagesize);
//            }
//        });
//        abDialogFragment = AbDialogUtil.showProgressDialog(this,R.mipmap.ic_load, "查询中,请等一小会");

        queryScoreList(page);

    }

    /**
     * 获取个人考分记录
     */
    private void queryScoreList(final int mypage) {

        String getUrl = XrjHttpClient.getExamResultUrl(String.valueOf(examination.getId()));

        //System.out.println("------" + getUrl);
      Log.i("tmdurl",getUrl);
        OkHttpUtils
                .get()
                .url(getUrl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("page", mypage + "")
                .build()
                .execute(new KaoshiHistory.ResultCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("result_onError", e.toString());
                                 Toast.makeText(KaoshiHistory.this, "网络异常", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(ExamResult response, int id) {
                                 switch (response.getError_code()) {
                                     case 0:
                                         if (mypage == 1)
                                             erDataBeanList.clear();
                                         if (response.getData().getLogs().size() == 0)
                                             Toast.makeText(KaoshiHistory.this, "没有更多了", Toast.LENGTH_SHORT).show();

                                         erDataBean = response.getData();

                                         erDataBeanList.addAll(response.getData().getLogs());

//                                         kaoshi_history_scorehint.setText("您在本次考试中的最好成绩为"+erDataBean.getResult()+"，答题耗时"+TimeUtil.secToTime02(erDataBean.getDuration())+",排行第"+erDataBean.getRank()+"名");
                                         //System.out.println(erDataBeanList.size() + " mylist size-------------");

                                         score_tv.setText(erDataBean.getResult());
                                         time_tv.setText(TimeUtil.secToTime02(erDataBean.getDuration()));
                                         sort_tv.setText(erDataBean.getRank()+"");
                                         if (handler != null)  //更新适配器  notify
                                             handler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class ResultCallback extends com.zhy.http.okhttp.callback.Callback<ExamResult> {

        @Override
        public ExamResult parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("我的成绩排行榜String", result);
            ExamResult eResult = new Gson().fromJson(result, ExamResult.class);
            return eResult;
        }
    }


    private void initUI() {


        kaoshi_history_paihang = (TextView) findViewById(R.id.kaoshi_history_paihang);
        kaoshi_history_xingming = (TextView) findViewById(R.id.kaoshi_history_xingming);
        kaoshi_history_danwei = (TextView) findViewById(R.id.kaoshi_history_danwei);
        kaoshi_history_score = (TextView) findViewById(R.id.kaoshi_history_score);
        score_tv = (TextView) findViewById(R.id.score_tv);
        time_tv = (TextView) findViewById(R.id.time_tv);
        sort_tv = (TextView) findViewById(R.id.sort_tv);


        if (EmptyUtil.IsNotEmpty(examination.getExamtype())) {
            if ("single".equals(examination.getExamtype())) {
                kaoshi_history_paihang.setText("答题序号");
                kaoshi_history_xingming.setText("答题时间");
                kaoshi_history_danwei.setText("成绩");
                kaoshi_history_score.setText("答题耗时");
            } else if ("repeat".equals(examination.getExamtype())) {
                kaoshi_history_paihang.setText("答题序号");
                kaoshi_history_xingming.setText("闯关时间");
                kaoshi_history_danwei.setText("成绩");
                kaoshi_history_score.setText("答题耗时");

            } else if ("accrued".equals(examination.getExamtype())) {
                kaoshi_history_paihang.setText("答题序号");
                kaoshi_history_xingming.setText("答题时间");
                kaoshi_history_danwei.setText("成绩");
                kaoshi_history_score.setText("答题耗时");
            }
        }


        kaoshi_history_title = (TextView) findViewById(R.id.kaoshi_history_title);
        if (examination != null)
            kaoshi_history_title.setText(examination.getExamname());

        iv_back = (ImageView) findViewById(R.id.iv_back_kaoshirank);
        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //实例化listview
        listView = (ListView) findViewById(R.id.pekaoshi_history_list);
        //实例化适配器
        mAdapter = new KaoshiHistoryAdapter(this, erDataBeanList, examination);
//		mAdapter=new ExamRankListAdapter(this, myList);
        listView.setAdapter(mAdapter);

        mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.kaoshi_history_mPull);
        //设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
//				refreshTest();
                type = 1;
                page = 1;
                queryScoreList(page);
            }
        });
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                type = 2;
                page++;
                queryScoreList(page);
            }
        });

        //设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
    }

//    private void refreshTest() {
//        list.add(new ExamRankInfo("张三", 1, 99));
//        list.add(new ExamRankInfo("张四", 2, 98));
//        list.add(new ExamRankInfo("张五", 3, 97));
//        list.add(new ExamRankInfo("张六", 4, 96));
//        list.add(new ExamRankInfo("张七", 5, 95));
//        list.add(new ExamRankInfo("张八", 6, 94));
//        handler.sendEmptyMessage(1);
//    }
}

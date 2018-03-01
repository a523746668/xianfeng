package com.qingyii.hxt;

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
import com.google.gson.Gson;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxt.adapter.KaoshiHistoryAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.ExamList;
import com.qingyii.hxt.pojo.ExamRank;
import com.qingyii.hxt.pojo.ExamResult;
import com.qingyii.hxt.util.EmptyUtil;
import com.qingyii.hxt.util.TextUtil;
import com.qingyii.hxt.util.TimeUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 个人考试历史分数列表
 *
 * @author shelia
 */
public class KaoshiHistoryActivity extends AbBaseActivity {
    private ListView listView;
    private ImageView iv_back;
    private ExamRank.DataBean erDataBean;
    private ExamResult.DataBean ertDataBean;
    private List<ExamResult.DataBean.LogsBean> erDataBeanList = new ArrayList<>();
    private ExamList.DataBean eDataBean;
    //private AbPullToRefreshView mAbPullToRefreshView;
    /**
     * AbDialogFragment 失效修改为 DialogFragment
     */
    private ShapeLoadingDialog shapeLoadingDialog = null;

    private KaoshiHistoryAdapter mAdapter;
    //	private ExamRankListAdapter mAdapter;
    private int page = 1;
    private int pagesize = 100;
    // 列表操作类型：1表示下拉刷新，2表示上拉加载更多
    int type = 1;
    private TextView kaoshi_history_title;
    private TextView kaoshi_history_paihang, kaoshi_history_xingming, kaoshi_history_danwei, kaoshi_history_score;
    private TextView score_tv;
    private TextView time_tv;
    private TextView sort_tv;
    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    mAdapter.notifyDataSetChanged();
                    break;
                case 0:
                    Toast.makeText(KaoshiHistoryActivity.this, "数据获取异常！", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(KaoshiHistoryActivity.this, "已是最新数据！", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    //刷新bestscore最好成绩  bestduration最好成绩答题耗时  bestordernum最好成绩排名
                    ArrayList<String> list = (ArrayList<String>) msg.obj;
                    if (list != null && list.size() >= 3) {
                        String strDuration = list.get(1);
                        if (strDuration == null || strDuration.equals("") || strDuration.equals("0")) {
                        } else {
                            String strScore = list.get(0);
                            if (EmptyUtil.IsNotEmpty(list.get(0))) {
                                if ("repeat".equals(eDataBean.getExamtype())) { //3
                                    strScore = "第" + strScore + "关";
                                } else if ("single".equals(eDataBean.getExamtype()) || "accrued".equals(eDataBean.getExamtype())) {  //2 //4
                                    strScore = TextUtil.getDefaultDecimalFormat().format(Float.parseFloat(strScore)) + "分";
                                }
                            }
                            if (EmptyUtil.IsNotEmpty(strDuration)) {
                                strDuration = TimeUtil.secToTime02(Integer.parseInt(strDuration));
                            }
                            score_tv.setText(strScore + "分");
                            time_tv.setText(strDuration);
                            sort_tv.setText(list.get(2)+"");
//                            kaoshi_history_scorehint.setText("我在本次考试中的最好成绩为" + strScore + "，答题耗时" + strDuration + "，排名第" + list.get(2) + "位");
                        }
                    }
                    break;
                default:
                    break;
            }
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }
//            if (mAbPullToRefreshView != null) {
//                mAbPullToRefreshView.onHeaderRefreshFinish();
//                mAbPullToRefreshView.onFooterLoadFinish();
//            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kaoshi_history);

        eDataBean = getIntent().getParcelableExtra("eDataBean");
        erDataBean = getIntent().getParcelableExtra("erDataBean");

        TextView activity_tltle = (TextView) findViewById(R.id.activity_tltle);
        activity_tltle.setText(erDataBean.getUsername() + "的考试成绩");

        shapeLoadingDialog = new ShapeLoadingDialog(this);

        initData();
        initUI();
    }

    private void initData() {
        //进度查询
        shapeLoadingDialog.setLoadingText("查询中,请等一小会");
        shapeLoadingDialog.show();

        queryScoreList(page);
    }

    private void initUI() {


        kaoshi_history_paihang = (TextView) findViewById(R.id.kaoshi_history_paihang);
        kaoshi_history_xingming = (TextView) findViewById(R.id.kaoshi_history_xingming);
        kaoshi_history_danwei = (TextView) findViewById(R.id.kaoshi_history_danwei);
        kaoshi_history_score = (TextView) findViewById(R.id.kaoshi_history_score);
        score_tv = (TextView) findViewById(R.id.score_tv);
        time_tv = (TextView) findViewById(R.id.time_tv);
        sort_tv = (TextView) findViewById(R.id.sort_tv);

        if (EmptyUtil.IsNotEmpty(eDataBean.getExamtype()))
            if ("single".equals(eDataBean.getExamtype())) {
                kaoshi_history_paihang.setText("答题序号");
                kaoshi_history_xingming.setText("答题时间");
                kaoshi_history_danwei.setText("成绩");
                kaoshi_history_score.setText("答题耗时");
            } else if ("repeat".equals(eDataBean.getExamtype())) {
                kaoshi_history_paihang.setText("答题序号");
                kaoshi_history_xingming.setText("闯关时间");
                kaoshi_history_danwei.setText("成绩");
                kaoshi_history_score.setText("答题耗时");

            } else if ("accrued".equals(eDataBean.getExamtype())) {
                kaoshi_history_paihang.setText("答题序号");
                kaoshi_history_xingming.setText("答题时间");
                kaoshi_history_danwei.setText("成绩");
                kaoshi_history_score.setText("答题耗时");
            }

        kaoshi_history_title = (TextView) findViewById(R.id.kaoshi_history_title);
        if (eDataBean != null)
            kaoshi_history_title.setText(eDataBean.getExamname());

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
        mAdapter = new KaoshiHistoryAdapter(this, erDataBeanList, eDataBean);
        listView.setAdapter(mAdapter);

//        mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.kaoshi_history_mPull);
//        //设置进度条的样式
//        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
//        //设置监听器
//        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
//
//            @Override
//            public void onHeaderRefresh(AbPullToRefreshView view) {
////				refreshTest();
//                type = 1;
//                page = 1;
//                //进度查询
//                shapeLoadingDialog.setLoadingText("查询中,请等一小会");
//                shapeLoadingDialog.show();
//                queryScoreList(page);
//            }
//        });
//        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
//
//            @Override
//            public void onFooterLoad(AbPullToRefreshView view) {
//                type = 2;
//                page++;
//                queryScoreList(page);
//            }
//        });
    }

    /**
     * 获取个人考分记录
     */
    private void queryScoreList(final int mypage) {

        String getUrl = XrjHttpClient.getExamResultUrl(String.valueOf(eDataBean.getId()));

        Log.e("user_id", erDataBean.getUser_id() + "");
        Log.e("page", mypage + "");

        OkHttpUtils
                .get()
                .url(getUrl + "/" + erDataBean.getUser_id())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                //.addParams("examination_id", eDataBean.getId() + "")
                //.addParams("user_id", erDataBean.getUser_id() + "")
                .addParams("page", mypage + "")
                .build()
                .execute(new KaoshiHistoryActivity.ResultCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("result_onError", e.toString());
                                 Toast.makeText(KaoshiHistoryActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(ExamResult response, int id) {
                                 switch (response.getError_code()) {
                                     case 0:
                                         if (mypage == 1)
                                             erDataBeanList.clear();
                                         if (response.getData().getLogs().size() == 0)
                                             Toast.makeText(KaoshiHistoryActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();

                                         ertDataBean = response.getData();

//                                         kaoshi_history_scorehint.setText("您在本次考试中的最好成绩为" + ertDataBean.getResult() + "，答题耗时" + TimeUtil.secToTime02(ertDataBean.getDuration()) + ",排行第" + ertDataBean.getRank() + "名");
                                         score_tv.setText(ertDataBean.getResult());
                                         time_tv.setText(TimeUtil.secToTime02(ertDataBean.getDuration()));
                                         sort_tv.setText(ertDataBean.getRank()+"");
                                         erDataBeanList.addAll(response.getData().getLogs());

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
}

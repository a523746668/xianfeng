package com.qingyii.hxtz;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxtz.adapter.ExamRankListAdapter;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.ExamList;
import com.qingyii.hxtz.pojo.ExamRank;
import com.qingyii.hxtz.pojo.ExamRankUnit;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class KCPersonalActivity extends AppCompatActivity {
    private ListView mListView;
    private ExamRankListAdapter mExamRankListAdapter;
    private AbPullToRefreshView mAbPullToRefreshView;
    private TextView personalfragment_score, personalfragment_paiming, personalfragment_xingming, personalfragment_unit;

    private List<ExamRank.DataBean> erDataBeanList = new ArrayList<>();
    private ExamList.DataBean eDataBean;
    private ExamRankUnit.DataBean eruDataBean;

    int type = 1;
    private int page = 1;
    //private int pagesize = 100;

    private ShapeLoadingDialog shapeLoadingDialog = null;

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (shapeLoadingDialog != null)
                shapeLoadingDialog.dismiss();

            mAbPullToRefreshView.onFooterLoadFinish();
            switch (msg.what) {
                case 1:
                    mExamRankListAdapter.notifyDataSetChanged();
                    //listView.invalidateViews();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcpersonal);

        eDataBean = this.getIntent().getParcelableExtra("eDataBean");
        eruDataBean = this.getIntent().getParcelableExtra("eruDataBean");
        //tltle设置
        TextView activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
        activity_tltle_name.setText(eruDataBean.getName() + " 成绩排行");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        personalfragment_paiming = (TextView) findViewById(R.id.personalfragment_paiming);
        personalfragment_xingming = (TextView) findViewById(R.id.personalfragment_xingming);
        personalfragment_unit = (TextView) findViewById(R.id.personalfragment_unit);

        personalfragment_paiming.setText("排名");
        personalfragment_xingming.setText("姓名");
        personalfragment_unit.setText("单位");

        //当考试类型是 “单次命题 ”时显示  “得分”，当考试类型是 “答题闯关 ”时显示  “闯关数”
        personalfragment_score = (TextView) findViewById(R.id.personalfragment_score);
        personalfragment_score.setText("成绩");

        //实例化listview
        mListView = (ListView) findViewById(R.id.personalfragment_list);
        mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.personalfragment_mPull_kaoshirank);

        //实例化适配器
        mExamRankListAdapter = new ExamRankListAdapter(this, erDataBeanList);
        mListView.setAdapter(mExamRankListAdapter);

        //list点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(KCPersonalActivity.this, KaoshiHistoryActivity.class);
                intent.putExtra("eDataBean", eDataBean);
                intent.putExtra("erDataBean", erDataBeanList.get(position));
                startActivity(intent);
//                Intent intent = new Intent(activity, PersonalScoreActivity.class);
//                intent.putExtra("score", myList.get(position));
//                intent.putExtra("typess", typess);
//                intent.putExtra("examinationname", examinationname);
//                intent.putExtra("examinationid", examinationid);
//                startActivity(intent);
            }
        });


        //下拉刷新监听
        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                // TODO Auto-generated method stub
                refreshTask();

            }
        });

        //上拉加载监听
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                // TODO Auto-generated method stub
                loadMoreTask();
            }
        });

        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));

        queryScoreList(page);
    }

    protected void refreshTask() {
        type = 1;
        page = 1;
        queryScoreList(1);
        mAbPullToRefreshView.onHeaderRefreshFinish();
    }

    protected void loadMoreTask() {
        type = 2;
        page++;
        queryScoreList(page);
        mAbPullToRefreshView.onFooterLoadFinish();
    }

    private void queryScoreList(final int mypage) {
        String getUrl = XrjHttpClient.getExamRankingPersonalUrl();

        shapeLoadingDialog = new ShapeLoadingDialog(KCPersonalActivity.this);
        shapeLoadingDialog.setLoadingText("查询中,请等一小会");
        shapeLoadingDialog.show();

        OkHttpUtils
                .post()
                .url(getUrl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("examination_id", eDataBean.getId() + "")
                .addParams("department_id", eruDataBean.getDepartment_id() + "")
                .addParams("group_id", eruDataBean.getGroup_id() + "")
                .addParams("page", mypage + "")
                .build()
                .execute(new RankCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("result_onError", e.toString());
                                 Toast.makeText(KCPersonalActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                                 if (shapeLoadingDialog != null)
                                     shapeLoadingDialog.dismiss();
                             }

                             @Override
                             public void onResponse(ExamRank response, int id) {

                                 switch (response.getError_code()) {
                                     case 0:
                                         if (mypage == 1)
                                             erDataBeanList.clear();
                                         if (response.getData().size() == 0)
                                             Toast.makeText(KCPersonalActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();

                                         erDataBeanList.addAll(response.getData());

                                         if (handler != null)  //更新 adapter
                                             handler.sendEmptyMessage(1);

                                         break;
                                     case 401: //登录过期
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

    /**
     * 获取个人考分列表
     */
    private abstract class RankCallback extends Callback<ExamRank> {

        @Override
        public ExamRank parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("个人成绩排行 String", result);
            ExamRank eResult = new Gson().fromJson(result, ExamRank.class);
            return eResult;
        }
    }
}

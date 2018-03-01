package com.qingyii.hxt.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.mingle.widget.ShapeLoadingDialog;
import com.qingyii.hxt.KCPersonalActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.ScoreRanking;
import com.qingyii.hxt.adapter.UnitFragmentAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.ExamList;
import com.qingyii.hxt.pojo.ExamRankUnit;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

@SuppressLint("NewApi")
public class UnitFragment extends Fragment {
    private Activity activity;
    private ListView listView;
    private AbPullToRefreshView abPullToRefreshView;
    private List<ExamRankUnit.DataBean> eruDataBeanList = new ArrayList<>();
    //private ArrayList<UnitScore> list2 = new ArrayList<UnitScore>();
    private UnitFragmentAdapter adapter;
    private TextView personalfragment_score, personalfragment_paiming, personalfragment_xingming, personalfragment_unit;
    public String examinationid;
    public String typess;
    public String examinationname;
    public String groupid;
    private Handler handler;
    private int page = 1;
    private int pagesize = 50;

    public ExamList.DataBean examination;
    // 列表操作类型：1表示下拉刷新，2表示上拉加载更多
    int type = 1;

    //private DialogFragment abDialogFragment = null;
    private ShapeLoadingDialog shapeLoadingDialog = null;

    private int fPage; //fragment page

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        activity = this.getActivity();
//		type=1;
//		page=1;
//		getUnitScore(page, pagesize);
    }


    public static UnitFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ScoreRanking.RANKING_DEPARTMENT_FRAGMENT_TAG, page);
        UnitFragment unitFragment = new UnitFragment();
        unitFragment.setArguments(args);
        return unitFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fPage = getArguments().getInt(ScoreRanking.RANKING_DEPARTMENT_FRAGMENT_TAG);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //进度查询
//				 abDialogFragment=AbDialogUtil.showLoadDialog(activity, R.drawable.ic_load, "查询中,请等一小会");
//				 abDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//
//					@Override
//					public void onLoad() {
//						// TODO Auto-generated method stub
////						type=1;
////						page=1;
////						queryScoreList(page,pagesize);
//					}
//				});
//				type=1;
//				page=1;
            getUnitScore(page);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                // TODO Auto-generated method stub

                if (shapeLoadingDialog != null) {
                    shapeLoadingDialog.dismiss();
                }

                abPullToRefreshView.onFooterLoadFinish();
                if (msg.what == 1) {
                    adapter.notifyDataSetChanged();
                    listView.invalidateViews();
                } else if (msg.what == 0) {
                    Toast.makeText(activity, "数据获取异常！", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 5) {
                    Toast.makeText(activity, "已是最新数据！", Toast.LENGTH_SHORT).show();
                }


                return false;
            }
        });


        activity = this.getActivity();

        examination = activity.getIntent().getParcelableExtra("examination");
        examinationid = String.valueOf(examination.getId());
        typess = examination.getExamtype();


        View view = inflater.inflate(R.layout.unitfragment, null);


        //实例化listview
        listView = (ListView) view.findViewById(R.id.personalfragment_list);
        abPullToRefreshView = (AbPullToRefreshView) view.findViewById(R.id.personalfragment_mPull_kaoshirank);


        //实例化适配器
        adapter = new UnitFragmentAdapter(activity, eruDataBeanList, typess, groupid);
        listView.setAdapter(adapter);

        //listview点击事件
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(activity, KCPersonalActivity.class);
                intent.putExtra("eDataBean", examination);
                intent.putExtra("eruDataBean", eruDataBeanList.get(position));
                startActivity(intent);
            }
        });

        //下拉刷新监听
        abPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                // TODO Auto-generated method stub
                refreshTask();
            }
        });


        //上啦加载监听
        abPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                // TODO Auto-generated method stub
                loadMoreTask();
            }
        });


        //设置样式
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));


        personalfragment_paiming = (TextView) view.findViewById(R.id.personalfragment_paiming);
        personalfragment_xingming = (TextView) view.findViewById(R.id.personalfragment_xingming);
        personalfragment_unit = (TextView) view.findViewById(R.id.personalfragment_unit);
        personalfragment_score = (TextView) view.findViewById(R.id.personalfragment_score);


        personalfragment_paiming.setText("排名");
        personalfragment_xingming.setText("单位");
        personalfragment_unit.setText("参与人数");

        //当考试类型是 “单词命题 ”时显示  “平均得分”，当考试类型是 “答题闯关 ”时显示  “平均闯关数”
        if ("single".equals(typess)) {
            personalfragment_score.setText("平均成绩");
        } else if ("repeat".equals(typess)) {
            personalfragment_score.setText("平均关数");
        } else if ("accrued".equals(typess)) {
            personalfragment_score.setText("平均关数");
        }


//		abDialogFragment=AbDialogUtil.showLoadDialog(activity, R.drawable.ic_load, "查询中,请等一小会");
        return view;
    }


    /**
     * 单位成绩排名接口
     */


    private void getUnitScore(final int mypage) {

        String getUrl = XrjHttpClient.getExamRankingDepartmentUrl();
        //System.out.println("examination_id ----"+examinationid +" url--"+getUrl);
//        int size = eruDataBeanList.size();
//        if (mypage == 1 && size > 0)  //不每次都请求网络
//            return;

        shapeLoadingDialog = new ShapeLoadingDialog(UnitFragment.this.getActivity());
        shapeLoadingDialog.setLoadingText("查询中,请等一小会");
        shapeLoadingDialog.show();

        OkHttpUtils
                .post()
                .url(getUrl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("examination_id", examinationid)
                .addParams("page", mypage + "")

                .build()
                .execute(new UnitFragment.RankCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("result_onError", e.toString());
                                 Toast.makeText(activity, "网络异常", Toast.LENGTH_LONG).show();
                                 if (shapeLoadingDialog != null) {
                                     shapeLoadingDialog.dismiss();
                                 }
                             }

                             @Override
                             public void onResponse(ExamRankUnit response, int id) {
                                 if (shapeLoadingDialog != null) {
                                     shapeLoadingDialog.dismiss();
                                 }
                                 switch (response.getError_code()) {
                                     case 0:
                                         //System.out.println(response.toString()+"-------------");
                                         if (mypage == 1)
                                             eruDataBeanList.clear();
                                         if (response.getData().size() == 0)
                                             Toast.makeText(UnitFragment.this.getActivity(), "没有更多了", Toast.LENGTH_SHORT).show();

                                         eruDataBeanList.addAll(response.getData());
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

    private abstract class RankCallback extends Callback<ExamRankUnit> {

        @Override
        public ExamRankUnit parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("单位成绩排行 String", result);
            //System.out.println("result---unit--"+result);
            ExamRankUnit eResult = new Gson().fromJson(result, ExamRankUnit.class);
            return eResult;
        }
    }

    public void onStart() {
        super.onStart();
//	type=1;
//	page=1;
//	getUnitScore(page, pagesize);
    }

    private void refreshTask() {
        type = 1;
        page = 1;
        getUnitScore(1);
        abPullToRefreshView.onHeaderRefreshFinish();
    }

    private void loadMoreTask() {
        type = 2;
        page++;
        getUnitScore(page);
        abPullToRefreshView.onFooterLoadFinish();
    }
}

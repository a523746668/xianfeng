package com.qingyii.hxtz.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Build;
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
import com.qingyii.hxtz.KaoshiHistoryActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.ScoreRanking;
import com.qingyii.hxtz.adapter.ExamRankListAdapter;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.pojo.ExamList;
import com.qingyii.hxtz.pojo.ExamRank;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

//import com.ab.fragment.AbDialogFragment;
//import com.ab.fragment.AbDialogFragment.AbDialogOnLoadListener;
//import com.ab.util.AbDialogUtil;
//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PersonalFragment extends Fragment {

    private Activity activity;
    private ListView listView;
    private TextView personalfragment_score, personalfragment_paiming, personalfragment_xingming, personalfragment_unit;
    private AbPullToRefreshView mAbPullToRefreshView;
    private List<ExamRank.DataBean> erDataBeanList = new ArrayList<>();
    private ExamRankListAdapter mAdapter;
    private int page = 1;
    private int pagesize = 100;
    //	private int pagesize=Integer.MAX_VALUE;
    // 列表操作类型：1表示下拉刷新，2表示上拉加载更多
    int type = 1;
    //	private Examination examination;
    public String examinationid;
    public String typess;
    public ExamList.DataBean examination;
    public String examinationname;
    public String groupid;

    private int fPage; //fragment page

    //private DialogFragment abDialogFragment = null;
    private ShapeLoadingDialog shapeLoadingDialog = null;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
//                if (abDialogFragment != null)
//                    abDialogFragment.dismiss();

            if (shapeLoadingDialog != null)
                shapeLoadingDialog.dismiss();

            mAbPullToRefreshView.onFooterLoadFinish();
            switch (msg.what) {
                case 1:
                    mAdapter.notifyDataSetChanged();
                    listView.invalidateViews();
                    break;
                case 0:
                    Toast.makeText(activity, "数据获取异常！", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(activity, "已是最新数据！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    public static PersonalFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ScoreRanking.RANKING_PERSONAL_FRAGMENT_TAG, page);
        PersonalFragment pageFragment = new PersonalFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fPage = getArguments().getInt(ScoreRanking.RANKING_PERSONAL_FRAGMENT_TAG);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        activity = this.getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        activity = this.getActivity();

        examination = activity.getIntent().getParcelableExtra("examination");
        examinationid = String.valueOf(examination.getId());
        typess = examination.getExamtype();

        View view = inflater.inflate(R.layout.personalfragment, null);

        personalfragment_paiming = (TextView) view.findViewById(R.id.personalfragment_paiming);
        personalfragment_xingming = (TextView) view.findViewById(R.id.personalfragment_xingming);
        personalfragment_unit = (TextView) view.findViewById(R.id.personalfragment_unit);

        personalfragment_paiming.setText("排名");
        personalfragment_xingming.setText("姓名");
        personalfragment_unit.setText("单位");

        //当考试类型是 “单次命题 ”时显示  “得分”，当考试类型是 “答题闯关 ”时显示  “闯关数”
        personalfragment_score = (TextView) view.findViewById(R.id.personalfragment_score);
        personalfragment_score.setText("成绩");

        //实例化listview
        listView = (ListView) view.findViewById(R.id.personalfragment_list);
        mAbPullToRefreshView = (AbPullToRefreshView) view.findViewById(R.id.personalfragment_mPull_kaoshirank);

        //实例化适配器
        mAdapter = new ExamRankListAdapter(activity, erDataBeanList);
        listView.setAdapter(mAdapter);

        //list点击事件
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(activity, KaoshiHistoryActivity.class);
                intent.putExtra("eDataBean", examination);
                intent.putExtra("erDataBean", erDataBeanList.get(position));
                startActivity(intent);
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

        return view;
    }

//    @SuppressLint("NewApi")
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        // TODO Auto-generated method stub
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            //进度查询
////			 abDialogFragment=AbDialogUtil.showLoadDialog(activity, R.drawable.ic_load, "查询中,请等一小会");
////			 abDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
////
////				@Override
////				public void onLoad() {
////					// TODO Auto-generated method stub
//////					type=1;
//////					page=1;
//////					queryScoreList(page,pagesize);
////				}
////			});
//            type = 1;
//            page = 1;
//            queryScoreList(page, pagesize);
//        }
//    }

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

    private void queryScoreList(final int mypage) {
        String getUrl = XrjHttpClient.getExamRankingPersonalUrl();

//        int size = erDataBeen.size();

//        if (mypage == 1 && size > 0)  //不每次都请求网络
//            return;

        shapeLoadingDialog = new ShapeLoadingDialog(PersonalFragment.this.getActivity());
        shapeLoadingDialog.setLoadingText("查询中,请等一小会");
        shapeLoadingDialog.show();
       Log.i("tmdexaminationid",examinationid);
        OkHttpUtils
                .post()
                .url(getUrl)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("examination_id", examinationid)
                .addParams("page", mypage + "")
                .build()
                .execute(new RankCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("result_onError", e.toString());
                                 Toast.makeText(activity, "网络异常", Toast.LENGTH_LONG).show();
                                 if (shapeLoadingDialog != null) {
                                     shapeLoadingDialog.dismiss();
                                 }
                             }

                             @Override
                             public void onResponse(ExamRank response, int id) {

                                 switch (response.getError_code()) {
                                     case 0:
                                         //System.out.println(response.toString()+"-------------");
                                         if (mypage == 1)
                                             erDataBeanList.clear();
                                         if (response.getData().size() == 0)
                                             Toast.makeText(PersonalFragment.this.getActivity(), "没有更多了", Toast.LENGTH_SHORT).show();

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
}

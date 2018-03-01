package com.qingyii.hxt.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qingyii.hxt.IntegralDetailsActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.adapter.HeaderAndFooterRecyclerViewAdapter;
import com.qingyii.hxt.adapter.SectionLevelAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.DocumentaryMy;
import com.qingyii.hxt.pojo.RankingList;
import com.qingyii.hxt.util.EndlessRecyclerOnScrollListener;
import com.qingyii.hxt.util.LoadingFooter;
import com.qingyii.hxt.util.RecyclerViewStateUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 部门星级
 */
public class SectionLevelFragment extends Fragment {
    private View sectionLevelFragment;
    private View headerView;
    private Intent intent;

    private RecyclerView recyclerView;
    private SectionLevelAdapter sectionLevelAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

    private TextView fragment_guan_li_in_null;

    //    private RankingList.DataBeanX rDataBeanX;
    private List<RankingList.DataBean> rDataBeanList = new ArrayList<>();

    private boolean loadJudge = true;
    private final int REQUEST_COUNT = 10;
    private boolean entry_permit = true;

    private DocumentaryMy.DataBean dDataBean;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (SectionLevelFragment.this == null || SectionLevelFragment.this.getActivity().isFinishing()) {
                return;
            }
            switch (msg.what) {
                case 0:
                    sectionLevelAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    RecyclerViewStateUtils.setFooterViewState(SectionLevelFragment.this.recyclerView, LoadingFooter.State.Normal);
                    break;
                case 2:
                    RecyclerViewStateUtils.setFooterViewState(SectionLevelFragment.this.getActivity(), SectionLevelFragment.this.recyclerView,
                            REQUEST_COUNT, LoadingFooter.State.NetWorkError, SectionLevelFragment.this.mFooterClick);
                    break;
                default:
                    break;
            }
            sectionLevelAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (sectionLevelFragment == null) {
            sectionLevelFragment = inflater.inflate(R.layout.fragment_guan_li_in, null);
            headerView = inflater.inflate(R.layout.fragment_unit_level_head, null);
            recyclerView = (RecyclerView) sectionLevelFragment.findViewById(R.id.fragment_guan_li_in);
            fragment_guan_li_in_null = (TextView) sectionLevelFragment.findViewById(R.id.fragment_guan_li_in_null);

            sectionLevelAdapter = new SectionLevelAdapter(this.getActivity(), rDataBeanList);
            mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(sectionLevelAdapter);

            mHeaderAndFooterRecyclerViewAdapter.addHeaderView(headerView);

            recyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            recyclerView.addOnScrollListener(mOnScrollListener);

//            if (MyApplication.userUtil.getCheck_level() == 5)
            sectionLevelAdapter.setOnItemClickLitener(new SectionLevelAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {

                    intent = new Intent(SectionLevelFragment.this.getActivity(), IntegralDetailsActivity.class);
                    intent.putExtra("dDataBean", dDataBean);
                    intent.putExtra("UserID", rDataBeanList.get(position).getId());
                    intent.putExtra("Level", rDataBeanList.get(position).getStars());
                    intent.putExtra("userName", rDataBeanList.get(position).getTruename());
                    startActivity(intent);
                }
            });

//            this.loadData();

            Bundle bundle = getArguments();//从activity传过来的Bundle
            if (bundle != null) {
                dDataBean = bundle.getParcelable("dDataBean");
            }
            getDateList();
        }

        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            dDataBean = bundle.getParcelable("dDataBean");
        }

        return sectionLevelFragment;
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(SectionLevelFragment.this.getActivity(),
                    recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            requestData();
            getDateList();
        }
    };

//    private void loadData(){
//        for (int i = 0; i < 10; i++) {
//            uList.add(i + "");
//        }
////        handler.sendEmptyMessage(0);
//    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
            if (state == LoadingFooter.State.Loading) {
                Log.d("@Cundong", "the state is Loading, just wait..");
                return;
            }

            if (loadJudge) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(SectionLevelFragment.this.getActivity(),
                        recyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
                // 网络请求
                getDateList();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(SectionLevelFragment.this.getActivity(),
                        recyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    private void requestData() {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                for (int i = 0; i < 10; i++) {
//                    uList.add(i + "");
//                }
//                //加载成功时
//                handler.sendEmptyMessage(1);
//
//            }
//        }.start();
    }

    /**
     * 排行榜数据
     */
    private int page = 1;
    final private int PAGESIZE = 40;

    public void getDateList() {

        if (entry_permit) {
            entry_permit = false;

            OkHttpUtils
                    .get()
                    .url(XrjHttpClient.getRankingListUrl() + "star/" + "department/" + dDataBean.getCurr_month())
                    .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                    .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                    .addParams("page", page + "")
                    .addParams("pagesize", PAGESIZE + "")
                    .build()
                    .execute(new RankingListCallback() {
                                 @Override
                                 public void onError(Call call, Exception e, int id) {
                                     Log.e("Fragment_onError", e.toString());
//                                 Toast.makeText(SectionLevelFragment.this.getActivity(), "网络异常—排行榜", Toast.LENGTH_LONG).show();
                                     handler.sendEmptyMessage(2);
                                 }

                                 @Override
                                 public void onResponse(RankingList response, int id) {
                                     Log.e("FragmentCallback", response.getError_msg());

                                     try {
                                         switch (response.getError_code()) {
                                             case 0:
                                                 entry_permit = true;
//                                             rDataBeanX = response.getData();
                                                 rDataBeanList.addAll(response.getData());
                                                 if (response.getData().size() < PAGESIZE)
                                                     loadJudge = false;
                                                 if (rDataBeanList != null) {
                                                     if (rDataBeanList.size() > 0) {
                                                         fragment_guan_li_in_null.setVisibility(View.GONE);
                                                     } else {
                                                         fragment_guan_li_in_null.setVisibility(View.VISIBLE);
                                                     }
                                                 } else {
                                                     fragment_guan_li_in_null.setVisibility(View.VISIBLE);
                                                 }
                                                 page++;
                                                 handler.sendEmptyMessage(1);
                                                 break;
                                             default:
                                                 handler.sendEmptyMessage(2);
                                                 break;
                                         }
                                     } catch (Exception e) {

                                     }
                                 }
                             }
                    );
        }
    }

    private abstract class RankingListCallback extends Callback<RankingList> {

        @Override
        public RankingList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("部门排行榜 JSON", result);
            RankingList rankingList = new Gson().fromJson(result, RankingList.class);
            return rankingList;
        }
    }
}

package com.qingyii.hxt.fragment;

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
import com.qingyii.hxt.R;
import com.qingyii.hxt.adapter.BranchRankAdapter;
import com.qingyii.hxt.adapter.HeaderAndFooterRecyclerViewAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.BranchRank;
import com.qingyii.hxt.pojo.DocumentaryMy;
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
 * 支部排行
 */
public class BranchRankFragment extends Fragment {
    private View branchRankFragment;
    private View headerView;

    private RecyclerView recyclerView;
    private BranchRankAdapter branchRankAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

    private TextView fragment_guan_li_in_null;

    private List<BranchRank.DataBean> bDataBeanList = new ArrayList<>();

    private boolean loadJudge = true;
    private final int REQUEST_COUNT = 10;
    private boolean entry_permit = true;

    private DocumentaryMy.DataBean dDataBean;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (BranchRankFragment.this == null || BranchRankFragment.this.getActivity().isFinishing()) {
                return;
            }
            switch (msg.what) {
                case 0:
                    branchRankAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    RecyclerViewStateUtils.setFooterViewState(BranchRankFragment.this.recyclerView, LoadingFooter.State.Normal);
                    break;
                case 2:
                    RecyclerViewStateUtils.setFooterViewState(BranchRankFragment.this.getActivity(), BranchRankFragment.this.recyclerView,
                            REQUEST_COUNT, LoadingFooter.State.NetWorkError, BranchRankFragment.this.mFooterClick);
                    break;
                default:
                    break;
            }
            branchRankAdapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(BranchRankFragment.this.getActivity(),
                    recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            getDateList();
        }
    };

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
                RecyclerViewStateUtils.setFooterViewState(BranchRankFragment.this.getActivity(),
                        recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                // 网络请求
                getDateList();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(BranchRankFragment.this.getActivity(),
                        recyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (branchRankFragment == null) {
            branchRankFragment = inflater.inflate(R.layout.fragment_guan_li_in, null);
            headerView = inflater.inflate(R.layout.fragment_branch_rank_head, null);
            recyclerView = (RecyclerView) branchRankFragment.findViewById(R.id.fragment_guan_li_in);
            fragment_guan_li_in_null = (TextView) branchRankFragment.findViewById(R.id.fragment_guan_li_in_null);

            branchRankAdapter = new BranchRankAdapter(this.getActivity(), bDataBeanList);
            mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(branchRankAdapter);

            mHeaderAndFooterRecyclerViewAdapter.addHeaderView(headerView);

            recyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            recyclerView.addOnScrollListener(mOnScrollListener);

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

        return branchRankFragment;
    }

    /**
     * 排行榜数据
     */
    private int page = 1;
    final private int PAGESIZE = 40;

    public void getDateList() {
//        Log.e("RankingList_URL", XrjHttpClient.getRankingListUrl() + "group/" + dDataBean.getCurr_month());

        if (entry_permit) {
            entry_permit = false;
            Log.e("RankingList_month", dDataBean.getCurr_month());

            OkHttpUtils
                    .get()
                    .url(XrjHttpClient.getRankingListUrl() + "group/" + dDataBean.getCurr_month())
                    .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                    .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                    .addParams("page", page + "")
                    .addParams("pagesize", PAGESIZE + "")
                    .build()
                    .execute(new BranchRankCallback() {
                                 @Override
                                 public void onError(Call call, Exception e, int id) {
                                     Log.e("RankingList_onError", e.toString());
//                                 Toast.makeText(BranchRankFragment.this.getActivity(), "网络异常—我的纪实数据", Toast.LENGTH_LONG).show();
                                     handler.sendEmptyMessage(2);
                                 }

                                 @Override
                                 public void onResponse(BranchRank response, int id) {
                                     Log.e("RankingListCallback", response.getError_msg());

                                     try {
                                         switch (response.getError_code()) {
                                             case 0:
                                                 entry_permit = true;
                                                 bDataBeanList.addAll(response.getData());
                                                 if (response.getData().size() < PAGESIZE)
                                                     loadJudge = false;
                                                 if (bDataBeanList != null) {
                                                     if (bDataBeanList.size() > 0) {
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

    private abstract class BranchRankCallback extends Callback<BranchRank> {

        @Override
        public BranchRank parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("支部排行榜 JSON", result);
            BranchRank branchRank = new Gson().fromJson(result, BranchRank.class);
            return branchRank;
        }
    }
}

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
import com.qingyii.hxt.adapter.SectionTotalAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.DocumentaryMy;
import com.qingyii.hxt.pojo.IntegralList;
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
 * 单位积分
 */
public class SectionTotalFragment extends Fragment {
    private View sectionTotalFragment;
    private View headerView;
    private Intent intent;

    private RecyclerView recyclerView;
    private SectionTotalAdapter sectionTotalAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

    private TextView fragment_guan_li_in_null;

    //    private RankingList.DataBeanX rDataBeanX;
//    private List<RankingList.DataBeanX.DataBean> rDataBeanList = new ArrayList<>();
    private List<IntegralList.DataBean> rDataBeanList = new ArrayList<>();

    private boolean loadJudge = true;
    private final int REQUEST_COUNT = 10;
    private boolean entry_permit = true;

    private DocumentaryMy.DataBean dDataBean;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            final UntreatedFragment uFragment = weakReference.get();
            if (SectionTotalFragment.this == null || SectionTotalFragment.this.getActivity().isFinishing()) {
                return;
            }
            switch (msg.what) {
                case 0:
                    sectionTotalAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    RecyclerViewStateUtils.setFooterViewState(SectionTotalFragment.this.recyclerView, LoadingFooter.State.Normal);
                    break;
                case 2:
                    RecyclerViewStateUtils.setFooterViewState(SectionTotalFragment.this.getActivity(), SectionTotalFragment.this.recyclerView,
                            REQUEST_COUNT, LoadingFooter.State.NetWorkError, SectionTotalFragment.this.mFooterClick);
                    break;
                default:
                    break;
            }
            sectionTotalAdapter.notifyDataSetChanged();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (sectionTotalFragment == null) {
            sectionTotalFragment = inflater.inflate(R.layout.fragment_guan_li_in, null);
            headerView = inflater.inflate(R.layout.fragment_unit_total_head, null);
            recyclerView = (RecyclerView) sectionTotalFragment.findViewById(R.id.fragment_guan_li_in);
            fragment_guan_li_in_null = (TextView) sectionTotalFragment.findViewById(R.id.fragment_guan_li_in_null);

            sectionTotalAdapter = new SectionTotalAdapter(this.getActivity(), rDataBeanList);
            mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(sectionTotalAdapter);

            mHeaderAndFooterRecyclerViewAdapter.addHeaderView(headerView);

            recyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            recyclerView.addOnScrollListener(mOnScrollListener);

//            if (MyApplication.userUtil.getCheck_level() == 5)
            sectionTotalAdapter.setOnItemClickLitener(new SectionLevelAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {

                    intent = new Intent(SectionTotalFragment.this.getActivity(), IntegralDetailsActivity.class);
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

        return sectionTotalFragment;
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(SectionTotalFragment.this.getActivity(),
                    recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            requestData();
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
                RecyclerViewStateUtils.setFooterViewState(SectionTotalFragment.this.getActivity(),
                        recyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
                // 网络请求
                getDateList();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(SectionTotalFragment.this.getActivity(),
                        recyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    private void requestData() {
//        new Thread() {
//
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
                    .url(XrjHttpClient.getRankingListUrl() + "score/" + "department/" + dDataBean.getCurr_month())
                    .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                    .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                    .addParams("page", page + "")
                    .addParams("pagesize", PAGESIZE + "")
                    .build()
                    .execute(new IntegralListCallback() {
                                 @Override
                                 public void onError(Call call, Exception e, int id) {
                                     Log.e("Fragment_onError", e.toString());
//                                 Toast.makeText(SectionTotalFragment.this.getActivity(), "网络异常—排行榜", Toast.LENGTH_LONG).show();
                                     handler.sendEmptyMessage(2);
                                 }

                                 @Override
                                 public void onResponse(IntegralList response, int id) {
                                     Log.e("FragmentCallback", response.getError_msg());

                                     switch (response.getError_code()) {
                                         case 0:
                                             entry_permit = true;
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
                                 }
                             }
                    );
        }
    }

    private abstract class IntegralListCallback extends Callback<IntegralList> {

        @Override
        public IntegralList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("部门排行榜 JSON", result);
            IntegralList integralList = new Gson().fromJson(result, IntegralList.class);
            return integralList;
        }
    }
}

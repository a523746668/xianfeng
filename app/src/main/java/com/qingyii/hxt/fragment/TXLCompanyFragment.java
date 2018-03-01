package com.qingyii.hxt.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxt.R;
import com.qingyii.hxt.adapter.PinnedHeaderExpandableAdapter;
import com.qingyii.hxt.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.AddressList;
import com.qingyii.hxt.pojo.AddressUnitList;
import com.qingyii.hxt.view.PinnedHeaderExpandableListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 单位通讯录
 */
public class TXLCompanyFragment extends Fragment {
    private View txlCompanyFragment;
    private Intent intent;

    private PinnedHeaderExpandableListView mPinnedHeaderExpandableListView;
    private PinnedHeaderExpandableAdapter pinnedHeaderExpandableAdapter;
    private AddressUnitList groupData;
    private HomeInfo.AccountBean moduletitle;
    private int listMark = 0;
    private List<List<AddressList.DataBean>> aDataBeanListList = new ArrayList<>();

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    if (listMark < groupData.getData().size())
                        addressList();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (txlCompanyFragment == null) {
            txlCompanyFragment = inflater.inflate(R.layout.fragment_txlcompany, container, false);
            intent = this.getActivity().getIntent();
            groupData = intent.getParcelableExtra("groupData");
            moduletitle = intent.getParcelableExtra("moduletitle");

            //创建组的个数
            for (int i = 0; i < groupData.getData().size(); i++)
                aDataBeanListList.add(new ArrayList<AddressList.DataBean>());

            mPinnedHeaderExpandableListView = (PinnedHeaderExpandableListView) txlCompanyFragment.findViewById(R.id.mPinnedHeaderExpandableListView);
            //设置悬浮头部VIEW
            //mPinnedHeaderExpandableListView.setHeaderView(NotifyView.inflate(this.getActivity(), R.layout.my_tong_xun_lu_group_head, null));
            mPinnedHeaderExpandableListView.setHeaderView(this.getActivity().getLayoutInflater().inflate(R.layout.my_tong_xun_lu_group_head, mPinnedHeaderExpandableListView, false));
            pinnedHeaderExpandableAdapter = new PinnedHeaderExpandableAdapter(aDataBeanListList, groupData, moduletitle, this.getActivity(), mPinnedHeaderExpandableListView);
            mPinnedHeaderExpandableListView.setAdapter(pinnedHeaderExpandableAdapter);

            handler.sendEmptyMessage(3);
        }
        // Inflate the layout for this fragment
        return txlCompanyFragment;
    }

    /**
     * 通讯录列表
     */
    public void addressList() {

        Log.e("AddressList—ID", groupData.getData().get(listMark).getId() + "");

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getAddressListUrl() + "/department/" + groupData.getData().get(listMark).getId())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new AddressListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("AddressList_onError", e.toString());
//                                 Toast.makeText(TXLCompanyFragment.this.getActivity(), "网络异常—通讯录列表", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(AddressList response, int id) {
                                 Log.e("AddressListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         aDataBeanListList.get(listMark).clear();
                                         aDataBeanListList.get(listMark).addAll(response.getData());
                                         listMark++;
                                         pinnedHeaderExpandableAdapter.notifyDataSetChanged();
                                         handler.sendEmptyMessage(3);
                                         break;
                                     default:
                                         Toast.makeText(TXLCompanyFragment.this.getActivity(), response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class AddressListCallback extends Callback<AddressList> {

        @Override
        public AddressList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("单位TXL列表 JSON", result);
            AddressList userContext = new Gson().fromJson(result, AddressList.class);
            return userContext;
        }
    }
}

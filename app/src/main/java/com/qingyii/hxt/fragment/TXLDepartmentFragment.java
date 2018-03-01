package com.qingyii.hxt.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxt.R;
import com.qingyii.hxt.adapter.TXLAdapter;
import com.qingyii.hxt.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.AddressList;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 部门通讯录
 */
public class TXLDepartmentFragment extends Fragment {
    private Intent intent;
    private HomeInfo.AccountBean moduletitle;
    private View txlDepartmentFragment;

    private ListView mListView;
    private TXLAdapter txlAdapter;
    private List<AddressList.DataBean> aDataBeanList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (txlDepartmentFragment == null) {
            txlDepartmentFragment = inflater.inflate(R.layout.fragment_txldepartment, container, false);

            intent = this.getActivity().getIntent();
            moduletitle = intent.getParcelableExtra("moduletitle");

            mListView = (ListView) txlDepartmentFragment.findViewById(R.id.mListView);
            txlAdapter = new TXLAdapter(this.getActivity(), aDataBeanList,moduletitle);
            mListView.setAdapter(txlAdapter);
        }
        // Inflate the layout for this fragment
        return txlDepartmentFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        addressList();
    }

    /**
     * 通讯录列表
     */
    public void addressList() {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getAddressListUrl() + "/department")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new AddressListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("AddressList_onError", e.toString());
//                                 Toast.makeText(TXLDepartmentFragment.this.getActivity(), "网络异常—请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(AddressList response, int id) {
                                 Log.e("AddressListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         aDataBeanList.clear();
                                         aDataBeanList.addAll(response.getData());
                                         txlAdapter.notifyDataSetChanged();
                                         break;
                                     default:
                                         Toast.makeText(TXLDepartmentFragment.this.getActivity(), response.getError_msg(), Toast.LENGTH_LONG).show();
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
            Log.e("部门TXL列表 JSON", result);
            AddressList userContext = new Gson().fromJson(result, AddressList.class);
            return userContext;
        }
    }
}

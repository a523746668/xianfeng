package com.qingyii.hxtz.adapter;

import android.content.Context;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxtz.home.mvp.model.entity.FakeData;

import java.util.List;

/**
 * Created by zhf on 2017/11/25.
 */

public class LattestAdapter extends BaseRecyclerAdapter<FakeData.DataBeanX.DataBean> {
    private Context context;

    public LattestAdapter(List<FakeData.DataBeanX.DataBean> data,Context context) {
        super(data);
        this.context=context;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.lattestrecyc;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, FakeData.DataBeanX.DataBean item) {

    }
}

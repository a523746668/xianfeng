package com.qingyii.hxtz.zhf.adapter;


import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;

import java.util.List;

//被嵌套的Recyclerview对应
public class Taskdetailadaptertwo extends BaseRecyclerAdapter<String> {
    public Taskdetailadaptertwo(List<String> data) {
        super(data);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.taskrecyctwo;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, String item) {
           holder.getTextView(R.id.recycy2tv).setText(item);
    }
}

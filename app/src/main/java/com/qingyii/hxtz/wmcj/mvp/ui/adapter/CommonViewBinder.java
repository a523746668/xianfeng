package com.qingyii.hxtz.wmcj.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Common;

import me.drakeet.multitype.ItemViewBinder;

public class CommonViewBinder extends ItemViewBinder<Common, CommonViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.category_recyclerview_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Common common) {
            holder.textView.setText(common.getTitle());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.category_item_tv);
        }
    }
}

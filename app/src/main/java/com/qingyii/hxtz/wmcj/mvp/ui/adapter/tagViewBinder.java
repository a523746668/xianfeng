package com.qingyii.hxtz.wmcj.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.ReportBean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Common;
import com.qingyii.hxtz.wmcj.mvp.model.bean.ReportMenu;

import java.util.ArrayList;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

public class tagViewBinder extends ItemViewBinder<Common, tagViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.category_recyclerview_string, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Common  tag) {
                holder.textView.setText(tag.getBiaoti());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
           textView= (TextView) itemView.findViewById(R.id.category_tv1);
        }
    }
}

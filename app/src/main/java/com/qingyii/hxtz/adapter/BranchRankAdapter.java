package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.BranchRank;

import java.util.List;

/**
 * Created by XRJ on 16/11/7.
 */

public class BranchRankAdapter extends RecyclerView.Adapter<BranchRankAdapter.ViewHolder> {
    private BranchRankAdapter.ViewHolder viewHolder;
    private Context context;
    private List<BranchRank.DataBean> bDataBeanList;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public BranchRankAdapter(Context context, List<BranchRank.DataBean> bDataBeanList) {
        this.context = context;
        this.bDataBeanList = bDataBeanList;
    }

    public void addItems(List<String> uList) {
        uList.addAll(uList);
    }

    @Override
    public BranchRankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_branch_rank_item, parent, false);
        viewHolder = new BranchRankAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });
        }
        BranchRank.DataBean bDataBean = bDataBeanList.get(position);

        holder.unit_level_item_ranking.setText(position + 1 + "");
        holder.unit_level_item_name.setText(bDataBean.getName() + "");
        holder.unit_level_item_section.setText(bDataBean.getPeople_cnt() + "");
        holder.unit_level_item_integral.setText(bDataBean.getFour_stars() + "");
    }

    @Override
    public int getItemCount() {
        return bDataBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView unit_level_item_ranking;
        private TextView unit_level_item_name;
        private TextView unit_level_item_section;
        private TextView unit_level_item_integral;

        public ViewHolder(View itemView) {
            super(itemView);
            unit_level_item_ranking = (TextView) itemView.findViewById(R.id.unit_level_item_ranking);
            unit_level_item_name = (TextView) itemView.findViewById(R.id.unit_level_item_name);
            unit_level_item_section = (TextView) itemView.findViewById(R.id.unit_level_item_section);
            unit_level_item_integral = (TextView) itemView.findViewById(R.id.unit_level_item_integral);
        }
    }
}

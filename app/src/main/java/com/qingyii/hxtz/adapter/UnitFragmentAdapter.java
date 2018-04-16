package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.ExamRankUnit;
import com.qingyii.hxtz.util.EmptyUtil;
import com.qingyii.hxtz.util.TextUtil;

import java.text.DecimalFormat;
import java.util.List;

public class UnitFragmentAdapter extends BaseAdapter {

    private Context context;
    private List<ExamRankUnit.DataBean> list;
    private DecimalFormat mFormat;
    private String type = "";
    private String groupid = "";

    public UnitFragmentAdapter(Context context, List<ExamRankUnit.DataBean> list, String type, String groupid) {
        this.context = context;
        this.list = list;
        mFormat = TextUtil.getDefaultDecimalFormat();
        this.type = type;
        this.groupid = groupid;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub


        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.unitfragment_item, null);
            holder.fragment_paiming = (TextView) convertView.findViewById(R.id.fragment_paiming);
            holder.fragment_xingming = (TextView) convertView.findViewById(R.id.fragment_xingming);
            holder.fragment_score = (TextView) convertView.findViewById(R.id.fragment_score);
            holder.fragment_danwei = (TextView) convertView.findViewById(R.id.fragment_danwei);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ExamRankUnit.DataBean data = list.get(position);

        if (EmptyUtil.IsNotEmpty(data.getName())) {
            holder.fragment_xingming.setText(data.getName());
        }

        if (EmptyUtil.IsNotEmpty(this.groupid)) {
            int groupid1 = Integer.valueOf(this.groupid);
            if (groupid1 <= 0) {
                if (EmptyUtil.IsNotEmpty(data.getName())) { //list.get(position).getDepname()
                    holder.fragment_xingming.setText(data.getName());
                }
            }
        }

        if (EmptyUtil.IsNotEmpty(String.valueOf(data.getPeople()))) {
            holder.fragment_danwei.setText(String.valueOf(data.getPeople()) + "人");
        }

        holder.fragment_score.setText(data.getResult());
//        if (EmptyUtil.IsNotEmpty(String.valueOf(list.get(position).getStages()))) {
//            float score = Float.valueOf(String.valueOf(list.get(position).getStages()));
//
//            if ("single".equals(this.type)) {  // 2
//                holder.fragment_score.setText(mFormat.format(score) + "分");
//            } else if ("repeat".equals(this.type) || "accrued".equals(this.type)) {  //3 // 4
//                holder.fragment_score.setText(mFormat.format(score) + "关");
//            }
//        }
        holder.fragment_paiming.setText((position + 1) + "");

        return convertView;
    }


    static class ViewHolder {
        TextView fragment_paiming, fragment_xingming, fragment_score, fragment_danwei;
    }
}

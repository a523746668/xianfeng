package com.qingyii.hxt.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.pojo.WaitAffirmList;
import com.qingyii.hxt.pojo.WaitAuditList;

import java.util.List;

/**
 * Created by 63264 on 16/9/22.
 */

public class AuditContextAdapter extends BaseAdapter {
    private Activity activity;
    private List<WaitAuditList.DataBean.ChecklogsBean> wChecklogsBeanList;

    public AuditContextAdapter(Activity activity, List<WaitAuditList.DataBean.ChecklogsBean> wChecklogsBeanList) {
        this.activity = activity;
        this.wChecklogsBeanList = wChecklogsBeanList;
    }

    @Override
    public int getCount() {
        return wChecklogsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return wChecklogsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = convertView.inflate(activity, R.layout.report_context_item, null);
            holder = new ViewHolder();
            holder.report_context_item_grade = (TextView) convertView.findViewById(R.id.report_context_item_grade);
            holder.report_context_item_tag = (TextView) convertView.findViewById(R.id.report_context_item_tag);
            holder.report_context_item_context = (TextView) convertView.findViewById(R.id.report_context_item_context);
            holder.report_context_item_appeals_rl = (RelativeLayout) convertView.findViewById(R.id.report_context_item_appeals_rl);
            holder.report_context_item_appeals = (TextView) convertView.findViewById(R.id.report_context_item_appeals);
            holder.report_context_item_superior = (TextView) convertView.findViewById(R.id.report_context_item_superior);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            WaitAuditList.DataBean.ChecklogsBean wDataBean = wChecklogsBeanList.get(position);

            //积分颜色判断
            double score = wDataBean.getScore();
            if (score < 0) {
                holder.report_context_item_grade.setText("" + score);
                holder.report_context_item_grade.setTextColor(Color.GREEN);
            } else if (score > 0) {
                holder.report_context_item_grade.setText("+" + score);
                holder.report_context_item_grade.setTextColor(Color.RED);
            } else {
                holder.report_context_item_grade.setText("" + score);
                holder.report_context_item_grade.setTextColor(Color.BLACK);
            }

            holder.report_context_item_tag.setText(wDataBean.getTag() + "");
            holder.report_context_item_context.setText("依据：" + wDataBean.getReason());

            //判断是否有申诉内容
            if (wDataBean.getAppeals() != null) {
                holder.report_context_item_appeals_rl.setVisibility(View.VISIBLE);
                holder.report_context_item_appeals.setText(wDataBean.getAppeals().getReason() + "");
                //判断是否已报告上级
                if (wDataBean.getAppeals().getResult().equals("已上报"))
                    holder.report_context_item_superior.setVisibility(View.VISIBLE);
                else
                    holder.report_context_item_superior.setVisibility(View.GONE);
            } else {
                holder.report_context_item_appeals_rl.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Log.e("审核详情适配器 加载数据", e.toString());
        }
        return convertView;
    }

    class ViewHolder {
        TextView report_context_item_grade;
        TextView report_context_item_tag;
        TextView report_context_item_context;
        RelativeLayout report_context_item_appeals_rl;
        TextView report_context_item_appeals;
        TextView report_context_item_superior;
    }
}
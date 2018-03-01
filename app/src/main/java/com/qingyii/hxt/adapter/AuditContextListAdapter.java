package com.qingyii.hxt.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.AuditContextListActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.ReportListActivity;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.httpway.GLUpload;
import com.qingyii.hxt.pojo.WaitAffirmList;
import com.qingyii.hxt.pojo.WaitAuditList;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;

import java.util.List;

/**
 * Created by 63264 on 16/9/22.
 */

public class AuditContextListAdapter extends BaseAdapter {
    private AuditContextListActivity auditContextListActivity;
    private List<WaitAuditList.DataBean> wDataBeanList;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public AuditContextListAdapter(AuditContextListActivity auditContextListActivity, List<WaitAuditList.DataBean> wDataBeanList) {
        this.auditContextListActivity = auditContextListActivity;
        this.wDataBeanList = wDataBeanList;
    }

    @Override
    public int getCount() {
        return wDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return wDataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = convertView.inflate(auditContextListActivity, R.layout.report_list_item, null);
            holder = new ViewHolder();
            holder.report_list_item_image = (ImageView) convertView.findViewById(R.id.report_list_item_image);
            holder.report_list_item_guan_add = (ImageView) convertView.findViewById(R.id.report_list_item_guan_add);
            holder.report_list_item_class = (TextView) convertView.findViewById(R.id.report_list_item_class);
            holder.report_list_item_time = (TextView) convertView.findViewById(R.id.report_list_item_time);
            holder.report_list_item_context = (TextView) convertView.findViewById(R.id.report_list_item_context);
            holder.report_list_item_integral = (TextView) convertView.findViewById(R.id.report_list_item_integral);
            holder.report_list_item_state = (TextView) convertView.findViewById(R.id.report_list_item_state);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            final WaitAuditList.DataBean wDataBean = wDataBeanList.get(position);

            if (wDataBean.getPicture().size() > 0)
                ImageLoader.getInstance().displayImage(wDataBeanList.get(position).getPicture().get(0).getUri(),
                        holder.report_list_item_image, MyApplication.options, animateFirstListener);

            if (wDataBean.getTags().equals("管理员添加"))
                holder.report_list_item_guan_add.setVisibility(View.VISIBLE);
            else
                holder.report_list_item_guan_add.setVisibility(View.GONE);

            holder.report_list_item_class.setText("类型：" + wDataBean.getDoctypename() + "    ");
            holder.report_list_item_time.setText("时间：" + wDataBean.getCreated_at());
            holder.report_list_item_context.setText("" + wDataBean.getContent());

            List<WaitAuditList.DataBean.ChecklogsBean> wChecklogsBea = wDataBean.getChecklogs();

            //积分颜色判断
            double score;
            //审核记录可能为null
            if (wChecklogsBea.size() > 0)
                score = wChecklogsBea.get(wChecklogsBea.size() - 1).getScore();
            else
                score = 0;

            if (score < 0) {
                holder.report_list_item_integral.setText("积分 " + score);
                holder.report_list_item_integral.setTextColor(Color.GREEN);
            } else if (score > 0) {
                holder.report_list_item_integral.setText("积分 +" + score);
                holder.report_list_item_integral.setTextColor(Color.RED);
            } else {
                holder.report_list_item_integral.setText("积分  " + score);
                holder.report_list_item_integral.setTextColor(Color.BLACK);
            }

            //只有在一级审核员进入时显示
            if (MyApplication.userUtil.getCheck_level() == 1)
                holder.report_list_item_state.setVisibility(View.VISIBLE);
//            holder.report_list_item_state.setText(wDataBean.getStatus());
            Log.e("ComtextListStatus", wDataBean.getStatus() + "");
            switch (wDataBean.getStatus()) {
                case 0:
                    holder.report_list_item_state.setText("待审核");
                    break;
                case 1:
                    holder.report_list_item_state.setText("待复核");
                    break;
                case 2:
                    holder.report_list_item_state.setText("待确认");
                    break;
                case 3:
                    holder.report_list_item_state.setText("已确认");
                    break;
            }

        } catch (Exception e) {
            Log.e("ReportListAdapter", e.toString());
        }

        return convertView;
    }

    class ViewHolder {
        ImageView report_list_item_image;
        ImageView report_list_item_guan_add;
        TextView report_list_item_class;
        TextView report_list_item_time;
        TextView report_list_item_context;
        TextView report_list_item_integral;
        TextView report_list_item_state;
    }
}
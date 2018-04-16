package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.ExamRank;
import com.qingyii.hxtz.util.EmptyUtil;
import com.qingyii.hxtz.util.TextUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 考场积分榜适配器
 *
 * @author shelia
 */
public class ExamRankListAdapter extends BaseAdapter {
    private List<ExamRank.DataBean> list;
    private Context context;
    //private String type;
    //private String groupid;
    private DecimalFormat mFormat;

    public ExamRankListAdapter(Context context, List<ExamRank.DataBean> list) {
        this.context = context;
        this.list = list;
        //this.type = type;
        //this.groupid = groupid;
        mFormat = TextUtil.getDefaultDecimalFormat();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.personalfragment_item, null);
            holder = new ViewHolder();
            holder.fragment_xingming = (TextView) convertView.findViewById(R.id.fragment_xingming);
            holder.fragment_paiming = (TextView) convertView.findViewById(R.id.fragment_paiming);
            holder.fragment_score = (TextView) convertView.findViewById(R.id.fragment_score);
            holder.fragment_danwei = (TextView) convertView.findViewById(R.id.fragment_danwei);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ExamRank.DataBean data = list.get(position);

//        ExamRank.DataBean.UserBean user = data.getUser();
//        ExamRank.DataBean.GroupBean group = data.getGroup();


        if (EmptyUtil.IsNotEmpty(data.getUsername())) {
            holder.fragment_xingming.setText(data.getUsername());
            //holder.fragment_xingming.setText(user.getTruename());
        }
        if (EmptyUtil.IsNotEmpty(data.getResult())) {
            holder.fragment_score.setText(data.getResult());
        }
        if (EmptyUtil.IsNotEmpty(data.getName())) {
            holder.fragment_danwei.setText(data.getName());
            //holder.fragment_danwei.setText(group.getName());
        }

//        if (EmptyUtil.IsNotEmpty(list.get(position).getScore())) {
//            String str = "";
//            if ("single".equals(this.type) || "repeat".equals(this.type)) {
//                float score = Float.valueOf(list.get(position).getScore());
//                str = mFormat.format(score) + "分";
//            } else if ("accrued".equals(this.type)) {  //累计闯关
//                str = "第" + list.get(position).getScore() + "关";
//            }
//            holder.fragment_score.setText(str);
//        }

//        if (EmptyUtil.IsNotEmpty(this.groupid)) {
//            int groupid1 = Integer.valueOf(this.groupid);
//            if (groupid1 <= 0) {
//                if (EmptyUtil.IsNotEmpty(group.getName())) { //list.get(position).getDepname() 之前
//                    holder.fragment_danwei.setText(group.getName()); // //list.get(position).getDepname()
//                }
//            }
//        }
        holder.fragment_paiming.setText((position + 1) + "");
//		holder.fragment_paiming.setVisibility(NotifyView.GONE);

        return convertView;
    }

    static class ViewHolder {
        TextView fragment_paiming, fragment_xingming, fragment_score, fragment_danwei;
    }

}

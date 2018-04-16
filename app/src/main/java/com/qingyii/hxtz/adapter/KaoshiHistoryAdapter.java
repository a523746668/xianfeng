package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.ExamList;
import com.qingyii.hxtz.pojo.ExamResult;
import com.qingyii.hxtz.util.EmptyUtil;
import com.qingyii.hxtz.util.TimeUtil;

import java.util.List;

/**
 * 历史分数适配器
 *
 * @author shelia
 */
public class KaoshiHistoryAdapter extends BaseAdapter {
    private Context context;
    private List<ExamResult.DataBean.LogsBean> list;
    private ExamList.DataBean examination;

    public KaoshiHistoryAdapter(Context context, List<ExamResult.DataBean.LogsBean> list, ExamList.DataBean examination) {
        this.context = context;
        this.list = list;
        this.examination = examination;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ExamResult.DataBean.LogsBean erDataBean = list.get(arg0);
        ViewHolder holder = null;
        if (arg1 == null) {
            arg1 = LayoutInflater.from(context).inflate(R.layout.kaoshi_history_list_item, null);
            holder = new ViewHolder();
            holder.kaoshi_history_item_paiming = (TextView) arg1.findViewById(R.id.kaoshi_history_item_paiming);
            holder.kaoshi_history_item_xingming = (TextView) arg1.findViewById(R.id.kaoshi_history_item_xingming);
            holder.kaoshi_history_item_danwei = (TextView) arg1.findViewById(R.id.kaoshi_history_item_danwei);
            holder.kaoshi_history_item_score = (TextView) arg1.findViewById(R.id.kaoshi_history_item_score);

            arg1.setTag(holder);
        } else
            holder = (ViewHolder) arg1.getTag();

        if (EmptyUtil.IsNotEmpty(erDataBean.getCreated_at())) {
            holder.kaoshi_history_item_xingming.setText(erDataBean.getCreated_at());//getCreatetimeStr
        }
        if (EmptyUtil.IsNotEmpty(erDataBean.getResult())) {
            holder.kaoshi_history_item_danwei.setText(erDataBean.getResult());
//            if ("repeat".equals(examination.getExamtype()) || "accrued".equals(examination.getExamtype())) { //3//4
//                holder.kaoshi_history_item_danwei.setText("第" + erDataBean.getStages() + "关");
//            } else if ("single".equals(examination.getExamtype())) {   //2
//                holder.kaoshi_history_item_danwei.setText(TextUtil.getDefaultDecimalFormat().format(Float.parseFloat(erDataBean.getScore() + "")) + "分");
//            }
        }
        if (EmptyUtil.IsNotEmpty(String.valueOf(erDataBean.getDuration()))) {
            holder.kaoshi_history_item_score.setText(TimeUtil.secToTime02(erDataBean.getDuration()));
        }

        holder.kaoshi_history_item_paiming.setText(String.valueOf(erDataBean.getTimes()));    //s.getScoreid()

        return arg1;
    }

    static class ViewHolder {
        TextView kaoshi_history_item_paiming, kaoshi_history_item_xingming, kaoshi_history_item_danwei, kaoshi_history_item_score;
    }
}

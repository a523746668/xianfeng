package com.qingyii.hxt.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.pojo.ExamList;
import com.qingyii.hxt.util.DateUtils;
import com.qingyii.hxt.util.StateUtil;

import java.util.List;

public class kaoshiAdapter extends BaseAdapter {

    private Context mContext;
    private List<ExamList.DataBean> eDataBeanlist;


    public kaoshiAdapter(Context mContext, List<ExamList.DataBean> eDataBeanlist) {
        super();
        this.mContext = mContext;
        this.eDataBeanlist = eDataBeanlist;

    }

    @Override
    public int getCount() {
        return eDataBeanlist.size();
    }

    @Override
    public Object getItem(int arg0) {
        return eDataBeanlist.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View contentview, ViewGroup arg2) {
        final viewHolder holder;
        if (contentview == null) {
            contentview = LayoutInflater.from(mContext).inflate(R.layout.kaoshi_list_item, null);
            holder = new viewHolder();
            holder.name_kaoshi_item = (TextView) contentview.findViewById(R.id.name_kaoshi_item);
            holder.time_kaoshi_item = (TextView) contentview.findViewById(R.id.time_kaoshi_item);
            holder.type_kaoshi_item = (TextView) contentview.findViewById(R.id.type_kaoshi_item);
            holder.type_iv_kaoshi_item = (ImageView) contentview.findViewById(R.id.type_iv_kaoshi_item);
            holder.unit_kaoshi_item = (TextView) contentview.findViewById(R.id.unit_kaoshi_item);
            holder.join_kaoshi_item = (ImageView) contentview.findViewById(R.id.join_kaoshi_item);
            contentview.setTag(holder);
        } else {
            holder = (viewHolder) contentview.getTag();
        }

        try {
            ExamList.DataBean eDataBean = eDataBeanlist.get(position);

            holder.name_kaoshi_item.setText(eDataBean.getExamname());

            long begintime = (long) eDataBean.getBegintime();
            long endtime = (long) eDataBean.getEndtime();


            holder.time_kaoshi_item.setText("考试时间:"
                    + DateUtils.getDateToLongString(begintime)
                    + "到" + DateUtils.getDateToLongString(endtime));

            //报空异常

            if (eDataBean.getCompany() == null) {

                holder.unit_kaoshi_item.setText("主办单位:" + "系统");
            } else {


                holder.unit_kaoshi_item.setText("主办单位:" + eDataBean.getCompany().getName());
            }

            String examtype = eDataBean.getExamtype();


            //System.out.println("考试时间 "+begintime+" --"+endtime+" "+ eDataBean.getExamtype()+" "+ eDataBean.getCompany().getName());


            if (examtype.equals("single")) {

                holder.type_kaoshi_item.setText("考试类型:单次考试");
                holder.type_iv_kaoshi_item.setBackgroundResource(R.mipmap.word_recruit);

            } else if (examtype.equals("repeat")) {

                holder.type_kaoshi_item.setText("考试类型:重复闯关");
                holder.type_iv_kaoshi_item.setBackgroundResource(R.mipmap.repeated_recruit);

            } else if (examtype.equals("accrued")) {

                holder.type_kaoshi_item.setText("考试类型:累计闯关");
                holder.type_iv_kaoshi_item.setBackgroundResource(R.mipmap.cumulative_recruit);

            } else {
                holder.type_kaoshi_item.setText("考试类型:普通");
            }

//            switch (eDataBean.getExamtype()) {
//                case "single": {
//                    System.out.println("判断出阿里");
//                    holder.type_kaoshi_item.setText("考试类型:单次考试");
//                    holder.type_iv_kaoshi_item.setBackgroundResource(R.mipmap.word_recruit);
//                } break;
//                case "repeat": {
//                    holder.type_kaoshi_item.setText("考试类型:重复闯关");
//                    holder.type_iv_kaoshi_item.setBackgroundResource(R.mipmap.repeated_recruit);
//                }   break;
//                case "accrued": {
//                    holder.type_kaoshi_item.setText("考试类型:累计闯关");
//                    holder.type_iv_kaoshi_item.setBackgroundResource(R.mipmap.cumulative_recruit);
//                }   break;
//                default: {
//                    holder.type_kaoshi_item.setText("考试类型:");
//                }   break;
//            }

            long date = DateUtils.getDateLong();

            if (eDataBean.getBegintime() * 1000 > date)
                holder.join_kaoshi_item.setBackgroundResource(R.mipmap.test_about);
            else if (eDataBean.getEndtime() * 1000 < date)
                holder.join_kaoshi_item.setBackgroundResource(R.mipmap.search_result);
            else
                holder.join_kaoshi_item.setBackgroundResource(R.mipmap.enter_test);

//        if (EmptyUtil.IsNotEmpty(eDataBeanlist.get(position).getJoinflag())) {
//            switch (Integer.parseInt(eDataBeanlist.get(position).getJoinflag())) {
//                case 1:
//                    holder.join_kaoshi_item.setBackground(mContext.getResources().getDrawable(R.drawable.corners_red));
//                    break;
//                case 2:
//                    holder.join_kaoshi_item.setBackground(mContext.getResources().getDrawable(R.drawable.corners_gray));
//                    break;
//                default:
//                    break;
//            }
//        }
        } catch (Exception e) {
            Log.e("kaoshiAdapter", e.toString());
        }

        return contentview;
    }

    static class viewHolder {
        TextView name_kaoshi_item, time_kaoshi_item, type_kaoshi_item, unit_kaoshi_item;
        ImageView join_kaoshi_item, type_iv_kaoshi_item;
    }
}

package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.TrainCourseList;
import com.qingyii.hxtz.util.DateUtils;

import java.util.List;

/**
 * Created by XRJ on 16/9/11.
 */
public class TrainScheduleAdapter extends BaseAdapter {
    private Context context;
    private List<TrainCourseList.DataBean> tDataList;

    public TrainScheduleAdapter(Context context, List<TrainCourseList.DataBean> tDataList) {
        this.context = context;
        this.tDataList = tDataList;
    }

    @Override
    public int getCount() {
        return tDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return tDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = View.inflate(context, R.layout.train_course_listview_item, null);
            vh = new ViewHolder();
            vh.course_name = (TextView) view.findViewById(R.id.course_name);
            vh.course_time = (TextView) view.findViewById(R.id.course_time);
            vh.course_teacher = (TextView) view.findViewById(R.id.course_teacher);
            vh.course_state = (ImageView) view.findViewById(R.id.course_state);
            vh.course_signin = (ImageView) view.findViewById(R.id.course_signin);
            view.setTag(vh);
        } else
            vh = (ViewHolder) view.getTag();

        TrainCourseList.DataBean tDataBean = tDataList.get(i);

        vh.course_name.setText(tDataBean.getCoursename());
        vh.course_time.setText("上课时间 : " + tDataBean.getBegintime()+"-"+tDataBean.getEndtime());
        vh.course_teacher.setText("主讲 : " + tDataBean.getTeachers());

        long begintime = DateUtils.getStringToLong2Date(tDataBean.getBegintime());
        long endtime = DateUtils.getStringToLong2Date(tDataBean.getEndtime());
        long nowtime = DateUtils.getDateLong();
//        Log.e("TrainList___begintime", begintime + "");
//        Log.e("TrainList___endtime", endtime + "");
//        Log.e("TrainList___nowTime", nowtime + "");

        if (nowtime < begintime)
            vh.course_state.setBackgroundResource(R.mipmap.train_state_ready);
        else if (nowtime > endtime)
            vh.course_state.setBackgroundResource(R.mipmap.train_state_end);
        else
            vh.course_state.setBackgroundResource(R.mipmap.train_state_ing);


        return view;
    }

    class ViewHolder {
        TextView course_name;
        TextView course_time;
        TextView course_teacher;
        ImageView course_state;
        ImageView course_signin;
    }
}

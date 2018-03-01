package com.qingyii.hxt.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.pojo.InformList;
import com.qingyii.hxt.pojo.TrainList;
import com.qingyii.hxt.util.DateUtils;

import java.util.List;

import static junit.runner.Version.id;

/**
 * Created by 63264 on 16/9/9.
 */
public class TrainAdapter extends BaseAdapter {
    private Context context;
    private List<TrainList.DataBean> lDataList;

    public TrainAdapter(Context context, List<TrainList.DataBean> lDataList) {
        this.context = context;
        this.lDataList = lDataList;
    }

    @Override
    public int getCount() {
        return lDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return lDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = View.inflate(context, R.layout.train_listview_item, null);
            vh = new ViewHolder();
            vh.train_time = (TextView) view.findViewById(R.id.train_time);
            vh.train_tltle = (TextView) view.findViewById(R.id.train_tltle);
            vh.train_state = (ImageView) view.findViewById(R.id.train_state);
            vh.train_sponsor = (TextView) view.findViewById(R.id.train_sponsor);
            vh.train_context = (TextView) view.findViewById(R.id.train_context);
            vh.train_time_valid = (TextView) view.findViewById(R.id.train_time_valid);
            vh.train_item_mark = (ImageView) view.findViewById(R.id.train_item_mark);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        TrainList.DataBean tDataBean = lDataList.get(i);
        long begintime = tDataBean.getBegintime();
        long endtime = tDataBean.getEndtime();
        long nowtime = (long) (DateUtils.getDateLong() / 1000);
//        Log.e("TrainList___begintime", begintime + "");
//        Log.e("TrainList___endtime", endtime + "");
//        Log.e("TrainList___nowTime", nowtime + "");

        vh.train_tltle.setText(tDataBean.getTrainingtitle());
        vh.train_sponsor.setText("主办 : " + tDataBean.getOrganizer());
        vh.train_context.setText(tDataBean.getContent());
        vh.train_time_valid.setText(DateUtils.getDateToLongString(begintime) +
                "——" + DateUtils.getDateToLongString(endtime));

        //未读通知提示
        SQLiteDatabase dbRead = MyApplication.helper.getReadableDatabase();
        int markTrain = dbRead.rawQuery("select * from Inform_info where mark = ? and training_id = ?", new String[]{"0", tDataBean.getId() + ""}).getCount();
        if (markTrain > 0)
            vh.train_item_mark.setVisibility(View.VISIBLE);
        else
            vh.train_item_mark.setVisibility(View.GONE);
        dbRead.close();

        if (nowtime < begintime) {
            vh.train_state.setBackgroundResource(R.mipmap.train_state_ready);
        } else if (nowtime > endtime) {
            vh.train_state.setBackgroundResource(R.mipmap.train_state_end);
        } else {
            vh.train_state.setBackgroundResource(R.mipmap.train_state_ing);
        }

        return view;
    }

    class ViewHolder {
        TextView train_time;
        TextView train_tltle;
        ImageView train_state;
        TextView train_sponsor;
        TextView train_context;
        TextView train_time_valid;
        ImageView train_item_mark;
    }
}

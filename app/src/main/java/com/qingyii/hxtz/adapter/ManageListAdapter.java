package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.pojo.ManageList;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;

import java.util.List;

/**
 * Created by 63264 on 16/9/22.
 */

public class ManageListAdapter extends BaseAdapter {

    private List<ManageList.DataBean> list;
    private Context context;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public ManageListAdapter(Context context, List<ManageList.DataBean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = convertView.inflate(context, R.layout.manage_list_item, null);
            holder = new ViewHolder();
            holder.manage_list_user_image = (ImageView) convertView.findViewById(R.id.manage_list_user_image);
            holder.manage_list_user_tv = (TextView) convertView.findViewById(R.id.manage_list_user_tv);
            holder.manage_list_user_log = (TextView) convertView.findViewById(R.id.manage_list_user_log);
            holder.manage_list_user_month_credits = (TextView) convertView.findViewById(R.id.manage_list_user_month_credits);
            holder.manage_list_user_all_credits = (TextView) convertView.findViewById(R.id.manage_list_user_all_credits);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ManageList.DataBean mInfo = list.get(position);
//        if (mInfo.getCheckdata() == null) {
//        ImageLoader.getInstance().displayImage(info.getPicaddress(), iv_shujia_Image, MyApplication.options, animateFirstListener);
//        ImageLoader.getInstance().displayImage(null, holder.manage_list_user_image, MyApplication.options);
        ImageLoader.getInstance().displayImage(null, holder.manage_list_user_image, MyApplication.options, animateFirstListener);

        holder.manage_list_user_tv.setText(mInfo.getTruename());
        holder.manage_list_user_log.setText("共" + mInfo.getCheckdata().getCount() + "篇");
        holder.manage_list_user_month_credits.setText("积分 " + mInfo.getCheckdata().getScore());
        holder.manage_list_user_all_credits.setText("月积分 " + mInfo.getCheckdata().getTotal());
//        }
        return convertView;
    }

    class ViewHolder {
        ImageView manage_list_user_image;
        TextView manage_list_user_tv;
        TextView manage_list_user_log;
        TextView manage_list_user_month_credits;
        TextView manage_list_user_all_credits;
    }
}
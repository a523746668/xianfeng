package com.qingyii.hxt.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.MyTongXunLuDetailsActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.pojo.AddressList;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;
import com.qingyii.hxt.view.RoundedImageView;

import java.util.List;


public class TXLAdapter extends BaseAdapter {
    private Activity activity;
    private List<AddressList.DataBean> aDataBeanList;
    private HomeInfo.AccountBean moduletitle;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public TXLAdapter(Activity activity, List<AddressList.DataBean> aDataBeanList, HomeInfo.AccountBean moduletitle) {
        this.activity = activity;
        this.aDataBeanList = aDataBeanList;
        this.moduletitle = moduletitle;
    }

    @Override
    public int getCount() {
        return aDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return aDataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.my_tong_xun_lu_child, null);
            holder = new ViewHolder();
            holder.my_tong_xun_lu_child = (RelativeLayout) convertView.findViewById(R.id.my_tong_xun_lu_child);
            holder.child_icon = (RoundedImageView) convertView.findViewById(R.id.child_icon);
            holder.child_name = (TextView) convertView.findViewById(R.id.child_name);
            holder.child_department = (TextView) convertView.findViewById(R.id.child_department);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final AddressList.DataBean aDataBean = aDataBeanList.get(position);

        ImageLoader.getInstance().displayImage(aDataBean.getAvatar(), holder.child_icon, MyApplication.options, animateFirstListener);
        holder.child_name.setText(aDataBean.getTruename());
//        holder.child_department.setText(aDataBean.getDepartment().getName() + "");
        holder.child_department.setVisibility(View.GONE);

        holder.my_tong_xun_lu_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MyTongXunLuDetailsActivity.class);
                intent.putExtra("aDataBean", aDataBean);
                intent.putExtra("moduletitle", moduletitle);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }


    class ViewHolder {
        RelativeLayout my_tong_xun_lu_child;
        RoundedImageView child_icon;
        TextView child_name;
        TextView child_department;
    }
}

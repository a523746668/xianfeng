package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.pojo.SubscribedNK;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;
import com.qingyii.hxtz.util.EmptyUtil;

import java.util.List;


public class neiKanAdapter extends BaseAdapter {
    private Context context;
    private List<SubscribedNK.DataBean> list;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    // 列表展现的数据


    public neiKanAdapter(Context context, List<SubscribedNK.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        //
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.neikan_item, null);
            vh = new ViewHolder();
            vh.iv_neikan_Image = (ImageView) convertView.findViewById(R.id.iv_neikan_Image);
            vh.tv_neikan_title = (TextView) convertView.findViewById(R.id.tv_neikan_title);
            vh.tv_neikan_name = (TextView) convertView.findViewById(R.id.tv_neikan_name);
            vh.tv_neikan_content = (TextView) convertView.findViewById(R.id.tv_neikan_content);
            vh.tv_neikan_jurisdiction = (ImageView) convertView.findViewById(R.id.tv_neikan_jurisdiction);
//        tv_neikan_title.setText(list.get(position).getNeikan_title());
//        tv_neikan_content.setText(list.get(position).getNeikan_content());
//        tv_neikan_name.setText("上传者:  " + list.get(position).getNeikan_name());
//        ImageLoader.getInstance().displayImage(n.getNeikan_img(), iv_neikan_Image);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        SubscribedNK.DataBean dataBean = list.get(position);
        vh.tv_neikan_title.setText(dataBean.getMagazinename());
        vh.tv_neikan_content.setText(dataBean.getMagazinedesc());
//        if (EmptyUtil.IsNotEmpty(list.get(position).getSponsor())) {
//        tv_neikan_name.setText("主办: " + list.get(position).getSponsor());
        if (EmptyUtil.IsNotEmpty(dataBean.getOrganizer())) {
            vh.tv_neikan_name.setText("主办: " + dataBean.getOrganizer());
        } else {
            vh.tv_neikan_name.setText("主办: 暂无信息");
        }

        if (dataBean.getPublishstatus().equals("public"))
            vh.tv_neikan_jurisdiction.setBackgroundResource(R.mipmap.neikan_gongkai);
        else if (dataBean.getPublishstatus().equals("share"))
            vh.tv_neikan_jurisdiction.setBackgroundResource(R.mipmap.neikan_gongxiang);
        else if (dataBean.getPublishstatus().equals("private"))
            vh.tv_neikan_jurisdiction.setBackgroundResource(R.mipmap.neikan_zhuanyou);
//		ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir+m.getPicaddress(), iv_neikan_Image, MyApplication.options, animateFirstListener);
        ImageLoader.getInstance().displayImage(dataBean.getMagezinecover(), vh.iv_neikan_Image, MyApplication.options, animateFirstListener);
        return convertView;
    }


    class ViewHolder {
        ImageView iv_neikan_Image;
        TextView tv_neikan_title;
        TextView tv_neikan_name;
        TextView tv_neikan_content;
        ImageView tv_neikan_jurisdiction;
    }
}

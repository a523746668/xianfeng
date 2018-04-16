package com.qingyii.hxtz.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.PeriodsArticleRela;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;

import java.util.ArrayList;

public class KanwuAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PeriodsArticleRela> list;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public KanwuAdapter(Context context, ArrayList<PeriodsArticleRela> list) {
        super();
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//		convertView=LayoutInflater.from(context).inflate(R.layout.kanwu_item, null);
//		TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
//		TextView tv_content = (TextView) convertView.findViewById(R.id.tv_content);
//		tv_title.setText(list.get(position).getTitle());
//		tv_content.setText(list.get(position).getContent());

        PeriodsArticleRela p = list.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.neikanxq1_item, null);

//        ImageView neikanxq1_image_iv = (ImageView) convertView.findViewById(R.id.neikanxq1_image_iv);

//        if (!EmptyUtil.IsNotEmpty(list.get(position).getPicaddress())) {
//            neikanxq1_image_iv.setVisibility(convertView.GONE);
//        }

        TextView tv_neikanxq1_title = (TextView) convertView.findViewById(R.id.tv_neikanxq1_title);
        TextView tv_neikanxq1_content = (TextView) convertView.findViewById(R.id.tv_neikanxq1_content);
        tv_neikanxq1_title.setText(list.get(position).getTitle());
        tv_neikanxq1_content.setText(list.get(position).getArticledesc());
//        if (EmptyUtil.IsNotEmpty(p.getPicaddress())) {
//            neikanxq1_image_iv.setVisibility(NotifyView.VISIBLE);
//            ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + p.getPicaddress(), neikanxq1_image_iv, MyApplication.options, animateFirstListener);
//        }
        return convertView;
    }

}

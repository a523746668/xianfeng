package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.R;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.pojo.AxpectNK;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;

import java.util.List;

public class neiKanXqAdapter extends BaseAdapter {
    private Context context;
    private List<AxpectNK.DataBean> list;
    //private String issuer;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public neiKanXqAdapter(Context context, List<AxpectNK.DataBean> list) {
        super();
        this.context = context;
        this.list = list;
        //this.issuer = issuer;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AxpectNK.DataBean aDataBean = list.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.neikanxqing, null);
        TextView tv_neikan_name = (TextView) convertView.findViewById(R.id.tv_neikan_name);
        TextView tv_neikan_content = (TextView) convertView.findViewById(R.id.tv_neikan_content);
        //TextView tv_neikan_date = (TextView) convertView.findViewById(R.id.tv_neikan_date);
        ImageView iv_neikan_Image = (ImageView) convertView.findViewById(R.id.iv_neikan_Image);

//		tv_neikan_date.setText(list.get(position).getNeikan_title());
//		tv_neikan_name.setText("上传者：   "+list.get(position).getNeikan_name());

//		tv_neikan_content.setText(list.get(position).getNeikan_content());
        tv_neikan_name.setText(aDataBean.getIssuename());
        tv_neikan_content.setText(aDataBean.getIssuedesc());
        //tv_neikan_date.setText(issuer);
//        ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + p.getPicaddress(), iv_neikan_Image, MyApplication.options, animateFirstListener);
        ImageLoader.getInstance().displayImage(aDataBean.getIssuecover(), iv_neikan_Image, MyApplication.options, animateFirstListener);
        return convertView;
    }

}

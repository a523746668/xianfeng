package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxt.R;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;

public class HuodongshuGvAdapter extends BaseAdapter{
	private Context context;
	private String [] imgUrls;
	private ImageView iv;
	private int screenW;
	private AnimateFirstDisplayListener listener = new AnimateFirstDisplayListener();
	public HuodongshuGvAdapter(Context context, String [] imgUrls, int screenW){
		this.context = context;
		this.imgUrls = imgUrls;
		this.screenW = screenW;
	}

	@Override
	public int getCount() {
		return imgUrls.length;
	}

	@Override
	public Object getItem(int position) {
		return imgUrls[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.huodongshu_gv_item, null);
		iv = (ImageView) convertView.findViewById(R.id.iv_huodongshu_gv_item);
		ImageLoader.getInstance().displayImage(imgUrls[position], iv, MyApplication.options, listener);
		return convertView;
	}
	

}

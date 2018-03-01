package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxt.R;
import com.qingyii.hxt.bean.rizhiInfo;

import java.util.ArrayList;

public class xierizhiImageAdapter extends BaseAdapter {
   private Context context;
   private ArrayList<rizhiInfo> list;
   
	public xierizhiImageAdapter(Context context, ArrayList<rizhiInfo> list) {
	super();
	this.context = context;
	this.list = list;
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
		rizhiInfo rinfo=list.get(position);
		convertView=LayoutInflater.from(context).inflate(R.layout.rizhiimage_item, null);
		ImageView iv_rizhiimage_item=(ImageView) convertView.findViewById(R.id.iv_rizhiimage_item);
		ImageLoader.getInstance().displayImage(rinfo.getRizhi_photo(), iv_rizhiimage_item);
		return convertView;
	}

}

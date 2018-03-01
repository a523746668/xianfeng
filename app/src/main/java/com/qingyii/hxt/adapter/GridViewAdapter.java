package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxt.R;
import com.qingyii.hxt.bean.gridview_dongtai;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<gridview_dongtai> list;
	@Override
	
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public GridViewAdapter(Context context, ArrayList<gridview_dongtai> list) {
		super();
		this.context = context;
		this.list = list;
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
		gridview_dongtai g=list.get(position); 
		convertView=LayoutInflater.from(context).inflate(R.layout.gridview_dongtai_item, null);
	      ImageView iv_gridview_imageUrl=(ImageView) convertView.findViewById(R.id.iv_gridview_imageUrl);
	      ImageLoader.getInstance().displayImage(g.getImageUrl(), iv_gridview_imageUrl);
		return convertView;
	}

}

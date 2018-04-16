package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.myTimeInfo;

import java.util.ArrayList;

public class myTimeAdapter extends BaseAdapter {
   private Context context;
   private ArrayList<myTimeInfo> list;
   
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
		convertView=LayoutInflater.from(context).inflate(R.layout.myshiguang_item, null);
		
		return convertView;
	}

}

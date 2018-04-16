package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.myjifenInfo;

import java.util.List;

public class zongjifenAdapter extends BaseAdapter {
   private Context context;
   private List<myjifenInfo> list;
   
	public zongjifenAdapter(Context context, List<myjifenInfo> list) {
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
		convertView=LayoutInflater.from(context).inflate(R.layout.fragment_zongjifen_item, null);
		TextView tv_zongjifen_date=(TextView) convertView.findViewById(R.id.tv_zongjifen_date);
		TextView tv_zongjifen_content=(TextView) convertView.findViewById(R.id.tv_zongjifen_content);
		tv_zongjifen_date.setText(list.get(position).getDate());
		tv_zongjifen_content.setText(list.get(position).getContent());
		
		return convertView;
	}

}

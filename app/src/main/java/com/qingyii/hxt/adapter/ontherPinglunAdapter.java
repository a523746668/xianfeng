package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.pojo.Discuss;
import com.qingyii.hxt.util.TimeUtil;

import java.util.ArrayList;

public class ontherPinglunAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Discuss> list;
    
	public ontherPinglunAdapter(Context context,
			ArrayList<Discuss> list) {
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
		Discuss info=list.get(position);
		convertView=LayoutInflater.from(context).inflate(R.layout.ontherpinglun_item, null);
		TextView tv_onther_name=(TextView) convertView.findViewById(R.id.tv_onther_name);
		TextView tv_onther_time=(TextView) convertView.findViewById(R.id.tv_onther_time);
		TextView tv_onther_content=(TextView) convertView.findViewById(R.id.tv_onther_content);
		
		tv_onther_name.setText(list.get(position).getUsername());
		tv_onther_time.setText(TimeUtil.getRecentTimeDisplayOfUnixTimestamp(Long.parseLong(list.get(position).getCreatetime())));
//		tv_onther_time.setText(list.get(position).getCreatetime());
		tv_onther_content.setText(list.get(position).getContent());
		return convertView;
	}

}

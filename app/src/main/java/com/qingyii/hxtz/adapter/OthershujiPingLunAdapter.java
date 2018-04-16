package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.Discuss;
import com.qingyii.hxtz.util.TimeUtil;

import java.util.ArrayList;

public class OthershujiPingLunAdapter  extends BaseAdapter{
	
    private Context context;
    private ArrayList<Discuss> list;
    
    
	public OthershujiPingLunAdapter(Context context, ArrayList<Discuss> list) {
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
		// TODO Auto-generated method stub
		convertView=LayoutInflater.from(context).inflate(R.layout.othershujipinglun, null);
		TextView tv_othershujipinglun_name=(TextView) convertView.findViewById(R.id.tv_othershujipinglun_name);
		TextView tv_othershujipinglun_time=(TextView) convertView.findViewById(R.id.tv_othershujipinglun_time);
		TextView tv_othershujipinglun_content=(TextView) convertView.findViewById(R.id.tv_othershujipinglun_content);
		tv_othershujipinglun_name.setText(list.get(position).getUsername());
		tv_othershujipinglun_time.setText(TimeUtil.getRecentTimeDisplayOfUnixTimestamp(Long.parseLong(list.get(position).getCreatetime())));
//		tv_onther_time.setText(list.get(position).getCreatetime());
		tv_othershujipinglun_content.setText(list.get(position).getContent());	
		
		
		
		return convertView;
	}

}

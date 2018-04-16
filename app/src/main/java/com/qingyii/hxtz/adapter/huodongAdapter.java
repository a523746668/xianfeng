package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.huodongInfo;

import java.util.ArrayList;

/**
 * 活动
 * @author Administrator
 *
 */
public class huodongAdapter extends BaseAdapter {
   private Context context;
   private ArrayList<huodongInfo> list;
	public huodongAdapter(Context context, ArrayList<huodongInfo> list){
		this.context=context;
		this.list=list;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView=LayoutInflater.from(context).inflate(R.layout.huodong_item, null);
			holder = new ViewHolder();
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title_huodong_item);
			holder.tv_leixing = (TextView) convertView.findViewById(R.id.tv_leixing_huodong_item);
			holder.tv_end_time = (TextView) convertView.findViewById(R.id.tv_end_time_huodong_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_title.setText(list.get(position).getTitle());
		holder.tv_leixing.setText(list.get(position).getLeixing());
		holder.tv_end_time.setText(list.get(position).getEndTime());
//		TextView tv_title=(TextView) convertView.findViewById(R.id.tv_title);
//		TextView tv_leixing=(TextView) convertView.findViewById(R.id.tv_leixing);
//		TextView tv_end_time=(TextView) convertView.findViewById(R.id.tv_end_time);
//		tv_title.setText(text);
//		tv_leixing.setText(text);
//		tv_end_time.setText(text);
		return convertView;
	}
	
	static class ViewHolder{
		TextView tv_title;
		TextView tv_leixing;
		TextView tv_end_time;
	}

}

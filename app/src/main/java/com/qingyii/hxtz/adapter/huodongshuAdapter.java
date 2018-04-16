package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.qingyii.hxtz.ActivityInfo;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.huodongshuInfo;

import java.util.List;

/**
 * 活动树
 * @author Administrator
 *
 */
public class huodongshuAdapter extends BaseAdapter {
    private Context context;
    private List<huodongshuInfo> list;
    private int screenW;
    private HuodongshuGvAdapter mAdapter;
    public  huodongshuAdapter(Context context, List<huodongshuInfo> list, int screenW) {
    	this.context = context;
    	this.list = list;;
    	this.screenW = screenW;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView=LayoutInflater.from(context).inflate(R.layout.huodongshu_item_list, null);
			holder = new ViewHolder();
			holder.tv_time = (TextView) convertView.findViewById(R.id.time_huodongshu_item);
			holder.tv_title = (TextView) convertView.findViewById(R.id.content_huodongshu_item);
			holder.gv = (GridView) convertView.findViewById(R.id.gv_huodongshu_item);
			holder.gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent intent=new Intent(context,ActivityInfo.class);
					intent.putExtra("selectIndext", position);
					context.startActivity(intent);
				}
			});
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_time.setText(list.get(position).getTime());
		holder.tv_title.setText(list.get(position).getTitle());
//		mAdapter = new HuodongshuGvAdapter(context, list.get(position).getImgUrls(), screenW);
		mAdapter = new HuodongshuGvAdapter(context, list.get(position).getImgUrls(), screenW);
		holder.gv.setAdapter(mAdapter);
		return convertView;
	}
	static class ViewHolder{
		private GridView gv;
		private TextView tv_title, tv_time;
	}

}

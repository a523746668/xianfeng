package com.qingyii.hxt.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.R;
import com.qingyii.hxt.bean.NewsType;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;

import java.util.ArrayList;

public class SubscriptionGridViewAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<NewsType> list;
	DisplayImageOptions options; // 配置图片加载及显示选项
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	public SubscriptionGridViewAdapter(Context context, ArrayList<NewsType> list) {
		this.context = context;
		this.list = list;
		initConfig();
	}

	private void initConfig() {
		options = MyApplication.options;
	}

	@Override
	public int getCount() {
		// 最后添加订阅频道
		return list.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView main_gridview_img;
		TextView main_gridview_title;

		convertView = LayoutInflater.from(context).inflate(
				com.qingyii.hxt.R.layout.main_gridview_item, null);
		if (position == list.size()) {
//			convertView.setBackgroundColor(Color.WHITE);// 设置背景颜色
			main_gridview_img = (ImageView) convertView
					.findViewById(R.id.main_gridview_img);
			main_gridview_img.setScaleType(ScaleType.CENTER_INSIDE);
			main_gridview_title = (TextView) convertView
					.findViewById(R.id.main_gridview_title);
			main_gridview_title.setText("添加");
			main_gridview_img.setImageResource(R.mipmap.tianjiatupian);
		} else {
			main_gridview_img = (ImageView) convertView
					.findViewById(R.id.main_gridview_img);
			main_gridview_title = (TextView) convertView
					.findViewById(R.id.main_gridview_title);
//			convertView.setBackgroundColor(Color.WHITE);// 设置背景颜色
			NewsType newsType = list.get(position);
			ImageLoader.getInstance().displayImage(newsType.getTypeaddress(),
					main_gridview_img, options, animateFirstListener);
			main_gridview_title.setText(newsType.getTypename());
		}

		return convertView;
	}
}

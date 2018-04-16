package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.BooksClassify;

import java.util.List;

public class zuanTiAdapter extends BaseAdapter {
    private Context context;
    private List<BooksClassify.DataBean> list;
    


	public zuanTiAdapter(Context context, List<BooksClassify.DataBean> list) {
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
		convertView=LayoutInflater.from(context).inflate(R.layout.fenlei_item, null);
		TextView tv_fenlei_name=(TextView) convertView.findViewById(R.id.tv_fenlei_name);
		tv_fenlei_name.setText(list.get(position).getName());
		return convertView;
	}

}

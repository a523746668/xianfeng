package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.EpubBookChapter;

import java.util.ArrayList;

public class muluAdapter extends BaseAdapter {
   private Context context;
   private ArrayList<EpubBookChapter> list;
   
	public muluAdapter(Context context, ArrayList<EpubBookChapter> list) {
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
		convertView=LayoutInflater.from(context).inflate(R.layout.mulu_item, null);
		TextView tv_mulu_number=(TextView) convertView.findViewById(R.id.tv_mulu_number);
		TextView tv_mulu_zangjie=(TextView) convertView.findViewById(R.id.tv_mulu_zangjie);
		TextView tv_mulu_name=(TextView) convertView.findViewById(R.id.tv_mulu_name);
//		tv_mulu_number.setText(list.get(position).get);
		tv_mulu_zangjie.setText(list.get(position).getTitle());
//		tv_mulu_name.setText(list.get(position).getContent());
		return convertView;
	}

}

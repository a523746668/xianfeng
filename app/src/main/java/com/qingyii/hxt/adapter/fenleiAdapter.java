package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.pojo.BooksClassify;

import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
public class fenleiAdapter extends BaseAdapter {
    private List<BooksClassify.DataBean> list;
    private Context context;
	
    public fenleiAdapter(List<BooksClassify.DataBean> list, Context context) {
		super();
		this.list = list;
		this.context = context;
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

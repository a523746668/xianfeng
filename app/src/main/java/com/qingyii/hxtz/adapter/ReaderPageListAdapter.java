package com.qingyii.hxtz.adapter;

import android.text.SpannableString;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.customview.ReadView;
import com.qingyii.hxtz.kanshuActivity;

import java.util.ArrayList;

/**
 * 阅读器分页适配器
 * @author shelia
 *
 */
public class ReaderPageListAdapter extends BaseAdapter{
	private kanshuActivity context;
	private ArrayList<SpannableString> list;
	public ReaderPageListAdapter(kanshuActivity context,ArrayList<SpannableString> list) {
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.reader_page_list_item, null);
		}
		ReadView rv=(ReadView) convertView.findViewById(R.id.main_context);
		rv.setTextSize(TypedValue.COMPLEX_UNIT_SP, Integer.parseInt(context.fontsize));
		rv.setText(list.get(arg0));
		return convertView;
	}
	

}

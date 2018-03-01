package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.bean.choiceOfResearchInfo;

import java.util.List;

public class choiceOfResearchAdapter extends BaseAdapter{

	private List<choiceOfResearchInfo> list;
	private Context mContext;
	public choiceOfResearchAdapter(Context c, List<choiceOfResearchInfo> list){
		this.mContext = c;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.choice_list_item, null);
			holder = new ViewHolder();
			holder.tv_choice = (TextView) convertView.findViewById(R.id.rb_chioce_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_choice.setText(list.get(position).getChoiceContent());
		return convertView;
	}
	static class ViewHolder{
		TextView tv_choice;
	}
}

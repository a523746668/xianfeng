package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.choiceOfQuestionInfo;

import java.util.List;

public class choiceOfQuestionAdapter extends BaseAdapter{
	private List<choiceOfQuestionInfo> list;
	private Context mContext;
	public choiceOfQuestionAdapter(Context c, List<choiceOfQuestionInfo> list){
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
			holder.rb_choice = (TextView) convertView.findViewById(R.id.rb_chioce_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.rb_choice.setText(list.get(position).getChoiceContent());
		return convertView;
	}
	static class ViewHolder{
		TextView rb_choice;
	}

}

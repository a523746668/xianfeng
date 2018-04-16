package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.ExamRankInfo;

import java.util.List;

/**
 * 广场积分榜适配器
 * @author shelia
 *
 */
public class ExamRankListAdapter_02 extends BaseAdapter{
	private List<ExamRankInfo> list;
	private Context context;
	public ExamRankListAdapter_02(Context context, List<ExamRankInfo> list){
		this.context = context;
		this.list = list;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.kaoshirank_list_item_02, null);
			holder = new ViewHolder();
			holder.tv_name = (TextView) convertView.findViewById(R.id.name_kaoshirank_item);
			holder.tv_rank = (TextView) convertView.findViewById(R.id.rank_kaoshirank_item);
			holder.tv_score = (TextView) convertView.findViewById(R.id.score_kaoshirank_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_name.setText(list.get(position).getName());
		holder.tv_rank.setText((position+1)+"");
		holder.tv_score.setText(list.get(position).getScore()+"分");
		return convertView;
	}
	static class ViewHolder{
		TextView tv_rank, tv_name, tv_score;
	}

}

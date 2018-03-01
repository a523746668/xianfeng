package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.pojo.PersonalScore;
import com.qingyii.hxt.util.EmptyUtil;
import com.qingyii.hxt.util.TextUtil;
import com.qingyii.hxt.util.TimeUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PersonalScoreAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<PersonalScore> list;
	private String typess;
	private DecimalFormat mFormat;

	public PersonalScoreAdapter(Context context,ArrayList<PersonalScore> list,String typess){
		mFormat = TextUtil.getDefaultDecimalFormat();
		      this.context=context;
		      this.list=list;
		      this.typess=typess;
	}
//	public void updateListUi() {  
//       
//        notifyDataSetChanged();  
//       
//    }  
	
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
		// TODO Auto-generated method stub
		
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.personalfragment_other_item,null);
			holder.fragment_paiming=(TextView) convertView.findViewById(R.id.fragment_paiming);
			holder.fragment_xingming=(TextView) convertView.findViewById(R.id.fragment_xingming);
			holder.fragment_score=(TextView) convertView.findViewById(R.id.fragment_score);
			holder.fragment_danwei=(TextView) convertView.findViewById(R.id.fragment_danwei);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		
		if(EmptyUtil.IsNotEmpty(list.get(position).getCreatetimeStr())){
			holder.fragment_xingming.setText(list.get(position).getCreatetimeStr());
		}
		if(EmptyUtil.IsNotEmpty(list.get(position).getScore())){
			String str = "";
			float score = Float.valueOf(list.get(position).getScore());
			if("2".equals(typess)||"4".equals(typess)){
				  str = mFormat.format(score)+"分";
			  }else if("3".equals(typess)){
				  str="第"+mFormat.format(score)+"关";
			  }
			holder.fragment_danwei.setText(str);
		}
		if(EmptyUtil.IsNotEmpty(list.get(position).getDuration())){
			holder.fragment_score.setText(TimeUtil.secToTime02(Integer.parseInt(list.get(position).getDuration())));
		}
		
//		if("2".equals(typess)){
//			holder.fragment_paiming.setVisibility(NotifyView.GONE);
//		}else if("3".equals(typess)){
//			holder.fragment_paiming.setVisibility(NotifyView.GONE);
//		}else if("4".equals(typess)){
//			holder.fragment_paiming.setText((position+1)+"");
//		}
		//holder.fragment_paiming.setText((position+1)+"");
		holder.fragment_paiming.setText(list.get(position).getScoreid());
		return convertView;
	}

	static class ViewHolder{
		TextView fragment_paiming, fragment_xingming, fragment_score,fragment_danwei;
	}
	
	
	
	
	
	
}

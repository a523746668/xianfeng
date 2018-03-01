package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.pojo.Discuss;

import java.util.ArrayList;


public class MoreCommentAdapter extends BaseAdapter {
	
private	ArrayList<Discuss>list=new ArrayList<Discuss>();
private Context context;	



public MoreCommentAdapter(ArrayList<Discuss>list,Context context){
	  super();
	  this.context=context;
	  this.list=list;
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
		// TODO Auto-generated method stub
		OtherMorecommentAdapter adapter;
		ArrayList<Discuss> list2 = new ArrayList<Discuss>();
		
		
	convertView=LayoutInflater.from(context).inflate(R.layout.shujixq_pinglun_item, null);
	
	TextView textViewname=(TextView) convertView.findViewById(R.id.tv_shujixq_pl_name);
	TextView textViewtime=(TextView) convertView.findViewById(R.id.tv_shujixq_pl_time);
	TextView textViewcomment=(TextView) convertView.findViewById(R.id.tv_shujixq_pl_content);
	
	ImageView iv_morecommentliang_dianzanimage=(ImageView) convertView.findViewById(R.id.iv_shujixqliang_pl_dianzanimage);
	ImageView iv_morecommentblack_dianzanimage=(ImageView) convertView.findViewById(R.id.iv_shujixqblack_pl_dianzanimage);
	TextView tv_morecomment_dianzanshu=(TextView) convertView.findViewById(R.id.tv_shujixq_pl_dianzanshu);
	ImageView iv_shujixq_pl_pinglun = (ImageView) convertView.findViewById(R.id.iv_shujixq_pl_pinglun);
	
	textViewname.setText(list.get(position).getUsername());
	textViewtime.setText(list.get(position).getCreatetimeStr());
	textViewcomment.setText(list.get(position).getContent());
	
		return convertView;
	}

}

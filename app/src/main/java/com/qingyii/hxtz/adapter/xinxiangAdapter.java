package com.qingyii.hxtz.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.xinxiangInfo;

import java.util.List;

public class xinxiangAdapter extends BaseAdapter {
   private Context context;
   private List<xinxiangInfo> list;
	
   public xinxiangAdapter(Context context, List<xinxiangInfo> list) {
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

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    convertView=LayoutInflater.from(context).inflate(R.layout.xinxiang_item, null);
	    Button bt_xinxiang_isyuedu=(Button) convertView.findViewById(R.id.bt_xinxiang_isyuedu);
	    TextView tv_xinxiang_content=(TextView) convertView.findViewById(R.id.tv_xinxiang_content);
	    TextView tv_xinxiang_name=(TextView) convertView.findViewById(R.id.tv_xinxiang_name);
	    tv_xinxiang_name.setText(list.get(position).getXinxiang_name());
	    tv_xinxiang_content.setText(list.get(position).getXinxiang_content());
	   
	    if (list.get(position).getIsyuedu().equals("1")) {
	    	bt_xinxiang_isyuedu.setText("已阅");
		}else if (list.get(position).getIsyuedu().equals("2")) {
			bt_xinxiang_isyuedu.setText("");
//			bt_xinxiang_isyuedu.setBackgroundColor(R.color.red);
		}
		return convertView;
	}

}

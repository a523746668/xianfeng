package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxt.R;
import com.qingyii.hxt.bean.mytongshiInfo;

import java.util.List;

public class mytongshiAdapter extends BaseAdapter {
    private Context context;
    private List<mytongshiInfo> list;
	public mytongshiAdapter(Context context,List<mytongshiInfo> list){
		this.list=list;
		this.context=context;
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
		mytongshiInfo info=list.get(position);
		convertView=LayoutInflater.from(context).inflate(R.layout.mytongshi_item, null);
		TextView tv_tongshi_name=(TextView) convertView.findViewById(R.id.tv_tongshi_name);
		TextView tv_tongshi_sex=(TextView) convertView.findViewById(R.id.tv_tongshi_sex);
		TextView tv_tongshi_unit=(TextView) convertView.findViewById(R.id.tv_tongshi_unit);
		TextView tv_tongshi_zhiwu=(TextView) convertView.findViewById(R.id.tv_tongshi_zhiwu);
		ImageView mytongshiInfo=(ImageView) convertView.findViewById(R.id.iv_tongshi_image);
		tv_tongshi_name.setText(list.get(position).getTongshiName());
		tv_tongshi_sex.setText(list.get(position).getTongshiSex());
		tv_tongshi_unit.setText(list.get(position).getTongshiUnit());
		tv_tongshi_zhiwu.setText(list.get(position).getTongshiZhiwu());
		ImageLoader.getInstance().displayImage(info.getTongshiImage(), mytongshiInfo);
		return convertView;
	}

}

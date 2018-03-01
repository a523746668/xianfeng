package com.qingyii.hxt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxt.ActivityInfo;
import com.qingyii.hxt.R;
import com.qingyii.hxt.bean.dongtaiInfo;
import com.qingyii.hxt.bean.gridview_dongtai;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;

import java.util.ArrayList;

public class dongtaiAdapter extends BaseAdapter {
   private Context context;
   private ArrayList<dongtaiInfo>list;
   private GridViewAdapter adapter;
   
   private AnimateFirstDisplayListener listener = new AnimateFirstDisplayListener();
   
   
   public dongtaiAdapter(Context context,ArrayList<dongtaiInfo>list) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		 ArrayList<gridview_dongtai>  list2=new ArrayList<gridview_dongtai>();
		ViewHolder holder = null;
		if(convertView == null){
			
			convertView=LayoutInflater.from(context).inflate(R.layout.dongtai_item, null);
			holder = new ViewHolder();
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_dt_name);
			holder. tv_content=(TextView) convertView.findViewById(R.id.tv_dt_content);
			holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_dt_icon);
			holder.gv_dongtai_item=(GridView) convertView.findViewById(R.id.gv_dongtai_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		dongtaiInfo d=list.get(position);
		holder.tv_name.setText(list.get(position).getName());
		holder.tv_content.setText(list.get(position).getDongtai());
		ImageLoader.getInstance().displayImage(d.getPhoto(), holder.iv_icon,MyApplication.options,listener);
		//加载gridview数据
		gridview_dongtai g=new gridview_dongtai();
		g.setImageUrl("drawable://"+ R.mipmap.haimianbb);
		gridview_dongtai g1=new gridview_dongtai();
		g1.setImageUrl("drawable://"+ R.mipmap.haimianbb);
		gridview_dongtai g2=new gridview_dongtai();
		g2.setImageUrl("drawable://"+ R.mipmap.haimianbb);
		gridview_dongtai g3=new gridview_dongtai();
		g3.setImageUrl("drawable://"+ R.mipmap.haimianbb);
		if(position==1){
			list2.add(g);
			list2.add(g1);
			list2.add(g2);
		}
		adapter=new GridViewAdapter(context, list2);
		holder.gv_dongtai_item.setAdapter(adapter);
		holder.gv_dongtai_item.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(context,ActivityInfo.class);
				intent.putExtra("selectIndext", position);
				context.startActivity(intent);
			}
		});
        return convertView;
	}
	static class ViewHolder{
		TextView tv_name, tv_content;
		ImageView iv_icon;
		GridView gv_dongtai_item;
	}

}

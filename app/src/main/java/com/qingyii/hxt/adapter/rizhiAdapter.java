package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.bean.gridview_dongtai;
import com.qingyii.hxt.bean.rizhiInfo;

import java.util.ArrayList;
import java.util.List;

public class rizhiAdapter extends BaseAdapter {
    private Context context;
    private List<rizhiInfo> list;
    private GridViewAdapter adapter;
    
    public rizhiAdapter(Context context, List<rizhiInfo> list) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ArrayList<gridview_dongtai>  list2=new ArrayList<gridview_dongtai>();
		convertView=LayoutInflater.from(context).inflate(R.layout.rizhi_item, null);
		TextView tv_rizhi_date=(TextView) convertView.findViewById(R.id.tv_rizhi_date);
		TextView  tv_tizhi_content=(TextView) convertView.findViewById(R.id.tv_tizhi_content);
		GridView  gv_rizhi_item=(GridView) convertView.findViewById(R.id.gv_rizhi_item);
		tv_rizhi_date.setText(list.get(position).getCreatDate());
		tv_tizhi_content.setText(list.get(position).getRizhi_title());
		//gridview
		gridview_dongtai g=new gridview_dongtai();
		g.setImageUrl("drawable://"+ R.mipmap.haimianbb);
		gridview_dongtai g1=new gridview_dongtai();
		g1.setImageUrl("drawable://"+ R.mipmap.haimianbb);
		gridview_dongtai g2=new gridview_dongtai();
		g2.setImageUrl("drawable://"+ R.mipmap.dongtaidu1);
		gridview_dongtai g3=new gridview_dongtai();
		g3.setImageUrl("drawable://"+ R.mipmap.dongtaidu1);

		if(position==1){
			list2.add(g);
			list2.add(g1);
			list2.add(g2);
			list2.add(g3);
		
		}
		adapter=new GridViewAdapter(context, list2);
		gv_rizhi_item.setAdapter(adapter);
		return convertView;
	}

}

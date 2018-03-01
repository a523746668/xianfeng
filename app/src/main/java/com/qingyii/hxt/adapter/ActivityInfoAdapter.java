package com.qingyii.hxt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxt.R;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.pojo.ActivityDiscussPic;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * 活动树图片适配器
 * @author shelia
 *
 */
public class ActivityInfoAdapter extends BaseAdapter{
	private AnimateFirstDisplayListener listener = new AnimateFirstDisplayListener();
	ArrayList<ActivityDiscussPic> list;
	Context context;
	public ActivityInfoAdapter(Context context,ArrayList<ActivityDiscussPic> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		PhotoView photo_big_pic_photoview = null;
		LinearLayout item_menu = null;
		TextView item_page=null;
		TextView item_content=null;
//		if(convertView==null){
			 convertView = LayoutInflater.from(context).inflate(R.layout.activity_info_item, parent, false);
			 photo_big_pic_photoview=(PhotoView) convertView.findViewById(R.id.photo_big_pic_photoview);
			 item_menu=(LinearLayout) convertView.findViewById(R.id.item_menu);
			 item_page=(TextView) convertView.findViewById(R.id.item_page);
			 item_content=(TextView) convertView.findViewById(R.id.item_content);
			 ImageView pingran=(ImageView) convertView.findViewById(R.id.pingran);
			 final ImageView xianhua=(ImageView) convertView.findViewById(R.id.xianhua);
			 final ImageView jidang=(ImageView) convertView.findViewById(R.id.jidang);
			 final TextView jidang_valuse=(TextView) convertView.findViewById(R.id.jidang_valuse);
			 final TextView xianhua_valuse=(TextView) convertView.findViewById(R.id.xianhua_valuse);
//		}
		pingran.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "接口对接中。。", Toast.LENGTH_SHORT).show();
			}
		});
		xianhua.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				xianhua.setBackgroundResource(R.mipmap.xianhua_red_72);
				xianhua_valuse.setText((Integer.parseInt(xianhua_valuse.getText().toString())+1)+"");
			}
		});
		jidang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				jidang.setBackgroundResource(R.mipmap.jidang_red_72);
				jidang_valuse.setText((Integer.parseInt(jidang_valuse.getText().toString())+1)+"");
			}
		});
		//设置上下滚动
		item_content.setMovementMethod(ScrollingMovementMethod.getInstance());
		ImageLoader.getInstance().displayImage(list.get(position).getPicaddress(), photo_big_pic_photoview, MyApplication.options, listener);
		//设置背景黑色半透明
		item_menu.setBackgroundColor(Color.argb(80, 0, 0, 0));
		item_page.setText(position+1+"/"+list.size());
		item_content.setText(list.get(position).getContent());
		return convertView;
	}

}

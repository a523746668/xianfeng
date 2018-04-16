package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.pojo.Book;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;
import com.qingyii.hxtz.util.EmptyUtil;

import java.util.ArrayList;

public class paihangAdapter extends BaseAdapter {
   private Context context;
   private ArrayList<Book> list;
   private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	@Override
	
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public paihangAdapter(Context context, ArrayList<Book> list) {
		super();
		this.context = context;
		this.list=list;
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
		Book info=list.get(position);
		convertView=LayoutInflater.from(context).inflate(R.layout.fragment_paihang_item, null);
		ImageView iv_paihang_Image=(ImageView) convertView.findViewById(R.id.iv_paihang_Image);
		TextView tv_paihang_isgk=(TextView) convertView.findViewById(R.id.tv_paihang_isgk);
		TextView tv_paihang_title=(TextView) convertView.findViewById(R.id.tv_paihang_title);
		TextView tv_paihang_content=(TextView) convertView.findViewById(R.id.tv_paihang_content);
		TextView tv_paihang_read=(TextView) convertView.findViewById(R.id.tv_paihang_read);
//		tv_paihang_isgk.setText(list.get(position).getPaihang_isGkai());
		
		if(EmptyUtil.IsNotEmpty(info.getBookname())){
			tv_paihang_title.setText(info.getBookname());
		}
		if(EmptyUtil.IsNotEmpty(info.getReadcount())){
			tv_paihang_read.setText("阅读次数：  "+info.getReadcount()+"次");
		}
		if(EmptyUtil.IsNotEmpty(info.getBookdesc())){
			tv_paihang_content.setText(info.getBookdesc());
		}
		if(EmptyUtil.IsNotEmpty(info.getReadflag())){
			if("1".equals(info.getReadflag())){
				tv_paihang_isgk.setText("公开");
			}else if("2".equals(info.getReadflag())){
				tv_paihang_isgk.setText("专有");
			}
		}
		 ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir+info.getPicaddress(), iv_paihang_Image, MyApplication.options, animateFirstListener);
//		ImageLoader.getInstance().displayImage(info.getPicaddress(), iv_paihang_Image);
								
		return convertView;
	}

}

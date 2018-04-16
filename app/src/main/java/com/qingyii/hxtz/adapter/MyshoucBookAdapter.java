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

/**
 * 
 * @author TaiAiCH
 *收藏书籍的适配器
 */

public class MyshoucBookAdapter extends BaseAdapter {
	private ArrayList<Book> list;
	private Context context;
	 private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private View bookview;
	public MyshoucBookAdapter(Context context,ArrayList<Book> list) {
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
		Book info=list.get(position);
		if(convertView ==null){
		convertView = LayoutInflater.from(context).inflate(R.layout.myshouc_shuji_item, null);
		ImageView iv_scsj_Image= (ImageView) convertView.findViewById(R.id.iv_scsj_Image);
		TextView tv_scsj_title= (TextView) convertView.findViewById(R.id.tv_scsj_title);
		TextView tv_scsj_name= (TextView) convertView.findViewById(R.id.tv_scsj_name);
		TextView tv_scsj_content= (TextView) convertView.findViewById(R.id.tv_scsj_content);
		TextView tv_scsj_isgk = (TextView) convertView.findViewById(R.id.tv_scsj_isgk);
       
		
		if(EmptyUtil.IsNotEmpty(info.getReadflag())){
			if("1".equals(info.getReadflag())){
				tv_scsj_isgk.setText("公开");
			}else if("2".equals(info.getReadflag())){
				tv_scsj_isgk.setText("专有");
			}
		}
		
		if(EmptyUtil.IsNotEmpty(list.get(position).getBookname())){
			 tv_scsj_title.setText(list.get(position).getBookname());
		}
       if(EmptyUtil.IsNotEmpty(list.get(position).getUsername())){
    	   tv_scsj_name.setText("上传者："+list.get(position).getUsername());
       }
       if(EmptyUtil.IsNotEmpty(list.get(position).getBookdesc())){
    	   tv_scsj_content.setText(list.get(position).getBookdesc()); 
       }
       if(EmptyUtil.IsNotEmpty(info.getPicaddress())){
    	   ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir+info.getPicaddress(), iv_scsj_Image,MyApplication.options);
       }
      
	}
		return convertView;
	}
}

package com.qingyii.hxt.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.andbase.library.app.base.AbBaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.LoginActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;

import java.io.InputStream;

//import com.ab.activity.AbActivity;

public class GuideViewflowAdapter extends BaseAdapter{
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	DisplayImageOptions options; // 配置图片加载及显示选项
	// 图片资源的id
	int[] imageUrls;
	private Context context;
	private LayoutInflater mInflater;
	public GuideViewflowAdapter(Context context,int[] imageUrls){
		this.context=context;
		this.imageUrls=imageUrls;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageUrls.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageUrls[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int id=imageUrls[position];
		convertView = mInflater.inflate(R.layout.guide_item, null);
		ImageView viewflow_item_imgView = (ImageView) convertView
				.findViewById(R.id.guide_img);
		//方式一：某些机型会出现OOM的情况(4.1.1版本容易出现)
//		viewflow_item_imgView.setBackgroundResource(id);
		//方式三：
		viewflow_item_imgView.setImageBitmap(readBitMap(context, id));
		//方式二:
		/*String imgurl="";
		if(position==0){
			imgurl="drawable://" + R.drawable.guide_01;
		}else if(position==1){
			imgurl="drawable://" + R.drawable.guide_02;
		}else if(position==2){
			imgurl="drawable://" + R.drawable.guide_03;
		}else if(position==3){
			imgurl="drawable://" + R.drawable.guide_04;
		}
		ImageLoader.getInstance().displayImage(imgurl,
				viewflow_item_imgView, options, animateFirstListener);*/
		viewflow_item_imgView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(position==imageUrls.length-1){
					Intent intent=new Intent(context,LoginActivity.class);
					context.startActivity(intent);
					((AbBaseActivity) context).finish();
				}
			}
		});
		return convertView;
	}
	
	/**
     * 以最省内存的方式读取本地资源的图片
     * @param context
     * @param resId
     * @return
     */  
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
}
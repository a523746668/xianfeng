package com.qingyii.hxtz.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.util.AbDialogUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxtz.LoginActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.database.DianzanTubiao;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.pojo.Discuss;
import com.qingyii.hxtz.util.EmptyUtil;
import com.qingyii.hxtz.util.TimeUtil;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//import com.ab.util.AbDialogUtil;

public class neiKanInfoAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Discuss> list;
	
//	private Discuss dinfo;
//	int posionId;

	public neiKanInfoAdapter(Context context, ArrayList<Discuss> list) {
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
//		posionId = position;
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	    ontherPinglunAdapter adapter;
		ArrayList<Discuss> list2 = new ArrayList<Discuss>();

		convertView = LayoutInflater.from(context).inflate(R.layout.neikaninfo_item, null);
		TextView tv_neikaninfo_name = (TextView) convertView.findViewById(R.id.tv_neikaninfo_name);
		TextView tv_neikaninfo_content = (TextView) convertView.findViewById(R.id.tv_neikaninfo_content);
		TextView tv_neikaninfo_time = (TextView) convertView.findViewById(R.id.tv_neikaninfo_time);
		ImageView iv_neikaninfo_image = (ImageView) convertView.findViewById(R.id.iv_neikaninfo_image);
		TextView tv_xianhuashu = (TextView) convertView.findViewById(R.id.tv_xianhuashu);
		ImageView iv_pinglun = (ImageView) convertView.findViewById(R.id.iv_pinglun);
        final ImageView neikaninfo_item_dianzanblack_iv = (ImageView) convertView.findViewById(R.id.neikaninfo_item_dianzanblack_iv);
        final ImageView neikaninfo_item_dianzanliang_iv = (ImageView) convertView.findViewById(R.id.neikaninfo_item_dianzanliang_iv);
		ListView mlistview = (ListView) convertView.findViewById(R.id.mlistview);
		tv_neikaninfo_name.setText(list.get(position).getUsername());
		tv_neikaninfo_content.setText(list.get(position).getContent());     
        tv_neikaninfo_time.setText(TimeUtil.getRecentTimeDisplayOfUnixTimestamp(Long.parseLong(list.get(position).getCreatetime())));
		list2=list.get(position).getDiscussList();
		
		if (EmptyUtil.IsNotEmpty(list.get(position).getPraisecount())) {
			tv_xianhuashu.setText(list.get(position).getPraisecount());
		}else {
			tv_xianhuashu.setText("0");
		}

		//头像
		if(EmptyUtil.IsNotEmpty(list.get(position).getPicaddress())){
			ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir+list.get(position).getPicaddress(), iv_neikaninfo_image, MyApplication.options);
		}else {
			iv_neikaninfo_image.setBackgroundResource(R.mipmap.haimianbb);
		}
		
		if(DianzanTubiao.pinglunChaXun(Integer.parseInt(list.get(position).getDiscussid()))){
			    list.get(position).setFlag(1);
		}else{
			    list.get(position).setFlag(0);
		}
		
		if(list.get(position).getFlag()==1){
			neikaninfo_item_dianzanblack_iv.setVisibility(View.GONE);
			neikaninfo_item_dianzanliang_iv.setVisibility(View.VISIBLE);
		}else if(list.get(position).getFlag()==0){
			neikaninfo_item_dianzanblack_iv.setVisibility(View.VISIBLE);
			neikaninfo_item_dianzanliang_iv.setVisibility(View.GONE);
		}
		
//        if(list.get(position).getFlag()==0){
//        	neikaninfo_item_dianzanliang_iv.setVisibility(NotifyView.GONE);
//        	neikaninfo_item_dianzanblack_iv.setVisibility(NotifyView.VISIBLE);
//        }else if(list.get(position).getFlag()==1){        	
//        	neikaninfo_item_dianzanliang_iv.setVisibility(NotifyView.VISIBLE);
//        	neikaninfo_item_dianzanblack_iv.setVisibility(NotifyView.GONE);
//        } 
//	
			
		
		
		
		
		/**
		 * 其他人评论
		 */
//		 ontherPinglunInfo info=new ontherPinglunInfo();
//		 info.setTv_onther_name("eason");
//		 info.setTv_onther_time("5分钟前");
//		 info.setTv_onther_content("美国和英国势头较强，恩说的好，非常赞同");
//		 ontherPinglunInfo info1=new ontherPinglunInfo();
//		 info1.setTv_onther_name("jarvan");
//		 info1.setTv_onther_time("12分钟前");
//		 info1.setTv_onther_content("啦啦啦啦啦啦啦");
//		 ontherPinglunInfo info2=new ontherPinglunInfo();
//		 info2.setTv_onther_name("Perso");
//		 info2.setTv_onther_time("30分钟前");
//		 info2.setTv_onther_content("还可以还可以还可以还可以");
//		 if (position==0) {
//		 list2.add(info);
//		 list2.add(info1);
//		 ontherPinglunInfo info=new ontherPinglunInfo();
//		 info.getUsername();
//		 info.getContent();
//		 info.getCreatetime();
//		 list2.add(info);
//		 }
		
		adapter = new ontherPinglunAdapter(context, list2);
		mlistview.setAdapter(adapter);
		
		iv_pinglun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(context, xiePinlunActivity2.class);
//				intent.putExtra("dinfo", list.get(position));
//				context.startActivity(intent);
				popupwindow(position);
			}
		});

		
		neikaninfo_item_dianzanblack_iv.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				 if(list.get(position).getFlag()==0){					 
					 pinglundianzan(position);
				 }else if(list.get(position).getFlag()==1){
				Toast.makeText(context, "您已经为该评论点赞过了！", Toast.LENGTH_SHORT).show();	
				 }
				
//				 tv_xianhuashu.setText((Integer.parseInt(tv_xianhuashu.getText().toString()) + 1) + "");
//				xianhua(1);
			}
		});
		
		
		neikaninfo_item_dianzanliang_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(list.get(position).getFlag()==0){					 
					 pinglundianzan(position);
				 }else if(list.get(position).getFlag()==1){
				Toast.makeText(context, "您已经为该评论点赞过了！", Toast.LENGTH_SHORT).show();	
				 }
			}
		});
		
		
		
		
		
		return convertView;

	}

	
	
	
	/**
	 * 弹出评论框
	 */
	private void popupwindow(final int position){
		
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    final  View layout=inflater.inflate(R.layout.xiepinglun_neikan_chuangkou, null);
//	    PopupWindow menuWindow = new PopupWindow(layout,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
	    /** 设置点击其他地方消失**/
//	    menuWindow.setFocusable(true); 
//	    menuWindow.setOutsideTouchable(true); 
//	    menuWindow.update(); 
//	    menuWindow.setBackgroundDrawable(new BitmapDrawable()); 
//        /**弹出效果**/
//	    menuWindow.showAsDropDown(layout); 
//       /** 设置在Activity中弹出位置**/
//	   menuWindow.showAtLocation(menuWindow, Gravity.BOTTOM, 0, 0); 
	    
	    
	    
	    
	    
	    ImageView xiepinglun_neikan_chuangkou_quxiao=(ImageView)layout. findViewById(R.id.xiepinglun_neikan_chuangkou_quxiao);
	    /** X按钮**/
	    xiepinglun_neikan_chuangkou_quxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AbDialogUtil.removeDialog(layout);
				
			}
		});
	    
	    TextView xiepinglun_neikan_chuangkou_fayan=(TextView) layout.findViewById(R.id.xiepinglun_neikan_chuangkou_fayan);
	             xiepinglun_neikan_chuangkou_fayan.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AbDialogUtil.removeDialog(layout);
					}
				});
	    
	    
	    
	    
	    
	    ImageView xiepinglun_neikan_chuangkou_zhengque=(ImageView) layout.findViewById(R.id.xiepinglun_neikan_chuangkou_zhengque);
	    final EditText xiepinglun_neikan_chuangkou_edittext=(EditText) layout.findViewById(R.id.xiepinglun_neikan_chuangkou_edittext);
	    /**正确按钮**/
	    xiepinglun_neikan_chuangkou_zhengque.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String edit=xiepinglun_neikan_chuangkou_edittext.getText().toString().trim();
				String parentid=list.get(position).getDiscussid();
				String articleid=list.get(position).getArticleid();
				
				if(!EmptyUtil.IsNotEmpty(edit)){
					Toast.makeText(context, "评论内容不能为空", Toast.LENGTH_SHORT).show();
				}else{
					fbPinglun(edit,parentid,articleid);
					AbDialogUtil.removeDialog(layout);
				}
			}
		});
     
	    AbDialogUtil.showDialog(layout, Gravity.BOTTOM);
	}	
	
	
	
	/**
	 * 子评论添加
	 * @param eidtt
	 */
	
 	@SuppressWarnings("unused")
	private void fbPinglun(String eidtt, String parentid,  String articleid){
	       JSONObject Obj=new JSONObject();
	      try {	
	        Obj.put("content", eidtt);
	        Obj.put("userid", CacheUtil.userid);
			Obj.put("parentid",parentid);
			Obj.put("articleid",articleid);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			byte[] bytes;
			ByteArrayEntity entity = null;
			try {
				bytes = Obj.toString().getBytes("utf-8");
				entity = new ByteArrayEntity(bytes);
				YzyHttpClient.post(context, HttpUrlConfig.addDiscuss, entity,
						new AsyncHttpResponseHandler(){
					     @Override
					    public void onSuccess(int statusCode, String content) {
					    	  super.onSuccess(statusCode, content);
								if(statusCode==499){
									Toast.makeText(context, CacheUtil.logout, Toast.LENGTH_SHORT).show();
									Intent intent=new Intent(context,LoginActivity.class);
									context.startActivity(intent);
									   onFinish();
								}else if (statusCode==200) {
					    		  
								try {
									JSONObject Obj=new JSONObject(content);								
									if("add_success".equals(Obj.getString("message"))){								
								            // 更新适配器
								            notifyDataSetChanged();										
											//发送广播											
											Intent intent=new Intent();
											intent.setAction("action.refreshMyAddress");
											context.sendBroadcast(intent);	
									Toast.makeText(context, "添加评论成功", Toast.LENGTH_SHORT).show();	
								
									}else{
								Toast.makeText(context, "添加评论失败", Toast.LENGTH_SHORT).show();		
									}
									
								} catch (JSONException e) {
								
									e.printStackTrace();
								}
							}
					    }
					
				});
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}
		
		
		}
	
	
	
/**
 * 评论点赞接口	
 */
private void  pinglundianzan(final int position){
	JSONObject jsonObject=new JSONObject();
	try {
		jsonObject.put("discussid", list.get(position).getDiscussid());
		jsonObject.put("userid", CacheUtil.userid);
		jsonObject.put("praisecount", 1);
		byte[]bytes;
		ByteArrayEntity entity=null;
		try {
			bytes=jsonObject.toString().getBytes("utf-8");
			entity=new ByteArrayEntity(bytes);
			YzyHttpClient.post(context, HttpUrlConfig.pinglundianzan, entity, new AsyncHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, String content) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, content);
					if(statusCode==200){
		            try {
						JSONObject jsonObject =new JSONObject(content);
						String message=jsonObject.getString("message");
						String msg=jsonObject.getString("msg");
					if(("prais_success").equals(message)){											   
						DianzanTubiao.pinglunadd(list.get(position).getDiscussid());					    
						int a=Integer.parseInt(list.get(position).getPraisecount())+1;
						list.get(position).setPraisecount(a+"");
						notifyDataSetChanged();																						
						}else {
						 if(("您已经为该评论点赞过了！").equals(msg)){								 
							 list.get(position).setFlag(1);
							Toast.makeText(context, msg, Toast.LENGTH_SHORT).show(); 
						DianzanTubiao.pinglunadd(list.get(position).getDiscussid());
						         notifyDataSetChanged();	
						 }else {
							 Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
						 }	
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
					}
				}				
				@Override
				public void onFailure(Throwable error, String content) {
					// TODO Auto-generated method stub
					super.onFailure(error, content);
									
				}
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();
					
				}
				
			});
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}	
	
	
	
	
	
	
	
///**
// * 鲜花鸡蛋接口
// */
//	private void xianhua(int flag) {
//		// flag=1 鲜花   flag=2  鸡蛋
//		JSONObject JSOBJ = new JSONObject();
//		try {
//			JSOBJ.put("discussid", list.get(posionId).getDiscussid() + "");
//			if (flag==1) {
//				JSOBJ.put("praisecount", 1);
//			}else if (flag==2) {
//				JSOBJ.put("eggcount", 1);
//			}					
//		    byte[] bytes;
//			ByteArrayEntity entity = null;
//			// 处理保存数据中文乱码问题
//			try {
//				bytes = JSOBJ.toString().getBytes("utf-8");
//				entity = new ByteArrayEntity(bytes);
//				YzyHttpClient.post(context, HttpUrlConfig.addXianhua,
//						entity, new AsyncHttpResponseHandler() {
//							@Override
//							public void onSuccess(int statusCode, String content) {
//								super.onSuccess(statusCode, content);
//								if (statusCode == 200) {
//									Gson gson = new Gson();
//     
//									try {
//										JSONObject Obj = new JSONObject(content);
//										
//									} catch (JSONException e) {
//                                         
//										e.printStackTrace();
//									}
//								}
//							}
//						});
//			} catch (UnsupportedEncodingException e) {
//
//				e.printStackTrace();
//			}
//
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}

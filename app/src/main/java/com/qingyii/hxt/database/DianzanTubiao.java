package com.qingyii.hxt.database;

import android.database.Cursor;
import android.database.SQLException;

import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.MyApplication;

public class DianzanTubiao {

	/**
	 * 内刊内容点赞查询
	 * @param Articleid
	 */
	public static boolean chaxun(int Articleid){
		boolean flag=false;
		Cursor re=MyApplication.helper.getWritableDatabase().
				rawQuery("select * from dianzan_info where comingtype=1 and article_id="+Articleid, null);
		if(re.getCount()>0){
			re.moveToFirst();
			for(int i=0;i<re.getCount();i++){
				int article_id=re.getInt(re.getColumnIndex("article_id"));
				
				if(article_id==Articleid){
					flag=true;
				}
				re.moveToNext();
			}
		}
		return flag;
	}
	
	
	/**
	 * 内刊内容点赞增加
	 */
     public static boolean add(String Articleid){
    	 boolean flag=true;
 		//1、插入新闻图片表
 		String sql="insert into dianzan_info(article_id,comingtype,username_id) VALUES("+Articleid+",'1','"+CacheUtil.userid+"')";
 			try {
 				MyApplication.helper.getWritableDatabase().execSQL(sql);
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				flag=false;
 				e.printStackTrace();
 			}
 			
 		return flag;
     }
	
     
     /**
      * 内刊内容评论查询
      */
     
   public static boolean  pinglunChaXun(int Discussid){
	     boolean flag=false;
	   String sql="select * from  dianzan_info where comingtype=2 and article_id="+Discussid;
	    Cursor re=MyApplication.helper.getWritableDatabase().rawQuery(sql, null);
	          if(re.getCount()>0){
	        	 re.moveToFirst();
	        	 for(int i=0;i<re.getCount();i++){
	        		 int  discussid=re.getInt(re.getColumnIndex("article_id"));
	        		 if(discussid==Discussid){
	        			 flag=true;
	        		 }
	        		 re.moveToNext();
	        	 }
	          }
	               return flag;
	   
   } 
   
   /**
    * 内刊内容子评论添加
    */
   
  public static boolean pinglunadd(String Discussid){
	    boolean flag=false;
	    String sql="insert into dianzan_info(article_id,comingtype,username_id) VALUES("+Discussid+",'2','"+CacheUtil.userid+"')";
	          MyApplication.helper.getWritableDatabase().execSQL(sql);
	          flag=true;
	  	  
	            return flag;
	   
  } 
   
   
   
   
   
   
   
     
     
}

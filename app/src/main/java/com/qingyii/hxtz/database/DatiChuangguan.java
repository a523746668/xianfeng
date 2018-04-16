package com.qingyii.hxtz.database;

import android.database.Cursor;
import android.database.SQLException;

import com.qingyii.hxtz.http.MyApplication;

public class DatiChuangguan {

	/**
	 * 答题闯关数据表增删改查
	 * @return
	 */
	
	
	/**
	 * 查询
	 * @param examinationid
	 * @param paperid
	 * @param flag
	 * @return
	 */
	public static int DatiQuery(int examinationid){
		         int flag_id=0;
		Cursor re=MyApplication.helper.getWritableDatabase().
				rawQuery("select * from chuangguan_info where examination_id="+examinationid+" order by id desc", null);
		if(re.getCount()>0){
			re.moveToFirst();
			for(int i=0;i<re.getCount();i++){
				int examination_id=re.getInt(re.getColumnIndex("examination_id"));								
				if(examination_id==examinationid){
					flag_id=re.getInt(re.getColumnIndex("flag_id"));
					return flag_id;
				}
				
				re.moveToNext();
			}
		}else{
			flag_id=-1;
		}
	
		return flag_id;

	}
	
	/**
	 * 删除
	 */
	 
	public static boolean delteDati(String examinationid){
   	 boolean flag=true;
		String sql="delete from chuangguan_info where examination_id="+examinationid;
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
	 * 答题闯关表添加
	 */
     public static boolean add(String examinationid,String paperid,String flag){
    	 
    	 boolean isflag=true;
    	 if(delteDati(examinationid)){
    		 //1、插入新闻图片表
	 		String sql="insert into chuangguan_info(examination_id,paper_id,flag_id) VALUES("+examinationid+","+paperid+","+flag+")";
	 			try {
	 				MyApplication.helper.getWritableDatabase().execSQL(sql);
	 			} catch (SQLException e) {
	 				// TODO Auto-generated catch block
	 				isflag=false;
	 				e.printStackTrace();
	 			}
	 			
	 			return isflag;
    	 }else{
    		 return false;
    	 }
     }
	
	
}

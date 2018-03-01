package com.zhf.Sqlitehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zhf.Util.Global;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zhf on 2017/10/23.
 */


//草稿箱的本地保存
public class DraftHelper extends SQLiteOpenHelper {
    private static final String SQL_NAME = "Draft.db";
    private static final int Draft_VERSION = 2;
    public DraftHelper(Context context) {
        super(context, Global.phone+SQL_NAME, null,Draft_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

      //草稿箱对应的建表数据
        db.execSQL("create table draft(_taskid integer, name text,summary_title text,a_time text,summary text, " +
                "my_score text, tags text, imgs text, files text, score_text text," +
                "is_show integer, is_show_auditing  integer, show_range integer,is_comment integer, situation text,selectphoto1 text,selectphoto2 text)");
    }

 //增加
  public void addBysql(Drafitbean drafitbean){
     SQLiteDatabase database=getReadableDatabase();
     String tags=chuli(drafitbean.getTags());
      String imgs=chuli(drafitbean.getImgs());
      String files=chuli(drafitbean.getFiles());
     String selectphoto1=chuli(drafitbean.getSelectedPhotos());
      String selectphoto2=chuli(drafitbean.getSelectedPhotos2());
      database.execSQL("insert into draft(_taskid , summary_title ,a_time ,summary ," +
              "my_score , tags , imgs , files , score_text ," +
              "is_show , is_show_auditing  , show_range ,is_comment ,situation,name,selectphoto1,selectphoto2) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
              new Object[]{drafitbean.getTaskid(), drafitbean.getSummary_title()==null?null:drafitbean.getSummary_title(),
                      drafitbean.getA_time()==null?null:drafitbean.getA_time(),drafitbean.getSummary()==null?null:drafitbean.getSummary()
      ,drafitbean.getMy_score(),tags,imgs,files,drafitbean.getScore_text()==null?null:drafitbean.getScore_text(),drafitbean.getIs_show(),drafitbean.getIs_show_auditing()
      ,drafitbean.getShow_range(),drafitbean.getIs_comment(),drafitbean.getSituation()==null?null:drafitbean.getSituation(),drafitbean.getName(),selectphoto1,selectphoto2}
      );
     database.close();


  }
 //查询
    public ArrayList<Drafitbean> getdata(int taskid){
         ArrayList<Drafitbean> list=new ArrayList<>();
        SQLiteDatabase sql=getReadableDatabase();
         Cursor cursor=sql.rawQuery("select * from draft where _taskid="+taskid,null);
        while (cursor.moveToNext()){
            String summary_title=cursor.getString(cursor.getColumnIndex("summary_title"));
            String a_time=cursor.getString(cursor.getColumnIndex("a_time"));
            String summary=cursor.getString(cursor.getColumnIndex("summary"));
            String my_score=cursor.getString(cursor.getColumnIndex("my_score"));
            String tags=cursor.getString(cursor.getColumnIndex("tags"));
            String imgs=cursor.getString(cursor.getColumnIndex("imgs"));
            String files=cursor.getString(cursor.getColumnIndex("files"));
            String score_text=cursor.getString(cursor.getColumnIndex("score_text"));
            String situation=cursor.getString(cursor.getColumnIndex("situation"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String select1=cursor.getString(cursor.getColumnIndex("selectphoto1"));
            String select2=cursor.getString(cursor.getColumnIndex("selectphoto2"));
            ArrayList<String> list1=chuli(tags);
            ArrayList<String> list2=chuli(imgs);
            ArrayList<String> list3=chuli(files);
            ArrayList<String> list4=chuli(select1);
            ArrayList<String> list5=chuli(select2);

            int  is_show=cursor.getInt(cursor.getColumnIndex("is_show"));
            int  is_show_auditing=cursor.getInt(cursor.getColumnIndex("is_show_auditing"));
            int  show_range=cursor.getInt(cursor.getColumnIndex("show_range"));
            int  is_comment=cursor.getInt(cursor.getColumnIndex("is_comment"));
           Drafitbean bean=new Drafitbean();
            bean.setSelectedPhotos(list4);
            bean.setSelectedPhotos2(list5);
            bean.setSummary_title(summary_title);
            bean.setA_time(a_time);
            bean.setSummary(summary);
            bean.setMy_score(my_score);
            bean.setTags(list1);
            bean.setImgs(list2);
            bean.setFiles(list3);
            bean.setScore_text(score_text);
            bean.setSituation(situation);
            bean.setName(name);
            bean.setIs_show(is_show);
            bean.setIs_show_auditing(is_show_auditing);
            bean.setShow_range(show_range);
            bean.setIs_comment(is_comment);
            list.add(bean);
        }
          sql.close();
          return  list;
    }
   //根据id 和name删除
   public void  deleteBySql(int  _taskid,String name){
       int num=getReadableDatabase().delete("draft","_taskid=? and name=?",new String []{_taskid+"",name});

   }

    public void  deleteBySql(int  _taskid){
        int num=getReadableDatabase().delete("draft","_taskid=? ",new String []{_taskid+""});

    }




    public String chuli(ArrayList<String> list){
        StringBuffer str=new StringBuffer();
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                if(i<list.size()-1){
                    str.append(list.get(i)+"~");}
                else {
                    str.append(list.get(i));
                }
            }
            return  str.toString();
        }

        return  "";

    }

    public ArrayList<String> chuli(String string){
           ArrayList<String >  list=new ArrayList<>();
             if(string.contains("~")){
                 String[] str=string.split("~");
                 for(int i=0;i<str.length;i++){
                     list.add(str[i]);
                 }
             }
            return  list;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          switch (oldVersion){
              case 1:
                 db.delete("draft",null,null);
                  db.execSQL("create table draft(_taskid integer, name text,summary_title text,a_time text,summary text, " +
                          "my_score text, tags text, imgs text, files text, score_text text," +
                          "is_show integer, is_show_auditing  integer, show_range integer,is_comment integer, situation text,selectphoto1 text,selectphoto2 text)");
                  break;
          }
    }
}

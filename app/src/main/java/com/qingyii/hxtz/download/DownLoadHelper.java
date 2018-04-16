package com.qingyii.hxtz.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 利用数据库来记录下载信息
 *
 * @author acer
 */
public class DownLoadHelper extends SQLiteOpenHelper {

    private static final String SQL_NAME = "download.db";
    //private static final int DOWNLOAD_VERSION = 1; //old  2016 /9 /23 注释
    private static final int DOWNLOAD_VERSION = 3; //old  2017 /4 /1 注释
//    private static final int DOWNLOAD_VERSION = 4;

    private static final String TAG = "DownloadHelper";

    public DownLoadHelper(Context context) {
        super(context, SQL_NAME, null, DOWNLOAD_VERSION);
    }

    /**
     * 在download.db数据库下创建一个download_info表存储下载信息
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 创建内刊文章内容(XRJ 16.8.20)
         */
        db.execSQL("create table article_info(_id integer, updated_at text, content text)");
        /**
         * 创建通知缓存信息(XRJ 16.10.9)
         * 添加 training_id 培训ID(XRJ 16.10.20)
         * 添加参数 删除 is_del  1已删除 0未删除(XRJ 16.10.11)
         */
        db.execSQL("create table Inform_info(_id integer, _title text, redtitle text, receipt_type text, " +
                "receipt_line integer, content text, receiptnums integer, sendnums integer, attachment text," +
                "needreceipt integer, notifytype text, created_at text, is_receipt integer, receipt_status text," +
                "training_id integer, mark integer)");
        /**
         * 创建公告缓存信息(XRJ 16.10.11)
         * 添加参数 删除 is_del  1已删除 0未删除(XRJ 16.10.11)
         */
        db.execSQL("create table Notice_info(_id integer, _title text, content text, author text, " +
                "readnums integer, attachment text, created_at text, mark integer)");
        /**
         * 创建书籍下载记录表：支付断点多线程下载
         */
        db.execSQL("create table download_info(_id integer PRIMARY KEY AUTOINCREMENT, thread_id integer, start_pos integer, end_pos integer, compelete_size integer,url char)");
        /**
         * 创建书籍缓存信息表
         */
        //2016/09/23 更新增加 字段 file_type  2016//12 修改字段了 shuji_author  shuji_sdcard_url  read_percent => read_nums ,
        //2016/12/23 更新增加 字段 press
        //2016/12/23 更新增加 字段 publishstatus
        //2016/1/9 更新增加 字段 stars
        db.execSQL("create table shuji_info(id integer PRIMARY KEY AUTOINCREMENT," +
                "shuji_photo text, shuji_title text, shuji_name text,shuji_content text," +
                "downLoadurl text,shuji_id text,shuji_sdcard_url text,shuji_author text," +
                "read_percent text,read_nums integer,read_locus text,file_type integer," +
                "press text,stars integer,size integer,publishstatus text," +
                "read_progress integer,total integer)");
        /**
         * 点赞缓存表：comingtype 1文章点赞 2评论点赞
         */
        db.execSQL("create table dianzan_info(id integer PRIMARY KEY AUTOINCREMENT, comingtype integer,username_id integer,article_id integer)");
        /**
         * 创建书籍书签表
         */
        db.execSQL("create table book_mark(id integer PRIMARY KEY AUTOINCREMENT, book_id integer,book_mark_locus text,book_mark_name text)");
        /**
         * 创建书籍批注表
         */
        db.execSQL("create table book_remark(id integer PRIMARY KEY AUTOINCREMENT, book_id integer,book_remark_locus text,book_remark_name text)");

        /**
         * 创建当前闯关表
         */
        db.execSQL("create table chuangguan_info(id integer PRIMARY KEY AUTOINCREMENT, examination_id integer,paper_id integer,flag_id integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        int needupgradeVersion = oldVersion;

        if (1 == needupgradeVersion) {
            db.beginTransaction();
            try {
                upgradeDatabaseToVersion1(db);
                db.setTransactionSuccessful();
            } catch (Throwable ex) {
                Log.e(TAG, ex.getMessage(), ex);
            } finally {
                db.endTransaction();
            }

            db.setTransactionSuccessful();
            db.endTransaction();
        }

        if (2 == needupgradeVersion) {
            db.beginTransaction();
            try {
                upgradeDatabaseToVersion2(db);
                db.setTransactionSuccessful();
            } catch (Throwable ex) {
                Log.e(TAG, ex.getMessage(), ex);
            } finally {
                db.endTransaction();
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }

//        if (3 == needupgradeVersion) {
//            db.beginTransaction();
//            try {
//                upgradeDatabaseToVersion3(db);
//                db.setTransactionSuccessful();
//            } catch (Throwable ex) {
//                Log.e(TAG, ex.getMessage(), ex);
//            } finally {
//                db.endTransaction();
//            }
//            db.setTransactionSuccessful();
//            db.endTransaction();
//        }
    }

    private void upgradeDatabaseToVersion1(SQLiteDatabase db) {  // 2016/9/23

        // Create table C
        String sql = "ALTER TABLE shuji_info ADD COLUMN file_type INTEGER";
        //db.execSQL("");
        db.execSQL(sql);
        //所有以前的书都为 1  epub
        String update = "UPDATA shuji_info SET file_type = 1 WHERE (id =0 OR id >0) ";
        db.execSQL(update);

    }


    private void upgradeDatabaseToVersion2(SQLiteDatabase db) {  // 2016/9/23

        // Create table C
        String sql = "ALTER TABLE shuji_info ADD COLUMN shuji_sdcard_url text";
        //db.execSQL("");
        db.execSQL(sql);
        // Create table C
        String sql2 = "ALTER TABLE shuji_info ADD COLUMN read_nums INTEGER";
        //db.execSQL("");
        db.execSQL(sql2);
        String sql3 = "ALTER TABLE shuji_info ADD COLUMN shuji_author text";
        //db.execSQL("");
        db.execSQL(sql3);

        //所有以前的书都为 1  epub

        //String update = "UPDATA shuji_info SET file_type = 1 WHERE (id =0 OR id >0) ";

        //db.execSQL(update);

    }

//    private void upgradeDatabaseToVersion3(SQLiteDatabase db) {  // 2017/4/1
//
//        // Create table C
//        String sql1 = "ALTER TABLE Inform_info ADD COLUMN is_del INTEGER DEFAULT 0";
//        String sql2 = "ALTER TABLE Notice_info ADD COLUMN is_del INTEGER DEFAULT 0";
//        //db.execSQL("");
//        db.execSQL(sql1);
//        db.execSQL(sql2);
//        //所有以前的书都为 1  epub
////        String update = "UPDATA shuji_info SET file_type = 1 WHERE (id =0 OR id >0) ";
////        db.execSQL(update);
//
//    }
}

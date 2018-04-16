package com.qingyii.hxtz.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.pojo.BookMark;

import java.util.ArrayList;

/**
 * 书籍缓存sqlite数据库操作类
 *
 * @author shelia
 */
public class BookDB {
    static SQLiteDatabase sdb = MyApplication.helper.getWritableDatabase();

    /**
     * 记录书籍阅读进度信息
     *
     * @param bookid
     * @param read_componentId
     * @param read_percent
     * @return
     */
    public static boolean updateBookRead(String bookid, String read_componentId, String read_percent, String read_locus) {
        boolean flag = true;
        String sql = "update shuji_info set read_componentId='" + read_componentId + "',read_percent='" + read_percent + "',read_locus='" + read_locus + "' where shuji_id=" + bookid;
        try {
            MyApplication.helper.getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 添加书签
     *
     * @param bookid
     * @param book_mark_locus
     * @param book_mark_name
     * @return
     */
    public static boolean addBookMark(String bookid, String book_mark_locus, String book_mark_name) {
        boolean flag = true;
        String sql = "insert into book_mark(book_id,book_mark_locus,book_mark_name) VALUES('" + bookid + "','" + book_mark_locus + "','" + book_mark_name + "')";
        try {
            MyApplication.helper.getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 获取指定书籍的书签列表
     *
     * @param bookid
     * @return
     */
    public static ArrayList<BookMark> getBookMarkList(String bookid) {
        ArrayList<BookMark> bmList = new ArrayList<BookMark>();
        Cursor re = MyApplication.helper.getWritableDatabase().
                rawQuery("select * from book_mark where book_id=" + bookid, null);
        if (re.getCount() > 0) {
            re.moveToFirst();
            for (int i = 0; i < re.getCount(); i++) {
                BookMark bm = new BookMark();
                bm.setId(re.getString(re.getColumnIndex("id")));
                bm.setBookId(re.getString(re.getColumnIndex("book_id")));
                bm.setBookMarkLoucs(re.getString(re.getColumnIndex("book_mark_locus")));
                bm.setBookMarkName(re.getString(re.getColumnIndex("book_mark_name")));
                bmList.add(bm);
                re.moveToNext();
            }
        }
        return bmList;
    }

    /**
     * 添加批注
     *
     * @param bookid
     * @param book_mark_locus
     * @param book_mark_name
     * @return
     */
    public static boolean addBookRemark(String bookid, String book_mark_locus, String book_mark_name) {
        boolean flag = true;
        String sql = "insert into book_remark(book_id,book_remark_locus,book_remark_name) VALUES('" + bookid + "','" + book_mark_locus + "','" + book_mark_name + "')";
        try {
            MyApplication.helper.getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 获取指定书籍的批注列表
     *
     * @param bookid
     * @return
     */
    public static ArrayList<BookMark> getBookRemarkList(String bookid) {
        ArrayList<BookMark> bmList = new ArrayList<BookMark>();
        Cursor re = MyApplication.helper.getWritableDatabase().
                rawQuery("select * from book_remark where book_id=" + bookid, null);
        if (re.getCount() > 0) {
            re.moveToFirst();
            for (int i = 0; i < re.getCount(); i++) {
                BookMark bm = new BookMark();
                bm.setId(re.getString(re.getColumnIndex("id")));
                bm.setBookId(re.getString(re.getColumnIndex("book_id")));
                bm.setBookMarkLoucs(re.getString(re.getColumnIndex("book_remark_locus")));
                bm.setBookMarkName(re.getString(re.getColumnIndex("book_remark_name")));
                bmList.add(bm);
                re.moveToNext();
            }
        }
        return bmList;
    }

    /**
     * 清空书籍下载表及书籍缓存表
     *
     * @return
     */
    public static boolean chearBook() {
        boolean flag = true;
        String sql1 = "delete from shuji_info";
        String sql2 = "delete from download_info";
        String sql3 = "delete from book_mark";
        String sql4 = "delete from book_remark";
        try {
            //开始事物
            sdb.beginTransaction();
            sdb.execSQL(sql1);
            sdb.execSQL(sql2);
            sdb.execSQL(sql3);
            sdb.execSQL(sql4);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            flag = false;
            e.printStackTrace();
        }
        //结束事物
        if (flag) {
            sdb.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交
            sdb.endTransaction();
        }
        return flag;
    }

    /**
     * VACUUM 命令通过复制主数据库中的内容到一个临时数据库文件，然后清空主数据库，并从副本中重新载入原始的数据库文件。这消除了空闲页，把表中的数据排列为连续的，另外会清理数据库文件结构
     *
     * @return
     */
    public static boolean VACUUMBook() {
        boolean flag = true;
        try {
            sdb.execSQL("VACUUM shuji_info");
            sdb.execSQL("VACUUM download_info");
            sdb.execSQL("VACUUM book_mark");
            sdb.execSQL("VACUUM book_remark");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /*
     * 删除书籍
     * */
    public static boolean deleteBookRead(String bookid) {
        boolean flag = true;
        String sql = "delete from shuji_info where shuji_id='" + bookid + "'";
        try {
            MyApplication.helper.getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }


    public static boolean updateFilename(String bookid, String filename) {
        boolean flag = true;
        String sql = "update shuji_info set shuji_sdcard_url='" + filename + "',downLoadurl='" + filename + "' where shuji_id='" + bookid + "'";
        try {
            MyApplication.helper.getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }


}

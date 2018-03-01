package com.qingyii.hxt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by XRJ on 16/9/9.
 */

public class DateUtils {

    /**
     * 获取系统时间 格式为：long
     */
    public static long getDateLong() {
        Date d = new Date();
        return d.getTime();
    }

    /**
     * 获取系统时间 格式为："yyyy/MM/dd "
     */
    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        return sf.format(d);
    }

    /**
     * 时间戳转换成字符窜 格式为："yyyy-MM-dd HH:mm"
     */
    public static String getDateToLongString(long time) {
        Date d = new Date(time * 1000);

//        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String stime = sf.format(d);

        return stime;
    }

    /**
     * 时间戳转换成字符窜 格式为："yyyy-MM-dd"
     */
    public static String getDateToString(long time) {
        Date d = new Date(time * 1000);
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToDate(String time) {
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToShortDate(String time) {
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToLongDate(String time) {
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd  hh:mm");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToLong2Date(String time) {
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd  hh:mm");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
}

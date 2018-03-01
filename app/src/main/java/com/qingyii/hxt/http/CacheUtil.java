package com.qingyii.hxt.http;


import com.qingyii.hxt.pojo.User;

/**
 * 缓存类
 *
 * @author wmh
 */
public class CacheUtil {
    /**
     * 当接口返回499时
     */
    public static String logout = "登录超时";

    public static String imei = "";
    /**
     * 用户账号
     */
    public static String userName = "";
    /**
     * 主页标题
     */
    public static String hometitle = "";
    /**
     * 用户ID
     */
    public static int userid = 0;
    /**
     * 用户对像
     */
    public static User user = null;

    public static long timeOffset = 0;


}

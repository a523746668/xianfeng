package com.qingyii.hxtz.base.app;

/**
 * Created by xubo on 2017/7/5.
 */

public class GlobalConsts {
    /**
     * 用户组id
     */
    public static final String ACCOUNT_ID = "account_id";

    public static final String MAGAZINE = "magazine";          //内刊模块
    public static final String BOOK = "book";              //书籍模块
    public static final String EXAMS = "exams";             //考试模块
    public static final String NOTIFY = "notify";            //通知模块
    public static final String ANNOUNCEMENT = "announcement";      //公告模块
    public static final String TRAIN = "train";             //培训模块
    public static final String DOCUMENTARY = "documentary";//同事圈与纪实模块
    public static final String MEETING = "meeting";
    public static final String WMCJ = "wmcj";
    public static final String DJFDY = "djfdy";
    public static final String ACTIVITY = "activity";
    public static final String MORE = "more";
    public static final String MY = "my";
    public static final String CIRCLE = "circle";
    /**
     * 写死数据，部门id为135不显示内容
     */
    public static final String[] HIDE_MODULES = {
            WMCJ, DJFDY, MORE  // "activity",
    };
    /**
     * 写死数据，部门id为135显示内容
     */
    public static final String[] SHOW_MODULES = {
            BOOK, EXAMS, ANNOUNCEMENT, DOCUMENTARY,CIRCLE
    };
    /**
     * 书籍进度
     */
    public static final String BOOK_PROGRESS = "book_progress";
    public static final String BOOK_CURRENT = "book_current";
    /**
     * 培训相关
     */
    public static final String TRAIN_ID = "trainId";
    /**
     * 会议相关
     */
    public static final String ISPUBLISH = "is_publish";
    public static final String TITLE = "title";

    //点击新闻界面的子item跳转详细内容的weburl
    public static final String ITEM_WEBURL="item_weburl";
}

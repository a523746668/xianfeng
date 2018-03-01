package com.qingyii.hxt.base.app;

/**
 * Created by xubo on 2017/6/21
 */
public interface   EventBusTags {


    enum UpdateType{
        no,del,add
    }

    String NOTIFY = "notify";
    String NOTICE = "notice";
    String MEETING = "meeting";
    String NOTIFY_SYSTEM = "notify_system";
    String NOTIFY_TRAIN = "notify_train";
    String HOME = "home";
    String SIGN_IN = "sign_in";
    String BOOK_PROGRESS = "book_progress";
    String DOWNLOAD = "download";
    String WORKPARK = "workpark";


    int UPDATE_NOTIFY_SIGN_IN = 10001;
    int UPDATE_NOTIFY_RETURN = 10002;
    int UPDATE_NOTIFY_RETURN_LIST = 10003;
    int ALREAY_SIGN_IN = 10006;



    int UPDATE_HOME_MEETING_COUNT_ADD = 10100;
    int UPDATE_HOME_MEETING_COUNT_DEL = 10101;
    int UPDATE_HOME_NOTIFY_COUNT_DEL = 10111;



    int UPDATE_MEETING_FEEDBAK_JOIN = 10200;
    int UPDATE_MEETING_FEEDBAK_LEAVE = 10201;
    int UPDATE_MEETING_SUMMARY_FINISH = 10250;
    int UPDATE_MEETING_LIST_SUMMARY_FINISH = 10251;

    int UPDATE_BOOK_READ_PROGRESS  = 10300;


    int DOWNLOAD_FINISH = 10400;
    int UPDATE_NOTICE_DETAILS = 10501;
    /**
     * 工作动态专题
     */
    int WORKPARK_TOPIC = 10600;
}

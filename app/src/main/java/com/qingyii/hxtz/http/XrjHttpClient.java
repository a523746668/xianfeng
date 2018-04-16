package com.qingyii.hxtz.http;

/**
 * Created by Administrator on 2016/8/19.
 */
public class XrjHttpClient {
    public static final String HEADER_API_VERSION = "Authorization:"+MyApplication.hxt_setting_config.getString("credentials", "");
    /**
     * 版本信息
     */
    public static final String ACCEPT_V2 = "application/vnd.xianfeng.v2+json";
    public static final String ACCEPT_V1 = "application/vnd.xianfeng.v1+json";
    /**
     * 请求Url前缀
     */
    public static final String URL_PR = "http://web.seeo.cn";
    //public static final String URL_PR="http://192.168.0.100" ;

    /**
     * 上传Url前缀
     */
   public static final String URL_UP = "http://admin.seeo.cn";
  // public static final String URL_UP = "http://192.168.0.100";//贤哥测试


    /**
     * 考试相关
     */

    public static final String PREPARE_EXAM_URL = "/examination/";


    /**
     * 全局
     *
     *
     */

    /**
     * 广告 URL
     */
    public static final String ADVERTISEMENT_URL = "/advertisement";

    public static String getAdvertisementUrl() {
        return URL_PR + ADVERTISEMENT_URL;
    }

    /**
     * 点赞 URL
     */
    public static final String UPVOTE_URL = "/upvote";

    public static String getUpvoteUrl() {
        return URL_PR + UPVOTE_URL;
    }

    /**
     * 评论 URL
     */
    public static final String COMMENT_URL = "/comment/post";

    public static String getCommentUrl() {
        return URL_PR + COMMENT_URL;
    }

    /**
     * 获取验证码 URL
     */
    public static final String GAINCODE_URL = "/sendcode";

    public static String getGainCodeUrl() {
        return URL_PR + GAINCODE_URL;
    }

    /**
     * 验证验证码 URL
     */
    public static final String VERIFYCODE_URL = "/verifycode";

    public static String getVerifyCodeUrl() {
        return URL_PR + VERIFYCODE_URL;
    }

    /**
     * 修改密码 URL
     */
    public static final String CHANGEPASSWORD_URL = "/changepassword";

    public static String getChangePasswordUrl() {
        return URL_PR + CHANGEPASSWORD_URL;
    }


    /**
     * 我的相关
     *
     *
     */

    /**
     * 已收藏的文章、内刊、书籍 URL
     */
    public static final String COLLECTED_URL = "/user/collected";

    public static String getCollectedUrl() {
        return URL_PR + COLLECTED_URL;
    }

    /**
     * 我的通讯录URL
     */
    public static final String ADDRESS_LIST_URL = "/users";

    public static String getAddressListUrl() {
        return URL_PR + ADDRESS_LIST_URL;
    }


    /**
     * 登录相关
     *
     *
     */

    /**
     * Login请 求URL
     */
    public static final String LOGIN_URL = "/auth/login";

    public static String getLoginUrl() {
       return URL_PR + LOGIN_URL;
      // return  "http://192.168.0.100/auth/login" ;

    }

    /**
     * 修改密码 求URL
     */
    public static final String ALTERPWD_URL = "/resetpassword";

    public static String getAlterPwdUrl() {
        return URL_PR + ALTERPWD_URL;
    }

    /**
     * User信息修改 请求URL
     */
    public static final String USERUPDATE_URL = "/user/edit";

    public static String getUserUpdateUrl() {
        return URL_PR + USERUPDATE_URL;
    }

    /**
     * User 请求
     */
    public static final String USER_URL = "/user/show";

    public static String getUserUrl() {
        return URL_PR + USER_URL;
    }

    /**
     * OtherUser 请求
     */
    public static final String OTHERUSER_URL = "/user/";

    public static String getOtherUserUrl() {
        return URL_PR + OTHERUSER_URL;
    }

    /**
     * getUserEditUrl 请求URL
     */
    public static final String USEREDIT_URL = "/user/edit";

    public static String getUserEditUrl() {
        return URL_PR + USEREDIT_URL;
    }

    /**
     * Avatar 请求URL
     */
    public static final String AVATAR_URL = "/api/upload/avatar";

    public static String getAvatarUrl() {
        return URL_UP + AVATAR_URL;
    }


    /**
     * 内刊相关
     *
     *
     */

    /**
     * UnSubscribedNK 请求URL
     */
    public static final String NK_UNSUBSCRIBED_URL = "/magazine";

    public static String getNKUnSubscribedUrl() {
        return URL_PR + NK_UNSUBSCRIBED_URL;
    }

    /**
     * SubscribedNK 请求URL
     */
    public static final String NK_SUBSCRIBED_URL = "/user/subscribed";

    public static String getNKSubscribedUrl() {
        return URL_PR + NK_SUBSCRIBED_URL;
    }

    /**
     * Subscribed 请求URL
     */
    public static final String SUBSCRIBED_URL = "/user/subscribe";

    public static String getSubscribedUrl() {
        return URL_PR + SUBSCRIBED_URL;
    }

    /**
     * StickSubscribed 请求URL
     */
    public static final String STICKSUBSCRIBED_URL = "/magazine/settop";

    public static String getStickSubscribedUrl() {
        return URL_PR + STICKSUBSCRIBED_URL;
    }

    /**
     * UnSubscribed 请求URL
     */
    public static final String UNSUBSCRIBED_URL = "/user/unsubscribe";

    public static String getUnSubscribedUrl() {
        return URL_PR + UNSUBSCRIBED_URL;
    }

    /**
     * AxpectNK 请求URL
     */
    public static final String NK_AXPECT_URL = "/issue";

    public static String getNKAxpectUrl() {
        return URL_PR + NK_AXPECT_URL;
    }

    /**
     * ArticleListNK 请求URL
     */
    public static final String NK_ARTICLELIST_URL = "/article";

    public static String getNKArticleListUrl() {
        return URL_PR + NK_ARTICLELIST_URL;
    }

    /**
     * ArticleNK 请求URL
     */
    public static final String NK_ARTICLE_URL = "/article";

    public static String getNKArticleUrl() {
        return URL_PR + NK_ARTICLE_URL;
    }

    /**
     * VoteNK 请求URL
     */
    public static final String NK_VOTE_URL = "/vote";

    public static String getNKVoteUrl() {
        return URL_PR + NK_VOTE_URL;
    }

    /**
     * GetComment 请求URL
     */
    public static final String NK_COMMENT_URL = "/comment";

    public static String getNKCommentUrl() {
        return URL_PR + NK_COMMENT_URL;
    }


    /**
     * 书籍相关
     *
     *
     */

    /**
     * Books 请求URL
     */
    public static final String SJ_BOOKS_URL = "/book";

    public static String getBooksUrl() {
        return URL_PR + SJ_BOOKS_URL;
    }

    /**
     * BooksCategory 请求URL
     */
    public static final String SJ_BOOKSCATEGORY_URL = "/category";

    public static String getBooksCategoryUrl() {
        return URL_PR + SJ_BOOKS_URL + SJ_BOOKSCATEGORY_URL;
    }

    /**
     * BooksSubject 请求URL
     */
    public static final String NK_BOOKSSUBJECT_URL = "/subject";

    public static String getBooksSubjectUrl() {
        return URL_PR + SJ_BOOKS_URL + NK_BOOKSSUBJECT_URL;
    }


    /**
     * 考试相关
     *
     *
     */

    /**
     * 个人考试结果
     */

    public static final String NK_EXAMRESULT_URL = "/examinationresult";

    public static String getExamResultUrl(String examid) {
        return URL_PR + NK_EXAMRESULT_URL + "/" + examid + "/record";
    }


    /**
     * 考试排行 pesonal 个人  department 单位
     */

    public static final String NK_RANKING_PERSONAL = NK_EXAMRESULT_URL + "/personal";

    public static String getExamRankingPersonalUrl() {
        return URL_PR + NK_RANKING_PERSONAL;
    }

    public static final String NK_RANKING_DEPARTMENT = NK_EXAMRESULT_URL + "/department";

    public static String getExamRankingDepartmentUrl() {
        return URL_PR + NK_RANKING_DEPARTMENT;
    }


    /**
     * ExamList 请求URL
     */
    public static final String NK_EXAMLIST_URL = "/examination";

    public static String getExamListUrl() {
        return URL_PR + NK_EXAMLIST_URL;
    }


    /**
     * 通知相关
     *
     *
     */

    /**
     * InformList 请求URL
     */
    public static final String TZ_INFORMLIST_URL = "/notify";

    public static String getInformListUrl() {
        return URL_PR + TZ_INFORMLIST_URL;
    }

    /**
     * TrainSchedule 请求URL
     */
    public static final String TZ_NOTICELIST_URL = "/announcement";

    public static String getNoticeListUrl() {
        return URL_PR + TZ_NOTICELIST_URL;
    }

    /**
     * CircleMark 请求URL
     */
    public static final String TZ_CIRCLEMARK_URL = "/message/count";

    public static String getCircleMarkUrl() {
        return URL_PR + TZ_CIRCLEMARK_URL;
    }


    /**
     * 培训相关
     *
     *
     */

    /**
     * TrainList 请求URL
     */
    public static final String PX_TRAINLIST_URL = "/training";

    public static String getTrainListUrl() {
        return URL_PR + PX_TRAINLIST_URL;
    }
    /**
     * MeetingMap 请求URL
     */
    public static final String PX_MEETING_URL = "/meeting";

    public static String getMeetingMapUrl() {
        return URL_PR + PX_MEETING_URL;
    }


    /**
     * 同事圈相关
     *
     *
     */

    /**
     * Associates 请求URL
     */
    public static final String GZQ_ASSOCIATES_URL = "/documentary";

    public static String getAssociatesUrl() {
        return URL_PR + GZQ_ASSOCIATES_URL;
    }

    public static final String CIRCLE_REPORT_URL = "/report";
    public static  String getReportUrl(){

        return URL_UP + "/api" + CIRCLE_REPORT_URL;
    }

    /**
     * AssociatesList 请求URL
     */
    public static final String GZQ_ASSOCIATESLIST_URL = "/documentary/company";

    public static final String SHIGUANGZHOU = "/documentary/";//时光轴

    public static String getAssociatesListUrl() {
        return URL_PR + GZQ_ASSOCIATESLIST_URL;
    }

    public static String getShiguangzhouListUrl() {

        return URL_PR + SHIGUANGZHOU;
    }

    /**
     * Category 请求URL
     */
    public static final String GZQ_CATEGORY_URL = "/documentary/category";

    public static String getCategoryUrl() {
        return URL_PR + GZQ_CATEGORY_URL;
    }

    /**
     * CirclePost 请求URL
     */
    public static final String GZQ_CIRCLEPOST_URL = "/documentary/post";

    public static String getCirclePostUrl() {
        return URL_PR + GZQ_CIRCLEPOST_URL;
    }

    /**
     * UploadPictures 请求URL
     */
    public static final String GZQ_UPLOADPICTURES_URL = "/api/upload/picture";

    public static String getUploadPicturesUrl() {
        return URL_UP + GZQ_UPLOADPICTURES_URL;
    }

    /**
     * UploadReport 请求URL
     */
    public static final String GZQ_UPLOADREPORT_URL = "/documentary/report";

    public static String getUploadReportUrl() {
        return URL_PR + GZQ_UPLOADREPORT_URL;
    }


    /**
     * 纪实管理相关
     *
     *  /documentarycheck/rank/
     */

    /**
     * DocumentaryMy 请求URL
     */
    public static final String JS_DOCUMENTARYMY_URL = "/documentarycheck/my/";

    public static String getDocumentaryMyUrl() {
        return URL_PR + JS_DOCUMENTARYMY_URL;
    }

    /**
     * DocumentaryStatus 请求URL
     */
    public static final String JS_DOCUMENTARYSTATUS_URL = "/documentarycheck/status/";

    public static String getDocumentaryStatusUrl() {
        return URL_PR + JS_DOCUMENTARYSTATUS_URL;
    }

    /**
     * RankingList 请求URL
     */
    public static final String JS_RANKINGLIST_URL = "/documentarycheck/rank/";

    public static String getRankingListUrl() {
        return URL_PR + JS_RANKINGLIST_URL;
    }

    /**
     * WaitAffirmList 请求URL
     */
    public static final String JS_WAITAFFIRMLIST_URL = "/documentarycheck/checklog/";

    public static String getWaitAffirmListUrl() {
        return URL_PR + JS_WAITAFFIRMLIST_URL;
    }


    /**
     * ManageList 请求URL
     */
    public static final String JS_MANAGELIST_URL = "/documentarycheck";

    public static String getManageListUrl() {
        return URL_PR + JS_MANAGELIST_URL;
    }

    /**
     * UserContext 请求URL
     */
    public static final String JS_USERCONTEXT_URL = "/documentary";

    public static String getUserContextUrl() {
        return URL_PR + JS_USERCONTEXT_URL;
    }

}

package com.qingyii.hxt.http;

import com.qingyii.hxt.util.SDUtil;

/**
 * 所有HTTP服务请求路径缓存
 * 
 * @author wmh
 * 
 */
public class HttpUrlConfig {
	// public static String BASE_URL = "http://192.168.1.150/HXT/";
	// // 图片缓存目录:放在tomcat根路径下
	// public static String photoDir = "http://192.168.1.150/HXT/";
//	public static String BASE_URL = "http://www.hongxin001.cn:8080/HXT/";
//	public static String photoDir = "http://www.hongxin001.cn:8080/HXT/";

	 public static String BASE_URL = "http://121.199.57.203:8080/HXT/";
	 public static String photoDir = "http://121.199.57.203:8080/HXT/";

//	 public static String BASE_URL = "http://192.168.1.199:8080/HXT/";
//	 public static String photoDir = "http://192.168.1.199:8080/HXT/";
//	
//	 public static String BASE_URL = "http://192.168.2.237:8080/HXT/";
//	 public static String photoDir = "http://192.168.2.237:8080/HXT/";
	
	// 新浪微博账号 ：xjrad@163.com qingyi123
	// 微信开放平台：xjrad@hotmail.com qingyi123
	// 百度：xjrad Q号
	// wmh:BmL1I7OUXgk1GaOUci2wrSsM
	// 百度API key:oD25tUuWWspsiWXG33pmWRPv
	// 百度Secret Key：xXHsWZ2XtugDi4czRcUZ1Ig9C4B3iGRo
	public static String baiduApiKey = "oD25tUuWWspsiWXG33pmWRPv";
	// 新浪微博云中原app key 旧：2824613388 新：520262367
	public static String sinaAppKey = "520262367";
	// 微信 app key 旧：wxcc2c65783c6ec0d0 新：wx3c28665ac58e2e17
	public static String weixinAppKey = "wx3c28665ac58e2e17";
	// 腾讯微博云中原app key
	public static String qqWeiboAppKey = "";
	// QQ空间云中原app key
	public static String qzoneAppKey = "";
	// 人人网云中原app key
	public static String renrenAppKey = "";
	// 百度云中原app key
	public static String baiduAppKey = "";
	// 图片缓存sd卡目录
	public final static String FILE_NAME = "/HXT";
	// 图片缓存SD卡全路径
	public final static String cacheDir = SDUtil.getSDPath() + FILE_NAME;
	/**
	 * queryDiscussList.do ----评论列表查询接口
	 */
	public static String queryDiscussList = "admin/queryDiscussList.do";

	/**
	 * 广告列表接口
	 */
	public static String queryAdvertList = "admin/queryAdvertList.do";

	/**
	 * queryDiscussByTreeList2.do----书籍评论列表接口
	 */
	public static String queryDiscussList2 = "admin/queryDiscussByTreeList2.do";

	/**
	 * 评论点赞接口
	 */

	public static String pinglundianzan = "admin/updateDiscuss.do";

	// /**
	// * updateDiscuss.do 点赞仍鸡蛋接口
	// */
	// public static String addXianhua = "admin/updateDiscuss.do";
	/**
	 * queryDiscussByTreeList.do ---评论树，目前只有两级
	 */
	public static String pinglunshu = "admin/queryDiscussByTreeList.do";
//	/**
//	 * querySpecialList.do ---专题列表
//	 */
//	public static String getzhuantiList = "admin/querySpecialList.do";
	/**
	 * queryMagazineList.do ----查询杂志列表信息
	 */
	public static String queryMagazineList = "admin/queryMagazineList2.do";
	/**
	 * addSubscribe.do ----订阅杂志
	 */
	public static String dymagazine = "admin/addSubscribe.do";
	/**
	 * querySubscribeList.do ---已订阅列表
	 */
	public static String querydingyueMagazineList = "admin/querySubscribeList.do";
	/**
	 * deleteSubscribe.do ----取消订阅
	 */
	public static String tuiding = "admin/deleteSubscribe.do";
	/**
	 * topSubscribe.do ------置顶订阅杂志
	 */
	public static String zhiding = "admin/topSubscribe.do";
	/**
	 * queryPeriodsList.do ---杂志期数列表查询，根据杂志ID查询
	 */
	public static String queryPeriodsList = "admin/queryPeriodsList.do";
	/**
	 * addDiscuss.do ---添加评论接口
	 */
	public static String addDiscuss = "admin/addDiscuss.do";
//	/**
//	 * queryBooktypeList.do 分类列表
//	 */
//	public static String getfenleiList = "admin/queryBooktypeList.do";
	/**
	 * queryBookTypeRelaList.do 分类里书籍列表
	 */
	public static String getfenleiBookList = "admin/queryBookTypeRelaList.do";
	/**
	 * queryBookList.do ----书籍列表
	 */
	public static String shujiList = "admin/queryBookList.do";
	/**
	 * queryBookList1.do ----书籍最新列表
	 */
	public static String queryBookList1 = "admin/queryBookList1.do";
	/**
	 * queryBookList3.do ----书籍推荐列表
	 */
	public static String queryBookList3 = "admin/queryBookList3.do";
	/**
	 * queryBooktypeList.do ----书籍分类列表
	 */
	public static String queryBooktypeList = "admin/queryBooktypeList.do";
	/**
	 * queryAllBookTypeRelaByParam.do ----书籍分类里列表
	 */
	public static String queryAllBookTypeRelaByParam = "admin/queryAllBookTypeRelaByParam.do";
	/**
	 * querySpecialList.do ----书籍专题列表
	 */
	public static String querySpecialList = "admin/querySpecialList.do";
	/**
	 * querySpecialBookRelaList.do ---专题里面书籍列表 根据专题ID
	 */
	public static String querySpecialBookRelaList = "admin/querySpecialBookRelaList.do";
	/**
	 * 
	 queryChapterList 章节内容查询，可根据bookid查询本书的所有章节内容，也可根据章节ID，查询单章内容信息
	 */
	public static String queryChapterList = "admin/queryChapterList.do";
	/**
	 * /** queryCatalogList.do ---书籍目录查询。传书籍ID
	 */
	public static String shujiMulu = "admin/queryCatalogList.do";
	/**
	 * queryExaminationPapersList.do ----考卷列表，根据考试ID查询
	 */
	public static String queryExaminationPapersList = "mobile/queryExaminationPapersList.do";
	
	/**
	 * queryPagersQuestionRelaList.do ---没有分组的     考题查询。 通过考卷ID查询，查询一张考卷下面的所有考题
	 */
	public static String queryPagersQuestionRelaList = "mobile/queryPagersQuestionRelaList.do";
	/**
	 * queryPagersQuestionRelaList.do ---已分组    考题查询。 通过考卷ID查询，查询一张考卷下面的所有考题
	 */
	
	public static String queryPagersQuestionRelaListByRedios = "admin/queryPagersQuestionRelaListByRedios.do";
	
	/**
	 * queryPagersQuestionRelaList.do ---已分组    最新考题查询。 通过考卷ID查询，查询一张考卷下面的所有考题
	 */
//	public static String queryPagersQuestionRelaList2 = "admin/queryPagersQuestionRelaList2.do";
		
	/**
	 * queryExaminationList.do -查询考试列表
	 */
	public static String queryExaminationList = "mobile/queryExaminationList.do";
	/**
	 * queryPeriodsArticleRelaList.do ----某期包含文章查询，根据期数ID
	 */
	public static String queryPeriodsList1 = "admin/queryPeriodsArticleRelaList.do";
//	/**
//	 * querySpecialBookRelaList.do ---专题里面书籍列表 根据专题ID
//	 */
//	public static String getzhuantibookList = "admin/querySpecialBookRelaList.do";
	/**
	 * addScore.do ----考试成绩提交接口
	 */
	public static final String addScore = "mobile/addScore.do";
	/**
	 * addUser.do ---注册用户
	 */
	public static final String addUser = "addUser.do";
	/**
	 * loginUser.do ----用户登陆
	 */
	public static final String loginUser = "loginUser.do";
	public static final String loginOut = "logoutUser.do";
	/**
	 * queryScoreList.do ----个人成绩排行(包括考试。。。)
	 */
	public static final String queryScoreList = "mobile/queryScoreList.do";
	/**
	 * addCollection.do 收藏接口
	 */
	public static final String shoucang = "admin/addCollection.do";
	/**
	 * deleteCollection.do --取消收藏
	 */
	public static final String cancelshoucang = "admin/deleteCollection.do";
	/**
	 * queryCollectionList.do ----传文章ID和用户ID，判断是否收藏过
	 */
	public static final String queryshoucang = "admin/queryCollectionList.do";

	/**
	 * queryIntegralLogsList.do ---查询积分
	 */
	public static final String getMyjifen = "admin/queryIntegralLogsList.do";

	/**
	 * queryCollectionList.do--我的收藏列表 参数flag：1--查询收藏过的杂志，2-查询收藏过的书籍，3-查询收藏过的文章
	 */
	public static final String shoucList = "admin/queryCollectionList.do";
	/**
	 * updateUser.do 修改密码
	 */
	public static final String changepassWord = "admin/updateUser.do";
	/**
	 * updateArticle.do 增加文章点赞数
	 */
	public static final String adddianzanshu = "admin/praiseArticle.do";

	/**
	 * queryVoteList.do 获取内刊投票信息接口
	 */
	public static final String toupiaoinfo = "admin/queryVoteList.do";
	/**
	 * addVoteUserRela.do ---投票接口
	 */
	public static final String vote = "mobile/addVoteUserRela.do";
	/**
	 * 考卷接口 queryExaminationPaperPassword (单次命题传密码 闯关传考卷id)
	 */
	public static final String queryExaminationPaperPassword = "mobile/queryExaminationPaperPassword.do";
	/**
	 * queryAllJobAndCompany.do 查询出所有的公司、部门、职位信息
	 */
	public static final String queryAllgsibmzw = "admin/queryAllJobAndCompany.do";
	/**
	 * uploadUserImage.do ----上传用户头像，传用户ID，和头像图片文件流
	 */
	public static final String uploadUserImage = "admin/uploadUserImage.do";
	/**
	 * gainCode.do ---红信通的短信接口
	 */
	public static final String gainCode="gainCode.do";
	/**
	 * checkPhone.do -----红信通第一次登陆/忘记密码的修改验证码接口
	 */
	public static final String checkPhone="checkPhone.do";
	/**
	 * 单位成绩排名接口
	 */
	public static final String queryScoreMaxList="mobile/queryScoreMaxList.do";
	
	/**
	 * 我的某次考试所有成绩接口
	 */
	public static final String queryScoreList2="mobile/queryScoreList2.do";
	/**
	 * 单位成绩排行点击进去的xx单位的考试成绩接口
	 */
	public static final String queryScoreAllCompanyList="mobile/queryScoreAllCompanyList.do";
	
	/**
	 * 个人成绩排行点击进去的xx的考试成绩接口
	 */
	public static final String queryScoreAllList="mobile/queryScoreAllList.do";
	
	/**
	 * 修改密码，不需要登录
	 */
	public static final String updateUser ="updateUser.do";
	/**
	 * 添加文章阅读次数  参数：articleid
	 */
	public static final String updateArticl="admin/addarticleReadCount.do";
	/**
	 * 添加书籍阅读次数 参数：bookid
	 */
	public static final String addReadCount="admin/addReadCount.do";
	/**
	 * addSuggestions.do---添加反馈接口
	 */
	public static final String addSuggestions="admin/addSuggestions.do";
	
	
	/**
	 * 同事圈
	 */
	 /**
	  * 主页动态列表
	  * page
	  * pagesize
	  * userid
	  * type (0,本单位的动态；1,本部门的动态; 2,我关注的动态; 3,我自己的动态(我自己的动态不需要下发评论列表))
	  */
	public static final String reqDynamicInfoList = "admin/reqDynamicInfoList.do";//colleaguecircle/
	/**
	 * 举报
	 * userid
	 * dynamicinfoid
	 * commentid =0,表示只对动态点赞
	 */
	public static final String reqCreateReport = "admin/reqCreateReport.do";
	
	/**
	 * 创建动态
	 * userid
	 * contenttxt
	 * contentimageurllist string[] max is 9
	 * typeid 类别id(工作，生活等)
	 * location lng lat address
	 * permission 权限（0,公开; 1,私密）
	 */
	public static final String reqCreateDynamicInfo = "admin/reqCreateDynamicInfo.do";
	
	/**
	 * 创建评论
	 * userid
	 * dynamicinfoid
	 * commentid 评论某动态下哪条评论(可以对别人的评论再次评论，如果评论别人的评论需要带上引用的评论id(只引用一级), =0,表示只评论动态)
	 * content
	 */
	public static final String reqCreateComment = "admin/reqCreateComment.do";
	
	/**
	 * 拉取动态类型列表
	 * userid
	 */
	public static final String reqDynamicInfoTypeList = "admin/reqDynamicInfoTypeList.do";
	
	/**
	 * 点赞
	 * userid
	 * dynamicinfoid
	 * commentid =0,表示只对动态点赞
	 */
	public static final String reqCreateLove = "admin/reqCreateLove.do";
	
	/**
	 * 获取动态统计信息
	 * companyid 公司(单位)id
	 * depid 部门id
	 */
	public static final String reqDepartmentDynamicInfoStatistics = "admin/reqDepartmentDynamicInfoStatistics.do";
	
	/**
	 * 获取我的动态统计信息
	 * userid 当前用户id
	 */
	public static final String reqMyDynamicInfoStatistics = "admin/reqMyDynamicInfoStatistics.do";
	
	/**
	 * 获取评论详情
	 * page 
	 * pagesize
	 * dynamicinfoid  动态id
	 */
	public static final String reqCommentList = "admin/reqCommentList.do";
	
	/**
	 * 获取评论详情
	 * page 
	 * pagesize
	 * dynamicinfoid  动态id
	 */
	public static final String reqUploadFile = "admin/uploadFile.do";
	
	/**
	 * 获取组织通讯录
	 * userid
	 * prevclass 服务器下发prevclass的下一级列表(0:拉取首页,当前用户能看到的最高级的通讯录列表，不同用户可能不一样; 其他:服务器下发的级别(curclass))
	 */
	public static final String reqAddressBook = "admin/reqAddressBook.do";
	
	/**
	 * 搜索通讯录用户
	 * userid
	 * keyword 关键字
	 */
	public static final String reqSearchUser = "admin/reqSearchUser.do";
	
	/**
	 * 查询用户用户信息
	 */
	public static final String reqUserInfo = "admin/reqUserInfo.do";
	
	/**
	 * 拉取举报原因列表
	 * userid
	 * reqCreateReport
	 */
	public static final String reqReportResonList = "admin/reqReportResonList.do";
	
	/**
	 * 删除动态
	 * userid
	 * dynamicid
	 */
	public static final String reqDelDynamicInfo = "admin/reqDelDynamicInfo.do";
	
	/**
	 * 删除评论
	 * userid
	 * commentid
	 */
	public static final String reqDelCommentInfo = "admin/reqDelCommentInfo.do";
	
	/**
	 * 同步服务器时间
	 */
	public static final String reqSyncTime = "syncTime.do";
	
	
}

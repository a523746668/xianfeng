package com.qingyii.hxtz.zhf.Util;

import com.qingyii.hxtz.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Taskdetailbean;
import com.qingyii.hxtz.wmcj.mvp.model.bean.WorkParkbean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhf on 2017/9/27.
 */


//存储一些关键变量
public class Global {
      //任务清单Fragment的翻页Title
     public static String TaskVpTitle="";

    public static long alltime=0;
     //public static  int TaskVpTitleId;

     // 行业组ID
     public  static int userid=-99;

    //任务清单Listview子条目
     public static  String  TaskListname="";

    //工作动态标题存储
    public  static WorkParkbean workParkbean=null;

    public static String[] leves={"一级","二级","三级","四级","五级"};

    public static Taskdetailbean taskdetailbean=null;

    public static String phone="";

    //是否在登录状态
    public static boolean flag=false;

    //是否获得HomeInfo
    public static boolean isFlag=false;

   //jpush
    public static boolean isjpush=false;

    public static ArrayList<HomeInfo.AccountBean.ModulesBean> list=new ArrayList<>();

    public static  List<Taskdetailbean.DataBean.IndustryParentBean> industryParent;

     public  static String system="";

}

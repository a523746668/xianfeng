package com.zhf.Util;

import com.zhf.bean.Taskdetailbean;
import com.zhf.bean.WorkParkbean;

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
     public  static int userid=6;

    //任务清单Listview子条目
     public static  String  TaskListname="";

    //工作动态标题存储
    public  static WorkParkbean workParkbean=null;

    public static String[] leves={"一级","二级","三级","四级","五级"};

    public static Taskdetailbean taskdetailbean=null;

    public static String phone="";

}

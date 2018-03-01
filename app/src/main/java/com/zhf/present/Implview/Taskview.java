package com.zhf.present.Implview;


import com.zhf.bean.TaskLineBean;
import com.zhf.bean.TaskTitlebean;
import com.zhf.zfragment.TasklistsonFragment;

import java.util.ArrayList;

public interface Taskview {
      void getdatasuccess(ArrayList<TasklistsonFragment> list);


      void gettitlessuccess(ArrayList<TaskTitlebean.DataBean.LibsystemBean> list);

      //数据为空的回调
      void getdatano();

      //出现问题的回调
      void getdataerror(Exception e);
}

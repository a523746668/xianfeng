package com.zhf.present.Implview;

import com.zhf.bean.Resultbean;
import com.zhf.bean.TaskTitlebean;
import com.zhf.bean.Tasklistbean;
import com.zhf.zfragment.ResultsonFragment;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/9/22.
 */

public  interface Resultview {
              void getdatasuccess(ArrayList<ResultsonFragment> list) ;

              void  gettitlesuccess(ArrayList<TaskTitlebean.DataBean.LibsystemBean> titles);

            void getdataerror(Exception e);

           void getdatano();


}

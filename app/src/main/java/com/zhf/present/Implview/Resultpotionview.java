package com.zhf.present.Implview;

import com.zhf.bean.TaskTitlebean;
import com.zhf.zfragment.ResultsonFragment;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/10/1.
 */

public interface Resultpotionview {
    void getdatasuccess(ArrayList<ResultsonFragment> list) ;

    void  gettitlesuccess(ArrayList<TaskTitlebean.DataBean.LibsystemBean> titles);

    void getdataerror(Exception e);

    void getdatano();
}

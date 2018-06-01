package com.qingyii.hxtz.zhf.present.Implview;

import com.qingyii.hxtz.zhf.bean.SCbean;
import com.qingyii.hxtz.zhf.bean.SCtypebean;

import java.util.ArrayList;

/**
 * Created by zhf on 2018/1/12.
 */

public  interface Scview {
     void gettypessuccess(SCtypebean ctypebean);

     void getdatasuccess(ArrayList<SCbean.DataBean> list);
}

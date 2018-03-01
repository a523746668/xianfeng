package com.zhf.present.Implview;

import com.zhf.bean.SCbean;
import com.zhf.bean.SCtypebean;

import java.util.ArrayList;

/**
 * Created by zhf on 2018/1/12.
 */

public  interface Scview {
     void gettypessuccess(SCtypebean ctypebean);

     void getdatasuccess(ArrayList<SCbean.DataBean> list);
}

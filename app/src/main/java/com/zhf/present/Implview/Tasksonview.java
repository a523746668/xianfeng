package com.zhf.present.Implview;

import com.zhf.bean.TaskLineBean;
import com.zhf.bean.TaskListViewBean;
import com.zhf.bean.Tasklistbean;

import java.util.ArrayList;

/**
 * Created by xubo on 2017/9/20.
 */

public interface Tasksonview {
    void getdatasuccess(TaskLineBean tasklistbean);

    void getyijishuju(ArrayList<TaskListViewBean> list);


}

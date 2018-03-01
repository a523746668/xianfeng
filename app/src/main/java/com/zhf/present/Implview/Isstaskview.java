package com.zhf.present.Implview;

import com.zhf.base.BaseActivityview;
import com.zhf.bean.SendTask;
import com.zhf.bean.TaskTag;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/10/12.
 */

public interface Isstaskview extends BaseActivityview {
    public void uploadimagessuccess(ArrayList<String> list);

    public void uploadfilesuccess(ArrayList<String> list);

    public void gettagssuccess(ArrayList<TaskTag.DataBean> list);

    public void fabusucccess();


    void uploadvideosuccess(SendTask task);
    void uploadpicturesuccess(SendTask task);
}

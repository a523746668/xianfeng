package com.zhf.base;

import android.content.Context;

import com.qingyii.hxt.base.view.BaseView;

/**
 * Created by zhf on 2017/9/24.
 */

public   abstract class Basepresenter<T extends BaseActivityview> {
    protected Context context;
    protected T Acvitivtyview;

    public Basepresenter(Context context, T acvitivtyview) {
        this.context = context;
        Acvitivtyview = acvitivtyview;
    }

    public void unbind(){
       context=null;
       Acvitivtyview=null;
   }
}

package com.qingyii.hxtz.wmcj.mvp.model.bean;

import android.widget.TextView;

import com.mcxtzhang.commonadapter.rv.IHeaderHelper;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.qingyii.hxtz.R;

public class Common  {

    public static String data_type="2";

     public int id;
     public  String title;
     public String biaoti;
     public String type;
     public boolean ischeck=false;

    public static String getData_type() {
        return data_type;
    }

    public static void setData_type(String data_type) {
        Common.data_type = data_type;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public Common() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Common(String biaoti, String type) {
        this.biaoti = biaoti;
        this.type=type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

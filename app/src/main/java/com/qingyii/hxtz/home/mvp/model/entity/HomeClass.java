package com.qingyii.hxtz.home.mvp.model.entity;

/**
 * Created by zhf on 2018/4/11.
 */

public class HomeClass {
    String text;
    int id;
    String tag;

    public HomeClass(String text, int id, String tag) {
        this.text = text;
        this.id = id;
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class Advert implements Serializable {

    /**
     * Serializable
     * 序列化就是将一个对象的状态（各个属性量）保存起来，然后在适当的时候再获得。
     */
    private static final long serialVersionUID = -8680153515093070936L;
    private String advertid;
    private String advertname;
    private String picaddress;
    private String linkaddress;
    private String advertdesc;
    private String userid;
    private String position;
    private String createtime;

    public String getAdvertid() {
        return advertid;
    }

    public void setAdvertid(String advertid) {
        this.advertid = advertid;
    }

    public String getAdvertname() {
        return advertname;
    }

    public void setAdvertname(String advertname) {
        this.advertname = advertname;
    }

    public String getPicaddress() {
        return picaddress;
    }

    public void setPicaddress(String picaddress) {
        this.picaddress = picaddress;
    }

    public String getLinkaddress() {
        return linkaddress;
    }

    public void setLinkaddress(String linkaddress) {
        this.linkaddress = linkaddress;
    }

    public String getAdvertdesc() {
        return advertdesc;
    }

    public void setAdvertdesc(String advertdesc) {
        this.advertdesc = advertdesc;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatetimeStr() {
        return createtimeStr;
    }

    public void setCreatetimeStr(String createtimeStr) {
        this.createtimeStr = createtimeStr;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    private String username;
    private String createtimeStr;
}

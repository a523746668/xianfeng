package com.qingyii.hxt.bean;

import java.io.Serializable;

/**
 * 通讯录组织实体类
 *
 * @author Lee
 */
public class ContactOrg implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8309624366361146963L;
    private int id;
    private String name;
    private String picaddress;
    private int curclass;
    private int curid;

    public int getCurid() {
        return curid;
    }

    public void setCurid(int curid) {
        this.curid = curid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicaddress() {
        return picaddress;
    }

    public void setPicaddress(String picaddress) {
        this.picaddress = picaddress;
    }

    public int getCurclass() {
        return curclass;
    }

    public void setCurclass(int curclass) {
        this.curclass = curclass;
    }
}

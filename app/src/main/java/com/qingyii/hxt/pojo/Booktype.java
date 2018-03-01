package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class Booktype implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3404496638849280499L;
	private String typeid;
	private String typename;
	private String picaddress;
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getPicaddress() {
		return picaddress;
	}
	public void setPicaddress(String picaddress) {
		this.picaddress = picaddress;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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
	private String createtime;
	private String createtimeStr;
}

package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class UserMailRela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -446721860299289895L;
	private String relaid;
	public String getRelaid() {
		return relaid;
	}
	public void setRelaid(String relaid) {
		this.relaid = relaid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public String getReadflag() {
		return readflag;
	}
	public void setReadflag(String readflag) {
		this.readflag = readflag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String userid;
	private String mailid;
	private String readflag;
}

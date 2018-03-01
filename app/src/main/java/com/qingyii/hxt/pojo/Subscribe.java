package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class Subscribe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4734085008922998091L;
	private String subscribeid;

	public String getSubscribeid() {
		return subscribeid;
	}

	public void setSubscribeid(String subscribeid) {
		this.subscribeid = subscribeid;
	}

	public String getMagazineid() {
		return magazineid;
	}

	public void setMagazineid(String magazineid) {
		this.magazineid = magazineid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getMagazinename() {
		return magazinename;
	}

	public void setMagazinename(String magazinename) {
		this.magazinename = magazinename;
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

	private String magazineid;
	private String userid;
	private String createtime;
	private String magazinename;
	private String username;
	private String createtimeStr;
}

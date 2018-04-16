package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class Magazine implements Serializable {

	/**
	 * 杂志列表实体类
	 */
	private static final long serialVersionUID = 6975715004072490534L;
	private String magazineid;
	/**
	 * 主办
	 */
	private String sponsor;
	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getMagazineid() {
		return magazineid;
	}

	public void setMagazineid(String magazineid) {
		this.magazineid = magazineid;
	}

	public String getMagazinename() {
		return magazinename;
	}

	public void setMagazinename(String magazinename) {
		this.magazinename = magazinename;
	}

	public String getPicaddress() {
		return picaddress;
	}

	public void setPicaddress(String picaddress) {
		this.picaddress = picaddress;
	}

	public String getMagazinedesc() {
		return magazinedesc;
	}

	public void setMagazinedesc(String magazinedesc) {
		this.magazinedesc = magazinedesc;
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

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public String getSubscribeid() {
		return subscribeid;
	}

	public void setSubscribeid(String subscribeid) {
		this.subscribeid = subscribeid;
	}



	public String getReadflag() {
		return readflag;
	}

	public void setReadflag(String readflag) {
		this.readflag = readflag;
	}



	private String subscribeid;
	private String readflag;
	private String username;
	private String magazinename;
	private String picaddress;
	private String magazinedesc;
	private String createtime;
	private String createtimeStr;
}

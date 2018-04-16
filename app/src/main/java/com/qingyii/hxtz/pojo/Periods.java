package com.qingyii.hxtz.pojo;

import java.io.Serializable;

/**
 * 内刊期数实体类
 * @author shelia
 *
 */
public class Periods implements Serializable {

	/**
	 * 杂志期数列表
	 */
	private static final long serialVersionUID = 7255976902623306863L;
	private String periodsid;

	public String getPeriodsid() {
		return periodsid;
	}

	public void setPeriodsid(String periodsid) {
		this.periodsid = periodsid;
	}

	public String getMagazineid() {
		return magazineid;
	}

	public void setMagazineid(String magazineid) {
		this.magazineid = magazineid;
	}

	public String getPicaddress() {
		return picaddress;
	}

	public void setPicaddress(String picaddress) {
		this.picaddress = picaddress;
	}

	public String getPeriodsdesc() {
		return periodsdesc;
	}

	public void setPeriodsdesc(String periodsdesc) {
		this.periodsdesc = periodsdesc;
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

	public String getPeriodsname() {
		return periodsname;
	}

	public void setPeriodsname(String periodsname) {
		this.periodsname = periodsname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String magazineid;
	private String picaddress;
	private String periodsdesc;
	private String createtime;
	private String magazinename;
	private String periodsname;
}

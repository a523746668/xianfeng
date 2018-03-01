package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class ActivityDiscussPic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4034122584447332800L;
	private String picid;
	private String discussid;
	private String picaddress;
	private String content;

	public String getPicid() {
		return picid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public String getDiscussid() {
		return discussid;
	}

	public void setDiscussid(String discussid) {
		this.discussid = discussid;
	}

	public String getPicaddress() {
		return picaddress;
	}

	public void setPicaddress(String picaddress) {
		this.picaddress = picaddress;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

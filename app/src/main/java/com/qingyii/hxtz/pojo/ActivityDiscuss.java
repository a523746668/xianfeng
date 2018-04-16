package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class ActivityDiscuss implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5062795476833436208L;
	private String discussid;

	public String getDiscussid() {
		return discussid;
	}

	public void setDiscussid(String discussid) {
		this.discussid = discussid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getPraisecount() {
		return praisecount;
	}

	public void setPraisecount(String praisecount) {
		this.praisecount = praisecount;
	}

	public String getEggcount() {
		return eggcount;
	}

	public void setEggcount(String eggcount) {
		this.eggcount = eggcount;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String content;
	private String userid;
	private String parentid;
	private String praisecount;
	private String eggcount;
	private String createtime;
	private String username;
}

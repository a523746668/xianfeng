package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class Suggestions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7629072439042876805L;
	public String getSuggestionsid() {
		return suggestionsid;
	}
	public void setSuggestionsid(String suggestionsid) {
		this.suggestionsid = suggestionsid;
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
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
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
	private String suggestionsid;
	private String content;
	private String userid;
	private String experience;
	private String createtime;
	private String createtimeStr;
}

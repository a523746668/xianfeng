package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class Investigation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8177518405910798970L;
	private String investigationid;
	private String title;
	private String content;
	private String integration;
	private String chooseflag;
	private String experience;
	private String createtime;

	public String getInvestigationid() {
		return investigationid;
	}

	public void setInvestigationid(String investigationid) {
		this.investigationid = investigationid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIntegration() {
		return integration;
	}

	public void setIntegration(String integration) {
		this.integration = integration;
	}

	public String getChooseflag() {
		return chooseflag;
	}

	public void setChooseflag(String chooseflag) {
		this.chooseflag = chooseflag;
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

	private String createtimeStr;
}

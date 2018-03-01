package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class InvestigationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4282196705606870067L;
	private String resultid;
	private String investigationid;
	private String infoid;
	private String answer;

	public String getResultid() {
		return resultid;
	}

	public void setResultid(String resultid) {
		this.resultid = resultid;
	}

	public String getInvestigationid() {
		return investigationid;
	}

	public void setInvestigationid(String investigationid) {
		this.investigationid = investigationid;
	}

	public String getInfoid() {
		return infoid;
	}

	public void setInfoid(String infoid) {
		this.infoid = infoid;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String userid;
	private String createtime;
	private String createtimeStr;
}

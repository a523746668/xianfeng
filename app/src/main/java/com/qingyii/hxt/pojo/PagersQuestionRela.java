package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class PagersQuestionRela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8992969090595924728L;
	private String relaid;
	private String questionid;
	private String paperid;
	public String getRelaid() {
		return relaid;
	}
	public void setRelaid(String relaid) {
		this.relaid = relaid;
	}
	public String getQuestionid() {
		return questionid;
	}
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}
	public String getPaperid() {
		return paperid;
	}
	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class SuggestionsReply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6475785774857534683L;
	private String replyid;

	public String getReplyid() {
		return replyid;
	}

	public void setReplyid(String replyid) {
		this.replyid = replyid;
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

	public String getAdoptflag() {
		return adoptflag;
	}

	public void setAdoptflag(String adoptflag) {
		this.adoptflag = adoptflag;
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

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String content;
	private String userid;
	private String parentid;
	private String adoptflag;
	private String praisecount;
	private String eggcount;
	private String createtime;
	private String createtimeStr;
}

package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class SuggestionsReplyPic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -391563401333057277L;
	private String picid;

	public String getPicid() {
		return picid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public String getReplyid() {
		return replyid;
	}

	public void setReplyid(String replyid) {
		this.replyid = replyid;
	}

	public String getPicaddress() {
		return picaddress;
	}

	public void setPicaddress(String picaddress) {
		this.picaddress = picaddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String replyid;
	private String picaddress;
}

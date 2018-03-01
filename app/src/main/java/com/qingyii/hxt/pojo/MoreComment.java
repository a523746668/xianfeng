package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class MoreComment implements Serializable{

	
	/**
	 *更多评论
	 */

	private String headportrait;//头像
	private String yourname; //名称
	private String commenttime; //时间
	private String morecomments; //评论	
	

	private static final long serialVersionUID = 6478935104436256286L;


	public String getHeadportrait() {
		return headportrait;
	}


	public void setHeadportrait(String headportrait) {
		this.headportrait = headportrait;
	}


	public String getYourname() {
		return yourname;
	}


	public void setYourname(String yourname) {
		this.yourname = yourname;
	}


	public String getCommenttime() {
		return commenttime;
	}


	public void setCommenttime(String commenttime) {
		this.commenttime = commenttime;
	}


	public String getMorecomments() {
		return morecomments;
	}


	public void setMorecomments(String morecomments) {
		this.morecomments = morecomments;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	
	

	
	
	
}

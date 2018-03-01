package com.qingyii.hxt.bean;

import java.io.Serializable;

public class CommentInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String yijian_name;
	private String yijian_ImageUrl;
	private String commentContent;
	private String commentId;
	//支持，点赞
	private int support;
	//反对，踩
	private int oppose;
	private String time;
	//评论者
	private String person;


	public String getYijian_ImageUrl() {
		return yijian_ImageUrl;
	}
	public void setYijian_ImageUrl(String yijian_ImageUrl) {
		this.yijian_ImageUrl = yijian_ImageUrl;
	}
	public String getYijian_name() {
		return yijian_name;
	}
	public void setYijian_name(String yijian_name) {
		this.yijian_name = yijian_name;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public int getSupport() {
		return support;
	}
	public void setSupport(int support) {
		this.support = support;
	}
	public int getOppose() {
		return oppose;
	}
	public void setOppose(int oppose) {
		this.oppose = oppose;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}

package com.qingyii.hxt.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class huodongInfo implements Serializable{
  /**
   * 活动实体类
   * 
   */
	private static final long serialVersionUID = 1L;
	private String id;
	private String url;
	private String title;
	private String leixing;
	private String endTime;
	private String content;
	private ArrayList<researchInfo> reseachList;
	private ArrayList<CommentInfo> commentList;
	public ArrayList<researchInfo> getReseachList() {
		return reseachList;
	}
	public void setReseachList(ArrayList<researchInfo> reseachList) {
		this.reseachList = reseachList;
	}
	public ArrayList<CommentInfo> getCommentList() {
		return commentList;
	}
	public void setCommentList(ArrayList<CommentInfo> commentList) {
		this.commentList = commentList;
	}
	public huodongInfo() {
		super();
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLeixing() {
		return leixing;
	}
	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEnd_time(String end_time) {
		this.endTime = end_time;
	}
	
}

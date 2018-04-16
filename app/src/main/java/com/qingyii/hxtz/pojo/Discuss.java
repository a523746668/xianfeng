package com.qingyii.hxtz.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class Discuss implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3041538725866013293L;
	private String discussid;

	public String getDiscussid() {
		return discussid;
	}

	public void setDiscussid(String discussid) {
		this.discussid = discussid;
	}

	public String getArticleid() {
		return articleid;
	}

	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getAuditflag() {
		return auditflag;
	}

	public void setAuditflag(String auditflag) {
		this.auditflag = auditflag;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
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


    public String getCollectionid() {
		return collectionid;
	}

	public void setCollectionid(String collectionid) {
		this.collectionid = collectionid;
	}


	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

		




	private String  picaddress;
	public String getPicaddress() {
		return picaddress;
	}

	public void setPicaddress(String picaddress) {
		this.picaddress = picaddress;
	}






	private String collectionid;
	private String articleid;
	private String bookid;
	private String userid;
	private String content;
	private String praisecount;
	private String eggcount;
	private String parentid;
	private String auditflag;
	private String createtime;
	private String title;
	private String username;
	private String bookname;
	private String createtimeStr;
	/**
	 * 内刊内容子评论点赞，默认为0时表示未点赞，1时表示点赞过
	 */
	private int flag=0;      
	
	
	
    private ArrayList<Discuss> discussList=new ArrayList<Discuss>();

	public ArrayList<Discuss> getDiscussList() {
		return discussList;
	}

	public void setDiscussList(ArrayList<Discuss> discussList) {
		this.discussList = discussList;
	}
}

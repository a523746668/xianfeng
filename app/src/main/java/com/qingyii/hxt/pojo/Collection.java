package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class Collection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3820271420039438005L;
	private String collectionid;
	private String articleid;
	private String magazineid;
	private String bookid;
	private String userid;
	public String getCollectionid() {
		return collectionid;
	}
	public void setCollectionid(String collectionid) {
		this.collectionid = collectionid;
	}
	public String getArticleid() {
		return articleid;
	}
	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}
	public String getMagazineid() {
		return magazineid;
	}
	public void setMagazineid(String magazineid) {
		this.magazineid = magazineid;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

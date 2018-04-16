package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class Catalog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7091281403581267037L;
	private String catalogid;
	private String catalogname;
	private String bookid;
	private String bookname;
	private String title;
	private String chapterid;
	public String getCatalogid() {
		return catalogid;
	}
	public void setCatalogid(String catalogid) {
		this.catalogid = catalogid;
	}
	public String getCatalogname() {
		return catalogname;
	}
	public void setCatalogname(String catalogname) {
		this.catalogname = catalogname;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getChapterid() {
		return chapterid;
	}
	public void setChapterid(String chapterid) {
		this.chapterid = chapterid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

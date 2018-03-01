package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class specialBookRela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1958536160551663879L;
    private String relaid;
    private String specialid;
    private String bookid;
    private String author;
    private String bookname;
	public String getRelaid() {
		return relaid;
	}
	public void setRelaid(String relaid) {
		this.relaid = relaid;
	}
	public String getSpecialid() {
		return specialid;
	}
	public void setSpecialid(String specialid) {
		this.specialid = specialid;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
    
    
}

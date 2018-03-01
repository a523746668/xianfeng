package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class BookTypeRela implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7769548843995034555L;
	private String relaid;
	private String bookid;
	private String typeid;
	private String bookname;
	private String typename;
	public String getRelaid() {
		return relaid;
	}
	public void setRelaid(String relaid) {
		this.relaid = relaid;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

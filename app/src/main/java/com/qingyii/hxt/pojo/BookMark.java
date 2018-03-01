package com.qingyii.hxt.pojo;

import java.io.Serializable;

/**
 * 书签/批注实体类
 * 
 * @author shelia
 * 
 */
public class BookMark implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5456020480732014575L;
	private String id;
	private String bookId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookMarkLoucs() {
		return bookMarkLoucs;
	}

	public void setBookMarkLoucs(String bookMarkLoucs) {
		this.bookMarkLoucs = bookMarkLoucs;
	}

	public String getBookMarkName() {
		return bookMarkName;
	}

	public void setBookMarkName(String bookMarkName) {
		this.bookMarkName = bookMarkName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String bookMarkLoucs;
	private String bookMarkName;

}

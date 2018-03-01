package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class Notation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2764708538588153957L;

	public String getNotationid() {
		return notationid;
	}

	public void setNotationid(String notationid) {
		this.notationid = notationid;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getChapterid() {
		return chapterid;
	}

	public void setChapterid(String chapterid) {
		this.chapterid = chapterid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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

	private String notationid;
	private String bookid;
	private String chapterid;
	private String content;
	private String position;
	private String createtime;
	private String title;
	private String bookname;
	private String createtimeStr;
}

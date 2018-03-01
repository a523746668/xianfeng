package com.qingyii.hxt.bean;

import java.io.Serializable;

public class fenleiInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7079609012218851444L;
    private String typename;
    private String typeid;
    private String bookname;
    private String ImageUrl;
    
	public String getImageUrl() {
		return ImageUrl;
	}
	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
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
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
    
}

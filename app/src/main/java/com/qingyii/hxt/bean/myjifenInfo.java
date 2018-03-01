package com.qingyii.hxt.bean;

public class myjifenInfo {
   /**
    * 我的积分实体类
    */
	
	private String jifen;
	private String date;
	private String content;
	public myjifenInfo( String date, String content) {
		super();
		
		this.date = date;
		this.content = content;
	}
	public String getJifen() {
		return jifen;
	}
	public void setJifen(String jifen) {
		this.jifen = jifen;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}


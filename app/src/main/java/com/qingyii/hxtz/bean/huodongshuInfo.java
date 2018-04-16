package com.qingyii.hxtz.bean;

public class huodongshuInfo {
	private String title;
	private String time;
	private String [] imgUrls;
	public huodongshuInfo() {
		super();
	}
	public huodongshuInfo(String title, String time) {
		super();
		this.title = title;
		this.time = time;
	}
	public String[] getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String[] imgUrls) {
		this.imgUrls = imgUrls;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}

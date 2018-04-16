package com.qingyii.hxtz.bean;

import java.io.Serializable;

public class MyshouckanwuInfo implements Serializable{

	/**
	 * 我的收藏刊物信息实体类
	 */
	private static final long serialVersionUID = 1481708854354203913L;

	private String title;
	private String content;
	public String getTitile() {
		return title;
	}
	public void setTitile(String titile) {
		title = titile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		content = content;
	}
	
}

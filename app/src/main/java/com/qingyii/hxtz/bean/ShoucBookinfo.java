package com.qingyii.hxtz.bean;
/**
 * 
 * 
 * @author TaiAiCH
 *我的收藏书籍实体bean
 */

public class ShoucBookinfo {
	private String icon;
	private String Titile;
	private String uploader;//上传者
	private String content;
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitile() {
		return Titile;
	}
	public void setTitile(String titile) {
		Titile = titile;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}

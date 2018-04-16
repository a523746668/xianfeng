package com.qingyii.hxtz.bean;

public class xinxiangInfo {
     private String xinxiang_name;
     private String xinxiang_content;
     private String isyuedu;
     
	public xinxiangInfo(String xinxiang_name, String xinxiang_content,
			String isyuedu) {
		super();
		this.xinxiang_name = xinxiang_name;
		this.xinxiang_content = xinxiang_content;
		this.isyuedu = isyuedu;
	}
	public String getXinxiang_name() {
		return xinxiang_name;
	}
	public void setXinxiang_name(String xinxiang_name) {
		this.xinxiang_name = xinxiang_name;
	}
	public String getXinxiang_content() {
		return xinxiang_content;
	}
	public void setXinxiang_content(String xinxiang_content) {
		this.xinxiang_content = xinxiang_content;
	}
	public String getIsyuedu() {
		return isyuedu;
	}
	public void setIsyuedu(String isyuedu) {
		this.isyuedu = isyuedu;
	}
     
}

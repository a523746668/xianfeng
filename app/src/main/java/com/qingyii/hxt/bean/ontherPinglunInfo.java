package com.qingyii.hxt.bean;

import java.io.Serializable;

public class ontherPinglunInfo implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 7551557228484836257L;
private String username;
   private String createtime;
   private String content;
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getCreatetime() {
	return createtime;
}
public void setCreatetime(String createtime) {
	this.createtime = createtime;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}

   
}

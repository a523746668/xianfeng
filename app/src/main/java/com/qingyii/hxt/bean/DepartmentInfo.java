package com.qingyii.hxt.bean;

import com.qingyii.hxt.pojo.User;

//同事圈部门信息
public class DepartmentInfo {
	int  deppersoncount;
    int dynamicinfocount;
    int commentcount;
    int receivelovecount;
    int sendlovecount;
    String logourl;
    String backgroundurl;
    User director;
    User manager;
	public int getDeppersoncount() {
		return deppersoncount;
	}
	public void setDeppersoncount(int deppersoncount) {
		this.deppersoncount = deppersoncount;
	}
	public int getDynamicinfocount() {
		return dynamicinfocount;
	}
	public void setDynamicinfocount(int dynamicinfocount) {
		this.dynamicinfocount = dynamicinfocount;
	}
	public int getCommentcount() {
		return commentcount;
	}
	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}
	public int getReceivelovecount() {
		return receivelovecount;
	}
	public void setReceivelovecount(int receivelovecount) {
		this.receivelovecount = receivelovecount;
	}
	public int getSendlovecount() {
		return sendlovecount;
	}
	public void setSendlovecount(int sendlovecount) {
		this.sendlovecount = sendlovecount;
	}
	public String getLogourl() {
		return logourl;
	}
	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}
	public String getBackgroundurl() {
		return backgroundurl;
	}
	public void setBackgroundurl(String backgroundurl) {
		this.backgroundurl = backgroundurl;
	}
	public User getDirector() {
		return director;
	}
	public void setDirector(User director) {
		this.director = director;
	}
	public User getManager() {
		return manager;
	}
	public void setManager(User manager) {
		this.manager = manager;
	}
}

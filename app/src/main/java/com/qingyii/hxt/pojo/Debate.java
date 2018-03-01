package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class Debate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 482981030337763239L;
	private String debateid;
	public String getDebateid() {
		return debateid;
	}
	public void setDebateid(String debateid) {
		this.debateid = debateid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSquareview() {
		return squareview;
	}
	public void setSquareview(String squareview) {
		this.squareview = squareview;
	}
	public String getMotionview() {
		return motionview;
	}
	public void setMotionview(String motionview) {
		this.motionview = motionview;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getWinflag() {
		return winflag;
	}
	public void setWinflag(String winflag) {
		this.winflag = winflag;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getEndtimeStr() {
		return endtimeStr;
	}
	public void setEndtimeStr(String endtimeStr) {
		this.endtimeStr = endtimeStr;
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
	private String title;
	private String content;
	private String squareview;
	private String motionview;
	private String endtime;
	private String winflag;
	private String experience;
	private String createtime;
	private String endtimeStr;
	private String createtimeStr;
}

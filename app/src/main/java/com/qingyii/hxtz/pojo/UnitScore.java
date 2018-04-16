package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class UnitScore implements Serializable{

	/**
	 * 单位成绩排名实体类
	 */
	private static final long serialVersionUID = -2483086633737285936L;

	private String scoreid; 
	private String score;
	private String userid;
	private String username;
	private String paperid;
	private String exminationid; 
	private String examinationname;
	
	private String companyid;   //单位ID
	private String companyname; //单位名
	
	private String joincount;  //参与人数
	private String name;  //真实姓名
	
	
	
	private String depid;
	private String depname;

	public String getDepid() {
		return depid;
	}

	public void setDepid(String depid) {
		this.depid = depid;
	}

	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScoreid() {
		return scoreid;
	}

	public void setScoreid(String scoreid) {
		this.scoreid = scoreid;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public String getExminationid() {
		return exminationid;
	}

	public void setExminationid(String exminationid) {
		this.exminationid = exminationid;
	}

	public String getExaminationname() {
		return examinationname;
	}

	public void setExaminationname(String examinationname) {
		this.examinationname = examinationname;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getJoincount() {
		return joincount;
	}

	public void setJoincount(String joincount) {
		this.joincount = joincount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
}

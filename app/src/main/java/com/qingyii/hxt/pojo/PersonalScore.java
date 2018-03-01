package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class PersonalScore implements Serializable{

	/**
	 * 个人历史考试成绩实体类
	 */
	private static final long serialVersionUID = 3913481728239088954L;

	
	private String scoreid;      //分数ID
	private String score;
	
	private String userid;
	private String username;
	
	private String paperid;
	
	private String exminationid;      //考试ID
	private String examinationname;   //考试名称
	
	private String companyid;   //单位ID
	private String companyname; //单位名
	
	private String jobname; 
	private String duration;  //考试时长
	
//	private String createtime;
	private String createtimeStr;
	private String name;
	
	private String depid;
	private String depname;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public String getCreatetime() {
//		return createtime;
//	}
//	public void setCreatetime(String createtime) {
//		this.createtime = createtime;
//	}
	public String getCreatetimeStr() {
		return createtimeStr;
	}
	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
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
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
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
	
	
	
	
	
	
	
	
}

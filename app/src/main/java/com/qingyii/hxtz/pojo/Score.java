package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class Score implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5230051045941648867L;
	private String scoreid;

	public String getScoreid() {
		return scoreid;
	}

	public void setScoreid(String scoreid) {
		this.scoreid = scoreid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getExaminationid() {
		return examinationid;
	}

	public void setExaminationid(String examinationid) {
		this.examinationid = examinationid;
	}

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public String getScore() {
//		if(!TextUtils.isEmpty(score)) {
//			float scoreF = Float.parseFloat(score);
//			return TextUtil.getDefaultDecimalFormat().format(scoreF);
//		}
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	

//	public String getCreatetime() {
//		return createtime;
//	}
//
//	public void setCreatetime(String createtime) {
//		this.createtime = createtime;
//	}

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name; //真实姓名
	
	private String userid;
	/**
	 * 职位
	 */
	private String jobname;
	/**
	 * 用户名
	 */
	private String username;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String examinationid;
	/**
	 * 考试名称
	 */
	private String examinationname;
	
	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getExaminationname() {
		return examinationname;
	}

	public void setExaminationname(String examinationname) {
		this.examinationname = examinationname;
	}

	public String getExaminationpapername() {
		return examinationpapername;
	}

	public void setExaminationpapername(String examinationpapername) {
		this.examinationpapername = examinationpapername;
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
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * 考试席卷名称
	 */
	private String examinationpapername;
	private String paperid;
	private String score;
	//private String createtime;
	private String createtimeStr;
	private String companyid;
	private String companyname;
	private String duration;
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
	
}

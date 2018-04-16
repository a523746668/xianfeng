package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class Job implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8211113366314776534L;
	private String jobid;
	private String jobname;
	public String getJobid() {
		return jobid;
	}
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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
	private String createtime;
	private String createtimeStr;
}

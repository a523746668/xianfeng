package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8494149379053309285L;
	private String userid;
	private String username;
	private String password;
	private String nicker;
	private String name;
	private String sex;
	private String birthday;
	/**
	 * 格式化后的日期格式
	 */
	private String birthdayStr;
	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	private String phone;
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNicker() {
		return nicker;
	}

	public void setNicker(String nicker) {
		this.nicker = nicker;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDepid() {
		return depid;
	}

	public void setDepid(String depid) {
		this.depid = depid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	public String getPicaddress() {
		return picaddress;
	}

	public void setPicaddress(String picaddress) {
		this.picaddress = picaddress;
	}

	public String getFrozenflag() {
		return frozenflag;
	}

	public void setFrozenflag(String frozenflag) {
		this.frozenflag = frozenflag;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public int getXiuflag() {
		return xiuflag;
	}

	public void setXiuflag(int xiuflag) {
		this.xiuflag = xiuflag;
	}

	private String depid;
	private String companyid;
	private String jobid;
	private String picaddress;
	private String frozenflag;
//	private String createtime;

	private String jobname;
	private String depname;
	private String companyname;

	private String createtimeStr;

	private int xiuflag;

	private String flag;  //查询标记 1--用户名模糊查询
}

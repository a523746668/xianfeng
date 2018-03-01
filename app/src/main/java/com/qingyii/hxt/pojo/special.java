package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class special implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3814300100450691483L;
    private String specialid;
    private String specialname;
    private String specialdesc;
    private String picaddress;
    private String userid;
    private String createtime;
	public String getSpecialid() {
		return specialid;
	}
	public void setSpecialid(String specialid) {
		this.specialid = specialid;
	}
	public String getSpecialname() {
		return specialname;
	}
	public void setSpecialname(String specialname) {
		this.specialname = specialname;
	}
	public String getSpecialdesc() {
		return specialdesc;
	}
	public void setSpecialdesc(String specialdesc) {
		this.specialdesc = specialdesc;
	}
	public String getPicaddress() {
		return picaddress;
	}
	public void setPicaddress(String picaddress) {
		this.picaddress = picaddress;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
    
}

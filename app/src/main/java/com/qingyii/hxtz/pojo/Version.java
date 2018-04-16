package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class Version implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6906639788553847287L;
	private String versionid;
	private String version;
	private String phonetype;
	private String versiondesc;

	public String getVersionid() {
		return versionid;
	}

	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPhonetype() {
		return phonetype;
	}

	public void setPhonetype(String phonetype) {
		this.phonetype = phonetype;
	}

	public String getVersiondesc() {
		return versiondesc;
	}

	public void setVersiondesc(String versiondesc) {
		this.versiondesc = versiondesc;
	}

	public String getAppadress() {
		return appadress;
	}

	public void setAppadress(String appadress) {
		this.appadress = appadress;
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

	private String appadress;
	private String createtime;
	private String createtimeStr;
}

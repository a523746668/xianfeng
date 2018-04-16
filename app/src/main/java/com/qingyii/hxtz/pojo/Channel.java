package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class Channel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2005122837481420115L;
	private String channlid;
	private String channelname;
	public String getChannlid() {
		return channlid;
	}
	public void setChannlid(String channlid) {
		this.channlid = channlid;
	}
	public String getChannelname() {
		return channelname;
	}
	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}
	public String getAnothername() {
		return anothername;
	}
	public void setAnothername(String anothername) {
		this.anothername = anothername;
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
	private String anothername;
	private String createtime;
	private String createtimeStr;
}

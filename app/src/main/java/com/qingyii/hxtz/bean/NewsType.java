package com.qingyii.hxtz.bean;

import java.io.Serializable;

public class NewsType implements Serializable {

	/**
	 * 新闻类型实体类（订阅频道）
	 */
	private static final long serialVersionUID = 1L;
	private int typeid;
	private String typename;
	private int pid;
	private String logoaddress;
	private String bgaddress;
	private String typeaddress;
	private String createdateStr;
	private String typeurl;
	public int getTypeorder() {
		return typeorder;
	}

	public void setTypeorder(int typeorder) {
		this.typeorder = typeorder;
	}

	private int typeorder;
	public String getTypeurl() {
		return typeurl;
	}

	public void setTypeurl(String typeurl) {
		this.typeurl = typeurl;
	}

	//是否已订阅标志：0代表未订阅，1代表已订阅
	private int falg=0;
    //订阅记录ID号
	private int subid;
	//分类标记（1-普通频道，2-精选）
	private int typeflag;
	//专题是否置顶:1-不置顶，2-置顶
	private int istop = 1;
	//专题插入新闻列表位置（位于此新闻ID后面）
	private int position;
	//离线下载状态：0默认是未下载，1表示下载中，2表示下载完
	private int downloadstate=0;
	//广播播放状态：用于控制订阅界面播放状态图标显示(-1默认不显示，0表示未播放，1表示播放)
	private int mmsradiostate=-1;
	//频道类型：showflag 0--普通频道，1--网址，2--广播，3--视频'
	private int showflag;
	public int getShowflag() {
		return showflag;
	}

	public void setShowflag(int showflag) {
		this.showflag = showflag;
	}

	public int getMmsradiostate() {
		return mmsradiostate;
	}

	public void setMmsradiostate(int mmsradiostate) {
		this.mmsradiostate = mmsradiostate;
	}

	public int getDownloadstate() {
		return downloadstate;
	}

	public void setDownloadstate(int downloadstate) {
		this.downloadstate = downloadstate;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getIstop() {
		return istop;
	}

	public void setIstop(int istop) {
		this.istop = istop;
	}

	public int getTypeflag() {
		return typeflag;
	}

	public void setTypeflag(int typeflag) {
		this.typeflag = typeflag;
	}

	public int getSubid() {
		return subid;
	}

	public void setSubid(int subid) {
		this.subid = subid;
	}

	public int getFalg() {
		return falg;
	}

	public void setFalg(int falg) {
		this.falg = falg;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getLogoaddress() {
		return logoaddress;
	}

	public void setLogoaddress(String logoaddress) {
		this.logoaddress = logoaddress;
	}

	public String getBgaddress() {
		return bgaddress;
	}

	public void setBgaddress(String bgaddress) {
		this.bgaddress = bgaddress;
	}

	public String getTypeaddress() {
		return typeaddress;
	}

	public void setTypeaddress(String typeaddress) {
		this.typeaddress = typeaddress;
	}

	public String getCreatedateStr() {
		return createdateStr;
	}

	public void setCreatedateStr(String createdateStr) {
		this.createdateStr = createdateStr;
	}
}

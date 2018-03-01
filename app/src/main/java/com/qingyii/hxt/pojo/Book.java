package com.qingyii.hxt.pojo;

import java.io.Serializable;

/**
 * 书籍实体类
 * @author shelia
 *
 */
public class Book implements Serializable{
	
	public String getReadcount() {
		return readcount;
	}
	public void setReadcount(String readcount) {
		this.readcount = readcount;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getPicaddress() {
		return picaddress;
	}
	public void setPicaddress(String picaddress) {
		this.picaddress = picaddress;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	public String getBookdesc() {
		return bookdesc;
	}
	public void setBookdesc(String bookdesc) {
		this.bookdesc = bookdesc;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getPressdate() {
		return pressdate;
	}
	public void setPressdate(String pressdate) {
		this.pressdate = pressdate;
	}
	public String getAuthordesc() {
		return authordesc;
	}
	public void setAuthordesc(String authordesc) {
		this.authordesc = authordesc;
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
	public String getCreatetimeStr() {
		return createtimeStr;
	}
	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}
	public String getPressdateStr() {
		return pressdateStr;
	}
	public void setPressdateStr(String pressdateStr) {
		this.pressdateStr = pressdateStr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getBookaddress() {
		return bookaddress;
	}
	public void setBookaddress(String bookaddress) {
		this.bookaddress = bookaddress;
	}

	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

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


	public String getReadflag() {
		return readflag;
	}
	public void setReadflag(String readflag) {
		this.readflag = readflag;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 2833247788030309472L;
    private String specialid;
    private String specialname;
    private String specialdesc;
	private String bookid;
	private String bookname;
	private String picaddress;
	private String username;
	private Integer userid;
	private Integer companyid;
	private String bookdesc;
	private String author;
	private String press;
	private String  pressdate;
	private String authordesc;
	private String experience;
	private String createtime;
	private String createtimeStr;
	private String pressdateStr;
	private String readcount;
	private String bookaddress;
	private String typename;
	private String typeid;
	//书籍阅读权限
	private String readflag;
	private String sdaddress;  //书籍下载缓存地址


	private int filetype;

	public static final int type_EPUB = 1;
	public static final int type_PDF  =2;
	public static final int type_TXT = 3;



	public String getSdaddress() {
		return sdaddress;
	}
	public void setSdaddress(String sdaddress) {
		this.sdaddress = sdaddress;
	}


	/**
	 * 书籍星级
	 */
	private String xingji;
	public String getXingji() {
		return xingji;
	}
	public void setXingji(String xingji) {
		this.xingji = xingji;
	}

	/**
	 * 阅读进度：章节路径
	 */
	private String read_componentId;
	/**
	 * 阅读进度：百分比
	 */
	private String read_percent;
	/**
	 * 阅读monocle库加载对像字符串：{"page":1,"componentId":"file:///mnt/sdcard/HXT/1432274176647/OEBPS/Text/copyright.html","percent":1}
	 */
	private String read_locus;
	public String getRead_locus() {
		return read_locus;
	}
	public void setRead_locus(String read_locus) {
		this.read_locus = read_locus;
	}
	public String getRead_componentId() {
		return read_componentId;
	}
	public void setRead_componentId(String read_componentId) {
		this.read_componentId = read_componentId;
	}
	public String getRead_percent() {
		return read_percent;
	}
	public void setRead_percent(String read_percent) {
		this.read_percent = read_percent;
	}

	public int getFiletype() {
		return filetype;
	}

	public void setFiletype(int filetype) {
		this.filetype = filetype;
	}
}

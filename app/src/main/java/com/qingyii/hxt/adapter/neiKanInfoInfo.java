package com.qingyii.hxt.adapter;

import java.util.ArrayList;

public class neiKanInfoInfo {
   private String  infodesc; // 投票项描述
   private String neiKanInfoInfo_toupiaoContent;
   private String neiKanInfoInfo_name;
   private String neiKanInfoInfo_content;
   private String neiKanInfoInfo_time;
   private String tv_neikaninfo_ImageUrl;
   private String votecount;//投票数量
   private String votedesc;//投票标题
   private String neiKanInfoInfo_baifenbi;
   private String voteid;//'投票ID'
   private String infoid; // '投票项ID',
   private String isvoted="2";//1 已对此文章投过票 2 没投
   private int selectcount;
 
   public int getSelectcount() {
	return selectcount;
}

public void setSelectcount(int selectcount) {
	this.selectcount = selectcount;
}

public String getIsvoted() {
	return isvoted;
}

public void setIsvoted(String isvoted) {
	this.isvoted = isvoted;
}
private ArrayList<neiKanInfoInfo>  voteinfoList=new ArrayList<neiKanInfoInfo>();
  /**
   * 0 radiobutton 隐藏   1radiobutton显示
   */
   private int flag=0;
   
public int getFlag() {
	return flag;
}

public void setFlag(int flag) {
	this.flag = flag; 
}




public String getVoteid() {
	return voteid;
}

public void setVoteid(String voteid) {
	this.voteid = voteid;
}

public String getInfoid() {
	return infoid;
}

public void setInfoid(String infoid) {
	this.infoid = infoid;
}

public ArrayList<neiKanInfoInfo> getVoteinfoList() {
	return voteinfoList;
}

public void setVoteinfoList(ArrayList<neiKanInfoInfo> voteinfoList) {
	this.voteinfoList = voteinfoList;
}

public String getVotedesc() {
	return votedesc;
}

public void setVotedesc(String votedesc) {
	this.votedesc = votedesc;
}

public String getVotecount() {
	return votecount;
}

public void setVotecount(String votecount) {
	this.votecount = votecount;
}

public String getNeiKanInfoInfo_baifenbi() {
	return neiKanInfoInfo_baifenbi;
}

public void setNeiKanInfoInfo_baifenbi(String neiKanInfoInfo_baifenbi) {
	this.neiKanInfoInfo_baifenbi = neiKanInfoInfo_baifenbi;
}

public String getTv_neikaninfo_ImageUrl() {
	return tv_neikaninfo_ImageUrl;
}

public String getNeiKanInfoInfo_toupiaoContent() {
	return neiKanInfoInfo_toupiaoContent;
}

public void setNeiKanInfoInfo_toupiaoContent(
		String neiKanInfoInfo_toupiaoContent) {
	this.neiKanInfoInfo_toupiaoContent = neiKanInfoInfo_toupiaoContent;
}



public String getInfodesc() {
	return infodesc;
}

public void setInfodesc(String infodesc) {
	this.infodesc = infodesc;
}

public void setTv_neikaninfo_ImageUrl(String tv_neikaninfo_ImageUrl) {
	this.tv_neikaninfo_ImageUrl = tv_neikaninfo_ImageUrl;
}
public String getNeiKanInfoInfo_name() {
	return neiKanInfoInfo_name;
}
public void setNeiKanInfoInfo_name(String neiKanInfoInfo_name) {
	this.neiKanInfoInfo_name = neiKanInfoInfo_name;
}
public String getNeiKanInfoInfo_content() {
	return neiKanInfoInfo_content;
}
public void setNeiKanInfoInfo_content(String neiKanInfoInfo_content) {
	this.neiKanInfoInfo_content = neiKanInfoInfo_content;
}
public String getNeiKanInfoInfo_time() {
	return neiKanInfoInfo_time;
}
public void setNeiKanInfoInfo_time(String neiKanInfoInfo_time) {
	this.neiKanInfoInfo_time = neiKanInfoInfo_time;
}
   
  
}

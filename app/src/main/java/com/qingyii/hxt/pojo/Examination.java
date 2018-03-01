package com.qingyii.hxt.pojo;

import java.io.Serializable;

/**
 * 考试实体类
 * @author shelia
 *
 */
public class Examination implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8996961404475787617L;
	private String examinationid;
	private String examinationname;
	/**
	 * 考试类型（1-题库抽选，2-单次命题，3-答题闯关1,4-答题闯关2）
	 */
	private String examtype;
	private String companyid;
	private String examinationdesc;
	//private String starttime;
	//private String endtime;
	private String topiccount;
	
	private int starttimeInt;
	private int endtimeInt;
	
	public int getStarttimeInt() {
		return starttimeInt*1000;
	}
	public void setStarttimeInt(int starttimeInt) {
		this.starttimeInt = starttimeInt;
	}
	public int getEndtimeInt() {
		return endtimeInt*1000;
	}
	public void setEndtimeInt(int endtimeInt) {
		this.endtimeInt = endtimeInt;
	}
	/**
	 * 可答题次数
	 */
	private String ansertime;
	private String duration;
	private String joincount;
	/**
	 * 本人是否可参与（1-可参与，2-不可参与）
	 */
	private String joinflag;
	private String experience;
	//private String createtime;
	
	//答题闯关，共有关数
	private String totn;
	
	
	private String answertype;//1：练习模式 2：考试模式  其他：考试模式
	private String score;//若为闯关考试表示第几关，否则表示分数
	
	private String showanswer;//1：显示  0：不显示
	private String showerrlist;//1：显示  0：不显示
	private String consumetime;//上次最好的成绩耗时，单位：秒
	
	private String groupid;
	
	public String getConsumetime() {
		return consumetime;
	}
	public void setConsumetime(String consumetime) {
		this.consumetime = consumetime;
	}
	public String getShowanswer() {
		return showanswer;
	}
	public void setShowanswer(String showanswer) {
		this.showanswer = showanswer;
	}
	public String getShowerrlist() {
		return showerrlist;
	}
	public void setShowerrlist(String showerrlist) {
		this.showerrlist = showerrlist;
	}
	public String getAnswertype() {
		return answertype;
	}
	public void setAnswertype(String answertype) {
		this.answertype = answertype;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}	 
	
	public String getTotn() {
		return totn;
	}
	public void setTotn(String totn) {
		this.totn = totn;
	}
	public String getExaminationid() {
		return examinationid;
	}
	public void setExaminationid(String examinationid) {
		this.examinationid = examinationid;
	}
	public String getExaminationname() {
		return examinationname;
	}
	public void setExaminationname(String examinationname) {
		this.examinationname = examinationname;
	}
	public String getExamtype() {
		return examtype;
	}
	public void setExamtype(String examtype) {
		this.examtype = examtype;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getExaminationdesc() {
		return examinationdesc;
	}
	public void setExaminationdesc(String examinationdesc) {
		this.examinationdesc = examinationdesc;
	}
//	public String getStarttime() {
//		return starttime;
//	}
//	public void setStarttime(String starttime) {
//		this.starttime = starttime;
//	}
//	public String getEndtime() {
//		return endtime;
//	}
//	public void setEndtime(String endtime) {
//		this.endtime = endtime;
//	}
	public String getTopiccount() {
		return topiccount;
	}
	public void setTopiccount(String topiccount) {
		this.topiccount = topiccount;
	}
	public String getAnsertime() {
		return ansertime;
	}
	public void setAnsertime(String ansertime) {
		this.ansertime = ansertime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getJoincount() {
		return joincount;
	}
	public void setJoincount(String joincount) {
		this.joincount = joincount;
	}
	public String getJoinflag() {
		return joinflag;
	}
	public void setJoinflag(String joinflag) {
		this.joinflag = joinflag;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
//	public String getCreatetime() {
//		return createtime;
//	}
//	public void setCreatetime(String createtime) {
//		this.createtime = createtime;
//	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getStarttimeStr() {
		return starttimeStr;
	}
	public void setStarttimeStr(String starttimeStr) {
		this.starttimeStr = starttimeStr;
	}
	public String getEndtimeStr() {
		return endtimeStr;
	}
	public void setEndtimeStr(String endtimeStr) {
		this.endtimeStr = endtimeStr;
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
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	private String companyname;
	private String starttimeStr;
	private String endtimeStr;
	private String createtimeStr;
}

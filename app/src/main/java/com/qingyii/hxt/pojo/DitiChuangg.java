package com.qingyii.hxt.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class DitiChuangg implements Serializable{

	/**
	 * 考试题目实体类
	 */
	private static final long serialVersionUID = 6729030410643649734L;

	/**
	 * 问题id
	 */
	private String questionid;	
	
	//private String createtime;
	//题目问题
	private String questiondesc;

	
	
	/**
	 * 判断是否为单选(1为单选，2为多选,3为判断)
	 */
	private String redios="1";
	
	
	/**
	 * 未答题在所有题目中的索引位置,默认为0，答过为1
	 */
	private int noAnderInext=0;
	

	/**
	 * 关联题目
	 */
	private ArrayList<DitiItem> questionOptionList=new ArrayList<DitiItem>();
	
	/**
	 * 选项ID
	 */
	private HashSet<String> xxid=new HashSet<String>();
	
	/**
	 * 题目做对时为true,默认为0
	 */
	private boolean timuflag=false;	
	


	






	public String getQuestiondesc() {
		return questiondesc;
	}


	public void setQuestiondesc(String questiondesc) {
		this.questiondesc = questiondesc;
	}


	public boolean isTimuflag() {
		return timuflag;
	}


	public void setTimuflag(boolean timuflag) {
		this.timuflag = timuflag;
	}


	public HashSet<String> getXxid() {
		return xxid;
	}


	public void setXxid(HashSet<String> xxid) {
		this.xxid = xxid;
	}


	public String getQuestionid() {
		return questionid;
	}


	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}


//	public String getCreatetime() {
//		return createtime;
//	}
//
//
//	public void setCreatetime(String createtime) {
//		this.createtime = createtime;
//	}


	public String getRedios() {
		return redios;
	}


	public void setRedios(String redios) {
		this.redios = redios;
	}


	public int getNoAnderInext() {
		return noAnderInext;
	}


	public void setNoAnderInext(int noAnderInext) {
		this.noAnderInext = noAnderInext;
	}

	public ArrayList<DitiItem> getQuestionOptionList() {
		return questionOptionList;
	}


	public void setQuestionOptionList(ArrayList<DitiItem> questionOptionList) {
		this.questionOptionList = questionOptionList;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	
}

package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class DitiItem implements Serializable{

	/**
	 *  某个题目实体类
	 */
	private static final long serialVersionUID = -5512489433584044096L;
	
	
	

	/**
	 * 答案选项
	 */
	private String optiondesc;
	
	/**
	 * 答题是否正确标志：0为错误，1为正确
	 */
	private String isright;
	
	private String questionid;
	/**
	 * 选项唯一ID
	 */
	private String optionid;
	
	
	
	
	
	
	
	
	
	
	
	
	public String getOptiondesc() {
		return optiondesc;
	}
	public void setOptiondesc(String optiondesc) {
		this.optiondesc = optiondesc;
	}
	public String getIsright() {
		return isright;
	}
	public void setIsright(String isright) {
		this.isright = isright;
	}
	public String getQuestionid() {
		return questionid;
	}
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}
	public String getOptionid() {
		return optionid;
	}
	public void setOptionid(String optionid) {
		this.optionid = optionid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}

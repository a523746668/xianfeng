package com.qingyii.hxtz.bean;

import java.io.Serializable;

public class choiceOfQuestionInfo implements Serializable{
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
  * 考试题目选项实体类
  */
	private String choiceId;
	private String choiceContent;
	public choiceOfQuestionInfo(String choiceId, String choiceContent) {
		super();
		this.choiceId = choiceId;
		this.choiceContent = choiceContent;
	}
	public String getChoiceId() {
		return choiceId;
	}
	public void setChoiceId(String choiceId) {
		this.choiceId = choiceId;
	}
	public String getChoiceContent() {
		return choiceContent;
	}
	public void setChoiceContent(String choiceContent) {
		this.choiceContent = choiceContent;
	}
	
}

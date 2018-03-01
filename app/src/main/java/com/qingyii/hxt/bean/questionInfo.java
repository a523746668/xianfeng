package com.qingyii.hxt.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class questionInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 考试问题实体类
	 */
	private String title;
	private String url;
	private String questionId;
	private String questionType;
	private String answer;
	private ArrayList<choiceOfQuestionInfo> choices;
	
	public ArrayList<choiceOfQuestionInfo> getChoice() {
		return choices;
	}

	public void setChoice(ArrayList<choiceOfQuestionInfo> choice) {
		this.choices = choice;
	}

	public questionInfo() {
	}
	
	public questionInfo(String title, String url, String questionId,
			String questionType) {
		super();
		this.title = title;
		this.url = url;
		this.questionId = questionId;
		this.questionType = questionType;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
		
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

}

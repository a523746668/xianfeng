package com.qingyii.hxtz.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class researchInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 调查问卷实体类
	 */
	private String researchId;
	private String reserchTitle;
	private String researchType;
	private ArrayList<choiceOfResearchInfo> choiceList;
	private String reserchAnswer;

	public ArrayList<choiceOfResearchInfo> getChoiceList() {
		return choiceList;
	}
	public void setChoiceList(ArrayList<choiceOfResearchInfo> choiceList) {
		this.choiceList = choiceList;
	}
	public String getReserchAnswer() {
		return reserchAnswer;
	}
	public void setReserchAnswer(String reserchAnswer) {
		this.reserchAnswer = reserchAnswer;
	}
	public String getResearchType() {
		return researchType;
	}
	public void setResearchType(String researchType) {
		this.researchType = researchType;
	}
	public String getResearchId() {
		return researchId;
	}
	public void setResearchId(String researchId) {
		this.researchId = researchId;
	}
	public String getReserchTitle() {
		return reserchTitle;
	}
	public void setReserchTitle(String reserchTitle) {
		this.reserchTitle = reserchTitle;
	}
}

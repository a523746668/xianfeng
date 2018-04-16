package com.qingyii.hxtz.pojo;

import java.io.Serializable;

/**
 * 题目实体类
 * @author shelia
 *
 */
public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -815482815744964825L;
	private String questionid;
	private String questiondesc;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String rightanswer1;
	private String rightanswer2;
	private String rightanswer3;
	private String rightanswer4;
	private String createtime;
	/**
	 * 判断是否为单选(1为单选，2为多选,3为判断)
	 */
	private String redios="1";
	/**
	 * 答题是否正确标志：默认未答flase
	 */
	private boolean andserFalg=false;
	/**
	 * 答题确定按钮显示状态：默认显示
	 */
	private boolean showOk=true;
	/**
	 * 未答题在所有题目中的索引位置
	 */
	private int noAnderInext=0;
	public int getNoAnderInext() {
		return noAnderInext;
	}

	public void setNoAnderInext(int noAnderInext) {
		this.noAnderInext = noAnderInext;
	}

	/**
	 * 单选类型/判断题目选中索引:1代表选择一个选项，2第二个
	 */
	private int selectindext=0;
	public int getSelectindext() {
		return selectindext;
	}

	public void setSelectindext(int selectindext) {
		this.selectindext = selectindext;
	}

	public String getCheckBoxSelectindext() {
		return checkBoxSelectindext;
	}

	public void setCheckBoxSelectindext(String checkBoxSelectindext) {
		this.checkBoxSelectindext = checkBoxSelectindext;
	}

	/**
	 * 多选类型题目选择索引串,以逗号分开
	 */
	private String checkBoxSelectindext="";
	public boolean isShowOk() {
		return showOk;
	}

	public void setShowOk(boolean showOk) {
		this.showOk = showOk;
	}

	public String getRedios() {
		return redios;
	}

	public void setRedios(String redios) {
		this.redios = redios;
	}

	/**
	 * 题目类型：1默认单选，2多选择，3判断题
	 */
//	private String questionType="1";
//	public String getQuestionType() {
//		return questionType;
//	}
//
//	public void setQuestionType(String questionType) {
//		this.questionType = questionType;
//	}

	public boolean isAndserFalg() {
		return andserFalg;
	}

	public void setAndserFalg(boolean andserFalg) {
		this.andserFalg = andserFalg;
	}

	public String getQuestionid() {
		return questionid;
	}

	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}

	public String getQuestiondesc() {
		return questiondesc;
	}

	public void setQuestiondesc(String questiondesc) {
		this.questiondesc = questiondesc;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getRightanswer1() {
		return rightanswer1;
	}

	public void setRightanswer1(String rightanswer1) {
		this.rightanswer1 = rightanswer1;
	}

	public String getRightanswer2() {
		return rightanswer2;
	}

	public void setRightanswer2(String rightanswer2) {
		this.rightanswer2 = rightanswer2;
	}

	public String getRightanswer3() {
		return rightanswer3;
	}

	public void setRightanswer3(String rightanswer3) {
		this.rightanswer3 = rightanswer3;
	}

	public String getRightanswer4() {
		return rightanswer4;
	}

	public void setRightanswer4(String rightanswer4) {
		this.rightanswer4 = rightanswer4;
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

	private String createtimeStr;
}

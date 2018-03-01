package com.qingyii.hxt.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 单次命题试卷实体类
 * 
 * @author shelia
 * 
 */
public class PagerQuestionsRela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8257980679557869781L;
	private String paperid;
	private String examinationid;
	/**
	 * 密码锁（单词命题才有）
	 */
	private String passwordlock;
	/**
	 * 考试时长（答题闯关1才有，单位分钟）
	 */
	private String duration;
	/**
	 * 题目数量（答题闯关1才有）
	 */
	private String topiccount;
	/**
	 * 单选题每题分数
	 */
	private String score1;
	/**
	 * 多选题每题分数
	 */
	private String score2;
	/**
	 * 判断题每题分数
	 */
	private String score3;
	/**
	 * 考卷类型（1自主命题，2题库抽选）
	 */
	private String type;
	/**
	 * 题库类型id
	 */
	private String typeid;
	/**
	 * 答题次数
	 */
	private String ansercount;
	public String getAnsercount() {
		return ansercount;
	}

	public void setAnsercount(String ansercount) {
		this.ansercount = ansercount;
	}
	/**
	 * 这套试卷总题目数
	 */
	private int questioncount;
	public int getQuestioncount() {
		return questioncount;
	}

	public void setQuestioncount(int questioncount) {
		this.questioncount = questioncount;
	}
	/**
	 * 题目列表
	 */
	private ArrayList<DitiChuangg> questions = new ArrayList<DitiChuangg>();

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public String getExaminationid() {
		return examinationid;
	}

	public void setExaminationid(String examinationid) {
		this.examinationid = examinationid;
	}

	public String getPasswordlock() {
		return passwordlock;
	}

	public void setPasswordlock(String passwordlock) {
		this.passwordlock = passwordlock;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTopiccount() {
		return topiccount;
	}

	public void setTopiccount(String topiccount) {
		this.topiccount = topiccount;
	}

	public String getScore1() {
		return score1;
	}

	public void setScore1(String score1) {
		this.score1 = score1;
	}

	public String getScore2() {
		return score2;
	}

	public void setScore2(String score2) {
		this.score2 = score2;
	}

	public String getScore3() {
		return score3;
	}

	public void setScore3(String score3) {
		this.score3 = score3;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public ArrayList<DitiChuangg> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<DitiChuangg> questions) {
		this.questions = questions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

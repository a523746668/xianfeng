package com.qingyii.hxt.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class kaoshiInfo implements Serializable {
	/**
	 * 考试实体类
	 */
	private static final long serialVersionUID = 1L;
	private String kaoshiid;
	private String kaoshiname;
	private String time;
	//简介
	private String dec;
	private int num;
	private String url;
	private String type;
	//是否可参与  0 不可参与 1 可参与 2 已结束 3参与过的
	private int joinflag;
//	//分数
	private int score;
	//排名
	private int rank;
	private ArrayList <questionInfo> questions;
	private ArrayList<ExamRankInfo> ranks;
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public ArrayList<questionInfo> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<questionInfo> questions) {
		this.questions = questions;
	}
	public ArrayList<ExamRankInfo> getRanks() {
		return ranks;
	}
	public void setRanks(ArrayList<ExamRankInfo> ranks) {
		this.ranks = ranks;
	}
	public kaoshiInfo() {
	}
	public kaoshiInfo(String id,String name, String time, String dec, int num,
			String url, String type, int joinflag) {
		super();
		this.kaoshiid = id;
		this.kaoshiname = name;
		this.time = time;
		this.dec = dec;
		this.num = num;
		this.url = url;
		this.type = type;
		this.joinflag = joinflag;
	}
	public String getId() {
		return kaoshiid;
	}
	public void setId(String id) {
		this.kaoshiid = id;
	}
//	public int getScore() {
//		return score;
//	}
//	public void setScore(int score) {
//		this.score = score;
//	}
//	public int getRank() {
//		return rank;
//	}
//	public void setRank(int rank) {
//		this.rank = rank;
//	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return kaoshiname;
	}
	public void setName(String name) {
		this.kaoshiname = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDec() {
		return dec;
	}
	public void setDec(String dec) {
		this.dec = dec;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getJoinflag() {
		return joinflag;
	}
	public void setJoinflag(int joinflag) {
		this.joinflag = joinflag;
	}

}

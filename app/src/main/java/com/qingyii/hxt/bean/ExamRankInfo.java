package com.qingyii.hxt.bean;

import java.io.Serializable;

/**
 * 广场积分排行实体类
 * @author Administrator
 *
 */
public class ExamRankInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private int rank;
	private int score;
	public ExamRankInfo() {
	}
	public ExamRankInfo(String name, int rank, int score) {
		super();
		this.name = name;
		this.rank = rank;
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}

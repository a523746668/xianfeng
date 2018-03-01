package com.qingyii.hxt.pojo;

import java.io.Serializable;

public class InvestigationInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -99922991042099304L;
	private String infoid;
	private String investigationid;

	public String getInfoid() {
		return infoid;
	}

	public void setInfoid(String infoid) {
		this.infoid = infoid;
	}

	public String getInvestigationid() {
		return investigationid;
	}

	public void setInvestigationid(String investigationid) {
		this.investigationid = investigationid;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String options;
	private String title;
}

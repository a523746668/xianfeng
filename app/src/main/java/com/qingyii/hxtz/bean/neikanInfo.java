package com.qingyii.hxtz.bean;

public class neikanInfo {
	/**
	 * 内刊实体类
	 */
	private String neikan_title;
	private String neikan_content;
	private String naikan_date;
	private String neikan_img;
	private String neikan_name;
	
	public String getNeikan_name() {
		return neikan_name;
	}


	public void setNeikan_name(String neikan_name) {
		this.neikan_name = neikan_name;
	}


	public String getNeikan_img() {
		return neikan_img;
	}


	public void setNeikan_img(String neikan_img) {
		this.neikan_img = neikan_img;
	}

	public neikanInfo() {
		super();
	}
	public neikanInfo(String neikan_title, String neikan_content,String neikan_name) {
		super();
		this.neikan_title = neikan_title;
		this.neikan_content = neikan_content;
		this.neikan_name=neikan_name;
	}

	
	public String getNaikan_date() {
		return naikan_date;
	}


	public void setNaikan_date(String naikan_date) {
		this.naikan_date = naikan_date;
	}


	public String getNeikan_title() {
		return neikan_title;
	}

	public void setNeikan_title(String neikan_title) {
		this.neikan_title = neikan_title;
	}

	public String getNeikan_content() {
		return neikan_content;
	}

	public void setNeikan_content(String neikan_content) {
		this.neikan_content = neikan_content;
	}
}

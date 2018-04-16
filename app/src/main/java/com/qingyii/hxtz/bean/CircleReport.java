package com.qingyii.hxtz.bean;

import java.io.Serializable;

/**
 * 举报原因实体类
 * 
 * @author Lee
 *
 */
public class CircleReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 342766467677815299L;
	private int typeid;
	private String typename;

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

}

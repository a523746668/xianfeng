package com.qingyii.hxtz.bean;

import java.io.Serializable;

/**
 * 同事圈发布类型
 * @author Lee
 *
 */
public class CircleType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4434791779195359269L;
	int typeid;
	String typename;
	
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

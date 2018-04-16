package com.qingyii.hxtz.bean;

public class dongtaiInfo {
   private String name;
   private String dongtai;
   private String photo;
   
public dongtaiInfo() {
	super();
}
public dongtaiInfo(String name, String dongtai) {
	super();
	this.name = name;
	this.dongtai = dongtai;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDongtai() {
	return dongtai;
}
public void setDongtai(String dongtai) {
	this.dongtai = dongtai;
}
public String getPhoto() {
	return photo;
}
public void setPhoto(String photo) {
	this.photo = photo;
}
}

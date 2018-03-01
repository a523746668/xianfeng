package com.qingyii.hxt.bean;

import com.qingyii.hxt.pojo.User;

import java.io.Serializable;

public class Comment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3419675287830048009L;
	public Comment() {
		
	}
	int commentid;
	User createuser;
	String content;
	Comment refcomment;
	int lovecount;
	int isloved;
	
	public User getCreateuser() {
		return createuser;
	}
	public void setCreateuser(User createuser) {
		this.createuser = createuser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Comment getRefcomment() {
		return refcomment;
	}
	public void setRefcomment(Comment refcomment) {
		this.refcomment = refcomment;
	}
	public int getCommentid() {
		return commentid;
	}
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	public int getLovecount() {
		return lovecount;
	}
	public void setLovecount(int lovecount) {
		this.lovecount = lovecount;
	}
	public int getIsloved() {
		return isloved;
	}
	public void setIsloved(int isloved) {
		this.isloved = isloved;
	}
	
	
	
//	@Override
//	public int describeContents() {
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel arg0, int arg1) {
//		arg0.writeParcelable(createuser, arg1);
//		arg0.writeString(content);
//		arg0.writeParcelable(refcomment, arg1);
//	}
//
//	public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
//
//		@Override
//		public Comment createFromParcel(Parcel arg0) {
//			return new Comment(arg0);
//		}
//
//		@Override
//		public Comment[] newArray(int arg0) {
//			return new Comment[arg0];
//		}
//	};
//
//	public Comment(Parcel parcel) {
//		this.createuser = parcel.readParcelable(User.class.getClassLoader());
//		this.content = parcel.readString();
//		this.refcomment = parcel.readParcelable(Comment.class.getClassLoader());
//	}
	
}

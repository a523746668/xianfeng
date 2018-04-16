package com.qingyii.hxtz.bean;

import com.qingyii.hxtz.pojo.User;

import java.io.Serializable;

public class DynamicInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2658189979911013942L;

	public DynamicInfo() {
		
	}
	
	int dynamicid;
	int createtime;
	String infotypename;
	User createuser;
	String contenttxt;
	String[] contentimageurllist;
	int lovecount;
	int isloved;
	int commentcount;
	int isreported;
	Comment[] commentlist;

	public int getDynamicid() {
		return dynamicid;
	}

	public void setDynamicid(int dynamicid) {
		this.dynamicid = dynamicid;
	}

	public int getCreatetime() {
		return createtime;
	}

	public void setCreatetime(int createtime) {
		this.createtime = createtime;
	}

	public String getInfotypename() {
		return infotypename;
	}

	public void setInfotypename(String infotypename) {
		this.infotypename = infotypename;
	}

	public User getCreateuser() {
		return createuser;
	}

	public void setCreateuser(User createuser) {
		this.createuser = createuser;
	}

	public String getContenttxt() {
		return contenttxt;
	}

	public void setContenttxt(String contenttxt) {
		this.contenttxt = contenttxt;
	}

	public String[] getContentimageurllist() {
		return contentimageurllist;
	}

	public void setContentimageurllist(String[] contentimageurllist) {
		this.contentimageurllist = contentimageurllist;
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

	public int getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}

	public int getIsreported() {
		return isreported;
	}

	public void setIsreported(int isreported) {
		this.isreported = isreported;
	}

	public Comment[] getCommentlist() {
		return commentlist;
	}

	public void setCommentlist(Comment[] commentlist) {
		this.commentlist = commentlist;
	}

//	@Override
//	public int describeContents() {
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel arg0, int arg1) {
//		arg0.writeInt(dynamicid);
//		arg0.writeInt(createtime);
//		arg0.writeString(infotypename);
//		arg0.writeParcelable(createuser, arg1);
//		arg0.writeString(contenttxt);
//		arg0.writeStringArray(contentimageurllist);
//		arg0.writeInt(lovecount);
//		arg0.writeInt(isloved);
//		arg0.writeInt(commentcount);
//		arg0.writeInt(isreported);
//		arg0.writeParcelableArray(commentlist, arg1);
//	}
//
//	public static final Parcelable.Creator<DynamicInfo> CREATOR = new Parcelable.Creator<DynamicInfo>() {
//
//		@Override
//		public DynamicInfo createFromParcel(Parcel arg0) {
//			return new DynamicInfo(arg0);
//		}
//
//		@Override
//		public DynamicInfo[] newArray(int arg0) {
//			return new DynamicInfo[arg0];
//		}
//	};
//
//	public DynamicInfo(Parcel parcel) {
//		this.dynamicid = parcel.readInt();
//		this.createtime = parcel.readInt();
//		this.infotypename = parcel.readString();
//		this.createuser = parcel.readParcelable(User.class.getClassLoader());
//		this.contenttxt = parcel.readString();
//		if (this.contentimageurllist!=null) {
//			parcel.readStringArray(this.contentimageurllist);
//		}
//		this.lovecount = parcel.readInt();
//		this.isloved = parcel.readInt();
//		this.commentcount = parcel.readInt();
//		this.isreported = parcel.readInt();
//		Parcelable[] parcelables = parcel.readParcelableArray(Comment.class
//				.getClassLoader());
////		if (parcelables != null) {
////			this.commentlist = Arrays.copyOf(parcelables, parcelables.length,
////					Comment[].class);
////		}
//	}
}

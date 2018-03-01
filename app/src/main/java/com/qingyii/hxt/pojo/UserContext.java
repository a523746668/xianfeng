package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 63264 on 16/9/26.
 */

public class UserContext implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":3,"username":"杨杰","content":"互动扫地","department":"星沙山水芙蓉天团","account_id":102,"doctypename":"自己提交","locallztion":"","status":4,"upvote":0,"cmtnums":4,"created_at":"2016-08-07 16:57:58","picture":[{"id":4,"uri":"http://xf.ccketang.comu6918988c3.png"}]},"......"]
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 3
     * username : 杨杰
     * content : 互动扫地
     * department : 星沙山水芙蓉天团
     * account_id : 102
     * doctypename : 自己提交
     * locallztion :
     * status : 4
     * upvote : 0
     * cmtnums : 4
     * created_at : 2016-08-07 16:57:58
     * picture : [{"id":4,"uri":"http://xf.ccketang.comu6918988c3.png"}]
     */

    private List<DataBean> data;

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private int id;
        private String username;
        private String content;
        private String department;
        private int account_id;
        private String doctypename;
        private String locallztion;
        private int status;
        private int upvote;
        private int cmtnums;
        private String created_at;
        /**
         * id : 4
         * uri : http://xf.ccketang.comu6918988c3.png
         */

        private List<PictureBean> picture;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public String getDoctypename() {
            return doctypename;
        }

        public void setDoctypename(String doctypename) {
            this.doctypename = doctypename;
        }

        public String getLocallztion() {
            return locallztion;
        }

        public void setLocallztion(String locallztion) {
            this.locallztion = locallztion;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUpvote() {
            return upvote;
        }

        public void setUpvote(int upvote) {
            this.upvote = upvote;
        }

        public int getCmtnums() {
            return cmtnums;
        }

        public void setCmtnums(int cmtnums) {
            this.cmtnums = cmtnums;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public List<PictureBean> getPicture() {
            return picture;
        }

        public void setPicture(List<PictureBean> picture) {
            this.picture = picture;
        }

        public static class PictureBean implements Parcelable {
            private int id;
            private String uri;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.uri);
            }

            public PictureBean() {
            }

            protected PictureBean(Parcel in) {
                this.id = in.readInt();
                this.uri = in.readString();
            }

            public static final Parcelable.Creator<PictureBean> CREATOR = new Parcelable.Creator<PictureBean>() {
                @Override
                public PictureBean createFromParcel(Parcel source) {
                    return new PictureBean(source);
                }

                @Override
                public PictureBean[] newArray(int size) {
                    return new PictureBean[size];
                }
            };
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.username);
            dest.writeString(this.content);
            dest.writeString(this.department);
            dest.writeInt(this.account_id);
            dest.writeString(this.doctypename);
            dest.writeString(this.locallztion);
            dest.writeInt(this.status);
            dest.writeInt(this.upvote);
            dest.writeInt(this.cmtnums);
            dest.writeString(this.created_at);
            dest.writeTypedList(this.picture);
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.username = in.readString();
            this.content = in.readString();
            this.department = in.readString();
            this.account_id = in.readInt();
            this.doctypename = in.readString();
            this.locallztion = in.readString();
            this.status = in.readInt();
            this.upvote = in.readInt();
            this.cmtnums = in.readInt();
            this.created_at = in.readString();
            this.picture = in.createTypedArrayList(PictureBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error_msg);
        dest.writeInt(this.error_code);
        dest.writeTypedList(this.data);
    }

    public UserContext() {
    }

    protected UserContext(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<UserContext> CREATOR = new Parcelable.Creator<UserContext>() {
        @Override
        public UserContext createFromParcel(Parcel source) {
            return new UserContext(source);
        }

        @Override
        public UserContext[] newArray(int size) {
            return new UserContext[size];
        }
    };
}

package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 63264 on 16/9/28.
 */

public class MyUserList implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":3,"username":"杨杰","content":"互动扫地","department":"星沙山水芙蓉天团","doctypename":"自己提交","locallztion":"","status":0,"upvote":0,"cmtnums":0,"created_at":"2016-08-07 16:57:58","checklog":{"id":1,"score":3,"reason":"做事积极","reasonpic":""}}]
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 3
     * username : 杨杰
     * content : 互动扫地
     * department : 星沙山水芙蓉天团
     * doctypename : 自己提交
     * locallztion :
     * status : 0
     * upvote : 0
     * cmtnums : 0
     * created_at : 2016-08-07 16:57:58
     * checklog : {"id":1,"score":3,"reason":"做事积极","reasonpic":""}
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
        private String doctypename;
        private String locallztion;
        private int status;
        private int upvote;
        private int cmtnums;
        private String created_at;
        /**
         * id : 1
         * score : 3
         * reason : 做事积极
         * reasonpic :
         */

        private ChecklogBean checklog;

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

        public ChecklogBean getChecklog() {
            return checklog;
        }

        public void setChecklog(ChecklogBean checklog) {
            this.checklog = checklog;
        }

        public static class ChecklogBean implements Parcelable {
            private int id;
            private int score;
            private String reason;
            private String reasonpic;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getReasonpic() {
                return reasonpic;
            }

            public void setReasonpic(String reasonpic) {
                this.reasonpic = reasonpic;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeInt(this.score);
                dest.writeString(this.reason);
                dest.writeString(this.reasonpic);
            }

            public ChecklogBean() {
            }

            protected ChecklogBean(Parcel in) {
                this.id = in.readInt();
                this.score = in.readInt();
                this.reason = in.readString();
                this.reasonpic = in.readString();
            }

            public static final Parcelable.Creator<ChecklogBean> CREATOR = new Parcelable.Creator<ChecklogBean>() {
                @Override
                public ChecklogBean createFromParcel(Parcel source) {
                    return new ChecklogBean(source);
                }

                @Override
                public ChecklogBean[] newArray(int size) {
                    return new ChecklogBean[size];
                }
            };
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
            dest.writeString(this.doctypename);
            dest.writeString(this.locallztion);
            dest.writeInt(this.status);
            dest.writeInt(this.upvote);
            dest.writeInt(this.cmtnums);
            dest.writeString(this.created_at);
            dest.writeParcelable(this.checklog, flags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.username = in.readString();
            this.content = in.readString();
            this.department = in.readString();
            this.doctypename = in.readString();
            this.locallztion = in.readString();
            this.status = in.readInt();
            this.upvote = in.readInt();
            this.cmtnums = in.readInt();
            this.created_at = in.readString();
            this.checklog = in.readParcelable(ChecklogBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
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

    public MyUserList() {
    }

    protected MyUserList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<MyUserList> CREATOR = new Parcelable.Creator<MyUserList>() {
        @Override
        public MyUserList createFromParcel(Parcel source) {
            return new MyUserList(source);
        }

        @Override
        public MyUserList[] newArray(int size) {
            return new MyUserList[size];
        }
    };
}

package com.qingyii.hxt.bean;

/**
 * 通用参数转型
 */


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by XRJ on 2016/8/22.
 */
//评论返回
public class Comment_circle implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"author":"王斌","content":"啊啊啊吧","parent_id":"609","created_at":"2017-03-06 15:14:55","id":610,"avatar":"","parent_author":"王斌","user":{"id":1077,"email":"13327216391@139.com","truename":"王斌","phone":"13327216391","nickname":"王斌","gender":"male","avatar":"","jobname":"营销总监","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","is_leader":0,"device_id":"13065ffa4e010afbb49"}}
     */

    private String error_msg;
    private int error_code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * author : 王斌
         * content : 啊啊啊吧
         * parent_id : 609
         * created_at : 2017-03-06 15:14:55
         * id : 610
         * avatar :
         * parent_author : 王斌
         * user : {"id":1077,"email":"13327216391@139.com","truename":"王斌","phone":"13327216391","nickname":"王斌","gender":"male","avatar":"","jobname":"营销总监","birthday":"0000-00-00","political":"正式党员","joinparty_at":"0000-00-00","idcode":"","is_leader":0,"device_id":"13065ffa4e010afbb49"}
         */

        private String author;
        private String content;
        private String parent_id;
        private String created_at;
        private int id;
        private String avatar;
        private String parent_author;
        private UserBean user;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getParent_author() {
            return parent_author;
        }

        public void setParent_author(String parent_author) {
            this.parent_author = parent_author;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean implements Parcelable {
            /**
             * id : 1077
             * email : 13327216391@139.com
             * truename : 王斌
             * phone : 13327216391
             * nickname : 王斌
             * gender : male
             * avatar :
             * jobname : 营销总监
             * birthday : 0000-00-00
             * political : 正式党员
             * joinparty_at : 0000-00-00
             * idcode :
             * is_leader : 0
             * device_id : 13065ffa4e010afbb49
             */

            private int id;
            private String email;
            private String truename;
            private String phone;
            private String nickname;
            private String gender;
            private String avatar;
            private String jobname;
            private String birthday;
            private String political;
            private String joinparty_at;
            private String idcode;
            private int is_leader;
            private String device_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getTruename() {
                return truename;
            }

            public void setTruename(String truename) {
                this.truename = truename;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getJobname() {
                return jobname;
            }

            public void setJobname(String jobname) {
                this.jobname = jobname;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getPolitical() {
                return political;
            }

            public void setPolitical(String political) {
                this.political = political;
            }

            public String getJoinparty_at() {
                return joinparty_at;
            }

            public void setJoinparty_at(String joinparty_at) {
                this.joinparty_at = joinparty_at;
            }

            public String getIdcode() {
                return idcode;
            }

            public void setIdcode(String idcode) {
                this.idcode = idcode;
            }

            public int getIs_leader() {
                return is_leader;
            }

            public void setIs_leader(int is_leader) {
                this.is_leader = is_leader;
            }

            public String getDevice_id() {
                return device_id;
            }

            public void setDevice_id(String device_id) {
                this.device_id = device_id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.email);
                dest.writeString(this.truename);
                dest.writeString(this.phone);
                dest.writeString(this.nickname);
                dest.writeString(this.gender);
                dest.writeString(this.avatar);
                dest.writeString(this.jobname);
                dest.writeString(this.birthday);
                dest.writeString(this.political);
                dest.writeString(this.joinparty_at);
                dest.writeString(this.idcode);
                dest.writeInt(this.is_leader);
                dest.writeString(this.device_id);
            }

            public UserBean() {
            }

            protected UserBean(Parcel in) {
                this.id = in.readInt();
                this.email = in.readString();
                this.truename = in.readString();
                this.phone = in.readString();
                this.nickname = in.readString();
                this.gender = in.readString();
                this.avatar = in.readString();
                this.jobname = in.readString();
                this.birthday = in.readString();
                this.political = in.readString();
                this.joinparty_at = in.readString();
                this.idcode = in.readString();
                this.is_leader = in.readInt();
                this.device_id = in.readString();
            }

            public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
                @Override
                public UserBean createFromParcel(Parcel source) {
                    return new UserBean(source);
                }

                @Override
                public UserBean[] newArray(int size) {
                    return new UserBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.author);
            dest.writeString(this.content);
            dest.writeString(this.parent_id);
            dest.writeString(this.created_at);
            dest.writeInt(this.id);
            dest.writeString(this.avatar);
            dest.writeString(this.parent_author);
            dest.writeParcelable(this.user, flags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.author = in.readString();
            this.content = in.readString();
            this.parent_id = in.readString();
            this.created_at = in.readString();
            this.id = in.readInt();
            this.avatar = in.readString();
            this.parent_author = in.readString();
            this.user = in.readParcelable(UserBean.class.getClassLoader());
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
        dest.writeParcelable(this.data, flags);
    }

    public Comment_circle() {
    }

    protected Comment_circle(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Comment_circle> CREATOR = new Parcelable.Creator<Comment_circle>() {
        @Override
        public Comment_circle createFromParcel(Parcel source) {
            return new Comment_circle(source);
        }

        @Override
        public Comment_circle[] newArray(int size) {
            return new Comment_circle[size];
        }
    };
}
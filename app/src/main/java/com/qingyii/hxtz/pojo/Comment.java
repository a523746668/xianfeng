package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 63264 on 16/9/6.
 */

public class Comment implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":245,"magazine_id":0,"author":"邬捷","content":"家里下雪了","upvote":0,"downvote":0,"parent_id":0,"created_at":"2016-11-25 16:23:32","parent_author":"","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","user":{"id":204,"email":"13574221122@139.com","truename":"邬捷","phone":"13574221122","nickname":"邬捷","gender":"secret","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","jobname":"","birthday":"1980-11-22","political":"正式党员","joinparty_at":"2016-11-25","idcode":"","is_leader":1,"device_id":""}},{"id":246,"magazine_id":0,"author":"邬捷","content":"家里下雪了","upvote":0,"downvote":0,"parent_id":0,"created_at":"2016-11-25 16:23:33","parent_author":"","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","user":{"id":204,"email":"13574221122@139.com","truename":"邬捷","phone":"13574221122","nickname":"邬捷","gender":"secret","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","jobname":"","birthday":"1980-11-22","political":"正式党员","joinparty_at":"2016-11-25","idcode":"","is_leader":1,"device_id":""}},{"id":247,"magazine_id":0,"author":"邬捷","content":"阿鲁特","upvote":0,"downvote":0,"parent_id":0,"created_at":"2016-11-25 16:29:24","parent_author":"","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","user":{"id":204,"email":"13574221122@139.com","truename":"邬捷","phone":"13574221122","nickname":"邬捷","gender":"secret","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","jobname":"","birthday":"1980-11-22","political":"正式党员","joinparty_at":"2016-11-25","idcode":"","is_leader":1,"device_id":""}},{"id":249,"magazine_id":0,"author":"邬捷","content":"阿狸在真我","upvote":0,"downvote":0,"parent_id":0,"created_at":"2016-11-25 16:32:40","parent_author":"","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","user":{"id":204,"email":"13574221122@139.com","truename":"邬捷","phone":"13574221122","nickname":"邬捷","gender":"secret","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","jobname":"","birthday":"1980-11-22","political":"正式党员","joinparty_at":"2016-11-25","idcode":"","is_leader":1,"device_id":""}},{"id":250,"magazine_id":0,"author":"邬捷","content":"就可以了","upvote":0,"downvote":0,"parent_id":0,"created_at":"2016-11-25 16:38:24","parent_author":"","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","user":{"id":204,"email":"13574221122@139.com","truename":"邬捷","phone":"13574221122","nickname":"邬捷","gender":"secret","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","jobname":"","birthday":"1980-11-22","political":"正式党员","joinparty_at":"2016-11-25","idcode":"","is_leader":1,"device_id":""}}]
     */

    private String error_msg;
    private int error_code;
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
        /**
         * id : 245
         * magazine_id : 0
         * author : 邬捷
         * content : 家里下雪了
         * upvote : 0
         * downvote : 0
         * parent_id : 0
         * created_at : 2016-11-25 16:23:32
         * parent_author :
         * avatar : http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg
         * user : {"id":204,"email":"13574221122@139.com","truename":"邬捷","phone":"13574221122","nickname":"邬捷","gender":"secret","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","jobname":"","birthday":"1980-11-22","political":"正式党员","joinparty_at":"2016-11-25","idcode":"","is_leader":1,"device_id":""}
         */

        private int id;
        private int magazine_id;
        private String author;
        private String content;
        private int upvote;
        private int downvote;
        private int parent_id;
        private String created_at;
        private String parent_author;
        private String avatar;
        private UserBean user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMagazine_id() {
            return magazine_id;
        }

        public void setMagazine_id(int magazine_id) {
            this.magazine_id = magazine_id;
        }

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

        public int getUpvote() {
            return upvote;
        }

        public void setUpvote(int upvote) {
            this.upvote = upvote;
        }

        public int getDownvote() {
            return downvote;
        }

        public void setDownvote(int downvote) {
            this.downvote = downvote;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getParent_author() {
            return parent_author;
        }

        public void setParent_author(String parent_author) {
            this.parent_author = parent_author;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean implements Parcelable {
            /**
             * id : 204
             * email : 13574221122@139.com
             * truename : 邬捷
             * phone : 13574221122
             * nickname : 邬捷
             * gender : secret
             * avatar : http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg
             * jobname :
             * birthday : 1980-11-22
             * political : 正式党员
             * joinparty_at : 2016-11-25
             * idcode :
             * is_leader : 1
             * device_id :
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
            dest.writeInt(this.id);
            dest.writeInt(this.magazine_id);
            dest.writeString(this.author);
            dest.writeString(this.content);
            dest.writeInt(this.upvote);
            dest.writeInt(this.downvote);
            dest.writeInt(this.parent_id);
            dest.writeString(this.created_at);
            dest.writeString(this.parent_author);
            dest.writeString(this.avatar);
            dest.writeParcelable(this.user, flags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.magazine_id = in.readInt();
            this.author = in.readString();
            this.content = in.readString();
            this.upvote = in.readInt();
            this.downvote = in.readInt();
            this.parent_id = in.readInt();
            this.created_at = in.readString();
            this.parent_author = in.readString();
            this.avatar = in.readString();
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
        dest.writeTypedList(this.data);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}

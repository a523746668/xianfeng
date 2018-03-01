package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 同事圈内容
 */
public class Associates implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"company":3,"department":3,"follow":2,"documentary":7,"comment":"82","upvote":"65","do_upvote":0,"docs":[{"id":8,"username":"杨杰","content":"654536534","department":"星沙山水芙蓉天团","account_id":102,"doctypename":"考古纪实","locallztion":"","status":3,"upvote":21,"cmtnums":23,"is_upvote":23,"created_at":"2015-09-14 15:03:52","comments":[{"id":3,"author":"789","content":"7777777","upvote":0,"downvote":0,"parent_id":5,"parent_author":"杨姐","created_at":"2016-08-19 14:31:40"}],"picture":[{"id":3,"uri":"http://xf.ccketang.com/fadsfsad.png"}]}]}
     */

    private String error_msg;
    private int error_code;
    /**
     * company : 3
     * department : 3
     * follow : 2
     * documentary : 7
     * comment : 82
     * upvote : 65
     * do_upvote : 0
     * docs : [{"id":8,"username":"杨杰","content":"654536534","department":"星沙山水芙蓉天团","account_id":102,"doctypename":"考古纪实","locallztion":"","status":3,"upvote":21,"cmtnums":23,"is_upvote":23,"created_at":"2015-09-14 15:03:52","comments":[{"id":3,"author":"789","content":"7777777","upvote":0,"downvote":0,"parent_id":5,"parent_author":"杨姐","created_at":"2016-08-19 14:31:40"}],"picture":[{"id":3,"uri":"http://xf.ccketang.com/fadsfsad.png"}]}]
     * user : {"avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","department":"党群工作部","truename":"邬捷"}
     */

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
        private int company;
        private int department;
        private int follow;
        private int documentary;
        private String comment;
        private String upvote;
        private int do_upvote;
        private int stars;

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        /**
         * id : 8
         * username : 杨杰
         * avatar : "图片地址"
         * content : 654536534
         * department : 星沙山水芙蓉天团
         * account_id : 102
         * doctypename : 考古纪实
         * locallztion :
         * status : 3
         * upvote : 21
         * cmtnums : 23
         * is_upvote : 23
         * created_at : 2015-09-14 15:03:52
         * comments : [{"id":3,"author":"789","content":"7777777","upvote":0,"downvote":0,"parent_id":5,"parent_author":"杨姐","created_at":"2016-08-19 14:31:40"}]
         * picture : [{"id":3,"uri":"http://xf.ccketang.com/fadsfsad.png"}]
         */

        private UserBean user;
        private List<DocsBean> docs;

        public int getCompany() {
            return company;
        }

        public void setCompany(int company) {
            this.company = company;
        }

        public int getDepartment() {
            return department;
        }

        public void setDepartment(int department) {
            this.department = department;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public int getDocumentary() {
            return documentary;
        }

        public void setDocumentary(int documentary) {
            this.documentary = documentary;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getUpvote() {
            return upvote;
        }

        public void setUpvote(String upvote) {
            this.upvote = upvote;
        }

        public int getDo_upvote() {
            return do_upvote;
        }

        public void setDo_upvote(int do_upvote) {
            this.do_upvote = do_upvote;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<DocsBean> getDocs() {
            return docs;
        }

        public void setDocs(List<DocsBean> docs) {
            this.docs = docs;
        }

        public static class UserBean implements Parcelable {
            /**
             * avatar : http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg
             * department : 党群工作部
             * truename : 邬捷
             */

            private String avatar;
            private String department;
            private String truename;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getDepartment() {
                return department;
            }

            public void setDepartment(String department) {
                this.department = department;
            }

            public String getTruename() {
                return truename;
            }

            public void setTruename(String truename) {
                this.truename = truename;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.avatar);
                dest.writeString(this.department);
                dest.writeString(this.truename);
            }

            public UserBean() {
            }

            protected UserBean(Parcel in) {
                this.avatar = in.readString();
                this.department = in.readString();
                this.truename = in.readString();
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

        public static class DocsBean implements Parcelable {
            private int id;
            private String username;
            private String avatar;
            private int user_id;
            private String content;
            private String department;
            private int account_id;
            private String doctypename;
            private String locallztion;
            private int status;
            private int upvote;
            private int cmtnums;
            private int is_upvote;
            private String created_at;
            /**
             * id : 3
             * author : 789
             * content : 7777777
             * upvote : 0
             * downvote : 0
             * parent_id : 5
             * parent_author : 杨姐
             * created_at : 2016-08-19 14:31:40
             */

            private List<CommentsBean> comments;
            /**
             * id : 3
             * uri : http://xf.ccketang.com/fadsfsad.png
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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
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

            public int getIs_upvote() {
                return is_upvote;
            }

            public void setIs_upvote(int is_upvote) {
                this.is_upvote = is_upvote;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public List<CommentsBean> getComments() {
                return comments;
            }

            public void setComments(List<CommentsBean> comments) {
                this.comments = comments;
            }

            public List<PictureBean> getPicture() {
                return picture;
            }

            public void setPicture(List<PictureBean> picture) {
                this.picture = picture;
            }

            public static class CommentsBean implements Parcelable {
                private int id;
                private String author;
                private String content;
                private int upvote;
                private int downvote;
                private String parent_id;
                private String parent_author;
                private String created_at;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
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

                public String getParent_id() {
                    return parent_id;
                }

                public void setParent_id(String parent_id) {
                    this.parent_id = parent_id;
                }

                public String getParent_author() {
                    return parent_author;
                }

                public void setParent_author(String parent_author) {
                    this.parent_author = parent_author;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }

                public CommentsBean() {
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.id);
                    dest.writeString(this.author);
                    dest.writeString(this.content);
                    dest.writeInt(this.upvote);
                    dest.writeInt(this.downvote);
                    dest.writeString(this.parent_id);
                    dest.writeString(this.parent_author);
                    dest.writeString(this.created_at);
                }

                protected CommentsBean(Parcel in) {
                    this.id = in.readInt();
                    this.author = in.readString();
                    this.content = in.readString();
                    this.upvote = in.readInt();
                    this.downvote = in.readInt();
                    this.parent_id = in.readString();
                    this.parent_author = in.readString();
                    this.created_at = in.readString();
                }

                public static final Creator<CommentsBean> CREATOR = new Creator<CommentsBean>() {
                    @Override
                    public CommentsBean createFromParcel(Parcel source) {
                        return new CommentsBean(source);
                    }

                    @Override
                    public CommentsBean[] newArray(int size) {
                        return new CommentsBean[size];
                    }
                };
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

            public DocsBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.username);
                dest.writeString(this.avatar);
                dest.writeInt(this.user_id);
                dest.writeString(this.content);
                dest.writeString(this.department);
                dest.writeInt(this.account_id);
                dest.writeString(this.doctypename);
                dest.writeString(this.locallztion);
                dest.writeInt(this.status);
                dest.writeInt(this.upvote);
                dest.writeInt(this.cmtnums);
                dest.writeInt(this.is_upvote);
                dest.writeString(this.created_at);
                dest.writeTypedList(this.comments);
                dest.writeTypedList(this.picture);
            }

            protected DocsBean(Parcel in) {
                this.id = in.readInt();
                this.username = in.readString();
                this.avatar = in.readString();
                this.user_id = in.readInt();
                this.content = in.readString();
                this.department = in.readString();
                this.account_id = in.readInt();
                this.doctypename = in.readString();
                this.locallztion = in.readString();
                this.status = in.readInt();
                this.upvote = in.readInt();
                this.cmtnums = in.readInt();
                this.is_upvote = in.readInt();
                this.created_at = in.readString();
                this.comments = in.createTypedArrayList(CommentsBean.CREATOR);
                this.picture = in.createTypedArrayList(PictureBean.CREATOR);
            }

            public static final Creator<DocsBean> CREATOR = new Creator<DocsBean>() {
                @Override
                public DocsBean createFromParcel(Parcel source) {
                    return new DocsBean(source);
                }

                @Override
                public DocsBean[] newArray(int size) {
                    return new DocsBean[size];
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
            dest.writeInt(this.company);
            dest.writeInt(this.department);
            dest.writeInt(this.follow);
            dest.writeInt(this.documentary);
            dest.writeString(this.comment);
            dest.writeString(this.upvote);
            dest.writeInt(this.do_upvote);
            dest.writeInt(this.stars);
            dest.writeParcelable(this.user, flags);
            dest.writeTypedList(this.docs);
        }

        protected DataBean(Parcel in) {
            this.company = in.readInt();
            this.department = in.readInt();
            this.follow = in.readInt();
            this.documentary = in.readInt();
            this.comment = in.readString();
            this.upvote = in.readString();
            this.do_upvote = in.readInt();
            this.stars = in.readInt();
            this.user = in.readParcelable(UserBean.class.getClassLoader());
            this.docs = in.createTypedArrayList(DocsBean.CREATOR);
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
        dest.writeParcelable(this.data, flags);
    }

    public Associates() {
    }

    protected Associates(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Associates> CREATOR = new Parcelable.Creator<Associates>() {
        @Override
        public Associates createFromParcel(Parcel source) {
            return new Associates(source);
        }

        @Override
        public Associates[] newArray(int size) {
            return new Associates[size];
        }
    };
}

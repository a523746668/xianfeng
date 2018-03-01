package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 63264 on 16/11/13.
 */

public class WaitAuditList implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":127,"user_id":155,"username":"张莉","content":"10月25日，刘霞参加西站收费站开展的\u201c学党章规则，学系列讲话\u201d教育活动","department":"冠市收费站","doctypename":"学习","locallztion":"","status":1,"upvote":0,"cmtnums":0,"source":33,"month":"201610","created_at":"2016-10-06 13:00:12","picture":[{"id":149,"uri":"http://xf.ccketang.com/upload/documentary/20161113/89d1cc37dc1964314dac432c3c2edcba.png"}],"checklogs":[{"id":135,"score":3,"reason":"输入审核依据，可留空","reasonpic":"","tag":"初审","appeals":{"reason":"我的心"}}]},{"id":128,"user_id":155,"username":"张莉","content":"在分公司团委组织下，参加了：争做时代好员工，勇当新现代排头兵活动\u201d。","department":"冠市收费站","doctypename":"活动","locallztion":"","status":2,"upvote":0,"cmtnums":0,"source":33,"month":"201610","created_at":"2016-10-07 13:00:45","picture":[{"id":150,"uri":"http://xf.ccketang.com/upload/documentary/20161113/2ce25e6d0d9f998ec748731c6dad03a0.png"}],"checklogs":[{"id":136,"score":2,"reason":"反光板","reasonpic":"","tag":"初审","appeals":{"reason":"改一下"}},{"id":146,"score":3,"reason":"腻害","reasonpic":"","tag":"复核","appeals":null}]},{"id":126,"user_id":155,"username":"张莉","content":"10月28日，在党支部收费工作中保持良好的精神面貌，获得驾驶司机的好评。","department":"冠市收费站","doctypename":"履职","locallztion":"","status":3,"upvote":0,"cmtnums":0,"source":33,"month":"201610","created_at":"2016-10-05 12:59:35","picture":[{"id":148,"uri":"http://xf.ccketang.com/upload/documentary/20161113/bee8d76f479ee7abf098c5a44a50fe35.png"}],"checklogs":[{"id":134,"score":2,"reason":"法官","reasonpic":"","tag":"初审","appeals":null}]}]
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
         * id : 127
         * user_id : 155
         * username : 张莉
         * content : 10月25日，刘霞参加西站收费站开展的“学党章规则，学系列讲话”教育活动
         * department : 冠市收费站
         * doctypename : 学习
         * locallztion :
         * status : 1
         * upvote : 0
         * cmtnums : 0
         * source : 33
         * month : 201610
         * created_at : 2016-10-06 13:00:12
         * in_appeal: 1
         * appeal_status: report
         * tags: "管理员添加"
         * picture : [{"id":149,"uri":"http://xf.ccketang.com/upload/documentary/20161113/89d1cc37dc1964314dac432c3c2edcba.png"}]
         * checklogs : [{"id":135,"score":3,"reason":"输入审核依据，可留空","reasonpic":"","tag":"初审","appeals":{"reason":"我的心"}}]
         */

        private int id;
        private int user_id;
        private String username;
        private String content;
        private String department;
        private String doctypename;
        private String locallztion;
        private int status;
        private int upvote;
        private int cmtnums;
        private int source;
        private String month;
        private String created_at;
        private int in_appeal;
        private String appeal_status;
        private String tags;
        private List<PictureBean> picture;
        private List<ChecklogsBean> checklogs;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getIn_appeal() {
            return in_appeal;
        }

        public void setIn_appeal(int in_appeal) {
            this.in_appeal = in_appeal;
        }

        public String getAppeal_status() {
            return appeal_status;
        }

        public void setAppeal_status(String appeal_status) {
            this.appeal_status = appeal_status;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public List<PictureBean> getPicture() {
            return picture;
        }

        public void setPicture(List<PictureBean> picture) {
            this.picture = picture;
        }

        public List<ChecklogsBean> getChecklogs() {
            return checklogs;
        }

        public void setChecklogs(List<ChecklogsBean> checklogs) {
            this.checklogs = checklogs;
        }

        public static class PictureBean implements Parcelable {
            /**
             * id : 149
             * uri : http://xf.ccketang.com/upload/documentary/20161113/89d1cc37dc1964314dac432c3c2edcba.png
             */

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

            public static final Creator<PictureBean> CREATOR = new Creator<PictureBean>() {
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

        public static class ChecklogsBean implements Parcelable {
            /**
             * id : 135
             * score : 3
             * reason : 输入审核依据，可留空
             * reasonpic :
             * tag : 初审
             * appeals : {"reason":"我的心"}
             */

            private int id;
            private double score;
            private String reason;
            private String reasonpic;
            private String tag;
            private AppealsBean appeals;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
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

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public AppealsBean getAppeals() {
                return appeals;
            }

            public void setAppeals(AppealsBean appeals) {
                this.appeals = appeals;
            }

            public static class AppealsBean implements Parcelable {
                /**
                 * reason : 我的心
                 * result: 已上报
                 */

                private String reason;
                private String result;

                public String getReason() {
                    return reason;
                }

                public void setReason(String reason) {
                    this.reason = reason;
                }

                public String getResult() {
                    return result;
                }

                public void setResult(String result) {
                    this.result = result;
                }

                public AppealsBean() {
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.reason);
                    dest.writeString(this.result);
                }

                protected AppealsBean(Parcel in) {
                    this.reason = in.readString();
                    this.result = in.readString();
                }

                public static final Creator<AppealsBean> CREATOR = new Creator<AppealsBean>() {
                    @Override
                    public AppealsBean createFromParcel(Parcel source) {
                        return new AppealsBean(source);
                    }

                    @Override
                    public AppealsBean[] newArray(int size) {
                        return new AppealsBean[size];
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
                dest.writeDouble(this.score);
                dest.writeString(this.reason);
                dest.writeString(this.reasonpic);
                dest.writeString(this.tag);
                dest.writeParcelable(this.appeals, flags);
            }

            public ChecklogsBean() {
            }

            protected ChecklogsBean(Parcel in) {
                this.id = in.readInt();
                this.score = in.readDouble();
                this.reason = in.readString();
                this.reasonpic = in.readString();
                this.tag = in.readString();
                this.appeals = in.readParcelable(AppealsBean.class.getClassLoader());
            }

            public static final Parcelable.Creator<ChecklogsBean> CREATOR = new Parcelable.Creator<ChecklogsBean>() {
                @Override
                public ChecklogsBean createFromParcel(Parcel source) {
                    return new ChecklogsBean(source);
                }

                @Override
                public ChecklogsBean[] newArray(int size) {
                    return new ChecklogsBean[size];
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
            dest.writeInt(this.user_id);
            dest.writeString(this.username);
            dest.writeString(this.content);
            dest.writeString(this.department);
            dest.writeString(this.doctypename);
            dest.writeString(this.locallztion);
            dest.writeInt(this.status);
            dest.writeInt(this.upvote);
            dest.writeInt(this.cmtnums);
            dest.writeInt(this.source);
            dest.writeString(this.month);
            dest.writeString(this.created_at);
            dest.writeInt(this.in_appeal);
            dest.writeString(this.appeal_status);
            dest.writeString(this.tags);
            dest.writeTypedList(this.picture);
            dest.writeTypedList(this.checklogs);
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.user_id = in.readInt();
            this.username = in.readString();
            this.content = in.readString();
            this.department = in.readString();
            this.doctypename = in.readString();
            this.locallztion = in.readString();
            this.status = in.readInt();
            this.upvote = in.readInt();
            this.cmtnums = in.readInt();
            this.source = in.readInt();
            this.month = in.readString();
            this.created_at = in.readString();
            this.in_appeal = in.readInt();
            this.appeal_status = in.readString();
            this.tags = in.readString();
            this.picture = in.createTypedArrayList(PictureBean.CREATOR);
            this.checklogs = in.createTypedArrayList(ChecklogsBean.CREATOR);
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

    public WaitAuditList() {
    }

    protected WaitAuditList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<WaitAuditList> CREATOR = new Parcelable.Creator<WaitAuditList>() {
        @Override
        public WaitAuditList createFromParcel(Parcel source) {
            return new WaitAuditList(source);
        }

        @Override
        public WaitAuditList[] newArray(int size) {
            return new WaitAuditList[size];
        }
    };
}

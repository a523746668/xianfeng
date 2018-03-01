package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by XRJ on 16/11/11.
 */

public class DocumentaryMy implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"avatar":"http://xf.ccketang.com/upload/avatar/20161113/67c6a1e7ce56d3d6fa748ab6d9af3fd7.jpg","truename":"杨杰","company":"星沙山水芙蓉天团","department":"技术部","check_level":1,"last_month_stars":4,"company_rank":3,"department_rank":2,"score":{"累计积分":510,"履职积分":20,"学习积分":10,"纪律积分":10,"品德积分":10},"stars_num":{"纪实总人数":59,"四星党员":0,"三星党员":1,"二星党员":1,"一星党员":1,"无星党员":1,"橙色警示":1,"黄色警示":1,"不合格":1,"四星比例":"0%"},"punishtype":0,"punishreason":"低于基础分","docs_cnt":0,"wait_check":0,"check_month":["2016-06","2016-07","2016-08","2016-09"],"curr_month":"2016-09"}
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
         * avatar : http://xf.ccketang.com/upload/avatar/20161113/67c6a1e7ce56d3d6fa748ab6d9af3fd7.jpg
         * truename : 杨杰
         * company : 星沙山水芙蓉天团
         * department : 技术部
         * check_level : 1
         * last_month_stars : 4
         * company_rank : 3
         * department_rank : 2
         * score : {"累计积分":510,"履职积分":20,"学习积分":10,"纪律积分":10,"品德积分":10}
         * stars_num : {"纪实总人数":59,"四星党员":0,"三星党员":1,"二星党员":1,"一星党员":1,"无星党员":1,"橙色警示":1,"黄色警示":1,"不合格":1,"四星比例":"0%"}
         * punishtype : 0
         * punishreason : 低于基础分
         * docs_cnt : 0
         * wait_check : 0
         * check_month : ["2016-06","2016-07","2016-08","2016-09"]
         * curr_month : 2016-09
         */

        private String avatar;
        private String truename;
        private String company;
        private String jobname;
        private String department;
        private int check_level;
        private int last_month_stars;
        private int company_rank;
        private int department_rank;
        private ScoreBean score;
        private StarsNumBean stars_num;
        private String punishtype;
        private String punishreason;
        private int docs_cnt;
        private int wait_check;
        private String curr_month;
        private List<String> check_month;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getJobname() {
            return jobname;
        }

        public void setJobname(String jobname) {
            this.jobname = jobname;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getCheck_level() {
            return check_level;
        }

        public void setCheck_level(int check_level) {
            this.check_level = check_level;
        }

        public int getLast_month_stars() {
            return last_month_stars;
        }

        public void setLast_month_stars(int last_month_stars) {
            this.last_month_stars = last_month_stars;
        }

        public int getCompany_rank() {
            return company_rank;
        }

        public void setCompany_rank(int company_rank) {
            this.company_rank = company_rank;
        }

        public int getDepartment_rank() {
            return department_rank;
        }

        public void setDepartment_rank(int department_rank) {
            this.department_rank = department_rank;
        }

        public ScoreBean getScore() {
            return score;
        }

        public void setScore(ScoreBean score) {
            this.score = score;
        }

        public StarsNumBean getStars_num() {
            return stars_num;
        }

        public void setStars_num(StarsNumBean stars_num) {
            this.stars_num = stars_num;
        }

        public String getPunishtype() {
            return punishtype;
        }

        public void setPunishtype(String punishtype) {
            this.punishtype = punishtype;
        }

        public String getPunishreason() {
            return punishreason;
        }

        public void setPunishreason(String punishreason) {
            this.punishreason = punishreason;
        }

        public int getDocs_cnt() {
            return docs_cnt;
        }

        public void setDocs_cnt(int docs_cnt) {
            this.docs_cnt = docs_cnt;
        }

        public int getWait_check() {
            return wait_check;
        }

        public void setWait_check(int wait_check) {
            this.wait_check = wait_check;
        }

        public String getCurr_month() {
            return curr_month;
        }

        public void setCurr_month(String curr_month) {
            this.curr_month = curr_month;
        }

        public List<String> getCheck_month() {
            return check_month;
        }

        public void setCheck_month(List<String> check_month) {
            this.check_month = check_month;
        }

        public static class ScoreBean implements Parcelable {
            /**
             * 累计积分 : 510
             * 作用积分 : 20
             * 学习积分 : 10
             * 纪律积分 : 10
             * 品德积分 : 10
             */

            private String 累计积分;
            private String 作用积分;
            private String 政治积分;
            private String 纪律积分;
            private String 品德积分;

            public String get累计积分() {
                return 累计积分;
            }

            public void set累计积分(String 累计积分) {
                this.累计积分 = 累计积分;
            }

            public String get作用积分() {
                return 作用积分;
            }

            public void set作用积分(String 作用积分) {
                this.作用积分 = 作用积分;
            }

            public String get政治积分() {
                return 政治积分;
            }

            public void set政治积分(String 政治积分) {
                this.政治积分 = 政治积分;
            }

            public String get纪律积分() {
                return 纪律积分;
            }

            public void set纪律积分(String 纪律积分) {
                this.纪律积分 = 纪律积分;
            }

            public String get品德积分() {
                return 品德积分;
            }

            public void set品德积分(String 品德积分) {
                this.品德积分 = 品德积分;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.累计积分);
                dest.writeString(this.作用积分);
                dest.writeString(this.政治积分);
                dest.writeString(this.纪律积分);
                dest.writeString(this.品德积分);
            }

            public ScoreBean() {
            }

            protected ScoreBean(Parcel in) {
                this.累计积分 = in.readString();
                this.作用积分 = in.readString();
                this.政治积分 = in.readString();
                this.纪律积分 = in.readString();
                this.品德积分 = in.readString();
            }

            public static final Creator<ScoreBean> CREATOR = new Creator<ScoreBean>() {
                @Override
                public ScoreBean createFromParcel(Parcel source) {
                    return new ScoreBean(source);
                }

                @Override
                public ScoreBean[] newArray(int size) {
                    return new ScoreBean[size];
                }
            };
        }

        public static class StarsNumBean implements Parcelable {
            /**
             * 纪实总人数 : 59
             * 四星党员 : 0
             * 三星党员 : 1
             * 二星党员 : 1
             * 一星党员 : 1
             * 无星党员 : 1
             * 橙色警示 : 1
             * 黄色警示 : 1
             * 不合格 : 1
             * 四星比例 : 0%
             */

            private int 纪实总人数;
            private int 四星党员;
            private int 三星党员;
            private int 二星党员;
            private int 一星党员;
            private int 无星党员;
            private int 橙色警示;
            private int 黄色警示;
            private int 不合格;
            private String 四星比例;

            public int get纪实总人数() {
                return 纪实总人数;
            }

            public void set纪实总人数(int 纪实总人数) {
                this.纪实总人数 = 纪实总人数;
            }

            public int get四星党员() {
                return 四星党员;
            }

            public void set四星党员(int 四星党员) {
                this.四星党员 = 四星党员;
            }

            public int get三星党员() {
                return 三星党员;
            }

            public void set三星党员(int 三星党员) {
                this.三星党员 = 三星党员;
            }

            public int get二星党员() {
                return 二星党员;
            }

            public void set二星党员(int 二星党员) {
                this.二星党员 = 二星党员;
            }

            public int get一星党员() {
                return 一星党员;
            }

            public void set一星党员(int 一星党员) {
                this.一星党员 = 一星党员;
            }

            public int get无星党员() {
                return 无星党员;
            }

            public void set无星党员(int 无星党员) {
                this.无星党员 = 无星党员;
            }

            public int get橙色警示() {
                return 橙色警示;
            }

            public void set橙色警示(int 橙色警示) {
                this.橙色警示 = 橙色警示;
            }

            public int get黄色警示() {
                return 黄色警示;
            }

            public void set黄色警示(int 黄色警示) {
                this.黄色警示 = 黄色警示;
            }

            public int get不合格() {
                return 不合格;
            }

            public void set不合格(int 不合格) {
                this.不合格 = 不合格;
            }

            public String get四星比例() {
                return 四星比例;
            }

            public void set四星比例(String 四星比例) {
                this.四星比例 = 四星比例;
            }

            public StarsNumBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.纪实总人数);
                dest.writeInt(this.四星党员);
                dest.writeInt(this.三星党员);
                dest.writeInt(this.二星党员);
                dest.writeInt(this.一星党员);
                dest.writeInt(this.无星党员);
                dest.writeInt(this.橙色警示);
                dest.writeInt(this.黄色警示);
                dest.writeInt(this.不合格);
                dest.writeString(this.四星比例);
            }

            protected StarsNumBean(Parcel in) {
                this.纪实总人数 = in.readInt();
                this.四星党员 = in.readInt();
                this.三星党员 = in.readInt();
                this.二星党员 = in.readInt();
                this.一星党员 = in.readInt();
                this.无星党员 = in.readInt();
                this.橙色警示 = in.readInt();
                this.黄色警示 = in.readInt();
                this.不合格 = in.readInt();
                this.四星比例 = in.readString();
            }

            public static final Creator<StarsNumBean> CREATOR = new Creator<StarsNumBean>() {
                @Override
                public StarsNumBean createFromParcel(Parcel source) {
                    return new StarsNumBean(source);
                }

                @Override
                public StarsNumBean[] newArray(int size) {
                    return new StarsNumBean[size];
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
            dest.writeString(this.avatar);
            dest.writeString(this.truename);
            dest.writeString(this.company);
            dest.writeString(this.jobname);
            dest.writeString(this.department);
            dest.writeInt(this.check_level);
            dest.writeInt(this.last_month_stars);
            dest.writeInt(this.company_rank);
            dest.writeInt(this.department_rank);
            dest.writeParcelable(this.score, flags);
            dest.writeParcelable(this.stars_num, flags);
            dest.writeString(this.punishtype);
            dest.writeString(this.punishreason);
            dest.writeInt(this.docs_cnt);
            dest.writeInt(this.wait_check);
            dest.writeString(this.curr_month);
            dest.writeStringList(this.check_month);
        }

        protected DataBean(Parcel in) {
            this.avatar = in.readString();
            this.truename = in.readString();
            this.company = in.readString();
            this.jobname = in.readString();
            this.department = in.readString();
            this.check_level = in.readInt();
            this.last_month_stars = in.readInt();
            this.company_rank = in.readInt();
            this.department_rank = in.readInt();
            this.score = in.readParcelable(ScoreBean.class.getClassLoader());
            this.stars_num = in.readParcelable(StarsNumBean.class.getClassLoader());
            this.punishtype = in.readString();
            this.punishreason = in.readString();
            this.docs_cnt = in.readInt();
            this.wait_check = in.readInt();
            this.curr_month = in.readString();
            this.check_month = in.createStringArrayList();
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

    public DocumentaryMy() {
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

    protected DocumentaryMy(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Creator<DocumentaryMy> CREATOR = new Creator<DocumentaryMy>() {
        @Override
        public DocumentaryMy createFromParcel(Parcel source) {
            return new DocumentaryMy(source);
        }

        @Override
        public DocumentaryMy[] newArray(int size) {
            return new DocumentaryMy[size];
        }
    };
}

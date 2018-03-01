package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 63264 on 16/12/8.
 */

public class ExamDetails implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"id":35,"examname":"测试累计闯关","examtype":"accrued","description":"闯关考试做下测试","begintime":1451322000,"endtime":1482776400,"answertimes":1000,"duration":0,"alluser_nums":0,"answered_nums":0,"stages":2,"passsore":60,"showerrors":1,"showanswer":0,"joincount":1,"last_pass_stages":1,"company":{"id":0,"name":"系统","logo":""}}
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
         * id : 35
         * examname : 测试累计闯关
         * examtype : accrued
         * description : 闯关考试做下测试
         * begintime : 1451322000
         * endtime : 1482776400
         * answertimes : 1000
         * duration : 0
         * alluser_nums : 0
         * answered_nums : 0
         * stages : 2
         * passsore : 60
         * showerrors : 1
         * showanswer : 0
         * joincount : 1
         * last_pass_stages : 1
         * company : {"id":0,"name":"系统","logo":""}
         */

        private int id;
        private String examname;
        private String examtype;
        private String description;
        private long begintime;
        private long endtime;
        private int answertimes;
        private int duration;
        private int alluser_nums;
        private int answered_nums;
        private int stages;
        private int passsore;
        private int showerrors;
        private int showanswer;
        private int joincount;
        private int last_pass_stages;
        private CompanyBean company;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getExamname() {
            return examname;
        }

        public void setExamname(String examname) {
            this.examname = examname;
        }

        public String getExamtype() {
            return examtype;
        }

        public void setExamtype(String examtype) {
            this.examtype = examtype;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getBegintime() {
            return begintime;
        }

        public void setBegintime(long begintime) {
            this.begintime = begintime;
        }

        public long getEndtime() {
            return endtime;
        }

        public void setEndtime(long endtime) {
            this.endtime = endtime;
        }

        public int getAnswertimes() {
            return answertimes;
        }

        public void setAnswertimes(int answertimes) {
            this.answertimes = answertimes;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getAlluser_nums() {
            return alluser_nums;
        }

        public void setAlluser_nums(int alluser_nums) {
            this.alluser_nums = alluser_nums;
        }

        public int getAnswered_nums() {
            return answered_nums;
        }

        public void setAnswered_nums(int answered_nums) {
            this.answered_nums = answered_nums;
        }

        public int getStages() {
            return stages;
        }

        public void setStages(int stages) {
            this.stages = stages;
        }

        public int getPasssore() {
            return passsore;
        }

        public void setPasssore(int passsore) {
            this.passsore = passsore;
        }

        public int getShowerrors() {
            return showerrors;
        }

        public void setShowerrors(int showerrors) {
            this.showerrors = showerrors;
        }

        public int getShowanswer() {
            return showanswer;
        }

        public void setShowanswer(int showanswer) {
            this.showanswer = showanswer;
        }

        public int getJoincount() {
            return joincount;
        }

        public void setJoincount(int joincount) {
            this.joincount = joincount;
        }

        public int getLast_pass_stages() {
            return last_pass_stages;
        }

        public void setLast_pass_stages(int last_pass_stages) {
            this.last_pass_stages = last_pass_stages;
        }

        public CompanyBean getCompany() {
            return company;
        }

        public void setCompany(CompanyBean company) {
            this.company = company;
        }

        public static class CompanyBean implements Parcelable {
            /**
             * id : 0
             * name : 系统
             * logo :
             */

            private int id;
            private String name;
            private String logo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeString(this.logo);
            }

            public CompanyBean() {
            }

            protected CompanyBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.logo = in.readString();
            }

            public static final Parcelable.Creator<CompanyBean> CREATOR = new Parcelable.Creator<CompanyBean>() {
                @Override
                public CompanyBean createFromParcel(Parcel source) {
                    return new CompanyBean(source);
                }

                @Override
                public CompanyBean[] newArray(int size) {
                    return new CompanyBean[size];
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
            dest.writeString(this.examname);
            dest.writeString(this.examtype);
            dest.writeString(this.description);
            dest.writeLong(this.begintime);
            dest.writeLong(this.endtime);
            dest.writeInt(this.answertimes);
            dest.writeInt(this.duration);
            dest.writeInt(this.alluser_nums);
            dest.writeInt(this.answered_nums);
            dest.writeInt(this.stages);
            dest.writeInt(this.passsore);
            dest.writeInt(this.showerrors);
            dest.writeInt(this.showanswer);
            dest.writeInt(this.joincount);
            dest.writeInt(this.last_pass_stages);
            dest.writeParcelable(this.company, flags);
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.examname = in.readString();
            this.examtype = in.readString();
            this.description = in.readString();
            this.begintime = in.readLong();
            this.endtime = in.readLong();
            this.answertimes = in.readInt();
            this.duration = in.readInt();
            this.alluser_nums = in.readInt();
            this.answered_nums = in.readInt();
            this.stages = in.readInt();
            this.passsore = in.readInt();
            this.showerrors = in.readInt();
            this.showanswer = in.readInt();
            this.joincount = in.readInt();
            this.last_pass_stages = in.readInt();
            this.company = in.readParcelable(CompanyBean.class.getClassLoader());
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

    public ExamDetails() {
    }

    protected ExamDetails(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ExamDetails> CREATOR = new Parcelable.Creator<ExamDetails>() {
        @Override
        public ExamDetails createFromParcel(Parcel source) {
            return new ExamDetails(source);
        }

        @Override
        public ExamDetails[] newArray(int size) {
            return new ExamDetails[size];
        }
    };
}

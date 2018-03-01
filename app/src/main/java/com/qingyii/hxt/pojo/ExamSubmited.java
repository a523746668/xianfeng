package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/8/19.
 */

public class ExamSubmited implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"is_pass":0,"score":0}
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
         * is_pass : 0
         * score : 0
         */

        private int is_pass;
        private double score;

        public int getIs_pass() {
            return is_pass;
        }

        public void setIs_pass(int is_pass) {
            this.is_pass = is_pass;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.is_pass);
            dest.writeDouble(this.score);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.is_pass = in.readInt();
            this.score = in.readInt();
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

    public ExamSubmited() {
    }

    protected ExamSubmited(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ExamSubmited> CREATOR = new Parcelable.Creator<ExamSubmited>() {
        @Override
        public ExamSubmited createFromParcel(Parcel source) {
            return new ExamSubmited(source);
        }

        @Override
        public ExamSubmited[] newArray(int size) {
            return new ExamSubmited[size];
        }
    };
}

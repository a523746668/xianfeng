package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by XRJ on 16/9/27.
 */

public class MyCheckState implements Parcelable {
    /**
     * error_msg : can_not_check_current_month
     * error_code : 1
     * data : {"count":4,"stars":0,"score":0,"total":0,"status":"checking"}
     */

    private String error_msg;
    private int error_code;
    /**
     * count : 4
     * stars : 0
     * score : 0
     * total : 0
     * status : checking
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
        private int count;
        private int stars;
        private int score;
        private int total;
        private String status;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.count);
            dest.writeInt(this.stars);
            dest.writeInt(this.score);
            dest.writeInt(this.total);
            dest.writeString(this.status);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.count = in.readInt();
            this.stars = in.readInt();
            this.score = in.readInt();
            this.total = in.readInt();
            this.status = in.readString();
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

    public MyCheckState() {
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

    protected MyCheckState(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Creator<MyCheckState> CREATOR = new Creator<MyCheckState>() {
        @Override
        public MyCheckState createFromParcel(Parcel source) {
            return new MyCheckState(source);
        }

        @Override
        public MyCheckState[] newArray(int size) {
            return new MyCheckState[size];
        }
    };
}

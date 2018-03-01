package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */

public class ExamRank implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"user_id":302,"username":"何晓溪","name":"测试单位","result":"2关"},{"user_id":304,"username":"饶远","name":"测试单位","result":"2关"},{"user_id":279,"username":"毛洪波","name":"连商网络公司","result":"2关"},{"user_id":293,"username":"王晓悦","name":"测试单位","result":"2关"},{"user_id":274,"username":"曾广超","name":"连商网络公司","result":"1关"}]
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
         * user_id : 302
         * username : 何晓溪
         * name : 测试单位
         * result : 2关
         */

        private int user_id;
        private String username;
        private String name;
        private String result;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.user_id);
            dest.writeString(this.username);
            dest.writeString(this.name);
            dest.writeString(this.result);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.user_id = in.readInt();
            this.username = in.readString();
            this.name = in.readString();
            this.result = in.readString();
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

    public ExamRank() {
    }

    protected ExamRank(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<ExamRank> CREATOR = new Parcelable.Creator<ExamRank>() {
        @Override
        public ExamRank createFromParcel(Parcel source) {
            return new ExamRank(source);
        }

        @Override
        public ExamRank[] newArray(int size) {
            return new ExamRank[size];
        }
    };
}

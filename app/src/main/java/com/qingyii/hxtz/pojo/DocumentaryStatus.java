package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by XRJ on 16/11/17.
 */

public class DocumentaryStatus implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"docs_cnt":0,"wait_check":"0","curr_month":"2016-09"}
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
         * docs_cnt : 0
         * check_cnt : 0
         * wait_check : 0
         * curr_month : 2016-09
         */

        private int docs_cnt;
        private int check_cnt;
        private String wait_check;
        private String curr_month;

        public int getDocs_cnt() {
            return docs_cnt;
        }

        public void setDocs_cnt(int docs_cnt) {
            this.docs_cnt = docs_cnt;
        }

        public int getCheck_cnt() {
            return check_cnt;
        }

        public void setCheck_cnt(int check_cnt) {
            this.check_cnt = check_cnt;
        }

        public String getWait_check() {
            return wait_check;
        }

        public void setWait_check(String wait_check) {
            this.wait_check = wait_check;
        }

        public String getCurr_month() {
            return curr_month;
        }

        public void setCurr_month(String curr_month) {
            this.curr_month = curr_month;
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.docs_cnt);
            dest.writeInt(this.check_cnt);
            dest.writeString(this.wait_check);
            dest.writeString(this.curr_month);
        }

        protected DataBean(Parcel in) {
            this.docs_cnt = in.readInt();
            this.check_cnt = in.readInt();
            this.wait_check = in.readString();
            this.curr_month = in.readString();
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

    public DocumentaryStatus() {
    }

    protected DocumentaryStatus(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DocumentaryStatus> CREATOR = new Parcelable.Creator<DocumentaryStatus>() {
        @Override
        public DocumentaryStatus createFromParcel(Parcel source) {
            return new DocumentaryStatus(source);
        }

        @Override
        public DocumentaryStatus[] newArray(int size) {
            return new DocumentaryStatus[size];
        }
    };
}

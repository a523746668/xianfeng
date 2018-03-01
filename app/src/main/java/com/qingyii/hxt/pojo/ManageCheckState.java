package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by XRJ on 16/9/27.
 */

public class ManageCheckState implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"docs_cnt":3,"people_cnt":4,"status":"checking"}
     */

    private String error_msg;
    private int error_code;
    /**
     * docs_cnt : 3
     * people_cnt : 4
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
        private int docs_cnt;
        private int people_cnt;
        private String status;

        public int getDocs_cnt() {
            return docs_cnt;
        }

        public void setDocs_cnt(int docs_cnt) {
            this.docs_cnt = docs_cnt;
        }

        public int getPeople_cnt() {
            return people_cnt;
        }

        public void setPeople_cnt(int people_cnt) {
            this.people_cnt = people_cnt;
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
            dest.writeInt(this.docs_cnt);
            dest.writeInt(this.people_cnt);
            dest.writeString(this.status);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.docs_cnt = in.readInt();
            this.people_cnt = in.readInt();
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

    public ManageCheckState() {
    }

    protected ManageCheckState(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ManageCheckState> CREATOR = new Parcelable.Creator<ManageCheckState>() {
        @Override
        public ManageCheckState createFromParcel(Parcel source) {
            return new ManageCheckState(source);
        }

        @Override
        public ManageCheckState[] newArray(int size) {
            return new ManageCheckState[size];
        }
    };
}

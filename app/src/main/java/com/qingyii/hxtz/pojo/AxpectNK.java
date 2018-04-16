package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 *
 * 期刊
 */

public class AxpectNK implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":8,"issuename":"9999999","issuecover":"","issuedesc":"<p>9999999999<br/><\/p>"}]
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 8
     * issuename : 9999999
     * issuecover :
     * issuedesc : <p>9999999999<br/></p>
     */

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
        private int id;
        private String issuename;
        private String issuecover;
        private String issuedesc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIssuename() {
            return issuename;
        }

        public void setIssuename(String issuename) {
            this.issuename = issuename;
        }

        public String getIssuecover() {
            return issuecover;
        }

        public void setIssuecover(String issuecover) {
            this.issuecover = issuecover;
        }

        public String getIssuedesc() {
            return issuedesc;
        }

        public void setIssuedesc(String issuedesc) {
            this.issuedesc = issuedesc;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.issuename);
            dest.writeString(this.issuecover);
            dest.writeString(this.issuedesc);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.issuename = in.readString();
            this.issuecover = in.readString();
            this.issuedesc = in.readString();
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

    public AxpectNK() {
    }

    protected AxpectNK(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<AxpectNK> CREATOR = new Parcelable.Creator<AxpectNK>() {
        @Override
        public AxpectNK createFromParcel(Parcel source) {
            return new AxpectNK(source);
        }

        @Override
        public AxpectNK[] newArray(int size) {
            return new AxpectNK[size];
        }
    };
}

package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/8/30.
 *
 * 投票
 */

public class VoteNK implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : vote_success
     */

    private String error_msg;
    private int error_code;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error_msg);
        dest.writeInt(this.error_code);
        dest.writeString(this.data);
    }

    public VoteNK() {
    }

    protected VoteNK(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readString();
    }

    public static final Parcelable.Creator<VoteNK> CREATOR = new Parcelable.Creator<VoteNK>() {
        @Override
        public VoteNK createFromParcel(Parcel source) {
            return new VoteNK(source);
        }

        @Override
        public VoteNK[] newArray(int size) {
            return new VoteNK[size];
        }
    };
}

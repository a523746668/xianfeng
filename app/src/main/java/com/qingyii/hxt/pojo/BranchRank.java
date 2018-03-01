package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by XRJ on 16/11/15.
 */

public class BranchRank implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":162,"name":"党群办公室","leader":"","secondary":"","remark":"","people_cnt":57,"four_stars":"0%"}]
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
         * id : 162
         * name : 党群办公室
         * leader :
         * secondary :
         * remark :
         * people_cnt : 57
         * four_stars : 0%
         */

        private int id;
        private String name;
        private String leader;
        private String secondary;
        private String remark;
        private int people_cnt;
        private String four_stars;

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

        public String getLeader() {
            return leader;
        }

        public void setLeader(String leader) {
            this.leader = leader;
        }

        public String getSecondary() {
            return secondary;
        }

        public void setSecondary(String secondary) {
            this.secondary = secondary;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getPeople_cnt() {
            return people_cnt;
        }

        public void setPeople_cnt(int people_cnt) {
            this.people_cnt = people_cnt;
        }

        public String getFour_stars() {
            return four_stars;
        }

        public void setFour_stars(String four_stars) {
            this.four_stars = four_stars;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.leader);
            dest.writeString(this.secondary);
            dest.writeString(this.remark);
            dest.writeInt(this.people_cnt);
            dest.writeString(this.four_stars);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.leader = in.readString();
            this.secondary = in.readString();
            this.remark = in.readString();
            this.people_cnt = in.readInt();
            this.four_stars = in.readString();
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

    public BranchRank() {
    }

    protected BranchRank(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<BranchRank> CREATOR = new Parcelable.Creator<BranchRank>() {
        @Override
        public BranchRank createFromParcel(Parcel source) {
            return new BranchRank(source);
        }

        @Override
        public BranchRank[] newArray(int size) {
            return new BranchRank[size];
        }
    };
}

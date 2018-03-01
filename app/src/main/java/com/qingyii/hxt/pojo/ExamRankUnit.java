package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */

public class ExamRankUnit implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"department_id":0,"people":0,"result":"0关","group_id":189,"name":"连商公司纪实审核"},{"department_id":0,"people":0,"result":"0关","group_id":201,"name":"纪实审核组织结构"}]
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
         * department_id : 0
         * people : 0
         * result : 0关
         * group_id : 189
         * name : 连商公司纪实审核
         */

        private int department_id;
        private int people;
        private String result;
        private int group_id;
        private String name;

        public int getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }

        public int getPeople() {
            return people;
        }

        public void setPeople(int people) {
            this.people = people;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.department_id);
            dest.writeInt(this.people);
            dest.writeString(this.result);
            dest.writeInt(this.group_id);
            dest.writeString(this.name);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.department_id = in.readInt();
            this.people = in.readInt();
            this.result = in.readString();
            this.group_id = in.readInt();
            this.name = in.readString();
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

    public ExamRankUnit() {
    }

    protected ExamRankUnit(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<ExamRankUnit> CREATOR = new Parcelable.Creator<ExamRankUnit>() {
        @Override
        public ExamRankUnit createFromParcel(Parcel source) {
            return new ExamRankUnit(source);
        }

        @Override
        public ExamRankUnit[] newArray(int size) {
            return new ExamRankUnit[size];
        }
    };
}

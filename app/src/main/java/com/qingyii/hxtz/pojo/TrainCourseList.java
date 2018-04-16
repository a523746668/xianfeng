package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by XRJ on 16/9/11.
 */
public class TrainCourseList implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":5,"coursename":"第五课","description":"中国湖南省长沙市长沙县","teachers":"A老师","begintime":"2016-08-31 19:15:00","endtime":"2016-08-31 19:15:00","address":"中国湖南省长沙市长沙县","note":"<p>中国湖南省长沙市长沙县<\/p>"}]
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 5
     * coursename : 第五课
     * description : 中国湖南省长沙市长沙县
     * teachers : A老师
     * begintime : 2016-08-31 19:15:00
     * endtime : 2016-08-31 19:15:00
     * address : 中国湖南省长沙市长沙县
     * note : <p>中国湖南省长沙市长沙县</p>
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
        private String coursename;
        private String description;
        private String teachers;
        private String begintime;
        private String endtime;
        private String address;
        private String note;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoursename() {
            return coursename;
        }

        public void setCoursename(String coursename) {
            this.coursename = coursename;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTeachers() {
            return teachers;
        }

        public void setTeachers(String teachers) {
            this.teachers = teachers;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.coursename);
            dest.writeString(this.description);
            dest.writeString(this.teachers);
            dest.writeString(this.begintime);
            dest.writeString(this.endtime);
            dest.writeString(this.address);
            dest.writeString(this.note);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.coursename = in.readString();
            this.description = in.readString();
            this.teachers = in.readString();
            this.begintime = in.readString();
            this.endtime = in.readString();
            this.address = in.readString();
            this.note = in.readString();
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

    public TrainCourseList() {
    }

    protected TrainCourseList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<TrainCourseList> CREATOR = new Parcelable.Creator<TrainCourseList>() {
        @Override
        public TrainCourseList createFromParcel(Parcel source) {
            return new TrainCourseList(source);
        }

        @Override
        public TrainCourseList[] newArray(int size) {
            return new TrainCourseList[size];
        }
    };
}

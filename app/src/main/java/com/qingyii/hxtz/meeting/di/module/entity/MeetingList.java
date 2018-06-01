package com.qingyii.hxtz.meeting.di.module.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xubo on 2017/6/13.
 */

public class MeetingList {


    /**
     * error_msg : success
     * error_code : 0
     * data :
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


        public int getGroupsign() {
            return groupsign;
        }

        public void setGroupsign(int groupsign) {
            this.groupsign = groupsign;
        }

        /**
         * id : 5
         * trainingtitle : 测试2
         * content : 测试4
         * description : 测试3
         * organizer : 测试5
         * begintime : 1475287200
         * endtime : 1475830800
         * address : 长沙五一广场
         * location : {"map":{"lat":28.19635,"lng":112.97733,"zoom":18},"location":{}}
         * neednotice : 0
         * noticebegintime : 0
         * frequency : 600
         */


        private int id;
        @SerializedName("meetingtitle")
        private String trainingtitle;
        private String content;
        private int groupsign;
        private String description;
        private String organizer;
        private Long begintime;
        private Long endtime;
        private String address;
        private String location;
        private int neednotice;
        private int noticebegintime;
        private int frequency;
        private String meetingtype;
        private String tag;
        private String status_mark;
        private int is_read;
        private int user_id;
        private int is_confirm;
        private int has_summary;
        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
        public int getHas_summary() {
            return has_summary;
        }

        public void setHas_summary(int has_summary) {
            this.has_summary = has_summary;
        }

        public int getIs_confirm() {
            return is_confirm;
        }

        public void setIs_confirm(int is_confirm) {
            this.is_confirm = is_confirm;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public String getStatus_mark() {
            return status_mark;
        }

        public void setStatus_mark(String status_mark) {
            this.status_mark = status_mark;
        }

        public String getMeetingtype() {
            return meetingtype;
        }

        public void setMeetingtype(String meetingtype) {
            this.meetingtype = meetingtype;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTrainingtitle() {
            return trainingtitle;
        }

        public void setTrainingtitle(String trainingtitle) {
            this.trainingtitle = trainingtitle;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getOrganizer() {
            return organizer;
        }

        public void setOrganizer(String organizer) {
            this.organizer = organizer;
        }

        public Long getBegintime() {
            return begintime;
        }

        public void setBegintime(Long begintime) {
            this.begintime = begintime;
        }

        public Long getEndtime() {
            return endtime;
        }

        public void setEndtime(Long endtime) {
            this.endtime = endtime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getNeednotice() {
            return neednotice;
        }

        public void setNeednotice(int neednotice) {
            this.neednotice = neednotice;
        }

        public int getNoticebegintime() {
            return noticebegintime;
        }

        public void setNoticebegintime(int noticebegintime) {
            this.noticebegintime = noticebegintime;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.trainingtitle);
            dest.writeString(this.content);
            dest.writeString(this.description);
            dest.writeString(this.organizer);
            dest.writeValue(this.begintime);
            dest.writeValue(this.endtime);
            dest.writeString(this.address);
            dest.writeString(this.location);
            dest.writeInt(this.neednotice);
            dest.writeInt(this.noticebegintime);
            dest.writeInt(this.frequency);
            dest.writeString(this.meetingtype);
            dest.writeString(this.tag);
            dest.writeString(this.status_mark);
            dest.writeInt(this.is_read);
            dest.writeInt(this.user_id);
            dest.writeInt(this.is_confirm);
            dest.writeInt(this.has_summary);
            dest.writeInt(this.groupsign);
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.trainingtitle = in.readString();
            this.content = in.readString();
            this.description = in.readString();
            this.organizer = in.readString();
            this.begintime = (Long) in.readValue(Long.class.getClassLoader());
            this.endtime = (Long) in.readValue(Long.class.getClassLoader());
            this.address = in.readString();
            this.location = in.readString();
            this.neednotice = in.readInt();
            this.noticebegintime = in.readInt();
            this.frequency = in.readInt();
            this.meetingtype = in.readString();
            this.tag = in.readString();
            this.status_mark = in.readString();
            this.is_read = in.readInt();
            this.user_id = in.readInt();
            this.is_confirm = in.readInt();
            this.has_summary = in.readInt();
            this.groupsign=in.readInt();

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
}

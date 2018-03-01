package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 63264 on 16/9/9.
 */

public class TrainList implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":5,"trainingtitle":"测试2","content":"测试4","description":"测试3","organizer":"测试5","begintime":1475287200,"endtime":1475830800,"address":"长沙五一广场","location":"{\"map\":{\"lat\":28.19635,\"lng\":112.97733,\"zoom\":18},\"location\":{}}","neednotice":0,"noticebegintime":0,"frequency":600,"exam_id":7}]
     */

    private String error_msg;
    private int error_code;
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
     * exam_id : 7
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
        private String trainingtitle;
        private String content;
        private String description;
        private String organizer;
        private long begintime;
        private long endtime;
        private String address;
        private String location;
        private int neednotice;
        private int noticebegintime;
        private int frequency;
        private int exam_id;

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

        public long getBegintime() {
            return begintime;
        }

        public void setBegintime(long begintime) {
            this.begintime = begintime;
        }

        public long getEndtime() {
            return endtime;
        }

        public void setEndtime(long endtime) {
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

        public int getExam_id() {
            return exam_id;
        }

        public void setExam_id(int exam_id) {
            this.exam_id = exam_id;
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
            dest.writeLong(this.begintime);
            dest.writeLong(this.endtime);
            dest.writeString(this.address);
            dest.writeString(this.location);
            dest.writeInt(this.neednotice);
            dest.writeInt(this.noticebegintime);
            dest.writeInt(this.frequency);
            dest.writeInt(this.exam_id);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.trainingtitle = in.readString();
            this.content = in.readString();
            this.description = in.readString();
            this.organizer = in.readString();
            this.begintime = in.readLong();
            this.endtime = in.readLong();
            this.address = in.readString();
            this.location = in.readString();
            this.neednotice = in.readInt();
            this.noticebegintime = in.readInt();
            this.frequency = in.readInt();
            this.exam_id = in.readInt();
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
        dest.writeTypedList(this.data);
    }

    public TrainList() {
    }

    protected TrainList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<TrainList> CREATOR = new Creator<TrainList>() {
        @Override
        public TrainList createFromParcel(Parcel source) {
            return new TrainList(source);
        }

        @Override
        public TrainList[] newArray(int size) {
            return new TrainList[size];
        }
    };
}

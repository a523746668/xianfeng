package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 63264 on 16/10/13.
 */

public class Advertisement implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":1,"adtitle":"纪念孙中山诞辰150周年大会举行 习近平发表讲话","adtype":0,"adwords":"纪念孙中山诞辰150周年大会举行 习近平发表讲话","adbanner":"","adlink":"http://cpc.people.com.cn/","description":"","mark":"INDEX_TOP"},{"id":2,"adtitle":"纪念孙中山诞辰150周年大会举行 习近平发表讲话","adtype":0,"adwords":"纪念孙中山诞辰150周年大会举行 习近平发表讲话","adbanner":"","adlink":"http://www.xianfeng.cc","description":"","mark":"INDEX_BOTTOM"},{"id":10,"adtitle":"#","adtype":1,"adwords":"","adbanner":"http://admin.seeo.cn/upload/ad/20161118/1afb28ac6cf9ecac53a7977e95afa63c.png","adlink":"http://www.baidu.com","description":"","mark":"INDEX_BANNER"}]
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
         * id : 1
         * adtitle : 纪念孙中山诞辰150周年大会举行 习近平发表讲话
         * adtype : 0
         * adwords : 纪念孙中山诞辰150周年大会举行 习近平发表讲话
         * adbanner :
         * adlink : http://cpc.people.com.cn/
         * description :
         * mark : INDEX_TOP
         */

        private int id;
        private String adtitle;
        private int adtype;
        private String adwords;
        private String adbanner;
        private String adlink;
        private String description;
        private String mark;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAdtitle() {
            return adtitle;
        }

        public void setAdtitle(String adtitle) {
            this.adtitle = adtitle;
        }

        public int getAdtype() {
            return adtype;
        }

        public void setAdtype(int adtype) {
            this.adtype = adtype;
        }

        public String getAdwords() {
            return adwords;
        }

        public void setAdwords(String adwords) {
            this.adwords = adwords;
        }

        public String getAdbanner() {
            return adbanner;
        }

        public void setAdbanner(String adbanner) {
            this.adbanner = adbanner;
        }

        public String getAdlink() {
            return adlink;
        }

        public void setAdlink(String adlink) {
            this.adlink = adlink;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.adtitle);
            dest.writeInt(this.adtype);
            dest.writeString(this.adwords);
            dest.writeString(this.adbanner);
            dest.writeString(this.adlink);
            dest.writeString(this.description);
            dest.writeString(this.mark);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.adtitle = in.readString();
            this.adtype = in.readInt();
            this.adwords = in.readString();
            this.adbanner = in.readString();
            this.adlink = in.readString();
            this.description = in.readString();
            this.mark = in.readString();
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

    public Advertisement() {
    }

    protected Advertisement(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<Advertisement> CREATOR = new Parcelable.Creator<Advertisement>() {
        @Override
        public Advertisement createFromParcel(Parcel source) {
            return new Advertisement(source);
        }

        @Override
        public Advertisement[] newArray(int size) {
            return new Advertisement[size];
        }
    };
}

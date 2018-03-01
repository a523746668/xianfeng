package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */

public class SubscribedNK implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":5,"magazinename":"111111111111111","magezinecover":"","magazinedesc":"<p>3344444444444444<br/><\/p>","organizer":"33333333333333333333","publishstatus":"public","subscribenums":0,"is_subscribe":1}]
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 5
     * magazinename : 111111111111111
     * magezinecover :
     * magazinedesc : <p>3344444444444444<br/></p>
     * organizer : 33333333333333333333
     * publishstatus : public
     * subscribenums : 0
     * is_subscribe : 1
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
        private String magazinename;
        private String magezinecover;
        private String magazinedesc;
        private String organizer;
        private String publishstatus;
        private int subscribenums;
        private int is_subscribe;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMagazinename() {
            return magazinename;
        }

        public void setMagazinename(String magazinename) {
            this.magazinename = magazinename;
        }

        public String getMagezinecover() {
            return magezinecover;
        }

        public void setMagezinecover(String magezinecover) {
            this.magezinecover = magezinecover;
        }

        public String getMagazinedesc() {
            return magazinedesc;
        }

        public void setMagazinedesc(String magazinedesc) {
            this.magazinedesc = magazinedesc;
        }

        public String getOrganizer() {
            return organizer;
        }

        public void setOrganizer(String organizer) {
            this.organizer = organizer;
        }

        public String getPublishstatus() {
            return publishstatus;
        }

        public void setPublishstatus(String publishstatus) {
            this.publishstatus = publishstatus;
        }

        public int getSubscribenums() {
            return subscribenums;
        }

        public void setSubscribenums(int subscribenums) {
            this.subscribenums = subscribenums;
        }

        public int getIs_subscribe() {
            return is_subscribe;
        }

        public void setIs_subscribe(int is_subscribe) {
            this.is_subscribe = is_subscribe;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.magazinename);
            dest.writeString(this.magezinecover);
            dest.writeString(this.magazinedesc);
            dest.writeString(this.organizer);
            dest.writeString(this.publishstatus);
            dest.writeInt(this.subscribenums);
            dest.writeInt(this.is_subscribe);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.magazinename = in.readString();
            this.magezinecover = in.readString();
            this.magazinedesc = in.readString();
            this.organizer = in.readString();
            this.publishstatus = in.readString();
            this.subscribenums = in.readInt();
            this.is_subscribe = in.readInt();
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

    public SubscribedNK() {
    }

    protected SubscribedNK(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<SubscribedNK> CREATOR = new Parcelable.Creator<SubscribedNK>() {
        @Override
        public SubscribedNK createFromParcel(Parcel source) {
            return new SubscribedNK(source);
        }

        @Override
        public SubscribedNK[] newArray(int size) {
            return new SubscribedNK[size];
        }
    };
}

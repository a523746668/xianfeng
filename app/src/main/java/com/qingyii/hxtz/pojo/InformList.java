package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by XRJ on 16/9/7.
 */

public class InformList implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":39,"title":"3232","redtitle":"","receipt_type":"flat","receipt_line":0,"content":"fdsa","receiptnums":0,"sendnums":0,"attachment":"","needreceipt":0,"notifytype":"common","created_at":"2016-08-31 20:15:19","is_receipt":1}]
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 39
     * title : 3232
     * redtitle :
     * receipt_type : flat
     * receipt_line : 0
     * content : fdsa
     * receiptnums : 0
     * sendnums : 0
     * attachment :
     * needreceipt : 0
     * notifytype : common
     * created_at : 2016-08-31 20:15:19
     * is_receipt : 1"
     * training_id": 33
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
        private String title;
        private String redtitle;
        private String receipt_type;
        private long receipt_line;
        private String content;
        private int receiptnums;
        private int sendnums;
        private String attachment;
        private int needreceipt;
        private String notifytype;
        private String created_at;
        private int is_receipt;
        private String receipt_status;
        private int training_id;
        private int mark;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRedtitle() {
            return redtitle;
        }

        public void setRedtitle(String redtitle) {
            this.redtitle = redtitle;
        }

        public String getReceipt_type() {
            return receipt_type;
        }

        public void setReceipt_type(String receipt_type) {
            this.receipt_type = receipt_type;
        }

        public long getReceipt_line() {
            return receipt_line;
        }

        public void setReceipt_line(long receipt_line) {
            this.receipt_line = receipt_line;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getReceiptnums() {
            return receiptnums;
        }

        public void setReceiptnums(int receiptnums) {
            this.receiptnums = receiptnums;
        }

        public int getSendnums() {
            return sendnums;
        }

        public void setSendnums(int sendnums) {
            this.sendnums = sendnums;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public int getNeedreceipt() {
            return needreceipt;
        }

        public void setNeedreceipt(int needreceipt) {
            this.needreceipt = needreceipt;
        }

        public String getNotifytype() {
            return notifytype;
        }

        public void setNotifytype(String notifytype) {
            this.notifytype = notifytype;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getIs_receipt() {
            return is_receipt;
        }

        public void setIs_receipt(int is_receipt) {
            this.is_receipt = is_receipt;
        }

        public String getReceipt_status() {
            return receipt_status;
        }

        public void setReceipt_status(String receipt_status) {
            this.receipt_status = receipt_status;
        }

        public int getTraining_id() {
            return training_id;
        }

        public void setTraining_id(int training_id) {
            this.training_id = training_id;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
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
            dest.writeString(this.title);
            dest.writeString(this.redtitle);
            dest.writeString(this.receipt_type);
            dest.writeLong(this.receipt_line);
            dest.writeString(this.content);
            dest.writeInt(this.receiptnums);
            dest.writeInt(this.sendnums);
            dest.writeString(this.attachment);
            dest.writeInt(this.needreceipt);
            dest.writeString(this.notifytype);
            dest.writeString(this.created_at);
            dest.writeInt(this.is_receipt);
            dest.writeString(this.receipt_status);
            dest.writeInt(this.training_id);
            dest.writeInt(this.mark);
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.title = in.readString();
            this.redtitle = in.readString();
            this.receipt_type = in.readString();
            this.receipt_line = in.readLong();
            this.content = in.readString();
            this.receiptnums = in.readInt();
            this.sendnums = in.readInt();
            this.attachment = in.readString();
            this.needreceipt = in.readInt();
            this.notifytype = in.readString();
            this.created_at = in.readString();
            this.is_receipt = in.readInt();
            this.receipt_status = in.readString();
            this.training_id = in.readInt();
            this.mark = in.readInt();
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

    public InformList() {
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

    protected InformList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<InformList> CREATOR = new Creator<InformList>() {
        @Override
        public InformList createFromParcel(Parcel source) {
            return new InformList(source);
        }

        @Override
        public InformList[] newArray(int size) {
            return new InformList[size];
        }
    };
}

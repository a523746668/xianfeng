package com.qingyii.hxtz.training.details.schedule.di.module.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubo on 2017/8/9.
 */

public class TrainNoticeList implements Parcelable {

    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":1789,"title":"测试培训2222","account_id":137,"redtitle":"","number":"","published_at":1502192501,"author":"易新煌","receipt_type":"flat","receipt_line":0,"content":"<p>\r\n\t培训时间：2017-08-08 19:35 至 2017-08-20 19:35\r\n<\/p>\r\n<p>\r\n\t培训地址：湖南 长沙 芙蓉区 万家丽\r\n<\/p>\r\n<p>\r\n\t培训内容：测试培训2222\r\n<\/p>","receiptnums":0,"sendnums":5,"attachment":"","status":"publish","needreceipt":0,"notifytype":"train","return_rows":0,"endtime":0,"created_at":"2017-08-08 19:41:41","super_id":1}]
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
         * id : 1789
         * title : 测试培训2222
         * account_id : 137
         * redtitle :
         * number :
         * published_at : 1502192501
         * author : 易新煌
         * receipt_type : flat
         * receipt_line : 0
         * content : <p>
         培训时间：2017-08-08 19:35 至 2017-08-20 19:35
         </p>
         <p>
         培训地址：湖南 长沙 芙蓉区 万家丽
         </p>
         <p>
         培训内容：测试培训2222
         </p>
         * receiptnums : 0
         * sendnums : 5
         * attachment :
         * status : publish
         * needreceipt : 0
         * notifytype : train
         * return_rows : 0
         * endtime : 0
         * created_at : 2017-08-08 19:41:41
         * super_id : 1
         */

        private int id;
        private String title;
        private int account_id;
        private String redtitle;
        private String number;
        private int published_at;
        private String author;
        private String receipt_type;
        private int receipt_line;
        private String content;
        private int receiptnums;
        private int sendnums;
        private String attachment;
        private String status;
        private int needreceipt;
        private String notifytype;
        private int return_rows;
        private int endtime;
        private String created_at;
        private int super_id;

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

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public String getRedtitle() {
            return redtitle;
        }

        public void setRedtitle(String redtitle) {
            this.redtitle = redtitle;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getPublished_at() {
            return published_at;
        }

        public void setPublished_at(int published_at) {
            this.published_at = published_at;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getReceipt_type() {
            return receipt_type;
        }

        public void setReceipt_type(String receipt_type) {
            this.receipt_type = receipt_type;
        }

        public int getReceipt_line() {
            return receipt_line;
        }

        public void setReceipt_line(int receipt_line) {
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public int getReturn_rows() {
            return return_rows;
        }

        public void setReturn_rows(int return_rows) {
            this.return_rows = return_rows;
        }

        public int getEndtime() {
            return endtime;
        }

        public void setEndtime(int endtime) {
            this.endtime = endtime;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getSuper_id() {
            return super_id;
        }

        public void setSuper_id(int super_id) {
            this.super_id = super_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.title);
            dest.writeInt(this.account_id);
            dest.writeString(this.redtitle);
            dest.writeString(this.number);
            dest.writeInt(this.published_at);
            dest.writeString(this.author);
            dest.writeString(this.receipt_type);
            dest.writeInt(this.receipt_line);
            dest.writeString(this.content);
            dest.writeInt(this.receiptnums);
            dest.writeInt(this.sendnums);
            dest.writeString(this.attachment);
            dest.writeString(this.status);
            dest.writeInt(this.needreceipt);
            dest.writeString(this.notifytype);
            dest.writeInt(this.return_rows);
            dest.writeInt(this.endtime);
            dest.writeString(this.created_at);
            dest.writeInt(this.super_id);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.title = in.readString();
            this.account_id = in.readInt();
            this.redtitle = in.readString();
            this.number = in.readString();
            this.published_at = in.readInt();
            this.author = in.readString();
            this.receipt_type = in.readString();
            this.receipt_line = in.readInt();
            this.content = in.readString();
            this.receiptnums = in.readInt();
            this.sendnums = in.readInt();
            this.attachment = in.readString();
            this.status = in.readString();
            this.needreceipt = in.readInt();
            this.notifytype = in.readString();
            this.return_rows = in.readInt();
            this.endtime = in.readInt();
            this.created_at = in.readString();
            this.super_id = in.readInt();
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
        dest.writeList(this.data);
    }

    public TrainNoticeList() {
    }

    protected TrainNoticeList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<TrainNoticeList> CREATOR = new Parcelable.Creator<TrainNoticeList>() {
        @Override
        public TrainNoticeList createFromParcel(Parcel source) {
            return new TrainNoticeList(source);
        }

        @Override
        public TrainNoticeList[] newArray(int size) {
            return new TrainNoticeList[size];
        }
    };
}

package com.qingyii.hxtz.notify.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubo on 2017/6/13.
 */

public class NotifyList implements Parcelable {


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

        //参数 unsign 未签收 unreturn 未回执 unread 未查阅 sign 已签收 return 已回执 read 已查阅
        public static final String UNSIGN = "unsign";
        public static final String UNRETURN = "unreturn";
        public static final String UNREAD = "unread";
        public static final String SIGN = "sign";
        public static final String RETURN = "return";
        public static final String READ = "read";
        //extraurgent 特急 urgent 急 flat 普通
        public static final String EXTRAURGENT = "extraurgent";
        public static final String URGENT = "urgent";
        public static final String FLAT = "flat";

        public static final String ID = "notify_item_id";
        /**
         * id : 1312
         * title : 潭耒分公司党委关于印发党委会议议事规则的通知
         * account_id : 135
         * redtitle : 中共现代投资股份有限公司潭耒分公司委员会文件
         * number : 潭耒分公司党〔2017〕6号
         * published_at : 1492051630
         * author : 中共现代投资股份有限公司潭耒分公司委员会
         * receipt_type : flat
         * receipt_line : 0
         * content : <p class="MsoNormal" align="center" style="text-align:left;">
         * receiptnums : 0
         * sendnums : 0
         * attachment :
         * status : publish
         * needreceipt : 0
         * notifytype : common
         * endtime : 0
         * created_at : 2017-04-12 18:36:32
         * super_id : 1
         * training_id : 0
         * receipt_status : sign
         * receipt_status_mark : read
         * timestamap : 1491993392
         * is_receipt : 1
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
        private int endtime;
        private String created_at;
        private int super_id;
        private int training_id;
        private String receipt_status;
        private String receipt_status_mark;
        private int timestamap;
        private int is_receipt;

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

        public int getTraining_id() {
            return training_id;
        }

        public void setTraining_id(int training_id) {
            this.training_id = training_id;
        }

        public String getReceipt_status() {
            return receipt_status;
        }

        public void setReceipt_status(String receipt_status) {
            this.receipt_status = receipt_status;
        }

        public String getReceipt_status_mark() {
            return receipt_status_mark;
        }

        public void setReceipt_status_mark(String receipt_status_mark) {
            this.receipt_status_mark = receipt_status_mark;
        }

        public int getTimestamap() {
            return timestamap;
        }

        public void setTimestamap(int timestamap) {
            this.timestamap = timestamap;
        }

        public int getIs_receipt() {
            return is_receipt;
        }

        public void setIs_receipt(int is_receipt) {
            this.is_receipt = is_receipt;
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
            dest.writeInt(this.endtime);
            dest.writeString(this.created_at);
            dest.writeInt(this.super_id);
            dest.writeInt(this.training_id);
            dest.writeString(this.receipt_status);
            dest.writeString(this.receipt_status_mark);
            dest.writeInt(this.timestamap);
            dest.writeInt(this.is_receipt);
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
            this.endtime = in.readInt();
            this.created_at = in.readString();
            this.super_id = in.readInt();
            this.training_id = in.readInt();
            this.receipt_status = in.readString();
            this.receipt_status_mark = in.readString();
            this.timestamap = in.readInt();
            this.is_receipt = in.readInt();
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

    public NotifyList() {
    }

    protected NotifyList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<NotifyList> CREATOR = new Parcelable.Creator<NotifyList>() {
        @Override
        public NotifyList createFromParcel(Parcel source) {
            return new NotifyList(source);
        }

        @Override
        public NotifyList[] newArray(int size) {
            return new NotifyList[size];
        }
    };
}

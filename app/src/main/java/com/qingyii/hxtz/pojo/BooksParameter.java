package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */

public class BooksParameter implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":6,"bookname":"真好书","filename":"转转大师-Word转PDF(pdftoword.55.la).pdf","bookcover":"/upload/book/cover/20160817/2ac68ccd295f6cb5b0a1e68fcae45502.jpg","description":"真好书","author":"真好书","press":"真好书","publishtime":1480521600,"authorinfo":"真好书","isrecommended":1,"readnums":0,"bookurl":"/upload/book/file/20160817/65c3835e81eb39e93ffc5d9c2ce3e0b9.pdf","stars":0,"updated_at":"2016-08-17 19:28:12"}]
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 6
     * bookname : 真好书
     * filename : 转转大师-Word转PDF(pdftoword.55.la).pdf
     * bookcover : /upload/book/cover/20160817/2ac68ccd295f6cb5b0a1e68fcae45502.jpg
     * description : 真好书
     * author : 真好书
     * press : 真好书
     * publishtime : 1480521600
     * authorinfo : 真好书
     * isrecommended : 1
     * readnums : 0
     * bookurl : /upload/book/file/20160817/65c3835e81eb39e93ffc5d9c2ce3e0b9.pdf
     * stars : 0
     * updated_at : 2016-08-17 19:28:12
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
        private String bookname;
        //private String filename;
        private String bookcover;
        private String description;
        private String author;
        private String press;
        private int publishtime;
        private String authorinfo;
        private int isrecommended;
        private int readnums;
        private String bookurl;
        private int stars;
        private String updated_at;
        private int filetype;
        private String publishstatus;
        private String SdCardUrl;
        private String read_locus;
        private int size;

        public String getRead_locus() {
            return read_locus;
        }

        public void setRead_locus(String read_locus) {
            this.read_locus = read_locus;
        }

        public String getSdCardUrl(){
            return this.SdCardUrl;
        }

        public void setSdCardUrl(String sdCardUrl){
            this.SdCardUrl = sdCardUrl;
        }

        public void setFiletype(int filetype){
            this.filetype = filetype;
        }

        public int getFiletype(){
            return this.filetype;
        }

        public String getPublishstatus() {
            return publishstatus;
        }

        public void setPublishstatus(String publishstatus) {
            this.publishstatus = publishstatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBookname() {
            return bookname;
        }

        public void setBookname(String bookname) {
            this.bookname = bookname;
        }

        //public String getFilename() {
        //    return filename;
        //}

        //public void setFilename(String filename) {
        //    this.filename = filename;
        //}

        public String getBookcover() {
            return bookcover;
        }

        public void setBookcover(String bookcover) {
            this.bookcover = bookcover;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPress() {
            return press;
        }

        public void setPress(String press) {
            this.press = press;
        }

        public int getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(int publishtime) {
            this.publishtime = publishtime;
        }

        public String getAuthorinfo() {
            return authorinfo;
        }

        public void setAuthorinfo(String authorinfo) {
            this.authorinfo = authorinfo;
        }

        public int getIsrecommended() {
            return isrecommended;
        }

        public void setIsrecommended(int isrecommended) {
            this.isrecommended = isrecommended;
        }

        public int getReadnums() {
            return readnums;
        }

        public void setReadnums(int readnums) {
            this.readnums = readnums;
        }

        public String getBookurl() {
            return bookurl;
        }

        public void setBookurl(String bookurl) {
            this.bookurl = bookurl;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
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
            dest.writeString(this.bookname);
            dest.writeString(this.bookcover);
            dest.writeString(this.description);
            dest.writeString(this.author);
            dest.writeString(this.press);
            dest.writeInt(this.publishtime);
            dest.writeString(this.authorinfo);
            dest.writeInt(this.isrecommended);
            dest.writeInt(this.readnums);
            dest.writeString(this.bookurl);
            dest.writeInt(this.stars);
            dest.writeString(this.updated_at);
            dest.writeInt(this.filetype);
            dest.writeString(this.publishstatus);
            dest.writeString(this.SdCardUrl);
            dest.writeString(this.read_locus);
            dest.writeInt(this.size);
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.bookname = in.readString();
            this.bookcover = in.readString();
            this.description = in.readString();
            this.author = in.readString();
            this.press = in.readString();
            this.publishtime = in.readInt();
            this.authorinfo = in.readString();
            this.isrecommended = in.readInt();
            this.readnums = in.readInt();
            this.bookurl = in.readString();
            this.stars = in.readInt();
            this.updated_at = in.readString();
            this.filetype = in.readInt();
            this.publishstatus = in.readString();
            this.SdCardUrl = in.readString();
            this.read_locus = in.readString();
            this.size = in.readInt();
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

    public BooksParameter() {
    }

    protected BooksParameter(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<BooksParameter> CREATOR = new Parcelable.Creator<BooksParameter>() {
        @Override
        public BooksParameter createFromParcel(Parcel source) {
            return new BooksParameter(source);
        }

        @Override
        public BooksParameter[] newArray(int size) {
            return new BooksParameter[size];
        }
    };
}

package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 63264 on 16/12/26.
 */

public class BookDetails implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"id":6,"bookname":"真好书","filename":"转转大师-Word转PDF(pdftoword.55.la).pdf","bookcover":"/upload/bo8fcae45502.jpg","description":"真好书","author":"真好书","press":"真好书","publishtime":1480521600,"authorinfo":"真好书","isrecommended":1,"readnums":0,"bookurl":"/upload/2ce3e0b9.pdf","stars":0,"updated_at":"2016-08-17 19:28:12","filetype":1}
     */

    private String error_msg;
    private int error_code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 6
         * bookname : 真好书
         * filename : 转转大师-Word转PDF(pdftoword.55.la).pdf
         * bookcover : /upload/bo8fcae45502.jpg
         * description : 真好书
         * author : 真好书
         * press : 真好书
         * publishtime : 1480521600
         * authorinfo : 真好书
         * isrecommended : 1
         * readnums : 0
         * bookurl : /upload/2ce3e0b9.pdf
         * stars : 0
         * updated_at : 2016-08-17 19:28:12
         * filetype : 1
         */

        private int id;
        private String bookname;
        private String filename;
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

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

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

        public int getFiletype() {
            return filetype;
        }

        public void setFiletype(int filetype) {
            this.filetype = filetype;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.bookname);
            dest.writeString(this.filename);
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
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.bookname = in.readString();
            this.filename = in.readString();
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
        dest.writeParcelable(this.data, flags);
    }

    public BookDetails() {
    }

    protected BookDetails(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<BookDetails> CREATOR = new Parcelable.Creator<BookDetails>() {
        @Override
        public BookDetails createFromParcel(Parcel source) {
            return new BookDetails(source);
        }

        @Override
        public BookDetails[] newArray(int size) {
            return new BookDetails[size];
        }
    };
}

package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by XRJ on 2016/8/20.
 *
 * 文章列表
 */

public class ArticleListNK implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":11,"created_at":"2016-08-17 10:47:14","updated_at":"2016-08-19 02:55:01","title":"asdasdasda1","keyword":"asd","description":"asda","content":"<p>sdfsdfsdfsdfsdffsdf<\/p>","author":"12","upvote":0,"readnums":0,"commentstatus":"normal","articlecover":"/upload/cover/20160817/6ee064b62c1da26bd8fabb06ec101fab.jpg","vote_id":13,"type":"article","pdf_name":"","pdf_path":"http://xf.ccketang.com"}]
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 11
     * created_at : 2016-08-17 10:47:14
     * updated_at : 2016-08-19 02:55:01
     * title : asdasdasda1
     * keyword : asd
     * description : asda
     * content : <p>sdfsdfsdfsdfsdffsdf</p>
     * author : 12
     * upvote : 0
     * readnums : 0
     * commentstatus : normal
     * articlecover : /upload/cover/20160817/6ee064b62c1da26bd8fabb06ec101fab.jpg
     * vote_id : 13
     * type : article
     * pdf_name :
     * pdf_path : http://xf.ccketang.com
     * size : 11
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
        private String created_at;
        private String updated_at;
        private String title;
        private String keyword;
        private String description;
        private String content;
        private String author;
        private int upvote;
        private int is_upvote;
        private int readnums;
        private String commentstatus;
        private String articlecover;
        private int vote_id;
        private String type;
        private String pdf_name;
        private String pdf_path;
        private int size;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getUpvote() {
            return upvote;
        }

        public void setUpvote(int upvote) {
            this.upvote = upvote;
        }

        public int getIs_upvote() {
            return is_upvote;
        }

        public void setIs_upvote(int is_upvote) {
            this.is_upvote = is_upvote;
        }

        public int getReadnums() {
            return readnums;
        }

        public void setReadnums(int readnums) {
            this.readnums = readnums;
        }

        public String getCommentstatus() {
            return commentstatus;
        }

        public void setCommentstatus(String commentstatus) {
            this.commentstatus = commentstatus;
        }

        public String getArticlecover() {
            return articlecover;
        }

        public void setArticlecover(String articlecover) {
            this.articlecover = articlecover;
        }

        public int getVote_id() {
            return vote_id;
        }

        public void setVote_id(int vote_id) {
            this.vote_id = vote_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPdf_name() {
            return pdf_name;
        }

        public void setPdf_name(String pdf_name) {
            this.pdf_name = pdf_name;
        }

        public String getPdf_path() {
            return pdf_path;
        }

        public void setPdf_path(String pdf_path) {
            this.pdf_path = pdf_path;
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
            dest.writeString(this.created_at);
            dest.writeString(this.updated_at);
            dest.writeString(this.title);
            dest.writeString(this.keyword);
            dest.writeString(this.description);
            dest.writeString(this.content);
            dest.writeString(this.author);
            dest.writeInt(this.upvote);
            dest.writeInt(this.is_upvote);
            dest.writeInt(this.readnums);
            dest.writeString(this.commentstatus);
            dest.writeString(this.articlecover);
            dest.writeInt(this.vote_id);
            dest.writeString(this.type);
            dest.writeString(this.pdf_name);
            dest.writeString(this.pdf_path);
            dest.writeInt(this.size);
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.created_at = in.readString();
            this.updated_at = in.readString();
            this.title = in.readString();
            this.keyword = in.readString();
            this.description = in.readString();
            this.content = in.readString();
            this.author = in.readString();
            this.upvote = in.readInt();
            this.is_upvote = in.readInt();
            this.readnums = in.readInt();
            this.commentstatus = in.readString();
            this.articlecover = in.readString();
            this.vote_id = in.readInt();
            this.type = in.readString();
            this.pdf_name = in.readString();
            this.pdf_path = in.readString();
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

    public ArticleListNK() {
    }

    protected ArticleListNK(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<ArticleListNK> CREATOR = new Parcelable.Creator<ArticleListNK>() {
        @Override
        public ArticleListNK createFromParcel(Parcel source) {
            return new ArticleListNK(source);
        }

        @Override
        public ArticleListNK[] newArray(int size) {
            return new ArticleListNK[size];
        }
    };
}

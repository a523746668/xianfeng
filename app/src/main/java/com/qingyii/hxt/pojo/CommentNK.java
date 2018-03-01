package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 *
 * 评论
 */

public class CommentNK implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":3,"article_id":1,"book_id":0,"user_id":0,"author":"789","content":"7777777","upvote":0,"downvote":0,"parent_id":0,"create_at":"2016-08-19 14:31:40","update_at":"2016-08-19 14:32:11"}]
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 3
     * article_id : 1
     * book_id : 0
     * user_id : 0
     * author : 789
     * content : 7777777
     * upvote : 0
     * downvote : 0
     * parent_id : 0
     * create_at : 2016-08-19 14:31:40
     * update_at : 2016-08-19 14:32:11
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
        private int article_id;
        private int book_id;
        private int user_id;
        private String author;
        private String content;
        private int upvote;
        private int downvote;
        private int parent_id;
        private String create_at;
        private String update_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getArticle_id() {
            return article_id;
        }

        public void setArticle_id(int article_id) {
            this.article_id = article_id;
        }

        public int getBook_id() {
            return book_id;
        }

        public void setBook_id(int book_id) {
            this.book_id = book_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUpvote() {
            return upvote;
        }

        public void setUpvote(int upvote) {
            this.upvote = upvote;
        }

        public int getDownvote() {
            return downvote;
        }

        public void setDownvote(int downvote) {
            this.downvote = downvote;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(String update_at) {
            this.update_at = update_at;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.article_id);
            dest.writeInt(this.book_id);
            dest.writeInt(this.user_id);
            dest.writeString(this.author);
            dest.writeString(this.content);
            dest.writeInt(this.upvote);
            dest.writeInt(this.downvote);
            dest.writeInt(this.parent_id);
            dest.writeString(this.create_at);
            dest.writeString(this.update_at);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.article_id = in.readInt();
            this.book_id = in.readInt();
            this.user_id = in.readInt();
            this.author = in.readString();
            this.content = in.readString();
            this.upvote = in.readInt();
            this.downvote = in.readInt();
            this.parent_id = in.readInt();
            this.create_at = in.readString();
            this.update_at = in.readString();
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

    public CommentNK() {
    }

    protected CommentNK(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<CommentNK> CREATOR = new Parcelable.Creator<CommentNK>() {
        @Override
        public CommentNK createFromParcel(Parcel source) {
            return new CommentNK(source);
        }

        @Override
        public CommentNK[] newArray(int size) {
            return new CommentNK[size];
        }
    };
}

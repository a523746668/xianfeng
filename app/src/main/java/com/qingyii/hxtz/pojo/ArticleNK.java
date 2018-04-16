package com.qingyii.hxtz.pojo;

/**
 * Created by Administrator on 2016/8/20.
 *
 * 文章
 */

public class ArticleNK {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"id":10,"title":"123423123","keyword":"123123","description":"12312","content":"<p>3123123123123<\/p>","author":"21312","upvote":0,"readnums":1,"commentstatus":"disable","articlecover":"http://xf.ccketang.com/upload/cover/20160817/adc89709a272a3754ee432ec5a6920f1.jpg","type":"article","created_at":"2016-08-17 10:47:14","updated_at":"2016-08-19 02:55:01","pdf_name":"","pdf_path":"http://xf.ccketang.com","is_collected":0}
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 10
     * title : 123423123
     * keyword : 123123
     * description : 12312
     * content : <p>3123123123123</p>
     * author : 21312
     * upvote : 0
     * readnums : 1
     * commentstatus : disable
     * articlecover : http://xf.ccketang.com/upload/cover/20160817/adc89709a272a3754ee432ec5a6920f1.jpg
     * type : article
     * created_at : 2016-08-17 10:47:14
     * updated_at : 2016-08-19 02:55:01
     * pdf_name :
     * pdf_path : http://xf.ccketang.com
     * is_collected : 0
     */

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

    public static class DataBean {
        private int id;
        private String title;
        private String keyword;
        private String description;
        private String content;
        private String author;
        private int upvote;
        private int readnums;
        private String commentstatus;
        private String articlecover;
        private String type;
        private String created_at;
        private String updated_at;
        private String pdf_name;
        private String pdf_path;
        private int is_collected;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public int getIs_collected() {
            return is_collected;
        }

        public void setIs_collected(int is_collected) {
            this.is_collected = is_collected;
        }
    }
}

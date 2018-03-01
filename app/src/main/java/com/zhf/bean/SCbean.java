package com.zhf.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhf on 2018/1/15.
 */

public class SCbean implements Serializable{

    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":544,"title":"怎样注册帐号和登录？登录之后为什么要修改密码？","type":"article","articlecover":"","pdf_path":"","content":""}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 544
         * title : 怎样注册帐号和登录？登录之后为什么要修改密码？
         * type : article
         * articlecover :
         * pdf_path :
         * content :
         */

        private int id;
        private String title;
        private String type;
        private String articlecover;
        private String pdf_path;
        private String content;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getArticlecover() {
            return articlecover;
        }

        public void setArticlecover(String articlecover) {
            this.articlecover = articlecover;
        }

        public String getPdf_path() {
            return pdf_path;
        }

        public void setPdf_path(String pdf_path) {
            this.pdf_path = pdf_path;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

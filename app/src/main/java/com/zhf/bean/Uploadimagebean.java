package com.zhf.bean;

/**
 * Created by zhf on 2017/10/12.
 */

public class Uploadimagebean {

    /**
     * error_msg : success
     * error_code : 0
     * data : {"id":2241,"uri":"http://admin.seeo.cn/upload/documentary/20171012/0a3143c5b779ff14f9b05b9523f89354.jpg"}
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

    public static class DataBean {
        /**
         * id : 2241
         * uri : http://admin.seeo.cn/upload/documentary/20171012/0a3143c5b779ff14f9b05b9523f89354.jpg
         */

        private int id;
        private String uri;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }
}

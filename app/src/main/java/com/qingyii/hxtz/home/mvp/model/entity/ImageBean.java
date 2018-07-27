package com.qingyii.hxtz.home.mvp.model.entity;

import java.util.List;

public class ImageBean {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":1,"url":"http://res.seeo.cn/uploads/qdt/1.jpg"},{"id":2,"url":"http://res.seeo.cn/uploads/qdt/2.jpg"}]
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

    public static class DataBean {
        /**
         * id : 1
         * url : http://res.seeo.cn/uploads/qdt/1.jpg
         */

        private int id;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    /**
     * error_msg : success
     * error_code : 0
     * data : {"id":1,"url":"http://res.seeo.cn/uploads/qdt/1109-143_5b2b589c15f49.png"}
     */


}

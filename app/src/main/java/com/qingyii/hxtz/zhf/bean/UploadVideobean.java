package com.qingyii.hxtz.zhf.bean;

/**
 * Created by zhf on 2017/12/28.
 */

public class UploadVideobean {

    /**
     * error_msg : success
     * error_code : 0
     * data : {"id":0,"uri":"http://res.seeo.cn/uploads/khsp/2018/05/0-2322_5b06210132209.mp4"}
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
         * id : 0
         * uri : http://res.seeo.cn/uploads/khsp/2018/05/0-2322_5b06210132209.mp4
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

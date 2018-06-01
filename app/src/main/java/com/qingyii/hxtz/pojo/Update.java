package com.qingyii.hxtz.pojo;

/**
 * Created by XRJ on 17/3/7.
 */

public class Update {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"version":"300112","download":"xxxx","update":0}
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
         * version : 300112
         * download : xxxx
         * update : 0
         */

        private String version;
        private String download;
        private int update;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public int getUpdate() {
            return update;
        }

        public void setUpdate(int update) {
            this.update = update;
        }
    }
    /**
     * error_msg : success
     * error_code : 0
     * data : {"timestamp":1488876212,"version":"357","download":"http://pre.im/HXTAPK"}
     */


}

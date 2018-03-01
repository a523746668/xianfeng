package com.qingyii.hxt.pojo;

/**
 * Created by XRJ on 17/3/7.
 */

public class CircleMark {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"notify":5,"announcement":1,"documentary":12}
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
         * notify : 5
         * announcement : 1
         * documentary : 12
         */

        private int notify;
        private int announcement;
        private int documentary;

        public int getNotify() {
            return notify;
        }

        public void setNotify(int notify) {
            this.notify = notify;
        }

        public int getAnnouncement() {
            return announcement;
        }

        public void setAnnouncement(int announcement) {
            this.announcement = announcement;
        }

        public int getDocumentary() {
            return documentary;
        }

        public void setDocumentary(int documentary) {
            this.documentary = documentary;
        }
    }
}

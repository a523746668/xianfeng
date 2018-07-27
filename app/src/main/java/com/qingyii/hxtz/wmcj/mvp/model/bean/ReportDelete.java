package com.qingyii.hxtz.wmcj.mvp.model.bean;

public class ReportDelete {

    /**
     * error_msg : success
     * error_code : 0
     * data : 1
     */

    private String error_msg;
    private int error_code;
    private int data;

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}

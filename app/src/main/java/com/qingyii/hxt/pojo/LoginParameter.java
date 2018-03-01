package com.qingyii.hxt.pojo;

/**
 * Created by Administrator on 2016/8/19.
 */

public class LoginParameter {
    /**
     * error_msg : success
     * error_code : 0
     * data : eyJ0Q
     */

    private String error_msg;
    private int error_code;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

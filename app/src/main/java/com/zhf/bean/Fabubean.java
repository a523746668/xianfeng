package com.zhf.bean;

/**
 * Created by zhf on 2017/10/12.
 */

public class Fabubean {

    /**
     * error_msg : success
     * error_code : 0
     * data : 提交活动成功
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

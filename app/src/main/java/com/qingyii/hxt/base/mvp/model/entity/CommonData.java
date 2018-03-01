package com.qingyii.hxt.base.mvp.model.entity;

/**
 * Created by xubo on 2017/6/15.
 *
 * 泛型通用实体类
 */

public class CommonData<T> {
    public final  static String SUCCESS = "success";
    /**
     * error_msg : success
     * error_code : 0
     * data : success
     */

    private String error_msg;
    private int error_code;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

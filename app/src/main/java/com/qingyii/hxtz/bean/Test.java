package com.qingyii.hxtz.bean;

public class Test {

    /**
     * error_msg : 您只能删除本单位上报的活动！
     * error_code : 1
     * data : null
     */

    private String error_msg;
    private int error_code;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

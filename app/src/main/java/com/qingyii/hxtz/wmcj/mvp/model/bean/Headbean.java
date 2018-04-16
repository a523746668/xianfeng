package com.qingyii.hxtz.wmcj.mvp.model.bean;

import java.util.List;

/**
 * Created by zhf on 2017/9/30.
 */

public class Headbean {


    /**
     * error_msg : success
     * error_code : 0
     * data : {"silder":[{"id":6,"a_status":1,"a_type":1,"a_name":"省直机关文明创建工作表彰会召开","q_id":0,"a_org_type":1,"a_org_id":7,"tags":"0","a_source":2,"a_time":"2017-07-25 00:00:06","is_delete":0,"creater_uid":1924,"update_uid":1924,"created_at":"2017-10-06 18:10:44","updated_at":"2017-10-06 18:10:44","img":"/upload/task/20171006/6219609d7013166746b1b6260319114a.JPG"},{"id":7,"a_status":1,"a_type":1,"a_name":"社区应急管理工作总结","q_id":0,"a_org_type":1,"a_org_id":7,"tags":"0","a_source":2,"a_time":"2017-10-06 00:00:07","is_delete":0,"creater_uid":1924,"update_uid":1924,"created_at":"2017-10-06 18:16:04","updated_at":"2017-10-06 18:16:04","img":"/upload/task/20171006/89fb59359b34d3a76183991335204c60.jpg"},{"id":8,"a_status":1,"a_type":1,"a_name":"青年志愿者协会活动工作总结","q_id":0,"a_org_type":1,"a_org_id":7,"tags":"0","a_source":2,"a_time":"2017-10-06 00:00:08","is_delete":0,"creater_uid":1924,"update_uid":1924,"created_at":"2017-10-06 18:20:08","updated_at":"2017-10-06 18:20:08","img":"/upload/task/20171006/1a29259384a457c3cfc118eb160b590c.jpg"},{"id":9,"a_status":1,"a_type":1,"a_name":"基础工作逾时上报的不计分测试内容","q_id":0,"a_org_type":1,"a_org_id":7,"tags":"0","a_source":2,"a_time":"2017-10-06 00:00:09","is_delete":0,"creater_uid":1924,"update_uid":1924,"created_at":"2017-10-06 18:25:09","updated_at":"2017-10-06 18:25:09","img":"/upload/task/20171006/895b323ce91dfd3e93ae5a74bfb2e882.png"},{"id":18,"a_status":1,"a_type":1,"a_name":"学雷锋精神、扬道德风尚","q_id":0,"a_org_type":1,"a_org_id":9,"tags":"0","a_source":2,"a_time":"2017-10-07 00:00:18","is_delete":0,"creater_uid":1926,"update_uid":1926,"created_at":"2017-10-07 10:15:22","updated_at":"2017-10-07 10:15:22","img":"/upload/task/20171007/2fa6cde9558a1d1a17023e98ef60722b.JPG"}]}
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
        private List<SilderBean> silder;

        public List<SilderBean> getSilder() {
            return silder;
        }

        public void setSilder(List<SilderBean> silder) {
            this.silder = silder;
        }

        public static class SilderBean {
            /**
             * id : 6
             * a_status : 1
             * a_type : 1
             * a_name : 省直机关文明创建工作表彰会召开
             * q_id : 0
             * a_org_type : 1
             * a_org_id : 7
             * tags : 0
             * a_source : 2
             * a_time : 2017-07-25 00:00:06
             * is_delete : 0
             * creater_uid : 1924
             * update_uid : 1924
             * created_at : 2017-10-06 18:10:44
             * updated_at : 2017-10-06 18:10:44
             * img : /upload/task/20171006/6219609d7013166746b1b6260319114a.JPG
             */

            private int id;
            private int a_status;
            private int a_type;
            private String a_name;
            private int q_id;
            private int a_org_type;
            private int a_org_id;
            private String tags;
            private int a_source;
            private String a_time;
            private int is_delete;
            private int creater_uid;
            private int update_uid;
            private String created_at;
            private String updated_at;
            private String img;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getA_status() {
                return a_status;
            }

            public void setA_status(int a_status) {
                this.a_status = a_status;
            }

            public int getA_type() {
                return a_type;
            }

            public void setA_type(int a_type) {
                this.a_type = a_type;
            }

            public String getA_name() {
                return a_name;
            }

            public void setA_name(String a_name) {
                this.a_name = a_name;
            }

            public int getQ_id() {
                return q_id;
            }

            public void setQ_id(int q_id) {
                this.q_id = q_id;
            }

            public int getA_org_type() {
                return a_org_type;
            }

            public void setA_org_type(int a_org_type) {
                this.a_org_type = a_org_type;
            }

            public int getA_org_id() {
                return a_org_id;
            }

            public void setA_org_id(int a_org_id) {
                this.a_org_id = a_org_id;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public int getA_source() {
                return a_source;
            }

            public void setA_source(int a_source) {
                this.a_source = a_source;
            }

            public String getA_time() {
                return a_time;
            }

            public void setA_time(String a_time) {
                this.a_time = a_time;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }

            public int getCreater_uid() {
                return creater_uid;
            }

            public void setCreater_uid(int creater_uid) {
                this.creater_uid = creater_uid;
            }

            public int getUpdate_uid() {
                return update_uid;
            }

            public void setUpdate_uid(int update_uid) {
                this.update_uid = update_uid;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}

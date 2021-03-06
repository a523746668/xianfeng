package com.qingyii.hxtz.pojo;

import java.util.List;

/**
 * Created by 63264 on 16/11/12.
 */

public class IntegralList {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":71,"truename":"admin","department":"技术部","stars":3,"score":520},{"id":56,"truename":"不快","department":"技术部","stars":3,"score":520},{"id":47,"truename":"杨杰","department":"技术部","stars":4,"score":510},{"id":67,"truename":"qq","department":"销售部","stars":2,"score":470},{"id":68,"truename":"zzz","department":"销售部","stars":2,"score":440},{"id":55,"truename":"老王","department":"技术部","stars":2,"score":440},{"id":126,"truename":"老六","department":"测试部门","stars":0,"score":387}]
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
         * id : 71
         * truename : admin
         * department : 技术部
         * stars : 3
         * score : 520
         */

        private int id;
        private String truename;
        private String department;
        private String punishtype;
        private int stars;
        private String score;

        public int getId() {
            return id;
        }

        public String getPunishtype (){

           return  this.punishtype;
        }

        public void setPunishtype(String punishtype){

            this.punishtype = punishtype;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }
}

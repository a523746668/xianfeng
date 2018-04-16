package com.qingyii.hxtz.pojo;

import java.util.List;

/**
 * Created by XRJ on 16/11/12.
 */

public class RankingList {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":47,"truename":"杨杰","department":"天团技术部","stars":4,"score":15},{"id":67,"truename":"qq","department":"天团技术部","stars":0,"score":30}]}
     */

    private String error_msg;
    private int error_code;

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

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 47
         * truename : 杨杰
         * department : 天团技术部
         * stars : 4
         * score : 15
         */

        private int id;
        private String truename;
        private String department;
        private int stars;
        private String score;
        private String punishtype;

        public String getPunishtype (){

            return  this.punishtype;
        }

        public void setPunishtype(String punishtype){

            this.punishtype = punishtype;
        }
        public int getId() {
            return id;
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

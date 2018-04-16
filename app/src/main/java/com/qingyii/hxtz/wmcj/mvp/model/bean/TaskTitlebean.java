package com.qingyii.hxtz.wmcj.mvp.model.bean;

import java.util.List;

/**
 * Created by zhf on 2017/9/25.
 */
// 任务清单标题
public class TaskTitlebean {


    @Override
    public String toString() {
        return "TaskTitlebean{" +
                "error_msg='" + error_msg + '\'' +
                ", error_code=" + error_code +
                ", data=" + data +
                '}';
    }

    /**
     * error_msg : success
     * error_code : 0
     * data : {"isadmin":false,"libsystem":[{"id":1,"title":"2017测试指标体系","industry_id":1},{"id":11,"title":"测试","industry_id":1}]}
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
         * isadmin : false
         * libsystem : [{"id":1,"title":"2017测试指标体系","industry_id":1},{"id":11,"title":"测试","industry_id":1}]
         */

        private boolean isadmin;
        private List<LibsystemBean> libsystem;

        public boolean isIsadmin() {
            return isadmin;
        }

        public void setIsadmin(boolean isadmin) {
            this.isadmin = isadmin;
        }

        public List<LibsystemBean> getLibsystem() {
            return libsystem;
        }

        public void setLibsystem(List<LibsystemBean> libsystem) {
            this.libsystem = libsystem;
        }

        public static class LibsystemBean {
            /**
             * id : 1
             * title : 2017测试指标体系
             * industry_id : 1
             */

            private int id;
            private String title;
            private int industry_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIndustry_id() {
                return industry_id;
            }

            public void setIndustry_id(int industry_id) {
                this.industry_id = industry_id;
            }
        }
    }
}

package com.qingyii.hxtz.wmcj.mvp.model.bean;


import java.util.List;

public class Taskdetailbean {


    /**
     * error_msg : success
     * error_code : 0
     * data : {"task":{"id":38,"task_type":"","created_at":"2017-10-06 15:18:19","updated_at":"2017-10-06 15:18:19","begin_at":"2017-01-01 00:00:00","data":"会议记录（纪要）照片、会议现场照片","status":1,"create_admin_id":1111,"create_industry_id":16,"score":"4.00","year":null,"unit":"year","times":1,"num":1,"cycle":"quarter","target":"党组（党委）年初专题研究文明创建工作。4 分","ind_lib_sys_id":29,"sort":1,"end_at":"2017-03-31 23:55:00","system_id":17,"industryName":"省直工委宣传部"},"indlibsyies":[{"id":23,"level":0,"levelChines":"一级","score":20,"title":"营造浓厚氛围夯实基础工作"},{"id":29,"level":1,"levelChines":"二级","score":8,"title":"创建氛围1"}]}
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
         * task : {"id":38,"task_type":"","created_at":"2017-10-06 15:18:19","updated_at":"2017-10-06 15:18:19","begin_at":"2017-01-01 00:00:00","data":"会议记录（纪要）照片、会议现场照片","status":1,"create_admin_id":1111,"create_industry_id":16,"score":"4.00","year":null,"unit":"year","times":1,"num":1,"cycle":"quarter","target":"党组（党委）年初专题研究文明创建工作。4 分","ind_lib_sys_id":29,"sort":1,"end_at":"2017-03-31 23:55:00","system_id":17,"industryName":"省直工委宣传部"}
         * indlibsyies : [{"id":23,"level":0,"levelChines":"一级","score":20,"title":"营造浓厚氛围夯实基础工作"},{"id":29,"level":1,"levelChines":"二级","score":8,"title":"创建氛围1"}]
         */

        private TaskBean task;
        private List<IndlibsyiesBean> indlibsyies;

        public TaskBean getTask() {
            return task;
        }

        public void setTask(TaskBean task) {
            this.task = task;
        }

        public List<IndlibsyiesBean> getIndlibsyies() {
            return indlibsyies;
        }

        public void setIndlibsyies(List<IndlibsyiesBean> indlibsyies) {
            this.indlibsyies = indlibsyies;
        }

        public static class TaskBean {
            /**
             * id : 38
             * task_type :
             * created_at : 2017-10-06 15:18:19
             * updated_at : 2017-10-06 15:18:19
             * begin_at : 2017-01-01 00:00:00
             * data : 会议记录（纪要）照片、会议现场照片
             * status : 1
             * create_admin_id : 1111
             * create_industry_id : 16
             * score : 4.00
             * year : null
             * unit : year
             * times : 1
             * num : 1
             * cycle : quarter
             * target : 党组（党委）年初专题研究文明创建工作。4 分
             * ind_lib_sys_id : 29
             * sort : 1
             * end_at : 2017-03-31 23:55:00
             * system_id : 17
             * industryName : 省直工委宣传部
             */
            private int industry_admin;

            public int getIndustry_admin() {
                return industry_admin;
            }

            public void setIndustry_admin(int industry_admin) {
                this.industry_admin = industry_admin;
            }


            private  int is_input;
            private int id;
            private String task_type;
            private String created_at;
            private String updated_at;
            private String begin_at;
            private String data;
            private int status;
            private int create_admin_id;
            private int create_industry_id;
            private String score;
            private Object year;
            private String unit;
            private int times;
            private int num;  //任务次数
            private String cycle;
            private String target;
            private int ind_lib_sys_id;
            private int sort;
            private String end_at;
            private int system_id;
            private String industryName;//执行组织

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            private String  accountName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTask_type() {
                return task_type;
            }

            public void setTask_type(String task_type) {
                this.task_type = task_type;
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

            public String getBegin_at() {
                return begin_at;
            }

            public void setBegin_at(String begin_at) {
                this.begin_at = begin_at;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getCreate_admin_id() {
                return create_admin_id;
            }

            public void setCreate_admin_id(int create_admin_id) {
                this.create_admin_id = create_admin_id;
            }


            public int getIs_input() {
                return is_input;
            }

            public void setIs_input(int is_input) {
                this.is_input = is_input;
            }

            public int getCreate_industry_id() {
                return create_industry_id;
            }

            public void setCreate_industry_id(int create_industry_id) {
                this.create_industry_id = create_industry_id;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public Object getYear() {
                return year;
            }

            public void setYear(Object year) {
                this.year = year;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getCycle() {
                return cycle;
            }

            public void setCycle(String cycle) {
                this.cycle = cycle;
            }

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }

            public int getInd_lib_sys_id() {
                return ind_lib_sys_id;
            }

            public void setInd_lib_sys_id(int ind_lib_sys_id) {
                this.ind_lib_sys_id = ind_lib_sys_id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getEnd_at() {
                return end_at;
            }

            public void setEnd_at(String end_at) {
                this.end_at = end_at;
            }

            public int getSystem_id() {
                return system_id;
            }

            public void setSystem_id(int system_id) {
                this.system_id = system_id;
            }

            public String getIndustryName() {
                return industryName;
            }

            public void setIndustryName(String industryName) {
                this.industryName = industryName;
            }
        }

        public static class IndlibsyiesBean {
            /**
             * id : 23
             * level : 0
             * levelChines : 一级
             * score : 20
             * title : 营造浓厚氛围夯实基础工作
             */

            private int id;
            private int level;
            private String levelChines;
            private int score;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getLevelChines() {
                return levelChines;
            }

            public void setLevelChines(String levelChines) {
                this.levelChines = levelChines;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}

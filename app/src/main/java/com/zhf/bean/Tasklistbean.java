package com.zhf.bean;

import java.util.List;

/**
 * Created by xubo on 2017/9/20.
 */


//task
// list对应的数据源
public class Tasklistbean {


    /**
     * error_msg : success
     * error_code : 0
     * data : {"indlibsyies":[{"id":2,"system_id":1,"parent_id":1,"library_id":108,"level":1,"row":4,"parent_link":"107-108","score":6,"title":"阿斯蒂芬","haschild":3},{"id":3,"system_id":1,"parent_id":1,"library_id":109,"level":1,"row":7,"parent_link":"107-109","score":9,"title":"添加","haschild":4},{"id":4,"system_id":1,"parent_id":1,"library_id":111,"level":1,"row":1,"parent_link":"107-111","score":0,"title":"小米官网","haschild":0}],"task":[{"id":1,"target":"22222","score":"3.00"}]}
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
        private List<IndlibsyiesBean> indlibsyies;
        private List<TaskBean> task;

        public List<IndlibsyiesBean> getIndlibsyies() {
            return indlibsyies;
        }

        public void setIndlibsyies(List<IndlibsyiesBean> indlibsyies) {
            this.indlibsyies = indlibsyies;
        }

        public List<TaskBean> getTask() {
            return task;
        }

        public void setTask(List<TaskBean> task) {
            this.task = task;
        }

        public static class IndlibsyiesBean {
            /**
             * id : 2
             * system_id : 1
             * parent_id : 1
             * library_id : 108
             * level : 1
             * row : 4
             * parent_link : 107-108
             * score : 6
             * title : 阿斯蒂芬
             * haschild : 3
             */

            private int id;
            private int system_id;
            private int parent_id;
            private int library_id;
            private int level;
            private int row;
            private String parent_link;
            private int score;
            private String title;
            private int haschild;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSystem_id() {
                return system_id;
            }

            public void setSystem_id(int system_id) {
                this.system_id = system_id;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getLibrary_id() {
                return library_id;
            }

            public void setLibrary_id(int library_id) {
                this.library_id = library_id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRow() {
                return row;
            }

            public void setRow(int row) {
                this.row = row;
            }

            public String getParent_link() {
                return parent_link;
            }

            public void setParent_link(String parent_link) {
                this.parent_link = parent_link;
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

            public int getHaschild() {
                return haschild;
            }

            public void setHaschild(int haschild) {
                this.haschild = haschild;
            }
        }

        public static class TaskBean {
            /**
             * id : 1
             * target : 22222
             * score : 3.00
             */

            private int id;
            private String target;
            private String score;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }
        }
    }
}

package com.qingyii.hxtz.wmcj.mvp.model.bean;

import java.util.List;

/**
 * Created by zhf on 2017/10/11.
 */

public class TaskListViewBean {


    /**
     * id : 10
     * system_id : 1
     * parent_id : 0
     * library_id : 10
     * level : 0
     * row : 2
     * parent_link : 10
     * name : 落实工作任务总结工作情况
     * child : [{"id":15,"system_id":1,"parent_id":10,"library_id":15,"level":1,"row":1,"parent_link":"10-15","name":"落实工作任务","mytask":[{"id":18,"target":"落实省直机关文明办组织开展的工作任务情况。5分。","score":"5.00"}]},{"id":16,"system_id":1,"parent_id":10,"library_id":16,"level":1,"row":1,"parent_link":"10-16","name":"全年工作总结","mytask":[{"id":19,"target":"深入总结本单位本部门全年文明创建工作情况（2000字以内） 5 分。","score":"4.00"}]}]
     */

    private int id;
    private int system_id;
    private int parent_id;
    private int library_id;
    private int level;
    private int row;
    private String parent_link;
    private String name;
    private List<ChildBean> child;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean {
        /**
         * id : 15
         * system_id : 1
         * parent_id : 10
         * library_id : 15
         * level : 1
         * row : 1
         * parent_link : 10-15
         * name : 落实工作任务
         * mytask : [{"id":18,"target":"落实省直机关文明办组织开展的工作任务情况。5分。","score":"5.00"}]
         */
        private List<ChildBean> child;

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        private int id;
        private int system_id;
        private int parent_id;
        private int library_id;
        private int level;
        private int row;
        private String parent_link;
        private String name;
        private List<MytaskBean> mytask;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<MytaskBean> getMytask() {
            return mytask;
        }

        public void setMytask(List<MytaskBean> mytask) {
            this.mytask = mytask;
        }

        public static class MytaskBean {
            /**
             * id : 18
             * target : 落实省直机关文明办组织开展的工作任务情况。5分。
             * score : 5.00
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

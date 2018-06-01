package com.qingyii.hxtz.wmcj.mvp.model.bean;

import java.util.List;

public class ExamineMenu {

    /**
     * error_msg : success
     * error_code : 0
     * data : {"firstSystem":[{"id":1,"title":"2017年文明单位日常工作考评细则表"},{"id":15,"title":"2018年文明创建日常工作考评细则"}],"tagList":[{"id":25,"tag_type_id":5,"name":"党建","created_uid":1},{"id":1923,"tag_type_id":5,"name":"湖南省文明创建","created_uid":1},{"id":1929,"tag_type_id":5,"name":"线上测试","created_uid":1113},{"id":1932,"tag_type_id":5,"name":"全国文明单位","created_uid":2887}]}
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
        private List<FirstSystemBean> firstSystem;
        private List<TagListBean> tagList;

        public List<FirstSystemBean> getFirstSystem() {
            return firstSystem;
        }

        public void setFirstSystem(List<FirstSystemBean> firstSystem) {
            this.firstSystem = firstSystem;
        }

        public List<TagListBean> getTagList() {
            return tagList;
        }

        public void setTagList(List<TagListBean> tagList) {
            this.tagList = tagList;
        }

        public static class FirstSystemBean {
            /**
             * id : 1
             * title : 2017年文明单位日常工作考评细则表
             */

            private int id;
            private String title;

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
        }

        public static class TagListBean {
            /**
             * id : 25
             * tag_type_id : 5
             * name : 党建
             * created_uid : 1
             */

            private int id;
            private int tag_type_id;
            private String name;
            private int created_uid;
            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTag_type_id() {
                return tag_type_id;
            }

            public void setTag_type_id(int tag_type_id) {
                this.tag_type_id = tag_type_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCreated_uid() {
                return created_uid;
            }

            public void setCreated_uid(int created_uid) {
                this.created_uid = created_uid;
            }
        }
    }
}

package com.zhf.bean;

import java.util.List;

/**
 * Created by zhf on 2017/9/22.
 */
//结果bean
public class Resultbean {


    /**
     * error_msg : success
     * error_code : 0
     * data : {"my":{"id":249,"name":"连商公司党委","parent_id":0,"score":0,"myscore":0,"parent":null},"parentindustry":{"id":2,"name":"省委党建行业组2","description":"省委党建行业组","sort":0,"status":1,"parent_id":1,"created_at":null,"updated_at":"2017-09-28 10:09:47","top_id":3,"tags":"","order":1,"totle":2},"topIndustry":{"id":3,"name":"欧舒丹","description":"省委党建行业组","sort":0,"status":1,"parent_id":4,"created_at":null,"updated_at":"2017-09-28 10:09:47","top_id":3,"tags":"","order":1,"totle":7},"brothindustry":[{"id":25,"industry_sub_id":0,"group_id":371,"sort":0,"status":1,"name":"文明创建","score":0,"myscore":0,"ranking":1},{"id":32,"industry_sub_id":0,"group_id":249,"sort":0,"status":1,"name":"连商公司党委","score":0,"myscore":0,"ranking":2}]}
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
         * my : {"id":249,"name":"连商公司党委","parent_id":0,"score":0,"myscore":0,"parent":null}
         * parentindustry : {"id":2,"name":"省委党建行业组2","description":"省委党建行业组","sort":0,"status":1,"parent_id":1,"created_at":null,"updated_at":"2017-09-28 10:09:47","top_id":3,"tags":"","order":1,"totle":2}
         * topIndustry : {"id":3,"name":"欧舒丹","description":"省委党建行业组","sort":0,"status":1,"parent_id":4,"created_at":null,"updated_at":"2017-09-28 10:09:47","top_id":3,"tags":"","order":1,"totle":7}
         * brothindustry : [{"id":25,"industry_sub_id":0,"group_id":371,"sort":0,"status":1,"name":"文明创建","score":0,"myscore":0,"ranking":1},{"id":32,"industry_sub_id":0,"group_id":249,"sort":0,"status":1,"name":"连商公司党委","score":0,"myscore":0,"ranking":2}]
         */

        private MyBean my;
        private ParentindustryBean parentindustry;
        private TopIndustryBean topIndustry;
        private List<BrothindustryBean> brothindustry;

        public MyBean getMy() {
            return my;
        }

        public void setMy(MyBean my) {
            this.my = my;
        }

        public ParentindustryBean getParentindustry() {
            return parentindustry;
        }

        public void setParentindustry(ParentindustryBean parentindustry) {
            this.parentindustry = parentindustry;
        }

        public TopIndustryBean getTopIndustry() {
            return topIndustry;
        }

        public void setTopIndustry(TopIndustryBean topIndustry) {
            this.topIndustry = topIndustry;
        }

        public List<BrothindustryBean> getBrothindustry() {
            return brothindustry;
        }

        public void setBrothindustry(List<BrothindustryBean> brothindustry) {
            this.brothindustry = brothindustry;
        }

        public static class MyBean {
            /**
             * id : 249
             * name : 连商公司党委
             * parent_id : 0
             * score : 0
             * myscore : 0
             * parent : null
             */

            private int id;
            private String name;
            private int parent_id;
            private int score;
            private int myscore;
            private Object parent;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getMyscore() {
                return myscore;
            }

            public void setMyscore(int myscore) {
                this.myscore = myscore;
            }

            public Object getParent() {
                return parent;
            }

            public void setParent(Object parent) {
                this.parent = parent;
            }
        }

        public static class ParentindustryBean {
            /**
             * id : 2
             * name : 省委党建行业组2
             * description : 省委党建行业组
             * sort : 0
             * status : 1
             * parent_id : 1
             * created_at : null
             * updated_at : 2017-09-28 10:09:47
             * top_id : 3
             * tags :
             * order : 1
             * totle : 2
             */

            private int id;
            private String name;
            private String description;
            private int sort;
            private int status;
            private int parent_id;
            private Object created_at;
            private String updated_at;
            private int top_id;
            private String tags;
            private int order;
            private int totle;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public Object getCreated_at() {
                return created_at;
            }

            public void setCreated_at(Object created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public int getTop_id() {
                return top_id;
            }

            public void setTop_id(int top_id) {
                this.top_id = top_id;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public int getTotle() {
                return totle;
            }

            public void setTotle(int totle) {
                this.totle = totle;
            }
        }

        public static class TopIndustryBean {
            /**
             * id : 3
             * name : 欧舒丹
             * description : 省委党建行业组
             * sort : 0
             * status : 1
             * parent_id : 4
             * created_at : null
             * updated_at : 2017-09-28 10:09:47
             * top_id : 3
             * tags :
             * order : 1
             * totle : 7
             */

            private int id;
            private String name;
            private String description;
            private int sort;
            private int status;
            private int parent_id;
            private Object created_at;
            private String updated_at;
            private int top_id;
            private String tags;
            private int order;
            private int totle;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public Object getCreated_at() {
                return created_at;
            }

            public void setCreated_at(Object created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public int getTop_id() {
                return top_id;
            }

            public void setTop_id(int top_id) {
                this.top_id = top_id;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public int getTotle() {
                return totle;
            }

            public void setTotle(int totle) {
                this.totle = totle;
            }
        }

        public static class BrothindustryBean {
            /**
             * id : 25
             * industry_sub_id : 0
             * group_id : 371
             * sort : 0
             * status : 1
             * name : 文明创建
             * score : 0
             * myscore : 0
             * ranking : 1
             */

            private int id;
            private int industry_sub_id;
            private int group_id;
            private int sort;
            private int status;
            private String name;
            private int score;
            private int myscore;
            private int ranking;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIndustry_sub_id() {
                return industry_sub_id;
            }

            public void setIndustry_sub_id(int industry_sub_id) {
                this.industry_sub_id = industry_sub_id;
            }

            public int getGroup_id() {
                return group_id;
            }

            public void setGroup_id(int group_id) {
                this.group_id = group_id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getMyscore() {
                return myscore;
            }

            public void setMyscore(int myscore) {
                this.myscore = myscore;
            }

            public int getRanking() {
                return ranking;
            }

            public void setRanking(int ranking) {
                this.ranking = ranking;
            }
        }
    }
}

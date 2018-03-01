package com.zhf.bean;

import java.util.List;

/**
 * Created by zhf on 2017/9/29.
 */

public class WorkParkbean {


    /**
     * error_msg : success
     * error_code : 0
     * data : {"menu_item":[{"id":0,"system_id":0,"title":"最新动态"},{"id":1,"system_id":1,"title":"营造浓厚氛围夯实工作基础"},{"id":2,"system_id":1,"title":"加强理论武装突出廉政建设"},{"id":7,"system_id":1,"title":"打造精神家园培育文明新风"},{"id":8,"system_id":1,"title":"优化内部管理推进综合治理"},{"id":9,"system_id":1,"title":"美化内外环境构筑生态文明"},{"id":10,"system_id":1,"title":"落实工作任务总结工作情况"}],"tag_item":[{"id":1924,"title":"文明创建"},{"id":1925,"title":"文明创建1"}]}
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
        private List<MenuItemBean> menu_item;
        private List<TagItemBean> tag_item;

        public List<MenuItemBean> getMenu_item() {
            return menu_item;
        }

        public void setMenu_item(List<MenuItemBean> menu_item) {
            this.menu_item = menu_item;
        }

        public List<TagItemBean> getTag_item() {
            return tag_item;
        }

        public void setTag_item(List<TagItemBean> tag_item) {
            this.tag_item = tag_item;
        }

        public static class MenuItemBean {
            /**
             * id : 0
             * system_id : 0
             * title : 最新动态
             */

            private int id;
            private int system_id;
            private String title;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class TagItemBean {
            /**
             * id : 1924
             * title : 文明创建
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
    }
}

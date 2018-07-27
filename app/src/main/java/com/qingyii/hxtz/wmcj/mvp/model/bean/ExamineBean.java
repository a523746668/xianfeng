package com.qingyii.hxtz.wmcj.mvp.model.bean;

import java.util.List;

public class ExamineBean {


    /**
     * error_msg : success
     * error_code : 0
     * data : {"title":"2018年文明创建日常工作考评细则","companyname":"省直机关工委","total":16401,"complete":5117,"percent":31.2,"sender":"省直机关工委","content":null,"doclist":[]}
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
         * title : 2018年文明创建日常工作考评细则
         * companyname : 省直机关工委
         * total : 16401
         * complete : 5117
         * percent : 31.2
         * sender : 省直机关工委
         * content : null
         * doclist : []
         */

        private String title;
        private String companyname;
        private int total;
        private int complete;
        private double percent;
        private String sender;
        private String content;
        private List<FileBean> doclist;
        private String enddate;
        private String completeobj;

        public String getCompleteobj() {
            return completeobj;
        }

        public void setCompleteobj(String completeobj) {
            this.completeobj = completeobj;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getComplete() {
            return complete;
        }

        public void setComplete(int complete) {
            this.complete = complete;
        }

        public double getPercent() {
            return percent;
        }

        public void setPercent(double percent) {
            this.percent = percent;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String  content) {
            this.content = content;
        }

        public List<FileBean> getDoclist() {
            return doclist;
        }

        public void setDoclist(List<FileBean> doclist) {
            this.doclist = doclist;
        }

        public  static  class FileBean{
           String filename;
           String fileurl;

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getFileurl() {
                return fileurl;
            }

            public void setFileurl(String fileurl) {
                this.fileurl = fileurl;
            }
        }
    }
}

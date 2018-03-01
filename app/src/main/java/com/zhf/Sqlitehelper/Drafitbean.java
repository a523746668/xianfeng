package com.zhf.Sqlitehelper;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/10/23.
 */


//草稿箱对应的数据模型
public class Drafitbean  {

    //对应的任务详情bean
    private int taskid;

   private String name;
    //上报标题
    String summary_title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String a_time;
    //上报内容
    String summary;
    String my_score;
    java.util.ArrayList<String > tags;
    //图片的本地地址,描述和地址用#分隔开，用类型开头（对应第一个上传文件）
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    //上床文件的本地地址（对应第二个台账记录）
    private ArrayList<String> selectedPhotos2 = new ArrayList<>();

    public ArrayList<String> getSelectedPhotos() {
        return selectedPhotos;
    }

    public void setSelectedPhotos(ArrayList<String> selectedPhotos) {
        this.selectedPhotos = selectedPhotos;
    }

    public ArrayList<String> getSelectedPhotos2() {
        return selectedPhotos2;
    }

    public void setSelectedPhotos2(ArrayList<String> selectedPhotos2) {
        this.selectedPhotos2 = selectedPhotos2;
    }

    //上传成功后的返回url
    ArrayList<String> imgs;

     ArrayList<String> files;
    String score_text;
    int is_show;
    int is_show_auditing;
    int show_range;
    int is_comment;
   //参与情况
    String situation;

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getSummary_title() {
        return summary_title;
    }

    public void setSummary_title(String summary_title) {
        this.summary_title = summary_title;
    }

    public String getA_time() {
        return a_time;
    }

    public void setA_time(String a_time) {
        this.a_time = a_time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMy_score() {
        return my_score;
    }

    public void setMy_score(String my_score) {
        this.my_score = my_score;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }

    public String getScore_text() {
        return score_text;
    }

    public void setScore_text(String score_text) {
        this.score_text = score_text;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getIs_show_auditing() {
        return is_show_auditing;
    }

    public void setIs_show_auditing(int is_show_auditing) {
        this.is_show_auditing = is_show_auditing;
    }

    public int getShow_range() {
        return show_range;
    }

    public void setShow_range(int show_range) {
        this.show_range = show_range;
    }

    public int getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(int is_comment) {
        this.is_comment = is_comment;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }
}

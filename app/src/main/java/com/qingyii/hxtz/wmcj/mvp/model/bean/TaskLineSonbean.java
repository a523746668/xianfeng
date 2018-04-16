package com.qingyii.hxtz.wmcj.mvp.model.bean;

/**
 * Created by zhf on 2017/10/11.
 */
//任务清单列表要展示的数据
public class TaskLineSonbean {

    private String  taskname;
    private String  score;
    private String level;
    private boolean istask=false;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean istask() {
        return istask;
    }

    public void setIstask(boolean istask) {
        this.istask = istask;
    }
}

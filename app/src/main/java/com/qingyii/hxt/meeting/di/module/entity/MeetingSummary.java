package com.qingyii.hxt.meeting.di.module.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xubo on 2017/8/21.
 */

public class MeetingSummary {
    /**
     * allow_comment : 0
     * audit : 0
     * file : ["a.jpg","b.jpg"]
     * picture : ["a.jpg","b.jpg"]
     * show : 0
     * visibility : 0
     * meeting_id
     * user_id
     * title
     * content
     */

    @SerializedName("allow_comment")
    private int allow_commentX;
    @SerializedName("audits")
    private int auditX;
    @SerializedName("show")
    private int showX;
    @SerializedName("visibility")
    private int visibilityX;
    @SerializedName("meeting_id")
    private String meeting_id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("file")
    private List<String> fileX;
    @SerializedName("picture")
    private String pictureX;

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAllow_commentX() {
        return allow_commentX;
    }

    public void setAllow_commentX(int allow_commentX) {
        this.allow_commentX = allow_commentX;
    }

    public int getAuditX() {
        return auditX;
    }

    public void setAuditX(int auditX) {
        this.auditX = auditX;
    }

    public int getShowX() {
        return showX;
    }

    public void setShowX(int showX) {
        this.showX = showX;
    }

    public int getVisibilityX() {
        return visibilityX;
    }

    public void setVisibilityX(int visibilityX) {
        this.visibilityX = visibilityX;
    }

    public List<String> getFileX() {
        return fileX;
    }

    public void setFileX(List<String> fileX) {
        this.fileX = fileX;
    }

    public String getPictureX() {
        return pictureX;
    }

    public void setPictureX(String pictureX) {
        this.pictureX = pictureX;
    }

}

package com.qingyii.hxtz.pojo;

import java.io.Serializable;

public class PeriodsArticleRela implements Serializable {

    /**
     *
     */

    private static final long serialVersionUID = 464682017995808837L;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRelaid() {
        return relaid;
    }

    public void setRelaid(String relaid) {
        this.relaid = relaid;
    }

    public String getPeriodsid() {
        return periodsid;
    }

    public void setPeriodsid(String periodsid) {
        this.periodsid = periodsid;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {

        this.content = content;

    }

    public String getReadcount() {
        return readcount;
    }

    public void setReadcount(String readcount) {
        this.readcount = readcount;
    }

    public String getPraisecount() {
        return praisecount;
    }

    public void setPraisecount(String praisecount) {
        this.praisecount = praisecount;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }


    public int getIsVote() {
        return isVote;
    }

    public void setIsVote(int isVote) {
        this.isVote = isVote;
    }


    public int getDiscussflag() {
        return discussflag;
    }

    public void setDiscussflag(int discussflag) {
        this.discussflag = discussflag;
    }


    public String getPicaddress() {
        return picaddress;
    }

    public void setPicaddress(String picaddress) {
        this.picaddress = picaddress;
    }


    public String getArticledesc() {
        return articledesc;
    }

    public void setArticledesc(String articledesc) {
        this.articledesc = articledesc;
    }


    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    /**
     * 图片地址
     */
    private String picaddress;
    private int discussflag;  //1为可评论，2为不可评论
    private String createtime;
    private String readcount;
    private String praisecount;
    private String content;
    private String relaid;
    private String periodsid;
    private String articleid;//文章id
    private String title;
    private int isVote; //1--需要投票，2--无需投票
    private String articledesc; /** 内刊主题内容 **/
    /**
     * 点赞标志, 0表示未点赞，1表示已点赞
     */
    private int flag = 0;
    private String author;


}

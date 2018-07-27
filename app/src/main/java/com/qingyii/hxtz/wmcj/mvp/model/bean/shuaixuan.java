package com.qingyii.hxtz.wmcj.mvp.model.bean;

import java.util.ArrayList;
import java.util.List;

public class shuaixuan {
    List<ReportMenu.DataBean.AllsonIndustryBean> danwei=new ArrayList<>();
    List<ReportMenu.DataBean.MenuItemBean> onetag=new ArrayList<>();
    List<ReportMenu.DataBean.MenuItem1Bean> twotag=new ArrayList<>();
    List<ReportMenu.DataBean.TagItemBean> ttags=new ArrayList<>();

    public List<ReportMenu.DataBean.TagItemBean> getTtags() {
        return ttags;
    }

    public void setTtags(List<ReportMenu.DataBean.TagItemBean> ttags) {
        this.ttags = ttags;
    }

    public List<ReportMenu.DataBean.AllsonIndustryBean> getDanwei() {
        return danwei;
    }

    public void setDanwei(List<ReportMenu.DataBean.AllsonIndustryBean> danwei) {
        this.danwei = danwei;
    }

    public List<ReportMenu.DataBean.MenuItemBean> getOnetag() {
        return onetag;
    }

    public void setOnetag(ArrayList<ReportMenu.DataBean.MenuItemBean> onetag) {
        this.onetag = onetag;
    }

    public List<ReportMenu.DataBean.MenuItem1Bean> getTwotag() {
        return twotag;
    }

    public void setTwotag(ArrayList<ReportMenu.DataBean.MenuItem1Bean> twotag) {
        this.twotag = twotag;
    }

}

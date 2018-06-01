package com.qingyii.hxtz.zhf.bean;

import java.io.Serializable;

/**
 * Created by zhf on 2017/12/9.
 */

public class SendTask   implements Serializable{
       public final  static  String IMG="img";
        public final static  String VIDEO="video";

         private  String type;//类型
         private String uri;//图片或者视频的本地地址
         private String content="";//图片或者视频的描述
         private String path;  //选择视频的缩略图
         private String weburl;  //上传成功后服务器返回的url;
         private String weburlpath;//上传缩率图成功后返回的url.

    public String getWeburlpath() {
        return weburlpath;
    }

    public void setWeburlpath(String weburlpath) {
        this.weburlpath = weburlpath;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    @Override
    public String toString() {
        return "SendTask{" +
                "type='" + type + '\'' +
                ", uri='" + uri + '\'' +
                ", content='" + content + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

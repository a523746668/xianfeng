package com.qingyii.hxt.view;

import android.os.Handler;
import android.support.annotation.NonNull;

/**
 * Created by 63264 on 16/10/10.
 */

public class Photo {
//    String url = 本地地址
//    String netUrl = 服务器地址
//    String tumbUrl = 缩略图地址

    private String url;
    private int id ;
    private Handler handler;

    public Photo(String url, int id, Handler handler) {
        this.url = url;
        this.id = id;
        this.handler = handler;
    }

    private int photoid ;
    private int sendStatus; //发送状态  //0 准备发送、1 正在发送、2 发送成功 3 发送失败

    public int getPhotoid() {
        return photoid;
    }

    public void setPhotoid(int photoid) {
        this.photoid = photoid;
    }

    public int getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }
}

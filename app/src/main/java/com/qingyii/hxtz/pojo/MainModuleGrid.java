package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by XRJ on 16/12/21.
 */

public class MainModuleGrid implements Parcelable {
    private String module;//模块
    private int moduleTip;//模块新消息
    private String moduleText;//模块名字
    private int moduleImage;//模块图片

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getModuleTip() {
        return moduleTip;
    }

    public void setModuleTip(int moduleTip) {
        this.moduleTip = moduleTip;
    }

    public String getModuleText() {
        return moduleText;
    }

    public void setModuleText(String moduleText) {
        this.moduleText = moduleText;
    }

    public int getModuleImage() {
        return moduleImage;
    }

    public void setModuleImage(int moduleImage) {
        this.moduleImage = moduleImage;
    }

    public MainModuleGrid() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.module);
        dest.writeInt(this.moduleTip);
        dest.writeString(this.moduleText);
        dest.writeInt(this.moduleImage);
    }

    protected MainModuleGrid(Parcel in) {
        this.module = in.readString();
        this.moduleTip = in.readInt();
        this.moduleText = in.readString();
        this.moduleImage = in.readInt();
    }

    public static final Creator<MainModuleGrid> CREATOR = new Creator<MainModuleGrid>() {
        @Override
        public MainModuleGrid createFromParcel(Parcel source) {
            return new MainModuleGrid(source);
        }

        @Override
        public MainModuleGrid[] newArray(int size) {
            return new MainModuleGrid[size];
        }
    };
}

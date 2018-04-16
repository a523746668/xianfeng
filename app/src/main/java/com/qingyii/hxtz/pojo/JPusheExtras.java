package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 63264 on 17/1/9.
 */

public class JPusheExtras implements Parcelable {
    /**
     * type : announcement
     * id : 80
     */

    private String type;
    private int id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeInt(this.id);
    }

    public JPusheExtras() {
    }

    protected JPusheExtras(Parcel in) {
        this.type = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<JPusheExtras> CREATOR = new Parcelable.Creator<JPusheExtras>() {
        @Override
        public JPusheExtras createFromParcel(Parcel source) {
            return new JPusheExtras(source);
        }

        @Override
        public JPusheExtras[] newArray(int size) {
            return new JPusheExtras[size];
        }
    };
}

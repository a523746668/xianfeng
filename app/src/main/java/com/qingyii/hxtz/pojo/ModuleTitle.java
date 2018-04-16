package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 63264 on 16/12/16.
 */

public class ModuleTitle implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"alias":"先锋云平台","modules":["magazine","book","exams","notify","announcement","train","documentary"]}
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

    public static class DataBean implements Parcelable {
        /**
         * alias : 先锋云平台
         * modules : ["magazine","book","exams","notify","announcement","train","documentary"]
         */

        private String alias;
        private List<String> modules;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public List<String> getModules() {
            return modules;
        }

        public void setModules(List<String> modules) {
            this.modules = modules;
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.alias);
            dest.writeStringList(this.modules);
        }

        protected DataBean(Parcel in) {
            this.alias = in.readString();
            this.modules = in.createStringArrayList();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error_msg);
        dest.writeInt(this.error_code);
        dest.writeParcelable(this.data, flags);
    }

    public ModuleTitle() {
    }

    protected ModuleTitle(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ModuleTitle> CREATOR = new Parcelable.Creator<ModuleTitle>() {
        @Override
        public ModuleTitle createFromParcel(Parcel source) {
            return new ModuleTitle(source);
        }

        @Override
        public ModuleTitle[] newArray(int size) {
            return new ModuleTitle[size];
        }
    };
}

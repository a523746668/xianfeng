package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by XRJ on 16/9/11.
 */
public class TrainFilesList implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":3,"filename":"index.png","uri":"http://xf.ccketang.com/upload/training/20160ea.png","mime":"image/png","size":118437,"description":""}]
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 3
     * filename : index.png
     * uri : http://xf.ccketang.com/upload/training/20160ea.png
     * mime : image/png
     * size : 118437
     * description :
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private int id;
        private String filename;
        private String uri;
        private String mime;
        private int size;
        private String description;
        private boolean isDownload;

        public boolean isDownload() {
            return isDownload;
        }

        public void setDownload(boolean download) {
            isDownload = download;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getMime() {
            return mime;
        }

        public void setMime(String mime) {
            this.mime = mime;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.filename);
            dest.writeString(this.uri);
            dest.writeString(this.mime);
            dest.writeInt(this.size);
            dest.writeString(this.description);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.filename = in.readString();
            this.uri = in.readString();
            this.mime = in.readString();
            this.size = in.readInt();
            this.description = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
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
        dest.writeTypedList(this.data);
    }

    public TrainFilesList() {
    }

    protected TrainFilesList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<TrainFilesList> CREATOR = new Parcelable.Creator<TrainFilesList>() {
        @Override
        public TrainFilesList createFromParcel(Parcel source) {
            return new TrainFilesList(source);
        }

        @Override
        public TrainFilesList[] newArray(int size) {
            return new TrainFilesList[size];
        }
    };
}

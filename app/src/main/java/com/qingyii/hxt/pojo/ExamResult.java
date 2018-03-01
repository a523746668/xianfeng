package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by XRJ on 2016/8/19.
 */

public class ExamResult implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"rank":3,"duration":57,"score":"0.00","stages":2,"logs":[{"examtype":"repeat","created_at":"2016-12-10 16:42:33","result":"2关","duration":57,"times":6},{"examtype":"repeat","created_at":"2016-12-10 16:42:19","result":"1关","duration":6,"times":5},{"examtype":"repeat","created_at":"2016-12-10 16:41:20","result":"1关","duration":4,"times":4},{"examtype":"repeat","created_at":"2016-12-10 16:40:46","result":"1关","duration":23,"times":3},{"examtype":"repeat","created_at":"2016-12-10 16:36:41","result":"2关","duration":64,"times":2},{"examtype":"repeat","created_at":"2016-12-10 16:29:43","result":"2关","duration":66,"times":1}]}
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
         * rank : 3
         * duration : 57
         * score : 0.00
         * stages : 2
         * logs : [{"examtype":"repeat","created_at":"2016-12-10 16:42:33","result":"2关","duration":57,"times":6},{"examtype":"repeat","created_at":"2016-12-10 16:42:19","result":"1关","duration":6,"times":5},{"examtype":"repeat","created_at":"2016-12-10 16:41:20","result":"1关","duration":4,"times":4},{"examtype":"repeat","created_at":"2016-12-10 16:40:46","result":"1关","duration":23,"times":3},{"examtype":"repeat","created_at":"2016-12-10 16:36:41","result":"2关","duration":64,"times":2},{"examtype":"repeat","created_at":"2016-12-10 16:29:43","result":"2关","duration":66,"times":1}]
         */

        private int rank;
        private int duration;
        private String result;
        private List<LogsBean> logs;

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public List<LogsBean> getLogs() {
            return logs;
        }

        public void setLogs(List<LogsBean> logs) {
            this.logs = logs;
        }

        public static class LogsBean implements Parcelable {
            /**
             * examtype : repeat
             * created_at : 2016-12-10 16:42:33
             * result : 2关
             * duration : 57
             * times : 6
             */

            private String examtype;
            private String created_at;
            private String result;
            private int duration;
            private int times;

            public String getExamtype() {
                return examtype;
            }

            public void setExamtype(String examtype) {
                this.examtype = examtype;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.examtype);
                dest.writeString(this.created_at);
                dest.writeString(this.result);
                dest.writeInt(this.duration);
                dest.writeInt(this.times);
            }

            public LogsBean() {
            }

            protected LogsBean(Parcel in) {
                this.examtype = in.readString();
                this.created_at = in.readString();
                this.result = in.readString();
                this.duration = in.readInt();
                this.times = in.readInt();
            }

            public static final Parcelable.Creator<LogsBean> CREATOR = new Parcelable.Creator<LogsBean>() {
                @Override
                public LogsBean createFromParcel(Parcel source) {
                    return new LogsBean(source);
                }

                @Override
                public LogsBean[] newArray(int size) {
                    return new LogsBean[size];
                }
            };
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.rank);
            dest.writeInt(this.duration);
            dest.writeString(this.result);
            dest.writeTypedList(this.logs);
        }

        protected DataBean(Parcel in) {
            this.rank = in.readInt();
            this.duration = in.readInt();
            this.result = in.readString();
            this.logs = in.createTypedArrayList(LogsBean.CREATOR);
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

    public ExamResult() {
    }

    protected ExamResult(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ExamResult> CREATOR = new Parcelable.Creator<ExamResult>() {
        @Override
        public ExamResult createFromParcel(Parcel source) {
            return new ExamResult(source);
        }

        @Override
        public ExamResult[] newArray(int size) {
            return new ExamResult[size];
        }
    };
}

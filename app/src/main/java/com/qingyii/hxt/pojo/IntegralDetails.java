package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by XRJ on 16/11/15.
 */

public class IntegralDetails implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"total":860,"score":{"履职积分":100,"学习积分":110,"纪律积分":110,"品德积分":110},"punishtype":"","reason":""}
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
         * total : 860
         * score : {"履职积分":100,"学习积分":110,"纪律积分":110,"品德积分":110}
         * punishtype :
         * reason :
         */

        private String total;
        private ScoreBean score;
        private String punishtype;
        private String reason;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public ScoreBean getScore() {
            return score;
        }

        public void setScore(ScoreBean score) {
            this.score = score;
        }

        public String getPunishtype() {
            return punishtype;
        }

        public void setPunishtype(String punishtype) {
            this.punishtype = punishtype;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public static class ScoreBean implements Parcelable {
            /**
             * 履职积分 : 100
             * 学习积分 : 110
             * 纪律积分 : 110
             * 品德积分 : 110
             */

            private String 作用积分;
            private String 政治积分;
            private String 纪律积分;
            private String 品德积分;

            public String get作用积分() {
                return 作用积分;
            }

            public void set作用积分(String 作用积分) {
                this.作用积分 = 作用积分;
            }

            public String get政治积分() {
                return 政治积分;
            }

            public void set政治积分(String 政治积分) {
                this.政治积分 = 政治积分;
            }

            public String get纪律积分() {
                return 纪律积分;
            }

            public void set纪律积分(String 纪律积分) {
                this.纪律积分 = 纪律积分;
            }

            public String get品德积分() {
                return 品德积分;
            }

            public void set品德积分(String 品德积分) {
                this.品德积分 = 品德积分;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.作用积分);
                dest.writeString(this.政治积分);
                dest.writeString(this.纪律积分);
                dest.writeString(this.品德积分);
            }

            public ScoreBean() {
            }

            protected ScoreBean(Parcel in) {
                this.作用积分 = in.readString();
                this.政治积分 = in.readString();
                this.纪律积分 = in.readString();
                this.品德积分 = in.readString();
            }

            public static final Creator<ScoreBean> CREATOR = new Creator<ScoreBean>() {
                @Override
                public ScoreBean createFromParcel(Parcel source) {
                    return new ScoreBean(source);
                }

                @Override
                public ScoreBean[] newArray(int size) {
                    return new ScoreBean[size];
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
            dest.writeString(this.total);
            dest.writeParcelable(this.score, flags);
            dest.writeString(this.punishtype);
            dest.writeString(this.reason);
        }

        protected DataBean(Parcel in) {
            this.total = in.readString();
            this.score = in.readParcelable(ScoreBean.class.getClassLoader());
            this.punishtype = in.readString();
            this.reason = in.readString();
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

    public IntegralDetails() {
    }

    protected IntegralDetails(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<IntegralDetails> CREATOR = new Parcelable.Creator<IntegralDetails>() {
        @Override
        public IntegralDetails createFromParcel(Parcel source) {
            return new IntegralDetails(source);
        }

        @Override
        public IntegralDetails[] newArray(int size) {
            return new IntegralDetails[size];
        }
    };
}

package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 63264 on 16/11/13.
 */

public class AuditCrewList implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"usres":[{"user_id":154,"username":"刘婷","appeal":0,"unchecked":0,"unconfirm":0,"count":[{"name":"活动","num":2}]},{"user_id":155,"username":"张莉","appeal":0,"unchecked":0,"unconfirm":0,"count":[{"name":"活动","num":1}]},{"user_id":156,"username":"李莉","appeal":0,"unchecked":0,"unconfirm":0,"count":[{"name":"履职","num":1},{"name":"学习","num":1}]},{"user_id":157,"username":"李满英","appeal":0,"unchecked":0,"unconfirm":0,"count":[{"name":"诚信","num":1},{"name":"履职","num":1},{"name":"学习","num":1}]},{"user_id":159,"username":"丁巍","appeal":0,"unchecked":0,"unconfirm":0,"count":[{"name":"活动","num":1},{"name":"履职","num":1}]}],"is_checked":1}
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
         * usres : [{"user_id":154,"username":"刘婷","appeal":0,"unchecked":0,"unconfirm":0,"count":[{"name":"活动","num":2}]},{"user_id":155,"username":"张莉","appeal":0,"unchecked":0,"unconfirm":0,"count":[{"name":"活动","num":1}]},{"user_id":156,"username":"李莉","appeal":0,"unchecked":0,"unconfirm":0,"count":[{"name":"履职","num":1},{"name":"学习","num":1}]},{"user_id":157,"username":"李满英","appeal":0,"unchecked":0,"unconfirm":0,"count":[{"name":"诚信","num":1},{"name":"履职","num":1},{"name":"学习","num":1}]},{"user_id":159,"username":"丁巍","appeal":0,"unchecked":0,"unconfirm":0,"count":[{"name":"活动","num":1},{"name":"履职","num":1}]}]
         * is_checked : 1
         */

        private int is_checked;
        private List<UsresBean> usres;

        public int getIs_checked() {
            return is_checked;
        }

        public void setIs_checked(int is_checked) {
            this.is_checked = is_checked;
        }

        public List<UsresBean> getUsres() {
            return usres;
        }

        public void setUsres(List<UsresBean> usres) {
            this.usres = usres;
        }

        public static class UsresBean implements Parcelable {
            /**
             * user_id : 154
             * username : 刘婷
             * appeal : 0
             * unchecked : 0
             * unconfirm : 0
             * count : [{"name":"活动","num":2}]
             */

            private int user_id;
            private String username;
            private int appeal;
            private int unchecked;
            private int unconfirm;
            private List<CountBean> count;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public int getAppeal() {
                return appeal;
            }

            public void setAppeal(int appeal) {
                this.appeal = appeal;
            }

            public int getUnchecked() {
                return unchecked;
            }

            public void setUnchecked(int unchecked) {
                this.unchecked = unchecked;
            }

            public int getUnconfirm() {
                return unconfirm;
            }

            public void setUnconfirm(int unconfirm) {
                this.unconfirm = unconfirm;
            }

            public List<CountBean> getCount() {
                return count;
            }

            public void setCount(List<CountBean> count) {
                this.count = count;
            }

            public static class CountBean implements Parcelable {
                /**
                 * name : 活动
                 * num : 2
                 */

                private String name;
                private int num;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.name);
                    dest.writeInt(this.num);
                }

                public CountBean() {
                }

                protected CountBean(Parcel in) {
                    this.name = in.readString();
                    this.num = in.readInt();
                }

                public static final Parcelable.Creator<CountBean> CREATOR = new Parcelable.Creator<CountBean>() {
                    @Override
                    public CountBean createFromParcel(Parcel source) {
                        return new CountBean(source);
                    }

                    @Override
                    public CountBean[] newArray(int size) {
                        return new CountBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.user_id);
                dest.writeString(this.username);
                dest.writeInt(this.appeal);
                dest.writeInt(this.unchecked);
                dest.writeInt(this.unconfirm);
                dest.writeTypedList(this.count);
            }

            public UsresBean() {
            }

            protected UsresBean(Parcel in) {
                this.user_id = in.readInt();
                this.username = in.readString();
                this.appeal = in.readInt();
                this.unchecked = in.readInt();
                this.unconfirm = in.readInt();
                this.count = in.createTypedArrayList(CountBean.CREATOR);
            }

            public static final Parcelable.Creator<UsresBean> CREATOR = new Parcelable.Creator<UsresBean>() {
                @Override
                public UsresBean createFromParcel(Parcel source) {
                    return new UsresBean(source);
                }

                @Override
                public UsresBean[] newArray(int size) {
                    return new UsresBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.is_checked);
            dest.writeTypedList(this.usres);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.is_checked = in.readInt();
            this.usres = in.createTypedArrayList(UsresBean.CREATOR);
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
        dest.writeParcelable(this.data, flags);
    }

    public AuditCrewList() {
    }

    protected AuditCrewList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<AuditCrewList> CREATOR = new Parcelable.Creator<AuditCrewList>() {
        @Override
        public AuditCrewList createFromParcel(Parcel source) {
            return new AuditCrewList(source);
        }

        @Override
        public AuditCrewList[] newArray(int size) {
            return new AuditCrewList[size];
        }
    };
}

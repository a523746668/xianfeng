package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 63264 on 16/9/22.
 */

public class ManageList implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":47,"email":"15345678909@chiigo.com","truename":"杨杰","phone":"15345678909","nickname":"4444","gender":"secret","device_id":"","checkdata":{"count":3,"stars":4,"score":15,"total":415}},{"id":55,"email":"13312345678@chiigo.com","truename":"速度","phone":"13312345678","nickname":"速度","gender":"secret","device_id":"","checkdata":{"count":0,"stars":0,"score":0,"total":0}},{"id":56,"email":"","truename":"不快","phone":"13312345679","nickname":"不快","gender":"secret","device_id":"","checkdata":{"count":1,"stars":0,"score":0,"total":0}},{"id":61,"email":"","truename":"易新","phone":"18170361875","nickname":"易新","gender":"secret","device_id":"","checkdata":{"count":0,"stars":0,"score":0,"total":0}},{"id":62,"email":"","truename":"易煌","phone":"18270361875","nickname":"易煌","gender":"secret","device_id":"","checkdata":{"count":0,"stars":0,"score":0,"total":0}},{"id":64,"email":"","truename":"张三","phone":"13000000000","nickname":"张三","gender":"secret","device_id":"","checkdata":{"count":0,"stars":0,"score":0,"total":0}},{"id":65,"email":"","truename":"钟","phone":"13111111111","nickname":"钟","gender":"secret","device_id":"","checkdata":{"count":0,"stars":0,"score":0,"total":0}}]
     */

    private String error_msg;
    private int error_code;
    /**
     * id : 47
     * email : 15345678909@chiigo.com
     * truename : 杨杰
     * phone : 15345678909
     * nickname : 4444
     * gender : secret
     * device_id :
     * checkdata : {"count":3,"stars":4,"score":15,"total":415}
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
        private String email;
        private String truename;
        private String phone;
        private String nickname;
        private String gender;
        private String device_id;
        /**
         * count : 3
         * stars : 4
         * score : 15
         * total : 415
         */

        private CheckdataBean checkdata;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public CheckdataBean getCheckdata() {
            return checkdata;
        }

        public void setCheckdata(CheckdataBean checkdata) {
            this.checkdata = checkdata;
        }

        public static class CheckdataBean implements Parcelable {
            private int count;
            private int stars;
            private int score;
            private int total;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getStars() {
                return stars;
            }

            public void setStars(int stars) {
                this.stars = stars;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.count);
                dest.writeInt(this.stars);
                dest.writeInt(this.score);
                dest.writeInt(this.total);
            }

            public CheckdataBean() {
            }

            protected CheckdataBean(Parcel in) {
                this.count = in.readInt();
                this.stars = in.readInt();
                this.score = in.readInt();
                this.total = in.readInt();
            }

            public static final Parcelable.Creator<CheckdataBean> CREATOR = new Parcelable.Creator<CheckdataBean>() {
                @Override
                public CheckdataBean createFromParcel(Parcel source) {
                    return new CheckdataBean(source);
                }

                @Override
                public CheckdataBean[] newArray(int size) {
                    return new CheckdataBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.email);
            dest.writeString(this.truename);
            dest.writeString(this.phone);
            dest.writeString(this.nickname);
            dest.writeString(this.gender);
            dest.writeString(this.device_id);
            dest.writeParcelable(this.checkdata, flags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.email = in.readString();
            this.truename = in.readString();
            this.phone = in.readString();
            this.nickname = in.readString();
            this.gender = in.readString();
            this.device_id = in.readString();
            this.checkdata = in.readParcelable(CheckdataBean.class.getClassLoader());
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

    public ManageList() {
    }

    protected ManageList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<ManageList> CREATOR = new Parcelable.Creator<ManageList>() {
        @Override
        public ManageList createFromParcel(Parcel source) {
            return new ManageList(source);
        }

        @Override
        public ManageList[] newArray(int size) {
            return new ManageList[size];
        }
    };
}

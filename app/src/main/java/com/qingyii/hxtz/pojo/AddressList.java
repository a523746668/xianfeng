package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by XRJ on 16/11/21.
 */

public class AddressList implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":47,"email":"15345678909@chiigo.com","truename":"杨杰","phone":"15345678909","gender":"secret","political":"群众","avatar":"http://localhost:8000/upload/3d69af3fd7.jpg","jobname":"","department":{"id":57,"name":"技术部"}}]
     */

    private String error_msg;
    private int error_code;
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
        /**
         * id : 47
         * email : 15345678909@chiigo.com
         * truename : 杨杰
         * phone : 15345678909
         * gender : secret
         * political : 群众
         * avatar : http://localhost:8000/upload/3d69af3fd7.jpg
         * jobname :
         * department : {"id":57,"name":"技术部"}
         */

        private int id;
        private String email;
        private String truename;
        private String phone;
        private String gender;
        private String political;
        private String avatar;
        private String jobname;
        private DepartmentBean department;

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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPolitical() {
            return political;
        }

        public void setPolitical(String political) {
            this.political = political;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getJobname() {
            return jobname;
        }

        public void setJobname(String jobname) {
            this.jobname = jobname;
        }

        public DepartmentBean getDepartment() {
            return department;
        }

        public void setDepartment(DepartmentBean department) {
            this.department = department;
        }

        public static class DepartmentBean implements Parcelable {
            /**
             * id : 57
             * name : 技术部
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
            }

            public DepartmentBean() {
            }

            protected DepartmentBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
            }

            public static final Parcelable.Creator<DepartmentBean> CREATOR = new Parcelable.Creator<DepartmentBean>() {
                @Override
                public DepartmentBean createFromParcel(Parcel source) {
                    return new DepartmentBean(source);
                }

                @Override
                public DepartmentBean[] newArray(int size) {
                    return new DepartmentBean[size];
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
            dest.writeString(this.gender);
            dest.writeString(this.political);
            dest.writeString(this.avatar);
            dest.writeString(this.jobname);
            dest.writeParcelable(this.department, flags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.email = in.readString();
            this.truename = in.readString();
            this.phone = in.readString();
            this.gender = in.readString();
            this.political = in.readString();
            this.avatar = in.readString();
            this.jobname = in.readString();
            this.department = in.readParcelable(DepartmentBean.class.getClassLoader());
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

    public AddressList() {
    }

    protected AddressList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<AddressList> CREATOR = new Parcelable.Creator<AddressList>() {
        @Override
        public AddressList createFromParcel(Parcel source) {
            return new AddressList(source);
        }

        @Override
        public AddressList[] newArray(int size) {
            return new AddressList[size];
        }
    };
}

package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/8/20.
 */

public class UserParameter implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"id":204,"email":"13574221122@139.com","truename":"邬捷","phone":"13574221122","nickname":"邬捷","gender":"secret","avatar":"http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg","jobname":"","birthday":"1980-11-22","political":"正式党员","joinparty_at":"2016-11-25","idcode":"","is_leader":1,"device_id":"","check_level":0,"is_follow":1,"join_doc":1,"department":{"id":110,"name":"党群工作部","address":"","phone":"","fax":"","logo":"","appname":"","website":"","addrshow":0,"leader":"","secondary":"","remark":"","status":"normal"},"company":{"id":109,"name":"潭耒分公司","address":"","phone":"","fax":"","logo":"","appname":"","website":"","addrshow":0,"leader":"","secondary":"","remark":"","status":"normal"}}
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
         * id : 204
         * email : 13574221122@139.com
         * truename : 邬捷
         * phone : 13574221122
         * nickname : 邬捷
         * gender : secret
         * avatar : http://xf.ccketang.com/upload/avatar/20161123/274ad4786c3abca69fa097b85867d9a4.jpg
         * jobname :
         * birthday : 1980-11-22
         * political : 正式党员
         * joinparty_at : 2016-11-25
         * idcode :
         * is_leader : 1
         * device_id :
         * check_level : 0
         * is_follow : 1
         * join_doc : 1
         * department : {"id":110,"name":"党群工作部","address":"","phone":"","fax":"","logo":"","appname":"","website":"","addrshow":0,"leader":"","secondary":"","remark":"","status":"normal"}
         * company : {"id":109,"name":"潭耒分公司","address":"","phone":"","fax":"","logo":"","appname":"","website":"","addrshow":0,"leader":"","secondary":"","remark":"","status":"normal"}
         */

        private int id;
        private String email;
        private String truename;
        private String phone;
        private String nickname;
        private String gender;
        private String avatar;
        private String jobname;
        private String birthday;
        private String political;
        private String joinparty_at;
        private String idcode;
        private int is_leader;
        private String device_id;
        private int check_level;
        private int is_follow;
        private int join_doc;
        private int account_id;
        private DepartmentBean department;
        private CompanyBean company;

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

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

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getPolitical() {
            return political;
        }

        public void setPolitical(String political) {
            this.political = political;
        }

        public String getJoinparty_at() {
            return joinparty_at;
        }

        public void setJoinparty_at(String joinparty_at) {
            this.joinparty_at = joinparty_at;
        }

        public String getIdcode() {
            return idcode;
        }

        public void setIdcode(String idcode) {
            this.idcode = idcode;
        }

        public int getIs_leader() {
            return is_leader;
        }

        public void setIs_leader(int is_leader) {
            this.is_leader = is_leader;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public int getCheck_level() {
            return check_level;
        }

        public void setCheck_level(int check_level) {
            this.check_level = check_level;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }

        public int getJoin_doc() {
            return join_doc;
        }

        public void setJoin_doc(int join_doc) {
            this.join_doc = join_doc;
        }

        public DepartmentBean getDepartment() {
            return department;
        }

        public void setDepartment(DepartmentBean department) {
            this.department = department;
        }

        public CompanyBean getCompany() {
            return company;
        }

        public void setCompany(CompanyBean company) {
            this.company = company;
        }

        public static class DepartmentBean implements Parcelable {
            /**
             * id : 110
             * name : 党群工作部
             * address :
             * phone :
             * fax :
             * logo :
             * appname :
             * website :
             * addrshow : 0
             * leader :
             * secondary :
             * remark :
             * status : normal
             */

            private int id;
            private String name;
            private String address;
            private String phone;
            private String fax;
            private String logo;
            private String appname;
            private String website;
            private int addrshow;
            private String leader;
            private String secondary;
            private String remark;
            private String status;

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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getFax() {
                return fax;
            }

            public void setFax(String fax) {
                this.fax = fax;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getAppname() {
                return appname;
            }

            public void setAppname(String appname) {
                this.appname = appname;
            }

            public String getWebsite() {
                return website;
            }

            public void setWebsite(String website) {
                this.website = website;
            }

            public int getAddrshow() {
                return addrshow;
            }

            public void setAddrshow(int addrshow) {
                this.addrshow = addrshow;
            }

            public String getLeader() {
                return leader;
            }

            public void setLeader(String leader) {
                this.leader = leader;
            }

            public String getSecondary() {
                return secondary;
            }

            public void setSecondary(String secondary) {
                this.secondary = secondary;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeString(this.address);
                dest.writeString(this.phone);
                dest.writeString(this.fax);
                dest.writeString(this.logo);
                dest.writeString(this.appname);
                dest.writeString(this.website);
                dest.writeInt(this.addrshow);
                dest.writeString(this.leader);
                dest.writeString(this.secondary);
                dest.writeString(this.remark);
                dest.writeString(this.status);
            }

            public DepartmentBean() {
            }

            protected DepartmentBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.address = in.readString();
                this.phone = in.readString();
                this.fax = in.readString();
                this.logo = in.readString();
                this.appname = in.readString();
                this.website = in.readString();
                this.addrshow = in.readInt();
                this.leader = in.readString();
                this.secondary = in.readString();
                this.remark = in.readString();
                this.status = in.readString();
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

        public static class CompanyBean implements Parcelable {
            /**
             * id : 109
             * name : 潭耒分公司
             * address :
             * phone :
             * fax :
             * logo :
             * appname :
             * website :
             * addrshow : 0
             * leader :
             * secondary :
             * remark :
             * status : normal
             */

            private int id;
            private String name;
            private String address;
            private String phone;
            private String fax;
            private String logo;
            private String appname;
            private String website;
            private int addrshow;
            private String leader;
            private String secondary;
            private String remark;
            private String status;

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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getFax() {
                return fax;
            }

            public void setFax(String fax) {
                this.fax = fax;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getAppname() {
                return appname;
            }

            public void setAppname(String appname) {
                this.appname = appname;
            }

            public String getWebsite() {
                return website;
            }

            public void setWebsite(String website) {
                this.website = website;
            }

            public int getAddrshow() {
                return addrshow;
            }

            public void setAddrshow(int addrshow) {
                this.addrshow = addrshow;
            }

            public String getLeader() {
                return leader;
            }

            public void setLeader(String leader) {
                this.leader = leader;
            }

            public String getSecondary() {
                return secondary;
            }

            public void setSecondary(String secondary) {
                this.secondary = secondary;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeString(this.address);
                dest.writeString(this.phone);
                dest.writeString(this.fax);
                dest.writeString(this.logo);
                dest.writeString(this.appname);
                dest.writeString(this.website);
                dest.writeInt(this.addrshow);
                dest.writeString(this.leader);
                dest.writeString(this.secondary);
                dest.writeString(this.remark);
                dest.writeString(this.status);
            }

            public CompanyBean() {
            }

            protected CompanyBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.address = in.readString();
                this.phone = in.readString();
                this.fax = in.readString();
                this.logo = in.readString();
                this.appname = in.readString();
                this.website = in.readString();
                this.addrshow = in.readInt();
                this.leader = in.readString();
                this.secondary = in.readString();
                this.remark = in.readString();
                this.status = in.readString();
            }

            public static final Parcelable.Creator<CompanyBean> CREATOR = new Parcelable.Creator<CompanyBean>() {
                @Override
                public CompanyBean createFromParcel(Parcel source) {
                    return new CompanyBean(source);
                }

                @Override
                public CompanyBean[] newArray(int size) {
                    return new CompanyBean[size];
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
            dest.writeInt(this.id);
            dest.writeString(this.email);
            dest.writeString(this.truename);
            dest.writeString(this.phone);
            dest.writeString(this.nickname);
            dest.writeString(this.gender);
            dest.writeString(this.avatar);
            dest.writeString(this.jobname);
            dest.writeString(this.birthday);
            dest.writeString(this.political);
            dest.writeString(this.joinparty_at);
            dest.writeString(this.idcode);
            dest.writeInt(this.is_leader);
            dest.writeString(this.device_id);
            dest.writeInt(this.check_level);
            dest.writeInt(this.is_follow);
            dest.writeInt(this.join_doc);
            dest.writeInt(this.account_id);
            dest.writeParcelable(this.department, flags);
            dest.writeParcelable(this.company, flags);
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.email = in.readString();
            this.truename = in.readString();
            this.phone = in.readString();
            this.nickname = in.readString();
            this.gender = in.readString();
            this.avatar = in.readString();
            this.jobname = in.readString();
            this.birthday = in.readString();
            this.political = in.readString();
            this.joinparty_at = in.readString();
            this.idcode = in.readString();
            this.is_leader = in.readInt();
            this.device_id = in.readString();
            this.check_level = in.readInt();
            this.is_follow = in.readInt();
            this.join_doc = in.readInt();
            this.account_id = in.readInt();
            this.department = in.readParcelable(DepartmentBean.class.getClassLoader());
            this.company = in.readParcelable(CompanyBean.class.getClassLoader());
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

    public UserParameter() {
    }

    protected UserParameter(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserParameter> CREATOR = new Parcelable.Creator<UserParameter>() {
        @Override
        public UserParameter createFromParcel(Parcel source) {
            return new UserParameter(source);
        }

        @Override
        public UserParameter[] newArray(int size) {
            return new UserParameter[size];
        }
    };
}

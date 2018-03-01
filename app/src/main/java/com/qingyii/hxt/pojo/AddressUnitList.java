package com.qingyii.hxt.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by XRJ on 16/11/29.
 */

public class AddressUnitList implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : [{"id":128,"name":"设计部","address":"","phone":"","fax":"","logo":"","appname":"","website":"","addrshow":0,"leader":"新煌","secondary":"","remark":""},{"id":127,"name":"营销部","address":"","phone":"","fax":"","logo":"","appname":"","website":"","addrshow":0,"leader":"王斌","secondary":"","remark":""}]
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
         * id : 128
         * name : 设计部
         * address :
         * phone :
         * fax :
         * logo :
         * appname :
         * website :
         * addrshow : 0
         * leader : 新煌
         * secondary :
         * remark :
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
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
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

    public AddressUnitList() {
    }

    protected AddressUnitList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<AddressUnitList> CREATOR = new Parcelable.Creator<AddressUnitList>() {
        @Override
        public AddressUnitList createFromParcel(Parcel source) {
            return new AddressUnitList(source);
        }

        @Override
        public AddressUnitList[] newArray(int size) {
            return new AddressUnitList[size];
        }
    };
}

package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 63264 on 16/11/13.
 */

public class AuditGroupList implements Parcelable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"is_checked":0,"is_reject":0,"groups":[{"id":165,"name":"二小组","is_haveuser":1,"leader":"","secondary":"","remark":"","people_cnt":13,"docs_cnt":6,"submit_cnt":2}]}
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
         * is_checked : 0
         * is_reject : 0
         * groups : [{"id":165,"name":"二小组","is_haveuser":1,"leader":"","secondary":"","remark":"","people_cnt":13,"docs_cnt":6,"submit_cnt":2}]
         */

        private int is_checked;
        private int is_reject;
        private List<GroupsBean> groups;

        public int getIs_checked() {
            return is_checked;
        }

        public void setIs_checked(int is_checked) {
            this.is_checked = is_checked;
        }

        public int getIs_reject() {
            return is_reject;
        }

        public void setIs_reject(int is_reject) {
            this.is_reject = is_reject;
        }

        public List<GroupsBean> getGroups() {
            return groups;
        }

        public void setGroups(List<GroupsBean> groups) {
            this.groups = groups;
        }

        public static class GroupsBean implements Parcelable {
            /**
             * id : 165
             * name : 二小组
             * is_haveuser : 1
             * leader :
             * secondary :
             * remark :
             * people_cnt : 13
             * docs_cnt : 6
             * submit_cnt : 2
             */

            private int id;
            private String name;
            private int is_haveuser;
            private String leader;
            private String secondary;
            private String remark;
            private int people_cnt;
            private int docs_cnt;
            private int submit_cnt;

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

            public int getIs_haveuser() {
                return is_haveuser;
            }

            public void setIs_haveuser(int is_haveuser) {
                this.is_haveuser = is_haveuser;
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

            public int getPeople_cnt() {
                return people_cnt;
            }

            public void setPeople_cnt(int people_cnt) {
                this.people_cnt = people_cnt;
            }

            public int getDocs_cnt() {
                return docs_cnt;
            }

            public void setDocs_cnt(int docs_cnt) {
                this.docs_cnt = docs_cnt;
            }

            public int getSubmit_cnt() {
                return submit_cnt;
            }

            public void setSubmit_cnt(int submit_cnt) {
                this.submit_cnt = submit_cnt;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeInt(this.is_haveuser);
                dest.writeString(this.leader);
                dest.writeString(this.secondary);
                dest.writeString(this.remark);
                dest.writeInt(this.people_cnt);
                dest.writeInt(this.docs_cnt);
                dest.writeInt(this.submit_cnt);
            }

            public GroupsBean() {
            }

            protected GroupsBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.is_haveuser = in.readInt();
                this.leader = in.readString();
                this.secondary = in.readString();
                this.remark = in.readString();
                this.people_cnt = in.readInt();
                this.docs_cnt = in.readInt();
                this.submit_cnt = in.readInt();
            }

            public static final Parcelable.Creator<GroupsBean> CREATOR = new Parcelable.Creator<GroupsBean>() {
                @Override
                public GroupsBean createFromParcel(Parcel source) {
                    return new GroupsBean(source);
                }

                @Override
                public GroupsBean[] newArray(int size) {
                    return new GroupsBean[size];
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
            dest.writeInt(this.is_reject);
            dest.writeTypedList(this.groups);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.is_checked = in.readInt();
            this.is_reject = in.readInt();
            this.groups = in.createTypedArrayList(GroupsBean.CREATOR);
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

    public AuditGroupList() {
    }

    protected AuditGroupList(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<AuditGroupList> CREATOR = new Parcelable.Creator<AuditGroupList>() {
        @Override
        public AuditGroupList createFromParcel(Parcel source) {
            return new AuditGroupList(source);
        }

        @Override
        public AuditGroupList[] newArray(int size) {
            return new AuditGroupList[size];
        }
    };
}

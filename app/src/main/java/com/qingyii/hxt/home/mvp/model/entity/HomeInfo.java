package com.qingyii.hxt.home.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by xubo on 2017/6/24.
 */

public class HomeInfo {

    /**
     * error_msg : success
     * error_code : 0
     * data : []
     * account : {"alias":"连商先锋","modules":[{"name":"通知","icon":"","mark":"notify","level":0},{"name":"会议","icon":"","mark":"meeting","level":0},{"name":"培训","icon":"","mark":"train","level":0},{"name":"文明创建","icon":"","mark":"wmcj","level":0},{"name":"党建辅导员","icon":"","mark":"djfdy","level":0},{"name":"刊物","icon":"","mark":"magazine","level":1},{"name":"书籍","icon":"","mark":"book","level":0},{"name":"考试","icon":"","mark":"exams","level":1},{"name":"公告","icon":"","mark":"announcement","level":1},{"name":"同事圈","icon":"","mark":"documentary","level":1}]}
     * message : {"system":0,"notify":0,"announcement":3,"documentary":17,"meeting":0,"train":0}
     * ad : {"INDEX_TOP":[{"id":1,"adtitle":"热烈庆祝现代投资股份有限公司潭耒分公司党员纪实管理系统正式运行！","adtype":0,"adwords":"纪念孙中山诞辰150周年大会举行 习近平发表讲话","adbanner":"","adlink":"http://www.hnredstar.gov.cn/","description":"","mark":"INDEX_TOP"}],"INDEX_BOTTOM":[{"id":2,"adtitle":"","adtype":0,"adwords":"纪念孙中山诞辰150周年大会举行 习近平发表讲话","adbanner":"","adlink":"","description":"","mark":"INDEX_BOTTOM"}],"INDEX_BANNER":[{"id":10,"adtitle":"中国共产党员网","adtype":1,"adwords":"","adbanner":"http://admin.seeo.cn/upload/ad/20170227/34d244972f0eb9c4a9b817fb62bcbbda.jpg","adlink":"http://cpc.people.com.cn/","description":"","mark":"INDEX_BANNER"}]}
     */

    private String error_msg;
    private int error_code;
    private AccountBean account;
    private MessageBean message;
    private AdBean ad;
    private List<?> data;

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

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public AdBean getAd() {
        return ad;
    }

    public void setAd(AdBean ad) {
        this.ad = ad;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public static class AccountBean implements Parcelable {

        /**
         * alias : 连商先锋
         * modules : [{"name":"通知","icon":"","mark":"notify","level":0},{"name":"会议","icon":"","mark":"meeting","level":0},{"name":"培训","icon":"","mark":"train","level":0},{"name":"文明创建","icon":"","mark":"wmcj","level":0},{"name":"党建辅导员","icon":"","mark":"djfdy","level":0},{"name":"刊物","icon":"","mark":"magazine","level":1},{"name":"书籍","icon":"","mark":"book","level":0},{"name":"考试","icon":"","mark":"exams","level":1},{"name":"公告","icon":"","mark":"announcement","level":1},{"name":"同事圈","icon":"","mark":"documentary","level":1}]
         */

        private String alias;
        private List<ModulesBean> modules;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public List<ModulesBean> getModules() {
            return modules;
        }

        public void setModules(List<ModulesBean> modules) {
            this.modules = modules;
        }

        public static class ModulesBean implements Parcelable {

            /**
             * name : 通知
             * icon :
             * mark : notify
             * level : 0
             */

            private String name;
            private String icon;
            private String mark;
            private int level;
            private int count;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public ModulesBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.name);
                dest.writeString(this.icon);
                dest.writeString(this.mark);
                dest.writeInt(this.level);
                dest.writeInt(this.count);
            }

            protected ModulesBean(Parcel in) {
                this.name = in.readString();
                this.icon = in.readString();
                this.mark = in.readString();
                this.level = in.readInt();
                this.count = in.readInt();
            }

            public static final Creator<ModulesBean> CREATOR = new Creator<ModulesBean>() {
                @Override
                public ModulesBean createFromParcel(Parcel source) {
                    return new ModulesBean(source);
                }

                @Override
                public ModulesBean[] newArray(int size) {
                    return new ModulesBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.alias);
            dest.writeTypedList(this.modules);
        }

        public AccountBean() {
        }

        protected AccountBean(Parcel in) {
            this.alias = in.readString();
            this.modules = in.createTypedArrayList(ModulesBean.CREATOR);
        }

        public static final Parcelable.Creator<AccountBean> CREATOR = new Parcelable.Creator<AccountBean>() {
            @Override
            public AccountBean createFromParcel(Parcel source) {
                return new AccountBean(source);
            }

            @Override
            public AccountBean[] newArray(int size) {
                return new AccountBean[size];
            }
        };
    }

    public static class MessageBean {
        /**
         * system : 0
         * notify : 0
         * announcement : 3
         * documentary : 17
         * meeting : 0
         * train : 0
         */

        private int system;
        private int notify;
        private int announcement;
        private int documentary;
        private int circle;
        private int meeting;
        private int train;

        public int getDocumentary() {
            return documentary;
        }

        public void setDocumentary(int documentary) {
            this.documentary = documentary;
        }

        public int getSystem() {
            return system;
        }

        public void setSystem(int system) {
            this.system = system;
        }

        public int getNotify() {
            return notify;
        }

        public void setNotify(int notify) {
            this.notify = notify;
        }

        public int getAnnouncement() {
            return announcement;
        }

        public void setAnnouncement(int announcement) {
            this.announcement = announcement;
        }

        public int getCircle() {
            return circle;
        }

        public void setCircle(int documentary) {
            this.circle = circle;
        }

        public int getMeeting() {
            return meeting;
        }

        public void setMeeting(int meeting) {
            this.meeting = meeting;
        }

        public int getTrain() {
            return train;
        }

        public void setTrain(int train) {
            this.train = train;
        }
    }

    public static class AdBean {
        private List<INDEXBANNERBean> INDEX_TOP;
        private List<INDEXBANNERBean> INDEX_BOTTOM;
        private List<INDEXBANNERBean> INDEX_BANNER;

        public List<INDEXBANNERBean> getINDEX_TOP() {
            return INDEX_TOP;
        }

        public void setINDEX_TOP(List<INDEXBANNERBean> INDEX_TOP) {
            this.INDEX_TOP = INDEX_TOP;
        }

        public List<INDEXBANNERBean> getINDEX_BOTTOM() {
            return INDEX_BOTTOM;
        }

        public void setINDEX_BOTTOM(List<INDEXBANNERBean> INDEX_BOTTOM) {
            this.INDEX_BOTTOM = INDEX_BOTTOM;
        }

        public List<INDEXBANNERBean> getINDEX_BANNER() {
            return INDEX_BANNER;
        }

        public void setINDEX_BANNER(List<INDEXBANNERBean> INDEX_BANNER) {
            this.INDEX_BANNER = INDEX_BANNER;
        }


        public static class INDEXBANNERBean implements Parcelable {

            /**
             * id : 10
             * adtitle : 中国共产党员网
             * adtype : 1
             * adwords :
             * adbanner : http://admin.seeo.cn/upload/ad/20170227/34d244972f0eb9c4a9b817fb62bcbbda.jpg
             * adlink : http://cpc.people.com.cn/
             * description :
             * mark : INDEX_BANNER
             */

            private int id;
            private String adtitle;
            private int adtype;
            private String adwords;
            private String adbanner;
            private String adlink;
            private String description;
            private String mark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAdtitle() {
                return adtitle;
            }

            public void setAdtitle(String adtitle) {
                this.adtitle = adtitle;
            }

            public int getAdtype() {
                return adtype;
            }

            public void setAdtype(int adtype) {
                this.adtype = adtype;
            }

            public String getAdwords() {
                return adwords;
            }

            public void setAdwords(String adwords) {
                this.adwords = adwords;
            }

            public String getAdbanner() {
                return adbanner;
            }

            public void setAdbanner(String adbanner) {
                this.adbanner = adbanner;
            }

            public String getAdlink() {
                return adlink;
            }

            public void setAdlink(String adlink) {
                this.adlink = adlink;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.adtitle);
                dest.writeInt(this.adtype);
                dest.writeString(this.adwords);
                dest.writeString(this.adbanner);
                dest.writeString(this.adlink);
                dest.writeString(this.description);
                dest.writeString(this.mark);
            }

            public INDEXBANNERBean() {
            }

            protected INDEXBANNERBean(Parcel in) {
                this.id = in.readInt();
                this.adtitle = in.readString();
                this.adtype = in.readInt();
                this.adwords = in.readString();
                this.adbanner = in.readString();
                this.adlink = in.readString();
                this.description = in.readString();
                this.mark = in.readString();
            }

            public static final Parcelable.Creator<INDEXBANNERBean> CREATOR = new Parcelable.Creator<INDEXBANNERBean>() {
                @Override
                public INDEXBANNERBean createFromParcel(Parcel source) {
                    return new INDEXBANNERBean(source);
                }

                @Override
                public INDEXBANNERBean[] newArray(int size) {
                    return new INDEXBANNERBean[size];
                }
            };
        }
    }
}

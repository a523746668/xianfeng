package com.qingyii.hxtz.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ExaminationPapers implements Parcelable {//Serializable{


    private String error_msg;
    private int error_code;


    private DataBean data;

    /**
     *
     */
    private static final long serialVersionUID = 5913662122949589027L;
    private String paperid;
    private String examinationid;
    /**
     * 密码锁（单词命题才有）
     */
    private String passwordlock;
    /**
     * 考试时长（答题闯关1才有，单位分钟）
     */
    private String duration;
    /**
     * 题目数量（答题闯关1才有）
     */
    private String topiccount;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public String getPaperid() {
        return paperid;
    }

    public void setPaperid(String paperid) {
        this.paperid = paperid;
    }

    public String getExaminationid() {
        return examinationid;
    }

    public void setExaminationid(String examinationid) {
        this.examinationid = examinationid;
    }

    public String getPasswordlock() {
        return passwordlock;
    }

    public void setPasswordlock(String passwordlock) {
        this.passwordlock = passwordlock;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTopiccount() {
        return topiccount;
    }

    public void setTopiccount(String topiccount) {
        this.topiccount = topiccount;
    }

    public String getExaminationname() {
        return examinationname;
    }

    public void setExaminationname(String examinationname) {
        this.examinationname = examinationname;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    private String examinationname;
    /**
     * 单选题每题分数
     */
    private String score1;
    /**
     * 多选题每题分数
     */
    private String score2;
    /**
     * 判断题每题分数
     */
    private String score3;
    /**
     * 考卷类型（1自主命题，2题库抽选）
     */
    private String type;
    /**
     * 题库类型id
     */
    private String typeid;
    private String ansercount;  //答题次数


    public String getAnsercount() {
        return ansercount;
    }

    public void setAnsercount(String ansercount) {
        this.ansercount = ansercount;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getScore3() {
        return score3;
    }

    public void setScore3(String score3) {
        this.score3 = score3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }


    public static class Userselection implements Parcelable {

        protected Userselection(Parcel in) {

        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Userselection> CREATOR = new Creator<Userselection>() {
            @Override
            public Userselection createFromParcel(Parcel in) {
                return new Userselection(in);
            }

            @Override
            public Userselection[] newArray(int size) {
                return new Userselection[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }


    public static class Option implements Parcelable {


        private String id;
        private String option;
        private int isanswer;


        public Option(String id, String option) {
            this.option = option;
            this.id = id;
        }

        protected Option(Parcel in) {

            this.id = in.readString();
            this.option = in.readString();
            this.isanswer = in.readInt();
        }

        public static final Creator<Option> CREATOR = new Creator<Option>() {
            @Override
            public Option createFromParcel(Parcel in) {
                return new Option(in);
            }

            @Override
            public Option[] newArray(int size) {
                return new Option[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.option);
            dest.writeInt(this.isanswer);
        }

        public int getIsanswer() {
            return isanswer;
        }

        public void setIsanswer(int isanswer) {
            this.isanswer = isanswer;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }


    public static class Question implements Parcelable {

        private String id;
        private String question;
        private String qtype;  //single  multi  judge
        private String answers;
        private String analyze;
        private int isAnswered = 0;

        private List<String> selection = new ArrayList<>();

        public List<String> getSelection() {
            return selection;
        }

        public void setSelection(List<String> selection) {
            this.selection = selection;
        }

        private List<Option> options;
        private List<Userselection> userselection;

        private int noAnswerInext = 0;  //是否做了这个题
        private boolean rightflag = false; //是否作对了这个题


        public List<Option> getOptions() {
            return options;
        }

        public void setOptions(List<Option> options) {
            this.options = options;
        }

        public String getAnalyze() {
            return analyze;
        }

        public void setAnalyze(String analyze) {
            this.analyze = analyze;
        }

        public String getAnswers() {
            return answers;
        }

        public void setAnswers(String answers) {
            this.answers = answers;
        }

        public String getQtype() {
            return qtype;
        }

        public void setQtype(String qtype) {
            this.qtype = qtype;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Userselection> getUserselection() {
            return userselection;
        }

        public void setUserselection(List<Userselection> userselection) {
            this.userselection = userselection;
        }

        public int getNoAnswerInext() {
            return noAnswerInext;
        }

        public void setNoAnswerInext(int noAnswerInext) {
            this.noAnswerInext = noAnswerInext;
        }

        public boolean isRightflag() {
            return rightflag;
        }

        public void setRightflag(boolean rightflag) {
            this.rightflag = rightflag;
        }

        public int getIsAnswered() {
            return isAnswered;
        }

        public void setIsAnswered(int isAnswered) {
            this.isAnswered = isAnswered;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.question);
            dest.writeString(this.qtype);
            dest.writeString(this.answers);
            dest.writeString(this.analyze);
            dest.writeInt(this.isAnswered);
            dest.writeStringList(this.selection);
            dest.writeTypedList(this.options);
            dest.writeTypedList(this.userselection);
            dest.writeInt(this.noAnswerInext);
            dest.writeByte(this.rightflag ? (byte) 1 : (byte) 0);
        }

        public Question() {
        }

        protected Question(Parcel in) {
            this.id = in.readString();
            this.question = in.readString();
            this.qtype = in.readString();
            this.answers = in.readString();
            this.analyze = in.readString();
            this.isAnswered = in.readInt();
            this.selection = in.createStringArrayList();
            this.options = in.createTypedArrayList(Option.CREATOR);
            this.userselection = in.createTypedArrayList(Userselection.CREATOR);
            this.noAnswerInext = in.readInt();
            this.rightflag = in.readByte() != 0;
        }

        public static final Creator<Question> CREATOR = new Creator<Question>() {
            @Override
            public Question createFromParcel(Parcel source) {
                return new Question(source);
            }

            @Override
            public Question[] newArray(int size) {
                return new Question[size];
            }
        };
    }


    public static class DataBean implements Parcelable {
        /**
         *
         */
        private static final long serialVersionUID = 5913662122949589027L;
        private String id;
        private String papername;
        private String description;
        private int qnums;
        private String gentype;
        private int examtimes;
        private String qbank_id;
        private String publishstatus;
        /**
         * 单选题每题分数
         */
        private String single_score;
        /**
         * 多选题每题分数
         */
        private String multi_score;
        /**
         * 判断题每题分数
         */
        private String judge_score;
        private int single_nums;
        private int multi_nums;
        private int judge_nums;
        private int passscore;
        private List<Question> questions = new ArrayList<Question>();
        /**
         * 密码锁（单词命题才有）
         */
        private String passwordlock;
        /**
         * 考试时长（答题闯关1才有，单位分钟）
         */
        private String duration;
        /**
         * 考试开始时间戳
         */
        private long timestamp;
        /**
         * 题目数量（答题闯关1才有）
         */
        private String topiccount;
        private String examinationname;
        /**
         * 考卷类型（1自主命题，2题库抽选）
         */
        private String type;
        /**
         * 题库类型id
         */
        private String typeid;
        private String ansercount;  //答题次数


        public DataBean() {
        }


        public String getPasswordlock() {
            return passwordlock;
        }

        public void setPasswordlock(String passwordlock) {
            this.passwordlock = passwordlock;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;

        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getPapername() {
            return papername;
        }

        public void setPapername(String papername) {
            this.papername = papername;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getQnums() {
            return qnums;
        }

        public void setQnums(int qnums) {
            this.qnums = qnums;
        }

        public String getGentype() {
            return gentype;
        }

        public void setGentype(String gentype) {
            this.gentype = gentype;
        }

        public int getExamtimes() {
            return examtimes;
        }

        public void setExamtimes(int examtimes) {
            this.examtimes = examtimes;
        }

        public String getQbank_id() {
            return qbank_id;
        }

        public void setQbank_id(String qbank_id) {
            this.qbank_id = qbank_id;
        }

        public String getPublishstatus() {
            return publishstatus;
        }

        public void setPublishstatus(String publishstatus) {
            this.publishstatus = publishstatus;
        }

        public int getSingle_nums() {
            return single_nums;
        }

        public String getSingle_score() {
            return single_score;
        }

        public void setSingle_score(String single_score) {
            this.single_score = single_score;
        }

        public String getMulti_score() {
            return multi_score;
        }

        public void setMulti_score(String multi_score) {
            this.multi_score = multi_score;
        }

        public String getJudge_score() {
            return judge_score;
        }

        public void setJudge_score(String judge_score) {
            this.judge_score = judge_score;
        }

        public void setSingle_nums(int single_nums) {
            this.single_nums = single_nums;
        }

        public int getMulti_nums() {
            return multi_nums;
        }

        public void setMulti_nums(int multi_nums) {
            this.multi_nums = multi_nums;
        }

        public int getJudge_nums() {
            return judge_nums;
        }

        public void setJudge_nums(int judge_nums) {
            this.judge_nums = judge_nums;
        }

        public int getPassscore() {
            return passscore;
        }

        public void setPassscore(int passscore) {
            this.passscore = passscore;
        }

        public List<Question> getQuestions() {
            return questions;
        }

        public void setQuestions(List<Question> questions) {
            this.questions = questions;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.papername);
            dest.writeString(this.description);
            dest.writeInt(this.qnums);
            dest.writeString(this.gentype);
            dest.writeInt(this.examtimes);
            dest.writeString(this.qbank_id);
            dest.writeString(this.publishstatus);
            dest.writeString(this.single_score);
            dest.writeString(this.multi_score);
            dest.writeString(this.judge_score);
            dest.writeInt(this.single_nums);
            dest.writeInt(this.multi_nums);
            dest.writeInt(this.judge_nums);
            dest.writeInt(this.passscore);
            dest.writeTypedList(this.questions);
            dest.writeString(this.passwordlock);
            dest.writeString(this.duration);
            dest.writeLong(this.timestamp);
            dest.writeString(this.topiccount);
            dest.writeString(this.examinationname);
            dest.writeString(this.type);
            dest.writeString(this.typeid);
            dest.writeString(this.ansercount);
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.papername = in.readString();
            this.description = in.readString();
            this.qnums = in.readInt();
            this.gentype = in.readString();
            this.examtimes = in.readInt();
            this.qbank_id = in.readString();
            this.publishstatus = in.readString();
            this.single_score = in.readString();
            this.multi_score = in.readString();
            this.judge_score = in.readString();
            this.single_nums = in.readInt();
            this.multi_nums = in.readInt();
            this.judge_nums = in.readInt();
            this.passscore = in.readInt();
            this.questions = in.createTypedArrayList(Question.CREATOR);
            this.passwordlock = in.readString();
            this.duration = in.readString();
            this.timestamp = in.readLong();
            this.topiccount = in.readString();
            this.examinationname = in.readString();
            this.type = in.readString();
            this.typeid = in.readString();
            this.ansercount = in.readString();
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

    public ExaminationPapers() {
    }

    protected ExaminationPapers(Parcel in) {
        this.error_msg = in.readString();
        this.error_code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        //this.data = in.createTypedArrayList(DataBean.CREATOR);
    }


    public static final Parcelable.Creator<ExaminationPapers> CREATOR = new Parcelable.Creator<ExaminationPapers>() {
        @Override
        public ExaminationPapers createFromParcel(Parcel source) {
            return new ExaminationPapers(source);
        }

        @Override
        public ExaminationPapers[] newArray(int size) {
            return new ExaminationPapers[size];
        }
    };


}

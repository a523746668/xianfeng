package com.qingyii.hxt;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.dialog.AbProgressDialogFragment;
import com.google.gson.Gson;
import com.qingyii.hxt.adapter.DatiViewflowAdapter;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.ExamList;
import com.qingyii.hxt.pojo.ExamSubmited;
import com.qingyii.hxt.pojo.ExaminationPapers;
import com.qingyii.hxt.util.EmptyUtil;
import com.qingyii.hxt.util.TextUtil;
import com.qingyii.hxt.util.TimeUtil;
import com.zhf.android_viewflow.ViewFlow;
import com.zhf.android_viewflow.ViewFlow.ViewSwitchListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Response;

import static com.qingyii.hxt.http.XrjHttpClient.PREPARE_EXAM_URL;
import static com.qingyii.hxt.http.XrjHttpClient.URL_PR;

/**
 * 答题闯关考试
 *
 * @author shelia
 */
public class DatiChuangguanActivity extends AbBaseActivity {

    public static HashMap<String, String> mLevel = new HashMap<String, String>();// 累计闯关的关数记录

    private AlertDialog mDialogFinish = null;
    // private AbSlidingTabView absTabView;
    private ImageView iv_back, iv_rank;
    private TextView tv_title;
    // private List<Fragment> mFragmentList;
    /**
     * 当前考卷
     */
    private ExaminationPapers.DataBean examinationPapers;
    private ExaminationPapers.DataBean epDataBean;
    private ExamList.DataBean examination;
    // private ExamContentFragment fragment;
    public ViewFlow kaoshi_viewflow;
    // private ListView listView;
    // private DatichuanggAdapter myAdapter;
    // private ArrayList<Question> list=new ArrayList<Question>();
    private TextView question_time, question_page;
    private TimeCount timeCount;
    private long start_time;
    private long end_time;

    private String answers;


    private boolean isWaitingChoose = false;//是否等待用户选择是否重闯

    /**
     * 考试用时
     */
    private int useTime = 0;
    /**
     * 闯关1考试时间到结束标志
     */
    private boolean overFlag = true;
    /**
     * 答题总得分数
     */
    public float count = 0;
    /**
     * 正确答题数
     */
    int rightNum = 0;
    // /**
    // * 未答题数
    // */
    // private ArrayList<Question> noAnserList=new ArrayList<Question>();

    /**
     * 未答题数
     */
    private ArrayList<ExaminationPapers.Question> noAnserList = new ArrayList<ExaminationPapers.Question>();

    private ArrayList<ExaminationPapers.Question> errQuestionList = new ArrayList<ExaminationPapers.Question>();

    /**
     * 错误答题数
     */
    int errorNum = 0;
    /**
     * 当前题目索引
     */
    public int selectIndext = 0;
    private TextView question_over;
    private View mDataView1;
    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//    private AbLoadDialogFragment mDialogFragment = null;
    private AbProgressDialogFragment mDialogFragment = null;
    private Handler handler = new Handler(new Handler.Callback() {

        @SuppressLint("NewApi")
        @Override
        public boolean handleMessage(Message msg) {
            if (mDialogFragment != null) {
                mDialogFragment.dismiss();
            }
            switch (msg.what) {
                case 0:
                    if (comflag == 1) { // 表示从本页面跳转到本页面时加载失败
                        c = c + 1;
                        if (c <= 2) {
//                            getData(Integer.parseInt(lists.get(flag).getId()));
                            Toast.makeText(DatiChuangguanActivity.this, "考题加载失败！", Toast.LENGTH_SHORT).show();
                        } else {
                            islasttime = true;
                            addScore(false);
                            Toast.makeText(DatiChuangguanActivity.this, "考题加载失败已超过3次！", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(DatiChuangguanActivity.this, "考题加载失败！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                   // question_page.setText("1/" + qList.size());
                    // //数据加载完后计时开始
                    break;
                case 2:
                    break;
                case 3:
                    Toast.makeText(DatiChuangguanActivity.this, "此考场暂无考卷或者您已经答到最后一关", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(DatiChuangguanActivity.this, "此考卷暂无考题", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    isOver = true;
                    Toast.makeText(DatiChuangguanActivity.this, "本次考试时间到，已自动交卷", Toast.LENGTH_SHORT).show();
                    overFlag = false;
                    TimuPanduan();
                    computeScore();
                    showScore();
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    /**
     * 闯关所有试卷对像列表
     */
    private ArrayList<ExaminationPapers.DataBean> lists = new ArrayList<>();
    //private String type;
    /**
     * 当前试卷索引
     */
    public int flag = 0;
    /**
     * 0代表从考场详情进入，1代表本页跳转
     */
    private int comflag;
    private int isflag;
    /**
     * 交卷状态
     */
    private boolean commitFlag = false;

    // 自定义参数
    private List<ExaminationPapers.Question> qList = new ArrayList<>();


    private DatiViewflowAdapter adapter;
    private PopupWindow pop;
    private int a = 0; // 判断提交试卷的次数，超过3次不成功则回到详情界面
    private int b = 0; // 判断监听HOME键为0时可以点击，为1时不能
    private int c = 0; // 判断获取试题是否成功，超过3次不成功则回到详情界面
    private Boolean islasttime = false; // 判断从上一关跳转到本关，是否获取到数据了，默认为false;
    private Boolean isaddflag = false; // 判断多次提交
    private Boolean isOver = false;

    private Boolean needCheckError = false; //
    private ProgressDialog progressDialog;

    private void setOverListener() {
        long starttime = 0;
        long endtime = 0;
        boolean iskk = false;
        try {
//            Date startDate = null;
//            Date endDate = null;
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
//            String begintimeStr = String.valueOf(examination.getBegintime());
//            String endtimeStr = String.valueOf(examination.getEndtime());
//            if (EmptyUtil.IsNotEmpty(begintimeStr)) {
//                startDate = sdf.parse(begintimeStr);
//            }
//            if (EmptyUtil.IsNotEmpty(endtimeStr)) {
//                endDate = sdf.parse(endtimeStr);
//            }
//            long nowTime = System.currentTimeMillis() / 1000
//                    - CacheUtil.timeOffset;
//            starttime = startDate.getTime() / 1000;
//            endtime = endDate.getTime() / 1000;

            long nowTime = System.currentTimeMillis() / 1000 - CacheUtil.timeOffset;

            starttime = (long) examination.getBegintime();
            endtime = (long) examination.getEndtime();

            long offsetTime = (endtime - nowTime) * 1000;
            handler.removeMessages(6);
            Message msg = Message.obtain();
            msg.what = 6;
            // Toast.makeText(DatiChuangguanActivity.this, offsetTime+"毫秒后考试过期",
            // Toast.LENGTH_SHORT).show();
            handler.sendMessageAtTime(msg, SystemClock.uptimeMillis() + offsetTime);
        } catch (Exception e) { //ParseException
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaoshicontent);

        // type = getIntent().getStringExtra("type");
        examination = getIntent().getParcelableExtra("eDataBean");  //(ExamList.DataBean)
        epDataBean = getIntent().getParcelableExtra("epDataBean");  //(ExaminationPapers.DataBean)
        comflag = getIntent().getIntExtra("comflag", 0);

        lists = getIntent().getParcelableArrayListExtra("list");  //(ArrayList<ExaminationPapers.DataBean>)

        // 判断闯关模式
        if ("3".equals(examination.getExamtype())) {
            if (comflag == 0) {
                // isflag=DatiChuangguan.DatiQuery(Integer.parseInt(examination.getExaminationid()))+1;
                String level = DatiChuangguanActivity.mLevel.get(String.valueOf(examination.getId()));
                if (level != null && level.length() > 0) {
                    int levelInt = Integer.valueOf(level);
                    isflag = levelInt + 1;
                } else {
                    isflag = 0;
                }
                int flagFromServer = 0;
                if (examination.getScore() != null) {
                    flagFromServer = Integer.valueOf(examination.getScore());
                }
                if (isflag < flagFromServer && flagFromServer != lists.size()) {
                    isflag = flagFromServer;
                }
                flag = getIntent().getIntExtra("flag", 0);
                flag = isflag;

            } else if (comflag == 1) {
                flag = getIntent().getIntExtra("flag", 0);
            }
            if (EmptyUtil.IsNotEmpty(examination.getConsumetime())) {
                // useTime = Integer.valueOf(examination.getConsumetime());
            }
        } else if ("4".equals(examination.getExamtype())) {
            if (comflag == 0) {
                flag = getIntent().getIntExtra("flag", 0);
            } else if (comflag == 1) {
                useTime = getIntent().getIntExtra("useTime", 0);
                // count = getIntent().getIntExtra("count", 0);
                // rightNum = getIntent().getIntExtra("rightNum", 0);
                // errorNum = getIntent().getIntExtra("errorNum", 0);
                flag = getIntent().getIntExtra("flag", 0);
            }
        }

        if (lists.size() > 0) {
            if (flag >= lists.size()) {
                if ("3".equals(examination.getExamtype())) {
                    isWaitingChoose = true;
                    KaoChangType02Activity.mAccumulateUseTime.put(String.valueOf(examination.getId()), "0");
                    AlertDialog alert = new AlertDialog.Builder(this)
                            .setPositiveButton("重闯", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface arg0, int arg1) {
                                    DatiChuangguanActivity.this.initUI();
                                    DatiChuangguanActivity.this.initData();
                                    DatiChuangguanActivity.this.flag = 0;
                                    examinationPapers = lists.get(flag);
//                            examinationPapers = epDataBean;
                                    mLevel.remove(String.valueOf(examination.getId()));
                                    isWaitingChoose = false;
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    DatiChuangguanActivity.this.finish();
                                }
                            })
                            .setMessage("恭喜您已经全部通关！需要重新再闯一次？")
                            .create();
                    alert.setCanceledOnTouchOutside(false);
                    alert.setCancelable(false);
                    alert.show();
                } else {
                    if ("3".equals(examination.getExamtype())) {
                        KaoChangType02Activity.mAccumulateUseTime.put(String.valueOf(examination.getId()), "0");
                    }
                    examinationPapers = lists.get(flag);
//            examinationPapers = epDataBean;
                }
            } else {
                isWaitingChoose = false;
                examinationPapers = lists.get(flag);
                DatiChuangguanActivity.this.initUI();
                DatiChuangguanActivity.this.initData();
            }
        }

        // flag=getIntent().getIntExtra("flag", isflag);
        // flag=getIntent().getIntExtra("flag", 0);
        // flag=isflag;


        // 注册广播
        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        setOverListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setOverListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeMessages(6);
    }
   private void jiaojuan(){
       count = 0;
       rightNum = 0;
       errorNum = 0;
       //结束时间
       end_time = System.currentTimeMillis() / 1000;
       TimuPanduan();
       mDialogFinish = new AlertDialog.Builder(DatiChuangguanActivity.this).create();
       mDialogFinish.show();
       Window window = mDialogFinish.getWindow();
       // *** 主要就是在这里实现这种效果的.
       // 设置窗口的内容页面,exit_app_tip.xml文件中定义view内容
       window.setContentView(R.layout.kaoshi_over_tip);
       // 为确认按钮添加事件,执行退出应用操作
       TextView ok = (TextView) window.findViewById(R.id.ks_yes);
       TextView ks_content = (TextView) window.findViewById(R.id.ks_content);
       ok.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
               if (mDialogFinish != null) {
                   mDialogFinish.cancel();
               }
               if (qList.size() > 0) {
                   progressDialog = new ProgressDialog(DatiChuangguanActivity.this);
                   progressDialog.setCancelable(false);
                   progressDialog.setMessage("正在提交，请稍后");
                   progressDialog.show();
                   TimuPanduan();
                   computeScore();
                   showScore();
               } else {
                   Toast.makeText(DatiChuangguanActivity.this,
                           "没有试题的考卷不需要交卷！", Toast.LENGTH_SHORT).show();
               }
           }
       });
       TextView ks_no = (TextView) window.findViewById(R.id.ks_no);
       ks_no.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
               if (mDialogFinish != null) {
                   mDialogFinish.cancel();
               }
               for (int i = 0; i < qList.size(); i++) {
                   if (qList.get(i).getNoAnswerInext() == 0) {
                       adapter = new DatiViewflowAdapter(DatiChuangguanActivity.this, qList);
                       kaoshi_viewflow.setAdapter(adapter, i);
                       break;
                   }
               }

           }
       });
       // 关闭alert对话框
       ImageView cancel = (ImageView) window.findViewById(R.id.ks_close);
       cancel.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
               if (mDialogFinish != null) {
                   mDialogFinish.cancel();
               }
           }
       });


       if (noAnserList.size() > 0) {
           ks_content.setText("还有" + noAnserList.size() + "题未做，确认交卷？");
       } else {
           ks_content.setText("否检查一遍？");
           ks_no.setText("检查一遍");
       }
   }
    @SuppressLint("NewApi")
    private void initUI() {
        question_over = (TextView) findViewById(R.id.question_over);
        question_over.setOnClickListener(new OnClickListener() {  //交卷

            @Override
            public void onClick(View arg0) {
                // if(a<=2){
                if(qList.size()>0&&kaoshi_viewflow.getSelectedItemPosition()>=qList.size()-1){
                jiaojuan();
            }
                else {
                    kaoshi_viewflow.setSelection(kaoshi_viewflow.getSelectedItemPosition()+1);
                }
                // }else {
                // Toast.makeText(DatiChuangguanActivity.this, "对不起！交卷次数超过3次！",
                // Toast.LENGTH_SHORT).show();
                // DatiChuangguanActivity.this.finish();
                // }
            }
        });

        question_page = (TextView) findViewById(R.id.question_page);
        // question_page.setText("1/"+qList.size());
        question_page.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kaoshi_viewflow.getSelectedItemPosition()<=0&&qList.size()>0){
                    Toasty.info(DatiChuangguanActivity.this, "已经是第一页了", Toast.LENGTH_SHORT, true).show();
                } else{
                    kaoshi_viewflow.setSelection(kaoshi_viewflow.getSelectedItemPosition()-1);
                }
            }
        });

        kaoshi_viewflow = (ViewFlow) findViewById(R.id.kaoshi_viewflow);
        adapter = new DatiViewflowAdapter(this, qList);
        kaoshi_viewflow.setAdapter(adapter, 0);
        // myAdapter=new DatichuanggAdapter(this, list,kaoshi_viewflow, lists,
        // flag,examination);
        // kaoshi_viewflow.setAdapter(myAdapter, 0);
        // viewflow滑动监听
        kaoshi_viewflow.setOnViewSwitchListener(new ViewSwitchListener() {

            @Override
            public void onSwitched(View view, int position) {
                // TODO Auto-generated method stub
                // selectIndext=position;
               // question_page.setText(position + 1 + "/" + qList.size());
                if(position>=qList.size()-1){
                    question_over.setText("交卷");
                } else {
                    question_over.setText("下一页");
                }

                ExaminationPapers.Question dc = qList.get(position);
                List<ExaminationPapers.Option> items = dc.getOptions();
                String str = "";
                int size = items.size();
                for (int i = 0; i < size; i++) {
                    ExaminationPapers.Option item = items.get(i);
                    if (item.getIsanswer() == 1) {
                        if (("single".equals(dc.getQtype()))    //1 之前
                                || ("multi".equals(dc.getQtype()))) {  //2 之前
                            str += getWordFromPosition(i) + ",";
                        } else {
                            str += item.getOption() + ",";
                        }
                    }
                }
                if (str.length() > 0) {
                    str = str.substring(0, str.length() - 1);
                }

                // Log.e("tag", position+"+"+str);

                // if(position==0){
                //
                // }else{
                // if(panduan(position-1)){
                // qList.get(position-1).setRightflag(true);
                // }else{
                // qList.get(position-1).setRightflag(false);
                // }
                //
                // if(qList.get(position-1).getXxid().isEmpty()){
                // qList.get(position-1).setNoAnswerInext(1);
                // }else{
                // qList.get(position-1).setNoAnswerInext(0);
                // }
                // }

                // if(position==lists.size()-1){
                //
                // flag= flag+1;
                // getData(Integer.parseInt(lists.get(flag).getPaperid()));
                // }
            }
        });
        question_time = (TextView) findViewById(R.id.question_time);

        Log.e("持续时间", Integer.parseInt(epDataBean.getDuration()) + "");
        timeCount = new TimeCount(Integer.parseInt(epDataBean.getDuration()) * 60 * 1000, 1000);

        // time.start();
        tv_title = (TextView) findViewById(R.id.title_kaoshicontent);
        // 加粗中文字体
        // tv_title.getPaint().setFakeBoldText(true);
        iv_back = (ImageView) findViewById(R.id.iv_back_kaoshicontent);
        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        iv_rank = (ImageView) findViewById(R.id.iv_rank_kaoshicontent);
        iv_rank.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent it = new Intent(DatiChuangguanActivity.this, kaoshiRankActivity.class);
                it.putExtra("examination", examination);
                startActivity(it);
                // selectIndext+=1;
                // if(selectIndext<list.size()){
                // kaoshi_viewflow.snapToScreen(selectIndext);
                // }
            }
        });
        if (examinationPapers != null) {
            tv_title.setText(examinationPapers.getPapername());
        }
    }

    private void initData() {
        // TODO Auto-generated method stub
        // 显示进度框
        /**
         * Load失效修改为Progress
         *
         * 监听失效
         */

        //System.out.println(lists.size()+" ---size"+ "----"+examination.getExamtype());

        if (lists.size() > 0) {  //解析数据
            qList.clear();
            qList.addAll(lists.get(flag).getQuestions());
            //qList = lists.get(flag).getQuestions();
            System.out.println(qList.size() + "  question----size");
            Message msg = new Message();
            msg.what = 1;
            if (handler != null) {
                handler.sendMessage(msg);
            }
            if (timeCount != null) {
                timeCount.start();  //计时开始
                start_time = System.currentTimeMillis() / 1000; //试卷开始时间
            }
        }
        // mDialogFragment = AbDialogUtil.showProgressDialog(this, R.mipmap.ic_load,"考题加载中，请稍等！");
    }

    @Override
    public void onBackPressed() {
        if (qList.size() > 0) {
            if (commitFlag) {
                DatiChuangguanActivity.this.finish();
            } else {
                new AlertDialog.Builder(DatiChuangguanActivity.this)
                        .setTitle("提醒")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("现在正在考试是否退出，退出则自动交卷！")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub
                                TimuPanduan();
                                computeScore();
                                showScore();
                            }
                        }).setNegativeButton("否", null).show();
            }
        } else {
            DatiChuangguanActivity.this.finish();
        }
    }

    /**
     * 例计时
     *
     * @author shelia
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            Toast.makeText(DatiChuangguanActivity.this, "考试已结束！", Toast.LENGTH_SHORT).show();
            question_time.setText("考试已结束");
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            // 倒计时
            question_time.setText(TimeUtil.secToTime(Integer.parseInt(millisUntilFinished / 1000 + "")));
            // 判断是否超时
            if (overFlag) {
                // 考试用时加1
                useTime++;
                // 计时
                if ((examinationPapers != null) && (examinationPapers != null)) {
                    // 闯关1才有限时:超时自动交卷
                    if ("3".equals(examination.getExamtype())) {
                        if (EmptyUtil.IsNotEmpty(examinationPapers.getDuration())) {
                            int duration = Integer.parseInt(examinationPapers.getDuration());
                            question_time.setText(TimeUtil
                                    .secToTime((duration * 60 - useTime)));
                            if (useTime >= duration * 60) {
                                Toast.makeText(DatiChuangguanActivity.this,
                                        "考试时间到，交卷中...", Toast.LENGTH_SHORT)
                                        .show();
                                overFlag = false;
                                TimuPanduan();
                                computeScore();
                                showScore();
                            }
                        }
                    } else if ("4".equals(examination.getExamtype())) {
                        question_time.setText(TimeUtil.secToTime(useTime));
                        if (examination.getDuration() != 0) {
                            // 考试时长：分
                            int duration = examination.getDuration();
                            if (useTime >= duration * 60) {
                                Toast.makeText(DatiChuangguanActivity.this,
                                        "考试时间到，交卷中...", Toast.LENGTH_SHORT)
                                        .show();
                                overFlag = false;
                                TimuPanduan();
                                computeScore();
                                showScore();
                            }
                        }
                    }
                }
            }
            // System.out.println("考试用时："+TimeUtil.secToTime(useTime));
            // question_time.setText(millisUntilFinished /1000+"秒");
        }
    }

    /**
     * 显示考试成绩
     */
    private void showScore() {

        // mDataView1 = mInflater.inflate(R.layout.kaoshi_sorce, null);

        if (mDataView1 == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            mDataView1 = mLayoutInflater.inflate(R.layout.kaoshi_sorce, null);
            pop = new PopupWindow(mDataView1,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        //
        // ColorDrawable cd = new ColorDrawable(0x000000);
        // pop.setBackgroundDrawable(cd);
        //
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);

        // 设置点击窗口外边窗口不消失
        pop.setOutsideTouchable(false);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);

        TextView pop_countmsg = (TextView) mDataView1
                .findViewById(R.id.pop_countmsg);
        TextView pop_time = (TextView) mDataView1.findViewById(R.id.pop_time);
        TextView pop_error = (TextView) mDataView1.findViewById(R.id.pop_error);
        TextView pop_right = (TextView) mDataView1.findViewById(R.id.pop_right);
        TextView pop_count = (TextView) mDataView1.findViewById(R.id.pop_count);

        LinearLayout pop_ll = (LinearLayout) mDataView1
                .findViewById(R.id.pop_ll);

        Button check_error = (Button) mDataView1.findViewById(R.id.check_error);
        check_error.setText("查看错题");


        if (errQuestionList.size() > 0
                && "1".equals(examination.getShowerrors())) {
            check_error.setVisibility(View.VISIBLE);
            check_error.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    // 交卷成功后再显示错题
                    needCheckError = true;
                    addScore(false);
                    pop.dismiss();
                    onDissmes();
                    Toast.makeText(DatiChuangguanActivity.this, "正在提交成绩...",
                            Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            check_error.setVisibility(View.GONE);
        }


        pop_right.setText("答对" + rightNum + "道题");
        pop_error.setText("答错" + errorNum + "道题");
        pop_time.setText("总共考试用时:" + TimeUtil.secToTime02(useTime));
        if ("3".equals(examination.getExamtype())) {
            String time = KaoChangType02Activity.mAccumulateUseTime
                    .get(String.valueOf(examination.getId()));
            if (time != null && time.length() > 0) {
                int resultTime = useTime + Integer.valueOf(time);
                KaoChangType02Activity.mAccumulateUseTime.put(
                        String.valueOf(examination.getId()), resultTime + "");
            }
            time = KaoChangType02Activity.mAccumulateUseTime.get(String.valueOf(examination.getId()));
            if (time != null && time.length() > 0) {
                pop_time.setText("你本次答题时间为:" + TimeUtil.secToTime02(useTime)
                        + ",累计答题耗时"
                        + TimeUtil.secToTime02(Integer.valueOf(time)));
            } else {
                pop_time.setText("你本次答题时间为:" + TimeUtil.secToTime02(useTime)
                        + ",累计答题耗时" + TimeUtil.secToTime02(useTime));
            }
        }
        Button pop_ok = (Button) mDataView1.findViewById(R.id.pop_ok);
        Button pop_next = (Button) mDataView1.findViewById(R.id.pop_next);
        Button pop_again = (Button) mDataView1.findViewById(R.id.pop_again);

        boolean isLimitTime = true;
        if ("3".equals(examination.getExamtype())) {
            isLimitTime = (examinationPapers.getDuration() == null) ? false
                    : true;
        } else {
            isLimitTime = (examination.getDuration() == 0) ? false : true;  //examination.getDuration() == null
        }

        int duration = 0;
        if (isLimitTime) {
            if ("3".equals(examination.getExamtype())) {
                duration = Integer.parseInt(examinationPapers.getDuration());
            } else {
                duration = examination.getDuration(); // Integer.parseInt(examination.getDuration());
            }
        }
        float i = flag * 100 + count;

        String resultCount = "您本次考试的得分为：" + TextUtil.getDefaultDecimalFormat().format(i);

        if ("3".equals(examination.getExamtype())/*
                                             * ||
											 * "4".equals(examination.getType())
											 */) {
            resultCount = "您这次的考试成绩为：第" + (flag) + "关";
        }

        if (islasttime) {
            pop_countmsg.setText("很遗憾，您败给了网络！");
            pop_ok.setVisibility(View.VISIBLE);
            pop_ok.setTag("0");
            pop_ll.setVisibility(View.GONE);
        } else {
            if (errorNum > 0 || noAnserList.size() > 0) {
                // if("3".equals(examination.getType())){
                // pop_count.setText("您本次考试的得分为："+count);
                // }else if("4".equals(examination.getType())){
                // pop_count.setText("您本次考试的得分为："+(flag*100+count));
                // }
                if (isOver) {
                    pop_countmsg.setText("本次考试时间到！");
                    pop_ok.setVisibility(View.VISIBLE);
                    pop_ok.setTag("0");
                    pop_ll.setVisibility(View.GONE);
                } else if (useTime >= duration * 60 && isLimitTime) {
                    pop_countmsg.setText("时间到，您闯关未成功！请继续加油！");
                    pop_ok.setVisibility(View.VISIBLE);
                    pop_ok.setTag("0");
                    pop_ll.setVisibility(View.GONE);
                } else {
                    pop_countmsg.setText("很遗憾，您闯关未成功！请继续加油！");
                    pop_ok.setVisibility(View.VISIBLE);
                    pop_ok.setTag("0");
                    pop_ll.setVisibility(View.GONE);
                }
            } else {
                if ("3".equals(examination.getExamtype())) {
                    // pop_count.setText("您本次考试的得分为："+count);
                    // DatiChuangguan.add(examination.getExaminationid(),
                    // lists.get(flag).getPaperid(), flag+"");
                    DatiChuangguanActivity.mLevel.put(
                            String.valueOf(examination.getId()), flag + "");
                }
                // }else if("4".equals(examination.getType())){
                // pop_count.setText("您本次考试的得分为："+(flag*100+count));
                // }
                if (flag + 1 >= lists.size()) {
                    if (isOver) {
                        pop_countmsg.setText("本次考试时间到,您已经通过最后一关！");
                        pop_ok.setVisibility(View.VISIBLE);
                        pop_ok.setTag("0");
                        pop_ll.setVisibility(View.GONE);
                    } else {
                        if (useTime >= duration * 60 && isLimitTime) {
                            pop_countmsg.setText("时间到,恭喜您闯关成功！您已经通过最后一关！");
                        } else {
                            pop_countmsg.setText("恭喜您闯关成功！您已经通过最后一关！");
                        }
                        pop_ll.setVisibility(View.GONE);
                        pop_ok.setVisibility(View.VISIBLE);
                        pop_ok.setTag("1");
                    }
                } else {
                    if (isOver) {
                        pop_countmsg.setText("本次考试时间到,恭喜您闯关成功！");
                        pop_ok.setVisibility(View.VISIBLE);
                        pop_ok.setTag("0");
                        pop_ll.setVisibility(View.GONE);
                    } else {
                        if (useTime >= duration * 60 && isLimitTime) {
                            pop_countmsg.setText("时间到,恭喜您闯关成功！请选择是否继续闯关？");
                        } else {
                            pop_countmsg.setText("恭喜您闯关成功！请选择是否继续闯关？");
                        }
                        pop_ok.setVisibility(View.GONE);
                        pop_next.setText("继续闯关");
                        pop_again.setText("稍后再来");
                        pop_ll.setVisibility(View.VISIBLE);
                    }
                }
                if ("3".equals(examination.getExamtype())/*
                                                     * ||
													 * "4".equals(examination.
													 * getType())
													 */) {
                    resultCount = "您这次的考试成绩为：第" + (flag + 1) + "关";
                }
            }

        }
        pop_count.setText(resultCount);
        pop_again.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // AbDialogUtil.removeDialog(mDataView1);
                addScore(true);
                pop.dismiss();
                onDissmes();
                Toast.makeText(DatiChuangguanActivity.this, "正在提交成绩...",
                        Toast.LENGTH_SHORT).show();
            }
        });

        pop_next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // AbDialogUtil.removeDialog(mDataView1);
                pop.dismiss();
                onDissmes();
                if (lists.size() > flag + 1) {
                    // 清除上一次的选项
                    for (int i = 0; i < qList.size(); i++) {
                        qList.get(i).getOptions().clear();
                    }
                    Toast.makeText(DatiChuangguanActivity.this, "正在进入下一关",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DatiChuangguanActivity.this,
                            DatiChuangguanActivity.class);
                    intent.putExtra("list", lists);
                    intent.putExtra("flag", flag += 1);
                    // examination.setConsumetime(useTime+"");
                    intent.putExtra("examination", examination);
                    intent.putExtra("comflag", 1);

                    if ("4".equals(examination.getExamtype())) {
                        intent.putExtra("useTime", useTime);
                        // intent.putExtra("count", count);
                        // intent.putExtra("rightNum", rightNum);
                        // intent.putExtra("errorNum", errorNum);
                    }
                    startActivity(intent);
                    DatiChuangguanActivity.this.finish();
                } else {
                    if (timeCount != null) {
                        timeCount.cancel();
                    }
                    addScore(true);
                    Toast.makeText(DatiChuangguanActivity.this,
                            "恭喜您已经全部通关！好好休息一下吧", Toast.LENGTH_SHORT).show();

                }

            }
        });

        pop_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // AbDialogUtil.removeDialog(mDataView1);
                needCheckError = false;
                int tag = 0;
                try {
                    tag = Integer.valueOf(arg0.getTag().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (flag + 1 >= lists.size() && tag == 1) {
                    addScore(true);
                } else {
                    addScore(false);
                }
                pop.dismiss();
                onDissmes();
                Toast.makeText(DatiChuangguanActivity.this, "正在提交成绩...", Toast.LENGTH_SHORT).show();
                // if(myflag==1){
                // if(lists.size()>flag){
                // Toast.makeText(DatiChuangguanActivity.this, "正在进入下一关",
                // Toast.LENGTH_SHORT).show();
                // Intent intent=new
                // Intent(DatiChuangguanActivity.this,DatiChuangguanActivity.class);
                // intent.putExtra("list", lists);
                // intent.putExtra("flag", flag+=1);
                // intent.putExtra("examination", examination);
                // startActivity(intent);
                // DatiChuangguanActivity.this.finish();
                // }else{
                // if(time!=null){
                // time.cancel();
                // }
                // Toast.makeText(DatiChuangguanActivity.this, "恭喜你闯到了最后一关",
                // Toast.LENGTH_SHORT).show();
                // DatiChuangguanActivity.this.finish();
                // }
                // }else{
                // DatiChuangguanActivity.this.finish();
                // }
            }
        });

        // pop.update();
        // pop.setOnDismissListener(new OnDismissListener(){
        //
        // //在dismiss中恢复透明度
        // public void onDismiss(){
        // WindowManager.LayoutParams lp=getWindow().getAttributes();
        // lp.alpha = 1f;
        // getWindow().setAttributes(lp);
        // }
        // });

        pop.showAtLocation(this.findViewById(R.id.kaoshicontent_rl),
                Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

        // pop.showAtLocation(this.findViewById(R.id.kaoshicontent_rl),
        // Gravity.CENTER, 0, 0);
        // AbDialogUtil.showDialog(mDataView1,Gravity.CENTER);
        // AbDialogUtil.showAlertDialog(mDataView1);
        // AbDialogUtil.showFullScreenDialog(mDataView1);

    }

    /**
     * 提交考试成绩
     *
     * @param
     */

    private abstract class ExamSubmitCallback extends Callback<ExamSubmited> {

        @Override
        public ExamSubmited parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            System.out.println(result + " -----response  + code" + id);
            ExamSubmited examSubmited = new Gson().fromJson(result, ExamSubmited.class);
            return examSubmited;
        }
    }

    private void getAnswerString() {  //把答案转化成 Json Strings
        //生成答案 json 字符串
        if (EmptyUtil.IsNotEmpty(answers)) {
            answers = "";
        }

        int listSize = qList.size();
        String startString = "{";
        String endString = "}";

        for (int i = 0; i < listSize; i++) {
            ExaminationPapers.Question q = qList.get(i);
            String aItemName = "\"" + q.getId() + "\"" + ":";
            String aItemValue = null;
            int selectSize = q.getSelection().size();
            if (selectSize > 0) {  //有选择答案的时候
                switch (q.getQtype()) {
                    case "single":
                        aItemValue = "[" + q.getSelection().get(0) + "]";
                        break;
                    case "multi":  //多选题
                        aItemValue = "[";
                        int n = 0;
                        for (String answer : q.getSelection()) {
                            if (n == 0) {
                                aItemValue = aItemValue + answer;
                            } else {
                                aItemValue = aItemValue + "," + answer;
                            }
                            n++;
                        }
                        aItemValue = aItemValue + "]";
                        break;
                    case "judge":
                        aItemValue = "[" + q.getSelection().get(0) + "]";
                        break;
                    default:
                        break;
                }
            } else { //没有选择答案
                aItemValue = "[]";
            }
            String aItem = aItemName + aItemValue;
            if (i == 0) {
                answers = startString + aItem;
            } else {
                answers = answers + "," + aItem;
            }
        }
        answers = answers + endString;  //for 循环结束后 加上一个 结束 }
    }

    private void addScore(boolean needPlus) {
        if (mDialogFinish != null) {
            mDialogFinish.cancel();
        }
        if (!isaddflag) {
            isaddflag = true;
            String pURL = URL_PR + PREPARE_EXAM_URL + "record";
            getAnswerString(); //获取答案
            Log.e("Exampaper_answers", answers);
            System.out.println("----------------answers=" + answers);
            OkHttpUtils
                    .post()
                    .url(pURL)
                    .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                    .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                    .addParams("starttime", String.valueOf(start_time))
                    .addParams("endtime", String.valueOf(end_time))
                    .addParams("examination_id", String.valueOf(examination.getId()))
                    .addParams("exampaper_id", examinationPapers.getId())
                    .addParams("answers", answers)  //{"20":[3,4]}
                    .build()
                    .execute(new ExamSubmitCallback() {

                                 @Override
                                 public void onError(Call call, Exception e, int id) {
                                     e.printStackTrace();
                                     if(progressDialog!=null)progressDialog.dismiss();
                                     Log.e("Exampaper_onError", e.toString());
                                     Toast.makeText(DatiChuangguanActivity.this, "网络异常—提交成绩失败", Toast.LENGTH_LONG).show();
                                     a = a + 1;
                                     if (a <= 2) {
                                         showScore();
                                         Toast.makeText(DatiChuangguanActivity.this, "交卷失败，请重新交卷！", Toast.LENGTH_SHORT).show();
                                     } else {
                                         Toast.makeText(DatiChuangguanActivity.this, "交卷次数已超过3次，请重新考试！", Toast.LENGTH_SHORT).show();
                                         DatiChuangguanActivity.this.finish();
                                     }
                                     // a=a+1;
                                     // Toast.makeText(DatiChuangguanActivity.this,
                                     // "交卷失败，请重新交卷！",
                                     // Toast.LENGTH_SHORT).show();
                                     isaddflag = false;
                                 }

                                 @Override
                                 public void onResponse(ExamSubmited response, int id) {
                                     Log.e("ExamSubmitedCallback", response.getError_msg());
                                     if(progressDialog!=null)progressDialog.dismiss();
                                     switch (response.getError_code()) {
                                         case 0:
                                             Toast.makeText(DatiChuangguanActivity.this, "成绩提交成功", Toast.LENGTH_LONG).show();
                                             // 1、显示考试完成情况UI
                                             // showScore();
                                             // //2、隐藏结束UI，防止重复提交服务器数据
                                             // question_over.setVisibility(NotifyListView.INVISIBLE);
                                             // DatiChuangguan.add(examination.getExaminationid(),
                                             // lists.get(flag).getPaperid(),
                                             // flag+"");
                                             // DatiChuangguanActivity.this.finish();
                                             if (needCheckError == true) {
                                                 Intent intent = new Intent(DatiChuangguanActivity.this, KaoshiCheckErrorActivity.class);
                                                 intent.putExtra("examination", DatiChuangguanActivity.this.examination);
                                                 intent.putExtra("errQuestionList", errQuestionList);
                                                 startActivity(intent);
                                                 DatiChuangguanActivity.this.finish();
                                             } else {
                                                 DatiChuangguanActivity.this.finish();
                                             }

                                             break;
                                         case 401: //未登录
                                             Toast.makeText(DatiChuangguanActivity.this, CacheUtil.logout, Toast.LENGTH_SHORT).show();
                                             Intent intent = new Intent(DatiChuangguanActivity.this, LoginActivity.class);
                                             startActivity(intent);
                                             onFinish();//重置状态
                                             break;
                                         default:
                                             break;
                                     }
                                 }

                                 public void onFinish() {
                                     //结束方法"
                                     // 3、设置交卷状态： true
                                     commitFlag = true;
                                     isaddflag = false;
                                 }
                             }
                    );

        } else {
            Toast.makeText(DatiChuangguanActivity.this, "正在提交试卷...",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private String getWordFromPosition(int position) {
        switch (position) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "G";
            case 7:
                return "H";
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        unregisterReceiver(mHomeKeyEventReceiver);
        // 闭关计时器
        if (timeCount != null) {
            timeCount.cancel();
        }
    }

    /**
     * 结束考试处理方法
     *
     * @param //默认0答题有错时结束，1答题全对闯关成功进入下一关
     */
    public synchronized void computeScore() {  //计算得分情况
        if (timeCount != null) {
            timeCount.cancel();
        }


        System.out.println("-------------computeScore");

        // 2、计算考生得分情况及正确，错误题数
        // for (int i = 0; i < list.size(); i++) {
        // Question q=list.get(i);
        count = 0;
        rightNum = 0;
        errorNum = 0;
        for (int i = 0; i < qList.size(); i++) {
            ExaminationPapers.Question q = qList.get(i);
            if (q.isRightflag()) {
                rightNum++;
                String qType = q.getQtype();

                switch (qType) {
                    case "single":
                        // 单选题

                        //System.out.println(examinationPapers.getSingle_score()+"-------------score single");

                        if (EmptyUtil.IsNotEmpty(examinationPapers.getSingle_score())) {
                            count += Float.parseFloat(examinationPapers.getSingle_score());
                        }
                        // count+=Integer.parseInt("2");
                        break;
                    case "multi":
                        // 多选题
                        if (EmptyUtil.IsNotEmpty(examinationPapers.getMulti_score())) {
                            count += Float.parseFloat(examinationPapers.getMulti_score());
                        }
                        // count+=Integer.parseInt("2");
                        break;
                    case "judge":
                        // 判断题
                        if (EmptyUtil.IsNotEmpty(examinationPapers.getJudge_score())) {
                            count += Float.parseFloat(examinationPapers.getJudge_score());
                        }
                        // count+=Integer.parseInt("2");
                        break;
                }
            } else {
                errorNum++;
            }
        }
        // addScore();
        // showScore();
    }

    /**
     * 判断题目是否正确
     *
     * @param position
     * @return
     */
    private boolean panduan(final int position) {

        boolean flag = true;
        // 相等个数
        int countge = 0;

        ExaminationPapers.Question question = qList.get(position);
        List<ExaminationPapers.Option> option = question.getOptions();
        List<String> selections = question.getSelection();

        if (option.size() <= 0) {
            flag = false;
        } else {

            for (int i = 0; i < option.size(); i++) {
                if (option.get(i).getIsanswer() == 1) {

                    // System.out.println(option.get(i).getOption()+"------"+);

                    if (!selections.contains(option.get(i).getId())) { //已选中选项是否有这个正确选项
                        flag = false;
                    }
                    countge++;
                }
            }

        }
        if (selections.size() != countge) {  //答对数量不对也是错的 主要针对 多选题
            flag = false;
        }

        return flag;
    }

    // 判断题目是否答对和未答题目已答题目的个数
    private void TimuPanduan() {
        errQuestionList.clear();
        for (int i = 0; i < qList.size(); i++) {
            if (panduan(i)) {
                qList.get(i).setRightflag(true);
            } else {
                qList.get(i).setRightflag(false);
                errQuestionList.add(qList.get(i));
            }

            if (qList.get(i).getOptions().isEmpty()) {
                qList.get(i).setNoAnswerInext(0);
            } else {
                qList.get(i).setNoAnswerInext(1);
            }
        }
        // 清空未答数据
        noAnserList.clear();

        // 获取未答题数
        for (int i = 0; i < qList.size(); i++) {
            ExaminationPapers.Question q = qList.get(i);
            if (q.getSelection().size() == 0) {  //没有做的题，做了都会选
                // //当确认按钮显示表示：用户对当前题目未答过
                // q.setNoAnswerInext(i);
                noAnserList.add(q);
            }
        }

    }

    /**
     * 监听是否点击了home键将客户端推到后台
     */
    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    // 表示按了home键,程序到了后台
                    if (b == 0) {
                        b = 1;
                        if (!isWaitingChoose) {
                            TimuPanduan();
                            computeScore();
                            addScore(false);
                            Toast.makeText(DatiChuangguanActivity.this, "自动交卷中...",
                                    Toast.LENGTH_SHORT).show();
                        }
                        DatiChuangguanActivity.this.finish();
                    }

                } else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
                    // 表示长按home键,显示最近使用的程序列表
                }
            }
        }
    };

    // 恢复透明
    private void onDissmes() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }
}

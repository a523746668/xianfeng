package com.qingyii.hxt;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.view.dialog.AbAlertDialogFragment;
import com.google.gson.Gson;
import com.kang.zbar.CameraTestActivity;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.ExamDetails;
import com.qingyii.hxt.pojo.ExamList;
import com.qingyii.hxt.pojo.ExaminationPapers;
import com.qingyii.hxt.util.DateUtils;
import com.qingyii.hxt.util.EmptyUtil;
import com.qingyii.hxt.util.RandomUtil;
import com.zhf.Util.Global;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

import static com.qingyii.hxt.http.XrjHttpClient.PREPARE_EXAM_URL;
import static com.qingyii.hxt.http.XrjHttpClient.URL_PR;

/**
 * 考场详情界面
 */
public class  KaoChangInfoActivity extends AbBaseActivity implements OnClickListener {

    private TextView kaoshi_name, kaoshi_desc;
    private TextView kaoshi_zhubandanwei, kaoshi_time, kaoshi_leixing,
            kaoshi_guangshu, dancixianshi_tv, kaoshi_danci, kaoshi_dati, kaoshi_yicanjia,
            kaoshi_gongyougs, kaoshi_xuefen, kaoshi_renshu, kaoshi_total_number, kaoshi_now_number;
    private LinearLayout kaoshi_total_ll;
    private Button kaoshi_go;
    private ImageView kaoshi_cj, kaoshi_cjph;
    //private Examination examination = null;
    private ExamList.DataBean eDataBean = null;
    private ExamDetails.DataBean edDataBean = null;
    private final static int SCANNIN_GREQUEST_CODE = 10;

    private Intent intent;
    /**
     * 答题闯关考卷列表数据源
     */
    private ArrayList<ExaminationPapers.DataBean> eDataBeenList = new ArrayList<>();
    private EditText sr_password;
    private AbAlertDialogFragment showAlertDialogss;
    //弹窗
    private Dialog dialog;

    /**
     * 单次命题题目数据源
     */
//    private ArrayList<Question> questionList = new ArrayList<>();
//    private Boolean isflag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kaochang_info);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("考试详情");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);

        returns_arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        eDataBean = getIntent().getParcelableExtra("examination");
        //弹窗设置
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);

        if (eDataBean != null) {
            initUI();
            initDate();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getinitData(eDataBean.getId());

//        if (eDataBean != null) {
//            if (EmptyUtil.IsNotEmpty(eDataBean.getId() + "")) {
//                if (eDataBean.getExamtype().equals("repeat") || eDataBean.getExamtype().equals("accrued"))
//                    getData(eDataBean.getId());
//            }
//        }
//        getData(Integer.parseInt(examination.getId() + ""));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (requestCode == 10) {
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        String pwd = bundle.getString("result");
                        if (EmptyUtil.IsNotEmpty(pwd)) {
                            sr_password.setText(pwd);
                        }
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.finish();
    }

    private void initUI() {
        dancixianshi_tv = (TextView) findViewById(R.id.dancixianshi_tv);
        kaoshi_zhubandanwei = (TextView) findViewById(R.id.kaoshi_zhubandanwei);
        kaoshi_time = (TextView) findViewById(R.id.kaoshi_time);
        kaoshi_leixing = (TextView) findViewById(R.id.kaoshi_leixing);
        kaoshi_gongyougs = (TextView) findViewById(R.id.kaoshi_gongyougs);
        kaoshi_guangshu = (TextView) findViewById(R.id.kaoshi_guangshu);
        kaoshi_danci = (TextView) findViewById(R.id.kaoshi_danci);
        kaoshi_dati = (TextView) findViewById(R.id.kaoshi_dati);
        kaoshi_yicanjia = (TextView) findViewById(R.id.kaoshi_yicanjia);
        kaoshi_xuefen = (TextView) findViewById(R.id.kaoshi_xuefen);
        kaoshi_renshu = (TextView) findViewById(R.id.kaoshi_renshu);

        kaoshi_desc = (TextView) findViewById(R.id.kaoshi_desc);
        kaoshi_name = (TextView) findViewById(R.id.kaoshi_name);
        kaoshi_cjph = (ImageView) findViewById(R.id.kaoshi_cjph);
        kaoshi_cjph.setOnClickListener(this);
        kaoshi_go = (Button) findViewById(R.id.kaoshi_go);
        kaoshi_go.setOnClickListener(this);
        kaoshi_cj = (ImageView) findViewById(R.id.kaoshi_cj);
        kaoshi_cj.setOnClickListener(this);

        kaoshi_total_ll = (LinearLayout) findViewById(R.id.kaoshi_total_ll);
        kaoshi_total_number = (TextView) findViewById(R.id.kaoshi_total_number);
        kaoshi_now_number = (TextView) findViewById(R.id.kaoshi_now_number);

    }

    private void initDate() {

        /**
         * 考试详情控件
         */
        try {
            if (eDataBean != null) {
                if (EmptyUtil.IsNotEmpty(eDataBean.getExamname()))
                    kaoshi_name.setText(eDataBean.getExamname());

                if (eDataBean.getCompany() == null)
                    kaoshi_zhubandanwei.setText("系统");
                else if (EmptyUtil.IsNotEmpty(eDataBean.getCompany().getName() + ""))
                    kaoshi_zhubandanwei.setText(eDataBean.getCompany().getName());

                if (EmptyUtil.IsNotEmpty(eDataBean.getBegintime() + ""))
                    if (EmptyUtil.IsNotEmpty(eDataBean.getEndtime() + "")) {
                        //必须转换一下 php 给的是 要 x 1000的
                        long begintime = (long) eDataBean.getBegintime();
                        long endtime = (long) eDataBean.getEndtime();

                        kaoshi_time.setText(DateUtils.getDateToLongString(begintime) + "至" + DateUtils.getDateToLongString(endtime));
                    }

                if (EmptyUtil.IsNotEmpty(eDataBean.getPasssore() + ""))
                    kaoshi_xuefen.setText(eDataBean.getPasssore() + "分");

                if (EmptyUtil.IsNotEmpty(eDataBean.getDuration() + ""))
                    if (eDataBean.getDuration() == 0)
                        kaoshi_danci.setText("不限时");
                    else
                        kaoshi_danci.setText(eDataBean.getDuration() + "分钟");
                else
                    kaoshi_danci.setText("不限时");

                if (EmptyUtil.IsNotEmpty(eDataBean.getAnswertimes() + ""))
                    kaoshi_dati.setText(eDataBean.getAnswertimes() + "次");

                switch (eDataBean.getExamtype()) {
                    case "single":
                        //kaoshi_leixing.setText(StateUtil.examinationType(eDataBean.getExamtype()));
                        kaoshi_leixing.setText("单次考试");
                        kaoshi_gongyougs.setVisibility(View.GONE);
                        kaoshi_guangshu.setVisibility(View.GONE);
                        //kaoshi_gongyougs.setText("满分合计：");
                        //kaoshi_guangshu.setText("");
                        break;
                    case "repeat":
                        dancixianshi_tv.setText("闯关总限时 :");
                        //kaoshi_leixing.setText(StateUtil.examinationType(eDataBean.getExamtype()));
                        kaoshi_leixing.setText("重复闯关");
                        kaoshi_gongyougs.setText("共有关数 :");
                        if (EmptyUtil.IsNotEmpty(eDataBean.getStages() + ""))
                            kaoshi_guangshu.setText(eDataBean.getStages() + "关");
                        else
                            kaoshi_guangshu.setText("无限关");

                        break;
                    case "accrued":
                        dancixianshi_tv.setText("单个关卡限时 :");
                        //kaoshi_leixing.setText(StateUtil.examinationType(eDataBean.getExamtype()));
                        kaoshi_leixing.setText("累计闯关");
                        kaoshi_gongyougs.setText("共有关数 :");
                        if (EmptyUtil.IsNotEmpty(eDataBean.getStages() + ""))
                            kaoshi_guangshu.setText(eDataBean.getStages() + "关");
                        else
                            kaoshi_guangshu.setText("无限关");
                        break;
                    default:
                        kaoshi_leixing.setText("暂无状态");
                        break;
                }
//            if ("2".equals(examination.getType())) {
//                kaoshi_leixing.setText(StateUtil.examinationType(examination
//                        .getType()));
//                kaoshi_gongyougs.setVisibility(NotifyListView.GONE);
//                kaoshi_guangshu.setVisibility(NotifyListView.GONE);
//                // kaoshi_gongyougs.setText("满分合计：");
//                // kaoshi_guangshu.setText("");
//            } else if ("3".equals(examination.getType())) {
//                dancixianshi_tv.setText("闯关限时 :");
//                kaoshi_leixing.setText(StateUtil.examinationType(examination
//                        .getType()));
//                kaoshi_gongyougs.setText("共有关数 :");
//                if (EmptyUtil.IsNotEmpty(examination.getTotn())) {
//                    kaoshi_guangshu.setText(examination.getTotn() + "关");
//                } else {
//                    kaoshi_guangshu.setText("无限关");
//                }
//
//            } else if ("4".equals(examination.getType())) {
//                dancixianshi_tv.setText("闯关限时 :");
//                kaoshi_leixing.setText(StateUtil.examinationType(examination
//                        .getType()));
//                kaoshi_gongyougs.setText("共有关数 :");
//            }
            }
        } catch (Exception e) {
            Log.e("KaoChangInfoActivityUI", e.toString());
        }
    }

    public boolean isjoing(ExamList.DataBean examination) {
        long starttime = 0;
        long endtime = 0;
        boolean iskk = false;

        try {
            Date startDate = null;
            Date endDate = null;
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            if (EmptyUtil.IsNotEmpty(examination.getBegintime() + "")) {
//                startDate = sdf.parse(examination.getStarttimeStr());
                starttime = (long) examination.getBegintime() * 1000;  //整形转long
                startDate = new Date(starttime);
            }
            if (EmptyUtil.IsNotEmpty(examination.getEndtime() + "")) {

                endtime = (long) examination.getEndtime() * 1000;//整形转long
                endDate = new Date(endtime);
            }
            long nowTime = System.currentTimeMillis() / 1000 - CacheUtil.timeOffset;
            starttime = startDate.getTime() / 1000;
            endtime = endDate.getTime() / 1000;
            if (nowTime <= endtime && nowTime >= starttime) {
                return true;
            } else {
                if (nowTime < starttime)
                    Toast.makeText(KaoChangInfoActivity.this, "考试还未开始", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(KaoChangInfoActivity.this, "此考试已结束", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//		if (EmptyUtil.IsNotEmpty(examination.getStarttime())) {
//			starttime = Long.parseLong(examination.getStarttime()) /1000;
//		}
//		if (EmptyUtil.IsNotEmpty(examination.getEndtime())) {
//			endtime = Long.parseLong(examination.getEndtime()) /1000;
//		}
        starttime = (long) examination.getBegintime() * 1000;
        endtime = (long) examination.getEndtime() * 1000;
        long now = System.currentTimeMillis() / 1000 - CacheUtil.timeOffset;
        if (now >= starttime && now <= endtime) {
            iskk = true;
        } else {
            if (now < starttime)
                Toast.makeText(KaoChangInfoActivity.this, "该考试还未开始", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(KaoChangInfoActivity.this, "此考试已结束", Toast.LENGTH_SHORT).show();
            iskk = false;
        }
        return iskk;
    }

    Button bt_sure;

    private void showmimashurukuang() {
        View showview = View.inflate(this, R.layout.activity_mimatishi, null);
        sr_password = (EditText) showview.findViewById(R.id.sr_password);
        showAlertDialogss = AbDialogUtil.showAlertDialog(showview);
        ImageView iv_shaomiao = (ImageView) showview.findViewById(R.id.iv_shaomiao);
        Button bt_cancel = (Button) showview.findViewById(R.id.bt_cancel);
        bt_sure = (Button) showview.findViewById(R.id.bt_sure);

        iv_shaomiao.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        bt_sure.setOnClickListener(this);
    }

    //    public void kaoshitype() {
//		if (list.size() > 0) {
//
//			if ("2".equals(examination.getType())) {
//				Intent intent = new Intent(KaoChangInfoActivity.this,
//						kaoshiContentActivity.class);
//				// 多套试题随机选择一套
//				Random random = new Random();
//				int indext = random.nextInt(list.size()) + 0;
//				// 给考卷对像赋值考试ID（因为接口未返回考试ID）
//				if (examination != null) {
//					list.get(indext).setExaminationid(
//							examination.getExaminationid());
//				}
//				intent.putExtra("examinationPapers", list.get(indext));
//				startActivity(intent);
//			} else if ("3".equals(examination.getType())
//					|| "4".equals(examination.getType())) {
//
//				Intent intent1 = new Intent(KaoChangInfoActivity.this,
//						DatiChuangguanActivity.class);
//				intent1.putExtra("list", list);
//				intent1.putExtra("type", examination.getType());
//				startActivity(intent1);
//			}
//
//		} else {
//			Toast.makeText(KaoChangInfoActivity.this, "此考场暂无考卷",
//					Toast.LENGTH_SHORT).show();
//		}
//    }
//初始化 待确认列表界面
    private View initAffirmContentLayout() {
        View affirmContentLayout = View.inflate(this, R.layout.user_context_affirm_menu, null);
        TextView affirm_context = (TextView) affirmContentLayout.findViewById(R.id.affirm_context);
        TextView affirm_cancel = (TextView) affirmContentLayout.findViewById(R.id.affirm_cancel);
        TextView affirm_submit = (TextView) affirmContentLayout.findViewById(R.id.affirm_submit);
        affirm_context.setText("恭喜您上次考试已通关，需要重新闯关一次？");
        affirm_cancel.setOnClickListener(this);
        affirm_submit.setText("重闯");
        affirm_submit.setOnClickListener(this);
        return affirmContentLayout;
    }
     private void kaoshigo(){
         if (isjoing(eDataBean)) {
             if ("single".equals(eDataBean.getExamtype())) {
                 if (edDataBean != null)
                     showmimashurukuang();
                 else
                     Toast.makeText(KaoChangInfoActivity.this, "您的网络异常，请检查网络后再试", Toast.LENGTH_SHORT).show();
             } else {
//                        System.out.println("isflag = --------- " + isflag);

//                        if (isflag == true) {
//                            int count = eDataBean.getAnswertimes(); // 这次考试能够参与的次数
//                            int joinCount = 0; // 你参与过的次数
                 //System.out.println(count+"----可以参与的次数");
                 //jionCount = Integer.parseInt(list.get(0).getAnsercount());
                 //joinCount = examination.get
                 if (edDataBean != null)
                     if (edDataBean.getJoincount() >= edDataBean.getAnswertimes()) {
                         Toast.makeText(KaoChangInfoActivity.this, "你考试次数已过,不能再参加考试了！", Toast.LENGTH_SHORT).show();
                     } else {
                         if (edDataBean != null) {
                             if (EmptyUtil.IsNotEmpty(edDataBean.getId() + "")) {
                                 if (edDataBean.getExamtype().equals("accrued"))
                                     if (edDataBean.getLast_pass_stages() < edDataBean.getStages())
                                         getData(edDataBean.getId(), edDataBean.getLast_pass_stages());
                                     else {
                                         if (dialog.isShowing()) {
                                             dialog.dismiss();
                                         } else {
                                             dialog.setContentView(initAffirmContentLayout());
                                             dialog.getWindow().setGravity(Gravity.CENTER);
                                             //获得屏幕看都，并传给dialog
                                             dialog.getWindow().getAttributes().width = (int) ((getWindowManager().getDefaultDisplay().getWidth()) * 0.8);
                                             dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                                             dialog.show();
                                         }
                                     }
                                 else if (edDataBean.getExamtype().equals("repeat"))
                                     getData(edDataBean.getId(), 0);
                             }
                         }
//                                System.out.println("intent ----size" + eDataBeenList.size());
//                                Intent intent1 = new Intent(KaoChangInfoActivity.this, DatiChuangguanActivity.class);
//                                intent1.putExtra("list", eDataBeenList);
//                                intent1.putExtra("examination", eDataBean);
//                                intent1.putExtra("flag", 0);
//                                intent1.putExtra("comflag", 0);
//                                startActivity(intent1);
                     }
                 else
                     Toast.makeText(KaoChangInfoActivity.this, "您的网络异常，请检查网络后再试", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(KaoChangInfoActivity.this, "试题正在加载...", Toast.LENGTH_SHORT).show();
//                        }
             }
             // kaoshitype();
         }
     }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kaoshi_go: {        //进入考试按钮
                //System.out.println(examination.getExamtype()+"----type");
                getinitData(eDataBean.getId(),false);

            }
            break;
            case R.id.affirm_cancel://确认取消
                if (dialog.isShowing())
                    dialog.dismiss();
                break;
            case R.id.affirm_submit://确认确认
                getData(edDataBean.getId(), 0);
                break;
            case R.id.kaoshi_cj:            //我的成绩按钮
                intent = new Intent(KaoChangInfoActivity.this, KaoshiHistory.class);
                intent.putExtra("examination", eDataBean);
                startActivity(intent);
                // Toast.makeText(KaoChangInfoActivity.this, "你还未进入考试，暂无成绩",
                // Toast.LENGTH_SHORT).show();
                break;
            case R.id.kaoshi_cjph:          //成绩排行按钮
                intent = new Intent(KaoChangInfoActivity.this, ScoreRanking.class);
                intent.putExtra("examination", eDataBean);
                startActivity(intent);
                break;
            case R.id.iv_shaomiao:          //输入密码扫码
                intent = new Intent(KaoChangInfoActivity.this, CameraTestActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                break;
            case R.id.bt_cancel:            //输入密码取消
                showAlertDialogss.dismiss();
                break;
            case R.id.bt_sure:              //输入密码确定
                if (edDataBean.getJoincount() >= edDataBean.getAnswertimes()) {
                    Toast.makeText(KaoChangInfoActivity.this, "你考试次数已用完,不能再参加考试了！", Toast.LENGTH_SHORT).show();
                } else {
                    // 得到用户输入的密码
                    String password = sr_password.getText().toString();
                    if (EmptyUtil.IsNotEmpty(password)) {
                        gerData(eDataBean.getId(), password);
//                        showAlertDialogss.dismiss();
                    } else
                        Toast.makeText(KaoChangInfoActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 考试详情
     *
     * @param examinationid
     */
    private void getinitData(int examinationid) {

        //http://xfapi.ccketang.com/examination/{examination_id}/exampaper/{exampaper_sort}
        String pURL = URL_PR + PREPARE_EXAM_URL + examinationid;
        Log.e("闯关_URL", pURL);

        OkHttpUtils
                .get()
                .url(pURL)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new ExamDetailsCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Exampaper_onError", e.toString());
                                 Toast.makeText(KaoChangInfoActivity.this, "您的网络异常，请检查网络后再试", Toast.LENGTH_LONG).show();
//                                 Toast.makeText(KaoChangInfoActivity.this, "网络异常—准备考试试卷", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(ExamDetails response, int id) {
                                 Log.e("ExamPaperCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         edDataBean = response.getData();

                                         if (EmptyUtil.IsNotEmpty(edDataBean.getExamname()))
                                             kaoshi_name.setText(edDataBean.getExamname());

                                         if (edDataBean.getExamtype().equals("single") || edDataBean.getExamtype().equals("repeat"))
                                             kaoshi_total_ll.setVisibility(View.GONE);
                                         else
                                             kaoshi_total_ll.setVisibility(View.VISIBLE);

                                         if (EmptyUtil.IsNotEmpty(edDataBean.getAnswered_nums() + ""))
                                             kaoshi_renshu.setText(edDataBean.getAnswered_nums() + "人次");

                                         if (EmptyUtil.IsNotEmpty(edDataBean.getJoincount() + ""))
                                             kaoshi_yicanjia.setText(edDataBean.getAnswertimes() - edDataBean.getJoincount() + "次");

                                         if (EmptyUtil.IsNotEmpty(edDataBean.getDescription()))
                                             kaoshi_desc.setText(edDataBean.getDescription());

                                         kaoshi_total_number.setText(edDataBean.getStages() + "");
                                         kaoshi_now_number.setText(edDataBean.getLast_pass_stages() + "");

                                         break;
                                     case 1:
                                         Toast.makeText(KaoChangInfoActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );

        Log.i("tmdceshi",pURL+"----"+XrjHttpClient.ACCEPT_V2+"------"+MyApplication.hxt_setting_config.getString("credentials", ""));
    }

    /**
     * 考试详情
     *
     * @param examinationid
     */
    private void getinitData(int examinationid ,boolean flag) {

        //http://xfapi.ccketang.com/examination/{examination_id}/exampaper/{exampaper_sort}
        String pURL = URL_PR + PREPARE_EXAM_URL + examinationid;
        Log.e("闯关_URL", pURL);

        OkHttpUtils
                .get()
                .url(pURL)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new ExamDetailsCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Exampaper_onError", e.toString());
                                 Toast.makeText(KaoChangInfoActivity.this, "您的网络异常，请检查网络后再试", Toast.LENGTH_LONG).show();
//                                 Toast.makeText(KaoChangInfoActivity.this, "网络异常—准备考试试卷", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(ExamDetails response, int id) {
                                 Log.e("ExamPaperCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         edDataBean = response.getData();

                                         if (EmptyUtil.IsNotEmpty(edDataBean.getExamname()))
                                             kaoshi_name.setText(edDataBean.getExamname());

                                         if (edDataBean.getExamtype().equals("single") || edDataBean.getExamtype().equals("repeat"))
                                             kaoshi_total_ll.setVisibility(View.GONE);
                                         else
                                             kaoshi_total_ll.setVisibility(View.VISIBLE);

                                         if (EmptyUtil.IsNotEmpty(edDataBean.getAnswered_nums() + ""))
                                             kaoshi_renshu.setText(edDataBean.getAnswered_nums() + "人次");

                                         if (EmptyUtil.IsNotEmpty(edDataBean.getJoincount() + ""))
                                             kaoshi_yicanjia.setText(edDataBean.getAnswertimes() - edDataBean.getJoincount() + "次");

                                         if (EmptyUtil.IsNotEmpty(edDataBean.getDescription()))
                                             kaoshi_desc.setText(edDataBean.getDescription());

                                         kaoshi_total_number.setText(edDataBean.getStages() + "");
                                         kaoshi_now_number.setText(edDataBean.getLast_pass_stages() + "");
                                         kaoshigo();
                                         break;
                                     case 1:
                                         Toast.makeText(KaoChangInfoActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );

        Log.i("tmdceshi",pURL+"----"+XrjHttpClient.ACCEPT_V2+"------"+MyApplication.hxt_setting_config.getString("credentials", ""));
    }

    private abstract class ExamDetailsCallback extends Callback<ExamDetails> {

        @Override
        public ExamDetails parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("详情 String", result);
            ExamDetails examDetails = new Gson().fromJson(result, ExamDetails.class);
            return examDetails;
        }
    }

    /**
     * 单次命题
     *
     * @param password
     */
    public void gerData(int examinationid, final String password) {

//        http://xfapi.ccketang.com/examination/{examination_id}/exampaper/{exampaper_sort}
        String pURL = URL_PR + PREPARE_EXAM_URL + examinationid + "/exampaper";

        Log.e("bt_sure.getText()", bt_sure.getText().toString());
        if (bt_sure != null)
            if (bt_sure.getText().toString().equals("确定")) {

                Log.e("单次_URL", pURL);
                bt_sure.setText("生成试卷...");

                OkHttpUtils
                        .post()
                        .url(pURL)
                        .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                        .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                        .addParams("password", password + "")
                        .build()
                        .execute(new ExampaperCallback() {
                                     @Override
                                     public void onError(Call call, Exception e, int id) {
                                         Log.e("Exampaper_onError", e.toString());
                                         Toast.makeText(KaoChangInfoActivity.this, "您的网络异常，请检查网络后再试", Toast.LENGTH_LONG).show();
                                         bt_sure.setText("确定");
                                         //Toast.makeText(KaoChangInfoActivity.this, "网络异常—准备考试试卷", Toast.LENGTH_LONG).show();
                                     }

                                     @Override
                                     public void onResponse(ExaminationPapers response, int id) {
                                         Log.e("ExamPaperCallback", response.getError_msg());
                                         switch (response.getError_code()) {
                                             case 0:
                                                 eDataBeenList.clear();
                                                 ExaminationPapers.DataBean epDataBean = response.getData();
                                                 eDataBeenList.add(epDataBean);

                                                 Intent intent = new Intent(KaoChangInfoActivity.this, DatiChuangguan2Activity.class);
                                                 intent.putExtra("list", eDataBeenList);
                                                 intent.putExtra("edDataBean", edDataBean);
                                                 intent.putExtra("key", RandomUtil.createRandom(false, 8));
                                                 //intent.putExtra("epDataBean", epDataBean);
                                                 intent.putExtra("flag", 0);
                                                 intent.putExtra("comflag", 0);
                                                 startActivity(intent);

                                                 showAlertDialogss.dismiss();
                                                 //isflag = true; //试题加载完成
                                                 break;
                                             case 1:
                                                 Toast.makeText(KaoChangInfoActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                                 break;
                                             default:
                                                 break;
                                         }
                                         if (bt_sure != null)
                                             bt_sure.setText("确定");
                                     }
                                 }
                        );
            }

    }

    /**
     * 闯关命题
     *
     * @param examinationid
     */
    private void getData(int examinationid, final int last_pass_stages) {

        Log.e("bt_sure.getText()", kaoshi_go.getText().toString());
        if (kaoshi_go != null)
            if (kaoshi_go.getText().toString().equals("进入考试")) {

                kaoshi_go.setText("生成试卷...");

                //http://xfapi.ccketang.com/examination/{examination_id}/exampaper/{exampaper_sort}
                String pURL = URL_PR + PREPARE_EXAM_URL + examinationid + "/exampaper";
                Log.e("闯关_URL", pURL);
                OkHttpUtils
                        .post()
                        .url(pURL)
                        .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                        .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                        .addParams("stage", last_pass_stages + 1 + "")
                        .build()
                        .execute(new ExampaperCallback() {

                                     @Override
                                     public void onError(Call call, Exception e, int id) {
                                         Log.e("Exampaper_onError", e.toString());
                                         Toast.makeText(KaoChangInfoActivity.this, "您的网络异常，请检查网络后再试", Toast.LENGTH_LONG).show();
                                         kaoshi_go.setText("进入考试");
                                         //Toast.makeText(KaoChangInfoActivity.this, "网络异常—准备考试试卷", Toast.LENGTH_LONG).show();
                                     }

                                     @Override
                                     public void onResponse(ExaminationPapers response, int id) {
                                         Log.e("ExamPaperCallback", response.getError_msg());
                                         switch (response.getError_code()) {
                                             case 0:
                                                 eDataBeenList.clear();
                                                 ExaminationPapers.DataBean epDataBean = response.getData();
                                                 eDataBeenList.add(epDataBean);
                                                 Intent intent = null;
                                                 if (edDataBean.getExamtype().equals("repeat")) {
                                                     intent = new Intent(KaoChangInfoActivity.this, DatiChuangguan3Activity.class);
                                                     intent.putExtra("continue_time", (long) (Integer.parseInt(eDataBeenList.get(0).getDuration()) * 60 * 1000));
                                                     Global.alltime= Integer.parseInt(eDataBeenList.get(0).getDuration()) * 60 * 1000;
                                                 } else if (edDataBean.getExamtype().equals("accrued"))
                                                     intent = new Intent(KaoChangInfoActivity.this, DatiChuangguan2Activity.class);

                                                 intent.putExtra("list", eDataBeenList);
                                                 intent.putExtra("edDataBean", edDataBean);
                                                 intent.putExtra("key", RandomUtil.createRandom(false, 8));
                                                 //intent.putExtra("epDataBean", epDataBean);
                                                 intent.putExtra("flag", 0);
                                                 intent.putExtra("comflag", 0);
                                                 intent.putExtra("last_pass_stages", last_pass_stages + 1);
                                                 startActivity(intent);

                                                 if (dialog.isShowing())
                                                     dialog.dismiss();

                                                 //isflag = true; //试题加载完成
                                                 break;
                                             case 1:
                                                 Toast.makeText(KaoChangInfoActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                                 break;
                                             default:
                                                 break;
                                         }
                                         if (kaoshi_go != null)
                                             kaoshi_go.setText("进入考试");
                                     }
                                 }
                        );
            }
    }

    private abstract class ExampaperCallback extends Callback<ExaminationPapers> {

        @Override
        public ExaminationPapers parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("试卷 String", result);
            ExaminationPapers examinationPapers = new Gson().fromJson(result, ExaminationPapers.class);
            return examinationPapers;
        }
    }
}

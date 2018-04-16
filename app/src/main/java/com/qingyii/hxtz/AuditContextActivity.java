package com.qingyii.hxtz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.adapter.AuditContextAdapter;
import com.qingyii.hxtz.circle.NonScrollGridView;
import com.qingyii.hxtz.circle.PhotoAdapter;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.httpway.GLUpload;
import com.qingyii.hxtz.pojo.AuditCrewList;
import com.qingyii.hxtz.pojo.DocumentaryStatus;
import com.qingyii.hxtz.pojo.WaitAuditList;

import java.util.ArrayList;
import java.util.List;

public class AuditContextActivity extends AbBaseActivity implements View.OnClickListener {
    private TextView activity_tltle_name;
    private View headerView;
    private Intent intent;
    private GLUpload glUpload = GLUpload.getGLUpload();
    private ListView mListView;
    private AuditContextAdapter auditContextAdapter;

    private AuditCrewList.DataBean.UsresBean aUsresBean;
    private WaitAuditList.DataBean wDataBean;
    private DocumentaryStatus.DataBean dsDataBean;

    //headerView 控件
    private TextView report_context_class;
    private TextView report_context_time;
    private TextView context_main_content;
    //headerView 图片展示
    private NonScrollGridView context_main_photo;
    private ArrayList<String> photos = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    //底部按钮
    private TextView context_operation_evaluate;
    private TextView context_operation_appear;
    //弹窗
    private Dialog dialog;
    //评价弹窗
    private View evaluateContentLayout;
    private ImageView evaluate_minus;
    private TextView evaluate_number;
    private double evaluate_grade = 0;
    private ImageView evaluate_add;
    //    private Spinner evaluate_decade;
//    private String decade;
    private Spinner evaluate_integer;
    private String integer;
    private Spinner evaluate_decimals;
    private String decimals;
    //    private List<String> listDecadeSpinner = new ArrayList<>();
    private List<String> listIntegerSpinner = new ArrayList<>();
    private List<String> listDecimalsSpinner = new ArrayList<>();
    private EditText evaluate_edit;
    private TextView evaluate_cancel;
    private TextView evaluate_submit;
    //    private TextView evaluate_appear;
    //确认弹窗
    private View affirmContentLayout;
    private TextView affirm_context;
    private TextView affirm_cancel;
    private TextView affirm_submit;

    private View appealContentLayout;
    private EditText menu_edit;
    private TextView guan_cancel;
    private TextView guan_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_context);
        //tltle设置
        activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
//        activity_tltle_name.setText("待确认打分项");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        wDataBean = intent.getParcelableExtra("wDataBean");
        aUsresBean = intent.getParcelableExtra("aUsresBean");
        dsDataBean = intent.getParcelableExtra("dsDataBean");

        if (aUsresBean != null && dsDataBean != null) {
            String[] month = dsDataBean.getCurr_month().split("-");
            activity_tltle_name.setText(aUsresBean.getUsername() + " " + Integer.parseInt(month[month.length - 1]) + "月纪实审核");
        }

        initView();
        initDate();
    }

    private void initView() {
        //弹窗设置
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        headerView = View.inflate(AuditContextActivity.this, R.layout.report_context_header, null);
        report_context_class = (TextView) headerView.findViewById(R.id.report_context_class);
        report_context_time = (TextView) headerView.findViewById(R.id.report_context_time);
        context_main_content = (TextView) headerView.findViewById(R.id.context_main_content);
        context_main_photo = (NonScrollGridView) headerView.findViewById(R.id.context_main_photo);
        mListView = (ListView) findViewById(R.id.mListView);
        context_operation_evaluate = (TextView) findViewById(R.id.context_operation_evaluate);
        context_operation_appear = (TextView) findViewById(R.id.context_operation_appear);
    }

    private void initDate() {
        try {
            report_context_class.setText("类型：" + wDataBean.getDoctypename() + "    ");
            report_context_time.setText("时间：" + wDataBean.getCreated_at());
            context_main_content.setText("" + wDataBean.getContent());

            for (int i = 0; i < wDataBean.getPicture().size(); i++)
                photos.add(wDataBean.getPicture().get(i).getUri());

            photoAdapter = new PhotoAdapter(this, photos);
            context_main_photo.setAdapter(photoAdapter);

            if (wDataBean.getChecklogs() != null) {
                mListView.addHeaderView(headerView);
                auditContextAdapter = new AuditContextAdapter(this, wDataBean.getChecklogs());
                mListView.setAdapter(auditContextAdapter);
            }

            //只有在一级审核员进入时显示
            if (MyApplication.userUtil.getCheck_level() == 1)
                context_operation_evaluate.setVisibility(View.VISIBLE);

            if (wDataBean.getChecklogs().size() > 0 &&
                    wDataBean.getChecklogs().get(wDataBean.getChecklogs().size() - 1).getAppeals() != null &&
                    !wDataBean.getChecklogs().get(wDataBean.getChecklogs().size() - 1).getAppeals().getResult().equals("已上报")) {
                context_operation_appear.setVisibility(View.VISIBLE);
                context_operation_appear.setOnClickListener(this);
            }

//            switch (MyApplication.userUtil.getCheck_level()) {
//                case 1:
//                    activity_tltle_name.setText(aUsresBean.getUsername() + "的纪实审核");
//                    break;
//                case 2:
//                    activity_tltle_name.setText(aUsresBean.getUsername() + "的纪实审核");
//                    break;
//                case 4:
//                    activity_tltle_name.setText(aUsresBean.getUsername() + "的纪实审核");
//                    break;
//                case 5:
//                    activity_tltle_name.setText(aUsresBean.getUsername() + "的纪实审核");
//                    break;
//                default:
//                    break;
//            }

            Log.e("ComtextStatus", wDataBean.getStatus() + "");
            switch (wDataBean.getStatus()) {
                case 0://待审核
                    context_operation_evaluate.setText("打分");
                    context_operation_evaluate.setOnClickListener(this);
                    break;
                case 1://申诉
                    context_operation_evaluate.setText("复核打分");
                    context_operation_evaluate.setOnClickListener(this);
                    break;
                case 2://待确认
                    if (wDataBean.getChecklogs() != null) {
                        if (wDataBean.getChecklogs().size() > 0) {
                            if (wDataBean.getChecklogs().get(wDataBean.getChecklogs().size() - 1).getAppeals() != null) {
                                if (wDataBean.getChecklogs().get(wDataBean.getChecklogs().size() - 1).getAppeals().getResult().equals("已上报")) {
                                    context_operation_evaluate.setText("已上报上级");
                                } else {
                                    context_operation_evaluate.setText("修改本次评分");
                                    context_operation_evaluate.setOnClickListener(this);
                                }
                            } else {
                                context_operation_evaluate.setText("修改本次评分");
                                context_operation_evaluate.setOnClickListener(this);
                            }
                        } else {
                            context_operation_evaluate.setText("修改本次评分");
                            context_operation_evaluate.setOnClickListener(this);
                        }
                    } else {
                        context_operation_evaluate.setText("修改本次评分");
                        context_operation_evaluate.setOnClickListener(this);
                    }
                    break;
                case 3://已确认
                    context_operation_evaluate.setText("已确认");
                    break;
            }
        } catch (Exception e) {
            Log.e("审核详情 加载数据", e.toString());
        }

//        listDecadeSpinner.add("2");
//        listDecadeSpinner.add("1");
//        listDecadeSpinner.add("0");
//        listDecadeSpinner.add("-0");
//        listDecadeSpinner.add("-1");
//        listDecadeSpinner.add("-2");

        listIntegerSpinner.add("20");
        listIntegerSpinner.add("19");
        listIntegerSpinner.add("18");
        listIntegerSpinner.add("17");
        listIntegerSpinner.add("16");
        listIntegerSpinner.add("15");
        listIntegerSpinner.add("14");
        listIntegerSpinner.add("13");
        listIntegerSpinner.add("12");
        listIntegerSpinner.add("11");
        listIntegerSpinner.add("10");
        listIntegerSpinner.add("9");
        listIntegerSpinner.add("8");
        listIntegerSpinner.add("7");
        listIntegerSpinner.add("6");
        listIntegerSpinner.add("5");
        listIntegerSpinner.add("4");
        listIntegerSpinner.add("3");
        listIntegerSpinner.add("2");
        listIntegerSpinner.add("1");
        listIntegerSpinner.add("0");
        listIntegerSpinner.add("-0");
        listIntegerSpinner.add("-1");
        listIntegerSpinner.add("-2");
        listIntegerSpinner.add("-3");
        listIntegerSpinner.add("-4");
        listIntegerSpinner.add("-5");
        listIntegerSpinner.add("-6");
        listIntegerSpinner.add("-7");
        listIntegerSpinner.add("-8");
        listIntegerSpinner.add("-9");
        listIntegerSpinner.add("-10");
        listIntegerSpinner.add("-11");
        listIntegerSpinner.add("-12");
        listIntegerSpinner.add("-13");
        listIntegerSpinner.add("-14");
        listIntegerSpinner.add("-15");
        listIntegerSpinner.add("-16");
        listIntegerSpinner.add("-17");
        listIntegerSpinner.add("-18");
        listIntegerSpinner.add("-19");
        listIntegerSpinner.add("-20");

        listDecimalsSpinner.add("0");
        listDecimalsSpinner.add("1");
        listDecimalsSpinner.add("3");
        listDecimalsSpinner.add("2");
        listDecimalsSpinner.add("5");
        listDecimalsSpinner.add("4");
        listDecimalsSpinner.add("6");
        listDecimalsSpinner.add("7");
        listDecimalsSpinner.add("8");
        listDecimalsSpinner.add("9");
    }

    //初始化 待确认列表界面
    private View initEvaluateContentLayout() {
        //底部弹窗布局
        evaluateContentLayout = View.inflate(this, R.layout.user_context_evaluate_menu, null);
        evaluate_minus = (ImageView) evaluateContentLayout.findViewById(R.id.evaluate_minus);
        evaluate_number = (TextView) evaluateContentLayout.findViewById(R.id.evaluate_number);
        evaluate_add = (ImageView) evaluateContentLayout.findViewById(R.id.evaluate_add);
        evaluate_edit = (EditText) evaluateContentLayout.findViewById(R.id.evaluate_edit);
        evaluate_integer = (Spinner) evaluateContentLayout.findViewById(R.id.evaluate_integer);
        evaluate_decimals = (Spinner) evaluateContentLayout.findViewById(R.id.evaluate_decimals);
        evaluate_cancel = (TextView) evaluateContentLayout.findViewById(R.id.evaluate_cancel);
        evaluate_submit = (TextView) evaluateContentLayout.findViewById(R.id.evaluate_submit);
//        evaluate_appear = (TextView) evaluateContentLayout.findViewById(R.id.evaluate_appear);
        evaluate_minus.setOnClickListener(this);
        evaluate_add.setOnClickListener(this);
        evaluate_cancel.setOnClickListener(this);
        evaluate_submit.setOnClickListener(this);
//        evaluate_appear.setOnClickListener(this);

        // 第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
//        final ArrayAdapter<String> adapterDuration = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, listDecadeSpinner);
//        adapterDuration.setDropDownViewResource(R.layout.spinner_dropdown);
//        evaluate_decade.setAdapter(adapterDuration);
//        evaluate_decade.setSelection(2);
//        decade = "0";

        // 第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        final ArrayAdapter<String> adapterInteger = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listIntegerSpinner);
        adapterInteger.setDropDownViewResource(R.layout.spinner_dropdown);
        evaluate_integer.setAdapter(adapterInteger);
        evaluate_integer.setSelection(20);
        integer = "0";

        // 第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        final ArrayAdapter<String> adapterDecimals = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listDecimalsSpinner);
        adapterDecimals.setDropDownViewResource(R.layout.spinner_dropdown);
        evaluate_decimals.setAdapter(adapterDecimals);
        evaluate_decimals.setSelection(0);
        decimals = "0";

//        evaluate_decade.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> arg0, NotifyListView arg1, int position, long arg3) {
//                Log.e("Decade_Spinner", position + "");
//                decade = listDecadeSpinner.get(position);
////                arg0.setVisibility(NotifyListView.VISIBLE);
//            }
//
//            public void onNothingSelected(AdapterView<?> arg0) {
////                arg0.setVisibility(NotifyListView.VISIBLE);
//            }
//        });

        evaluate_integer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Log.e("Integer_Spinner", position + "");
                integer = listIntegerSpinner.get(position);
                if (listIntegerSpinner.get(position).equals("20") || listIntegerSpinner.get(position).equals("-20")) {
                    evaluate_decimals.setSelection(0);
                    decimals = "0";
                }
//                arg0.setVisibility(NotifyListView.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
//                arg0.setVisibility(NotifyListView.VISIBLE);
            }
        });

        evaluate_decimals.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Log.e("Decimals_Spinner", position + "");
                decimals = listDecimalsSpinner.get(position);
                if (listIntegerSpinner.get(position).equals("20") || listIntegerSpinner.get(position).equals("-20")) {
                    evaluate_decimals.setSelection(0);
                    decimals = "0";
                }
//                arg0.setVisibility(NotifyListView.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
//                arg0.setVisibility(NotifyListView.VISIBLE);
            }
        });

        if (wDataBean.getChecklogs().size() > 0 && wDataBean.getChecklogs().get(wDataBean.getChecklogs().size() - 1).getAppeals() != null) {
//            evaluate_appear.setVisibility(NotifyListView.VISIBLE);
            evaluate_cancel.setText("维持初审");
//            evaluate_edit.setHint("");
        }

        return evaluateContentLayout;
    }

    //初始化 待确认列表界面
    private View initAffirmContentLayout() {
        affirmContentLayout = View.inflate(this, R.layout.user_context_affirm_menu, null);
        affirm_context = (TextView) affirmContentLayout.findViewById(R.id.affirm_context);
        affirm_cancel = (TextView) affirmContentLayout.findViewById(R.id.affirm_cancel);
        affirm_submit = (TextView) affirmContentLayout.findViewById(R.id.affirm_submit);
        affirm_cancel.setOnClickListener(this);
        affirm_submit.setOnClickListener(this);
        return affirmContentLayout;
    }

    //初始化 待确认列表界面
    private View initAppealContentLayout() {
        appealContentLayout = View.inflate(this, R.layout.user_context_appeal_menu, null);
        menu_edit = (EditText) appealContentLayout.findViewById(R.id.menu_edit);
        guan_cancel = (TextView) appealContentLayout.findViewById(R.id.guan_cancel);
        guan_submit = (TextView) appealContentLayout.findViewById(R.id.guan_submit);
        guan_cancel.setOnClickListener(this);
        guan_submit.setOnClickListener(this);
        menu_edit.setHint("维持初审依据和理由，多行输入......");
        return appealContentLayout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.context_operation_evaluate://主页审核
                //将布局设置给Dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.setContentView(initEvaluateContentLayout());
//                    evaluate_cancel.setText("取消");
                    evaluate_submit.setText("提交");
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
                }
                break;
            case R.id.evaluate_cancel://维持原审核 或 取消
                if (wDataBean.getChecklogs().size() > 0 && wDataBean.getChecklogs().get(wDataBean.getChecklogs().size() - 1).getAppeals() != null) {
                    //判断复审时为 点击弹出维持原审的弹窗
                    //将布局设置给Dialog
//                    if (dialog.isShowing()) {
                    dialog.dismiss();
//                    } else {
                    dialog.setContentView(initAppealContentLayout());
//                    guan_cancel.setText("取消");
                    guan_submit.setText("提交");
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
//                    }
                } else
                    dialog.dismiss();
                break;
            case R.id.guan_cancel://维持原审核取消
                dialog.dismiss();
                break;
            case R.id.guan_submit://维持原审核提交
                if (menu_edit.getText().toString().equals("")) {
                    Toast.makeText(AuditContextActivity.this, "审核内容不能为空", Toast.LENGTH_LONG).show();
                } else {
                    double score = wDataBean.getChecklogs().get(wDataBean.getChecklogs().size() - 1).getScore();

                    glUpload.auditUpload(AuditContextActivity.this, dialog, wDataBean.getId(),
                            score + "", menu_edit.getText().toString(), "");
                }
                break;
            case R.id.evaluate_submit://审核提交

                evaluate_grade = Double.parseDouble(integer + "." + decimals);

                if (evaluate_edit.getText().toString().equals("")) {
                    Toast.makeText(AuditContextActivity.this, "审核内容不能为空", Toast.LENGTH_LONG).show();
                } else if (wDataBean.getStatus() == 2) {
                    if (wDataBean.getChecklogs() != null)
                        glUpload.auditUpload(AuditContextActivity.this, dialog, wDataBean.getId(),
                                evaluate_grade + "", evaluate_edit.getText().toString(),
                                wDataBean.getChecklogs().get(wDataBean.getChecklogs().size() - 1).getId() + "");
                    else
                        Toast.makeText(AuditContextActivity.this, "修改目标值为空", Toast.LENGTH_LONG).show();

                } else {
                    glUpload.auditUpload(AuditContextActivity.this, dialog, wDataBean.getId(),
                            evaluate_grade + "",
                            evaluate_edit.getText().toString(), "");
                }
                break;
//            case R.id.evaluate_minus://审核减分
//                if (evaluate_grade > -200)
//                    evaluate_grade--;
//                else
//                    Toast.makeText(AuditContextActivity.this, "已到上限", Toast.LENGTH_SHORT).show();
//                evaluate_number.setText(evaluate_grade / 10 + "." + Math.abs(evaluate_grade % 10));
//                break;
//            case R.id.evaluate_add://审核加分
//                if (evaluate_grade < 200)
//                    evaluate_grade++;
//                else
//                    Toast.makeText(AuditContextActivity.this, "已到上限", Toast.LENGTH_SHORT).show();
//                evaluate_number.setText(evaluate_grade / 10 + "." + Math.abs(evaluate_grade % 10));
//                break;
            case R.id.context_operation_appear://上报上级
                //将布局设置给Dialog
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                } else {
                dialog.dismiss();
                dialog.setContentView(initAffirmContentLayout());
                affirm_context.setText("您确定要报上级复核吗？");
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                dialog.show();
//                }
                break;
            case R.id.affirm_cancel://弹窗取消
                dialog.dismiss();
                break;
            case R.id.affirm_submit://弹窗确认
                glUpload.appearUpload(AuditContextActivity.this, dialog, wDataBean.getId());
                break;
            default:
                break;
        }
    }
}

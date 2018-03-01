package com.qingyii.hxt;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.adapter.ReportContextAdapter;
import com.qingyii.hxt.circle.NonScrollGridView;
import com.qingyii.hxt.circle.PhotoAdapter;
import com.qingyii.hxt.httpway.GLUpload;
import com.qingyii.hxt.pojo.WaitAffirmList;

import java.util.ArrayList;

public class ReportContextActivity extends AbBaseActivity implements View.OnClickListener {
    private View headerView;
    private Intent intent;
    private GLUpload glUpload = GLUpload.getGLUpload();
    private ListView mListView;
    private ReportContextAdapter reportContextAdapter;

    private WaitAffirmList.DataBean wDataBean;
    //headerView 控件
    private TextView report_context_class;
    private TextView report_context_time;
    private TextView context_main_content;
    //headerView 图片展示
    private NonScrollGridView context_main_photo;
    private ArrayList<String> photos = new ArrayList<String>();
    private PhotoAdapter photoAdapter;
    //底部按钮
    private TextView context_operation_affirm;
    //private TextView context_operation_evaluate;
    private TextView context_operation_appeal;
    //弹窗
    private Dialog dialog;
    //确认弹窗
    private View affirmContentLayout;
    private TextView affirm_cancel;
    private TextView affirm_submit;
    //申诉弹窗
    private View menuContentLayout;
    private EditText menu_edit;
    private TextView guan_cancel;
    private TextView guan_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_context);
        //tltle设置
        TextView activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
        activity_tltle_name.setText("纪实积分确认");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //弹窗设置
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);

        intent = getIntent();
        wDataBean = intent.getParcelableExtra("wDataBean");

        mListView = (ListView) findViewById(R.id.mListView);

        context_operation_affirm = (TextView) findViewById(R.id.context_operation_affirm);
//        context_operation_evaluate = (TextView) findViewById(R.id.context_operation_evaluate);
        context_operation_appeal = (TextView) findViewById(R.id.context_operation_appeal);

        switch (wDataBean.getStatus()) {
            case 0:
                context_operation_affirm.setText("待审核");
                break;
            case 1:
                context_operation_affirm.setText("申诉中");
                break;
            case 2:
                context_operation_affirm.setText("确认");
                break;
            case 3:
                context_operation_affirm.setText("已确认");
                break;
        }
//        context_operation_evaluate.setOnClickListener(this);
        context_operation_appeal.setOnClickListener(this);
//        context_operation_evaluate.setVisibility(NotifyListView.GONE);
        //普通用户选项
        if (wDataBean.getStatus() == 2) {
            context_operation_affirm.setOnClickListener(this);
            context_operation_affirm.setVisibility(View.VISIBLE);
            context_operation_appeal.setVisibility(View.VISIBLE);
        } else {
            context_operation_affirm.setVisibility(View.VISIBLE);
            context_operation_appeal.setVisibility(View.GONE);
        }

        if (wDataBean.getChecklogs().size() > 0)
            if (wDataBean.getChecklogs().get(wDataBean.getChecklogs().size() - 1).getAppeals() != null)
                if (wDataBean.getChecklogs().get(wDataBean.getChecklogs().size() - 1).getAppeals().getResult().equals("已上报")) {
                    context_operation_affirm.setVisibility(View.VISIBLE);
                    context_operation_appeal.setVisibility(View.GONE);
                }

        headerView = View.inflate(ReportContextActivity.this, R.layout.report_context_header, null);
        report_context_class = (TextView) headerView.findViewById(R.id.report_context_class);
        report_context_time = (TextView) headerView.findViewById(R.id.report_context_time);
        context_main_content = (TextView) headerView.findViewById(R.id.context_main_content);

        context_main_photo = (NonScrollGridView) headerView.findViewById(R.id.context_main_photo);

        initDate();
        initAffirmContentLayout();
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
                reportContextAdapter = new ReportContextAdapter(this, wDataBean.getChecklogs());
                mListView.setAdapter(reportContextAdapter);
            }
        } catch (Exception e) {
            Log.e("我的详情 加载数据", e.toString());
        }
    }

    //初始化 待确认列表界面
    private void initAffirmContentLayout() {
        affirmContentLayout = View.inflate(this, R.layout.user_context_affirm_menu, null);
        affirm_cancel = (TextView) affirmContentLayout.findViewById(R.id.affirm_cancel);
        affirm_submit = (TextView) affirmContentLayout.findViewById(R.id.affirm_submit);
        affirm_cancel.setOnClickListener(this);
        affirm_submit.setOnClickListener(this);

        menuContentLayout = View.inflate(this, R.layout.user_context_appeal_menu, null);
        menu_edit = (EditText) menuContentLayout.findViewById(R.id.menu_edit);
        guan_cancel = (TextView) menuContentLayout.findViewById(R.id.guan_cancel);
        guan_submit = (TextView) menuContentLayout.findViewById(R.id.guan_submit);
        guan_cancel.setOnClickListener(this);
        guan_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.context_operation_affirm://主页确认
                //将布局设置给Dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.setContentView(affirmContentLayout);
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    //获得屏幕看都，并传给dialog
                    dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
                }
                break;
            case R.id.affirm_cancel://确认取消
                dialog.dismiss();
                break;
            case R.id.affirm_submit://确认确认
                glUpload.submitUpload(ReportContextActivity.this, dialog, wDataBean.getId());
                break;
            case R.id.context_operation_appeal://主页申诉
                //将布局设置给Dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.setContentView(menuContentLayout);
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
                }
                break;
            case R.id.context_operation_evaluate://主页审核
                break;
            case R.id.guan_cancel://申诉取消
                dialog.dismiss();
                break;
            case R.id.guan_submit://申诉提交
                if (menu_edit.getText().toString().equals("")) {
                    Toast.makeText(ReportContextActivity.this, "申诉内容不能为空", Toast.LENGTH_LONG).show();
                } else {
                    glUpload.appealUpload(ReportContextActivity.this, dialog, wDataBean.getId(), menu_edit.getText().toString(),
                            wDataBean.getChecklogs().get(wDataBean.getChecklogs().size() - 1).getId());
                }
                break;
            default:
                break;
        }
    }
}

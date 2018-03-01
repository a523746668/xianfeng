package com.qingyii.hxt;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.circle.BigPhotoFragment;
import com.qingyii.hxt.circle.NonScrollGridView;
import com.qingyii.hxt.circle.PhotoAdapter;
import com.qingyii.hxt.httpway.GLUpload;
import com.qingyii.hxt.pojo.MyUserList;
import com.qingyii.hxt.pojo.UserContext;

import java.util.ArrayList;
import java.util.List;

public class UserContextActivity extends AbBaseActivity implements View.OnClickListener {
    private TextView activity_tltle_name;

    private GLUpload glUpload = GLUpload.getGLUpload();
    private Intent intent;
    private String uiParameter;
    private UserContext.DataBean uDataBean;
    private MyUserList.DataBean mDataBean;

    private TextView context_main_content;
    private TextView context_main_time;
    private TextView context_main_integral;
    private TextView context_main_gist;
    //底部菜单
    private Dialog dialog;
    private View menuContentLayout;
    private EditText menu_edit;
    private TextView guan_cancel;
    private TextView guan_submit;

    private View evaluateContentLayout;
    private ImageView evaluate_minus;
    private TextView evaluate_number;
    private int evaluate_grade = 0;
    private ImageView evaluate_add;
    private EditText evaluate_edit;
    private TextView evaluate_cancel;
    private TextView evaluate_submit;

    private View affirmContentLayout;
    private TextView affirm_cancel;
    private TextView affirm_submit;

    private NonScrollGridView context_main_photo;
    private ArrayList<String> photos = new ArrayList<String>();
    private PhotoAdapter photoAdapter = new PhotoAdapter(this, photos);

    private TextView context_operation_affirm;
    private TextView context_operation_evaluate;
    private TextView context_operation_appeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_context);
        //tltle设置
        intent = getIntent();
        uiParameter = intent.getStringExtra("UIparameter");
        Log.e("UserContextUI", uiParameter);
        if (uiParameter.equals("My"))
            mDataBean = intent.getParcelableExtra("UserContext");
        else if (uiParameter.equals("Manage"))
            uDataBean = intent.getParcelableExtra("UserContext");

        activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
        activity_tltle_name.setText(intent.getStringExtra("tltleName"));
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        context_main_content = (TextView) findViewById(R.id.context_main_content);
        context_main_time = (TextView) findViewById(R.id.context_main_time);
        context_main_integral = (TextView) findViewById(R.id.context_main_integral);
        context_main_gist = (TextView) findViewById(R.id.context_main_gist);
        context_main_photo = (NonScrollGridView) findViewById(R.id.context_main_photo);
        context_operation_affirm = (TextView) findViewById(R.id.context_operation_affirm);
        context_operation_evaluate = (TextView) findViewById(R.id.context_operation_evaluate);
        context_operation_appeal = (TextView) findViewById(R.id.context_operation_appeal);

        context_main_photo.setAdapter(photoAdapter);

        switch (uiParameter) {
            case "My":
                context_operation_affirm.setVisibility(View.VISIBLE);
                context_operation_evaluate.setVisibility(View.GONE);
                context_operation_appeal.setVisibility(View.VISIBLE);

                //底部弹窗布局
                menuContentLayout = View.inflate(this, R.layout.user_context_appeal_menu, null);
                menu_edit = (EditText) menuContentLayout.findViewById(R.id.menu_edit);
                guan_cancel = (TextView) menuContentLayout.findViewById(R.id.guan_cancel);
                guan_submit = (TextView) menuContentLayout.findViewById(R.id.guan_submit);
                guan_cancel.setOnClickListener(this);
                guan_submit.setOnClickListener(this);

                affirmContentLayout = View.inflate(this, R.layout.user_context_affirm_menu, null);
                affirm_cancel = (TextView) affirmContentLayout.findViewById(R.id.affirm_cancel);
                affirm_submit = (TextView) affirmContentLayout.findViewById(R.id.affirm_submit);
                affirm_cancel.setOnClickListener(this);
                affirm_submit.setOnClickListener(this);

                initMyDate();
                break;
            case "Manage":
                context_operation_affirm.setVisibility(View.GONE);
                context_operation_evaluate.setVisibility(View.VISIBLE);
                context_operation_appeal.setVisibility(View.GONE);

                //底部弹窗布局
                evaluateContentLayout = View.inflate(this, R.layout.user_context_evaluate_menu, null);
                evaluate_minus = (ImageView) evaluateContentLayout.findViewById(R.id.evaluate_minus);
                evaluate_number = (TextView) evaluateContentLayout.findViewById(R.id.evaluate_number);
                evaluate_add = (ImageView) evaluateContentLayout.findViewById(R.id.evaluate_add);
                evaluate_edit = (EditText) evaluateContentLayout.findViewById(R.id.evaluate_edit);
                evaluate_cancel = (TextView) evaluateContentLayout.findViewById(R.id.evaluate_cancel);
                evaluate_submit = (TextView) evaluateContentLayout.findViewById(R.id.evaluate_submit);
                evaluate_minus.setOnClickListener(this);
                evaluate_add.setOnClickListener(this);
                evaluate_cancel.setOnClickListener(this);
                evaluate_submit.setOnClickListener(this);

                initManageDate();
                break;
            default:
                break;
        }

        //底部弹窗样式设置
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);

        context_operation_affirm.setOnClickListener(this);
        context_operation_evaluate.setOnClickListener(this);
        context_operation_appeal.setOnClickListener(this);

    }

    private void initMyDate() {
        context_main_content.setText(mDataBean.getContent());
        context_main_time.setText(mDataBean.getCreated_at());
        //内容图片
        MyUserList.DataBean.ChecklogBean images = mDataBean.getChecklog();

        if (images != null) {
//            for (int size = 0; size < images.size(); size++) {
//                photos.add(images.get(size).getUri());
//                if (photos.size() >= 9) {
//                    break;
//                }
//            }
            photos.add(images.getReasonpic());
            photoAdapter.notifyDataSetChanged();
        }
        context_main_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                BigPhotoFragment frag = BigPhotoFragment.getInstance(photos, arg2);
                frag.show(UserContextActivity.this.getSupportFragmentManager(), "BigPhotoFragment");
            }
        });
    }


    private void initManageDate() {
        context_main_content.setText(uDataBean.getContent());
        context_main_time.setText(uDataBean.getCreated_at());
        //内容图片
        List<UserContext.DataBean.PictureBean> images = uDataBean.getPicture();

        if (images != null) {
            for (int size = 0; size < images.size(); size++) {
                photos.add(images.get(size).getUri());
                if (photos.size() >= 9) {
                    break;
                }
            }
            photoAdapter.notifyDataSetChanged();
        }
        context_main_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                BigPhotoFragment frag = BigPhotoFragment.getInstance(photos, arg2);
                frag.show(UserContextActivity.this.getSupportFragmentManager(), "BigPhotoFragment");
            }
        });
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
                glUpload.submitUpload(UserContextActivity.this, dialog, mDataBean.getId());
                break;
            case R.id.context_operation_evaluate://主页审核
                //将布局设置给Dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.setContentView(evaluateContentLayout);
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
                }
                break;
            case R.id.evaluate_minus://审核减分
                if (evaluate_grade > -5)
                    evaluate_grade--;
                else
                    Toast.makeText(UserContextActivity.this, "已到上限", Toast.LENGTH_SHORT).show();
                evaluate_number.setText(evaluate_grade + "");
                break;
            case R.id.evaluate_add://审核加分
                if (evaluate_grade < 5)
                    evaluate_grade++;
                else
                    Toast.makeText(UserContextActivity.this, "已到上限", Toast.LENGTH_SHORT).show();
                evaluate_number.setText(evaluate_grade + "");
                break;
            case R.id.evaluate_cancel://审核取消
                dialog.dismiss();
                break;
            case R.id.evaluate_submit://审核提交
                if (evaluate_edit.getText().toString().equals("")) {
                    Toast.makeText(UserContextActivity.this, "审核内容不能为空", Toast.LENGTH_LONG).show();
                } else {
                    glUpload.auditUpload(UserContextActivity.this, dialog, uDataBean.getId(),
                            evaluate_grade / 10 + "." + Math.abs(evaluate_grade % 10), evaluate_edit.getText().toString(), "");
                }
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
            case R.id.guan_cancel://申诉取消
                dialog.dismiss();
                break;
            case R.id.guan_submit://申诉提交
                if (menu_edit.getText().toString().equals("")) {
                    Toast.makeText(UserContextActivity.this, "申诉内容不能为空", Toast.LENGTH_LONG).show();
                } else {
//                    glUpload.appealUpload(UserContextActivity.this, dialog, mDataBean.getId(),menu_edit.getText().toString());
                }
                break;
            default:
                break;
        }
    }
}

package com.qingyii.hxt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.pojo.TrainCourseList;
import com.qingyii.hxt.pojo.TrainList;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import static com.qingyii.hxt.QrCodeSignInActivity.REQUEST_CODE;

public class ScheduleActivity extends AbBaseActivity {
    private Intent intent;
    private TrainList.DataBean tDataBean;
    private TrainCourseList.DataBean tcDataBean;

    private TextView schedule_name;
    private TextView schedule_time;
    private TextView schedule_teacher;
    private TextView schedule_context;
    private RelativeLayout schedule_signin;
    private RelativeLayout schedule_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        intent = getIntent();
        tDataBean = intent.getParcelableExtra("training");
        tcDataBean = intent.getParcelableExtra("schedule");

        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("课程");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(view -> finish());

        schedule_name = (TextView) findViewById(R.id.schedule_name);
        schedule_time = (TextView) findViewById(R.id.schedule_time);
        schedule_teacher = (TextView) findViewById(R.id.schedule_teacher);
        schedule_context = (TextView) findViewById(R.id.schedule_context);
        schedule_signin = (RelativeLayout) findViewById(R.id.schedule_signin);
        schedule_download = (RelativeLayout) findViewById(R.id.schedule_download);

        this.loadDatas(savedInstanceState);
        this.onClick();
    }

    private void loadDatas(Bundle savedInstanceState) {
        schedule_name.setText(tcDataBean.getCoursename());
        schedule_time.setText("时间 : " + tcDataBean.getBegintime() + "至" + tcDataBean.getEndtime());
        schedule_teacher.setText("主讲 : " + tcDataBean.getTeachers());
        schedule_context.setText(tcDataBean.getDescription());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Intent intent = new Intent(this, QrCodeSignInActivity.class);
                    if (getIntent().hasExtra(QrCodeSignInActivity.TITLE))
                        intent.putExtra(QrCodeSignInActivity.TITLE, getIntent().getStringExtra(QrCodeSignInActivity.TITLE));
                    else
                        intent.putExtra(QrCodeSignInActivity.TITLE, getString(R.string.sign_in_details));
                    intent.putExtra("webUrl", result.toString());
                    startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void onClick() {
        schedule_signin.setOnClickListener(view -> {
            intent = new Intent(ScheduleActivity.this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
        schedule_download.setOnClickListener(view -> {
            intent = new Intent(ScheduleActivity.this, TrainDownloadActivity.class);
            intent.putExtra("training", tDataBean);
            intent.putExtra("schedule", tcDataBean);
            intent.putExtra("tltle", "课件下载");
            startActivity(intent);
//                Intent intent = new Intent(ScheduleActivity.this, TrainCoursewareActivity.class);
//                intent.putExtra("training", tDataBean);
//                intent.putExtra("schedule", tcDataBean);
//                startActivity(intent);
        });
    }
}

package com.qingyii.hxt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.DocumentaryMy;
import com.qingyii.hxt.pojo.IntegralDetails;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

public class IntegralDetailsActivity extends AppCompatActivity {
    private TextView activity_tltle_name;
    private Intent intent;

    private DocumentaryMy.DataBean dDataBean;
    private int userID;
    private int level;
    private String userName;

    private TextView integral_details_time;
    private TextView integral_details_all;
    private TextView integral_details_resumption;
    private TextView integral_details_activity;
    private TextView integral_details_integrity;
    private TextView integral_details_study;
    private ImageView guan_li_in_head_star[] = new ImageView[5];
    private TextView guan_li_in_head_no;
    private TextView integral_details_remark;
    private ImageView integral_details_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_details);
        //tltle设置
        activity_tltle_name = (TextView) findViewById(R.id.activity_tltle_name);
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        dDataBean = intent.getParcelableExtra("dDataBean");
        userID = intent.getIntExtra("UserID", 0);
        level = intent.getIntExtra("Level", 0);
        userName = intent.getStringExtra("userName");

        activity_tltle_name.setText(userName + "的积分详情");

        integral_details_time = (TextView) findViewById(R.id.integral_details_time);
        integral_details_all = (TextView) findViewById(R.id.integral_details_all);
        integral_details_resumption = (TextView) findViewById(R.id.integral_details_resumption);
        integral_details_activity = (TextView) findViewById(R.id.integral_details_activity);
        integral_details_integrity = (TextView) findViewById(R.id.integral_details_integrity);
        integral_details_study = (TextView) findViewById(R.id.integral_details_study);
        guan_li_in_head_star[0] = (ImageView) findViewById(R.id.guan_li_in_head_star1);
        guan_li_in_head_star[1] = (ImageView) findViewById(R.id.guan_li_in_head_star2);
        guan_li_in_head_star[2] = (ImageView) findViewById(R.id.guan_li_in_head_star3);
        guan_li_in_head_star[3] = (ImageView) findViewById(R.id.guan_li_in_head_star4);
        guan_li_in_head_star[4] = (ImageView) findViewById(R.id.guan_li_in_head_star5);
        guan_li_in_head_no = (TextView) findViewById(R.id.guan_li_in_head_no);
        integral_details_remark = (TextView) findViewById(R.id.integral_details_remark);
        integral_details_image = (ImageView) findViewById(R.id.integral_details_image);

        judgeStars();
        getDateList();
    }

    private void judgeStars() {
        int i = 0;
        Log.e("星级：", level + "");
        for (; i < level; i++)
            guan_li_in_head_star[i].setVisibility(View.VISIBLE);
        for (; i < guan_li_in_head_star.length; i++)
            guan_li_in_head_star[i].setVisibility(View.GONE);

        //无星标识显示
        if (level > 0)
            guan_li_in_head_no.setVisibility(View.GONE);
        else
            guan_li_in_head_no.setVisibility(View.VISIBLE);
    }

    public void getDateList() {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl() + "/score/detail/" + dDataBean.getCurr_month())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("user_id", userID + "")
                .build()
                .execute(new IntegralDetailsCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("IntegralDetails_onError", e.toString());
                                 Toast.makeText(IntegralDetailsActivity.this, "网络连接已断开", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(final IntegralDetails response, int id) {
                                 Log.e("IntegralDetailsCallback", response.getError_msg());

                                 try {
                                     switch (response.getError_code()) {
                                         case 0:
                                             integral_details_time.setText("截至  " + dDataBean.getCurr_month());
                                             integral_details_all.setText(response.getData().getTotal() + "");
                                             integral_details_resumption.setText(response.getData().getScore().get作用积分() + "");
                                             integral_details_activity.setText(response.getData().getScore().get纪律积分() + "");
                                             integral_details_integrity.setText(response.getData().getScore().get品德积分() + "");
                                             integral_details_study.setText(response.getData().getScore().get政治积分() + "");
                                             if (response.getData().getReason().equals(""))
                                                 integral_details_remark.setText(response.getData().getReason() + "");
                                             else
                                                 integral_details_remark.setText("   无");
                                             switch (response.getData().getPunishtype()) {
                                                 case "talk":
                                                     integral_details_image.setBackgroundResource(R.mipmap.guan_li_warn_talk);
                                                     break;
                                                 case "orange":
                                                     integral_details_image.setBackgroundResource(R.mipmap.guan_li_warn_orange);
                                                     break;
                                                 case "yellow":
                                                     integral_details_image.setBackgroundResource(R.mipmap.guan_li_warn_yellow);
                                                     break;
                                                 case "red":
                                                     integral_details_image.setBackgroundResource(R.mipmap.guan_li_warn_red);
                                                     break;
                                                 default:
                                                     break;
                                             }
                                             break;
                                         default:
                                             Toast.makeText(IntegralDetailsActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                             break;
                                     }
                                     if (!response.getData().getPunishtype().equals(""))
                                         integral_details_image.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 intent = new Intent(IntegralDetailsActivity.this, GuanLiWarnActivity.class);
                                                 intent.putExtra("iDataBean", response.getData());
                                                 intent.putExtra("userName",userName);
                                                 startActivity(intent);
                                             }
                                         });
                                 } catch (Exception e) {
                                     Log.e("IntegralDetailsExcep", e.toString());
                                 }
                             }
                         }
                );
    }

    private abstract class IntegralDetailsCallback extends Callback<IntegralDetails> {

        @Override
        public IntegralDetails parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("积分详情 JSON", result);
            IntegralDetails integralDetails = new Gson().fromJson(result, IntegralDetails.class);
            return integralDetails;
        }
    }
}

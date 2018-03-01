package com.qingyii.hxt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.DocumentaryMy;
import com.qingyii.hxt.pojo.IntegralDetails;
import com.qingyii.hxt.view.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

public class GuanLiWarnActivity extends AppCompatActivity {
    private TextView activity_tltle_name;
    private String userName;
    private Intent intent;
    private DocumentaryMy.DataBean dDataBean;
    private IntegralDetails.DataBean iDataBean;

    private RoundedImageView warn_head_photo;
    private TextView warn_head_remark;
    private TextView warn_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_li_warn);
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
        iDataBean = intent.getParcelableExtra("iDataBean");
        userName = intent.getStringExtra("userName");

        initDate();
    }

    private void initDate() {
        warn_head_photo = (RoundedImageView) findViewById(R.id.warn_head_photo);
        warn_head_remark = (TextView) findViewById(R.id.warn_head_remark);
        warn_username = (TextView) findViewById(R.id.warn_username);
        if (dDataBean != null) {
            activity_tltle_name.setText("我的纪实");
            getDateList();
        } else if (iDataBean != null) {
            activity_tltle_name.setText("纪实查询");
            warn_head_remark.setText("" + iDataBean.getReason() + "");
            warn_username.setText(userName+"");
            switch (iDataBean.getPunishtype()) {
                case "talk":
                    warn_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_talk);
                    break;
                case "orange":
                    warn_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_orange);
                    break;
                case "yellow":
                    warn_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_yellow);
                    break;
                case "red":
                    warn_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_red);
                    break;
                default:
                    break;
            }
        }
    }

    public void getDateList() {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getManageListUrl() + "/score/detail/" + dDataBean.getCurr_month())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new IntegralDetailsCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("IntegralDetails_onError", e.toString());
                                 Toast.makeText(GuanLiWarnActivity.this, "网络异常—我的纪实数据", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(IntegralDetails response, int id) {
                                 Log.e("IntegralDetailsCallback", response.getError_msg());

                                 try {
                                     switch (response.getError_code()) {
                                         case 0:
                                             warn_head_remark.setText("评语：" + response.getData().getReason() + "");
                                             warn_username.setText(dDataBean.getTruename()+"");
                                             switch (response.getData().getPunishtype()) {
                                                 case "talk":
                                                     warn_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_talk);
                                                     break;
                                                 case "orange":
                                                     warn_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_orange);
                                                     break;
                                                 case "yellow":
                                                     warn_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_yellow);
                                                     break;
                                                 case "red":
                                                     warn_head_photo.setBackgroundResource(R.mipmap.guan_li_warn_red);
                                                     break;
                                                 default:
                                                     break;
                                             }
                                             break;
                                         default:
                                             break;
                                     }
                                 } catch (Exception e) {

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

package com.qingyii.hxt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.ManageCheckState;
import com.qingyii.hxt.pojo.MyCheckState;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

public class GuanLiActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView activity_tltle_name;
    private TextView guanli_time;
    private ImageButton guanli_time_last;
    private ImageButton guanli_time_next;

    private Intent intent;
    private String uiParameter;

    private LinearLayout guanli_manage_ll;
    private TextView manage_release_num;
    private TextView manage_log_num;

    private LinearLayout guanli_user_ll;
    private LinearLayout user_log;
    private TextView user_log_num;
    private LinearLayout user_month_credits;
    private TextView user_month_credits_num;
    private LinearLayout user_all_credits;
    private TextView user_all_credits_num;

    private ImageView guanli_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_li);
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
        uiParameter = intent.getStringExtra("UIparameter");
        Log.e("GuanLiUI", uiParameter);

        //获得当地时间,并转化为String
        Date date = new Date();
        Log.e("日期", new SimpleDateFormat("MM").format(date));
        year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        //防止报错

        guanli_time = (TextView) findViewById(R.id.guanli_time);//时间
        guanli_time_last = (ImageButton) findViewById(R.id.guanli_time_last);//上个月
        guanli_time_next = (ImageButton) findViewById(R.id.guanli_time_next);//下个月

        guanli_manage_ll = (LinearLayout) findViewById(R.id.guanli_manage_ll);//管理员展示区域
        manage_release_num = (TextView) findViewById(R.id.manage_release_num);//发布人数
        manage_log_num = (TextView) findViewById(R.id.manage_log_num);//日志总篇幅

        guanli_user_ll = (LinearLayout) findViewById(R.id.guanli_user_ll);//普通用户展示区域
        user_log = (LinearLayout) findViewById(R.id.user_log);//日志总篇幅
        user_log_num = (TextView) findViewById(R.id.user_log_num);
        user_month_credits = (LinearLayout) findViewById(R.id.user_month_credits);//月积分
        user_month_credits_num = (TextView) findViewById(R.id.user_month_credits_num);
        user_all_credits = (LinearLayout) findViewById(R.id.user_all_credits);//总积分
        user_all_credits_num = (TextView) findViewById(R.id.user_all_credits_num);

        guanli_button = (ImageView) findViewById(R.id.guanli_button);

        if (uiParameter.equals("My")) {
            guanli_manage_ll.setVisibility(View.GONE);
            guanli_user_ll.setVisibility(View.VISIBLE);
            this.myCheckState(lastData());
        } else if (uiParameter.equals("Manage")) {
            guanli_manage_ll.setVisibility(View.VISIBLE);
            guanli_user_ll.setVisibility(View.GONE);
            this.manageCheckState(lastData());
        }

        guanli_time_last.setOnClickListener(this);
        guanli_time_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guanli_time_last:
                switch (uiParameter) {
                    case "My":
                        this.myCheckState(lastData());
                        break;
                    case "Manage":
                        this.manageCheckState(lastData());
                        break;
                    case "Max":
                        this.manageCheckState(lastData());
                        break;
                    default:
                        break;
                }
                break;
            case R.id.guanli_time_next:
                switch (uiParameter) {
                    case "My":
                        this.myCheckState(nextData());
                        break;
                    case "Manage":
                        this.manageCheckState(nextData());
                        break;
                    case "Max":
                        this.manageCheckState(nextData());
                        break;
                    default:
                        break;
                }
                break;
            case R.id.guanli_manage_ll:
                intent = new Intent(GuanLiActivity.this, ManageListActivity.class);
//                intent.putExtra("simpleDate", simpleDate);
                intent.putExtra("UIparameter", uiParameter);
                startActivity(intent);
                break;
            case R.id.user_log:
                intent = new Intent(GuanLiActivity.this, UserListActivity.class);
//                intent.putExtra("simpleDate", simpleDate);
                intent.putExtra("UIparameter", uiParameter);
                startActivity(intent);
                break;
            case R.id.guanli_button:
                //判断进入页面
                switch (uiParameter) {
                    case "My":
                        intent = new Intent(GuanLiActivity.this, UserListActivity.class);
                        break;
                    case "Manage":
                        intent = new Intent(GuanLiActivity.this, ManageListActivity.class);
                        break;
                    case "Max":
                        intent = new Intent(GuanLiActivity.this, ManageListActivity.class);
                        break;
                    default:
                        break;
                }
                intent.putExtra("UIparameter", uiParameter);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 审核员 审核状态
     */
    private void manageCheckState(final String thisDate) {
        OkHttpUtils
                .get()
                .url(XrjHttpClient.getManageListUrl() + "/" + toStringDate() + "/check")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new ManageCheckStateCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("审核员的审核状态_onError", e.toString());
                                 Toast.makeText(GuanLiActivity.this, "查询上限", Toast.LENGTH_SHORT).show();
                                 if (last_ro_next)
                                     MyApplication.hxt_setting_config.edit().putString("ManageDate", lastData()).commit();
                                 else
                                     MyApplication.hxt_setting_config.edit().putString("ManageDate", nextData()).commit();
                             }

                             @Override
                             public void onResponse(ManageCheckState response, int id) {
                                 Log.e("审核员的审核状态Callback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         MyApplication.hxt_setting_config.edit().putString("ManageDate", thisDate).commit();
                                         activity_tltle_name.setText(toStringDate());
                                         guanli_time.setText(toStringDate());

                                         manage_log_num.setText(response.getData().getDocs_cnt() + "");
                                         manage_release_num.setText(response.getData().getPeople_cnt() + "");
                                         switch (response.getData().getStatus()) {
                                             case "start":
                                                 guanli_button.setBackgroundResource(R.mipmap.guan_start);
                                                 guanli_button.setOnClickListener(GuanLiActivity.this);
                                                 guanli_manage_ll.setOnClickListener(GuanLiActivity.this);
                                                 break;
                                             case "checking":
                                                 guanli_button.setBackgroundResource(R.mipmap.guan_ing);
                                                 guanli_button.setOnClickListener(GuanLiActivity.this);
                                                 guanli_manage_ll.setOnClickListener(GuanLiActivity.this);
                                                 break;
                                             case "continue":
                                                 guanli_button.setBackgroundResource(R.mipmap.guan_continue);
                                                 guanli_button.setOnClickListener(GuanLiActivity.this);
                                                 guanli_manage_ll.setOnClickListener(GuanLiActivity.this);
                                                 break;
                                             case "filed":
                                                 guanli_button.setBackgroundResource(R.mipmap.guan_ed);
                                                 break;
                                             default:
                                                 guanli_button.setBackgroundResource(R.mipmap.guan_ed);
                                                 break;
                                         }
                                         break;
                                     default:
                                         Toast.makeText(GuanLiActivity.this, "查询上限", Toast.LENGTH_SHORT).show();
                                         if (last_ro_next)
                                             MyApplication.hxt_setting_config.edit().putString("ManageDate", lastData()).commit();
                                         else
                                             MyApplication.hxt_setting_config.edit().putString("ManageDate", nextData()).commit();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class ManageCheckStateCallback extends Callback<ManageCheckState> {
        @Override
        public ManageCheckState parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("审核员的审核状态_String", result);
            ManageCheckState manageCheckState = new Gson().fromJson(result, ManageCheckState.class);
            return manageCheckState;
        }
    }

    /**
     * 我的 审核状态
     */
    private void myCheckState(final String thisDate) {
        OkHttpUtils
                .get()
                .url(XrjHttpClient.getManageListUrl() + "/" + thisDate + "/my")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new MyCheckStateCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("MyCheckState_onError", e.toString());
                                 Toast.makeText(GuanLiActivity.this, "查询上限", Toast.LENGTH_SHORT).show();
                                 if (last_ro_next)
                                     MyApplication.hxt_setting_config.edit().putString("ManageDate", lastData()).commit();
                                 else
                                     MyApplication.hxt_setting_config.edit().putString("ManageDate", nextData()).commit();
                             }

                             @Override
                             public void onResponse(MyCheckState response, int id) {
//                                 Log.e("MyCheckStateCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         MyApplication.hxt_setting_config.edit().putString("ManageDate", thisDate).commit();
                                         activity_tltle_name.setText(toStringDate());
                                         guanli_time.setText(toStringDate());

                                         user_log_num.setText(response.getData().getCount() + "");
                                         response.getData().getStars();
                                         user_month_credits_num.setText(response.getData().getScore() + "");
                                         user_all_credits_num.setText(response.getData().getTotal() + "");
                                         switch (response.getData().getStatus()) {
                                             case "start":
                                                 guanli_button.setBackgroundResource(R.mipmap.guan_start);
                                                 guanli_button.setOnClickListener(GuanLiActivity.this);
                                                 user_log.setOnClickListener(GuanLiActivity.this);
                                                 break;
                                             case "checking":
                                                 guanli_button.setBackgroundResource(R.mipmap.guan_ing);
                                                 guanli_button.setOnClickListener(GuanLiActivity.this);
                                                 user_log.setOnClickListener(GuanLiActivity.this);
                                                 break;
                                             case "filed":
                                                 guanli_button.setBackgroundResource(R.mipmap.guan_ed);
                                                 break;
                                             default:
                                                 guanli_button.setBackgroundResource(R.mipmap.guan_ed);
                                                 break;
                                         }
                                         break;
                                     default:
                                         Toast.makeText(GuanLiActivity.this, "查询上限", Toast.LENGTH_SHORT).show();
                                         if (last_ro_next)
                                             MyApplication.hxt_setting_config.edit().putString("ManageDate", lastData()).commit();
                                         else
                                             MyApplication.hxt_setting_config.edit().putString("ManageDate", nextData()).commit();
                                         break;
                                 }
                             }
                         }
                );

    }

    private abstract class MyCheckStateCallback extends Callback<MyCheckState> {
        @Override
        public MyCheckState parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("MyCheckState_String", result);
            MyCheckState myCheckState = new Gson().fromJson(result, MyCheckState.class);
            return myCheckState;
        }
    }

    private int year;
    private int month;
    private boolean last_ro_next = false;

    //上一个月
    private String lastData() {
        if (month == 1) {
            year--;
            month = 12;
        } else
            month--;
        //调整查询上月或者下月的状态 在请求时，存储数据
        last_ro_next = false;
        return this.toStringDate();
    }

    //下一个月
    private String nextData() {
        if (month == 12) {
            year++;
            month = 1;
        } else
            month++;
        //调整查询上月或者下月的状态 在请求时，存储数据
        last_ro_next = true;
        return this.toStringDate();
    }

    //数字返回字符串行数据
    private String toStringDate() {
        if (9 < month && month <= 12) {
            return year + "-" + month;
        } else {
            return year + "-0" + month;
        }
    }
}
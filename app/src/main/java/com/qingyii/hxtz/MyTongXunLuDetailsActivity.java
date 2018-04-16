package com.qingyii.hxtz;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.circle.ShiGuangZhou;
import com.qingyii.hxtz.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.httpway.HandleParameterCallback;
import com.qingyii.hxtz.pojo.AddressList;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.qingyii.hxtz.pojo.UserParameter;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;
import com.qingyii.hxtz.view.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

public class MyTongXunLuDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private AddressList.DataBean aDataBean;
    private HomeInfo.AccountBean moduletitle;
    private UserParameter.DataBean uDataBean;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private RoundedImageView txl_details_header;
    private TextView txl_details_name;
    private TextView txl_details_unit;
    private TextView txl_details_phone;
    private ImageView txl_details_emblem;
    private TextView txl_details_duty;
    private ImageView unit_level_star1[] = new ImageView[5];
    private TextView unit_level_star1_no;

    private TextView txl_details_shiguangzhou;
    //private RelativeLayout txl_details_jishiguanli;

    private TextView txl_details_call;
    private TextView txl_details_attention;
    //弹窗
    private Dialog dialog;
    //确认弹窗
    private View affirmContentLayout;
    private TextView affirm_context;
    private TextView affirm_cancel;
    private TextView affirm_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tong_xun_lu_details);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("详情资料");
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
        aDataBean = intent.getParcelableExtra("aDataBean");
        moduletitle = intent.getParcelableExtra("moduletitle");

        txl_details_header = (RoundedImageView) findViewById(R.id.txl_details_header);
        txl_details_name = (TextView) findViewById(R.id.txl_details_name);
        txl_details_unit = (TextView) findViewById(R.id.txl_details_unit);
        txl_details_phone = (TextView) findViewById(R.id.txl_details_phone);
        txl_details_emblem = (ImageView) findViewById(R.id.txl_details_emblem);
        txl_details_duty = (TextView) findViewById(R.id.txl_details_duty);
        unit_level_star1[0] = (ImageView) findViewById(R.id.unit_level_star1);
        unit_level_star1[1] = (ImageView) findViewById(R.id.unit_level_star2);
        unit_level_star1[2] = (ImageView) findViewById(R.id.unit_level_star3);
        unit_level_star1[3] = (ImageView) findViewById(R.id.unit_level_star4);
        unit_level_star1[4] = (ImageView) findViewById(R.id.unit_level_star5);
        unit_level_star1_no = (TextView) findViewById(R.id.unit_level_star1_no);

        txl_details_shiguangzhou = (TextView) findViewById(R.id.txl_details_shiguangzhou);
        //txl_details_jishiguanli = (RelativeLayout) findViewById(R.id.txl_details_jishiguanli);

        txl_details_call = (TextView) findViewById(R.id.txl_details_call);
        txl_details_attention = (TextView) findViewById(R.id.txl_details_attention);

        for (int i = 0; i < moduletitle.getModules().size(); i++)
            if (moduletitle.getModules().get(i).equals("documentary"))
                txl_details_shiguangzhou.setVisibility(View.VISIBLE);

        txl_details_shiguangzhou.setOnClickListener(this);
        //txl_details_jishiguanli.setOnClickListener(this);
        txl_details_call.setOnClickListener(this);
        txl_details_attention.setOnClickListener(this);

        otherUserRFI();
    }

    private void initDate() {
        ImageLoader.getInstance().displayImage(aDataBean.getAvatar(), txl_details_header, MyApplication.options, animateFirstListener);
        txl_details_name.setText(uDataBean.getTruename() + "");
        if (uDataBean.getDepartment() != null)
            txl_details_unit.setText("部门：" + uDataBean.getDepartment().getName());
        if (!uDataBean.getJobname().equals(""))
            txl_details_duty.setText("职务：" + uDataBean.getJobname());
        else
            txl_details_duty.setText("职务：无");
        txl_details_phone.setText("" + uDataBean.getPhone());
        if (uDataBean.getPolitical().equals("正式党员"))
            txl_details_emblem.setVisibility(View.VISIBLE);
        else
            txl_details_emblem.setVisibility(View.GONE);

        if (uDataBean.getIs_follow() == 1)
            txl_details_attention.setText("取消关注");
        else
            txl_details_attention.setText("关注");

//        int i = 0;
////        Log.e("列表星级：", rDataBean.getStars() + "");
//        for (; i < uDataBean.getStars(); i++)
//            unit_level_star1[i].setVisibility(NotifyListView.VISIBLE);
//
//        for (; i < unit_level_star1.length; i++)
//            unit_level_star1[i].setVisibility(NotifyListView.GONE);
//
//        //无星标识显示
//        if (uDataBean.getStars() > 0)
//            unit_level_star1_no.setVisibility(NotifyListView.GONE);
//        else
//            unit_level_star1_no.setVisibility(NotifyListView.VISIBLE);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txl_details_shiguangzhou://时光轴
                intent = new Intent(MyTongXunLuDetailsActivity.this, ShiGuangZhou.class);
                //传用户资料
//                intent.putExtra("User", createUser);
                intent.putExtra("UserId", uDataBean.getId());
                //System.out.println(aDocsBean.getUser_id()+"-----------");
                intent.putExtra("ShiGuangType", 0);
                MyTongXunLuDetailsActivity.this.startActivity(intent);

//                intent = new Intent(MyTongXunLuDetailsActivity.this, ShiGuangZhou.class);
//                startActivity(intent);
                break;
//            case R.id.txl_details_jishiguanli://纪实跳转
//                Toast.makeText(MyTongXunLuDetailsActivity.this, "敬请期待", Toast.LENGTH_LONG).show();
//                break;
            case R.id.txl_details_call://打电话
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    dialog.setContentView(initAffirmContentLayout());
                    affirm_context.setText("确定拨打：" + aDataBean.getPhone());
//                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().width = (int) (getWindowManager().getDefaultDisplay().getWidth() * 0.8);
                    dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                    dialog.show();
                }
                break;
            case R.id.affirm_cancel://打电话 取消
                dialog.dismiss();
                break;
            case R.id.affirm_submit://打电话 确认
                //用intent启动拨打电话
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + aDataBean.getPhone()));
                startActivity(intent);
                dialog.dismiss();
                break;
            case R.id.txl_details_attention://关注
                followRequest();
                break;
            default:
                break;
        }
    }

    public void otherUserRFI() {

        Log.e("aDataBean.getId()", aDataBean.getId() + "");

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getOtherUserUrl() + aDataBean.getId())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new UserCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("OtherCallback_onError", e.toString());
//                                 Toast.makeText(MyTongXunLuDetailsActivity.this, "网络异常--请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(UserParameter response, int id) {
                                 Log.e("OtherCallback", response.getError_msg() + "");
                                 switch (response.getError_code()) {
                                     case 0:
                                         uDataBean = response.getData();
                                         initDate();
                                         break;
                                     default:
                                         Toast.makeText(MyTongXunLuDetailsActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    public void followRequest() {

        String follow;
        if (uDataBean.getIs_follow() == 1)
            follow = "unfollow";
        else
            follow = "follow";

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getOtherUserUrl() + follow)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("user_id", uDataBean.getId() + "")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("OtherCallback_onError", e.toString());
//                                 Toast.makeText(MyTongXunLuDetailsActivity.this, "网络异常--请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("OtherCallback", response.getError_msg() + "");
                                 switch (response.getError_code()) {
                                     case 0:
                                         if (uDataBean.getIs_follow() == 1) {
                                             uDataBean.setIs_follow(0);
                                             Toast.makeText(MyTongXunLuDetailsActivity.this, "已取消", Toast.LENGTH_LONG).show();
                                         } else {
                                             uDataBean.setIs_follow(1);
                                             Toast.makeText(MyTongXunLuDetailsActivity.this, "已关注", Toast.LENGTH_LONG).show();
                                         }
                                         initDate();
                                         break;
                                     default:
                                         Toast.makeText(MyTongXunLuDetailsActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class UserCallback extends Callback<UserParameter> {
        @Override
        public UserParameter parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("Other_String", result);
            UserParameter user = new Gson().fromJson(result, UserParameter.class);
            return user;
        }
    }
}

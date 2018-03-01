package com.qingyii.hxt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.circle.ShiGuangZhou;
import com.qingyii.hxt.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.AddressUnitList;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;
import com.qingyii.hxt.view.RoundedImageView;
import com.zhf.MyshoucangActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 我的界面
 *
 * @author Administrator
 */
public class myActivity extends BaseActivity implements OnClickListener {
    private Intent intent;
    private ImageView iv_touxiang;
    private RoundedImageView head_photo;
    private ImageView head_emblem;
    private TextView tv_name;
    private TextView tv_section;
    private LinearLayout rl_gonggaoban;
    private RelativeLayout rl_myshujia;
    private RelativeLayout rl_mychengji;
    private RelativeLayout rl_myneikan;
    private RelativeLayout rl_myshoucang;
    private RelativeLayout rl_myshiguangzhou;
    private RelativeLayout rl_tongxunlu;
    private RelativeLayout rl_snezhi;

    private HomeInfo.AccountBean moduletitle;

//    private UserInfo.DataBean uDataBean = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    MyApplication myApplication = new MyApplication();

    private void goBack() {
        myApplication.finishActivity(myActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("我的");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(view -> goBack());

        intent = getIntent();
        moduletitle = intent.getParcelableExtra("moduletitle");

        myApplication.addActivity(this);

        addressUnitList();

        initUI();
        initData();
//        userInfo();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient
                .Builder(this)
                .addApi(AppIndex.API)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private void initUI() {
        iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
        head_photo = (RoundedImageView) findViewById(R.id.head_photo);
        head_emblem = (ImageView) findViewById(R.id.head_emblem);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_section = (TextView) findViewById(R.id.tv_section);
        rl_gonggaoban = (LinearLayout) findViewById(R.id.rl_gonggaoban);
        rl_myshujia = (RelativeLayout) findViewById(R.id.rl_myshujia);
        rl_mychengji = (RelativeLayout) findViewById(R.id.rl_mychengji);
        rl_myneikan = (RelativeLayout) findViewById(R.id.rl_myneikan);
        rl_myshoucang = (RelativeLayout) findViewById(R.id.rl_myshoucang);
        rl_myshiguangzhou = (RelativeLayout) findViewById(R.id.rl_myshiguangzhou);
        rl_tongxunlu = (RelativeLayout) findViewById(R.id.rl_tongxunlu);

        rl_snezhi = (RelativeLayout) findViewById(R.id.rl_snezhi);

        for (int i = 0; i < moduletitle.getModules().size(); i++){
                if(moduletitle.getModules().get(i).getMark().startsWith("task")) {
                    rl_myshoucang.setVisibility(View.VISIBLE);
                    continue;
                }
            switch (moduletitle.getModules().get(i).getMark()) {
                case "magazine":
                    rl_myneikan.setVisibility(View.VISIBLE);
                    rl_myshoucang.setVisibility(View.VISIBLE);
                    break;
                case "book":
                    rl_myshujia.setVisibility(View.VISIBLE);
                    break;
                case "exams":
                    rl_mychengji.setVisibility(View.VISIBLE);
                    break;
                case "documentary":
                    rl_myshiguangzhou.setVisibility(View.VISIBLE);
                    break;
            }

        }


        rl_myshiguangzhou.setOnClickListener(this);

        iv_touxiang.setOnClickListener(this);
        rl_myshujia.setOnClickListener(this);
        rl_mychengji.setOnClickListener(this);
        rl_myneikan.setOnClickListener(this);
        rl_myshoucang.setOnClickListener(this);
        rl_snezhi.setOnClickListener(this);
        rl_tongxunlu.setOnClickListener(this);

//        rl_gonggaoban.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyListView v) {
//                if (uDataBean!=null) {
//                    Intent it = new Intent(myActivity.this, myActivity1.class);
//                    it.putExtra("UserInfo", uDataBean);
//                    startActivity(it);
//                }else {
//                    Toast.makeText(myActivity.this,"网络异常",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//        returns_arrow.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyListView v) {
//                onBackPressed();
//
//            }
//        });

//        rl_rizhi.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyListView v) {
//                Intent it = new Intent(myActivity.this, rizhiActivity.class);
//                startActivity(it);
//
//            }
//        });
    }

    private void initData() {
        if (MyApplication.userUtil != null) {
            Log.i("TMDTOUXIANG",MyApplication.userUtil.getAvatar());
            ImageLoader.getInstance().displayImage(MyApplication.userUtil.getAvatar(),
                    head_photo, MyApplication.options, animateFirstListener);
            if (MyApplication.userUtil.getPolitical().equals("正式党员"))
                head_emblem.setVisibility(View.VISIBLE);
            else
                head_emblem.setVisibility(View.GONE);

            tv_name.setText(MyApplication.userUtil.getTruename());
            if (!MyApplication.userUtil.getJobname().equals(""))
                tv_section.setText(MyApplication.userUtil.getDepartment().getName() + "  " + MyApplication.userUtil.getJobname());
            else
                tv_section.setText(MyApplication.userUtil.getDepartment().getName());
        }
//        if (CacheUtil.user != null) {
//            tv_name.setText(CacheUtil.user.getName());
//        }
//        if (EmptyUtil.IsNotEmpty(CacheUtil.user.getPicaddress())) {
//            ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + CacheUtil.user.getPicaddress(), iv_touxiang);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_touxiang:
                if (MyApplication.userUtil != null)
//                    it.putExtra("UserInfo", uDataBean);
                    startActivity(new Intent(myActivity.this, myActivity1.class));
                else
                    Toast.makeText(myActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                break;
            case R.id.rl_myshujia:
                startActivity(new Intent(myActivity.this, shuJiaActivity.class));
                break;
            case R.id.rl_mychengji:
//                startActivity(new Intent(myActivity.this, kaochangActivity.class));
                startActivity(new Intent(myActivity.this, KaoChangType02Activity.class));
                break;
            case R.id.rl_myneikan:
                startActivity(new Intent(myActivity.this, neiKanActivity.class));
                break;
            case R.id.rl_myshoucang:
                startActivity(new Intent(myActivity.this, MyshoucangActivity.class));
                break;
            case R.id.rl_snezhi:
                startActivity(new Intent(myActivity.this, myShezhiActivity.class));
                break;
            case R.id.rl_myshiguangzhou:
                intent = new Intent();
                intent.putExtra("ShiGuangType", 1);
                intent.setClass(myActivity.this, ShiGuangZhou.class);
                startActivity(intent);
                break;
            case R.id.rl_tongxunlu:
                if (groupData.getData() != null) {
                    intent = new Intent(myActivity.this, MyTongXunLuActivity.class);
                    intent.putExtra("groupData", groupData);
                    intent.putExtra("moduletitle", moduletitle);
                    startActivity(intent);
                } else
                    Toast.makeText(myActivity.this, "未获得单位列表", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("my Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    /**
     * 单位录列表
     */
    private AddressUnitList groupData = null;

    public void addressUnitList() {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getAddressListUrl() + "/company")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new AddressUnitListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("AddressList_onError", e.toString());
                                 Toast.makeText(myActivity.this, "网络异常—请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(AddressUnitList response, int id) {
                                 Log.e("AddressListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         groupData = response;
                                         Log.e("groupData.size()", groupData.getData().size() + "");
                                         break;
                                     default:
                                         Toast.makeText(myActivity.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    private abstract class AddressUnitListCallback extends Callback<AddressUnitList> {

        @Override
        public AddressUnitList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("单位录列表 JSON", result);
            AddressUnitList addressUnitList = new Gson().fromJson(result, AddressUnitList.class);
            return addressUnitList;
        }
    }

    /**
     * 用户信息
     */
//    public void userInfo() {
//
//        OkHttpUtils
//                .get()
//                .url(XrjHttpClient.getUserUrl())
//                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
//                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
////                .addParams("id","0")
////                .addParams("direction","it")
//                //1. id为0或不传 ，返回最新的10条记录
//                //2. id = 10, direction = gt 返回id大于10的 10条记录
//                //3. id = 20, direction = lt 返回id小于20的10条记录
//                .build()
//                .execute(new UserInfoCallback() {
//                             @Override
//                             public void onError(Call call, Exception e, int id) {
//                                 Log.e("UserInfo_onError", e.toString());
//                                 Toast.makeText(myActivity.this, "网络异常—考试列表", Toast.LENGTH_LONG).show();
//                             }
//
//                             @Override
//                             public void onResponse(UserInfo response, int id) {
//                                 Log.e("UserInfoCallback", response.getError_msg());
//
//                                 switch (response.getError_code()) {
//                                     case 0:
//                                         uDataBean = response.getData();
//                                         tv_name.setText(response.getData().getNickname());
//                                         break;
//                                     default:
//                                         break;
//                                 }
//                             }
//                         }
//                );
//    }
//
//    private abstract class UserInfoCallback extends Callback<UserInfo> {
//
//        @Override
//        public UserInfo parseNetworkResponse(Response response, int id) throws Exception {
//            String result = response.body().string();
//            UserInfo userInfo = new Gson().fromJson(result, UserInfo.class);
//            return userInfo;
//        }
//    }
}

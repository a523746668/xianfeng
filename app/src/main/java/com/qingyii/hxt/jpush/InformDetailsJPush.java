package com.qingyii.hxt.jpush;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.google.gson.Gson;
import com.qingyii.hxt.home.mvp.ui.HomeActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.httpway.TZUpload;
import com.qingyii.hxt.notify.mvp.model.entity.NotifyList;
import com.qingyii.hxt.util.DateUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 通知详情
 */
public class InformDetailsJPush extends AbBaseActivity implements View.OnClickListener {
    private WebView wb_inform_content;
    private LinearLayout operation_ll;
    private TextView inform_check_time;
    private TextView inform_check;

    private Dialog bottomDialog;
    private View bottomSheet;

    private Intent intent;
    private NotifyList.DataBean iDataBean;
    private int informID;

    TZUpload tzUpload = TZUpload.getTZUpload();


    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_details);
        intent = getIntent();
        informID = intent.getIntExtra("ID", 0);
        getInform(informID);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("通知");
        LinearLayout returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bottomDialog = new Dialog(this, R.style.ActionSheetDialogStyle);

        //修改此条通知的状态 为已读
//        try {
//            SQLiteDatabase dbWrite = MyApplication.helper.getWritableDatabase();
//            dbWrite.execSQL("update Inform_info set mark = 1 where _id =" + iDataBean.getId());
//
////            Cursor c = dbWrite.query("Notice_info", null, "_id=?", new String[]{iDataBean.getId() + ""}, null, null, null);
////            c.moveToFirst();
////            Log.e("数据库", "mark：" + c.getInt(c.getColumnIndex("mark")));
//            dbWrite.close();
//        } catch (Exception e) {
//            Log.e("数据库", "通知修改错误：" + e.toString());
//        }
        operation_ll = (LinearLayout) findViewById(R.id.operation_ll);
        wb_inform_content = (WebView) findViewById(R.id.wb_notify_details_content);
        /**
         *
         * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
         * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
         * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
         * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
         * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
         * setSupportZoom 设置是否支持变焦
         * */
        wb_inform_content.getSettings().setBuiltInZoomControls(true);// 隐藏缩放按钮
        wb_inform_content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
        wb_inform_content.getSettings().setUseWideViewPort(true);// 可任意比例缩放
        wb_inform_content.getSettings().setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        wb_inform_content.getSettings().setSavePassword(true);
        wb_inform_content.getSettings().setSaveFormData(true);// 保存表单数据
        wb_inform_content.getSettings().setJavaScriptEnabled(true);
        wb_inform_content.getSettings().setGeolocationEnabled(true);// 启用地理定位
        wb_inform_content.getSettings().setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
        wb_inform_content.getSettings().setDomStorageEnabled(true);

        inform_check_time = (TextView) findViewById(R.id.notify_details_sign_time);
        inform_check = (TextView) findViewById(R.id.notify_details_sign);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startActivity(new Intent(this, HomeActivity.class));
    }

    private void loadUI() {
        String webUrl = "";
        final Map<String, String> extraHeaders = new HashMap<String, String>();

        extraHeaders.put("Accept", XrjHttpClient.ACCEPT_V2);
        extraHeaders.put("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));

        //load网页
        webUrl = XrjHttpClient.getInformListUrl() + "/" + iDataBean.getId();
//        webUrl = "http://v.youku.com/v_show/id_XMTgxMjc3MDIzMg==_ev_1.html?spm=a2hww.20020887.m_205902.5~5~5~5~5~5~5~A&from=y9.3-idx-uhome-1519-20887.205902.1-1&x=1";
        Log.e("通知详情网页连接", "UIR：" + webUrl);

        wb_inform_content.loadUrl(webUrl, extraHeaders);

        //WebView播放视频 方法实现
        wb_inform_content.setWebChromeClient(new WebChromeClient());
        //WebView点击事件依旧显示在本页面
        wb_inform_content.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url, extraHeaders);
                return true;
            }
        });

        if (iDataBean.getNotifytype().equals("system"))
            operation_ll.setVisibility(View.GONE);
        else
            operation_ll.setVisibility(View.VISIBLE);

        //time
//        inform_check_time.setText(iDataBean.getCreated_at());
        Log.e("通知详情有效时间", "Date：" + iDataBean.getReceipt_line());
        Log.e("通知详情当前时间", "Date：" + DateUtils.getDateLong());
        Log.e("通知详情有效时间", "Date：" + DateUtils.getDateToLongString(iDataBean.getReceipt_line()));
        if (iDataBean.getReceipt_line() > 0)
            inform_check_time.setText("截止时限：" + DateUtils.getDateToLongString(iDataBean.getReceipt_line()));

        //是否签到
        switch (iDataBean.getIs_receipt()) {
            case 0:
                if (iDataBean.getReceipt_line() < DateUtils.getDateLong() / 1000 && iDataBean.getReceipt_line() != 0) {
                    inform_check.setSelected(true);
                    inform_check.setText("已过期");
                } else {
                    inform_check.setSelected(false);
                    inform_check.setOnClickListener(this);
                }
                break;
            case 1:
                inform_check.setSelected(true);
                Log.e("通知签收状态", iDataBean.getReceipt_status() + "");
                if (iDataBean.getReceipt_status().equals("sign"))
                    inform_check.setText("已签收");
                else if (iDataBean.getReceipt_status().equals("rejected"))
                    inform_check.setText("已请假");
                break;
            default:
                break;
        }
    }

    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.inform_bottom_sheet_layout, (ViewGroup) getWindow().getDecorView(), false);
        Button inform_bottom_sheet_qx = (Button) view.findViewById(R.id.inform_bottom_sheet_qx);
        Button inform_bottom_sheet_qd = (Button) view.findViewById(R.id.inform_bottom_sheet_qd);

        inform_bottom_sheet_qx.setOnClickListener(this);
        inform_bottom_sheet_qd.setOnClickListener(this);
        return view;
    }

    private View createLeaveView() {
        View view = LayoutInflater.from(this).inflate(R.layout.inform_leave_layout, (ViewGroup) getWindow().getDecorView(), false);
        final EditText inform_edit = (EditText) view.findViewById(R.id.inform_edit);
        Button inform_cancel = (Button) view.findViewById(R.id.inform_cancel);
        Button inform_submit = (Button) view.findViewById(R.id.inform_submit);

        inform_cancel.setOnClickListener(this);
        inform_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inform_edit.getText().toString().equals(""))
                    Toast.makeText(InformDetailsJPush.this, "请假理由不能为空", Toast.LENGTH_LONG).show();
                else
                    tzUpload.askForLeave(InformDetailsJPush.this, bottomDialog, iDataBean, inform_edit.getText().toString(), inform_check, mHandler);
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notify_details_sign:
                if (bottomSheet == null) {
                    if (iDataBean.getNotifytype().equals("common"))
                        bottomSheet = createBottomSheetView();
                    else if (iDataBean.getNotifytype().equals("meeting"))
                        bottomSheet = createLeaveView();
                }
                //将布局设置给Dialog
                if (!inform_check.isSelected())
                    if (bottomDialog.isShowing()) {
                        bottomDialog.dismiss();
                    } else {
                        bottomDialog.setContentView(bottomSheet);
                        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                        bottomDialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                        bottomDialog.getWindow().setAttributes(bottomDialog.getWindow().getAttributes());
                        bottomDialog.show();
                    }
                break;
            case R.id.inform_cancel:
                tzUpload.signIn(InformDetailsJPush.this, bottomDialog, iDataBean, inform_check, mHandler);
                break;
            case R.id.inform_bottom_sheet_qx:
                bottomDialog.dismiss();
                break;
            case R.id.inform_bottom_sheet_qd:
                tzUpload.signIn(InformDetailsJPush.this, bottomDialog, iDataBean, inform_check, mHandler);
                break;
            default:
                break;
        }
    }

    private void getInform(int informID) {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getInformListUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("only", informID + "")
                .build()
                .execute(new InformListCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("InformList_onError", e.toString());
                                 Toast.makeText(InformDetailsJPush.this, "通知消息获取失败", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(NotifyList response, int id) {
                                 Log.e("InformListCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         iDataBean = response.getData().get(0);
                                         loadUI();
                                         break;
                                     default:
                                         Toast.makeText(InformDetailsJPush.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class InformListCallback extends com.zhy.http.okhttp.callback.Callback<NotifyList> {

        @Override
        public NotifyList parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("通知——String", result);
            NotifyList informList = new Gson().fromJson(result, NotifyList.class);
            return informList;
        }
    }
}

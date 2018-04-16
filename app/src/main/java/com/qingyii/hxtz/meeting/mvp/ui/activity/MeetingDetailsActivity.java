package com.qingyii.hxtz.meeting.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.qingyii.hxtz.MainWebActivity;
import com.qingyii.hxtz.QrCodeSignInActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.TrainSiteActivity;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.base.app.GlobalConsts;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.meeting.di.component.DaggerMeetingDetailsComponent;
import com.qingyii.hxtz.meeting.di.module.MeetingDetailsModule;
import com.qingyii.hxtz.meeting.di.module.entity.MeetingList;
import com.qingyii.hxtz.meeting.mvp.presenter.MeetingetailsPresenter;
import com.qingyii.hxtz.pojo.TrainList;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.autolayout.AutoLinearLayout;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import im.delight.android.webview.AdvancedWebView;

import static com.github.barteksc.pdfviewer.util.FileUtils.openFile;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.qingyii.hxtz.QrCodeSignInActivity.REQUEST_CODE;
import static com.qingyii.hxtz.base.app.EventBusTags.UPDATE_MEETING_SUMMARY_FINISH;

/**
 * 会议详情
 */
public class MeetingDetailsActivity extends BaseActivity<MeetingetailsPresenter> implements CommonContract.MeetingDetailsView, View.OnClickListener, AdvancedWebView.Listener {
    public static final String PARAMS = "meeting_data";
    public static final String PARAMS2 = "position";
    public static final String SIGN = "sign";
    public static final String REJECT = "reject";
    @BindView(R.id.toolbar_back)
    Button toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    Button toolbarRight;
    @BindView(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    @BindView(R.id.wb_meeting_details_content)
    AdvancedWebView mWebView;
    @BindView(R.id.meeting_details_feedback_time)
    TextView mSignTime;
    @BindView(R.id.meeting_details_feedback)
    TextView mDetailsSign;
    @BindView(R.id.operation_ll)
    AutoLinearLayout operation;
    @BindView(R.id.webview_progressbar)
    ProgressBar webview_progressbar;
    private Dialog dialog;
    private boolean isIntercept;
    private MeetingList.DataBean data;
    private boolean isSignin;
    private EditText editText;
    private int position;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMeetingDetailsComponent
                .builder()
                .appComponent(appComponent)
                .meetingDetailsModule(new MeetingDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_meeting_details;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        data = getIntent().getParcelableExtra(PARAMS);
        position = getIntent().getIntExtra(PARAMS2, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        if (data.getStatus_mark().equals("未开始")) {
            mSignTime.setText("反馈时限：" + format.format(new Date(data.getBegintime() * 1000)));
        } else {
            mSignTime.setText("反馈时限：反馈时间已过");
            mDetailsSign.setVisibility(View.GONE);
        }
        toolbarTitle.setText(R.string.meeting_details);
        toolbarRightTv.setTextColor(getResources().getColorStateList(R.color.toolbar_right_tv_selector));


        if (data.getStatus_mark().equals("已结束")) {
            toolbarRightTv.setVisibility(View.GONE);
            if (MyApplication.userUtil.getId() == data.getUser_id()) {

                isSignin = true;
                toolbarRightTv.setVisibility(View.VISIBLE);
                toolbarRightTv.setText("发布报道");
            } else {
                toolbarRightTv.setVisibility(View.GONE);
            }
        } else {
            toolbarRightTv.setVisibility(View.VISIBLE);
            toolbarRightTv.setText(R.string.scan_qr_code_sign_in);
        }
        if (data.getHas_summary() != 0) {
            toolbarRightTv.setVisibility(View.GONE);
            mSignTime.setVisibility(View.GONE);
            mDetailsSign.setVisibility(View.VISIBLE);
            mDetailsSign.setText("查看会议报道");
            mDetailsSign.setTextColor(getResources().getColor(R.color.notifyTitleColor));
        } else {
            updateFeedBack();
        }
        initWebView();
    }

    @Subscriber(tag = EventBusTags.MEETING)
    public void onEvent(Message msg) {
        if(msg.what==UPDATE_MEETING_SUMMARY_FINISH){
            toolbarRightTv.setVisibility(View.GONE);
            mSignTime.setVisibility(View.GONE);
            mDetailsSign.setVisibility(View.VISIBLE);
            mDetailsSign.setText("查看会议报道");
            mDetailsSign.setClickable(true);
            data.setHas_summary(1);
            mDetailsSign.setTextColor(getResources().getColor(R.color.notifyTitleColor));
            Message new_msg = Message.obtain();
            new_msg.what = EventBusTags.UPDATE_MEETING_LIST_SUMMARY_FINISH;
            new_msg.arg1 = position;
            new_msg.arg2 = 1;
            EventBus.getDefault().post(new_msg, EventBusTags.MEETING);
        }
    }
    private void updateFeedBack() {
        mDetailsSign.setClickable(data.getIs_confirm() == 0 && data.getStatus_mark().equals("未开始"));
        if (data.getIs_confirm() == 1) {
            mDetailsSign.setText("已确认");
            mDetailsSign.setTextColor(getResources().getColor(R.color.white));
            mDetailsSign.setBackgroundResource(R.color.green);
        } else if (data.getIs_confirm() == 2) {
            mDetailsSign.setText("已请假");
            mDetailsSign.setBackgroundResource(R.color.red);
            mDetailsSign.setTextColor(getResources().getColor(R.color.white));
        } else {
            mDetailsSign.setText("点击反馈");
            mDetailsSign.setBackgroundResource(R.color.white);
            mDetailsSign.setTextColor(getResources().getColor(R.color.black));
        }
    }

    @Override
    public void UpdateFeedbackStatus(String status) {
        mDetailsSign.setClickable(false);
        Message msg = new Message();
        switch (status) {
            case SIGN:
                data.setIs_confirm(1);
                mDetailsSign.setText("已确认");
                msg.what = EventBusTags.UPDATE_MEETING_FEEDBAK_JOIN;
                msg.arg1 = position;
                msg.arg2 = 1;
                break;
            case REJECT:
                data.setIs_confirm(2);
                mDetailsSign.setText("已请假");
                msg.what = EventBusTags.UPDATE_MEETING_FEEDBAK_LEAVE;
                msg.arg1 = position;
                msg.arg2 = 2;
                break;
        }
        EventBus.getDefault().post(msg, EventBusTags.MEETING);
        updateFeedBack();
    }

    private void initWebView() {
        mWebView.getSettings().setBuiltInZoomControls(true);// 隐藏缩放按钮
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
        mWebView.getSettings().setUseWideViewPort(true);// 可任意比例缩放
        mWebView.getSettings().setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        mWebView.getSettings().setSavePassword(true);
        mWebView.getSettings().setSaveFormData(true);// 保存表单数据
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebView.setListener(this, this);
        mWebView.addHttpHeader("Accept", XrjHttpClient.ACCEPT_V2);
        mWebView.addHttpHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""));
        String webUrl = XrjHttpClient.URL_PR + "/meeting/" + data.getId();
        Log.i("tmdmeet",webUrl);
        //WebView点击事件依旧显示在本页面
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                if (isIntercept) {
                    isIntercept = false;
                    return true;
                }
//                if(url.startsWith(XrjHttpClient.URL_UP)){
//                    showProgressDialog();
//                }
                view.loadUrl(url);
                return false;
            }
        });
        mWebView.addJavascriptInterface(new clickOnJS(), "click");
        mWebView.loadUrl(webUrl);

    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        if (webview_progressbar != null)
            webview_progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(String url) {
        if (webview_progressbar != null)
            webview_progressbar.setVisibility(View.GONE);
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        if (webview_progressbar != null)
            webview_progressbar.setVisibility(View.GONE);
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        File file = new File(HttpUrlConfig.cacheDir, suggestedFilename);
        if (file.exists()) {
            openFile(this, file.getPath(), mimeType);
        } else {
            mPresenter.downloadFile(url, file, mimeType);
        }

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    class clickOnJS {
        @JavascriptInterface
        public void openMap() {
            isIntercept = true;
            Intent intent = new Intent(MeetingDetailsActivity.this, TrainSiteActivity.class);
            TrainList.DataBean tDataBean = new TrainList.DataBean();
            tDataBean.setId(data.getId());
            tDataBean.setLocation(data.getLocation());
            intent.putExtra("isMeeting", true);
            intent.putExtra("training", tDataBean);
            intent.putExtra("tltle", data.getAddress());
            startActivity(intent);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == keyEvent.KEYCODE_BACK) {//监听返回键，如果可以后退就后退
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;

            }
        }
        return super.onKeyDown(keyCode, keyEvent);
    }


    @Nullable
    @OnClick({R.id.toolbar_back_layout, R.id.toolbar_right_layout, R.id.meeting_details_feedback})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.toolbar_back_layout:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    killMyself();
                }
                break;
            case R.id.meeting_details_feedback:
                if (data.getHas_summary() != 0) {
                    intent = goToMeetingSummary(false);
                    launchActivity(intent);
                } else {
                    show();
                }
                break;
            case R.id.toolbar_right_layout:

                if (isSignin) {
                    intent = goToMeetingSummary(true);
                } else {
                    intent = new Intent(this, CaptureActivity.class);

                }
                startActivityForResult(intent, REQUEST_CODE);
                break;

        }
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
                    intent.putExtra("webUrl", result.toString());
                    intent.putExtra(QrCodeSignInActivity.TITLE, getString(R.string.sign_in_details));
                    startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MeetingDetailsActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @NonNull
    private Intent goToMeetingSummary(boolean isPublish) {
        Intent intent = new Intent(this, MeetingSummaryActivity.class);
        if (isPublish) {
            intent.putExtra(GlobalConsts.ISPUBLISH, isPublish);
            intent.putExtra(PARAMS, data);
        } else {
            HomeInfo.AdBean.INDEXBANNERBean aDataBean = new HomeInfo.AdBean.INDEXBANNERBean();
            aDataBean.setAdtitle("会议报道");
            aDataBean.setAdlink(XrjHttpClient.URL_PR + "/meeting/" + data.getId() + "/summary/show");
            intent = new Intent(this, MainWebActivity.class);
            intent.putExtra("aDataBean", aDataBean);
        }
        return intent;
    }

    private void show() {
        dialog = new Dialog(this, R.style.my_dialog);
        View root = LayoutInflater.from(this).inflate(R.layout.sign_dialog, null);
        Button btnOk = (Button) root.findViewById(R.id.dialog_ok);
        Button btnCancel = (Button) root.findViewById(R.id.dialog_cancel);
        editText = (EditText) root.findViewById(R.id.sign_dialog_et);
        btnOk.setText(R.string.confirm_join);
        btnCancel.setText(R.string.ask_for_leave);
        editText.setHint(R.string.leave_reason);
        btnOk.setOnClickListener(this);
        btnOk.setTag(editText.getText().toString());
        btnCancel.setOnClickListener(this);
        dialog.setContentView(root);
        dialog.setCancelable(true);//不能点返回取消
        dialog.setCanceledOnTouchOutside(true);//不能点外部取消
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);//显示在window底部
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
//      lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
//      lp.alpha = 9f; // 透明度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        checkNotNull(message);
        UiUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_ok:
                mPresenter.requestFeedback(data.getId(), SIGN, "");
                dialog.dismiss();
                break;
            case R.id.dialog_cancel:
                mPresenter.requestFeedback(data.getId(), REJECT, editText.getText().toString());
                dialog.dismiss();
                break;
        }
    }
}

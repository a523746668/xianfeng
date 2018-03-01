package com.qingyii.hxt.notify.mvp.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.app.EventBusTags;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.notify.di.component.DaggerNotifyDetailsComponent;
import com.qingyii.hxt.notify.di.module.NotifyDetailsModule;
import com.qingyii.hxt.notify.mvp.model.entity.NotifyList;
import com.qingyii.hxt.notify.mvp.presenter.NotifyDetailsPresenter;
import com.qingyii.hxt.util.DateUtils;
import com.zhy.autolayout.AutoLinearLayout;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import im.delight.android.webview.AdvancedWebView;

import static com.github.barteksc.pdfviewer.util.FileUtils.openFile;
import static com.github.barteksc.pdfviewer.util.FileUtils.openFile;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.qingyii.hxt.R.id.notify_details_sign;
import static com.qingyii.hxt.notify.mvp.model.entity.NotifyList.DataBean.ID;
/**
 * 通知详情
 */
public class NotifyDetailsActivity extends BaseActivity<NotifyDetailsPresenter> implements CommonContract.NotifyDetailsView, View.OnClickListener, AdvancedWebView.Listener  {
    public static final String NOTIFY = "notify";
    public static final String POSITION = "position";

    @BindView(R.id.toolbar_back)
    Button toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    Button toolbarRight;
    @BindView(R.id.wb_notify_details_content)
    AdvancedWebView mWebView;
    @BindView(R.id.notify_details_sign_time)
    TextView mSignTime;
    @BindView(R.id.notify_details_sign)
    TextView mDetailsSign;
    @BindView(R.id.operation_ll)
    AutoLinearLayout operation;
    @BindView(R.id.webview_progressbar)
    ProgressBar webview_progressbar;
    private NotifyList.DataBean notify;
    private Dialog dialog;
    private int position;
    private ProgressDialog mDialog;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerNotifyDetailsComponent
                .builder()
                .appComponent(appComponent)
                .notifyDetailsModule(new NotifyDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        if (getIntent().getParcelableExtra(NOTIFY) != null) {
             notify = getIntent().getParcelableExtra(NOTIFY);
        }
        if (getIntent().hasExtra(POSITION)) {
            position = getIntent().getIntExtra(POSITION,0);
        }
        return R.layout.activity_notify_details;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbarTitle.setText(R.string.notify_details);
        initWebView();
        Log.i("tmdtongzhi",DateUtils.getDateToLongString(notify.getReceipt_line())+"----"+DateUtils.getDateToLongString(System.currentTimeMillis()/1000));
        switch (notify.getReceipt_status_mark()) {
            case NotifyList.DataBean.UNREAD:
            case NotifyList.DataBean.READ:
                operation.setVisibility(View.GONE);
                break;
            case NotifyList.DataBean.UNSIGN:
                mDetailsSign.setSelected(false);
                operation.setVisibility(View.VISIBLE);
                mDetailsSign.setText("点击签收");
                if(notify.getReceipt_line()>0)
                    mSignTime.setText("签收时限："+DateUtils.getDateToLongString(notify.getReceipt_line()));
                break;
            case NotifyList.DataBean.SIGN:
                operation.setVisibility(View.VISIBLE);
                mDetailsSign.setText("已签收");
                mDetailsSign.setClickable(false);
                mDetailsSign.setSelected(true);
                if(notify.getReceipt_line()>0)
                    mSignTime.setText("签收时限："+DateUtils.getDateToLongString(notify.getReceipt_line()));
                break;
            case NotifyList.DataBean.UNRETURN:
                mDetailsSign.setSelected(false);
                operation.setVisibility(View.VISIBLE);
                mDetailsSign.setText("点击回执");
                if(notify.getReceipt_line()>0)
                    mSignTime.setText("回执时限："+DateUtils.getDateToLongString(notify.getReceipt_line()));
                break;

            case NotifyList.DataBean.RETURN:
                operation.setVisibility(View.VISIBLE);
                mDetailsSign.setText("已回执");
                mDetailsSign.setClickable(false);
                mDetailsSign.setSelected(true);
                if(notify.getReceipt_line()>0)
                    mSignTime.setText("回执时限："+DateUtils.getDateToLongString(notify.getReceipt_line()));
                break;
        }
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
        String webUrl = XrjHttpClient.getInformListUrl() + "/" + notify.getId();
        Log.i("tmdweburl",webUrl);
        //WebView点击事件依旧显示在本页面
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(webUrl);
    }
    @Override
    public void UpdateReadStatus() {

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


    @Override
    protected void onResume() {
        super.onResume();
         mWebView.reload();

    }

    @Override
    public void UpdateSignStatus() {
        mDetailsSign.setText("已签收");
        mDetailsSign.setClickable(false);
        mDetailsSign.setSelected(true);
        Message event = new Message();
        event.what = EventBusTags.UPDATE_NOTIFY_SIGN_IN;
        event.arg1 = position;
        EventBus.getDefault().post(event,EventBusTags.NOTIFY);
        mWebView.reload();
    }
    @Nullable
    @OnClick({R.id.toolbar_back_layout, R.id.toolbar_right_layout, notify_details_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_layout:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    killMyself();
                }
                break;
            case R.id.toolbar_right_layout:
                break;
            case notify_details_sign:

                if (notify.getReceipt_status_mark().equals(NotifyList.DataBean.UNSIGN)) {
                    if(System.currentTimeMillis()/1000>notify.getReceipt_line()){
                        Toasty.info(NotifyDetailsActivity.this,"已超过签收时间，无法签收",0).show();
                        return;
                    }
                    show();
                } else if (notify.getReceipt_status_mark().equals(NotifyList.DataBean.UNRETURN)) {
                    if(System.currentTimeMillis()/1000>notify.getReceipt_line()){
                       Log.i("tmdtongzhi",System.currentTimeMillis()/1000+"---"+notify.getReceipt_line());
                        Toasty.info(NotifyDetailsActivity.this,"已超过回执时间，无法回执",0).show();
                        return;
                    }

                    Intent mReturn = new Intent(this,NotifyReturnActivity.class);
                    mReturn.putExtra(ID,notify.getId());
                    launchActivity(mReturn);
                }
                break;
        }
    }

    private void show() {
        dialog = new Dialog(this, R.style.my_dialog);
        View root = LayoutInflater.from(this).inflate(R.layout.sign_dialog, null);
        Button btnOk = (Button) root.findViewById(R.id.dialog_ok);
        Button btnCancel = (Button) root.findViewById(R.id.dialog_cancel);
        EditText editText = (EditText) root.findViewById(R.id.sign_dialog_et);
        btnOk.setOnClickListener(this);
        btnOk.setTag(editText.getText().toString());
        btnCancel.setOnClickListener(this);
        dialog.setContentView(root);
        dialog.setCancelable(false);//不能点返回取消
        dialog.setCanceledOnTouchOutside(false);//不能点外部取消
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
    /**
     * eventbus 更新状态
     *
     * @param message
     */
    @Subscriber(tag = EventBusTags.NOTIFY,mode = ThreadMode.MAIN)
    public void onEvent(Message message){
        switch (message.what) {
            case EventBusTags.UPDATE_NOTIFY_RETURN:
                //更新已签收状态
                mDetailsSign.setText("已回执");
                mDetailsSign.setClickable(false);
                mDetailsSign.setSelected(true);
                Message event = new Message();
                event.arg1 = position;
                event.what = EventBusTags.UPDATE_NOTIFY_RETURN_LIST;
                EventBus.getDefault().post(event,EventBusTags.NOTIFY);
                break;
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_ok:
                mPresenter.requestSignMark(notify.getId(), (String) v.getTag());

                dialog.dismiss();
                break;
            case R.id.dialog_cancel:
                dialog.dismiss();
                dialog = null;
                break;
        }
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
            mPresenter.downloadFile(url,file,mimeType);
        }

    }
    @Override
    public void onExternalPageRequest(String url) {

    }

}

package com.qingyii.hxtz.home.mvp.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.leethink.badger.BadgeUtil;
import com.qingyii.hxtz.GuanLiInActivity;
import com.qingyii.hxtz.KaoChangType02Activity;
import com.qingyii.hxtz.LoginActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.TrainActivity;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxtz.base.app.EventBusTags;
import com.qingyii.hxtz.base.mvp.contract.CommonContract;
import com.qingyii.hxtz.base.utils.WindowUtils;
import com.qingyii.hxtz.circle.CircleMainActivity;
import com.qingyii.hxtz.download.DownloadUtil;
import com.qingyii.hxtz.home.di.component.DaggerHomeNewComponent;
import com.qingyii.hxtz.home.di.module.HomeModule;
import com.qingyii.hxtz.home.mvp.model.entity.FakeData;
import com.qingyii.hxtz.home.mvp.model.entity.HomeClass;
import com.qingyii.hxtz.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxtz.home.mvp.presenter.HomePresenter;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.httpway.Login;
import com.qingyii.hxtz.meeting.mvp.ui.activity.MeetingActivity;
import com.qingyii.hxtz.myActivity;
import com.qingyii.hxtz.neiKanActivity;
import com.qingyii.hxtz.notice.mvp.ui.activity.NoticeDetailsActivity;
import com.qingyii.hxtz.notify.di.module.NotifyDetailsModule;
import com.qingyii.hxtz.notify.mvp.presenter.NotifyDetailsPresenter;
import com.qingyii.hxtz.notify.mvp.ui.activity.NotifyActivity;
import com.qingyii.hxtz.shuJiaActivity;
import com.qingyii.hxtz.util.UpdateUtil;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WMCJActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.qingyii.hxtz.zhf.Util.Global;
import com.qingyii.hxtz.zhf.Util.HintUtil;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

import static android.view.KeyEvent.KEYCODE_BACK;
import static com.github.barteksc.pdfviewer.util.FileUtils.openFile;
import static com.qingyii.hxtz.base.app.GlobalConsts.ACTIVITY;
import static com.qingyii.hxtz.base.app.GlobalConsts.ANNOUNCEMENT;
import static com.qingyii.hxtz.base.app.GlobalConsts.BOOK;
import static com.qingyii.hxtz.base.app.GlobalConsts.CIRCLE;
import static com.qingyii.hxtz.base.app.GlobalConsts.DJFDY;
import static com.qingyii.hxtz.base.app.GlobalConsts.DOCUMENTARY;
import static com.qingyii.hxtz.base.app.GlobalConsts.EXAMS;
import static com.qingyii.hxtz.base.app.GlobalConsts.MAGAZINE;
import static com.qingyii.hxtz.base.app.GlobalConsts.MEETING;
import static com.qingyii.hxtz.base.app.GlobalConsts.MORE;
import static com.qingyii.hxtz.base.app.GlobalConsts.MY;
import static com.qingyii.hxtz.base.app.GlobalConsts.NOTIFY;
import static com.qingyii.hxtz.base.app.GlobalConsts.TRAIN;
import static com.qingyii.hxtz.base.app.GlobalConsts.WMCJ;

public class HomeNewActivity extends BaseActivity<HomePresenter> implements CommonContract.HomeInfoView,CommonContract.NotifyDetailsView {

    @BindView(R.id.toolbar_back)
    Button back;

    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.homerecyc)
    RecyclerView recyclerView;

    @BindView(R.id.homewebview)
    WebView webview;

    private Map<String, Class<?>> map = new HashMap<>();
    ArrayList<HomeClass>  homes=new ArrayList<>();
    private HomeInfo homeInfo;
    private RxPermissions mRxPermissions;
    UpdateUtil util=new UpdateUtil();
    ValueCallback<Uri> mUploadMessage;
    private String url="http://192.168.0.106:81";
    private String xianshang="https://wap.seeo.cn/C143/";
    private String murl=url+"/C143";
    public static int webviewUpload =100;

     @Inject
     NotifyDetailsPresenter mnPresenter;

    //是否是重新登录跳转过来的界面
    private boolean isLoginag=false;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerHomeNewComponent.builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .notifyDetailsModule(new NotifyDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_home_new;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

         String registrationID= JPushInterface.getRegistrationID(MyApplication.getInstance());
         MyApplication.hxt_setting_config.edit().putString("DeviceID",registrationID).commit();
         isLoginag=getIntent().getBooleanExtra("userchange",false);
         initoolbar();
         initHomeInfoName();
         initwebview();
         mPresenter.getpersion();
          if(!isLoginag){
              util.Updatehome(this,true);
          }
         if(!TextUtils.isEmpty(MyApplication.hxt_setting_config.getString("token",""))){
           Login login= Login.getLogin();
           login.userRFI();
           String token=MyApplication.hxt_setting_config.getString("token","");
           login.userDevice(this,token);
           mPresenter.gethomeinfo();
        }
    }

    private void initwebview() {

        //声明WebSettings子类
        WebSettings webSettings = webview.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);


//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
       webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setBlockNetworkImage(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高webview渲染的优先级
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
              //  view.loadUrl(url);
                Log.i("tmdurl",url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("tmdurlstat",url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("tmdurlfinish", url);
                backjianting(url);
                if (url.contains("=") && url.endsWith("=")&&url.contains("#?")) {
                    view.loadUrl(xianshang+"login");
                    return;
                }
                if(url.startsWith(xianshang+"app?token=")&&!url.endsWith("token=")){
                    String[] urls = url.split("=");
                    MyApplication.hxt_setting_config.edit().putString("token", urls[1]).commit();
                    MyApplication.hxt_setting_config.edit().putString("credentials", "Bearer " +urls[1]).commit();
                    mPresenter.gethomeinfo();
                    Login.getLogin().userRFI();
                    Login.getLogin().userDevice(HomeNewActivity.this,MyApplication.hxt_setting_config.getString("token",""));
                    return;
                }
                if (url.startsWith(xianshang+"appm?m2")||url.contains("appm?m2=")) {
                    if (url.contains("=") && !url.endsWith("=")) {
                        String[] urls = url.split("=");
                        MyApplication.hxt_setting_config.edit().putString("token", urls[1]).commit();
                        MyApplication.hxt_setting_config.edit().putString("credentials", "Bearer " +urls[1]).commit();
                        isneedrequest();
                        if (urls[0].endsWith("m2")) {
                            Intent intent = new Intent(HomeNewActivity.this, WMCJActivity.class);
                            startActivity(intent);
                            view.goBack();
                            return;
                        }
                    }
                }

                if (url.startsWith(xianshang+"more#")&&!url.contains("?m5=")) {
                    if (url.contains("=") && !url.endsWith("=")) {
                        String[] urls = url.split("=");
                        MyApplication.hxt_setting_config.edit().putString("token", urls[1]).commit();
                       MyApplication.hxt_setting_config.edit().putString("credentials", "Bearer " +urls[1]).commit();
                       isneedrequest();
                        String  num =urls[0].substring(urls[0].length() - 3) ;
                        Intent intent = new Intent();
                        Log.i("tmdnum",num+"");
                        if(num.contains("46")) {
                            Log.i("tmdnum",num+"");
                            intent.setClass(HomeNewActivity.this, map.get(CIRCLE));
                            startActivity(intent);

                        } else if(num.contains("36")) {
                            intent.setClass(HomeNewActivity.this, map.get(MEETING));
                            startActivity(intent);
                        } else if(num.contains("37"))
                        {      intent.setClass(HomeNewActivity.this, map.get(TRAIN));
                               startActivity(intent);}
                          else if(num.contains("40")) {
                            intent.setClass(HomeNewActivity.this, map.get(BOOK));
                            startActivity(intent);
                        } else if(num.contains("42")) {
                            intent.setClass(HomeNewActivity.this, map.get(MAGAZINE));
                            startActivity(intent);
                        }
                          else if(num.contains("44")) {
                                intent.setClass(HomeNewActivity.this, map.get(ANNOUNCEMENT));
                                startActivity(intent);}
                        view.goBack();
                        }

                    Log.i("tmd","444");
                             return;
                    }
                    if(url.startsWith(xianshang+"appm?m5")||url.contains("appm?m5=")){

                        if (url.contains("=") && !url.endsWith("=")) {
                            String[] urls = url.split("=");

                            MyApplication.hxt_setting_config.edit().putString("token", urls[1]).commit();
                            MyApplication.hxt_setting_config.edit().putString("credentials", "Bearer " +urls[1]).commit();
                            isneedrequest();
                            Log.i("tmdmy",urls[0]+"-----"+urls[1]+"-----"+homeInfo.getError_msg());
                            if(homeInfo!=null){
                                Intent intent = new Intent(HomeNewActivity.this,map.get(MY));
                                intent.putExtra("moduletitle",homeInfo.getAccount());
                                startActivity(intent);
                                view.goBack();
                            }
                        }
                    }
                }


        });
        webview.setWebChromeClient(new WebChromeClient(){
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType) {
                if (mUploadMessage != null)
                    return;
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "文件选择"),
                        webviewUpload);
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, "");
            }

            // For Android > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }

            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                if (mUploadCallbackAboveL != null)
                    return false;
                mUploadCallbackAboveL = filePathCallback;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "文件选择"),
                        webviewUpload);
                return true;
            }
            });
       webview.setDownloadListener(new DownloadListener() {
           @Override
           public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
               File file = new File(HttpUrlConfig.cacheDir, userAgent);
               if (file.exists()) {
                   openFile(HomeNewActivity.this, file.getPath(), mimetype);
               } else {
                   mnPresenter.downloadFile(url,file,mimetype);
               }
           }
       });
       if(!isLoginag){
           webview.loadUrl(xianshang);
       } else {
           webview.loadUrl(xianshang+"login");
       }
    }

    private void  isneedrequest() {
          if(!Global.isFlag){
              Login.getLogin().userRFI();
              mPresenter.gethomeinfo();
          }
          if(!Global.isjpush){
              String token=MyApplication.hxt_setting_config.getString("token","");
              Login.getLogin().userDevice(this,token);
          }
    }

    ValueCallback<Uri[]> mUploadCallbackAboveL;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== webviewUpload)
        {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            }
            else  if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != webviewUpload
                || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
        return;
    }

    private void initHomeInfoName() {
        map.put(NOTIFY, NotifyActivity.class);
        map.put(MEETING, MeetingActivity.class);
        map.put(TRAIN, TrainActivity.class);
        map.put(WMCJ, null);
        map.put(DJFDY, null);
        map.put(ACTIVITY, null);
        map.put(BOOK, shuJiaActivity.class);
        map.put(MAGAZINE, neiKanActivity.class);
        map.put(MORE, null);
        map.put(MY, myActivity.class);
        map.put(ANNOUNCEMENT, NoticeDetailsActivity.class);
        map.put(CIRCLE, CircleMainActivity.class);
        map.put(DOCUMENTARY, GuanLiInActivity.class);
        map.put(EXAMS, KaoChangType02Activity.class);
    }
    private void initoolbar() {
       back.setVisibility(View.GONE);
        title.setText("湘直宣传云");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webview.canGoBack()){
                    webview.goBack();
                }
            }
        });
    }

    private void  backjianting( String url){
        if(webview.canGoBack()&&!url.equalsIgnoreCase("https://wap.seeo.cn/C143")){
            back.setVisibility(View.VISIBLE);
        } else {
            back.setVisibility(View.GONE);
        }
    }

    @Override
    public void setAdapter(BaseRecyclerAdapter adapter) {

    }
    private int badgeCount;
    @Override
    public void updateUI(HomeInfo homeInfo) {
        this.homeInfo=homeInfo;
        ArrayList<HomeInfo.AccountBean.ModulesBean> list= (ArrayList<HomeInfo.AccountBean.ModulesBean>) homeInfo.getAccount().getModules();
       for(HomeInfo.AccountBean.ModulesBean bean :list){
           if(bean.getMark().startsWith("task")){
               String num=bean.getMark().substring(bean.getMark().lastIndexOf("/")+1);
               Global.userid=Integer.valueOf(num);
           }
          if(bean.getMark().equalsIgnoreCase("meeting")){
              Global.list.add(bean);
          }
          if(bean.getMark().equalsIgnoreCase("train")){
              Global.list.add(bean);
          }
           try {
               // 获取实体类的所有属性，返回Field数组
               Field[] field = homeInfo.getMessage().getClass().getDeclaredFields();
               // 遍历所有属性
               badgeCount = 0;
               for (int j = 0; j < field.length; j++) {
                   String name = field[j].getName();
                   if (name.equals("system")) {
                       name = name.substring(0, 1).toUpperCase() + name.substring(1);
                       Method m = homeInfo.getMessage().getClass().getMethod("get" + name);
                       Integer value = (Integer) m.invoke(homeInfo.getMessage());

                       badgeCount += value;
                   }
                   Log.i("tmdhomejiaobiao",badgeCount+"----------");
                   updateMessageCount(field[j].getName(), EventBusTags.UpdateType.no, homeInfo.getMessage());
               }
           } catch (Exception e) {
               Log.i("tmdhomenew",e.getMessage().toString());
           }

       }
    }
    private void updateMessageCount(String name, EventBusTags.UpdateType type, Object model) throws Exception {
        for (HomeInfo.AccountBean.ModulesBean modulesBean : homeInfo.getAccount().getModules()) {
            if (name.equals(modulesBean.getMark())) {
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Method m = model.getClass().getMethod("get" + name);
                Integer value = (Integer) m.invoke(model);
                if(modulesBean.getMark().equals(CIRCLE)){
                    value = 0;
                }
                if (value != null) {
                    switch (type) {
                        case no:
                            modulesBean.setCount(value);
                            badgeCount += value;
                            break;
                        case add:
                            modulesBean.setCount(value + 1);
                            badgeCount++;
                            break;
                        case del:
                            modulesBean.setCount(value - 1);
                            badgeCount--;
                            break;
                    }
                }
                Log.i("tmdbadgeCount",badgeCount+"--------");
                BadgeUtil.sendBadgeNotification(null, 100, this, badgeCount, badgeCount);
            }
        }
    }


    @Override
    public RxPermissions getRxPermissions() {
        return this.mRxPermissions;
    }

    @Override
    public void getfakedatasuceess(FakeData fakeData) {

    }

    @Override
    public String[] getPerMissions() {
        List<String> list = new ArrayList<>();
        list.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        list.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        list.add(android.Manifest.permission.READ_PHONE_STATE);
        list.add(android.Manifest.permission.ACCESS_NETWORK_STATE);
        list.add(android.Manifest.permission.CAMERA);
        list.add(Manifest.permission.RECORD_AUDIO);
        return list.toArray(new String[]{});
    }

    @Override
    public void showLoading() {
        HintUtil.showdialog(this);
    }

    @Override
    public void hideLoading() {
           HintUtil.stopdialog();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }
    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.HOME)
    public void getMy(int i){
        mPresenter.gethomeinfo();
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.HOME)
    public void refreshwebview(boolean flag){
         Log.i("tmdhome","运行了此方法："+murl);
      //  webview.loadUrl(murl);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("tmdhome",webview.canGoBack()+"");
        if ((keyCode == KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        } else {
            WindowUtils.showExitDialog(this);
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        if (webview != null) {
            webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webview.clearHistory();

            ((ViewGroup) webview.getParent()).removeView(webview);
            webview.destroy();
            webview = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public void UpdateReadStatus() {

    }

    @Override
    public void UpdateSignStatus() {

    }
}

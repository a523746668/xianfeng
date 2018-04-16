package com.qingyii.hxtz.home.mvp.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
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
import com.qingyii.hxtz.home.di.component.DaggerHomeNewComponent;
import com.qingyii.hxtz.home.di.module.HomeModule;
import com.qingyii.hxtz.home.mvp.model.entity.FakeData;
import com.qingyii.hxtz.home.mvp.model.entity.HomeClass;
import com.qingyii.hxtz.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxtz.home.mvp.presenter.HomePresenter;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.meeting.mvp.ui.activity.MeetingActivity;
import com.qingyii.hxtz.myActivity;
import com.qingyii.hxtz.neiKanActivity;
import com.qingyii.hxtz.notice.mvp.ui.activity.NoticeDetailsActivity;
import com.qingyii.hxtz.notify.mvp.ui.activity.NotifyActivity;
import com.qingyii.hxtz.shuJiaActivity;
import com.qingyii.hxtz.util.UpdateUtil;
import com.qingyii.hxtz.wmcj.mvp.ui.activity.WMCJActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhf.Util.Global;
import com.zhf.Util.HintUtil;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

import static android.view.KeyEvent.KEYCODE_BACK;
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

public class HomeNewActivity extends BaseActivity<HomePresenter> implements CommonContract.HomeInfoView {

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

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerHomeNewComponent.builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_home_new;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initoolbar();
        initHomeInfoName();
        inittablayout();
        initwebview();
        mPresenter.getpersion();
        if(Global.flag){
           // mPresenter.gethomeinfo();
        }
        util.Updatehome(this,true);

    }

    private void initwebview() {
        String murl="http://wap.seeo.cn/143/";
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
        //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
              //  view.loadUrl(url);
                return false;
            }
        });

        webview.loadUrl(murl);

    }

    private void inittablayout() {
          HomeClass homeClass1=new HomeClass("文件通知",R.mipmap.wjtz,NOTIFY);
        HomeClass homeClass2=new HomeClass("文明创建",R.mipmap.wmcj,WMCJ);
        HomeClass homeClass3=new HomeClass("答题闯关",R.mipmap.dtcg,EXAMS);
        HomeClass homeClass4=new HomeClass("更多",R.mipmap.gd,MORE);
        HomeClass homeClass5=new HomeClass("我的",R.mipmap.my,MY);
        homes.add(homeClass1);
        homes.add(homeClass2);
        homes.add(homeClass3);
        homes.add(homeClass4);
        homes.add(homeClass5);
        BaseRecyclerAdapter<HomeClass> adapter=new BaseRecyclerAdapter<HomeClass>(homes) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.homerecyc;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, int position, HomeClass item) {
               TextView tv= holder.getTextView(R.id.homerecyctv);
                tv.setText(item.getText());
                Drawable drawable = getResources().getDrawable(item.getId());
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv.setCompoundDrawables(null,drawable,null,null);
                tv.setCompoundDrawablePadding(8);
            }
        };
       recyclerView.setLayoutManager(new GridLayoutManager(this,5));
       recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object Data, View view, int position) {
                Class  aClass =map.get(homes.get(position).getTag());
                if(aClass==null){
                    UiUtils.snackbarText("功能升级改版中...");
                    return;
                }
                String  phone = MyApplication.hxt_setting_config.getString("phone", "");
                String  pwd = MyApplication.hxt_setting_config.getString("pwd", "");
                Intent intent=new Intent();
                if(TextUtils.isEmpty(phone)|TextUtils.isEmpty(pwd)){
                    MyApplication.aClass=aClass;
                    intent.setClass(HomeNewActivity.this, LoginActivity.class);
                } else {
                    intent.setClass(HomeNewActivity.this,aClass);
                    if(homes.get(position).getTag().equalsIgnoreCase(MY)){
                    intent.putExtra("moduletitle", homeInfo.getAccount());
                    }
                }
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(Object Data, View view, int position) {

            }
        });

              recyclerView.setVisibility(View.GONE);
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
        map.put(EXAMS, null);

    }
    private void initoolbar() {
       back.setVisibility(View.GONE);
        title.setText("湘直宣传云");

    }

    @Override
    public void setAdapter(BaseRecyclerAdapter adapter) {

    }

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
        list.add(Manifest.permission.READ_PHONE_STATE);
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
}

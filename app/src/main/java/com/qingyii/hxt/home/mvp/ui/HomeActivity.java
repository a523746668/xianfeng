package com.qingyii.hxt.home.mvp.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.leethink.badger.BadgeUtil;
import com.qingyii.hxt.BuildConfig;
import com.qingyii.hxt.GuanLiInActivity;
import com.qingyii.hxt.KaoChangType02Activity;
import com.qingyii.hxt.LoginActivity;
import com.qingyii.hxt.MainWebActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.TrainActivity;
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxt.base.app.EventBusTags;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.utils.WindowUtils;
import com.qingyii.hxt.circle.CircleMainActivity;

import com.qingyii.hxt.home.di.component.DaggerHomeComponent;
import com.qingyii.hxt.home.di.module.HomeModule;
import com.qingyii.hxt.home.mvp.model.entity.FakeData;
import com.qingyii.hxt.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxt.home.mvp.presenter.HomePresenter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.meeting.mvp.ui.activity.MeetingActivity;
import com.qingyii.hxt.myActivity;
import com.qingyii.hxt.myShezhiActivity;
import com.qingyii.hxt.neiKanActivity;
import com.qingyii.hxt.notice.mvp.ui.activity.NoticeDetailsActivity;
import com.qingyii.hxt.notify.mvp.ui.activity.NotifyActivity;
import com.qingyii.hxt.pojo.Update;
import com.qingyii.hxt.shuJiaActivity;
import com.qingyii.hxt.util.NetworkImageHolderView;
import com.qingyii.hxt.util.UpdateUtil;
import com.qingyii.hxt.wmcj.mvp.ui.WMCJActivity;
import com.qingyii.hxt.wmcj.mvp.ui.WorkParkDetailsActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhf.Util.Global;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.qingyii.hxt.R.id.text99;
import static com.qingyii.hxt.base.app.GlobalConsts.ACTIVITY;
import static com.qingyii.hxt.base.app.GlobalConsts.ANNOUNCEMENT;
import static com.qingyii.hxt.base.app.GlobalConsts.BOOK;
import static com.qingyii.hxt.base.app.GlobalConsts.CIRCLE;
import static com.qingyii.hxt.base.app.GlobalConsts.DJFDY;
import static com.qingyii.hxt.base.app.GlobalConsts.DOCUMENTARY;
import static com.qingyii.hxt.base.app.GlobalConsts.EXAMS;
import static com.qingyii.hxt.base.app.GlobalConsts.MAGAZINE;
import static com.qingyii.hxt.base.app.GlobalConsts.MEETING;
import static com.qingyii.hxt.base.app.GlobalConsts.MORE;
import static com.qingyii.hxt.base.app.GlobalConsts.MY;
import static com.qingyii.hxt.base.app.GlobalConsts.NOTIFY;
import static com.qingyii.hxt.base.app.GlobalConsts.TRAIN;
import static com.qingyii.hxt.base.app.GlobalConsts.WMCJ;

/**
 * 主页
 */
public class HomeActivity extends BaseActivity<HomePresenter> implements CommonContract.HomeInfoView {
    @BindView(R.id.home_right_button)
    Button homeRightButton;
    @BindView(R.id.home_title)
    TextView homeTitle;
    @BindView(R.id.home_system_notice_count)
    TextView homeSystemNoticeCount;
    @BindView(R.id.home_toolbar)
    RelativeLayout homeToolbar;
    @BindView(R.id.home_ad_convenientbanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.home_viewFilter)
    ViewFlipper mFilter;
    @BindView(R.id.home_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.home_tv_banner)
    TextView tv_banner;
    @BindView(R.id.home_banner_close)
    Button homeBannerClose;
    @BindView(R.id.home_banner_rl)
    RelativeLayout banner_rl;
     @BindView(R.id.home_new)
     ImageView  iv;
    UpdateUtil util=new UpdateUtil();

    private List<HomeInfo.AdBean.INDEXBANNERBean> topList;
    private Map<String, Class<?>> map = new HashMap<>();
    private Context context;
    private HomeInfo homeInfo;
    private RxPermissions mRxPermissions;
    private int badgeCount;
    private boolean is_first = true;
    private ArrayList<FakeData.DataBeanX.DataBean> fakeDatas=new ArrayList<>();

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .injcet(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        initHomeInfoName();
     //   CrashHandler.getCrashHandler().init(this);

        return R.layout.activity_home;
    }

    private void initHomeInfoName() {
        map.put(NOTIFY, NotifyActivity.class);
        map.put(MEETING, MeetingActivity.class);
        map.put(TRAIN, TrainActivity.class);
        map.put(WMCJ, WMCJActivity.class);
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

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_banner.setSelected(true);
        context = this;
        util.Updatehome(this,true);

    }

    @Override
    public void setAdapter(BaseRecyclerAdapter adapter) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<HomeInfo.AccountBean.ModulesBean>() {

            @Override
            public void onItemClick(HomeInfo.AccountBean.ModulesBean data, View view, int position) {
                if (MyApplication.userUtil != null && MyApplication.userUtil.getId() != 0) {

                    if(data.getMark().startsWith("task")){
                        Intent intent=new Intent(context,map.get(WMCJ));
                        String num=data.getMark().substring(data.getMark().lastIndexOf("/")+1);
                        Global.userid=Integer.valueOf(num);
                        Log.i("tmdwmcj",num);
                        startActivity(intent);
                        return;
                    }
                    Class clazz = map.get(data.getMark());
                    if (clazz == null) {
                        showMessage(getString(R.string.function_is_developing));
                        return;
                    }
                    if(data.getMark().equals(DOCUMENTARY)&&MyApplication.userUtil != null && MyApplication.userUtil.getJoin_doc() != 1) {
                            Toast.makeText(context, "对不起，您没有参与纪实审核", Toast.LENGTH_LONG).show();
                            return;
                    }


                    Intent it = new Intent(context, map.get(data.getMark()));
                    if (data.getMark().equals(MY))
                        it.putExtra("moduletitle", homeInfo.getAccount());
                    startActivity(it);
                } else
                    startActivity(new Intent(context, LoginActivity.class));
            }

            @Override
            public void onItemLongClick(HomeInfo.AccountBean.ModulesBean Data, View view, int position) {

            }
        });
    }

    @Override
    public void updateUI(HomeInfo homeInfo) {
        this.homeInfo = homeInfo;
        homeTitle.setText(homeInfo.getAccount().getAlias());
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
                    setSystemNotifyCount(value);
                    badgeCount += value;
                }
                updateMessageCount(field[j].getName(), EventBusTags.UpdateType.no, homeInfo.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initAd(homeInfo.getAd());
        mPresenter.requesrfakedata();

    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public void getfakedatasuceess(FakeData fakeData) {
             fakeDatas.clear();
             fakeDatas.addAll(fakeData.getData().getData());
             initViewFilter();

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

//    @Subscriber(tag = EventBusTags.HOME, mode = ThreadMode.MAIN)
//    public void onEvent(Message message) {
//        switch (message.what) {
//            case EventBusTags.UPDATE_HOME_MEETING_COUNT_DEL:
//                try {
//                    updateMessageCount((String) message.obj, EventBusTags.UpdateType.del, homeInfo.getMessage());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case EventBusTags.UPDATE_HOME_NOTIFY_COUNT_DEL:
//                try {
//                    updateMessageCount((String) message.obj, EventBusTags.UpdateType.del, homeInfo.getMessage());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//    }

    private void updateMessageCount(String name, EventBusTags.UpdateType type, Object model) throws Exception {
        for (HomeInfo.AccountBean.ModulesBean modulesBean : getMessageCount()) {
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
                mRecyclerView.getAdapter().notifyDataSetChanged();
                BadgeUtil.sendBadgeNotification(null, 100, this, badgeCount, badgeCount);
            }
        }
    }

    private List<HomeInfo.AccountBean.ModulesBean> getMessageCount() {
        return (List<HomeInfo.AccountBean.ModulesBean>) ((BaseRecyclerAdapter) mRecyclerView.getAdapter()).getData();
    }

    private void setSystemNotifyCount(Integer value) {
        if (value > 0) {
            homeSystemNoticeCount.setVisibility(View.VISIBLE);
            homeSystemNoticeCount.setText(value.toString());
        } else {
            homeSystemNoticeCount.setVisibility(View.GONE);
        }
    }


    //滚动广告
    private void initViewFilter() {
       // FakeData[] datas = ((App) getApplicationContext()).getAppComponent().gson().fromJson(getFakeData(), FakeData[].class);
        //List<FakeData> list = Arrays.asList(datas);

        if (fakeDatas.size() <= 0) return;
        Log.i("tmdfakedata",fakeDatas.size()+"");
        LinearLayout view = null;
        for (int i = 0; i <=fakeDatas.size() - 1; i++) {
            if (i % 2 == 0) {
                view = new LinearLayout(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.gravity = Gravity.CENTER_VERTICAL;
                view.setLayoutParams(lp);
                view.setOrientation(LinearLayout.VERTICAL);
            }
            final View inflate = View.inflate(this, R.layout.recent_news_item, null);
            ((TextView) inflate.findViewById(R.id.recent_news_tv1)).setText(fakeDatas.get(i).getName());
           // ((TextView) inflate.findViewById(R.id.recent_news_tv2)).setText(list.get(i).getTime());
            int finalI = i;
            inflate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fakeDatas.get(finalI).getType().equalsIgnoreCase("activity")){
                               Intent intent=new Intent(HomeActivity.this, WorkParkDetailsActivity.class);
                                 intent.putExtra("actid",fakeDatas.get(finalI).getId());
                                 startActivity(intent);
                        }
                    }
                });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            inflate.setLayoutParams(layoutParams);
            view.addView(inflate);
            if (i % 2 == 1 || i == fakeDatas.size() - fakeDatas.size() % 2) {
                mFilter.addView(view);
            }
        }
        //是否自动开始滚动
        mFilter.setAutoStart(true);
        //滚动时间
        mFilter.setFlipInterval(4000);
        //开始滚动
        mFilter.startFlipping();
        //出入动画
        mFilter.setOutAnimation(this, R.anim.push_up_out);
        mFilter.setInAnimation(this, R.anim.push_down_in);

    }

    private void initAd(HomeInfo.AdBean ad) {
        ArrayList<String> bannerList = new ArrayList<>();
        for (HomeInfo.AdBean.INDEXBANNERBean indexbannerBean : ad.getINDEX_BANNER()) {
            bannerList.add(indexbannerBean.getAdbanner());
        }
        List<HomeInfo.AdBean.INDEXBANNERBean> aDataBeanlist = ad.getINDEX_BANNER();
        topList = ad.getINDEX_TOP();
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, bannerList)
                .setPageIndicator(new int[]{R.drawable.indicator_unselected, R.drawable.indicator_selectedr})
                .setOnItemClickListener(position -> {
                    if (!aDataBeanlist.get(position).getAdlink().equals("")) {
                        Intent intent = new Intent(HomeActivity.this, MainWebActivity.class);
                         intent.putExtra("flag",100);
                        intent.putExtra("aDataBean", aDataBeanlist.get(position));
                        startActivity(intent);
                    } else {
                        Toast.makeText(HomeActivity.this, "无内容", Toast.LENGTH_SHORT).show();
                    }
                });
        //当广告大于一张时
        if (bannerList.size() > 1)
            //开始自动翻页 设置延迟
            convenientBanner.startTurning(5000);
        if (topList.size() > 0) {
            banner_rl.setVisibility(View.VISIBLE);
            //防止text值太短，不滚动
            tv_banner.setText(topList.get(0).getAdtitle() + "　　　　　　　　　　" + topList.get(0).getAdtitle());
            tv_banner.setSelected(true);
        } else
            banner_rl.setVisibility(View.GONE);
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
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


        mPresenter.requestHomeInfo(is_first,this);
        if(is_first)is_first =false;
        //极光设置
        JPushInterface.onResume(this);
        //Log.e("onResume()", "被执行"+ BuildConfig.DEBUG);
        //HintUtil.showtoast(this,BuildConfig.DEBUG+"-----");

    }

    @Override
    protected void onPause() {
        super.onPause();
        //极光设置
        JPushInterface.onPause(this);
        //停止翻页
        convenientBanner.stopTurning();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        WindowUtils.showExitDialog(this);
    }


    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.home_right_layout, R.id.home_banner_close, R.id.home_banner_rl,R.id.home_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_right_layout:
                Intent system = new Intent(this, NotifyActivity.class);
                system.putExtra(EventBusTags.NOTIFY_SYSTEM, EventBusTags.NOTIFY_SYSTEM);
                startActivity(system);
                break;
            case R.id.home_banner_close:
                banner_rl.setVisibility(View.GONE);
                break;
            case R.id.home_banner_rl:
                if (topList.size() > 0)
                    if (!topList.get(0).getAdlink().equals("")) {
                        Intent intent = new Intent(this, MainWebActivity.class);
                        intent.putExtra("flag",100);
                        intent.putExtra("aDataBean", topList.get(0));
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "无内容", Toast.LENGTH_SHORT).show();
                    }
                break;
            case R.id.home_new:
                Intent intent=new Intent(HomeActivity.this,LattestNewsActivity.class);
                intent.putExtra("fakedatas",fakeDatas);
                startActivity(intent);

                break;
        }
    }

    private String getFakeData() {
        return "[\n" +
                "    {\n" +
                "        \"name\": \"平台给你推送了一条系统消息，请查阅。\",\n" +
                "        \"time\": \"刚刚\"\n" +
                "    },\n" +
                "\n" +
                "    {\"name\": \"陈梗发布最新动态“湖南总队召开党组中心组学习”。\",\n" +
                "        \"time\": \"3分钟前\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"余敏在同事圈发布了一条活动日志。\",\n" +
                "        \"time\": \"10分钟前\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "        \"name\": \"黄友洪发表了活动小结：“党支部召开主题党日… \",\n" +
                "        \"time\": \"半小时前\"\n" +
                "    },   \n" +
                "    {\n" +
                "        \"name\": \"谭曙光发布了最新动态“党委专题研究文明创建工作”\",\n" +
                "        \"time\": \"3小时前\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "        \"name\": \"李小华为张爱梅的同事圈工作日志点了个赞。\",\n" +
                "        \"time\": \"6小时前\"\n" +
                "    },    \n" +
                "    {\n" +
                "        \"name\": \"张海洋收藏了学习图书《习近平用典》\",\n" +
                "        \"time\": \"12小时前\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "        \"name\": \"李莉在手机端发起了“点赞中央新一届领导集体”\",\n" +
                "        \"time\": \"1天前\"\n" +
                "    },    \n" +
                "    {\n" +
                "        \"name\": \"刘菲发送了“党建工作座谈会的”的详情\",\n" +
                "        \"time\": \"2天前\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "        \"name\": \"周小云参加了“两学一做知识测试闯关”过了第2关\",\n" +
                "        \"time\": \"3天前\"\n" +
                "    },\n" +
                "]\n" +
                "\n";
    }


    /**
     *   检查版本号更新
     */
    public void Update() {

        OkHttpUtils
                .post()
                .url(XrjHttpClient.URL_PR + "/ server")
//                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
//                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new UpdateCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 //Timber.e("Update_onError", e.toString());
//                                 Toast.makeText(FirstActivity.this, "网络异常--请检查网络", Toast.LENGTH_LONG).show();
                                Log.i("tmderror",e.getMessage());
                             }

                             @Override
                             public void onResponse(Update response, int id) {
                                 Log.e("UpdateCallback", response.getError_msg()+"--------"+response.getData().getDownload());
                                 switch (response.getError_code()) {
                                     case 0:
                                         try {
//                                             PackageManager pm = FirstActivity.this.getPackageManager();
//                                             PackageInfo pi = pm.getPackageInfo(FirstActivity.this.getPackageName(), 0);
                                             PackageInfo packageInfo = HomeActivity.this.getPackageManager().getPackageInfo(HomeActivity.this.getPackageName(), 0);
                                             String versionName = packageInfo.versionName;
                                             int versioncode = packageInfo.versionCode;
                                             //Toast.makeText(FirstActivity.this, "软件版本号为" + versionName, Toast.LENGTH_LONG).show();
                                             if (versioncode < Integer.parseInt(response.getData().getVersion())) {


                                                 View view= LayoutInflater.from(HomeActivity.this).inflate(R.layout.drafit,null);
                                                 AlertDialog dialog=new AlertDialog.Builder(HomeActivity.this)
                                                         .setView(view)
                                                         .setCancelable(false)
                                                         .show();
                                                 Button qr= (Button) view.findViewById(R.id.draftqueren);
                                                 Button qx= (Button) view.findViewById(R.id.draftquxiao);
                                                 EditText name= (EditText) view.findViewById(R.id.draftname);
                                                 name.setVisibility(View.GONE);
                                                 TextView textView= (TextView) view.findViewById(text99);
                                                 textView.setVisibility(View.VISIBLE);
                                                 TextView tishi= (TextView) view.findViewById(R.id.tishi);
                                                 ProgressBar bar= (ProgressBar) view.findViewById(R.id.updateprogress);
                                                 TextView  bfb= (TextView) view.findViewById(R.id.baifenbi);

                                                 qr.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         //  Toast.makeText(FirstActivity.this, "软件版本较低，请先更新再继续使用", Toast.LENGTH_LONG).show();
                                                        /* Intent intent = new Intent();
                                                         intent.setAction("android.intent.action.VIEW");

                                                         Uri content_url = Uri.parse(response.getData().getDownload() + "");
                                                         intent.setData(content_url);
                                                         startActivity(intent);
                                                         dialog.dismiss();  */

                                                         qr.setVisibility(View.GONE);
                                                         qx.setVisibility(View.GONE);
                                                         textView.setVisibility(View.GONE);
                                                         tishi.setVisibility(View.VISIBLE);
                                                         bar.setVisibility(View.VISIBLE);
                                                         bfb.setVisibility(View.VISIBLE);
                                                         Log.i("tmdxiazai",response.getData().getDownload());
                                                         OkHttpUtils.get()
                                                                 .url(response.getData().getDownload())
                                                                 .build()
                                                                 .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), versionName+"xfypt.apk") {

                                                                     @Override
                                                                     public void onError(Call call, Exception e, int id) {
                                                                            Log.i("tmdxiazai",e.getMessage().toString());
                                                                     }

                                                                     @Override
                                                                     public void onResponse(File response, int id) {
                                                                         installApk(HomeActivity.this,response);

                                                                     }

                                                                     @Override
                                                                     public void inProgress(float progress, long total, int id) {
                                                                         super.inProgress(progress, total, id);
                                                                         int  i= (int) (progress*100);
                                                                         bfb.setText(i+"%");
                                                                         bar.setProgress(i);
                                                                     }
                                                                 });


                                                     }
                                                 });
                                                 qx.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         if(dialog!=null&&dialog.isShowing())
                                                             dialog.dismiss();
                                                     }
                                                 });



                                             }
                                         } catch (Exception e) {
                                             Log.e("VersionInfo", "Exception", e);
                                         }
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }



    //判断SD卡是否可用
    public   boolean issafe(){
        return Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 安装apk
     */
    private void installApk(Context mContext, File file) {
      ;
        Intent it = new Intent(Intent.ACTION_VIEW);
         if(Build.VERSION.SDK_INT>=24){
             Uri apkUri = FileProvider.getUriForFile(getApplicationContext(),
                     BuildConfig.APPLICATION_ID + ".fileprovider", file);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
             it.setDataAndType(apkUri, "application/vnd.android.package-archive");
         }   else {
             Uri fileUri = Uri.fromFile(file);
             it.setDataAndType(fileUri, "application/vnd.android.package-archive");
             it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 防止打不开应用
         }
        mContext.startActivity(it);
    }

    private abstract class UpdateCallback extends com.zhy.http.okhttp.callback.Callback<Update> {

        @Override
        public Update parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("UpdateCallback_String", result);
            Update update = new Gson().fromJson(result, Update.class);
            return update;
        }
    }


}

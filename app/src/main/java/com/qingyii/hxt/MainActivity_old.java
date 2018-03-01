package com.qingyii.hxt;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qingyii.hxt.adapter.MainModuleGridAdapter;
import com.qingyii.hxt.adapter.SubscriptionGridViewAdapter;
import com.qingyii.hxt.bean.NewsType;
import com.qingyii.hxt.circle.CircleMainActivity;
import com.qingyii.hxt.customview.MyGridView;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.httpway.ADUpload;
import com.qingyii.hxt.meeting.mvp.ui.activity.MeetingActivity;
import com.qingyii.hxt.notice.mvp.ui.activity.NoticeActivity;
import com.qingyii.hxt.notify.mvp.ui.activity.NotifyActivity;
import com.qingyii.hxt.pojo.Advertisement;
import com.qingyii.hxt.pojo.CircleMark;
import com.qingyii.hxt.pojo.DocumentaryStatus;
import com.qingyii.hxt.pojo.MainModuleGrid;
import com.qingyii.hxt.pojo.ModuleTitle;
import com.qingyii.hxt.util.NetworkImageHolderView;
import com.readystatesoftware.viewbadger.BadgeView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 主页
 */
public class MainActivity_old extends BaseActivity implements OnClickListener {
    private HashMap moduleMap;

    private ImageView shezhi_btn;
    /**
     * AbSlidingPlayView 失效，注释相关
     */
//    AbSlidingPlayView mAbSlidingPlayView = null;
//    private MyGridView main_GridView;
    private SubscriptionGridViewAdapter myAdapter;
    private TextView tv_banner;
    private ArrayList<NewsType> lists = new ArrayList<NewsType>();
    private RelativeLayout banner_rl;

    private MyGridView main_gridview;
    private MainModuleGridAdapter mainModuleGridAdapter;
    private List<MainModuleGrid> mainModuleGridList = new ArrayList<>();

    private ImageView banner_close;
    private LinearLayout main_ll;
    private BadgeView nk_badge = null, sj_badge = null, hd_badge = null, gc_badge = null;
    //private int banerImg[]={R.drawable.tupian,R.drawable.tupian1,R.drawable.tupian2,R.drawable.tupian3};
    private View mPlayView;
    /**
     * 滚动广告数据源头部
     */
    private ConvenientBanner convenientBanner;
    private List<String> bannerList = new ArrayList<>();
    private List<Advertisement.DataBean> aBannerList = new ArrayList<>();
    private List<Advertisement.DataBean> aDataBeanlist = new ArrayList<>();
    /**
     * 底部广告
     */
    private List<Advertisement.DataBean> topList = new ArrayList<>();

    private boolean isInThisView = false;
    ;
//    private ArrayList<Advert> list2 = new ArrayList<Advert>();

    private String[] needPermission_list;

    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 221;
    public static final int READ_PHONE_STATE_REQUEST_CODE = 222;
    public static final int ACCESS_NETWORK_STATE_REQUEST_CODE = 223;
    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 224;
    public static final int ACCESS_COARSE_LOCATION = 225;
    public static final int ALL_REQUEST_CODE = 220;

    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 1:
                    for (int i = 0; i < aDataBeanlist.size(); i++) {
                        switch (aDataBeanlist.get(i).getMark()) {
                            case "INDEX_BANNER":
                                bannerList.add(aDataBeanlist.get(i).getAdbanner());
                                aBannerList.add(aDataBeanlist.get(i));
                                break;
                            case "INDEX_TOP":
                                topList.add(aDataBeanlist.get(i));
                                break;
                            case "INDEX_BOTTOM":
                                break;
                            default:
                                break;
                        }
                    }
                    convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                        @Override
                        public NetworkImageHolderView createHolder() {
                            return new NetworkImageHolderView();
                        }
                    }, bannerList)
                            .setPageIndicator(new int[]{R.drawable.indicator_unselected, R.drawable.indicator_selectedr})
                            .setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    if (!aDataBeanlist.get(position).getAdlink().equals("")) {
                                        Intent intent = new Intent(MainActivity_old.this, MainWebActivity.class);
                                        intent.putExtra("aDataBean", aBannerList.get(position));
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity_old.this, "无内容", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    //当广告只有一张时
                    if (bannerList.size() > 1)
                        //开始自动翻页 设置延迟
                        convenientBanner.startTurning(3000);
//                    else
                    //关闭手动切换
//                        convenientBanner.setManualPageable(false);


                    if (topList.size() > 0) {
                        banner_rl.setVisibility(View.VISIBLE);
                        tv_banner.setText(topList.get(0).getAdtitle()
//                                + topList.get(0).getAdwords()
                        );
                    } else
                        banner_rl.setVisibility(View.GONE);
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    MyApplication myApplication = new MyApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatiChuangguanActivity.mLevel.clear();
        setContentView(R.layout.activity_main);
        initUI();
        initData();
        isInThisView = true;
        GetPermission();
    }


    private void GetPermission() {

        System.out.println("------获取权限-------");
        //读取是否获取权限

        if (Build.VERSION.SDK_INT >= 23) {

            boolean has_WRITE_EXTERNAL_STORAGE = false;
            boolean has_READ_PHONE_STATE = false;
            boolean has_READ_EXTERNAL_STORAGE = false;
            boolean has_ACCESS_NETWORK_STATE = false;

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                has_READ_EXTERNAL_STORAGE = false;
                System.out.println("检查 READ_EXTERNAL_STORAGE");
            } else {
                has_READ_EXTERNAL_STORAGE = true;
            }

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                has_WRITE_EXTERNAL_STORAGE = false;
                System.out.println("检查 WRITE_EXTERNAL_STORAGE");
            } else
                has_WRITE_EXTERNAL_STORAGE = true;

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                has_READ_PHONE_STATE = false;
                System.out.println("检查 READ_PHONE_STATE");
            } else
                has_READ_PHONE_STATE = true;

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                has_ACCESS_NETWORK_STATE = false;


                System.out.println("检查 ACCESS_NETWORK_STATE");

            } else
                has_ACCESS_NETWORK_STATE = true;

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                has_ACCESS_NETWORK_STATE = false;
                System.out.println("检查 ACCESS_NETWORK_STATE");
            } else
                has_ACCESS_NETWORK_STATE = true;

            //添加权限
            needPermission_list = new String[]{""};

            if (!has_READ_EXTERNAL_STORAGE)
                addString(android.Manifest.permission.READ_EXTERNAL_STORAGE);

            if (!has_WRITE_EXTERNAL_STORAGE)
                addString(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (!has_READ_PHONE_STATE)
                addString(android.Manifest.permission.READ_PHONE_STATE);

            if (!has_ACCESS_NETWORK_STATE)
                addString(android.Manifest.permission.ACCESS_NETWORK_STATE);

            if (!has_ACCESS_NETWORK_STATE)
                addString(android.Manifest.permission.CAMERA);

            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, needPermission_list,
                    ALL_REQUEST_CODE);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {


        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                savePermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case READ_PHONE_STATE_REQUEST_CODE:
                savePermission(android.Manifest.permission.READ_PHONE_STATE);
                break;
            case READ_EXTERNAL_STORAGE_REQUEST_CODE:
                savePermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
            case ACCESS_NETWORK_STATE_REQUEST_CODE:
                savePermission(android.Manifest.permission.ACCESS_NETWORK_STATE);
                break;
//            case ALL_REQUEST_CODE: {
//
//                for (int i = 0; i < permissions.length; i++) {
//
//                    if (permissions[i].equals(android.Manifest.permission.READ_PHONE_STATE)
//                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        //用户同意使用 获取 imei 这样的 设备 id
//                        JSession.getInstance().startPushWork();
//                    }
//                }
//            }
//            break;
            default:
                break;
        }
    }

    public void savePermission(String permissionName) {

    }

    private void addString(String string) {
        if (needPermission_list.length == 1 && needPermission_list[0].length() == 0) {  //初始化时
            needPermission_list = new String[]{string};
        } else {
            needPermission_list = Arrays.copyOf(needPermission_list, needPermission_list.length + 1);
            needPermission_list[needPermission_list.length - 1] = string;

            for (String s : needPermission_list) {
                System.out.println(s + "---  lenght = " + needPermission_list.length);
            }
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //极光设置
        isInThisView = true;
        JPushInterface.onResume(this);
        Log.e("onResume()", "被执行");
        getDateStatus();
        //loadInformList();
        setInformMark();
        loadCircleMark();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.e("onPause()", "被执行");
        //极光设置
        JPushInterface.onPause(this);
        //停止翻页
        convenientBanner.stopTurning();
        isInThisView = false;
    }


    private TextView textTitle;

    @SuppressWarnings("deprecation")
    private void initUI() {
        convenientBanner = (ConvenientBanner) findViewById(R.id.main_ad_convenientbanner);

        textTitle = (TextView) findViewById(R.id.main_title);

        main_gridview = (MyGridView) findViewById(R.id.main_gridview);

//        iv_xuanze = (ImageView) findViewById(R.id.iv_xuanze);
        main_ll = (LinearLayout) findViewById(R.id.main_ll);
        banner_rl = (RelativeLayout) findViewById(R.id.banner_rl);
        banner_close = (ImageView) findViewById(R.id.banner_close);
        tv_banner = (TextView) findViewById(R.id.tv_banner);
        shezhi_btn = (ImageView) findViewById(R.id.shezhi_btn);
//        main_GridView = (MyGridView) findViewById(R.id.main_GridView);
//        main_GridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        myAdapter = new SubscriptionGridViewAdapter(this, lists);
//        main_GridView.setAdapter(myAdapter);

        shezhi_btn.setOnClickListener(this);
        banner_close.setOnClickListener(this);
        tv_banner.setOnClickListener(this);
//        main_GridView.setOnItemClickListener(this);
    }

    private void initData() {
        // TODO Auto-generated method stub
        mainModuleGridAdapter = new MainModuleGridAdapter(this, mainModuleGridList);
        main_gridview.setAdapter(mainModuleGridAdapter);

        main_gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (mainModuleGridList.get(i).getModule()) {
                    case "magazine":
                        if (MyApplication.userUtil.getId() != 0) {
                            startActivity(new Intent(MainActivity_old.this, neiKanActivity.class));
                        } else
                            startActivity(new Intent(MainActivity_old.this, LoginActivity.class));
                        break;
                    case "book":
                        if (MyApplication.userUtil.getId() != 0) {
                            startActivity(new Intent(MainActivity_old.this, shuJiaActivity.class)); //shuJiaActivity.class;
                        } else
                            startActivity(new Intent(MainActivity_old.this, LoginActivity.class));
                        break;
                    case "exams":
//                    Toast.makeText(this, "敬请期待", Toast.LENGTH_LONG).show();
                        if (MyApplication.userUtil.getId() != 0)
                            //Intent it=new Intent(HomeActivity.this,KaoChangTypesActivity.class);
                            startActivity(new Intent(MainActivity_old.this, KaoChangType02Activity.class));
                        else
                            startActivity(new Intent(MainActivity_old.this, LoginActivity.class));
                        break;
                    case "notify":
                        startActivity(new Intent(MainActivity_old.this, NotifyActivity.class));
                        break;
                    case "announcement":
                        startActivity(new Intent(MainActivity_old.this, NoticeActivity.class));
                        break;
                    case "train":
//                      tv_peixun_tip.setVisibility(NotifyListView.GONE);
                        startActivity(new Intent(MainActivity_old.this, TrainActivity.class));
                        break;
                    case "circle":
//                      tv_peixun_tip.setVisibility(NotifyListView.GONE);
                        startActivity(new Intent(MainActivity_old.this, CircleMainActivity.class));
                        break;
                    case "documentary":
//                tv_peixun_tip.setVisibility(NotifyListView.GONE);
//                if (MyApplication.hxt_setting_config.getInt("UserLevel", 0) == 0) {
//                    Intent intent = new Intent(HomeActivity.this, GuanLiActivity.class);
//                    intent.putExtra("UIparameter", "My");
//                    startActivity(intent);
//                } else
                        if (MyApplication.userUtil != null && MyApplication.userUtil.getJoin_doc() != 1)
                            Toast.makeText(MainActivity_old.this, "对不起，您没有参与纪实审核", Toast.LENGTH_LONG).show();
                        else
                            startActivity(new Intent(MainActivity_old.this, GuanLiInActivity.class));
                        break;
                    case "my":
                        if (MyApplication.userUtil.getId() != 0) {
                            Intent intent = new Intent(MainActivity_old.this, myActivity.class);
                            intent.putExtra("moduletitle", moduletitle);
                            if (moduletitle != null)
                                startActivity(intent);
                        } else
                            startActivity(new Intent(MainActivity_old.this, LoginActivity.class));
                        break;
                    case "meeting":
                        try {
                            if (MyApplication.userUtil.getId() != 0) {
                                Intent intent = new Intent(MainActivity_old.this, MeetingActivity.class);
                                intent.putExtra("moduletitle", moduletitle);
                                if (moduletitle != null)
                                    startActivity(intent);
                            } else
                                startActivity(new Intent(MainActivity_old.this, LoginActivity.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        getModuleTitle();

        //网络加载广告
        ADUpload adUpload = ADUpload.getADUpload();
        adUpload.adList(this, aDataBeanlist, handler);
    }

    @Override
    public void onClick(View v) {
        Intent it;
        try {
            switch (v.getId()) {
                case R.id.shezhi_btn:   //设置
                    if (MyApplication.userUtil != null)
                        startActivity(new Intent(MainActivity_old.this, myShezhiActivity.class));
                    else
                        startActivity(new Intent(MainActivity_old.this, LoginActivity.class));
                    break;
                case R.id.banner_close: //关闭字条广告
                    banner_rl.setVisibility(View.GONE);
                    break;
                case R.id.tv_banner:    //字条广告
                    if (topList.size() > 0)
                        if (!topList.get(0).getAdlink().equals("")) {
                            Intent intent = new Intent(MainActivity_old.this, MainWebActivity.class);
                            intent.putExtra("aDataBean", topList.get(0));
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity_old.this, "无内容", Toast.LENGTH_SHORT).show();
                        }
//              Intent intnet=new Intent(HomeActivity.this,SpannableStringActivity.class);
//              startActivity(intnet);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Log.e("MainActivity点击事件", e.toString());
        }
    }

    private ModuleTitle.DataBean moduletitle;

    /**
     * 我的模块数据
     */
    public void getModuleTitle() {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.URL_PR + "/account/setting")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new ModuleTitleCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Status_onError", e.toString());
//                                 Toast.makeText(GuanLiInActivity.this, "网络异常，请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(ModuleTitle response, int id) {
                                 Log.e("StatusCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         moduletitle = response.getData();

                                         textTitle.setText(moduletitle.getAlias() + "");
                                         MainModuleGrid mainModuleGrid = null;
                                         for (int i = 0; i < moduletitle.getModules().size(); i++)
                                             switch (moduletitle.getModules().get(i)) {
                                                 case "magazine":
                                                     mainModuleGrid = new MainModuleGrid();
                                                     mainModuleGrid.setModule("magazine");
                                                     mainModuleGrid.setModuleImage(R.drawable.magazine);
                                                     mainModuleGrid.setModuleText("刊物");

                                                     mainModuleGridList.add(mainModuleGrid);
                                                     break;
                                                 case "book":
                                                     mainModuleGrid = new MainModuleGrid();
                                                     mainModuleGrid.setModule("book");
                                                     mainModuleGrid.setModuleImage(R.drawable.book);
                                                     mainModuleGrid.setModuleText("书架");

                                                     mainModuleGridList.add(mainModuleGrid);
                                                     break;
                                                 case "exams":
                                                     mainModuleGrid = new MainModuleGrid();
                                                     mainModuleGrid.setModule("exams");
                                                     mainModuleGrid.setModuleImage(R.drawable.exams);
                                                     mainModuleGrid.setModuleText("考场");

                                                     mainModuleGridList.add(mainModuleGrid);
                                                     break;
                                                 case "notify":
                                                     mainModuleGrid = new MainModuleGrid();
                                                     mainModuleGrid.setModule("notify");
                                                     mainModuleGrid.setModuleImage(R.mipmap.main_inform);
                                                     mainModuleGrid.setModuleText("通知");

                                                     mainModuleGridList.add(mainModuleGrid);
                                                     break;
                                                 case "announcement":
                                                     mainModuleGrid = new MainModuleGrid();
                                                     mainModuleGrid.setModule("announcement");
                                                     mainModuleGrid.setModuleImage(R.drawable.announcement);
                                                     mainModuleGrid.setModuleText("公告");

                                                     mainModuleGridList.add(mainModuleGrid);
                                                     break;
                                                 case "train":
                                                     mainModuleGrid = new MainModuleGrid();
                                                     mainModuleGrid.setModule("train");
                                                     mainModuleGrid.setModuleImage(R.mipmap.main_train);
                                                     mainModuleGrid.setModuleText("培训");

                                                     mainModuleGridList.add(mainModuleGrid);
                                                     break;
                                                 case "documentary":
                                                     mainModuleGrid = new MainModuleGrid();
                                                     mainModuleGrid.setModule("circle");
                                                     mainModuleGrid.setModuleImage(R.drawable.circle);
                                                     mainModuleGrid.setModuleText("同事圈");

                                                     mainModuleGridList.add(mainModuleGrid);

                                                     mainModuleGrid = new MainModuleGrid();
                                                     mainModuleGrid.setModule("documentary");
                                                     mainModuleGrid.setModuleImage(R.drawable.documentary);
                                                     mainModuleGrid.setModuleText("纪实管理");

                                                     mainModuleGridList.add(mainModuleGrid);
                                                     break;
                                             }

                                         mainModuleGrid = new MainModuleGrid();
                                         mainModuleGrid.setModule("my");
                                         mainModuleGrid.setModuleImage(R.mipmap.main_my);
                                         mainModuleGrid.setModuleText("我的");

                                         mainModuleGridList.add(mainModuleGrid);
                                         mainModuleGrid = new MainModuleGrid();
                                         mainModuleGrid.setModule("meeting");
                                         mainModuleGrid.setModuleImage(R.mipmap.main_my);
                                         mainModuleGrid.setModuleText("会议");

                                         mainModuleGridList.add(mainModuleGrid);

                                         loadCircleMark();

                                         getDateStatus();

                                         //mainModuleGridAdapter.notifyDataSetChanged();
                                         break;
                                     default:
                                         Toast.makeText(MainActivity_old.this, response.getError_msg(), Toast.LENGTH_LONG).show();
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

    private abstract class ModuleTitleCallback extends com.zhy.http.okhttp.callback.Callback<ModuleTitle> {

        @Override
        public ModuleTitle parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("Mode JSON", result);
            ModuleTitle moduletitle = new Gson().fromJson(result, ModuleTitle.class);
            return moduletitle;
        }
    }

    /**
     * 消息数目请求
     */
    public void loadCircleMark() {

        OkHttpUtils
                .get()
                .url(XrjHttpClient.getCircleMarkUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("doc_id", MyApplication.hxt_setting_config.getInt("CircleNewID", 0) + "")
                //1. id为0或不传 ，返回最新的10条记录CircleNewID
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new CircleMarkCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("CircleMark_onError", e.toString());
                                 Toast.makeText(MainActivity_old.this, "消息数目获取失败", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(CircleMark response, int id) {
                                 Log.e("CircleMarkCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         for (int i = 0; i < mainModuleGridList.size(); i++)
                                             switch (mainModuleGridList.get(i).getModule()) {
                                                 case "circle":
                                                     mainModuleGridList.get(i).setModuleTip(response.getData().getDocumentary());
                                                     break;
                                                 default:
                                                     break;
                                             }
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class CircleMarkCallback extends com.zhy.http.okhttp.callback.Callback<CircleMark> {

        @Override
        public CircleMark parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("CircleMarkString", result);
            CircleMark circleMark = new Gson().fromJson(result, CircleMark.class);
            return circleMark;
        }
    }

    /**
     * 计算通知公告条数并标记
     */
    private void setInformMark() {
        try {
            SQLiteDatabase dbRead = MyApplication.helper.getReadableDatabase();
            int markInform = dbRead.rawQuery("select * from Inform_info where mark = ?", new String[]{"0"}).getCount();
            int markNotice = dbRead.rawQuery("select * from Notice_info where mark = ?", new String[]{"0"}).getCount();
            int markTrain = dbRead.rawQuery("select * from Inform_info where mark = ? and training_id >= 1", new String[]{"0"}).getCount();

            for (int i = 0; i < mainModuleGridList.size(); i++)
                switch (mainModuleGridList.get(i).getModule()) {
                    case "notify":
                        mainModuleGridList.get(i).setModuleTip(markInform);
                        break;
                    case "announcement":
                        mainModuleGridList.get(i).setModuleTip(markNotice);
                        break;
                    case "train":
                        mainModuleGridList.get(i).setModuleTip(markTrain);
                        break;
                    default:
                        break;
                }

            mainModuleGridAdapter.notifyDataSetChanged();
            dbRead.close();
        } catch (Exception e) {
            Log.e("数据库", "通知修改错误：" + e.toString());
        }
    }

    private DocumentaryStatus.DataBean dsDataBean;
    //年月日选择器
    private Calendar calendar = Calendar.getInstance();
    //时间
    final int YEAR = calendar.get(Calendar.YEAR), MONTH = calendar.get(Calendar.MONTH);

    /**
     * 我的纪实数据
     */
    public void getDateStatus() {
        String month = "";
        if (MONTH > 9)
            month = YEAR + "-" + MONTH;
        else
            month = YEAR + "-0" + MONTH;

        Log.e("Status_month", month);
        OkHttpUtils
                .get()
                .url(XrjHttpClient.getDocumentaryStatusUrl() + month)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .build()
                .execute(new DocumentaryStatusCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Status_onError", e.toString());
//                                 Toast.makeText(GuanLiInActivity.this, "网络异常，请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(DocumentaryStatus response, int id) {
                                 Log.e("StatusCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         dsDataBean = response.getData();

                                         for (int i = 0; i < mainModuleGridList.size(); i++)
                                             switch (mainModuleGridList.get(i).getModule()) {
                                                 case "documentary":
                                                     mainModuleGridList.get(i).setModuleTip(dsDataBean.getDocs_cnt() + dsDataBean.getCheck_cnt());
                                                     break;
                                                 default:
                                                     break;
                                             }

                                         mainModuleGridAdapter.notifyDataSetChanged();
                                         break;
                                     default:
                                         Toast.makeText(MainActivity_old.this, response.getError_msg(), Toast.LENGTH_LONG).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class DocumentaryStatusCallback extends com.zhy.http.okhttp.callback.Callback<DocumentaryStatus> {

        @Override
        public DocumentaryStatus parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("我的待定数据 JSON", result);
            DocumentaryStatus documentaryStatus = new Gson().fromJson(result, DocumentaryStatus.class);
            return documentaryStatus;
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
//        new AlertDialog.Builder(HomeActivity.this)
//                .setTitle("提醒").setIcon(R.drawable.ic_launcher)
//                .setMessage("是否退出先锋平台？")
//                .setPositiveButton("是", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        MyApplication.getInstance().AppExit();
//                        HomeActivity.this.finish();
//                    }
//                })
//                .setNegativeButton("否", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//
//                    }
//                })
//                .show();

        if (isInThisView) {

            final AlertDialog dlg = new AlertDialog.Builder(this).create();

            dlg.show();
            //Window window = dlg.getWindow();
            // *** 主要就是在这里实现这种效果的.
            // 设置窗口的内容页面,exit_app_tip.xml文件中定义view内容
            dlg.setContentView(R.layout.exit_app_tip);

            // 为确认按钮添加事件,执行退出应用操作
            TextView ok = (TextView) dlg.findViewById(R.id.btn_ok);
            ok.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    myApplication.finishActivity(MainActivity_old.this);
                    MyApplication.getInstance().AppExit();
                }
            });

            // 关闭alert对话框架
            TextView cancel = (TextView) dlg.findViewById(R.id.btn_cancel);
            cancel.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dlg.cancel();
                }
            });
        }
    }

}

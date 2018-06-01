package com.qingyii.hxtz.http;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.hss01248.dialog.StyledDialog;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.qingyii.hxtz.download.DownLoadHelper;
import com.qingyii.hxtz.pojo.UserParameter;
import com.qingyii.hxtz.util.CallAlarmReceiver;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.callback.Callback;

import org.geometerplus.zlibrary.ui.android.library.ZLAndroidApplication;

import java.io.File;
import java.io.IOException;

import java.util.Set;
import java.util.Stack;


import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Response;

//FrontiaApplication
public class MyApplication extends ZLAndroidApplication {

    private static Stack<Activity> activityStack;
    private static MyApplication singleton;
    public static DisplayImageOptions options; // 配置图片加载及显示选项
    public static SharedPreferences hxt_setting_config;
    public static DownLoadHelper helper;
    public static UserParameter.DataBean userUtil;
    public static Class aClass;


    public UserParameter.DataBean userBackups;
    public UserParameter.DataBean getUserUtil(){
//        if (userUtil==null){
//            userUtil =
//        }else {
            return userUtil;
//        }
    }

    private abstract class UserCallback extends Callback<UserParameter> {
        @Override
        public UserParameter parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("User_String", result);
            UserParameter user = new Gson().fromJson(result, UserParameter.class);
            return user;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StyledDialog.init(this);




        initImageLoader(getApplicationContext());
        singleton = this;
        // 初始化一些基础数据
        initData();
        // 极光推送
        JpushInit();
        // 百度Frontia:包括如下产品，推送，社会化分享，第三方SSO登录等
//        boolean isInit = Frontia.init(getApplicationContext(),
//                HttpUrlConfig.baiduApiKey);
//        System.out.println("百度Frontia初始化状态：" + isInit);
        // 获取sqlite库操作对像
        helper = new DownLoadHelper(this);
        ZXingLibrary.initDisplayOpinion(this);
        //CrashHandler.getCrashHandler().init(getApplicationContext());
//        initRemind();

        //reader 相关
        //initPrefs();
        //AppUtils.init(this);
    }

//    protected void initPrefs() {
//        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
//    }

    //闹钟
    public void initRemind() {
        boolean remind_switch = hxt_setting_config.getBoolean(5 + "_remind_switch", false);
        int duration = hxt_setting_config.getInt(5 + "_remind_duration", 0);
        int frequency = hxt_setting_config.getInt(5 + "_remind_frequency", 0);
        String remind_timeDatas = hxt_setting_config.getString(5 + "_remind_timeDatas", null);
        if (remind_timeDatas != null) {
            String timeDatas[] = remind_timeDatas.split(",");

            for (int i = 0; i < timeDatas.length; i++) {
                //广播跳转
                Intent intent = new Intent(MyApplication.this, CallAlarmReceiver.class);
                //启动一个广播
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.this, i, intent, 0);
                //创建闹钟
                AlarmManager alarmManager;
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, Long.parseLong(timeDatas[i]), pendingIntent);
            }
        }
    }

    // 初始化 JPush
   private void JpushInit() {
        Log.d("JPush", "[ExampleApplication] onCreate");

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        JPushInterface.setAlias(this,"test", new TagAliasCallback(){ //设置别名 用来测试

             @Override
             public void gotResult(int var1, String var2, Set<String> var3){
                 System.out.println(var2+"-----------test");
            }
        });
    }

    private void initData() {
        // 设置界面参数保存文件引用
        hxt_setting_config = getSharedPreferences("hxt_setting_config_xiangzhi", Context.MODE_PRIVATE);
    }

    // Android-Universal-Image-Loader异步加载图片组件初始化
    public static void initImageLoader(Context context) {
        // 获取SD卡的目录
        File sdCardDir = Environment.getExternalStorageDirectory();
        File cacheDir = null;
        try { 
            cacheDir = new File(sdCardDir.getCanonicalPath()
                    + HttpUrlConfig.FILE_NAME);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        options = new DisplayImageOptions.Builder()
//                .showImageForEmptyUri(R.mipmap.ic_launcher)
//                .showImageOnFail(R.mipmap.ic_launcher)
/**
                  .showStubImage(R.mipmap.image_loading)
//                .showStubImage(R.mipmap.img_loading)
                // 在ImageView加载过程中显示图片
//                .showImageOnLoading(R.mipmap.img_loading)
//                .showImageForEmptyUri(R.drawable.empty_photo)
                .showImageOnLoading(R.mipmap.image_loading)
                .showImageForEmptyUri(R.drawable.image_empty)
                // image 连接地址为空时 image_empty
                .showImageOnFail(R.mipmap.image_error)
 */
                // image加载失败
                .cacheInMemory(true)
                // 加载图片时会在内存中加载缓存
                .cacheOnDisc(true)
                // 加载图片时会在磁盘中加载缓存
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                // .displayer(new RoundedBitmapDisplayer(10))//
                // 设置用户加载图片task(这里是圆角图片显示)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options)
                // .threadPriority(Thread.NORM_PRIORITY - 2)
                // .threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
                // .discCacheFileCount(1000)//缓存图片张数
                .discCache(new UnlimitedDiscCache(cacheDir))
                // sd卡缓存目录
                // .memoryCache(new UsingFreqLimitedMemoryCache(50*1024*1024))
                .memoryCache(new WeakMemoryCache())
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // .discCacheFileNameGenerator(new YzyNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(config);
        // 清除磁盘缓存
        // ImageLoader.getInstance().clearDiscCache();
        // 支持的图片获取方式
        // String imageUri = "http://site.com/image.png"; // from Web
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        // String imageUri = "assets://image.png"; // from assets
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        // 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）

    }

    // Returns the application instance
    public static MyApplication getInstance() {
        return singleton;
    }

    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }
    /**
     * 移除Activity
     */
    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
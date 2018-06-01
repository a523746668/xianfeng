package com.qingyii.hxtz.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.home.mvp.ui.MoreActivity;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.meeting.mvp.ui.activity.MeetingActivity;
import com.qingyii.hxtz.notice.mvp.ui.activity.NoticeDetailsActivity;
import com.qingyii.hxtz.notify.mvp.ui.activity.NotifyActivity;
import com.qingyii.hxtz.pojo.JPusheExtras;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private Context mContext;
    private Vibrator mVibrator;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;

        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            MyApplication.hxt_setting_config.edit().putString("DeviceID", regId).commit();
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE) + bundle.getString(JPushInterface.EXTRA_TITLE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            //打开自定义的Activity
//            Intent i = new Intent(context, MoreActivity.class);
//            i.putExtras(bundle);
//            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }


    public void doVibrator(SET_TYPE type) {  //是否振动提示

        mVibrator = (Vibrator) mContext.getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        //mVibrator.vibrate( new long[]{500,1000,500,1000},-1);   //振动两次
        mVibrator.vibrate(new long[]{500, 1000}, -1); //一次

    }

    public void doSound(SET_TYPE type, Notification notification, Context context) {

        MediaPlayer mp = MediaPlayer.create(context, R.raw.di);

        mp.start();

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

    }

    private String getActivityName() {

        //过时的方法
//        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
//        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
//        //String shortClassName = info.topActivity.getShortClassName();    //类名
//        String className = info.topActivity.getClassName();              //完整类名
//        //String packageName = info.topActivity.getPackageName();          //包名

        //新的方法

        String className;

        if (mContext != null) {
            String contextString = mContext.toString();
            className = contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));

        } else {
            className = "";
        }


        System.out.println(className + " classname------------");
        return className;

    }

    /**
     * 接收消息后 显示通知
     *
     * @param context
     * @param mTitle
     * @param mContent
     * @param type
     */
    public void ShowNotification(Context context, String mTitle, String mContent, String extras, NOTICE_TYPE type) {
        NotificationManager notificationManager;
        Notification notification;
        notification = new Notification();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent();

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        JPusheExtras jPusheExtras = new Gson().fromJson(extras, JPusheExtras.class);
        if (jPusheExtras == null || extras == null || extras.equals("")) {
           Log.e("推送 打开页面", "MoreActivity 1");
            intent.setClass(context, MoreActivity.class);  //去到哪个Activity
        } else {
            switch (jPusheExtras.getType()) {
                case "notify"://通知 点击跳转到通知详情
//                    Log.e("推送 打开页面", "InformDetailsJPush");
                    intent.setClass(context, NotifyActivity.class);
                    break;
                case "announcement"://公告  跳转到公告详情
//                    Log.e("推送 打开页面", "NoticeDetailsJPush");
                    intent.setClass(context, NoticeDetailsActivity.class);
                    break;
                case "magazine"://内刊  跳转到内刊的期列表
//                    Log.e("推送 打开页面", "neiKanXqJPush");
                    intent.setClass(context, neiKanXqJPush.class);
                    break;
                case "training"://培训 跳转到培训首页
//                    Log.e("推送 打开页面", "TrainDetailsJPush");
                    intent.setClass(context, TrainDetailsJPush.class);
                    break;
                case "meeting":
                    intent.setClass(context, MeetingActivity.class);
                    break;
                default:
//                    Log.e("推送 打开页面", "MoreActivity");
                    intent.setClass(context, MoreActivity.class);
                    break;
            }
            intent.putExtra("ID", jPusheExtras.getId());
        }
        //aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        doVibrator(SET_TYPE.NOTICE_PUSHMSG);
        doSound(SET_TYPE.NOTICE_PUSHMSG, notification, context);
        //Integer.parseInt(RandomUtil.createRandom(true, 9))
        notification.contentIntent = PendingIntent.getActivity(context, 4321, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        //notifyid = jsession.getNotifyid();
        //jsession.setNotifyid(notifyid);


        String className = getActivityName();

        NOTICE_TYPE msgType = NOTICE_TYPE.NOTICE_1;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyApplication.getInstance())
                .setSmallIcon(R.mipmap.xzlogo)
                .setContentTitle(mTitle + "")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mContent))
                .setContentText(mContent + "").setAutoCancel(true);

        mBuilder.setContentIntent(notification.contentIntent);

        notificationManager.notify(0, mBuilder.build());


        if (className.equals("notice.class") && (msgType == NOTICE_TYPE.NOTICE_1 || msgType == NOTICE_TYPE.NOTICE_2)) {
            //不提示 但播放声音  //判断当前界面Activity名 暂时用不着


        } else {


            //notificationManager.notify(msgType, notification);

        }


//
//        pb.putBoolean("fromPush", true);
//
//        Intent intent=new Intent();
//
//        lockPatternUtils = new LockPatternUtils(context);
//
//        String className = getActiveName();
//
//
//        //ystem.out.println( className +" ----" + msgType);
//
//
//        //如果锁屏了 就 进入锁屏
//        if (lockPatternUtils.isLocking()){
//
//
//            //System.out.println("-------lock----");
//
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.setClass(context, LockScreen.class);
//            //aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            doVibrator(SET_TYPE.NOTICE_PUSHMSG);
//            doSound(SET_TYPE.NOTICE_PUSHMSG, notification, context);
//
//        }else {
//
//            //System.out.println("-------nolock----"+type);
//
//            switch (type) {
//
//
//                case MODIFY_HEAD:
//                    //317
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, Setting.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHQREPLY);
//                    doSound(SET_TYPE.NOTICE_PUSHPHOTONEW, notification, context);
//
//
//                    break;
//
//
//                case MODIFY_BLOG_LOGO:
//                    //305
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, Home.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHQREPLY);
//                    doSound(SET_TYPE.NOTICE_PUSHPHOTONEW, notification, context);
//
//                    break;
//
//
//                case NEWAGREE_Q:
//                    //403
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ReadQ.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHQREPLY);
//                    doSound(SET_TYPE.NOTICE_PUSHQREPLY, notification, context);
//
//                    break;
//
//                case NEWAGREE_BBS:
//                    //453
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ReadQ.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHQREPLY);
//                    doSound(SET_TYPE.NOTICE_PUSHQREPLY, notification, context);
//
//                    break;
//
//                case RECIVE_INVITE:
//                    //306
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, AcceptInvite.class);
//                    //aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHMSG);
//                    doSound(); //必须有声音
//
//                    break;
//
//                case RECIVE_ACCEPT_INVITE:
//                    //302
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, Setting.class);
//                    //aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    doVibrator(SET_TYPE.NOTICE_PUSHMSG);
//                    doSound(SET_TYPE.NOTICE_PUSHMSG, notification, context);
//
//                    break;
//
//                case NEWMSG_TEXT:
//                    //201
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//                    intent.setClass(context, ListMSG.class);
//                    Log.i(TAG,"准备跳转到ListMSG.class");
//                    //aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    doVibrator(SET_TYPE.NOTICE_PUSHMSG);
//                    doSound(SET_TYPE.NOTICE_PUSHMSG, notification, context);
//                    break;
//
//
//                case NEWMSG_EMOTION_GIF:
//                    //206
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ListMSG.class);
//                    //aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    doVibrator(SET_TYPE.NOTICE_PUSHMSG);
//                    doSound(SET_TYPE.NOTICE_PUSHMSG, notification, context);
//                    break;
//
//                case NEWMSG_PIC:
//                    //202
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ListMSG.class);
//                    //aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    doVibrator(SET_TYPE.NOTICE_PUSHMSG);
//                    doSound(SET_TYPE.NOTICE_PUSHMSG, notification, context);
//                    break;
//
//                case NEWMSG_VOICE:
//                    //203
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ListMSG.class);
//                    //aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    doVibrator(SET_TYPE.NOTICE_PUSHMSG);
//                    doSound(SET_TYPE.NOTICE_PUSHMSG, notification, context);
//                    break;
//
//                case NEWMSG_POSITION:
//                    //204
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ListMSG.class);
//                    //aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    doVibrator(SET_TYPE.NOTICE_PUSHMSG);
//                    doSound(SET_TYPE.NOTICE_PUSHMSG, notification, context);
//                    break;
//
//                case NEWMSG_STATUS:
//                    //205
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ListMSG.class);
//                    //aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    doVibrator(SET_TYPE.NOTICE_PUSHMSG);
//                    doSound(SET_TYPE.NOTICE_PUSHMSG, notification, context);
//                    break;
//
//                case NEWANSWER:
//                    //401
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ReadQ.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHQREPLY);
//                    doSound(SET_TYPE.NOTICE_PUSHQREPLY, notification, context);
//                    break;
//                case NEWCOMMENT_Q:
//                    //402
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ListComment.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHQCOMMENT);
//                    doSound(SET_TYPE.NOTICE_PUSHQCOMMENT, notification, context);
//                    break;
//                case NEWREPLY:
//                    //451
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ReadQ.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHQREPLY);
//                    doSound(SET_TYPE.NOTICE_PUSHQREPLY, notification, context);
//                    break;
//
//                case NEWDIARY:
//                    //311
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ListDIARY.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHDIARYNEW);
//                    doSound(SET_TYPE.NOTICE_PUSHDIARYNEW, notification, context);
//                    break;
//
//                case NEWALBUM:
//                    //313
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ListAlbum.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHALBUMNEW);
//                    doSound(SET_TYPE.NOTICE_PUSHALBUMNEW, notification, context);
//                    break;
//
//
//
//                case NEWCOMMENT_BBS:
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ListComment.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHQCOMMENT);
//                    doSound(SET_TYPE.NOTICE_PUSHQCOMMENT, notification, context);
//                    break;
//
//                case NEWCOMMENT_DIARY:
//
//
//                    System.out.println("new diary comment");
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ListComment.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHDIARYCOMMENT);
//                    doSound(SET_TYPE.NOTICE_PUSHDIARYCOMMENT, notification, context);
//                    break;
//
//                case NEWCOMMENT_PHOTO:
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, ListComment.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHPHOTOCOMMENT);
//                    doSound(SET_TYPE.NOTICE_PUSHPHOTOCOMMENT, notification, context);
//                    break;
//
//                case NEWPHOTO:
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setClass(context, LookPhotoInAlbum.class);
//                    intent.putExtra("info", pb);
//                    doVibrator(SET_TYPE.NOTICE_PUSHPHOTONEW);
//                    doSound(SET_TYPE.NOTICE_PUSHPHOTONEW, notification, context);
//                    break;
//
//            }
//
//        }
//
//        notification.contentIntent=PendingIntent.getActivity(context, 4321, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//        notifyid = jsession.getNotifyid();
//
//        if (className.equals("cn.jiluai.ListMSG") && ( msgType == NEWMSG_TEXT || msgType == NEWMSG_EMOTION_GIF || msgType == NEWMSG_PIC || msgType == NEWMSG_POSITION ||
//                msgType == NEWMSG_STATUS || msgType == NEWMSG_VOICE
//
//        ) ){
//            //不提示 但播放声音
//
//        }else {
//
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(JSession.getInstance())
//                    .setSmallIcon(R.drawable.logo)
//                    .setContentTitle(mTitle)
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText(mContent))
//                    .setContentText("赶紧打开").setAutoCancel(true);
//
//            mBuilder.setContentIntent(notification.contentIntent);
//
//            notificationManager.notify(msgType, mBuilder.build());
//
//            //notificationManager.notify(msgType, notification);
//
//        }
//
//        jsession.setNotifyid(notifyid);
//
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MoreActivity
    private void processCustomMessage(Context context, Bundle bundle) {

        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.e(TAG, "title : " + title);
        title = bundle.getString(JPushInterface.EXTRA_TITLE);
        Log.e(TAG, "title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.e(TAG, "message : " + message);
        message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.e(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e(TAG, "extras : " + extras);

        ShowNotification(context, bundle.getString(JPushInterface.EXTRA_TITLE).equals("")
                        || bundle.getString(JPushInterface.EXTRA_TITLE) != null
                        ? "有新的消息" : bundle.getString(JPushInterface.EXTRA_TITLE)/*消息标题*/,
                bundle.getString(JPushInterface.EXTRA_MESSAGE),
                bundle.getString(JPushInterface.EXTRA_EXTRA),
                NOTICE_TYPE.NOTICE_1); //类别

//        if (MoreActivity.isForeground) {
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Intent msgIntent = new Intent(MoreActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MoreActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (null != extraJson && extraJson.length() > 0) {
//                        msgIntent.putExtra(MoreActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {

//                }
//            }
//            context.sendBroadcast(msgIntent);
//        }
    }
}

//public class MyReceiver extends BroadcastReceiver {
//    private static final String TAG = "MyReceiver";
//    private String fileName = "";
//    private String apk_id = "";
//    private String apk_name = "";
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        // 过SystemService 以获取 DownloadManager
//        //获取手机信息
////		PhoneInfo phoneInfo=new PhoneInfo(context);
////		Config.phoneNumber=phoneInfo.getNativePhoneNumber();
////		Config.phoneProvidersName=phoneInfo.getProvidersName();
////		Config.imei=JPushInterface.getUdid(context);
////		System.out.println(TAG+"：手机号："+Config.phoneNumber+",手机运营商："+Config.phoneProvidersName);
//
//		/*// --------声明一个Intent用以启动一个Service;
//        Intent intent_service = new Intent(context, MoreActivity.class);
//        // 可以在服务里面进行一些用户不需要知道的操作，比如更新。
//        context.startService(intent_service); */
//
//        Bundle bundle = intent.getExtras();
////		Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
//
//        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            Log.d(TAG, "接收Registration Id : " + regId);
//            //send the Registration Id to your server...
//
//        } else if (JPushInterface.ACTION_STOPPUSH.equals(intent.getAction())) {  //ACTION_UNREGISTER
//            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            Log.d(TAG, "接收UnRegistration Id : " + regId);
//            //send the UnRegistration Id to your server...
//        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//
//
//            //ACTION_UNREGISTER 参数过时 9.22
////		else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())){
////        	String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
////            Log.d(TAG, "接收UnRegistration Id : " + regId);
////          //send the UnRegistration Id to your server...
//        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//
//            Log.d(TAG, "接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            Log.d(TAG, "接收到推送下来的通知");
//            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//            Log.d(TAG, "接收到推送下来的通知的ID: " + notifactionId);
//
//        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            Log.d(TAG, "用户点击打开了通知");
//            if (null != bundle) {
//                String title = bundle
//                        .getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
//                String newsObjStr = bundle.getString(JPushInterface.EXTRA_EXTRA);
//                System.out.println("推送信息：" + newsObjStr);
//                try {
//                    JSONObject newsObj = new JSONObject(newsObjStr);
//                    if (newsObj != null) {
//                        Periods p = new Periods();
//                        p.setPeriodsid(newsObj.getString("periodsid"));
//                        p.setPeriodsname(newsObj.getString("periodsname"));
//                        if (CacheUtil.userid > 0) {
//                            //若登录了直接进入文章列表
//                            Intent aIntent = new Intent();
//                            aIntent.setClass(context, neiKanXq1.class);
//                            aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            aIntent.putExtra("Periods", p);
//                            context.startActivity(aIntent);
//                        } else {
//                            //若未登录了直接进入登录界面
//                            Intent aIntent = new Intent();
//                            aIntent.setClass(context, LoginActivity.class);
//                            aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            aIntent.putExtra("Periods", p);
//                            context.startActivity(aIntent);
//                        }
//                    }
//                    /*if(newsObj!=null){
//	    				//新闻ID
//	    				int newsid=newsObj.getInt("newid");
//	    				//新闻类型
//	    				int newstype=newsObj.getInt("newstype");
//	    				//新闻类型ID
//	    				int newsTypeId=0;
////	    				if(newsObj.has("newstypeid")){
////	    					newsTypeId=newsObj.getInt("newstypeid");
////	    				}
//	    				Intent aIntent = new Intent();
//	    				if(newstype==1){
//	    					//普通新闻
//	    					aIntent.setClass(context, NewsInfoActivity.class);
//	    					aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	    					aIntent.putExtra("newsid", newsid);
//	    					aIntent.putExtra("newsTypeId", newsTypeId);
//	    					context.startActivity(aIntent);
//	    				}else if(newstype==2){
//	    					//图片新闻
//	    					aIntent.setClass(context, PhotoNewsInfoActivity.class);
//	    					aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	    					aIntent.putExtra("newsid", newsid);
//	    					context.startActivity(aIntent);
//	    				}
//    				}*/
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//
//        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//            Log.d(TAG, "用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
//            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
//
//        } else {
//            Log.d(TAG, "Unhandled intent - " + intent.getAction());
//        }
//    }
//
//    // 打印所有的 intent extra 数据
//    private static String printBundle(Bundle bundle) {
//        StringBuilder sb = new StringBuilder();
//        for (String key : bundle.keySet()) {
//            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
//            } else {
//                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
//            }
//        }
//        return sb.toString();
//    }
//
//}

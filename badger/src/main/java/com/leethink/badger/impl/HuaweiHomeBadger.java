package com.leethink.badger.impl;

import android.app.Notification;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.leethink.badger.Badger;

import java.util.Arrays;
import java.util.List;


public class HuaweiHomeBadger extends Badger {

    private static final String CONTENT_URI = "content://com.huawei.android.launcher.settings/badge/";
    private static final String COUNT = "count";
    private static final String TAG = "tag";

    @Override
    public void executeBadge(Context context, Notification notification, int notificationId, int thisNotificationCount, int count) {
        setNotification(notification, notificationId, context);
        try {
            Bundle bundle =new Bundle();
            bundle.putString("package", context.getPackageName().toString());
            bundle.putString("class", "com.qingyii.hxtz.FirstActivity");
            bundle.putInt("badgenumber",count);
            ContentResolver t=context.getContentResolver();
            t.call(Uri.parse(CONTENT_URI), "change_badge", null, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.huawei.android.launcher");
    }
}

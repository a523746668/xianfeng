package com.qingyii.hxt.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qingyii.hxt.AlarmSettingAgain;

public class CallAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Intent alaramIntent = new Intent(context, AlarmSettingAgain.class);
        alaramIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alaramIntent);
    }

}

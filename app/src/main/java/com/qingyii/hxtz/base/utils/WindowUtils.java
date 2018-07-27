package com.qingyii.hxtz.base.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jess.arms.integration.AppManager;
import com.qingyii.hxtz.R;

import org.simple.eventbus.EventBus;

import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.body.ProgressInfo;

import static com.jess.arms.integration.AppManager.APPMANAGER_MESSAGE;

/**
 * Created by xubo on 2017/6/27.
 */

public class WindowUtils {
    public static void showExitDialog(Context context) {
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        dlg.setContentView(R.layout.exit_app_tip);
        TextView ok = (TextView) dlg.findViewById(R.id.btn_ok);
        ok.setOnClickListener(v -> {
            Message msg = new Message();
            msg.what = AppManager.APP_EXIT;
            EventBus.getDefault().post(msg, APPMANAGER_MESSAGE);
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) dlg.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(v -> dlg.cancel());
    }

    @NonNull
    public static ProgressListener getDownloadListener() {
        final ProgressDialog dialog= (ProgressDialog) StyledDialog.buildProgress( "下载中...",true).setCancelable(false,false).show();
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                // 如果你不屏蔽用户重复点击上传或下载按钮,就可能存在同一个 Url 地址,上一次的上传或下载操作都还没结束,
                // 又开始了新的上传或下载操作,那现在就需要用到 id(请求开始时的时间) 来区分正在执行的进度信息
                // 这里我就取最新的上传进度用来展示,顺便展示下 id 的用法
                StyledDialog.updateProgress(dialog, progressInfo.getPercent(), 100, "下载进度", true);
                Log.i("tmddowmload",progressInfo.getPercent()+"--");
                dialog.setProgress(progressInfo.getPercent());
                if (progressInfo.isFinish()) {
                    dialog.dismiss();
                }
//                if (mLastDownloadingInfo == null) {
//                    mLastDownloadingInfo = progressInfo;
//                }
//
//                //因为是以请求开始时的时间作为 Id ,所以值越大,说明该请求越新
//                if (progressInfo.getId() < mLastDownloadingInfo.getId()) {
//                    return;
//                } else if (progressInfo.getId() > mLastDownloadingInfo.getId()) {
//                    mLastDownloadingInfo = progressInfo;
//                }
//
//                int progress = mLastDownloadingInfo.getPercent();
//                mDownloadProgress.setProgress(progress);
//                mDownloadProgressText.setText(progress + "%");
//                Log.d(TAG, "--Download-- " + progress + " %");
            }

            @Override
            public void onError(long id, Exception e) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mUploadProgress.setProgress(0);
//                        mUploadProgressText.setText("error");
//                    }
//                });
            }
        };
    }
}

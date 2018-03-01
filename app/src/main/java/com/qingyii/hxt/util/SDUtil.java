package com.qingyii.hxt.util;

import android.os.Environment;

import java.io.File;

public class SDUtil {
    //�ж�sd���Ƿ����
    public static boolean sdCardExist() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        return sdCardExist;
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //�ж�sd���Ƿ����
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//��ȡ��Ŀ¼
        }
        //���ǵ���Щ�ֻ�û��SD���ᱨ��ָ���쳣
        if (sdDir != null) {
            return sdDir.toString();
        } else {
            return "";
        }
    }
}
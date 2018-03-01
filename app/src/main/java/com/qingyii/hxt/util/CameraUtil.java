package com.qingyii.hxt.util;

import android.content.Context;
import android.content.pm.PackageManager;

public class CameraUtil {
	/**
	 * �ж��ֻ�����ͷ�Ƿ����
	 * @param context
	 * @return
	 */
	public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            
            return true;
        } else {
           
            return false;
        }
    }
}

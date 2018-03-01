package com.zhf.Util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by zhf on 2017/12/5.
 */

public class PictureCompressUtil {
    public final static int QUALITY_320P = 320;//480, 320
    public final static int QUALITY_360P = 360;//640, 360
    public final static int QUALITY_480P = 480;//640, 480
    public final static int QUALITY_720P = 720;//1280, 720
    public final static int QUALITY_1080P = 1080;//1920, 1080
    public final static int QUALITY_2K = 1440;//2560, 1440
    public final static int QUALITY_4K = 2160;//3840, 2160

    public final static int QUALITY_DEFAULT = QUALITY_360P;
    public final static int SIZE_1KB = 1024;
    public final static int SIZE_1MB = SIZE_1KB * 1024;
    public final  static int SIZE_2MB=SIZE_1MB*2;


    public static void compressBitmap(Bitmap bitmap, String filePath) {
        compressBitmap(bitmap, filePath, SIZE_1MB, QUALITY_DEFAULT);
    }

    public static void compressBitmap(Bitmap bitmap, String filePath, int maxByte, int quality) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();


        float ratio = getRatioSize(w, h, quality);

        int resultW = Math.round(w / ratio);
        int resultH = Math.round(h / ratio);

        Bitmap result = Bitmap.createBitmap(resultW, resultH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, resultW, resultH);
        canvas.drawBitmap(bitmap, null, rect, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;//100不压缩品质
        result.compress(Bitmap.CompressFormat.JPEG, options, baos);
        Log.i("tmdzzyasuo",baos.toByteArray().length+"-----"+baos.size());
        while (baos.toByteArray().length > maxByte) {
            Log.i("tmdzzyasuo",baos.toByteArray().length+"11111");
            baos.reset();
            options -= 10;
            result.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        try {
            File file =new File(filePath);
            FileOutputStream fileOutputStream=new FileOutputStream(file);

            fileOutputStream.write(baos.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("tmdyichang",e.getMessage().toString());
        }

        if (!result.isRecycled()) {
            result.recycle();
            result = null;
        }
    }

    /**
     * 计算缩放比例
     */
    private static float getRatioSize(int w, int h, int qualityH) {
        float ratio;
        if (w > h) {
            ratio = h * 100.00f / qualityH / 100f;
        } else {
            ratio = w * 100.00f / qualityH / 100f;
        }
        if (ratio <= 0) ratio = 1;
        return ratio;
    }


}

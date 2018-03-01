package com.zhf.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.zhf.bean.SendTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by zhf on 2017/12/11.
 */

public class SelectVideoUtil  {
         public  final static int VideoS=9632;  //  选择视频
         public  final  static int VideoP=9635 ;//  拍摄视频
         public  final static int VideoS2=9631;  //  选择视频
         public  final  static int VideoP2=9634 ;//  拍摄视频
         public final static int SPHOTO1=998;
         public final static int SPHOTO2=997;
         public final static int Cmamera1=996;
         public final static int Cmameral2=995;


         // 选择视频
     public   static  void VideoSlect(Activity context){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
         context.startActivityForResult(intent, VideoS);
    }


     //将选择的视频的第一张图片保存
     public static SendTask getsendtask(SendTask task, Bitmap bitmap, File file){
         try {
             FileOutputStream fileOutputStream=new FileOutputStream(file);
             int options = 80;//100不压缩品质
             bitmap.compress(Bitmap.CompressFormat.JPEG, options, fileOutputStream);
             fileOutputStream.flush();
             fileOutputStream.close();
              task.setPath(file.getPath());

         } catch (Exception e){
            e.printStackTrace();
         }

         bitmap.recycle();
         return  task;
     }


   //根据视频地址获得缩略图
    public static Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media =new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }


  //打开相机拍照并且返回保存并且返回地址
      public static String usecamera(Activity context){
            String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/xfypt/"+System.currentTimeMillis()+"xf.jpg";
            File file=new File(path);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          Uri  uri;
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                  uri= FileProvider.getUriForFile(context, "com.qingyii.hxt.fileprovider", file);

          }   else {
              uri=Uri.fromFile(file);
          }

          intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
          context.startActivityForResult(intent,SelectVideoUtil.Cmamera1);
            return  path;
      }


}

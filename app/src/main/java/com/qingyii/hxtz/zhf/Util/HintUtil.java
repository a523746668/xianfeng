package com.qingyii.hxtz.zhf.Util;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.zyao89.view.zloading.ZLoadingDialog;

import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;
import static com.zyao89.view.zloading.Z_TYPE.STAR_LOADING;

/**
 * Created by admin on 2017/7/20.
 */
//toast的请求提示
public class HintUtil {
     private  static Toast toast;
       private static ZLoadingDialog dialog ;


    public static  void showtoast(Context context ,String content){
        if(toast==null){
            toast=Toast.makeText(context,content,Toast.LENGTH_SHORT);
        } else {
              toast.setText(content);
        }
      toast.show();
    }

    public static void showdialog(Context context){
          if(dialog==null){
              dialog=new  ZLoadingDialog(context);
              dialog.setLoadingBuilder(DOUBLE_CIRCLE)//设置类型
                      .setLoadingColor(Color.RED)//颜色
                      .setHintText("Loading...")
                      .setHintTextSize(16) // 设置字体大小 dp
                      .setHintTextColor(Color.WHITE)// 设置字体颜色
                      .setCancelable(false)
                      .setCanceledOnTouchOutside(false)
                      .show();

           } else {
              dialog.show();
          }
    }

     public static void startupload(Context context){
         if(dialog==null){
             dialog=new  ZLoadingDialog(context);
             dialog.setLoadingBuilder(STAR_LOADING)//设置类型
                     .setLoadingColor(Color.RED)//颜色
                     .setHintText("Upload...")
                     .setHintTextSize(16) // 设置字体大小 dp
                     .setHintTextColor(Color.WHITE)// 设置字体颜色
                     .setCancelable(false)
                     .setCanceledOnTouchOutside(false)
                     .show();
         } else {
             dialog.show();
         }
     }

    public static void stopdialog(){
        if(dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
    }

}

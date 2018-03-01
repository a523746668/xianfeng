package com.zhf.Util;

import android.content.Context;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;

/**
 * Created by admin on 2017/7/20.
 */
//toast的请求提示
public class HintUtil {
     private  static Toast toast;
       private static SpotsDialog dialog;


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
              dialog=new SpotsDialog(context,"正在加载");
             dialog.show();
          } else {
              dialog.show();
          }
    }

     public static void startupload(Context context){
         if(dialog==null){
             dialog=new SpotsDialog(context,"正在上传");
             dialog.show();
         } else {
             dialog.show();
         }
     }

    public static void stopdialog(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
            dialog=null;
        }
    }

}

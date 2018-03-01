package com.zhf.Util;

import java.io.File;

/**
 * Created by zhf on 2018/1/18.
 */

public class FileUtil {

       public  static boolean ispdf(File file ){
          if(!file.exists()){
              return  false;
            }
           String path= file.getPath();
          if(path.endsWith(".pdf")){
              return  true;
          }

           int dotIndex = path.lastIndexOf(".");
           if (dotIndex < 0) {
               return false;
           }
           String end = path.substring(dotIndex, path.length()).toLowerCase();
           if (end == "") {
               return false;
           }
           if(end.equalsIgnoreCase(".pdf")){
               return  true;
           }
           return  false;
       }
}

package com.qingyii.hxt.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * xml文件解析
 * @author shelia
 *
 */
public class XmlUtil {
	/**
	 * 获取epub文件解压后html文件存放根目录
	 * @param filePath
	 * @return
	 */
  public static String getRootDir(String filePath){
	  String str="";
	  File xmlFlie = new File(filePath);  
	  try {
		InputStream inputStream = new FileInputStream(xmlFlie);
        XmlPullParserFactory factory;
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);   
	        XmlPullParser xpp;
	        xpp = factory.newPullParser();
			xpp.setInput(inputStream, "UTF-8");  
	        int eventType = xpp.getEventType();
	      //处理事件，不碰到文档结束就一直处理  
	        while (eventType != XmlPullParser.END_DOCUMENT) {   
	            //因为定义了一堆静态常量，所以这里可以用switch  
	            switch (eventType) {  
	                case XmlPullParser.START_DOCUMENT:  
	                    // 不做任何操作或初开始化数据  
	                    break;  
	      
	                case XmlPullParser.START_TAG:  
	                    // 解析XML节点数据  
	                    // 获取当前标签名字  
	                    String tagName = xpp.getName();  
	      
	                    if(tagName.equals("rootfile")){  
	      
	                        // 通过getAttributeValue 和 netxText解析节点的属性值和节点值  
	                    	String path=xpp.getAttributeValue(null, "full-path");
	                    	if(EmptyUtil.IsNotEmpty(path)){
	                    		if(path.contains("/")){
	                    			str=path.substring(0,path.indexOf("/"));
	                    		}
	                    	}
	                    }  
	                    break;  
	      
	                case XmlPullParser.END_TAG:  
	                    // 单节点完成，可往集合里边添加新的数据  
	                    break;  
	                case XmlPullParser.END_DOCUMENT:  
	      
	                    break;  
	            }  
	      
	            // 别忘了用next方法处理下一个事件，不然就会死循环  
	            try {
					eventType = xpp.next();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	          } 
		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   
         
         
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	  return str;
  }
}

package com.qingyii.hxtz.util;


public class TextSizeUtil {
	/**
	 * �����б�ҳ����ֻ�ֱ������������С
	 * @param screenWidth
	 * @param screenHeight
	 * @return
	 */
	public static int getFontSize(int screenWidth, int screenHeight) {  
        screenWidth=screenWidth>screenHeight?screenWidth:screenHeight;  
        int rate = (int)(5*(float) screenWidth/320);
        
       /* if(CacheUtil.phonedensity>1){
        	rate=(int) (rate/CacheUtil.phonedensity);
        }else if(CacheUtil.phonedensity<1){
        	rate=(int) (rate*CacheUtil.phonedensity);
        }
        */
        if(rate<15){
        	rate=15;
        }else if(rate>=18){
        	rate=18;
        }
        return rate;
//        return rate<14?14:rate;   
	} 
}

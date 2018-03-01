package com.qingyii.hxt.util;

import android.text.TextUtils;

/**
 * �հ��ַ�null�ж�
 * @author shelia
 *
 */
public class EmptyUtil {
	public static boolean IsNotEmpty(String str){
		if(!TextUtils.isEmpty(str)){
			if(!"null".equals(str)){
				return true;
			}
		}
		return false;
		
	}
}

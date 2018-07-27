package com.qingyii.hxtz.util;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TextUtil {

	private static DecimalFormat mFormat;
	
	public static DecimalFormat getDefaultDecimalFormat() {
		if(mFormat==null) {
			mFormat = new DecimalFormat("####0.0");
		}
		return mFormat;
	}

	public String chuli(ArrayList<String> list){
		StringBuffer str=new StringBuffer();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				if(i<list.size()-1){
					str.append(list.get(i)+",");}
				else {
					str.append(list.get(i));
				}
			}
			return  str.toString();
		}
		return  "";
	}

}

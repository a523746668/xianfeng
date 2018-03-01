package com.qingyii.hxt.util;

import java.text.DecimalFormat;

public class TextUtil {

	private static DecimalFormat mFormat;
	
	public static DecimalFormat getDefaultDecimalFormat() {
		if(mFormat==null) {
			mFormat = new DecimalFormat("####0.0");
		}
		return mFormat;
	}
}

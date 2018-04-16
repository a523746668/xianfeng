package com.qingyii.hxtz.util;
/**
 * 红信通各种状态转换工具类
 * @author shelia
 *
 */
public class StateUtil {
	public static String examinationType(String type) {
		String typeStr="暂无状态";
		if("1".equals(type)){
			typeStr="题库抽选";
		}else if("2".equals(type)){
			typeStr="单次命题";
		}else if("3".equals(type)){
			typeStr="累计闯关";
		}else if("4".equals(type)){
			typeStr="重复闯关";
		}
		return typeStr;
	}
}

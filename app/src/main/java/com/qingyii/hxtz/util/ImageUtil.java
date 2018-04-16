package com.qingyii.hxtz.util;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * 图片加载工具类
 * @author Lee
 *
 */
public class ImageUtil {
	public static DisplayImageOptions newDisplayOptions(int defaultImg) {
		return new DisplayImageOptions.Builder()
		.showStubImage(defaultImg)
		// 在ImageView加载过程中显示图片
		.showImageOnLoading(defaultImg)
		.showImageForEmptyUri(defaultImg)
		// image连接地址为空时
		.showImageOnFail(defaultImg)
		// image加载失败
		.cacheInMemory(true)
		// 加载图片时会在内存中加载缓存
		.cacheOnDisc(true)
		// 加载图片时会在磁盘中加载缓存
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		// .displayer(new RoundedBitmapDisplayer(10))//
		// 设置用户加载图片task(这里是圆角图片显示)
		.build();
	}
}

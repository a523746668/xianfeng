/*
 * Copyright (C) 2007-2015 FBReader.ORG Limited <contact@fbreader.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.zlibrary.ui.android.library;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.component.AppComponent;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import org.geometerplus.android.fbreader.config.ConfigShadow;
import org.geometerplus.zlibrary.ui.android.image.ZLAndroidImageManager;

public abstract class ZLAndroidApplication extends MultiDexApplication implements App {

	private ZLAndroidLibrary myLibrary;
	private ConfigShadow myConfig;
	private AppLifecycles mAppDelegate;

	/**
	 * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
	 * 常用于 MultiDex 以及插件化框架的初始化
	 *
	 * @param base
	 */
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		this.mAppDelegate = new AppDelegate(base);
		this.mAppDelegate.attachBaseContext(base);
	}
	@Override
	public void onCreate() {
		super.onCreate();
		this.mAppDelegate.onCreate(this);
		// this is a workaround for strange issue on some devices:
		//    NoClassDefFoundError for android.os.AsyncTask
		try {
			//解决某些设备无法实例化AsyncTask
			Class.forName("android.os.AsyncTask");
		}catch (Throwable t) {
		}

		myConfig = new ConfigShadow(this);
		//图片管理
		new ZLAndroidImageManager();
		//获取程序信息
		myLibrary = new ZLAndroidLibrary(this);

		Logger.init("FBReader").logLevel(LogLevel.NONE);
	}

	public final ZLAndroidLibrary library() {
		return myLibrary;
	}
	/**
	 * 在模拟环境中程序终止时会被调用
	 */
	@Override
	public void onTerminate() {
		super.onTerminate();
		if (mAppDelegate != null)
			this.mAppDelegate.onTerminate(this);
	}

	/**
	 * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
	 *
	 * @return
	 */
	@Override
	public AppComponent getAppComponent() {
		return ((App) mAppDelegate).getAppComponent();
	}

}

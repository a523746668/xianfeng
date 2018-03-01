package com.qingyii.hxt.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class AssetsUtil {

	public static String loadJSONFromAsset(Context context, String filename) {
		String json = "";
		try {

			InputStream is = context.getAssets().open(filename);

			int size = is.available();

			byte[] buffer = new byte[size];

			is.read(buffer);

			is.close();

			json = new String(buffer, "utf-8");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return json;

	}
}

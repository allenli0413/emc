package com.emiancang.emiancang.uitl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefsUtil {
	public final static String SETTING = "MLRJ";
	public final static String SHOWGUIDE = "GUIDE";
	public final static String USERKEY = "USER";
	public final static String WAREHOUSEID = "WAREHOUSEID";
	public final static String CITYHISTORY = "CITYHISTORY";
	public final static String ENTERPRISETYPE = "ENTERPRISETYPE";
	public final static String CUSTID = "CUSTID";
	public final static String KEEP_CITY = "KEEPCITY";
	public final static String KEEP_CITY_ID = "KEEPCITYID";
	public final static String NEW_MESSAGE = "NEWMESSAGE";

	public static void putValue(Context context, String key, int value) {
		Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
				.edit();
		sp.putInt(key, value);
		sp.commit();
	}

	public static void putValue(Context context, String key, boolean value) {
		Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
				.edit();
		sp.putBoolean(key, value);
		sp.commit();
	}

	public static void putValue(Context context, String key, String value) {
		Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
				.edit();
		sp.putString(key, value);
		sp.commit();
	}

	public static void putValue(Context context, String key, long value) {
		Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
				.edit();
		sp.putLong(key, value);
		sp.commit();
	}

	public static int getValue(Context context, String key, int defValue) {
		SharedPreferences sp = context.getSharedPreferences(SETTING,
				Context.MODE_PRIVATE);
		int value = sp.getInt(key, defValue);
		return value;
	}

	public static boolean getValue(Context context, String key, boolean defValue) {
		SharedPreferences sp = context.getSharedPreferences(SETTING,
				Context.MODE_PRIVATE);
		boolean value = sp.getBoolean(key, defValue);
		return value;
	}

	public static String getValue(Context context, String key, String defValue) {
		SharedPreferences sp = context.getSharedPreferences(SETTING,
				Context.MODE_PRIVATE);
		String value = sp.getString(key, defValue);
		return value;
	}

	public static long getValue(Context context, String key, long defValue) {
		SharedPreferences sp = context.getSharedPreferences(SETTING,
				Context.MODE_PRIVATE);
		long value = sp.getLong(key, defValue);
		return value;
	}

	public static void remove(Context context, String key) {
		Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
				.edit();
		sp.remove(key);
		sp.commit();
	}
}

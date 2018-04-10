package com.zfg.org.myexample.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
	
	//share数据保存
	public static final String NEWPART_DEVICE_CODE = "NEWPART_DEVICE_CODE";
	public final static String USER_ID = "user_mid";
	public final static String UID = "user_id";
	
	//系统参数更新时间
	public static final String SYS_UPDATE_TIME = "sycn_update_time";
	public static final String FIRST_IN = "first_in";
	public static final String USERTYPE = "user_type";
	public static final String UPDATE_TIME = "UPDATE_TIME";
	public static final String UPDATE_URL = "UPDATE_URL";
	public static final String UPDATE_APK = "UPDATE_APK";
	//是否有更新
	public static final String HAS_UPDATE = "has_update_data";
	public static final String CACHE_NUM = "cache_total_num";//
	public static final String DEVICE_CODE = "device_code";
	
	public static final String INDICATE_CODE = "INDICATE_CODE";

	//默认缓存用户
	public static final String CACHE_USER = "cache_user";

	private final String SHARED_PREFERENCE_NAME = "com.dian.diabetes";
	private static Preference catche;
	private SharedPreferences spf;

	public static Preference instance(Context context) {
		if (catche == null) {
			catche = new Preference(context);
		}
		return catche;
	}

	public Preference(Context context) {
		spf = context.getSharedPreferences(SHARED_PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	public void putBoolean(String key, boolean value) {
		spf.edit().putBoolean(key, value).commit();
	}

	public boolean getBoolean(String key) {
		return spf.getBoolean(key, false);
	}

	public void putString(String key, String value) {
		spf.edit().putString(key, value).commit();
	}

	public String getString(String key) {
		return spf.getString(key, "");
	}

	public void putInt(String key, int value) {
		spf.edit().putInt(key, value).commit();
	}

	public void putLong(String key, long value) {
		spf.edit().putLong(key, value).commit();
	}

	public int getInt(String key) {
		return spf.getInt(key, 0);
	}

	public int getInt(String key, int defaultValue) {
		return spf.getInt(key, defaultValue);
	}

	public long getLong(String key) {
		return spf.getLong(key, 0);
	}

	public long getLong(String key, long def) {
		return spf.getLong(key, def);
	}

	public void clearData() {
		spf.edit().clear().commit();
	}

	public void remove(String key) {
		spf.edit().remove(key).commit();
	}
	
	public void commit(){
		spf.edit().commit();
	}
	
}

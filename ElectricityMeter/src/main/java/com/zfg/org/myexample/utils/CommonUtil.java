package com.zfg.org.myexample.utils;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;


public class CommonUtil {

	private static Map<String, String> commons = new HashMap<String, String>();
	private static Map<String, String> values = new HashMap<String, String>();

	static {
		// key-value对应输入
		commons.put("sex0", "女");
		commons.put("sex1", "男");

		commons.put("indicatesex1", "女");
		commons.put("indicatesex0", "男");

		// 七点法
		commons.put("diabetes44", "总体");
		

		// 近一个月或者一周
		commons.put("last1", "近一周");
		commons.put("last2", "近一个月");
		commons.put("last3", "近三个月");

		// 餐前餐后
		commons.put("stage0", "餐前");
		commons.put("stage1", "餐后");

		// 药物剂量
		commons.put("medicine1", "一日一次");
		commons.put("medicine2", "一日两次");
		commons.put("medicine3", "一日三次");
		commons.put("medicine4", "一日四次");

		// 强度
		commons.put("strong0", "轻体力劳动");
		commons.put("strong1", "中体力劳动");
		commons.put("strong2", "强体力劳动");

		// 饮食早中晚
		commons.put("eat0", "早餐");
		commons.put("eat1", "上午加餐");
		commons.put("eat2", "中餐");
		commons.put("eat3", "下午加餐");
		commons.put("eat4", "晚餐");
		commons.put("eat5", "晚上加餐");

		commons.put("strength1", "比较轻松");
		commons.put("strength2", "有些吃力，但可以坚持");
		commons.put("strength3", "非常吃力，甚至自感承受不了");

		// medicine
		commons.put("mstage1", "餐前");
		commons.put("mstage2", "餐后");

		commons.put("medicine" + ContantsUtil.BRECKFAST + 1, "早餐前");
		commons.put("medicine" + ContantsUtil.BRECKFAST + 2, "早餐后");
		commons.put("medicine" + ContantsUtil.LAUNCH + 1, "中餐前");
		commons.put("medicine" + ContantsUtil.LAUNCH + 2, "中餐后");
		commons.put("medicine" + ContantsUtil.DINNER + 1, "晚餐前");
		commons.put("medicine" + ContantsUtil.DINNER + 2, "晚餐后");
		commons.put("medicine" + ContantsUtil.SLEEP_PRE + 1, "睡前");
		commons.put("medicine" + ContantsUtil.SLEEP_PRE + 2, "睡后");

		// 反向value-key对应输入

	}

	public static String getValue(String key) {
		Log.d("key", key);
		String value = commons.get(key);
		return value;
	}

	//  补零12位
	public static String AddZeros(String data){
		if (data.length() == 12){
			return data;
		} else {
			String str ="000000000000";
			String str_m=str.substring(0, 12-data.length())+data;
			return str_m;
		}
	}

	public static String AddZeros(String data,int len) {
		int i =len - data.length();
		String str_m = data;
		for(int j= 0; j <i; j++)
			str_m  = "0" + str_m;
		return str_m;
		// System.out.println(str_m);
	}

}

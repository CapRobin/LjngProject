package com.zfg.org.myexample.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;

import com.zfg.org.myexample.db.PropertyBo;
import com.zfg.org.myexample.db.PropertyDefBo;
import com.zfg.org.myexample.db.dao.DefSet;
import com.zfg.org.myexample.db.dao.UserSet;

//import com.dian.diabetes.db.PropertyBo;
//import com.dian.diabetes.db.PropertyDefBo;
//import com.dian.diabetes.db.dao.DefSet;
//import com.dian.diabetes.db.dao.UserSet;

/**
 * 默认的配置数据
 * 
 * @author longbh
 */
public class Config {

	// 页面更新变量
	public static Map<String, Boolean> pageMap = new HashMap<String, Boolean>();
	// 用户设置
	private static Map<String, Object> properties = new HashMap<String, Object>();

	// 用于定时更新服务器数据
	public static long lastUpdateTime = 0;
	public static boolean isUpdate = true;
	private static long delta = 2 * 1000;

	public static void initConfig(Context context) {
		defaultConfig();
		//属性
//		PropertyDefBo defBo = new PropertyDefBo(context);
//		List<DefSet> defData = defBo.getList();
//		for (DefSet item : defData) {
//			properties.put(item.getKey(), item.getValue());
//		}
		// 用户自定义配置
	}

	public static void loadUserSet(Context context) {
		PropertyDefBo defBo = new PropertyDefBo(context);
		List<DefSet> defData = defBo.getList();
		// load服务端默认配置
		for (DefSet item : defData) {
			properties.put(item.getKey(), item.getValue());
		}
		loadDefSet(context);
	}

	// 本地数据库存放上次拉下来的设置数据
	public static void loadDefSet(Context context) {
		// 用户自定义配置
		PropertyBo bo = new PropertyBo(context);
		List<UserSet> data = bo.getList(ContantsUtil.DEFAULT_TEMP_UID);
		for (UserSet item : data) {
			properties.put(item.getKey(), item.getValue());
		}
	}

	// 本地配置默认设置数据
	private static void defaultConfig() {
//		properties.clear();
//		// 闹钟默认时间
//		properties.put("clock" + ContantsUtil.EAT_PRE, "09:00");
//		properties.put("clock" + ContantsUtil.EAT_AFTER, "09:30");
//		properties.put("clock" + ContantsUtil.LAUNCH + ContantsUtil.EAT_PRE,
//				"12:00");
//		properties.put("clock" + ContantsUtil.LAUNCH + ContantsUtil.EAT_AFTER,
//				"12:30");
//		properties.put("clock" + ContantsUtil.DINNER + ContantsUtil.EAT_PRE,
//				"18:00");
//		properties.put("clock" + ContantsUtil.DINNER + ContantsUtil.EAT_AFTER,
//				"18:30");
//		properties.put("clock" + ContantsUtil.SLEEP_PRE + ContantsUtil.EAT_PRE,
//				"22:00");
//
//		// 糖化值
//		properties.put("diastaticValue", "6.5");
//
//		// 血糖标准
//		properties.put("levelHigh" + ContantsUtil.EAT_PRE, "14");
//		properties.put("levelMid" + ContantsUtil.EAT_PRE, "9");
//		properties.put("levelLow" + ContantsUtil.EAT_PRE, "5");
//		properties.put("levelHigh" + ContantsUtil.EAT_AFTER, "15");
//		properties.put("levelMid" + ContantsUtil.EAT_AFTER, "10");
//		properties.put("levelLow" + ContantsUtil.EAT_AFTER, "6");
//
//		// 血糖目标推荐值
//		properties.put("levelHighDef" + ContantsUtil.EAT_PRE, "14");
//		properties.put("levelMidDef" + ContantsUtil.EAT_PRE, "9");
//		properties.put("levelLowDef" + ContantsUtil.EAT_PRE, "5");
//		properties.put("levelHighDef" + ContantsUtil.EAT_AFTER, "15");
//		properties.put("levelMidDef" + ContantsUtil.EAT_AFTER, "10");
//		properties.put("levelLowDef" + ContantsUtil.EAT_AFTER, "6");
//
//		// 运动统计
//		//properties.put("defNetCalore", "3000");
//
//		// 热量高低统计
//		properties.put("highCalore", "0.1");
//		properties.put("lowCalore", "-0.1");
//		
//		//目标体重
//		properties.put("weightHigh", "70");
//		properties.put("weightLow", "60");
//
//		// BMI
//		properties.put("bmiHigh", "25");
//		properties.put("bmiLow", "18");
//
//		// 血红蛋白标准
//		properties.put("highProtein", "6.5");
//		properties.put("lowProtein", Integer.MIN_VALUE + "");
//
//		// 腰围标准
//		properties.put("waistHigh", "102");
//		properties.put("waistLow", "68");
//
//		// 血脂标准
//		properties.put("highLipid", "102");
//		properties.put("lowLipid", "68");
//
//		// 心率
//		properties.put("highHeart", "100");
//		properties.put("lowHeart", "60");
//
//		// 血压标准
//		properties.put("highOpenPress", "80");
//		properties.put("lowOpenPress", "60");
//		properties.put("highClosePress", "140");
//		properties.put("lowClosePress", "90");
//
//		// 胆固醇
//		properties.put("highCh", "5.2");
//		properties.put("lowCh", "3.11");
//		// 甘油三脂
//		properties.put("highTg", "1.7");
//		properties.put("lowTg", "0.56");
//		// 高密度脂蛋白
//		properties.put("highHdl", "2.07");
//		properties.put("lowHdl", "1.03");
//		// 低密度脂蛋白
//		properties.put("highLdl", "3.12");
//		properties.put("lowLdl", "2.1");
	}

	/**
	 * 血糖是否超标状态
	 * 
	 * @param value
	 * @return
	 */
//	public static int getBloodState(float value, int state) {
//		if (value > StringUtil.toFloat(properties.get("levelHigh" + state))) {
//			return ContantsUtil.HIGH_DIABETES;
//		} else if (value > StringUtil.toFloat(properties
//				.get("levelMid" + state))) {
//			return ContantsUtil.HIGH_PRE_DIABETES;
//		} else if (value > StringUtil.toFloat(properties
//				.get("levelLow" + state))) {
//			return ContantsUtil.MID_DIABETES;
//		} else {
//			return ContantsUtil.LOW_PRE_DIABETES;
////		}
//	}

	public static String getEatLevelColor(String level, Resources res) {
		if ("低".equals(level)) {
			return "<font color=\"#FFBE46\">低</font>";
		} else if ("中".equals(level)) {
			return "<font color=\"#00DAA7\">中</font>";
		} else {
			return "<font color=\"#FF4C4C\">高</font>";
		}
	}

	public static String getSportLevelColor(int level, Resources res) {
		if (level == 0) {
			return "<font color=\"#FF4C4C\">低</font>";
		} else if (level == 1) {
			return "<font color=\"#00DAA7\">中</font>";
		} else {
			return "<font color=\"#FFBE46\">高</font>";
		}
	}

	// 体重等级
	public static int getIndicateLevel(float weight, String lowKey,
			String highKey) {
		float high = StringUtil.toFloat(properties.get(highKey));
		float low = StringUtil.toFloat(properties.get(lowKey));
		if (weight > high) {
			return 2;
		} else if (weight < low) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 血糖状态
	 * 
	 * @param value
	 * @param alignment
	 * @return
	 */
//	public static int getBloodState(float value, float[] alignment) {
//		if (value > alignment[2]) {
//			return ContantsUtil.HIGH_DIABETES;
//		} else if (value > alignment[1]) {
//			return ContantsUtil.HIGH_PRE_DIABETES;
//		} else if (value > alignment[0]) {
//			return ContantsUtil.MID_DIABETES;
//		} else if(value == 0){
//			return -1;
//		}else {
//			return ContantsUtil.LOW_PRE_DIABETES;
//		}
//	}
	
	/**
	 * 指标状态
	 * 
	 * @param value
	 * @param alignment
	 * @return
	 */
	public static int getIndicatLevel(float value, float[] alignment) {
//		if (value > alignment[1]) {
//			return 2;
//		} else if (value < alignment[0]) {
//			return 1;
//		} else{
			return 0;
//		}
	}

	/**
	 * 获取吃饭时间段
	 * 
	 * @param date
	 * @return
	 */
//	public static int getEatState(Date date) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		int hour = calendar.get(Calendar.HOUR_OF_DAY);
//		if (hour >= 6 && hour < 10) {
//			return ContantsUtil.BRECKFAST;
//		} else if (hour >= 10 && hour < 15) {
//			return ContantsUtil.LAUNCH;
//		} else if (hour >= 15 && hour < 20) {
//			return ContantsUtil.DINNER;
//		} else if (hour >= 20 && hour < 24) {
//			return ContantsUtil.SLEEP_PRE;
//		} else {
//			return ContantsUtil.SLEEP_PRE;
//		}
//	}

//	public static int getEatState(long date) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(date);
//		int hour = calendar.get(Calendar.HOUR_OF_DAY);
//		if (hour >= 6 && hour < 10) {
//			return ContantsUtil.BRECKFAST;
//		} else if (hour >= 10 && hour < 15) {
//			return ContantsUtil.LAUNCH;
//		} else if (hour >= 15 && hour < 20) {
//			return ContantsUtil.DINNER;
//		} else if (hour >= 20 && hour < 24) {
//			return ContantsUtil.SLEEP_PRE;
//		} else {
//			return ContantsUtil.SLEEP_PRE;
//		}
//	}

//	public static int[] getEatStateArry(long date) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(date);
//		int hour = calendar.get(Calendar.HOUR_OF_DAY);
//		int[] value = { ContantsUtil.BRECKFAST, ContantsUtil.EAT_PRE };
//		if (hour >= 6 && hour < 10) {
//			value[0] = ContantsUtil.BRECKFAST;
//			if (hour < 9) {
//				value[1] = ContantsUtil.EAT_PRE;
//			} else {
//				value[1] = ContantsUtil.EAT_AFTER;
//			}
//		} else if (hour >= 10 && hour < 15) {
//			value[0] = ContantsUtil.LAUNCH;
//			if (hour < 12) {
//				value[1] = ContantsUtil.EAT_PRE;
//			} else {
//				value[1] = ContantsUtil.EAT_AFTER;
//			}
//		} else if (hour >= 15 && hour < 20) {
//			value[0] = ContantsUtil.DINNER;
//			if (hour < 18) {
//				value[1] = ContantsUtil.EAT_PRE;
//			} else {
//				value[1] = ContantsUtil.EAT_AFTER;
//			}
//		} else {
//			value[0] = ContantsUtil.SLEEP_PRE;
//			value[1] = ContantsUtil.EAT_PRE;
//		}
//		return value;
//	}

	public static boolean getPageState(String obj) {
		Boolean state = pageMap.get(obj);
		if (state == null) {
			return false;
		}
		return state;
	}
	
	public static void resetPageState(){
		pageMap.clear();
	}

	public static void setProperty(String key, String value) {
		properties.put(key, value);
	}

	public static Object getProperty(String key) {
		return properties.get(key);
	}

	public static String getStringPro(String key) {
		return properties.get(key) + "";
	}

	public static float getFloatPro(String key) {
		return StringUtil.toFloat(properties.get(key));
	}

	public static void setPro(String key, String value) {
		properties.put(key, value);
	}

	public static int getIntPro(String key) {
		return StringUtil.toInt(properties.get(key));
	}
	
	public static boolean hasKey(String key){
		return properties.containsKey(key);
	}

	public static boolean canUpdate() {
		if (!isUpdate && (System.currentTimeMillis() - lastUpdateTime > delta)) {
			lastUpdateTime = System.currentTimeMillis();
			isUpdate = true;
			return true;
		}
		return false;
	}

	public static void stopUpdate() {
		isUpdate = false;
		lastUpdateTime = System.currentTimeMillis();
	}

	public static void startUpdate() {
		isUpdate = false;
		lastUpdateTime = 0;
	}
}

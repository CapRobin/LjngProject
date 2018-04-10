package com.zfg.org.myexample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zfg.org.myexample.db.dao.Alarm;
import com.zfg.org.myexample.db.dao.DefSet;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.db.dao.Plan;
import com.zfg.org.myexample.db.dao.UserSet;
import com.zfg.org.myexample.model.MeterInfoModel;
import com.zfg.org.myexample.utils.Config;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.StringUtil;

public class DefSetService {

	/**
	 * 控糖目标
	 * 
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	public List<DefSet> convertTarget(String data) throws JSONException {
		List<DefSet> listData = new ArrayList<DefSet>();
//		JSONObject jsonData = new JSONObject(data);
//		DefSet item = new DefSet();
//		item.setKey("levelHigh" + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("fbgHigh"));
//		listData.add(item);
//		item = new DefSet();
//		item.setKey("levelMid" + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("fbg"));
//		listData.add(item);
//		item = new DefSet();
//		item.setKey("levelLow" + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("fbgLow"));
//		listData.add(item);
//
//		item = new DefSet();
//		item.setKey("levelHigh" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("pbgHigh"));
//		listData.add(item);
//		item = new DefSet();
//		item.setKey("levelMid" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("pbg"));
//		listData.add(item);
//		item = new DefSet();
//		item.setKey("levelLow" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("pbgLow"));
//		listData.add(item);
//
//		item = new DefSet();
//		item.setKey("sleepLow" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("sleepLow"));
//		listData.add(item);
//		item = new DefSet();
//		item.setKey("sleep" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("sleep"));
//		listData.add(item);
//		item = new DefSet();
//		item.setKey("sleepHigh" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("sleepHigh"));
//		listData.add(item);
		return listData;
	}

	/**
	 * 控糖目标
	 * 
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	public List<UserSet> convertUserTarget(String data) throws JSONException {
		List<UserSet> listData = new ArrayList<UserSet>();
//		JSONObject jsonData = new JSONObject(data);
//		UserSet item = new UserSet();
//		item.setKey("levelHigh" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("fbgHigh"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//		item = new UserSet();
//		item.setKey("levelMid" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("fbg"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//		item = new UserSet();
//		item.setKey("levelLow" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("fbgLow"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//
//		item = new UserSet();
//		item.setKey("levelHigh" + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("pbgHigh"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//		item = new UserSet();
//		item.setKey("levelMid" + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("pbg"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//		item = new UserSet();
//		item.setKey("levelLow" + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("pbgLow"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//
//		item = new UserSet();
//		item.setKey("sleepLow" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("sleepLow"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//		item = new UserSet();
//		item.setKey("sleep" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("sleep"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//		item = new UserSet();
//		item.setKey("sleepHigh" + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("sleepHigh"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//		
//		//糖化值
//		if(jsonData.has("diastaticValue")){
//			item = new UserSet();
//			item.setKey("diastaticValue");
//			item.setValue(jsonData.getString("diastaticValue"));
//			item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//			listData.add(item);
//		}
//		
//		//体重  腰围   bmi
//		if(jsonData.has("waistLow")){
//			item = new UserSet();
//			item.setKey("waistLow");
//			item.setValue(jsonData.getString("waistLow"));
//			item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//			listData.add(item);	
//		}
//		if(jsonData.has("waistLow")){
//			item = new UserSet();
//			item.setKey("waistHigh");
//			item.setValue(jsonData.getString("waistHigh"));
//			item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//			listData.add(item);
//		}
//		
//		if(jsonData.has("waistLow")){
//			item = new UserSet();
//			item.setKey("weightHigh");
//			item.setValue(jsonData.getString("weightHigh"));
//			item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//			listData.add(item);
//		}
//		if(jsonData.has("waistLow")){
//			item = new UserSet();
//			item.setKey("weightLow");
//			item.setValue(jsonData.getString("weightLow"));
//			item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//			listData.add(item);
//		}
//		if(jsonData.has("waistLow")){
//			item = new UserSet();
//			item.setKey("bmiLow");
//			item.setValue(jsonData.getString("bmiLow"));
//			item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//			listData.add(item);
//		}
//		if(jsonData.has("waistLow")){
//			item = new UserSet();
//			item.setKey("bmiHigh");
//			item.setValue(jsonData.getString("bmiHigh"));
//			item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//			listData.add(item);
//		}
//		
		return listData;
	}

	public List<Plan> convertPlan(String data) throws JSONException {
		List<Plan> listData = new ArrayList<Plan>();
//		JSONArray array = new JSONArray(data);
//		for (int i = 0; i < array.length(); i++) {
//			JSONObject itemObj = array.getJSONObject(i);
//			Plan plan = new Plan();
//			plan.setIdx(itemObj.getInt("idx"));
//			plan.setName(itemObj.getString("name"));
//			plan.setDays(itemObj.getString("days"));
//			listData.add(plan);
//		}
		return listData;
	}

	public List<DefSet> convertTime(String data) throws JSONException {
		List<DefSet> listData = new ArrayList<DefSet>();
//		JSONObject jsonData = new JSONObject(data);
//		DefSet item = new DefSet();
//		item.setKey("clock" + ContantsUtil.BRECKFAST + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("breT1"));
//		listData.add(item);
//		item = new DefSet();
//		item.setKey("clock" + ContantsUtil.BRECKFAST + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("breT2"));
//		listData.add(item);
//
//		item = new DefSet();
//		item.setKey("clock" + ContantsUtil.LAUNCH + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("lunT1"));
//		listData.add(item);
//		item = new DefSet();
//		item.setKey("clock" + ContantsUtil.LAUNCH + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("lunT2"));
//		listData.add(item);
//
//		item = new DefSet();
//		item.setKey("clock" + ContantsUtil.DINNER + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("supT1"));
//		listData.add(item);
//		item = new DefSet();
//		item.setKey("clock" + ContantsUtil.DINNER + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("supT2"));
//		listData.add(item);
		return listData;
	}

	public List<UserSet> convertUserTime(String data) throws JSONException {
		List<UserSet> listData = new ArrayList<UserSet>();
//		JSONObject jsonData = new JSONObject(data);
//		UserSet item = new UserSet();
//		item.setKey("clock" + ContantsUtil.BRECKFAST + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("breT1"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//		item = new UserSet();
//		item.setKey("clock" + ContantsUtil.BRECKFAST + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("breT2"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//
//		item = new UserSet();
//		item.setKey("clock" + ContantsUtil.LAUNCH + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("lunT1"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//		item = new UserSet();
//		item.setKey("clock" + ContantsUtil.LAUNCH + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("lunT2"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//
//		item = new UserSet();
//		item.setKey("clock" + ContantsUtil.DINNER + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("supT1"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//		item = new UserSet();
//		item.setKey("clock" + ContantsUtil.DINNER + ContantsUtil.EAT_AFTER);
//		item.setValue(jsonData.getString("supT2"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
//
//		item = new UserSet();
//		item.setKey("clock" + ContantsUtil.SLEEP_PRE + ContantsUtil.EAT_PRE);
//		item.setValue(jsonData.getString("slpT1"));
//		item.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//		listData.add(item);
		return listData;
	}

	public List<Alarm> convertAlarm(String days) {
		Map<String, Alarm> mapData = new HashMap<String, Alarm>();
		try {
			JSONObject jsonData = new JSONObject(days).getJSONObject("days");
			for (int i = 1; i < 8; i++) {
				JSONObject itemData = jsonData.getJSONObject("d" + i);
				// 早餐前
				ddAlarm(0, itemData.getInt("breT1"), i, mapData);
				// 早餐后
				ddAlarm(1, itemData.getInt("breT2"), i, mapData);
				// 中餐前
				ddAlarm(10, itemData.getInt("lunT1"), i, mapData);
				// 中餐后
				ddAlarm(11, itemData.getInt("lunT2"), i, mapData);
				// 晚餐前
				ddAlarm(20, itemData.getInt("supT1"), i, mapData);
				// 晚餐后
				ddAlarm(21, itemData.getInt("supT2"), i, mapData);
				// 睡前
				ddAlarm(30, itemData.getInt("slpT1"), i, mapData);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ArrayList<Alarm>(mapData.values());
	}

	private void ddAlarm(int type, int enable, int week,
			Map<String, Alarm> mapData) {
//		Alarm alarm = mapData.get("plan" + type);
//		if (alarm == null) {
//			alarm = new Alarm();
//			String time = (String) Config.getProperty("clock" + type);
//			String timeArray[] = time.split(":");
//			alarm.setHour(StringUtil.toShort(timeArray[0]));
//			alarm.setMinite(StringUtil.toShort(timeArray[1]));
//			alarm.setMessage("您计划的测试时间到了");
//			alarm.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//			alarm.setSub_type((short) type);
//			alarm.setType((short) ContantsUtil.SUGAR_ALARM);
//			alarm.setTitle("提醒您");
//			alarm.setRepeat(-1);
//		}
//		if (enable == 1) {
//			alarm.set(week, true);
//			alarm.setUn(week, false);
//			alarm.setEnable(true);
//			alarm.setRepeat(-1);
//		} else {
//			alarm.set(week, false);
//			alarm.setUn(week, false);
//			alarm.setEnable(true);
//			alarm.setRepeat(-1);
//		}
//		mapData.put("plan" + type, alarm);
	}

	public List<MeterInfo> convertMeterInfo(List<MeterInfoModel> data) throws JSONException {
		List<MeterInfo> listData = new ArrayList<MeterInfo>();
		for (int i = 0; i < data.size(); i++) {
			MeterInfo meterInfo = new MeterInfo();
			meterInfo.setComm_address((data.get(i).getCOMM_ADDRESS().toString()));
			meterInfo.setCustomer_name((data.get(i).getCUSTOMER_NAME().toString()));
			meterInfo.setMetertype((data.get(i).getMeterType().toString()));
			meterInfo.setAreaname((data.get(i).getAREANAME().toString()));
			meterInfo.setName((data.get(i).getAREANAME().toString()));
			meterInfo.setPhone1((data.get(i).getPHONE1().toString()));
			meterInfo.setTerminal_address((data.get(i).getTERMINAL_ADDRESS().toString()));
			listData.add(meterInfo);
		}
		System.out.println(listData.toString());
		return listData;
	}


}

package com.zfg.org.myexample.service;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import com.zfg.org.myexample.model.ClockModel;
import com.zfg.org.myexample.db.dao.Alarm;

public class AlarmService {

	public List<ClockModel> convertClock(String days) {
		List<ClockModel> lists = new ArrayList<ClockModel>();
		try {
			JSONObject jsonData = new JSONObject(days);
			for (int i = 1; i < 8; i++) {
				JSONObject itemData = jsonData.getJSONObject("d" + i);
				// 星期label
				ClockModel model = new ClockModel();
				model.setLabel(true);
				model.setWeek(i);
				lists.add(model);
				// 早餐前
				model = new ClockModel();
				model.setLabel(false);
				model.setWeek(i);
				model.setEnable(itemData.getInt("breT1") == 1 ? 1 : -1);
				model.setType(0);
				lists.add(model);
				// 早餐后
				model = new ClockModel();
				model.setLabel(false);
				model.setWeek(i);
				model.setEnable(itemData.getInt("breT2") == 1 ? 1 : -1);
				model.setType(1);
				lists.add(model);
				// 中餐前
				model = new ClockModel();
				model.setLabel(false);
				model.setWeek(i);
				model.setEnable(itemData.getInt("lunT1") == 1 ? 1 : -1);
				model.setType(10);
				lists.add(model);
				// 中餐后
				model = new ClockModel();
				model.setLabel(false);
				model.setWeek(i);
				model.setEnable(itemData.getInt("lunT2") == 1 ? 1 : -1);
				model.setType(11);
				lists.add(model);
				// 晚餐前
				model = new ClockModel();
				model.setLabel(false);
				model.setWeek(i);
				model.setEnable(itemData.getInt("supT1") == 1 ? 1 : -1);
				model.setType(20);
				lists.add(model);
				// 晚餐后
				model = new ClockModel();
				model.setLabel(false);
				model.setWeek(i);
				model.setEnable(itemData.getInt("supT2") == 1 ? 1 : -1);
				model.setType(21);
				lists.add(model);
				// 睡前
				model = new ClockModel();
				model.setLabel(false);
				model.setWeek(i);
				model.setEnable(itemData.getInt("slpT1") == 1 ? 1 : -1);
				model.setType(30);
				lists.add(model);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * alarm to json
	 * 
	 * @param alarms
	 * @return
	 */
	public JSONObject convertJson(List<Alarm> alarms) {
		if (alarms.size() != 7) {
			return null;
		}
		try {
			JSONObject data = new JSONObject();
			JSONObject day1 = new JSONObject();
			data.put("d1", day1);
			JSONObject day2 = new JSONObject();
			data.put("d2", day2);
			JSONObject day3 = new JSONObject();
			data.put("d3", day3);
			JSONObject day4 = new JSONObject();
			data.put("d4", day4);
			JSONObject day5 = new JSONObject();
			data.put("d5", day5);
			JSONObject day6 = new JSONObject();
			data.put("d6", day6);
			JSONObject day7 = new JSONObject();
			data.put("d7", day7);
			// 早餐前
			for (int i = 1; i < 8; i++) {
				Alarm alarm = alarms.get(0);
				boolean is = alarm.isSet(i);
				int value = is ? 1 : 0;
				data.getJSONObject("d" + i).put("breT1", value);
			}
			// 早餐后
			for (int i = 1; i < 8; i++) {
				Alarm alarm = alarms.get(1);
				boolean is = alarm.isSet(i);
				int value = is ? 1 : 0;
				data.getJSONObject("d" + i).put("breT2", value);
			}
			// 中餐前
			for (int i = 1; i < 8; i++) {
				Alarm alarm = alarms.get(2);
				boolean is = alarm.isSet(i);
				int value = is ? 1 : 0;
				data.getJSONObject("d" + i).put("lunT1", value);
			}
			// 中餐后
			for (int i = 1; i < 8; i++) {
				Alarm alarm = alarms.get(3);
				boolean is = alarm.isSet(i);
				int value = is ? 1 : 0;
				data.getJSONObject("d" + i).put("lunT2", value);
			}
			// 晚餐前
			for (int i = 1; i < 8; i++) {
				Alarm alarm = alarms.get(4);
				boolean is = alarm.isSet(i);
				int value = is ? 1 : 0;
				data.getJSONObject("d" + i).put("supT1", value);
			}
			// 晚餐后
			for (int i = 1; i < 8; i++) {
				Alarm alarm = alarms.get(5);
				boolean is = alarm.isSet(i);
				int value = is ? 1 : 0;
				data.getJSONObject("d" + i).put("supT2", value);
			}
			// 睡前
			for (int i = 1; i < 8; i++) {
				Alarm alarm = alarms.get(6);
				boolean is = alarm.isSet(i);
				int value = is ? 1 : 0;
				data.getJSONObject("d" + i).put("slpT1", value);
			}
			return data;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}

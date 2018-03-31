package com.zfg.org.myexample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.zfg.org.myexample.db.AlarmBo;
//import com.zfg.org.myexample.db.BloodBo;
import com.zfg.org.myexample.db.EatBo;
import com.zfg.org.myexample.db.IndicateBo;
import com.zfg.org.myexample.db.MedicineBo;
import com.zfg.org.myexample.db.SportBo;
import com.zfg.org.myexample.db.UserBo;
import com.zfg.org.myexample.db.dao.Alarm;
import com.zfg.org.myexample.db.dao.Diabetes;
import com.zfg.org.myexample.db.dao.Eat;
import com.zfg.org.myexample.db.dao.IndicateValue;
import com.zfg.org.myexample.db.dao.Medicine;
import com.zfg.org.myexample.db.dao.Sport;
import com.zfg.org.myexample.db.dao.User;
//import com.zfg.org.myexample.dto.SportDto;
//import com.zfg.org.myexample.dto.SycnIndicateDto;
import com.zfg.org.myexample.utils.Config;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
//import com.google.gson.Gson;

public class UpdateService {

	private Context context;
	private UserBo userBo;
	private User user;
//	private Gson gson;

	public UpdateService(Context context, UserBo bo) {
		this.context = context;
		userBo = bo;
//		gson = new Gson();
		user = userBo.getUserByServerId(ContantsUtil.DEFAULT_TEMP_UID);
	}
	
	/**
	 * 用户自定义参数
	 * 
	 * @param updateTime
	 */
	public void updateUserProperty() {
//		if (user.getUpdate_time() != 0) {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//			params.put("sleepLow",
//					Config.getProperty("sleepLow" + ContantsUtil.EAT_PRE));
//			params.put("sleep",
//					Config.getProperty("sleep" + ContantsUtil.EAT_PRE));
//			params.put("sleepHigh",
//					Config.getProperty("sleepHigh" + ContantsUtil.EAT_PRE));
//			params.put("pbgHigh",
//					Config.getProperty("levelHigh" + ContantsUtil.EAT_AFTER));
//			params.put("pbg",
//					Config.getProperty("levelMid" + ContantsUtil.EAT_AFTER));
//			params.put("pbgLow",
//					Config.getProperty("levelLow" + ContantsUtil.EAT_AFTER));
//			params.put("fbgHigh",
//					Config.getProperty("levelHigh" + ContantsUtil.EAT_PRE));
//			params.put("fbg",
//					Config.getProperty("levelMid" + ContantsUtil.EAT_PRE));
//			params.put("fbgLow",
//					Config.getProperty("levelLow" + ContantsUtil.EAT_PRE));
//			params.put("diastaticValue", Config.getProperty("diastaticValue"));
//			//体重、身高、bmi目标 
//			params.put("waistLow", Config.getProperty("waistLow"));
//			params.put("waistHigh", Config.getProperty("waistHigh"));
//			params.put("weightHigh", Config.getProperty("weightHigh"));
//			params.put("weightLow", Config.getProperty("weightLow"));
//			params.put("bmiLow", Config.getProperty("bmiLow"));
//			params.put("bmiHigh", Config.getProperty("bmiHigh"));
//			
//			Log.d("target", params.toString());
//			// 控糖目标
//			String result = HttpServiceUtil.post(ContantsUtil.LOAD_TARGET,
//					params);
//			if (!CheckUtil.isNull(result)) {
//				try {
//					JSONObject resultJson = new JSONObject(result);
//					if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//						user.setUpdate_time(0);
//						userBo.updateUser(user);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		if (user.getTime_update() != 0) {
//			Map<String, Object> params = new HashMap<String, Object>();
//			// 提交提醒时间
//			params.clear();
//			params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//			params.put(
//					"breT1",
//					Config.getProperty("clock" + ContantsUtil.BRECKFAST
//							+ ContantsUtil.EAT_PRE));
//			params.put(
//					"breT2",
//					Config.getProperty("clock" + ContantsUtil.BRECKFAST
//							+ ContantsUtil.EAT_AFTER));
//			params.put(
//					"lunT1",
//					Config.getProperty("clock" + ContantsUtil.LAUNCH
//							+ ContantsUtil.EAT_PRE));
//			params.put(
//					"lunT2",
//					Config.getProperty("clock" + ContantsUtil.LAUNCH
//							+ ContantsUtil.EAT_AFTER));
//			params.put(
//					"supT1",
//					Config.getProperty("clock" + ContantsUtil.DINNER
//							+ ContantsUtil.EAT_PRE));
//			params.put(
//					"supT2",
//					Config.getProperty("clock" + ContantsUtil.DINNER
//							+ ContantsUtil.EAT_AFTER));
//			params.put(
//					"slpT1",
//					Config.getProperty("clock" + ContantsUtil.SLEEP_PRE
//							+ ContantsUtil.EAT_PRE));
//			Log.d("time", params.toString());
//			String result = HttpServiceUtil
//					.post(ContantsUtil.LOAD_TIME, params);
//			if (!CheckUtil.isNull(result)) {
//				try {
//					JSONObject resultJson = new JSONObject(result);
//					if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//						user.setTime_update(0);
//						userBo.updateUser(user);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}

	/**
	 * 提交血糖数据
	 * 
	 * @param user
	 */
	public void updateDiabetes() {
//		BloodBo bo = new BloodBo(context);
//		List<Diabetes> updateList = bo
//				.listUserUpdateData(ContantsUtil.DEFAULT_TEMP_UID);
//		if (updateList.size() == 0) {
//			return;
//		}
//		List<DialbeteDto> dataList = new ArrayList<DialbeteDto>();
//		for (Diabetes item : updateList) {
//			DialbeteDto model = new DialbeteDto();
//			if (item.getStatus() == ContantsUtil.DELETE) {
//				model.setId(item.getServerid());
//				model.setStatus(1);
//			} else if (item.getStatus() == ContantsUtil.NO_SERVER) {
//				model.setCreateTime(item.getCreate_time());
//				model.setDinOrder(item.getSub_type());
//				model.setDinStage(item.getType());
//				model.setLevel(item.getLevel());
//				model.setNote(item.getMark());
//				model.setSigns(item.getFeel());
//				model.setUpdateTime(item.getUpdate_time());
//				model.setValue(item.getValue() + "");
//				if (!CheckUtil.isNull(item.getServerid())) {
//					model.setId(item.getServerid());
//				}
//			}
//			dataList.add(model);
//		}
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//		params.put("dataString", gson.toJson(dataList));
//		String result = HttpServiceUtil.post(ContantsUtil.MERGE_DATA, params);
//		if (!CheckUtil.isNull(result)) {
//			try {
//				JSONObject resultJson = new JSONObject(result);
//				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//					String dataStr = resultJson.getString("data");
//					if (!CheckUtil.isNull(dataStr)) {
//						JSONObject dataObj = new JSONObject(dataStr);
//						JSONArray dataArray = new JSONArray(
//								dataObj.getString("glucoses"));
//						saveBackTransaction(updateList, dataArray);
//						long timestap = dataObj.getLong("timestamp");
//						user.setDiabetes_time(timestap);
//						userBo.updateUser(user);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	private void saveBackTransaction(List<Diabetes> data, JSONArray dataArray)
			throws JSONException {
//		BloodBo bo = new BloodBo(context);
//		for (int i = 0; i < dataArray.length(); i++) {
//			JSONObject item = dataArray.getJSONObject(i);
//			int status = item.getInt("status");
//			if (status == 1) {
//				bo.deleteData(item.getString("id"));
//			} else {
//				Diabetes diabetes = data.get(i);
//				String id = item.getString("id");
//				diabetes.setServerid(id);
//				diabetes.setStatus((short) ContantsUtil.SERVER);
//				bo.update(diabetes);
//			}
//		}
	}

	/**
	 * 更新个人计划到服务器
	 * 
	 * @param user
	 */
	public void updateAlarm() {
//		List<Alarm> alarmsList = new AlarmBo(context)
//				.list(ContantsUtil.DEFAULT_TEMP_UID,ContantsUtil.SUGAR_ALARM);
//		JSONObject planData = new AlarmService().convertJson(alarmsList);
//		if (user.getPlan_update() != 0 && planData != null) {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("mid", user.getService_uid());
//			params.put("schemeDetail", planData.toString());
//			String result = HttpServiceUtil.post(ContantsUtil.DBT_SCHEME,
//					params);
//			if (!CheckUtil.isNull(result)) {
//				try {
//					JSONObject resultJson = new JSONObject(result);
//					if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//						user.setTime_update(0);
//						userBo.updateUser(user);
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}
	
	public void updateEat(){
//		EatBo eatBo = new EatBo(context);
//		List<Eat> lists = eatBo.list(ContantsUtil.DEFAULT_TEMP_UID);
//		if(lists.size() == 0){
//			return;
//		}
//		List<EatDto> dataList = new ArrayList<EatDto>();
//		for(Eat eat : lists){
//			EatDto dto = new EatDto();
//			dto.of(eat);
//			dataList.add(dto);
//		}
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//		params.put("dataString", gson.toJson(dataList));
//		String result = HttpServiceUtil.post(ContantsUtil.UPDATE_EAT, params);
//		Log.d("update", result);
//		if (!CheckUtil.isNull(result)) {
//			try {
//				JSONObject resultJson = new JSONObject(result);
//				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//					String dataStr = resultJson.getString("data");
//					if (!CheckUtil.isNull(dataStr)) {
//						JSONObject dataObj = new JSONObject(dataStr);
//						JSONArray dataArray = new JSONArray(
//								dataObj.getString("dinners"));
//						for (int i = 0; i < dataArray.length(); i++) {
//							JSONObject item = dataArray.getJSONObject(i);
//							int status = item.getInt("status");
//							if (status == 1) {
//								eatBo.deleteData(item.getString("id"));
//							} else {
//								Eat eat = lists.get(i);
//								String id = item.getString("id");
//								eat.setServerid(id);
//								eat.setStatus((short) ContantsUtil.SERVER);
//								eatBo.updateEat(eat);
//							}
//						}
//						
//						long timestap = dataObj.getLong("timestamp");
//						user.setEat_time(timestap);
//						userBo.updateUser(user);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	
	public void updateSport(){
//		SportBo sportBo = new SportBo(context);
//		List<Sport> lists = sportBo.list(ContantsUtil.DEFAULT_TEMP_UID);
//		if(lists.size() == 0){
//			return;
//		}
//		
//		List<SportDto> dataList = new ArrayList<SportDto>();
//		for(Sport sport : lists){
//			SportDto dto = new SportDto();
//			dto.of(sport);
//			dataList.add(dto);
//		}
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//		params.put("dataString", gson.toJson(dataList));
//		String result = HttpServiceUtil.post(ContantsUtil.UPDATE_SPORT, params);
//		Log.d("update", result);
//		if (!CheckUtil.isNull(result)) {
//			try {
//				JSONObject resultJson = new JSONObject(result);
//				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//					String dataStr = resultJson.getString("data");
//					if (!CheckUtil.isNull(dataStr)) {
//						JSONObject dataObj = new JSONObject(dataStr);
//						JSONArray dataArray = new JSONArray(
//								dataObj.getString("sports"));
//						for (int i = 0; i < dataArray.length(); i++) {
//							JSONObject item = dataArray.getJSONObject(i);
//							int status = item.getInt("status");
//							if (status == 1) {
//								sportBo.deleteData(item.getString("id"));
//							} else {
//								Sport eat = lists.get(i);
//								String id = item.getString("id");
//								eat.setServerid(id);
//								eat.setStatus((short) ContantsUtil.SERVER);
//								sportBo.update(eat);
//							}
//						}
//						
//						long timestap = dataObj.getLong("timestamp");
//						user.setSport_time(timestap);
//						userBo.updateUser(user);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	
	public void updateMedicine(){
//		MedicineBo bo = new MedicineBo(context);
//		List<Medicine> lists = bo.list(ContantsUtil.DEFAULT_TEMP_UID);
//		if(lists.size() == 0){
//			return;
//		}
//		List<MedicineDto> dataList = new ArrayList<MedicineDto>();
//		for(Medicine medicine : lists){
//			MedicineDto dto = new MedicineDto();
//			dto.of(medicine);
//			dataList.add(dto);
//		}
//		//???
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//		params.put("dataString", gson.toJson(dataList));
//		Log.d("update", params.get("dataString")+"");
//		String result = HttpServiceUtil.post(ContantsUtil.UPDATE_MEDICINE, params);
//		Log.d("update", result);
//		if (!CheckUtil.isNull(result)) {
//			try {
//				JSONObject resultJson = new JSONObject(result);
//				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//					String dataStr = resultJson.getString("data");
//					if (!CheckUtil.isNull(dataStr)) {
//						JSONObject dataObj = new JSONObject(dataStr);
//						JSONArray dataArray = new JSONArray(
//								dataObj.getString("medics"));
//						for (int i = 0; i < dataArray.length(); i++) {
//							JSONObject item = dataArray.getJSONObject(i);
//							int status = item.getInt("status");
//							if (status == 1) {
//								bo.deleteData(item.getString("id"));
//							} else {
//								Medicine eat = lists.get(i);
//								String id = item.getString("id");
//								eat.setServerid(id);
//								eat.setStatus((short) ContantsUtil.SERVER);
//								bo.update(eat);
//							}
//						}
//						
//						long timestap = dataObj.getLong("timestamp");
//						user.setMedicine_time(timestap);
//						userBo.updateUser(user);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	
	public void updateIndicates(){
//		IndicateBo bo = new IndicateBo(context);
//		List<IndicateValue> lists = bo.list(ContantsUtil.DEFAULT_TEMP_UID);
//		if(lists.size() == 0){
//			return;
//		}
//		List<SycnIndicateDto> dataList = new ArrayList<SycnIndicateDto>();
//		for(IndicateValue value : lists){
//			SycnIndicateDto dto = new SycnIndicateDto();
//			dto.of(value);
//			dataList.add(dto);
//		}
//		//???
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//		params.put("dataString", gson.toJson(dataList));
//		Log.d("update", params.get("dataString")+"");
//		String result = HttpServiceUtil.post(ContantsUtil.UPDATE_INDICATE, params);
//		Log.d("update", result);
//		if (!CheckUtil.isNull(result)) {
//			try {
//				JSONObject resultJson = new JSONObject(result);
//				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//					String dataStr = resultJson.getString("data");
//					if (!CheckUtil.isNull(dataStr)) {
//						JSONObject dataObj = new JSONObject(dataStr);
//						JSONArray dataArray = new JSONArray(
//								dataObj.getString("indicators"));
//						for (int i = 0; i < dataArray.length(); i++) {
//							JSONObject item = dataArray.getJSONObject(i);
//							int status = item.getInt("status");
//							if (status == 1) {
//								bo.deleteData(item.getString("id"));
//							} else {
//								IndicateValue eat = lists.get(i);
//								String id = item.getString("id");
//								eat.setServerid(id);
//								eat.setStatus((short) ContantsUtil.SERVER);
//								bo.updateIndicateValue(eat);
//							}
//						}
//						
//						long timestap = dataObj.getLong("timestamp");
//						user.setIndicate_update(timestap);
//						userBo.updateUser(user);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
}

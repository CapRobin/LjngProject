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
import com.zfg.org.myexample.db.CommonBo;
import com.zfg.org.myexample.db.EatBo;
import com.zfg.org.myexample.db.IndicateBo;
import com.zfg.org.myexample.db.MedicineBo;
import com.zfg.org.myexample.db.PlanBo;
import com.zfg.org.myexample.db.PropertyBo;
import com.zfg.org.myexample.db.PropertyDefBo;
import com.zfg.org.myexample.db.SportBo;
import com.zfg.org.myexample.db.UserBo;
import com.zfg.org.myexample.db.dao.Alarm;
import com.zfg.org.myexample.db.dao.Common;
import com.zfg.org.myexample.db.dao.DefSet;
import com.zfg.org.myexample.db.dao.Diabetes;
import com.zfg.org.myexample.db.dao.Eat;
import com.zfg.org.myexample.db.dao.IndicateValue;
import com.zfg.org.myexample.db.dao.Medicine;
import com.zfg.org.myexample.db.dao.Plan;
import com.zfg.org.myexample.db.dao.Sport;
import com.zfg.org.myexample.db.dao.User;
import com.zfg.org.myexample.db.dao.UserSet;
import com.zfg.org.myexample.utils.Config;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;

/**
 * 加载服务端数据到本地的公共类，包括血糖、饮食、运动数据
 * 
 * @author Administrator
 * 
 */
public class LoadingService {

	private Context context;
	private UserBo userBo;
	private User user;

	public LoadingService(Context context, UserBo bo) {
		this.context = context;
		userBo = bo;
		user = userBo.getUserByServerId(ContantsUtil.DEFAULT_TEMP_UID);
	}

	public void loadingUserSet() {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//		params.put("timestamp", user.getDiabetes_time());
//		String result = HttpServiceUtil.post(ContantsUtil.SETTING_UPDATE,
//				params);
//		if (!CheckUtil.isNull(result)) {
//			try {
//				JSONObject resultJson = new JSONObject(result);
//				DefSetService service = new DefSetService();
//				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//					JSONObject data = resultJson.getJSONObject("data");
//					List<UserSet> listData = new ArrayList<UserSet>();
//					int state = 0;
//					// 控糖目标
//					String dbtTarget = data.getString("dbtTarget");
//					if (!CheckUtil.isNull(dbtTarget)) {
//						listData.addAll(service.convertUserTarget(dbtTarget));
//						state = 1;
//					}
//					// 控糖sycnTime
//					String dbtMonitimes = data.getString("dbtMonitimes");
//					if (!CheckUtil.isNull(dbtMonitimes)) {
//						listData.addAll(service.convertUserTime(dbtMonitimes));
//						state = 1;
//					}
//					new PropertyBo(context).saveUpdate(listData);
//					if (state == 1) {
//						Config.loadDefSet(context);
//					}
//					// 控糖计划
//					if (data.has("dbtScheme")) {
//						String dbtScheme = data.getString("dbtScheme");
//						if (!CheckUtil.isNull(dbtScheme)) {
//							new AlarmBo(context).updateAlarm(
//									service.convertAlarm(dbtScheme),
//									ContantsUtil.DEFAULT_TEMP_UID,
//									ContantsUtil.curUser.getService_uid(),
//									ContantsUtil.SUGAR_ALARM);
//						}
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	// 同步饮食数据
	public void loadingEatData() {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//		params.put("timestamp", user.getEat_time());
//		String result = HttpServiceUtil.post(ContantsUtil.LOAD_EAT, params);
//		Log.d("eat", result);
//		if (!CheckUtil.isNull(result)) {
//			try {
//				JSONObject resultJson = new JSONObject(result);
//				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//					String dataStr = resultJson.getString("data");
//					if (!CheckUtil.isNull(dataStr)) {
//						JSONObject dataJson = new JSONObject(dataStr);
//						EatBo eatBo = new EatBo(context);
//						EatService service = new EatService();
//						JSONArray dataArray = new JSONArray(
//								dataJson.getString("dinners"));
//						for (int i = 0; i < dataArray.length(); i++) {
//							JSONObject item = dataArray.getJSONObject(i);
//							int status = item.getInt("status");
//							if (status == 1) {
//								eatBo.deleteData(item.getString("id"));
//							} else {
//								Eat eat = service.convertJson(item);
//								eatBo.saveByServerId(eat);
//							}
//						}
//						long timestap = dataJson.getLong("timestamp");
//						user.setEat_time(timestap);
//						userBo.updateUser(user);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	// 同步运动数据
	public void loadingSportData() {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//		params.put("timestamp", user.getSport_time());
//		String result = HttpServiceUtil.post(ContantsUtil.LOAD_SPORT, params);
//		Log.d("sport", result);
//		if (!CheckUtil.isNull(result)) {
//			try {
//				JSONObject resultJson = new JSONObject(result);
//				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//					String dataStr = resultJson.getString("data");
//					if (!CheckUtil.isNull(dataStr)) {
//						JSONObject dataJson = new JSONObject(dataStr);
//						SportBo sportBo = new SportBo(context);
//						SportService service = new SportService();
//						JSONArray dataArray = new JSONArray(
//								dataJson.getString("sports"));
//						for (int i = 0; i < dataArray.length(); i++) {
//							JSONObject item = dataArray.getJSONObject(i);
//							int status = item.getInt("status");
//							if (status == 1) {
//								sportBo.deleteData(item.getString("id"));
//							} else {
//								Sport sport = service.convertJson(item);
//								sportBo.saveByServerId(sport);
//							}
//						}
//						long timestap = dataJson.getLong("timestamp");
//						user.setSport_time(timestap);
//						userBo.updateUser(user);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	// 同步指标数据
	public void loadingIndicateData() {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//		params.put("timestamp", user.getIndicate_update());
//		String result = HttpServiceUtil
//				.post(ContantsUtil.LOAD_INDICATE, params);
//		if (!CheckUtil.isNull(result)) {
//			try {
//				JSONObject resultJson = new JSONObject(result);
//				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//					String dataStr = resultJson.getString("data");
//					if (!CheckUtil.isNull(dataStr)) {
//						JSONObject dataJson = new JSONObject(dataStr);
//						IndicateBo indicate = new IndicateBo(context);
//						IndicateService service = new IndicateService();
//						JSONArray dataArray = new JSONArray(
//								dataJson.getString("indicators"));
//						for (int i = 0; i < dataArray.length(); i++) {
//							JSONObject item = dataArray.getJSONObject(i);
//							int status = item.getInt("status");
//							if (status == 1) {
//								indicate.deleteData(item.getString("id"));
//							} else {
//								IndicateValue normal = service
//										.convertJson(item);
//								indicate.saveByServerId(normal);
//							}
//						}
//						long timestap = dataJson.getLong("timestamp");
//						user.setIndicate_update(timestap);
//						userBo.updateUser(user);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	// 同步用药数据
	public void loadingMedicineData() {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//		params.put("timestamp", user.getMedicine_time());
//		String result = HttpServiceUtil
//				.post(ContantsUtil.LOAD_MEDICINE, params);
//		if (!CheckUtil.isNull(result)) {
//			try {
//				JSONObject resultJson = new JSONObject(result);
//				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//					String dataStr = resultJson.getString("data");
//					if (!CheckUtil.isNull(dataStr)) {
//						JSONObject dataJson = new JSONObject(dataStr);
//						MedicineBo medicineBo = new MedicineBo(context);
//						AlarmBo alarmBo = new AlarmBo(context);
//						MedicineService service = new MedicineService();
//						JSONArray dataArray = new JSONArray(
//								dataJson.getString("medics"));
//						for (int i = 0; i < dataArray.length(); i++) {
//							JSONObject item = dataArray.getJSONObject(i);
//							int status = item.getInt("status");
//							if (status == 1) {
//								medicineBo.deleteData(item.getString("id"));
//							} else {
//								List<Alarm> alarms = new ArrayList<Alarm>();
//								Medicine medicine = service.convertJson(alarms,
//										item);
//								long id = medicineBo.saveByServerId(medicine);
//								if (alarms.size() != 0) {
//									for (Alarm alarm : alarms) {
//										alarm.setoId(id);
//									}
//									alarmBo.saveAlarmsNoNext(id, alarms,
//											ContantsUtil.DEFAULT_TEMP_UID,
//											ContantsUtil.curUser
//													.getService_uid());
//								}
//							}
//						}
//						alarmBo.setNextAlarm(ContantsUtil.DEFAULT_TEMP_UID,
//								ContantsUtil.curUser.getService_uid());
//						long timestap = dataJson.getLong("timestamp");
//						user.setMedicine_time(timestap);
//						userBo.updateUser(user);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	/**
	 * 同步血糖数据
	 * 
	 * @param user
	 */
	public void loadingDbtData() {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("mid", ContantsUtil.DEFAULT_TEMP_UID);
//		params.put("timestamp", user.getDiabetes_time());
//		String result = HttpServiceUtil.post(ContantsUtil.UPDATE_DBT, params);
//		if (!CheckUtil.isNull(result)) {
//			try {
//				JSONObject resultJson = new JSONObject(result);
//				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
//					String dataStr = resultJson.getString("data");
//					if (!CheckUtil.isNull(dataStr)) {
//						JSONObject dataJson = new JSONObject(dataStr);
//						JSONArray dataArray = new JSONArray(
//								dataJson.getString("glucoses"));
//						BloodBo bo = new BloodBo(context);
//						DiabetesService service = new DiabetesService();
//						for (int i = 0; i < dataArray.length(); i++) {
//							JSONObject item = dataArray.getJSONObject(i);
//							int status = item.getInt("status");
//							if (status == 1) {
//								bo.deleteData(item.getString("id"));
//							} else {
//								Diabetes diabetes = service.convertJson(item);
//								bo.saveUpdateDiabetes(diabetes);
//							}
//						}
//						long timestap = dataJson.getLong("timestamp");
//						user.setDiabetes_time(timestap);
//						userBo.updateUser(user);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	public static void sycnData(Preference preference, Context context,
			String result) {
		if (!CheckUtil.isNull(result)) {
			try {
				JSONObject resultJson = new JSONObject(result);
				int state = 0;
				if (CheckUtil.checkStatusOk(resultJson.getInt("status"))) {
					JSONObject data = resultJson.getJSONObject("data");
					// 伴随状态
					String dbtSigns = data.getString("dbtSigns");
					if (!CheckUtil.isNull(dbtSigns)) {
						CommonService commonService = new CommonService();
						List<Common> listData = commonService.convertCommon(
								dbtSigns, "dbtSigns");
						CommonBo commBo = new CommonBo(context);
						commBo.saveUpdateList(listData);
					}
					// 控糖目标
					List<DefSet> listData = new ArrayList<DefSet>();
					String dbtTarget = data.getString("dbtTarget");
					if (!CheckUtil.isNull(dbtTarget)) {
						DefSetService service = new DefSetService();
						listData.addAll(service.convertTarget(dbtTarget));
						state = 1;
					}
					// 控糖sycnTime
					String dbtMonitimes = data.getString("dbtMonitimes");
					if (!CheckUtil.isNull(dbtMonitimes)) {
						DefSetService service = new DefSetService();
						listData.addAll(service.convertTime(dbtMonitimes));
						state = 1;
					}

					long sycnTime = data.getLong("timestamp");
					new PropertyDefBo(context).saveUpdate(listData);
					if (state == 1) {
						Config.loadDefSet(context);
					}
					preference.putLong(Preference.SYS_UPDATE_TIME, sycnTime);

					// 同步测试计划
					String dbtScheme = data.getString("dbtSchemes");
					if (!CheckUtil.isNull(dbtScheme)) {
						DefSetService service = new DefSetService();
						List<Plan> alarms = service.convertPlan(dbtScheme);
						new PlanBo(context).saveList(alarms);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}

package com.zfg.org.myexample.db;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.zfg.org.myexample.db.dao.Common;
import com.zfg.org.myexample.db.dao.Diabetes;
import com.zfg.org.myexample.db.dao.Eat;
import com.zfg.org.myexample.db.dao.Sport;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;

public class SycnData {

	private Context context;
	private String json;

	private SycnData(Context context, String data) {
		this.context = context;
		json = data;
	}

	public void sycnData() {

	}

	/**
	 * 同步血糖数据
	 * 
	 * @param data
	 */
	private void sycnDiabetes(String data) throws JSONException {
		if (CheckUtil.isNull(data)) {
//			JSONArray array = new JSONArray(data);
//			BloodBo bo = new BloodBo(context);
//			for (int i = 0; i < array.length(); i++) {
//				JSONObject itemData = array.getJSONObject(i);
//				int state = itemData.getInt("state");
//				if (CheckUtil.checkEquels(state, ContantsUtil.DELETE)) {
//					String serverId = itemData.getString("id");
//					bo.deleteData(serverId);
//				} else if (CheckUtil.checkEquels(state, ContantsUtil.NO_SERVER)) {
//					bo.saveUpdateDiabetes(toDiabetesModel(itemData));
//				}
//			}
		}
	}

	public Diabetes toDiabetesModel(JSONObject data) {
		Diabetes diabetes = new Diabetes();

		return diabetes;
	}

	/**
	 * 同步血糖数据
	 * 
	 * @param data
	 */
	private void sycnEat(String data) throws JSONException {
		if (CheckUtil.isNull(data)) {
			JSONArray array = new JSONArray(data);
			EatBo bo = new EatBo(context);
			for (int i = 0; i < array.length(); i++) {
				JSONObject itemData = array.getJSONObject(i);
				int state = itemData.getInt("state");
				if (CheckUtil.checkEquels(state, ContantsUtil.DELETE)) {
					String serverId = itemData.getString("id");
					bo.deleteData(serverId);
				} else if (CheckUtil.checkEquels(state, ContantsUtil.NO_SERVER)) {
					bo.saveUpdateEat(toEatModel(itemData));
				}
			}
		}
	}

	public Eat toEatModel(JSONObject data) {
		Eat eat = new Eat();

		return eat;
	}

	/**
	 * 同步运动数据
	 * @param data
	 * @throws JSONException
	 */
	private void sycnSport(String data) throws JSONException {
		if (CheckUtil.isNull(data)) {
			JSONArray array = new JSONArray(data);
			SportBo bo = new SportBo(context);
			for (int i = 0; i < array.length(); i++) {
				JSONObject itemData = array.getJSONObject(i);
				int state = itemData.getInt("state");
				if (CheckUtil.checkEquels(state, ContantsUtil.DELETE)) {
					String serverId = itemData.getString("id");
					bo.deleteData(serverId);
				} else if (CheckUtil.checkEquels(state, ContantsUtil.NO_SERVER)) {
					bo.saveUpdateSport(toSportModel(itemData));
				}
			}
		}
	}

	public Sport toSportModel(JSONObject data) {
		Sport eat = new Sport();

		return eat;
	}
	
	/**
	 * 同步枚举数据
	 * @param data
	 * @throws JSONException
	 */
	private void sycnCommon(String data) throws JSONException {
		if (CheckUtil.isNull(data)) {
			JSONArray array = new JSONArray(data);
			CommonBo bo = new CommonBo(context);
			for (int i = 0; i < array.length(); i++) {
				JSONObject itemData = array.getJSONObject(i);
				int state = itemData.getInt("state");
				if (CheckUtil.checkEquels(state, ContantsUtil.DELETE)) {
					String serverId = itemData.getString("id");
					bo.deleteByServerId(serverId);
				} else if (CheckUtil.checkEquels(state, ContantsUtil.NO_SERVER)) {
					bo.saveOrUpdate(toCommontModel(itemData));
				}
			}
		}
	}
	
	private Common toCommontModel(JSONObject itemData){
		Common common = new Common();
		
		return common;
	}
}

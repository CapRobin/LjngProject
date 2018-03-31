package com.zfg.org.myexample.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.zfg.org.myexample.db.dao.Diabetes;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.DateUtil;
import com.zfg.org.myexample.utils.StringUtil;

public class DiabetesService {
	
	public Diabetes convertJson(JSONObject data) throws JSONException{
		Diabetes diabetes = new Diabetes();
		diabetes.setValue(StringUtil.toFloat(data.get("value")));
		diabetes.setCreate_time(data.getLong("createTime"));
		diabetes.setDay(DateUtil.parseToDate(diabetes.getCreate_time()));
		diabetes.setUpdate_time(data.getLong("updateTime"));
		diabetes.setLevel(data.getInt("level"));
		diabetes.setFeel(data.getString("signs"));
		diabetes.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
		diabetes.setServerid(data.getString("id"));
		diabetes.setType(StringUtil.toShort(data.get("dinStage")));
		diabetes.setSub_type(StringUtil.toShort(data.get("dinOrder")));
		diabetes.setMark(data.getString("note"));
		diabetes.setStatus((short) ContantsUtil.SERVER);
		return diabetes;
	}

}

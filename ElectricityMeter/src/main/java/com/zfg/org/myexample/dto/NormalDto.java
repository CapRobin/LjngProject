package com.zfg.org.myexample.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class NormalDto {

	public long id;
	public long issueId;
	public int strength;
	public float heat;
	
	public void of(JSONObject dataJson) throws JSONException{
		id = dataJson.getLong("id");
		issueId = dataJson.getLong("issueId");
		strength = dataJson.getInt("strength");
		heat = (float) dataJson.getDouble("heat");
	}
}

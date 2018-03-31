package com.zfg.org.myexample.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlanDto {

	public int index;
        public String name;
	public String contain = "";

	public void of(JSONObject data) throws JSONException {
		this.name = data.getString("name");
		this.index = data.getInt("index");
		JSONArray array = data.getJSONArray("contain");
		for (int i = 0; i < array.length(); i++) {
			this.contain += array.get(i).toString() + "\n";
		}
	}

}

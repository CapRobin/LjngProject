package com.zfg.org.myexample.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class GroupDto {

	public long id;
	public String name;
	public int idx;
	public long parentId;
	public String icon;
	
	public void of(JSONObject json) throws JSONException{
		this.id = json.getLong("id");
		this.name = json.getString("name");
		this.idx = json.getInt("idx");
		this.parentId = json.getLong("parentId");
		if(json.has("icon")){
			this.icon = json.getString("icon");
		}
	}

}

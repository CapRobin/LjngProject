package com.zfg.org.myexample.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemModel {
	
	public Integer page_index;
	public String page_name;
	public int days_refresh;
	public boolean is_write = false;
	public long update_time;
	public List<DataModel> question_line = new ArrayList<DataModel>();
	
	public void addDataModel(JSONObject data) throws JSONException{
		DataModel model = new DataModel();
		model.sex = data.getInt("sex");
		model.index = data.getInt("index");
		model.code = data.getString("code");
		model.question = data.getString("question");
		model.method_question = data.getInt("method_question");
		if(data.has("value_mid")){
			model.value_mid = data.getString("value_mid");
			if(model.method_question == 1){
				model.answer = model.value_mid;
			}
		}
		if(data.has("unit")){
			model.unit = data.getString("unit");
		}
		if(data.has("answer")){
			model.answer = data.getString("answer");
		}
		question_line.add(model);
	}
}

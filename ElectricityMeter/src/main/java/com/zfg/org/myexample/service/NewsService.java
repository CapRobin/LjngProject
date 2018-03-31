package com.zfg.org.myexample.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zfg.org.myexample.db.dao.Favorate;
import com.zfg.org.myexample.db.dao.News;
import com.zfg.org.myexample.utils.CheckUtil;

public class NewsService {
	
	public List<News> convertCommon(String data)
			throws JSONException {
		List<News> listData = new ArrayList<News>();
		if (!CheckUtil.isNull(data)) {
			JSONArray dataArray = new JSONArray(data);
			for (int i = 0; i < dataArray.length(); i++) {
				listData.add(convertModel(dataArray.getJSONObject(i)));
			}
		}
		return listData;
	}

	public News convertModel(JSONObject data)
			throws JSONException {
		News set = new News();
		set.setTitle(data.getString("title"));
		set.setAuthor(data.getString("author"));
		set.setDay(data.getLong("createTime"));
		set.setSummary(data.getString("summary"));
		set.setThumbnail(data.getString("thumbnail"));
		set.setContent(data.getString("content"));
		set.setSid(data.getLong("id"));
		return set;
	}
	
	public News convertFavorate(Favorate favorate){
		News set = new News();
		set.setTitle(favorate.getTitle());
		set.setAuthor(favorate.getAuthor());
		set.setDay(favorate.getDay());
		set.setSummary(favorate.getSummary());
		set.setThumbnail(favorate.getThumbnail());
		set.setContent(favorate.getContent());
		return set;
	}
}

package com.zfg.org.myexample.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.zfg.org.myexample.db.dao.User;

public class UserService {

	public User convertJsonTo(JSONObject jsonData) throws JSONException {
		User user = new User();
		user.setPhone(jsonData.getString("phone"));
		user.setNick_name(jsonData.getString("nickname"));
		user.setService_uid(jsonData.getString("uid"));
		user.setService_mid(jsonData.getString("mid"));
		user.setDiabetes_time(0);
		user.setEat_time(0);
		user.setSport_time(0);
		user.setUpdate_time(0);
		user.setTime_update(0);
		user.setIndicate_update(0);
		user.setSupport(3000);
		return user;
	}
	
	public User convertUserTo(User u) throws JSONException {
	    User user = new User();
	    user.setPhone(u.getPhone());
	    user.setNick_name(u.getNick_name());
	    user.setService_uid(u.getService_uid());
	    user.setService_mid(u.getService_mid());
	    user.setDiabetes_time(0);
	    user.setEat_time(0);
	    user.setSport_time(0);
	    user.setUpdate_time(0);
	    user.setTime_update(0);
	    user.setIndicate_update(0);
	    user.setSupport(3000);
	    return user;
	}

}

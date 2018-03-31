package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.List;
import android.content.Context;

import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.UserSet;
import com.zfg.org.myexample.db.dao.UserSetDao;
import com.zfg.org.myexample.db.dao.UserSetDao.Properties;

import de.greenrobot.dao.query.QueryBuilder;

public class PropertyBo {

	private UserSetDao dao;

	public PropertyBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		dao = mDaoSession.getUserSetDao();
	}

	/**
	 * 根据key获取值
	 * @param key
	 * @param userId
	 * @return
	 */
	public UserSet getByKey(String key, String userId) {
		QueryBuilder<UserSet> qb = dao.queryBuilder();
		qb.where(Properties.Service_mid.eq(userId), Properties.Key.eq(key));
		try {
			return qb.unique();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取所有配置
	 * @param userId
	 * @return
	 */
	public List<UserSet> getList(String userId) {
		QueryBuilder<UserSet> qb = dao.queryBuilder();
		qb.where(Properties.Service_mid.eq(userId));
		return qb.list();
	}
	
	/**
	 * 更新某一个值
	 * @param key
	 * @param userId
	 * @param value
	 */
	public void updateByKey(String key,String userId,String value){
		UserSet set = getByKey(key,userId);
		if(set == null){
			set = new UserSet();
			set.setKey(key);
			set.setValue(value);
			set.setService_mid(userId);
			dao.insert(set);
		}else{
			set.setValue(value);
			dao.update(set);
		}
	}
	
	/**
	 * 更新
	 * 
	 * @param data
	 */
	public void saveUpdate(List<UserSet> data) {
		dao.deleteAll();
		dao.insertInTx(data);
	}
}

package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.List;

import android.content.Context;

import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.User;
import com.zfg.org.myexample.db.dao.UserDao;
import com.zfg.org.myexample.db.dao.UserDao.Properties;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * 用户表的数据库操作类
 * 
 * @author Chanjc@ifenguo.com
 * @crusereDate 2014年7月9日
 * 
 */
public class UserBo {

	private UserDao userDao;

	public UserBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		userDao = mDaoSession.getUserDao();
	}

	/**
	 * 新增一个用户
	 * 
	 * @param user
	 * @return 影响行数
	 */
	public long saveUser(User user) {
		return userDao.insert(user);
	}
	
	public User saveUserServer(User user){
		User temp = getUserByServerId(user.getService_uid());
		if(temp == null){
			long id = userDao.insert(user);
			user.setId(id);
			return user;
		}else{
			temp.setService_mid(user.getService_mid());
			temp.setService_uid(user.getService_uid());
			temp.setNick_name(user.getNick_name());
			temp.setPhone(user.getPhone());
			userDao.update(temp);
			return temp;
		}
	}

	/**
	 * 删除一个用户
	 * 
	 * @param user
	 */
	public void deleteUser(User user) {
		if (user != null) {
			userDao.delete(user);
		}
	}

	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * 获取一个用户
	 */
	public User getUser(long id) {
		QueryBuilder qb = userDao.queryBuilder();
		qb.where(Properties.Id.eq(id));
		if (qb.list().size() > 0) {
			return (User) qb.list().get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据uid得到她的用户信息
	 * @param uid
	 * @return
	 */
	public User getUserByServerId(String master_mid) {
		QueryBuilder<User> qb = userDao.queryBuilder();
		qb.where(Properties.Service_mid.eq(master_mid));
		if (qb.list().size() > 0) {
			return (User) qb.list().get(0);
		} else {
			return null;
		}
	}

	/**
	 * 保存或修改
	 * 
	 * @param user
	 * @return
	 */
	public void updateUser(User user) {
		if (user != null) {
			userDao.update(user);
		}
	}
}

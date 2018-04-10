package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.zfg.org.myexample.db.dao.Common;
import com.zfg.org.myexample.db.dao.CommonDao;
import com.zfg.org.myexample.db.dao.CommonDao.Properties;
import com.zfg.org.myexample.db.dao.DaoSession;

import de.greenrobot.dao.query.QueryBuilder;

/**
 *
 */
public class CommonBo {
	private CommonDao commonDao;

	public CommonBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		commonDao = mDaoSession.getCommonDao();
	}

	/**
	 * 保存或者更新某一条数据
	 * 
	 * @param common
	 * @return
	 */
	public long saveOrUpdate(Common common) {
		QueryBuilder<Common> qb = commonDao.queryBuilder();
		qb.where(Properties.Serverid.eq(common.getServerid()));
		if (qb.count() == 0) {
			return commonDao.insert(common);
		} else {
			Common res = qb.unique();
			res.setName(common.getName());
			res.setImage(common.getImage());
			commonDao.update(res);
			return res.getId();
		}
	}

	/**
	 * 获取某分类的common
	 * 
	 * @param group
	 * @return
	 */
	public List<Common> getCommonsByGroup(String group) {
		QueryBuilder<Common> qb = commonDao.queryBuilder();
		qb.where(Properties.Group.eq(group));
		return qb.list();
	}

	public Map<String, Common> getCommMap(String group) {
		QueryBuilder<Common> qb = commonDao.queryBuilder();
		qb.where(Properties.Group.eq(group)).orderAsc(Properties.Index);
		List<Common> data = qb.list();
		Map<String, Common> mapData = new HashMap<String, Common>();
		for (Common itemData : data) {
			mapData.put(itemData.getServerid(), itemData);
		}
		return mapData;
	}

	public void saveUpdateList(List<Common> listData) {
		commonDao.deleteAll();
		commonDao.insertInTx(listData);
	}

	/**
	 * 删除某条服务端同步数据
	 *
	 * @param serverId
	 */
	public void deleteByServerId(String serverId) {
		QueryBuilder<Common> qb = commonDao.queryBuilder();
		qb.where(Properties.Serverid.eq(serverId));
		commonDao.deleteInTx(qb.list());
	}
}

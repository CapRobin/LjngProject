package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.List;

import android.content.Context;

import com.zfg.org.myexample.db.dao.AlarmHistory;
import com.zfg.org.myexample.db.dao.AlarmHistoryDao;
import com.zfg.org.myexample.db.dao.DaoSession;
import de.greenrobot.dao.query.QueryBuilder;

public class HistoryBo {
	private AlarmHistoryDao cacheDao;

	public HistoryBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		cacheDao = mDaoSession.getAlarmHistoryDao();
	}

	public List<AlarmHistory> listData() {
		QueryBuilder<AlarmHistory> qb = cacheDao.queryBuilder();
		return qb.list();
	}

	public void saveCache(AlarmHistory cache) {
		cacheDao.insert(cache);
	}

	public void saveList(List<AlarmHistory> listData) {
		cacheDao.insertInTx(listData);
	}

	/**
	 * 删除单条记录
	 * 
	 * @param id
	 */
	public void delete(long id) {
		AlarmHistory cache = new AlarmHistory(id);
		cacheDao.delete(cache);
	}

	public void deletes(List<AlarmHistory> list) {
		cacheDao.deleteInTx(list);
	}

	public void deleteKeys(List<Long> list) {
		cacheDao.deleteByKeyInTx(list);
	}
}

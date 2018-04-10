package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.List;

import android.content.Context;

import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.DiabetesCache;
import com.zfg.org.myexample.db.dao.DiabetesCacheDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * 缓存临时存放bo
 */
public class CacheBo {
	
	private DiabetesCacheDao cacheDao;

	public CacheBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		cacheDao = mDaoSession.getDiabetesCacheDao();
	}
	
	public List<DiabetesCache> listData(){
		QueryBuilder<DiabetesCache> qb = cacheDao.queryBuilder();
		return qb.list();
	}
	
	public int count(){
		return (int) cacheDao.count();
	}
	
	public void saveCache(DiabetesCache cache){
		cacheDao.insert(cache);
	}
	
	public void saveList(List<DiabetesCache> listData){
		cacheDao.insertInTx(listData);
	}
	
	/**
	 * 删除单条记录
	 * @param id
	 */
	public void delete(long id){
		DiabetesCache cache = new DiabetesCache(id);
		cacheDao.delete(cache);
	}
	
	public void deletes(List<DiabetesCache> list){
		cacheDao.deleteInTx(list);
	}
	
	public void deleteKeys(List<Long> list){
		cacheDao.deleteByKeyInTx(list);
	}

}

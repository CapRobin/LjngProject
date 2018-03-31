package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.List;

import android.content.Context;

import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.Sport;
import com.zfg.org.myexample.db.dao.SportDao;
import com.zfg.org.myexample.db.dao.SportDao.Properties;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;

import de.greenrobot.dao.query.QueryBuilder;

public class SportBo {

	private SportDao sportDao;

	public SportBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		sportDao = mDaoSession.getSportDao();
	}

	public Sport getById(long id) {
		return sportDao.load(id);
	}

	/**
	 * 保存或修改
	 * 
	 * @param Eat
	 * @return
	 */
	public long saveUpdateSport(Sport sport) {
		return sportDao.insertOrReplace(sport);
	}
	
	public void update(Sport sport){
		sportDao.update(sport);
	}

	/**
	 * 保存或修改，根据服务器id
	 * 
	 * @param sport
	 */
	public void saveByServerId(Sport sport) {
		QueryBuilder<Sport> qb = sportDao.queryBuilder();
		qb.where(Properties.Serverid.eq(sport.getServerid()));
		List<Sport> list = qb.list();
		if (list.size() > 1) {
			sport.setId(list.get(0).getId());
			sportDao.update(sport);
		} else {
			sportDao.insert(sport);
		}
	}

	/**
	 * 获取某一天的所有值
	 * 
	 * @param mid
	 * @param day
	 * @return
	 */
	public List<Sport> listDaySport(String mid, String day) {
		QueryBuilder<Sport> qb = sportDao.queryBuilder();
		qb.where(Properties.Status.notEq(ContantsUtil.DELETE), Properties.Service_mid.eq(mid),
				Properties.Day.eq(day));
		return qb.list();
	}
	
	public List<Sport> list(String mid){
		QueryBuilder<Sport> qb = sportDao.queryBuilder();
		qb.where(Properties.Service_mid.eq(mid),
				Properties.Status.notEq(ContantsUtil.SERVER));
		return qb.list();
	}

	/**
	 * 加载最近多长时间的数据
	 * 
	 * @param mid
	 * @param lastTime
	 * @return
	 */
	public List<Sport> loadTotal(String mid, long lastTime) {
		QueryBuilder<Sport> qb = sportDao.queryBuilder();
		qb.where(Properties.Status.notEq(ContantsUtil.DELETE),
				Properties.Service_mid.eq(mid),
				Properties.Create_time.lt(lastTime));
		return qb.list();
	}

	/**
	 * 加载最近多长时间的数据
	 * 
	 * @param mid
	 * @param lastTime
	 * @return
	 */
	public List<Sport> loadTotalOrder(String mid, long lastTime,long after) {
		QueryBuilder<Sport> qb = sportDao.queryBuilder();
		qb.where(Properties.Status.notEq(ContantsUtil.DELETE),
				Properties.Service_mid.eq(mid),
				Properties.Create_time.lt(after),
				Properties.Create_time.gt(lastTime)).orderAsc(
				Properties.Create_time);
		return qb.list();
	}

	/**
	 * 查询需要同步到服务端数据
	 * 
	 * @return
	 */
	public List<Sport> loadSycnData() {
		QueryBuilder<Sport> qb = sportDao.queryBuilder();
		qb.where(Properties.Status.notEq(ContantsUtil.SERVER));
		return qb.list();
	}

	/**
	 * 根据服务端id删除本地数据
	 * 
	 * @param serverId
	 */
	public void deleteData(String serverId) {
		QueryBuilder<Sport> qb = sportDao.queryBuilder();
		qb.where(Properties.Serverid.eq(serverId));
		sportDao.deleteInTx(qb.list());
	}
	
	/**
	 * 本地删除
	 * @param diabetes
	 */
	public void deleteLocal(Sport sport){
		if(CheckUtil.isNull(sport.getServerid())){
			sportDao.delete(sport);
		}else{
			sport.setStatus((short) ContantsUtil.DELETE);
			sportDao.update(sport);
		}
	}

	public void delete(Sport sport) {
		sportDao.delete(sport);
	}
}

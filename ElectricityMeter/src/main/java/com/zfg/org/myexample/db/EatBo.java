package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.List;

import android.content.Context;

import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.Eat;
import com.zfg.org.myexample.db.dao.EatDao;
import com.zfg.org.myexample.db.dao.EatDao.Properties;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;

import de.greenrobot.dao.query.QueryBuilder;

public class EatBo {

	private EatDao eatDao;

	public EatBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		eatDao = mDaoSession.getEatDao();
	}

	public Eat getById(long id) {
		return eatDao.load(id);
	}

	/**
	 * 保存或修改
	 * 
	 * @param Eat
	 * @return
	 */
	public long saveUpdateEat(Eat eat) {
		return eatDao.insertOrReplace(eat);
	}
	
	public void updateEat(Eat eat){
		eatDao.update(eat);
	}
	
	/**
	 * 保存或修改，根据服务器id
	 * @param diabetes
	 */
	public void saveByServerId(Eat eat){
		QueryBuilder<Eat> qb = eatDao.queryBuilder();
		qb.where(Properties.Serverid.eq(eat.getServerid()));
		List<Eat> list = qb.list();
		if(list.size() > 1){
			eat.setId(list.get(0).getId());
			eatDao.update(eat);
		}else{
			eatDao.insert(eat);
		}
	}

	/**
	 * 获取某一天的所有值
	 * 
	 * @param mid
	 * @param day
	 * @return
	 */
	public List<Eat> listDayEat(String mid, String day) {
		QueryBuilder<Eat> qb = eatDao.queryBuilder();
		qb.where(Properties.Status.notEq(ContantsUtil.DELETE), Properties.Service_mid.eq(mid),
				Properties.Day.eq(day)).orderAsc(Properties.DinnerType);
		return qb.list();
	}
	
	public List<Eat> list(String mid){
		QueryBuilder<Eat> qb = eatDao.queryBuilder();
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
	public List<Eat> loadTotal(String mid, long lastTime) {
		QueryBuilder<Eat> qb = eatDao.queryBuilder();
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
	public List<Eat> loadTotalOrder(String mid, long lastTime,long after) {
		QueryBuilder<Eat> qb = eatDao.queryBuilder();
		qb.where(Properties.Status.notEq(ContantsUtil.DELETE),
				Properties.Service_mid.eq(mid),
				Properties.Create_time.lt(after),
				Properties.Create_time.gt(lastTime)).orderAsc(Properties.Create_time);
		return qb.list();
	}

	/**
	 * 查询需要同步到服务端数据
	 * 
	 * @return
	 */
	public List<Eat> loadSycnData() {
		QueryBuilder<Eat> qb = eatDao.queryBuilder();
		qb.where(Properties.Status.notEq(ContantsUtil.SERVER));
		return qb.list();
	}

	/**
	 * 根据服务端id删除本地数据
	 * 
	 * @param serverId
	 */
	public void deleteData(String serverId) {
		QueryBuilder<Eat> qb = eatDao.queryBuilder();
		qb.where(Properties.Serverid.eq(serverId));
		eatDao.deleteInTx(qb.list());
	}
	
	/**
	 * 本地删除
	 * @param diabetes
	 */
	public void deleteLocal(Eat eat){
		if(CheckUtil.isNull(eat.getServerid())){
			eatDao.delete(eat);
		}else{
			eat.setStatus((short) ContantsUtil.DELETE);
			eatDao.update(eat);
		}
	}
	
	public void delete(Eat eat){
		eatDao.delete(eat);
	}
}

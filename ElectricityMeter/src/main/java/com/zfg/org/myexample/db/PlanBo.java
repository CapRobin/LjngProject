package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.List;

import android.content.Context;

import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.Plan;
import com.zfg.org.myexample.db.dao.PlanDao;

import de.greenrobot.dao.query.QueryBuilder;

public class PlanBo {

	private PlanDao planDao;

	public PlanBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		planDao = mDaoSession.getPlanDao();
	}

	public List<Plan> listData() {
		QueryBuilder<Plan> qb = planDao.queryBuilder();
		qb.orderAsc(PlanDao.Properties.Idx);
		return qb.list();
	}

	public void save(Plan plan) {
		planDao.insert(plan);
	}

	public void saveList(List<Plan> lists) {
		planDao.deleteAll();
		planDao.insertInTx(lists);
	}

}

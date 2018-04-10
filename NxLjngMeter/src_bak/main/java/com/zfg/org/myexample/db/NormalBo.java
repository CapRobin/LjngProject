package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.List;

import android.content.Context;

import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.Normal;
import com.zfg.org.myexample.db.dao.NormalDao;

import de.greenrobot.dao.query.QueryBuilder;

public class NormalBo {
	private NormalDao dao;

	public NormalBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		dao = mDaoSession.getNormalDao();
	}

	public void saveNormal(Normal normal) {
		Normal temp = getByIsueId(normal.getIssueId());
		if(temp == null){
			normal.setNum(1);
			dao.insertOrReplace(normal);
		}else{
			temp.setNum(temp.getNum() + 1);
		}
	}

	public List<Normal> listNormal(long type) {
		QueryBuilder<Normal> qb = dao.queryBuilder();
		qb.where(NormalDao.Properties.Parent.eq(type)).orderDesc(NormalDao.Properties.Num);
		return qb.list();
	}
	
	public Normal getByIsueId(long id){
		QueryBuilder<Normal> qb = dao.queryBuilder();
		qb.where(NormalDao.Properties.IssueId.eq(id));
		List<Normal> datas = qb.list();
		if(datas.size() == 0){
			return null;
		}
		return datas.get(0);
	}

	public Normal getById(long id) {
		return dao.load(id);
	}

	public void update(Normal normal) {
		dao.update(normal);
	}

	public void delete(long id) {
		dao.deleteByKey(id);
	}

	public void clearData() {
		dao.deleteAll();
	}
}

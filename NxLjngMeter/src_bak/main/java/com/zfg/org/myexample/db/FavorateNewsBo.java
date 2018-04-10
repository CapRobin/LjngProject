package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.List;

import android.content.Context;

import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.Favorate;
import com.zfg.org.myexample.db.dao.FavorateDao;
import de.greenrobot.dao.query.QueryBuilder;

public class FavorateNewsBo {

	private FavorateDao dao;

	public FavorateNewsBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		dao = mDaoSession.getFavorateDao();
	}

	public long saveNews(Favorate medicine) {
		return dao.insertOrReplace(medicine);
	}
	
	public void saveArray(List<Favorate> list) {
		dao.insertInTx(list);
	}

	public List<Favorate> listNews() {
		QueryBuilder<Favorate> qb = dao.queryBuilder();
		return qb.list();
	}

	public Favorate getById(long id) {
		return dao.load(id);
	}

	public void delete(long id) {
		dao.deleteByKey(id);
	}

	public void clearData(){
		dao.deleteAll();
	}
}

package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.Indicate;
import com.zfg.org.myexample.db.dao.IndicateDao;
import com.zfg.org.myexample.db.dao.IndicateValue;
import com.zfg.org.myexample.db.dao.IndicateValueDao;
import com.zfg.org.myexample.db.dao.IndicateValueDao.Properties;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;

import de.greenrobot.dao.query.QueryBuilder;

public class IndicateBo {
	private IndicateDao dao;
	private IndicateValueDao valueDao;

	public IndicateBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		dao = mDaoSession.getIndicateDao();
		valueDao = mDaoSession.getIndicateValueDao();
	}

	public long saveIndicate(Indicate normal) {
		return dao.insertOrReplace(normal);
	}

	public void updateIndicateValue(IndicateValue normal) {
		valueDao.update(normal);
	}

	public List<IndicateValue> list(String mid) {
		QueryBuilder<IndicateValue> qb = valueDao.queryBuilder();
		qb.where(Properties.Service_mid.eq(mid),
				Properties.Status.notEq(ContantsUtil.SERVER));
		return qb.list();
	}

	/**
	 * 保存明细数据
	 * 
	 * @param value
	 * @param iId
	 * @return
	 */
	public long saveValue(String key, String group, String markNo, float value,
			long iId, String uid, int level, long time) {
		Indicate model = getByKey(key, uid);
		if (model != null) {
			model.setLevel(level);
			if (model.getUpdate_time() < time) {
				if (model.getLast_value() < value) {
					model.setUp_down(0);
				} else if (model.getLast_value() == value) {
					model.setUp_down(-1);
				} else {
					model.setUp_down(1);
				}
				if(model.getLast_value() == 0){
					model.setUp_down(-1);
				}
				model.setUpdate_time(time);
				model.setLast_value(value);
			}
			dao.update(model);
		}

		IndicateValue indicateVal = new IndicateValue();
		indicateVal.setStatus((short) ContantsUtil.NO_SERVER);
		indicateVal.setValue(value);
		indicateVal.setGroup(group);
		indicateVal.setMarkNo(markNo);
		indicateVal.setKey(key);
		indicateVal.setLevel(level);
		indicateVal.setCreate_time(time);
		indicateVal.setUpdate_time(System.currentTimeMillis());
		indicateVal.setService_mid(uid);
		return valueDao.insert(indicateVal);
	}

	public void saveByServerId(IndicateValue value) {
		QueryBuilder<IndicateValue> qb = valueDao.queryBuilder();
		qb.where(Properties.Serverid.eq(value.getServerid()));
		List<IndicateValue> list = qb.list();
		if (list.size() > 0) {
			value.setId(list.get(0).getId());
			valueDao.update(value);
		} else {
			valueDao.insert(value);
		}
		Indicate indicate = getByKey(value.getKey(), value.getService_mid());
		if (indicate != null) {
			if (indicate.getUpdate_time() <= value.getUpdate_time()) {
				if (indicate.getLast_value() < value.getValue()) {
					indicate.setUp_down(0);
				} else if (indicate.getLast_value() == value.getValue()) {
					indicate.setUp_down(-1);
				} else {
					indicate.setUp_down(1);
				}
				if(indicate.getLast_value() == 0){
					indicate.setUp_down(-1);
				}
				indicate.setLevel(value.getLevel());
				indicate.setUpdate_time(value.getCreate_time());
				indicate.setLast_value(value.getValue());
				indicate.setValue1(value.getValue1());
				dao.update(indicate);
			}
		}
	}

	public long savePress(String key, String group, String markNo, float value,
			float value1, long iId, String uid, int level, int level1, long time) {
		Indicate model = getByKey(key, uid);
		model.setLevel(level);
		if (model.getUpdate_time() < time) {
			if (model.getLast_value() < value) {
				model.setUp_down(0);
			} else if (model.getLast_value() == value) {
				model.setUp_down(-1);
			} else {
				model.setUp_down(1);
			}
			if(model.getLast_value() == 0){
				model.setUp_down(-1);
			}
			model.setUpdate_time(time);
			model.setLast_value(value);
			model.setValue1(value1);
		}
		dao.update(model);

		IndicateValue indicateVal = new IndicateValue();
		indicateVal.setStatus((short) ContantsUtil.NO_SERVER);
		indicateVal.setService_mid(model.getService_mid());
		indicateVal.setValue(value);
		indicateVal.setValue1(value1);
		indicateVal.setGroup(group);
		indicateVal.setMarkNo(markNo);
		indicateVal.setKey(key);
		indicateVal.setLevel(level);
		indicateVal.setLevel1(level1);
		indicateVal.setCreate_time(time);
		indicateVal.setUpdate_time(System.currentTimeMillis());
		indicateVal.setService_mid(uid);
		return valueDao.insert(indicateVal);
	}

	public void deleteValue(IndicateValue value, long indicateId) {
		if (value != null) {
			Indicate indicate = getByKey(value.getKey(), value.getService_mid());
			if (CheckUtil.isNull(indicate.getService_mid())) {
				valueDao.delete(value);
			} else {
				value.setStatus(ContantsUtil.DELETE);
				valueDao.update(value);
			}
			if (value.getCreate_time() == indicate.getUpdate_time()) {
				QueryBuilder<IndicateValue> qb = valueDao.queryBuilder();
				qb.where(
						IndicateValueDao.Properties.Service_mid.eq(value
								.getService_mid()),
						IndicateValueDao.Properties.Status
								.notEq(ContantsUtil.DELETE),
						IndicateValueDao.Properties.Key.eq(indicate.getKey()))
						.orderDesc(IndicateValueDao.Properties.Update_time)
						.limit(2);
				List<IndicateValue> datas = qb.list();
				if (datas.size() > 1) {
					IndicateValue lastValue = datas.get(0);
					IndicateValue lastValue1 = datas.get(1);
					indicate.setLevel(lastValue.getLevel());
					indicate.setUpdate_time(lastValue.getCreate_time());
					if (lastValue.getValue() > lastValue1.getValue()) {
						indicate.setUp_down(ContantsUtil.UP);
					} else if (lastValue.getValue() == lastValue1.getValue()) {
						indicate.setUp_down(-1);
					} else {
						indicate.setUp_down(ContantsUtil.DOWN);
					}
					indicate.setLast_value(lastValue.getValue());
					dao.update(indicate);
				} else {
					indicate.setLast_value(0);
					indicate.setUpdate_time(0);
					indicate.setLevel(1);
					indicate.setUp_down(-1);
					dao.update(indicate);
				}
			}
		}
	}

	public void updateValue(float valueFlo, IndicateValue value,
			long indicateId, int level, long time) {
		Indicate indicate = getByKey(value.getKey(), value.getService_mid());
		value.setValue(valueFlo);
		value.setLevel(level);
		value.setUpdate_time(time);
		valueDao.update(value);

		if (indicate != null) {
			if (indicate.getUpdate_time() <= time) {
				if (indicate.getLast_value() < valueFlo) {
					indicate.setUp_down(0);
				} else if (indicate.getLast_value() == valueFlo) {
					indicate.setUp_down(-1);
				} else {
					indicate.setUp_down(1);
				}
				if(indicate.getLast_value() == 0){
					indicate.setUp_down(-1);
				}
				indicate.setUpdate_time(time);
				indicate.setLast_value(valueFlo);
				indicate.setValue1(value.getValue1());
				dao.update(indicate);
			}
		}
	}

	public void updatePress(float valueFlo, float value1, IndicateValue value,
			long indicateId, int level, int level1, long time) {
		Indicate indicate = getByKey(value.getKey(), value.getService_mid());
		value.setValue(valueFlo);
		value.setValue1(value1);
		value.setLevel(level);
		value.setLevel1(level1);
		value.setUpdate_time(time);
		valueDao.update(value);

		if (indicate.getUpdate_time() <= time) {
			if (indicate.getLast_value() < valueFlo) {
				indicate.setUp_down(0);
			} else if (indicate.getLast_value() == valueFlo) {
				indicate.setUp_down(-1);
			} else {
				indicate.setUp_down(1);
			}
			if(indicate.getLast_value() == 0){
				indicate.setUp_down(-1);
			}
			indicate.setUpdate_time(time);
			indicate.setLast_value(valueFlo);
			indicate.setValue1(value1);
		}
		dao.update(indicate);
	}

	public List<Indicate> listIndicate(String userId) {
		QueryBuilder<Indicate> qb = dao.queryBuilder();
		qb.where(IndicateDao.Properties.Service_mid.eq(userId));
		return qb.list();
	}

	public Map<String, Indicate> getMapIndicate(String userId) {
		QueryBuilder<Indicate> qb = dao.queryBuilder();
		qb.where(IndicateDao.Properties.Service_mid.eq(userId));
		List<Indicate> data = qb.list();
		Map<String, Indicate> maps = new HashMap<String, Indicate>();
		for (Indicate indicate : data) {
			maps.put(indicate.getKey(), indicate);
		}
		return maps;
	}

	public Map<String, IndicateValue> keyMapIndicate(String group) {
		QueryBuilder<IndicateValue> qb = valueDao.queryBuilder();
		qb.where(IndicateValueDao.Properties.Group.eq(group),
				IndicateValueDao.Properties.Status.notEq(ContantsUtil.DELETE));
		List<IndicateValue> data = qb.list();
		Map<String, IndicateValue> maps = new HashMap<String, IndicateValue>();
		for (IndicateValue indicate : data) {
			Log.d("dd", indicate.getKey());
			maps.put(indicate.getKey(), indicate);
		}
		return maps;
	}

	public void deleteByGroup(String group, long indicateId, String mid) {
		QueryBuilder<IndicateValue> qb = valueDao.queryBuilder();
		qb.where(IndicateValueDao.Properties.MarkNo.eq(group));
		List<IndicateValue> data = qb.list();
		for (IndicateValue value : data) {
			value.setStatus(ContantsUtil.DELETE);
		}
		valueDao.updateInTx(data);
		Indicate indicate = dao.load(indicateId);
		qb.where(IndicateValueDao.Properties.Service_mid.eq(mid),
				IndicateValueDao.Properties.Status.notEq(ContantsUtil.DELETE),
				IndicateValueDao.Properties.Key.eq(indicate.getKey()))
				.orderDesc(IndicateValueDao.Properties.Create_time).limit(2);
		List<IndicateValue> datas = qb.list();
		if (datas.size() > 1) {
			IndicateValue lastValue = datas.get(0);
			IndicateValue lastValue1 = datas.get(1);
			indicate.setLevel(lastValue.getLevel());
			indicate.setUpdate_time(lastValue.getCreate_time());
			if (lastValue.getValue() > lastValue1.getValue()) {
				indicate.setUp_down(ContantsUtil.UP);
			} else if (lastValue.getValue() == lastValue1.getValue()) {
				indicate.setUp_down(-1);
			} else {
				indicate.setUp_down(ContantsUtil.DOWN);
			}
			indicate.setLast_value(lastValue.getValue());
			dao.update(indicate);
		} else {
			indicate.setLast_value(0);
			indicate.setUpdate_time(0);
			indicate.setLevel(1);
			indicate.setUp_down(-1);
			dao.update(indicate);
		}
	}

	/**
	 * 列举指标值列表
	 * 
	 * @param userId
	 * @param inId
	 * @return
	 */
	public List<IndicateValue> listValue(String userId, String key, long preTime) {
		QueryBuilder<IndicateValue> qb = valueDao.queryBuilder();
		qb.where(IndicateValueDao.Properties.Service_mid.eq(userId),
				IndicateValueDao.Properties.Key.eq(key),
				IndicateValueDao.Properties.Status.notEq(ContantsUtil.DELETE),
				IndicateValueDao.Properties.Create_time.gt(preTime)).orderAsc(
				IndicateValueDao.Properties.Create_time);
		return qb.list();
	}

	public Indicate getById(long id) {
		return dao.load(id);
	}

	public Indicate getByKey(String key, String uid) {
		QueryBuilder<Indicate> qb = dao.queryBuilder();
		qb.where(IndicateDao.Properties.Key.eq(key),
				IndicateDao.Properties.Service_mid.eq(uid));
		List<Indicate> data = qb.list();
		if (data.size() == 0) {
			return null;
		}
		return data.get(0);
	}

	public void delete(long id) {
		dao.deleteByKey(id);
	}

	/**
	 * 根据服务端id删除本地数据
	 * 
	 * @param serverId
	 */
	public void deleteData(String serverId) {
		QueryBuilder<IndicateValue> qb = valueDao.queryBuilder();
		qb.where(Properties.Serverid.eq(serverId));
		valueDao.deleteInTx(qb.list());
	}

	public void clearData() {
		dao.deleteAll();
	}
}

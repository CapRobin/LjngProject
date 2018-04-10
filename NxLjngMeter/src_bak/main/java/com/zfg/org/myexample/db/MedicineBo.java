package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.List;

import android.content.Context;

import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.Medicine;
import com.zfg.org.myexample.db.dao.MedicineDao;
import com.zfg.org.myexample.db.dao.MedicineDao.Properties;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * 药物管理
 * 
 * @author longbh
 * 
 */
public class MedicineBo {
	private MedicineDao dao;

	public MedicineBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		dao = mDaoSession.getMedicineDao();
	}

	public long saveMedicine(Medicine medicine) {
		return dao.insertOrReplace(medicine);
	}
	
	/**
	 * 保存或修改，根据服务器id
	 * 
	 * @param sport
	 */
	public long saveByServerId(Medicine medicine) {
		QueryBuilder<Medicine> qb = dao.queryBuilder();
		qb.where(Properties.Serverid.eq(medicine.getServerid()));
		List<Medicine> list = qb.list();
		if (list.size() > 1) {
			medicine.setId(list.get(0).getId());
			dao.update(medicine);
			return medicine.getId();
		} else {
			return dao.insert(medicine);
		}
	}
	
	public void update(Medicine entity){
		dao.update(entity);
	}

	public List<Medicine> listMedicines(String mid) {
		QueryBuilder<Medicine> qb = dao.queryBuilder();
		qb.where(Properties.Status.notEq(ContantsUtil.DELETE),
				Properties.Service_mid.eq(mid));
		return qb.list();
	}
	
	public List<Medicine> list(String mid){
		QueryBuilder<Medicine> qb = dao.queryBuilder();
		qb.where(Properties.Service_mid.eq(mid),
				Properties.Status.notEq(ContantsUtil.SERVER));
		return qb.list();
	}
	
	/**
	 * 根据服务端id删除本地数据
	 * 
	 * @param serverId
	 */
	public void deleteData(String serverId) {
		QueryBuilder<Medicine> qb = dao.queryBuilder();
		qb.where(Properties.Serverid.eq(serverId));
		dao.deleteInTx(qb.list());
	}
	
	/**
	 * 本地删除
	 * @param diabetes
	 */
	public void deleteLocal(Medicine medicine){
		if(CheckUtil.isNull(medicine.getServerid())){
			dao.delete(medicine);
		}else{
			medicine.setStatus((short) ContantsUtil.DELETE);
			dao.update(medicine);
		}
	}
	
	public Medicine getById(long id){
		return dao.load(id);
	}

	public void delete(long id) {
		dao.deleteByKey(id);
	}
}

package com.zfg.org.myexample.db;

import android.content.Context;

import com.zfg.org.myexample.application.DbApplication;
import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.db.dao.MeterInfoDao;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;


/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：MeterInfoBo
 * Describe：手机端数据库配置
 * Date：2018-03-27 15:29:55
 * Author: CapRobin@yeah.net
 *
 */
public class MeterInfoBo {

    private MeterInfoDao dao;

    public MeterInfoBo(Context context) {
        DaoSession mDaoSession = DbApplication.getDaoSession(context);
        dao = mDaoSession.getMeterinfoDao();
    }

    public long saveMeterInfo(MeterInfo meterInfo) {
        return dao.insertOrReplace(meterInfo);
    }

    /**
     * 保存或修改，根据服务器id
     *
     * @param sport
     */
    public long saveByServerId(MeterInfo meterInfo) {
        QueryBuilder<MeterInfo> qb = dao.queryBuilder();
        qb.where(MeterInfoDao.Properties.Comm_Address.eq(meterInfo.getComm_address()));
        List<MeterInfo> list = qb.list();
        if (list.size() > 1) {
            meterInfo.setId(list.get(0).getId());
            dao.update(meterInfo);
            return meterInfo.getId();
        } else {
            return dao.insert(meterInfo);
        }
    }

    public void update(MeterInfo entity){
        dao.update(entity);
    }

    public List<MeterInfo> listMeterInfos(String mid) {
        QueryBuilder<MeterInfo> qb = dao.queryBuilder();
//        qb.where(MeterInfoDao.Properties.Status.notEq(ContantsUtil.DELETE),
//                MeterInfoDao.Properties.Service_mid.eq(mid));
        return qb.list();
    }

    public List<MeterInfo> list(String mid){
        QueryBuilder<MeterInfo> qb = dao.queryBuilder();
        qb.where(MeterInfoDao.Properties.Customer_name.eq(mid));
//                MeterInfoDao.Properties..notEq(ContantsUtil.SERVER));
        return qb.list();
    }

    /**
     * Describe：查询所有数据
     * Params: 
     * Date：2018-03-27 15:30:20
     */
    
    public List<MeterInfo> listAllData() {
        QueryBuilder<MeterInfo> qb = dao.queryBuilder();
        qb.orderAsc(MeterInfoDao.Properties.Comm_Address);
        return qb.list();
    }



    /**
     * 根据服务端id删除本地数据
     *
     * @param serverId
     */
    public void deleteData(String serverId) {
        QueryBuilder<MeterInfo> qb = dao.queryBuilder();
        qb.where(MeterInfoDao.Properties.Id.eq(serverId));
        dao.deleteInTx(qb.list());
    }

    /**
     * 本地删除
     * @param diabetes
     */
    public void deleteLocal(MeterInfo meterInfo){
//        if(CheckUtil.isNull(meterInfo.getServerid())){
//            dao.delete(meterInfo);
//        }else{
//            meterInfo.setStatus((short) ContantsUtil.DELETE);
//            dao.update(meterInfo);
//        }
    }
    /**
     * Describe：保存数据
     * Params:
     * Date：2018-03-27 15:39:38
     */
    public void saveList(List<MeterInfo> lists) {
        dao.deleteAll();
        dao.insertInTx(lists);
    }

    public MeterInfo getById(long id){
        return dao.load(id);
    }

    public void delete(long id) {
        dao.deleteByKey(id);
    }
}

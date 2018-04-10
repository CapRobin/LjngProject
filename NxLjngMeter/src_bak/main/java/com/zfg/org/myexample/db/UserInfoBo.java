package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.List;

import android.content.Context;

import com.zfg.org.myexample.db.dao.Alarm;
import com.zfg.org.myexample.db.dao.Common;
import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.UserInfo;
import com.zfg.org.myexample.db.dao.UserInfoDao;
import com.zfg.org.myexample.db.dao.UserInfoDao.Properties;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * 类/接口注释
 * 
 * @author Chanjc@ifenguo.com
 * @createDate 2014年7月15日
 * 
 */
public class UserInfoBo {

    private UserInfoDao userInfoDao;
    
    public UserInfoBo(Context context) {
        DaoSession mDaoSession = DbApplication.getDaoSession(context);
        userInfoDao = mDaoSession.getUserInfoDao();
    }
    
    public UserInfoDao getUserInfoDao(){
        return userInfoDao;
    }
    
    /**
     * 新增  用户个人信息
     * @param userInfo
     * @return 影响行数
     */
    public long saveUserInfo(UserInfo userInfo) {
        return userInfoDao.insert(userInfo);
    }
    
    
    /**
     * 删除 用户个人信息
     * @param userInfo
     */
    public void deleteUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            userInfoDao.delete(userInfo);
        }
    }
    
    /**
     * 修改 用户个人信息
     * 
     * @param userInfo
     * @return
     */
    public void updateUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            userInfoDao.update(userInfo);
        }
    }
    
    
    /**
     * 获取一个用户信息
     */
    public UserInfo getUserInfo(long id) {
        QueryBuilder<UserInfo> qb = userInfoDao.queryBuilder();
        qb.where(Properties.Id.eq(id));
        if (qb.list().size() > 0) {
            return (UserInfo) qb.list().get(0);
        } else {
            return null;
        }
    }
    
    /**
     * 获取一个用户信息
     */
    public UserInfo getUinfoByMid(Object mid) {
        QueryBuilder<UserInfo> qb = userInfoDao.queryBuilder();
        qb.where(Properties.Mid.eq(mid));
        if (qb.list().size() > 0) {
            return (UserInfo) qb.list().get(0);
        } else {
            return null;
        }
    }
    
    public List<UserInfo> list(String uid){
    	 QueryBuilder<UserInfo> qb = userInfoDao.queryBuilder();
         qb.where(Properties.Uid.eq(uid));
         return qb.list();
    }
}

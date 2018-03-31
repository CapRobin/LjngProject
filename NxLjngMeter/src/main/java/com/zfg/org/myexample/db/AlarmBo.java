package com.zfg.org.myexample.db;

import com.zfg.org.myexample.application.DbApplication;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfg.org.myexample.db.dao.Alarm;
import com.zfg.org.myexample.db.dao.AlarmDao;
import com.zfg.org.myexample.db.dao.AlarmDao.Properties;
import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.utils.DateUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.util.Log;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * 闹钟bo类
 */
public class AlarmBo {

	// 闹钟静态常量
	public static final String ALARM_ALERT_ACTION = "cn.dian.diabetes.ALARM_ALERT";
	public static final String ALARM_RAW_DATA = "intent.extra.alarm_raw";
	public static final String ALARM_INTENT_EXTRA = "intent.extra.alarm";
	public static final String ALARM_KILLED = "alarm_killed";
	public static final String ALARM_DISMISS_ACTION = "cn.ctibet.calendar.ALARM_DISMISS";
	public static final String ALARM_KILLED_TIMEOUT = "alarm_killed_timeout"; // 删除闹
	public static final String ALARM_DONE_ACTION = "cn.ctibet.calendar.ALARM_DONE";

	private AlarmDao alarmDao;
	private Context context;

	public AlarmBo(Context context) {
		DaoSession mDaoSession = DbApplication.getDaoSession(context);
		alarmDao = mDaoSession.getAlarmDao();
		this.context = context;
	}

	public boolean isSaved(Alarm alarm) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Type.eq(alarm.getType()),
				Properties.Sub_type.eq(alarm.getSub_type()),
				Properties.Day_of_week.eq(alarm.getDay_of_week()),
				Properties.Service_mid.eq(alarm.getService_mid()));
		return qb.buildCount().count() > 0 ? true : false;
	}

	public Alarm getById(long id) {
		return alarmDao.load(id);
	}

	public List<Alarm> list(String mid, int type) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Service_mid.eq(mid), Properties.Type.eq(type));
		return qb.list();
	}

	public List<Alarm> orderList(String mid, int type) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Service_mid.eq(mid), Properties.Type.eq(type))
				.orderAsc(Properties.Sub_type);
		return qb.list();
	}

	/**
	 * 读取计划闹钟
	 * @param mid
	 * @param alarmType
	 * @return
	 */
	public Map<String, Alarm> getPlanMapAlarm(String mid, int alarmType) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Service_mid.eq(mid), Properties.Type.eq(alarmType));
		List<Alarm> listData = qb.list();
		Map<String, Alarm> data = new HashMap<String, Alarm>();
		for (Alarm alarm : listData) {
			data.put("plan" + alarm.getSub_type(), alarm);
		}
		return data;
	}

	public long saveOrUpdate(Alarm alarm) {
		if (isSaved(alarm)) {
			alarmDao.update(alarm);
		} else {
			long id = alarmDao.insert(alarm);
			alarm.setId(id);
		}
		setNextAlarm(alarm.getService_mid(),alarm.getUid());
		return alarm.getId();
	}

	/**
	 * 新建一个闹钟
	 * 
	 * @param alarm
	 * @return
	 */
	public long saveAlarm(Alarm alarm) {
		long id = alarmDao.insert(alarm);
		setNextAlarm(alarm.getService_mid(),alarm.getUid());
		return id;
	}

	public void saveAlarms(long oid, List<Alarm> alarms, String mId,String uId) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Oid.eq(oid));
		alarmDao.deleteInTx(qb.list());

		alarmDao.insertOrReplaceInTx(alarms);
		setNextAlarm(mId,uId);
	}
	
	public void saveMedicineAlarms(long oid, List<Alarm> alarms,String mid, String uId) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Oid.eq(oid));
		alarmDao.deleteInTx(qb.list());

		alarmDao.insertInTx(alarms);
		setNextAlarm(mid,uId);
	}
	
	public void updateAlarms(List<Alarm> alarms){
		alarmDao.updateInTx(alarms);
	}

	public void saveAlarmsNoNext(long oid, List<Alarm> alarms,String mid, String uId) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Oid.eq(oid));
		List<Alarm> lists = qb.list();
		if (lists.size() != 0) {
			alarmDao.deleteInTx(lists);
		}
		alarmDao.insertOrReplaceInTx(alarms);
		setNextAlarm(mid,uId);
	}

	/**
	 * 闹钟更新
	 * 
	 * @param alarm
	 */
	public void updateByMap(String mid, String uid,Map<String, Alarm> mapData) {
		Collection<Alarm> listData = mapData.values();
		alarmDao.insertOrReplaceInTx(listData);
		setNextAlarm(mid,uid);
	}

	/**
	 * 更新闹钟信息
	 * 
	 * @param subType
	 * @param type
	 * @param hour
	 * @param mini
	 */
	public void updateAlarm(String mid,String uid, int type, int subType, int hour,
			int mini) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Service_mid.eq(mid),
				Properties.Sub_type.eq(subType), Properties.Type.eq(type));
		List<Alarm> list = qb.list();
		for (Alarm alarm : list) {
			alarm.setHour((short) hour);
			alarm.setMinite((short) mini);
		}
		alarmDao.updateInTx(list);
		setNextAlarm(mid,uid);
	}

	/**
	 * 计算下一个闹钟时间
	 * 
	 * @param mid
	 * @return
	 */
	public Alarm caculateNext(String mid,String uid) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Uid.eq(uid));
		List<Alarm> listData = qb.list();
		long minTime = Long.MAX_VALUE;
		Alarm minAlarm = null;
		long now = System.currentTimeMillis();
		for (Alarm alarm : listData) {
			if (!alarm.getEnable()) {
				continue;
			}
			long alarmTime = alarm.caculateAlarm_time();
			if (alarmTime > now && alarmTime < minTime) {
				minTime = alarmTime;
				minAlarm = alarm;
				minAlarm.setAlarm_time(alarmTime);
			}
		}
		if (minAlarm != null) {
			Log.d("next_time", DateUtil.parseToString(minAlarm.getAlarm_time()));
		}
		return minAlarm;
	}

	public long setNextAlarm(String mid,String uid) {
		Alarm alarm = caculateNext(mid,uid);
		if (alarm != null) {
			enableAlert(alarm, alarm.getAlarm_time());
			return alarm.getAlarm_time();
		} else {
			disableAlert();
			return 0;
		}
	}

	/**
	 * 开启闹钟
	 * 
	 * @param context
	 * @param alarm
	 */
	private void enableAlert(final Alarm alarm, long time) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(ALARM_ALERT_ACTION);
		Parcel out = Parcel.obtain();
		alarm.writeToParcel(out, 0);
		out.setDataPosition(0);
		intent.putExtra(ALARM_RAW_DATA, out.marshall());
		out.recycle();
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.set(AlarmManager.RTC_WAKEUP, time, sender);
	}

	/**
	 * 关闭闹钟
	 * 
	 * @param id
	 *            Alarm ID.
	 */
	public void disableAlert() {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0,
				new Intent(ALARM_ALERT_ACTION),
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.cancel(sender);
		setStatusBarIcon(context, false);
	}

	private void setStatusBarIcon(Context context, boolean enabled) {
		Intent alarmChanged = new Intent("android.intent.action.ALARM_CHANGED");
		alarmChanged.putExtra("alarmSet", enabled);
		context.sendBroadcast(alarmChanged);
	}

	public void delete(long id) {
		alarmDao.deleteByKey(id);
	}

	/**
	 * 更新某一个用户的所有闹钟
	 * 
	 * @param lists
	 * @param mid
	 */
	public void updateAlarm(List<Alarm> lists, String mid, String uId,int type) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Service_mid.eq(mid), Properties.Type.eq(type));
		alarmDao.deleteInTx(qb.list());
		alarmDao.insertInTx(lists);
		setNextAlarm(mid,uId);
	}

	public List<Alarm> listAlarms(long oId) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Oid.eq(oId));
		return qb.list();
	}

	public void deleteAlarm(long oid, String mid,String uid) {
		QueryBuilder<Alarm> qb = alarmDao.queryBuilder();
		qb.where(Properties.Oid.eq(oid));
		alarmDao.deleteInTx(qb.list());
		setNextAlarm(mid,uid);
	}
}

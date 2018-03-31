package com.zfg.org.myexample.activity.alarm;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.db.AlarmBo;
import com.zfg.org.myexample.db.dao.Alarm;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.StringUtil;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;

/**
 * 闹钟监听器，并打开一个activity的弹出框
 * @author hua
 *
 */
@SuppressWarnings("deprecation")
public class AlarmReceiver extends BroadcastReceiver {

	private AlarmBo bo;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (AlarmBo.ALARM_KILLED.equals(intent.getAction())) {
			updateNotification(context,
					(Alarm) intent
							.getParcelableExtra(AlarmBo.ALARM_INTENT_EXTRA),
					intent.getIntExtra(AlarmBo.ALARM_KILLED_TIMEOUT, -1));
			return;
		} else if (!AlarmBo.ALARM_ALERT_ACTION.equals(intent.getAction())) {
			return;
		}
		Alarm alarm = null;

		final byte[] data = intent.getByteArrayExtra(AlarmBo.ALARM_RAW_DATA);
		if (data != null) {
			Parcel in = Parcel.obtain();
			in.unmarshall(data, 0, data.length);
			in.setDataPosition(0);
			alarm = new Alarm(in);
			in.recycle();
		}
		bo = new AlarmBo(context);
		if (alarm == null) {
			bo.setNextAlarm(ContantsUtil.DEFAULT_TEMP_UID,
					ContantsUtil.curUser.getService_uid());
			return;
		}

		bo.setNextAlarm(ContantsUtil.DEFAULT_TEMP_UID,
				ContantsUtil.curUser.getService_uid());
		int id = StringUtil.toInt(alarm.getId());

		AlarmAlertWakeLock.acquireCpuWakeLock(context);

		/* Close dialogs and window shade */
		Intent closeDialogs = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		context.sendBroadcast(closeDialogs);

		// 启动音乐service
		Intent playAlarm = new Intent(AlarmBo.ALARM_ALERT_ACTION);
		playAlarm.putExtra(AlarmBo.ALARM_INTENT_EXTRA, alarm);
		context.startService(playAlarm);

		// 启动activity
		Intent notify = new Intent(context, AlarmAlert.class);
		notify.putExtra(AlarmBo.ALARM_INTENT_EXTRA, alarm);
		PendingIntent pendingNotify = PendingIntent.getActivity(context, id,
				notify, 0);
		String label = context.getString(R.string.app_name);
		Notification n = new Notification(R.mipmap.ic_launcher, label, 0);
//		n.setLatestEventInfo(context, label, alarm.getTitle(), pendingNotify);
		n.flags |= Notification.FLAG_SHOW_LIGHTS
				| Notification.FLAG_ONGOING_EVENT;
		n.defaults |= Notification.DEFAULT_LIGHTS;

		// NEW: Embed the full-screen UI here. The notification manager will
		// take care of displaying it if it's OK to do so.
		Class<?> c = AlarmAlert.class;
		Intent alarmAlert = new Intent(context, c);
		alarmAlert.putExtra(AlarmBo.ALARM_INTENT_EXTRA, alarm);
		alarmAlert.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_NO_USER_ACTION);
		n.fullScreenIntent = PendingIntent.getActivity(context, id, alarmAlert,
				0);
		NotificationManager nm = getNotificationManager(context);
		nm.notify(id, n);
	}

	private void updateNotification(Context context, Alarm alarm, int timeout) {
		NotificationManager nm = getNotificationManager(context);
		String label = alarm.getTitle();
		Notification n = new Notification(R.mipmap.ic_launcher, label,
				alarm.getAlarm_time());
//		n.setLatestEventInfo(context, label, context.getString(R.string.app_name), null);
		n.flags |= Notification.FLAG_AUTO_CANCEL;
		nm.cancel(StringUtil.toInt(alarm.getId()));
		nm.notify(StringUtil.toInt(alarm.getId()), n);
	}

	private NotificationManager getNotificationManager(Context context) {
		return (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}
}

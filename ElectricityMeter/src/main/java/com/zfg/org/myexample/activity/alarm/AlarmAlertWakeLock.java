package com.zfg.org.myexample.activity.alarm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;

/**
 * 唤醒屏幕和关闭屏幕
 */
class AlarmAlertWakeLock {

	private static PowerManager.WakeLock sCpuWakeLock;

	/**
	 * 点亮屏幕
	 * @param context
	 */
	@SuppressLint("Wakelock")
	static void acquireCpuWakeLock(Context context) {
		if (sCpuWakeLock != null) {
			return;
		}
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		sCpuWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.ON_AFTER_RELEASE, "AlarmClock");
		sCpuWakeLock.acquire();
	}

	/**
	 * 关闭屏幕
	 */
	static void releaseCpuLock() {
		if (sCpuWakeLock != null) {
			sCpuWakeLock.release();
			sCpuWakeLock = null;
		}
	}
}

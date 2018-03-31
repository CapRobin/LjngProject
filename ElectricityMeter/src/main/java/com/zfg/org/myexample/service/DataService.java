package com.zfg.org.myexample.service;

//import org.ColdStorage.db.UserBo;
import com.zfg.org.myexample.db.dao.User;
import com.zfg.org.myexample.utils.Config;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.utils.ContantsUtil;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * 数据同步service,开启服务在后端同步数据，同步结束之后stopservice
 * 
 * @author longbh
 * 
 */
public class DataService extends Service {

//	private UserBo userBo;
	private Context context;

	private Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			stopSelf();
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Config.startUpdate();
//		userBo = new UserBo(this);
		context = this;
		boolean manager = intent.getBooleanExtra("state", false);
		if (manager) {
			startUpdatehread();
		} else {
			startLoadThread();
		}
		return START_NOT_STICKY;
	}

	private void startLoadThread() {
		Thread loadthread = new Thread() {
			public void run() {
//				User user = userBo
//						.getUserByServerId(ContantsUtil.DEFAULT_TEMP_UID);
//				if (user != null) {
//					if (Config.canUpdate()) {
//						UpdateService service = new UpdateService(context,
//								userBo);
//						service.updateUserProperty();
//						service.updateDiabetes();
//						service.updateAlarm();
//						service.updateEat();
//						service.updateSport();
//						service.updateMedicine();
//						service.updateIndicates();
//						Config.stopUpdate();
//						Preference.instance(getApplicationContext()).putBoolean(
//								Preference.HAS_UPDATE, false);
//						Log.d("update", "上传了一次数据");
//					}
//					LoadingService service = new LoadingService(context, userBo);
//					service.loadingUserSet();
//					service.loadingDbtData();
//					service.loadingEatData();
//					service.loadingSportData();
//					service.loadingMedicineData();
//					service.loadingIndicateData();
//					Config.stopUpdate();
//					Preference.instance(context).putBoolean(
//							Preference.HAS_UPDATE, false);
//					Log.d("load", "下载了一次数据");
//				}
//				handle.sendEmptyMessage(1);
			}
		};
		loadthread.start();
	}

	private void startUpdatehread() {
		Thread loadthread = new Thread() {
			public void run() {
//				User user = userBo
//						.getUserByServerId(ContantsUtil.DEFAULT_TEMP_UID);
//				if (user != null) {
//					UpdateService service = new UpdateService(context, userBo);
//					service.updateUserProperty();
//					service.updateDiabetes();
//					service.updateAlarm();
//					service.updateEat();
//					service.updateSport();
//					service.updateMedicine();
//					service.updateIndicates();
//					Config.stopUpdate();
//					Preference.instance(getApplicationContext()).putBoolean(
//							Preference.HAS_UPDATE, false);
//					Log.d("update", "上传了一次数据");
//				}
//				handle.sendEmptyMessage(1);
			}
		};
		loadthread.start();
	}

	public void onDestroy() {
		super.onDestroy();
		Config.stopUpdate();
	}
}
